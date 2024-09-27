package com.example.proyectofinal_adivinaquien

import android.app.ActivityOptions
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class Inicio : AppCompatActivity() {
    private lateinit var btn_continuar: Button
    private lateinit var regis_nombre : EditText
    private lateinit var txt_registrado : TextView
    private lateinit var txt_bienvenida : TextView
    private lateinit var titu_regisnom : TextView
    private lateinit var anim_bienvenida : LottieAnimationView

    private lateinit var preferencias : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE)
        val nombre_user = preferencias.getString("nombre","")
        btn_continuar = findViewById(R.id.btn_confirmar)
        titu_regisnom = findViewById(R.id.tit_ingresarnom)
        regis_nombre = findViewById(R.id.ingresar_nombre)
        txt_registrado = findViewById(R.id.registrado)
        txt_bienvenida = findViewById(R.id.txtx_bienvenido)
        anim_bienvenida = findViewById(R.id.anim_bienvenida)

        txt_bienvenida.setText("Bienvenid@ de nuevo " + nombre_user)

        if(nombre_user == ""){
            captura(this)
        }
        else{
            btn_continuar.visibility = View.GONE
            titu_regisnom.visibility = View.GONE
            regis_nombre.visibility = View.GONE
            txt_registrado.visibility = View.GONE
            txt_bienvenida.visibility = View.VISIBLE
            anim_bienvenida.setAnimation(R.raw.hola)
            anim_bienvenida.playAnimation()

            Handler().postDelayed({
                pantallaprincipal(this)
            }, 4000)
        }

    }

    private fun captura(context: Context){
        var bandera : Boolean
        var nombre : String
        val editor : SharedPreferences.Editor = preferencias.edit()

        btn_continuar.setOnClickListener {
            nombre = regis_nombre.text.toString()
            bandera = false
            if(nombre != ""){

                if(bandera == false){
                    //agregamos todos los datos las preferencias
                    editor.putString("nombre", nombre)
                    editor.putString("puntuacion_alta", "0")
                    editor.putString("ultima_puntuacion", "0")
                    editor.putString("victorias", "0")
                    editor.putString("derrotas", "0")
                    editor.commit()

                    btn_continuar.visibility = View.GONE
                    titu_regisnom.visibility = View.GONE
                    regis_nombre.visibility = View.GONE
                    txt_registrado.visibility = View.GONE
                    txt_bienvenida.visibility = View.GONE

                    anim_bienvenida.setAnimation(R.raw.bienvenido)
                    anim_bienvenida.playAnimation()

                    Handler().postDelayed({
                        pantallaprincipal(context)
                    }, 2500)

                }
            }else{
                val toast = Toast.makeText(applicationContext, "Ingresa un nombre", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
    }

    fun pantallaprincipal(vista: Context){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}