package com.example.proyectofinal_adivinaquien

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.*

class jugadorvsjugador : AppCompatActivity() {
    //Declaracion de variables

    private lateinit var db : FirebaseDatabase
    private lateinit var dbreference : DatabaseReference
    private lateinit var anim_ganar : LottieAnimationView
    private lateinit var anim_pato : LottieAnimationView

    private lateinit var img_carta_maestra : ImageView
    private lateinit var img_carta_secreta : ImageView

    private lateinit var recycler_mensajes : TextView

    private lateinit var cartaMaestra : String
    private lateinit var contenedor_respuesta : LinearLayout
    private lateinit var contenedor_pistas : LinearLayout
    private lateinit var contenedor_puntuacion : LinearLayout
    private lateinit var contenedor_encabezado : LinearLayout
    private lateinit var contenedor_total: ScrollView
    private lateinit var contenedor_victoria : LinearLayout
    private lateinit var contenedor_principal : LinearLayout
    private lateinit var contenedor_entrada : LinearLayout

    private lateinit var btn_Respuesta : Button
    private lateinit var btn_confirmar : Button
    private lateinit var btn_entrar : Button
    private lateinit var btn_crear: Button
    private lateinit var txt_Respuesta : EditText
    private lateinit var txt_mensajes : EditText
    private lateinit var txt_puntuacion : TextView
    private lateinit var btn_comenzar : Button
    private lateinit var btn_enviar : Button
    private lateinit var txt_nombrejugador : TextView
    private lateinit var txt_puntuacion_al : TextView
    private lateinit var txt_ultima_punt : TextView
    private lateinit var tit_juego : TextView
    private lateinit var txt_puntuacion_final : TextView
    private lateinit var txt_nombre_final: TextView
    private lateinit var txt_codigo : EditText
    private lateinit var id_sala : TextView

    private lateinit var carta1 : ImageView
    private lateinit var carta2 : ImageView
    private lateinit var carta3 : ImageView
    private lateinit var carta4 : ImageView
    private lateinit var carta5 : ImageView
    private lateinit var carta6 : ImageView
    private lateinit var carta7 : ImageView
    private lateinit var carta8 : ImageView
    private lateinit var carta9 : ImageView
    private lateinit var carta10 : ImageView
    private lateinit var carta11 : ImageView
    private lateinit var carta12 : ImageView
    private lateinit var carta13 : ImageView
    private lateinit var carta14 : ImageView
    private lateinit var carta15 : ImageView
    private lateinit var carta16 : ImageView
    private lateinit var carta17 : ImageView
    private lateinit var carta18 : ImageView
    private lateinit var carta19 : ImageView
    private lateinit var carta20 : ImageView
    private lateinit var carta21 : ImageView
    private lateinit var carta22 : ImageView
    private lateinit var carta23 : ImageView
    private lateinit var carta24 : ImageView

