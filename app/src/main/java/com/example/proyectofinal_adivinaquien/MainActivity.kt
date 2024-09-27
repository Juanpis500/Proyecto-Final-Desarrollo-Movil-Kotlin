package com.example.proyectofinal_adivinaquien

import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonCancellable.cancel
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

enum class ProviderType{
    GOOGLE
}

class MainActivity : AppCompatActivity() {

    //Declaracion de variables
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    private val GOOGLE_SING_IN = 100

    private lateinit var contenedor_menu : LinearLayout
    private lateinit var contenedor_perfil : LinearLayout

    private lateinit var btn_perfil : Button
    private lateinit var btn_cerrar : Button
    private lateinit var btn_google : Button
    private lateinit var btn_guardar : Button

    private lateinit var txt_nombre_jugador : TextView
    private lateinit var txt_puntuacion_alta : TextView
    private lateinit var txt_ultima_puntuacion : TextView
    private lateinit var txt_victorias : TextView
    private lateinit var txt_derrotas : TextView
    private lateinit var txt_vinculacion : TextView

    private lateinit var preferencias : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var imagen : LottieAnimationView = findViewById(R.id.img_logo)
        imagen.setAnimation(R.raw.wave)

        //Inicializamos las variables
        auth = Firebase.auth

        contenedor_menu = findViewById(R.id.Contenedor_Menu)
        contenedor_perfil = findViewById(R.id.Contenedor_Perfil)

        btn_cerrar = findViewById(R.id.btn_cerrar)
        btn_perfil = findViewById(R.id.btn_perfil)
        btn_google = findViewById(R.id.btn_google)
        btn_guardar = findViewById(R.id.btn_guardar)

        txt_nombre_jugador = findViewById(R.id.txt_nombre_jugador_perfil)
        txt_puntuacion_alta = findViewById(R.id.txt_puntuacion_alta_perfil)
        txt_ultima_puntuacion = findViewById(R.id.txt_ultima_puntuacion_perfil)
        txt_victorias = findViewById(R.id.txt_victorias_perfil)
        txt_derrotas = findViewById(R.id.txt_derrotas_perfil)
        txt_vinculacion = findViewById(R.id.txt_vinculacion)

        preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE)
        val nombre_user = preferencias.getString("nombre","")

        //Actualizamos las estadisticas
        txt_nombre_jugador.setText(nombre_user)
        txt_puntuacion_alta.setText(preferencias.getString("puntuacion_alta",""))
        txt_ultima_puntuacion.setText(preferencias.getString("ultima_puntuacion",""))
        txt_victorias.setText(preferencias.getString("victorias",""))
        txt_derrotas.setText(preferencias.getString("derrotas",""))


        btn_perfil.setOnClickListener {
            contenedor_menu.visibility = View.GONE
            contenedor_perfil.visibility = View.VISIBLE

        }

        btn_cerrar.setOnClickListener {
            contenedor_perfil.visibility =View.GONE
            contenedor_menu.visibility = View.VISIBLE
        }

        btn_guardar.setOnClickListener {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            // Create a new user with a first and last name
            val user = hashMapOf(
                "Nombre_Usuario" to preferencias.getString("nombre",""),
                "Victorias_pvp" to preferencias.getString("victorias",""),
                "Derrotas_pvp" to preferencias.getString("derrotas",""),
                "Puntuacion_alta" to preferencias.getString("puntuacion_alta",""),
                "Ultima_puntuacion" to preferencias.getString("ultima_puntuacion",""),
                "Ultima_Fecha" to currentDate
            )

            // Add a new document with a generated ID
            db.collection("users").document(preferencias.getString("email","").toString())
                .set(user)
                .addOnSuccessListener { documentReference ->
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
        }

        val hainiciado = preferencias.getString("email","")

        if(hainiciado == ""){
            session()
            setup()
        }else{
            txt_vinculacion.setText("Presiona en el boton para guardar el progreso.")
            btn_guardar.visibility = View.VISIBLE
            btn_google.visibility = View.GONE
        }
    }

    private fun setup() {
        btn_google.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("86829375115-9rk8l44opkusf0u9j1eas41e4v122r9s.apps.googleusercontent.com")
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent,GOOGLE_SING_IN)
        }
    }

    private fun session() {
        val email = preferencias.getString("email","")

    }

    override fun onResume() {
        super.onResume()
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
    }

    fun inicarpartidaIA(vista: View){
        val intent = Intent(this, jugadorvsIA::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    fun jugadorvsjugador(vista: View){
        val intent = Intent(this, jugadorvsjugador::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SING_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if(account != null){

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful){
                            val editor : SharedPreferences.Editor = preferencias.edit()
                            txt_vinculacion.setText("Ahora tu cuenta a sido verificada, tus registros se han guardado en la base de datos")
                            btn_google.visibility = View.INVISIBLE
                            editor.putString("email", it.result?.user?.email)
                            editor.commit()

                            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                            val currentDate = sdf.format(Date())
                            // Create a new user with a first and last name
                            val user = hashMapOf(
                                "Nombre_Usuario" to preferencias.getString("nombre",""),
                                "Victorias_pvp" to preferencias.getString("victoria",""),
                                "Derrotas_pvp" to preferencias.getString("derrotas",""),
                                "Puntuacion_alta" to preferencias.getString("puntuacion_alta",""),
                                "Ultima_puntuacion" to preferencias.getString("ultima_puntuacion",""),
                                "Ultima_Fecha" to currentDate
                            )

                            // Add a new document with a generated ID
                            db.collection("users").document(it.result?.user?.email.toString())
                                .set(user)
                                .addOnSuccessListener { documentReference ->

                                }
                                .addOnFailureListener { e ->
                                    Log.w(ContentValues.TAG, "Error adding document", e)
                                }
                        }else{
                            showAlert()
                        }
                    }
                }
            }catch (e: ApiException){
                showAlert()
            }
        }
    }

    private fun showAlert(){
        val builder =AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}