    private lateinit var preferencias : SharedPreferences


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugadorvsjugador)

        recycler_mensajes = findViewById(R.id.recycler_Mensajes)

        btn_Respuesta = findViewById(R.id.btn_respuesta)
        btn_confirmar = findViewById(R.id.btn_confirmar)
        btn_comenzar = findViewById(R.id.btn_comenzar)
        btn_enviar = findViewById(R.id.btn_enviar)
        btn_crear = findViewById(R.id.btn_crear)
        btn_entrar = findViewById(R.id.btn_entrar)

        anim_ganar = findViewById(R.id.anim_win)
        anim_pato = findViewById(R.id.anim_bien)

        img_carta_maestra = findViewById(R.id.img_carta_maestra)
        img_carta_secreta = findViewById(R.id.img_carta_secreta)

        contenedor_respuesta = findViewById(R.id.Contenedor_respuesta)
        contenedor_pistas = findViewById(R.id.contenedor_Pistas)
        contenedor_puntuacion = findViewById(R.id.contenedor_Puntuacion)
        contenedor_total = findViewById(R.id.contener_total)
        contenedor_encabezado = findViewById(R.id.contenedor_encabezado)
        contenedor_victoria = findViewById(R.id.contenedor_Victoria)
        contenedor_principal = findViewById(R.id.Contenedor_juego)
        contenedor_entrada = findViewById(R.id.contenedor_entrada)

        txt_Respuesta = findViewById(R.id.txt_respuesta)
        txt_puntuacion = findViewById(R.id.txt_puntuacion)
        txt_nombrejugador = findViewById(R.id.txt_nombre_jugador)
        txt_puntuacion_al = findViewById(R.id.txt_puntuacion_alta)
        txt_ultima_punt = findViewById(R.id.txt_ultima_puntuacion)
        tit_juego = findViewById(R.id.tit_juego)
        txt_puntuacion_final = findViewById(R.id.txt_puntuacion_final)
        txt_nombre_final = findViewById(R.id.txt_nombre_final)
        txt_mensajes = findViewById(R.id.txt_mensaje)
        txt_codigo = findViewById(R.id.txt_codigo_sala)
        id_sala = findViewById(R.id.id_sala)

        preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferencias.edit()
        val nombre_user = preferencias.getString("nombre","")
        val Derrotas = preferencias.getString("derrotas","")
        val Victorias = preferencias.getString("victorias","")

        db = FirebaseDatabase.getInstance()


        var matrizCartas = Array(24){
            Array<String?>(3){null}
        }

        matrizCartas = rellenarmatriz()

        carta1 = findViewById(R.id.Carta1)
        carta2 = findViewById(R.id.Carta2)
        carta3 = findViewById(R.id.Carta3)
        carta4 = findViewById(R.id.Carta4)
        carta5 = findViewById(R.id.Carta5)
        carta6 = findViewById(R.id.Carta6)
        carta7 = findViewById(R.id.Carta7)
        carta8 = findViewById(R.id.Carta8)
        carta9 = findViewById(R.id.Carta9)
        carta10 = findViewById(R.id.Carta10)
        carta11 = findViewById(R.id.Carta11)
        carta12 = findViewById(R.id.Carta12)
        carta13 = findViewById(R.id.Carta13)
        carta14 = findViewById(R.id.Carta14)
        carta15 = findViewById(R.id.Carta15)
        carta16 = findViewById(R.id.Carta16)
        carta17 = findViewById(R.id.Carta17)
        carta18 = findViewById(R.id.Carta18)
        carta19 = findViewById(R.id.Carta19)
        carta20 = findViewById(R.id.Carta20)
        carta21 = findViewById(R.id.Carta21)
        carta22 = findViewById(R.id.Carta22)
        carta23 = findViewById(R.id.Carta23)
        carta24 = findViewById(R.id.Carta24)

        //establecemos los valores para la interfaz
        txt_nombrejugador.setText(nombre_user)


        //Coloca todas las cartas en un orden aleatorio
        colorcaralzazar(matrizCartas)

        btn_entrar.setOnClickListener {
            if(txt_codigo.text.toString() != ""){
                dbreference = db.getReference(txt_codigo.text.toString())//sala de chat
                contenedor_entrada.visibility = View.GONE
                contenedor_principal.visibility = View.VISIBLE
                id_sala.setText("ID Sala: " + txt_codigo.text.toString())
                mensajes()
            }
            else{
                val toast = Toast.makeText(applicationContext, "Ingresa un codigo", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        btn_crear.setOnClickListener {
            val random = Random()
            val num = random.nextInt(1000000..9999999)
            id_sala.setText("ID Sala: " + num.toString())
            dbreference = db.getReference(num.toString())//sala de chat
            contenedor_entrada.visibility = View.GONE
            contenedor_principal.visibility = View.VISIBLE
            mensajes()

        }

        btn_comenzar.setOnClickListener {
            btn_comenzar.visibility = View.GONE
            //Selecciona una carta al azar para que adivine el jugador
            SeleccionAlazar(matrizCartas)
            btn_Respuesta.visibility = View.VISIBLE
            img_carta_secreta.setImageResource(resources.getIdentifier(matrizCartas[cartaMaestra.toInt()][2],"drawable", packageName))
            img_carta_secreta.visibility = View.VISIBLE
        }

        var band = false
        btn_Respuesta.setOnClickListener {
            if(!band){
                contenedor_respuesta.visibility = View.VISIBLE
                band = true
            }
            else{
                contenedor_respuesta.visibility = View.GONE
                band = false
            }
        }

        btn_confirmar.setOnClickListener {
            if(txt_Respuesta.text.toString() == matrizCartas[cartaMaestra.toInt()][1]){
                val toast = Toast.makeText(applicationContext, "Corercto Acertaste al personaje Victoria", Toast.LENGTH_SHORT)
                toast.show()

                /*contenedor_encabezado.visibility = View.GONE
                tit_juego.visibility = View.GONE
                contenedor_puntuacion.visibility = View.GONE
                contenedor_total.visibility = View.GONE

                anim_ganar.setAnimation(R.raw.confettitrophy)
                anim_ganar.visibility = View.VISIBLE
                //anim_ganar.playAnimation()

                img_carta_maestra.setImageResource(resources.getIdentifier(matrizCartas[cartaMaestra.toInt()][2],"drawable", packageName))
                txt_nombre_final.setText(matrizCartas[cartaMaestra.toInt()][1])

                Handler().postDelayed({
                    pantallaVictoria(applicationContext)
                }, 2000)*/

            }
            else{
                val toast = Toast.makeText(applicationContext, "Incrorrecto Perdiste", Toast.LENGTH_SHORT)
                toast.show()
            }
        }


    }

    fun mensajes (){
        btn_enviar.setOnClickListener {
            dbreference.push().setValue(preferencias.getString("nombre","")+ ": "+ txt_mensajes.text.toString())
            txt_mensajes.setText("")
        }

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                var Mensajes = recycler_mensajes.text.toString()
                recycler_mensajes.setText(Mensajes +"\n" + dataSnapshot.getValue().toString())

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {


            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        dbreference.addChildEventListener(childEventListener)
    }

    private fun pantallaVictoria(applicationContext: Context) {
        anim_pato.setAnimation(R.raw.duck)
        anim_pato.playAnimation()
        anim_ganar.visibility = View.GONE
        contenedor_victoria.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun SeleccionAlazar(matrizCartas: Array<Array<String?>>) {
        val random = Random()
        val num = random.nextInt(0..23)

        cartaMaestra = matrizCartas[num][0].toString()

        Log.d("Carta Maestra",matrizCartas[num][1].toString())
    }

    private fun colorcaralzazar(matrizCartas: Array<Array<String?>>) {
        var matrizCartasT = Array(24){
            Array<String?>(3){null}
        }
        val random = Random()
        var band:Boolean = false
        //var aleatorios = intArrayOf(25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25)
        var aleatorios = ArrayList<Int>()
        var num : Int = 0

        matrizCartasT = matrizCartas

        for(i in 0..23){
            aleatorios.add(i)
        }

        for (i in 0..23){
            var temporal = aleatorios.get(i)
            var index = random.nextInt(0..23)

            aleatorios.set(i,aleatorios.get(index))
            aleatorios.set(index,temporal)
        }


        carta1.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(0)][2],"drawable", packageName))
        carta2.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(1)][2],"drawable", packageName))
        carta3.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(2)][2],"drawable", packageName))
        carta4.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(3)][2],"drawable", packageName))
        carta5.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(4)][2],"drawable", packageName))
        carta6.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(5)][2],"drawable", packageName))
        carta7.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(6)][2],"drawable", packageName))
        carta8.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(7)][2],"drawable", packageName))
        carta9.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(8)][2],"drawable", packageName))
        carta10.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(9)][2],"drawable", packageName))
        carta11.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(10)][2],"drawable", packageName))
        carta12.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(11)][2],"drawable", packageName))
        carta13.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(12)][2],"drawable", packageName))
        carta14.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(13)][2],"drawable", packageName))
        carta15.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(14)][2],"drawable", packageName))
        carta16.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(15)][2],"drawable", packageName))
        carta17.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(16)][2],"drawable", packageName))
        carta18.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(17)][2],"drawable", packageName))
        carta19.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(18)][2],"drawable", packageName))
        carta20.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(19)][2],"drawable", packageName))
        carta21.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(20)][2],"drawable", packageName))
        carta22.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(21)][2],"drawable", packageName))
        carta23.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(22)][2],"drawable", packageName))
        carta24.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(23)][2],"drawable", packageName))



        var banderas = intArrayOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)


        carta1.setOnClickListener {
            if(banderas.get(0) == 0){
                carta1.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(0,1)
            }
            else{
                carta1.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(0)][2],"drawable", packageName))
                banderas.set(0,0)
            }
        }
        carta2.setOnClickListener {
            if(banderas.get(1) == 0){
                carta2.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(1,1)
            }
            else{
                carta2.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(1)][2],"drawable", packageName))
                banderas.set(1,0)
            }
        }
        carta3.setOnClickListener {
            if(banderas.get(2) == 0){
                carta3.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(2,1)
            }
            else{
                carta3.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(2)][2],"drawable", packageName))
                banderas.set(2,0)
            }
        }
        carta4.setOnClickListener {
            if(banderas.get(3) == 0){
                carta4.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(3,1)
            }
            else{
                carta4.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(3)][2],"drawable", packageName))
                banderas.set(3,0)
            }
        }
        carta5.setOnClickListener {
            if(banderas.get(4) == 0){
                carta5.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(4,1)
            }
            else{
                carta5.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(4)][2],"drawable", packageName))
                banderas.set(4,0)
            }
        }
        carta6.setOnClickListener {
            if(banderas.get(5) == 0){
                carta6.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(5,1)
            }
            else{
                carta6.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(5)][2],"drawable", packageName))
                banderas.set(5,0)
            }
        }
        carta7.setOnClickListener {
            if(banderas.get(6) == 0){
                carta7.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(6,1)
            }
            else{
                carta7.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(6)][2],"drawable", packageName))
                banderas.set(6,0)
            }
        }
        carta8.setOnClickListener {
            if(banderas.get(7) == 0){
                carta8.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(7,1)
            }
            else{
                carta8.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(7)][2],"drawable", packageName))
                banderas.set(7,0)
            }
        }
        carta9.setOnClickListener {
            if(banderas.get(8) == 0){
                carta9.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(8,1)
            }
            else{
                carta9.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(8)][2],"drawable", packageName))
                banderas.set(8,0)
            }
        }
        carta10.setOnClickListener {
            if(banderas.get(9) == 0){
                carta10.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(9,1)
            }
            else{
                carta10.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(9)][2],"drawable", packageName))
                banderas.set(9,0)
            }
        }
        carta11.setOnClickListener {
            if(banderas.get(10) == 0){
                carta11.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(10,1)
            }
            else{
                carta11.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(10)][2],"drawable", packageName))
                banderas.set(10,0)
            }
        }
        carta12.setOnClickListener {
            if(banderas.get(11) == 0){
                carta12.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(11,1)
            }
            else{
                carta12.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(11)][2],"drawable", packageName))
                banderas.set(11,0)
            }
        }
        carta13.setOnClickListener {
            if(banderas.get(12) == 0){
                carta13.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(12,1)
            }
            else{
                carta13.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(12)][2],"drawable", packageName))
                banderas.set(12,0)
            }
        }
        carta14.setOnClickListener {
            if(banderas.get(13) == 0){
                carta14.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(13,1)
            }
            else{
                carta14.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(13)][2],"drawable", packageName))
                banderas.set(13,0)
            }
        }
        carta15.setOnClickListener {
            if(banderas.get(14) == 0){
                carta15.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(14,1)
            }
            else{
                carta15.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(14)][2],"drawable", packageName))
                banderas.set(14,0)
            }
        }
        carta16.setOnClickListener {
            if(banderas.get(15) == 0){
                carta16.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(15,1)
            }
            else{
                carta16.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(15)][2],"drawable", packageName))
                banderas.set(15,0)
            }
        }
        carta17.setOnClickListener {
            if(banderas.get(16) == 0){
                carta17.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(16,1)
            }
            else{
                carta17.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(16)][2],"drawable", packageName))
                banderas.set(16,0)
            }
        }
        carta18.setOnClickListener {
            if(banderas.get(17) == 0){
                carta18.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(17,1)
            }
            else{
                carta18.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(17)][2],"drawable", packageName))
                banderas.set(17,0)
            }
        }
        carta19.setOnClickListener {
            if(banderas.get(18) == 0){
                carta19.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(18,1)
            }
            else{
                carta19.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(18)][2],"drawable", packageName))
                banderas.set(18,0)
            }
        }
        carta20.setOnClickListener {
            if(banderas.get(19) == 0){
                carta20.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(19,1)
            }
            else{
                carta20.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(19)][2],"drawable", packageName))
                banderas.set(19,0)
            }
        }
        carta21.setOnClickListener {
            if(banderas.get(20) == 0){
                carta21.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(20,1)
            }
            else{
                carta21.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(20)][2],"drawable", packageName))
                banderas.set(20,0)
            }
        }
        carta22.setOnClickListener {
            if(banderas.get(21) == 0){
                carta22.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(21,1)
            }
            else{
                carta22.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(21)][2],"drawable", packageName))
                banderas.set(21,0)
            }
        }
        carta23.setOnClickListener {
            if(banderas.get(22) == 0){
                carta23.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(22,1)
            }
            else{
                carta23.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(22)][2],"drawable", packageName))
                banderas.set(22,0)
            }
        }
        carta24.setOnClickListener {
            if(banderas.get(23) == 0){
                carta24.setImageResource(R.drawable.carta_parte_atras);
                banderas.set(23,1)
            }
            else{
                carta24.setImageResource(resources.getIdentifier(matrizCartas[aleatorios.get(23)][2],"drawable", packageName))
                banderas.set(23,0)
            }
        }



    }

    private fun rellenarmatriz(): Array<Array<String?>> {
        var matrizCartas = Array(24){
            Array<String?>(3){null}
        }

        for (i in 0..23){
            matrizCartas[i][0]= "$i"
        }
        val pista1persona:String =" Pista 1: \n El personaje secreto tiene aspecto humano"
        val pista1animal:String =" Pista 1: \n El personaje secreto tiene aspecto animal"

        //Se rellena los nombre de las cartas
        matrizCartas[0][1] = "Ariel"
        matrizCartas[1][1] = "Stitch"
        matrizCartas[2][1] = "Elsa"
        matrizCartas[3][1] = "Sebastian"
        matrizCartas[4][1] = "Rapunzel"
        matrizCartas[5][1] = "Mickey Mouse"
        matrizCartas[6][1] = "Ana"
        matrizCartas[7][1] = "Winnie Pooh"
        matrizCartas[8][1] = "Mr Increible"
        matrizCartas[9][1] = "Bambi"
        matrizCartas[10][1] = "Edna Moda"
        matrizCartas[11][1] = "Dumbo"
        matrizCartas[12][1] = "Mulan"
        matrizCartas[13][1] = "Goofy"
        matrizCartas[14][1] = "Jazmin"
        matrizCartas[15][1] = "Minnie"
        matrizCartas[16][1] = "Vanellope"
        matrizCartas[17][1] = "Timon"
        matrizCartas[18][1] = "Hercules"
        matrizCartas[19][1] = "Donald"
        matrizCartas[20][1] = "Lilo"
        matrizCartas[21][1] = "Remi"
        matrizCartas[22][1] = "Campanita"
        matrizCartas[23][1] = "Dory"

        //Se rellena con la ruta
        matrizCartas[0][2] = "carta_ariel"
        matrizCartas[1][2] = "carta_stitch"
        matrizCartas[2][2] = "carta_elsa"
        matrizCartas[3][2] = "carta_sebastian"
        matrizCartas[4][2] = "carta_rapunzel"
        matrizCartas[5][2] = "carta_mickey"
        matrizCartas[6][2] = "carta_ana"
        matrizCartas[7][2] = "carta_winnie"
        matrizCartas[8][2] = "carta_mrincreible"
        matrizCartas[9][2] = "carta_bambi"
        matrizCartas[10][2] = "carta_edna"
        matrizCartas[11][2] = "carta_dumbo"
        matrizCartas[12][2] = "carta_mulan"
        matrizCartas[13][2] = "carta_goofy"
        matrizCartas[14][2] = "carta_jazmin"
        matrizCartas[15][2] = "carta_minnie"
        matrizCartas[16][2] = "carta_vanellope"
        matrizCartas[17][2] = "carta_timon"
        matrizCartas[18][2] = "carta_hercules"
        matrizCartas[19][2] = "carta_donald"
        matrizCartas[20][2] = "carta_lilo"
        matrizCartas[21][2] = "carta_remi"
        matrizCartas[22][2] = "carta_campanita"
        matrizCartas[23][2] = "carta_dory"

        return matrizCartas
    }

    //Se utiliza para quitar la barra de estado
    override fun onResume() {
        super.onResume()
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
    }
    fun Regresar(vista: View){
        //Termina la conexion


        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    fun Regresarvictoria(vista: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
    fun reintentar(vista: View){
        val intent = Intent(this, jugadorvsIA::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    fun Random.nextInt(range: IntRange): Int {
        return range.start + nextInt(range.last - range.start)
    }

    companion object{
        const val PROGRESS_INCREMENT = 1
    }
}