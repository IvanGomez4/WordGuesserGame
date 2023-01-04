package net.azarquiel.wordguessergame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.wordguessergame.R
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var aciertos : Int = 0
    private val palabras = arrayOf("ABACO", "ABEJA", "ABETO", "ABONO", "ABRIL", "ACARO", "ACEBO", "ACERA", "ACERO", "ACIDO", "ACTOR", "ADIOS", "ADOBE", "ADOBO", "AFORO", "AGUJA", "AJETE", "AJUAR", "ALAMO", "ALBUM", "ALDEA", "ALERO", "ALETA", "ALIAS", "ALTAR", "AMEBA", "AMIGA", "AMIGO", "ANCLA", "ANDEN", "ANEXO", "APERO", "APODO", "APOYO", "APURO", "ARABE", "ARADO", "ARBOL", "ARCEN",
        "ARCON", "ARDOR", "ARENA", "ARGON", "ARIES", "ARNES", "AROMA", "ARPON", "ARROZ", "ASADO", "ASCUA", "ASILO", "ASTRO", "ATAJO", "ATAUD", "ATICO", "ATLAS", "ATOMO", "ATRIL", "AUTOR", "AVENA", "AVION", "AVISO", "AXILA", "AYUDA", "AYUNO", "AZADA", "AZOTE", "BACHE", "BAILE", "BALDA", "BALON", "BALSA", "BAMBU", "BANCO", "BANDA", "BARBA", "BARCA", "BARCO", "BARON", "BARRA", "BARRO",
        "BETUN", "BICHO", "BILIS", "BLUSA", "BOINA", "BOLLO", "BOLSA", "BOLSO", "BOMBA", "BOMBO", "BORDE", "BORLA", "BORNE", "BOTIN", "BOTON", "BOXEO", "BOZAL", "BRAGA", "BRASA", "BRAZO", "BREZO", "BRIDA", "BRISA", "BROCA", "BROMA", "BROTE", "BRUJA", "BRUJO", "BRUMA", "BUCLE", "BUFON", "BULBO", "BULTO", "BUQUE", "BURLA", "BURRA", "BURRO", "BUSTO", "BUZON", "CABLE", "CABRA", "CACAO",
        "CAJON", "CALDO", "CALIZ", "CALMA", "CALOR", "CALVA", "CALZO", "CAMPO", "CANAL", "CANOA", "CANON", "CAOBA", "CAPEA", "CARDO", "CARGA", "CARGO", "CARPA", "CARRO", "CARTA", "CASCO", "CASPA", "CATRE", "CAUCE", "CAZON", "CEBRA", "CEDRO", "CELDA", "CENSO", "CERDO", "CERRO", "CHAPA", "CHICA", "CHICO", "CHIVO", "CHOPO", "CHOTO", "CHOZA", "CICLO", "CIEGO", "CIELO", "CIENO", "CIFRA",
        "CINCO", "CINTA", "CIRCO", "CISNE", "CIVIL", "CLAVO", "CLERO", "CLIMA", "CLORO", "COBRA", "COBRE", "COCHE", "COFRE", "COITO", "COJIN", "COLOR", "CONDE", "COPLA", "CORAL", "CORSE", "CORZO", "COXIS", "CREDO", "CREMA", "CRIBA", "CRUCE", "CUERO", "CUEVA", "CULTO", "CUOTA", "CUPON", "CURVA", "CUTIS", "DALIA", "DANZA", "DARDO", "DATIL", "DEDAL", "DELTA", "DESEO", "DEUDA", "DIANA",
        "DIETA", "DIODO", "DIOSA", "DIQUE", "DISCO", "DIVAN", "DOLAR", "DORSO", "DOSIS", "DROGA", "DUCHA", "DUELO", "DUETO", "DULCE", "DUQUE", "EBANO", "EDEMA", "ENANO", "ENERO", "EPOCA", "ERIZO", "ERROR", "ESPIA", "ESQUI", "ETAPA", "EXITO", "EXODO", "FAENA", "FAJIN", "FALDA", "FALLA", "FALTA", "FANGO", "FAROL", "FAUNA", "FAVOR", "FECHA", "FELPA", "FEMUR", "FERIA", "FEUDO", "FIBRA",
        "FICHA", "FIDEO", "FILON", "FINAL", "FINCA", "FIRMA", "FIRME", "FLAMA", "FLECO", "FLEMA", "FLORA", "FLUJO", "FLUOR", "FOBIA", "FOGON", "FOLIO", "FONDA", "FONDO", "FORJA", "FORRO", "FOSIL", "FRASE", "FRENO", "FRESA", "FRUTA", "FUEGO", "FUNDA", "FUSIL", "GAITA", "GALAN", "GALGO", "GALLO", "GAMBA", "GANSO", "GARRA", "GARZA", "GENTE", "GESTO", "GLOBO", "GOLFO", "GOLPE", "GORRA",
        "GORRO", "GRADA", "GRANO", "GRASA", "GRECA", "GRIFO", "GRIPE", "GRUMO", "GRUPO", "GRUTA", "GUATA", "GUION", "GUISO", "GUSTO", "HACHA", "HAREN", "HEBRA", "HELIO", "HEROE", "HIELO", "HIENA", "HIMEN", "HOGAR", "HONGO", "HONOR", "HONRA", "HORCA", "HORNO", "HOTEL", "HUCHA", "HUESO", "HUEVO", "HUMOR", "HURON", "HURTO", "ICONO", "IDOLO", "ISLAM", "JABON", "JALEA", "JALEO", "JAMON",
        "JARRA", "JARRO", "JAULA", "JEQUE", "JERGA", "JUEGO", "JUNCO", "JUNTA", "JUREL", "LABIO", "LABOR", "LACON", "LAGAR", "LAICO", "LANCE", "LANZA", "LAPIZ", "LARVA", "LATEX", "LATIN", "LATON", "LECHE", "LECHO", "LEGUA", "LENTE", "LEONA", "LEPRA", "LETRA", "LIBRO", "LICOR", "LIMON", "LINCE", "LINDE", "LINEA", "LIRIO", "LIRON", "LISTA", "LITRO", "LLAMA", "LLAVE", "LOCAL", "LONJA",
        "LUCHA", "LUGAR", "LUNAR", "MADRE", "MAGIA", "MAGMA", "MALLA", "MALTA", "MANGA", "MANGO", "MANIA", "MANTA", "MANTO", "MARCA", "MARCO", "MAREA", "MAREO", "MASIA", "MATIZ", "MECHA", "MELON", "MENTA", "MESON", "METAL", "METRO", "MICRA", "MIEDO", "MILLA", "MIRLO", "MIRRA", "MOCHO", "MOJON", "MOLDE", "MOLLA", "MOMIA", "MONJA", "MONJE", "MONTE", "MORRO", "MORSA", "MOSCA", "MOSTO",
        "MOTEL", "MOTOR", "MOVIL", "MUECA", "MUELA", "MUGRE", "MUJER", "MULTA", "MUNDO", "MURAL", "MUSEO", "MUSGO", "MUSLO", "NACAR", "NALGA", "NARDO", "NARIZ", "NAVIO", "NIETO", "NIEVE", "NINFA", "NIVEL", "NOBLE", "NOCHE", "NOGAL", "NORIA", "NORMA", "NORTE", "NOVIO", "NUBLO", "OASIS", "OCASO", "OJERA", "OLIVA", "OLIVO", "OPALO", "OPERA", "ORDEN", "OREJA", "ORUJO", "OSTRA", "OVALO",
        "OVEJA", "OVULO", "OXIDO", "OZONO", "PACTO", "PADRE", "PAJAR", "PALCO", "PALIO", "PALMA", "PALMO", "PANAL", "PANDA", "PANEL", "PANZA", "PAPEL", "PARED", "PARRA", "PARTO", "PASEO", "PASTA", "PATIN", "PATIO", "PAUSA", "PAUTA", "PAVOR", "PEAJE", "PEANA", "PECHO", "PEDAL", "PEINE", "PELEA", "PENAL", "PERAL", "PERCA", "PERLA", "PERNO", "PERRO", "PESCA", "PESTE", "PEZON", "PIANO",
        "PIARA", "PICOR", "PIEZA", "PILAR", "PILON", "PINAR", "PINZA", "PIOJO", "PISTA", "PIZCA", "PLACA", "PLAGA", "PLANO", "PLATA", "PLATO", "PLAYA", "PLAZA", "PLAZO", "PLENO", "PLOMO", "PLUMA", "POBRE", "PODER", "POEMA", "POETA", "POLEA", "POLEN", "POLLO", "POLVO", "POMPA", "POSTE", "POTRO", "PRADO", "PRESA", "PRESO", "PRIMO", "PRIOR", "PROSA", "PUBIS", "PUDOR", "PUGNA", "PULGA",
        "PULPA", "PULSO", "PUNTA", "PURGA", "QUEJA", "QUESO", "RABIA", "RACHA", "RADAR", "RADIO", "RAMAL", "RAMPA", "RANGO", "RAPTO", "RASGO", "RATON", "RAYON", "RAZON", "RECTA", "REGLA", "REHEN", "REINA", "REINO", "REJON", "RELOJ", "RENTA", "RETEN", "REUMA", "RIADA", "RIEGO", "RIFLE", "RIGOR", "RITMO", "RIVAL", "ROBLE", "ROCIO", "ROLLO", "ROMBO", "RONDA", "ROSAL", "ROSCA", "ROSCO",
        "RUEDA", "RUIDO", "RUINA", "RUMOR", "SABIO", "SABLE", "SABOR", "SAETA", "SALDO", "SALMO", "SALON", "SALSA", "SALUD", "SALVE", "SANTO", "SARNA", "SARRO", "SAUCE", "SAVIA", "SECTA", "SEDAL", "SELLO", "SELVA", "SEMEN", "SENDA", "SEPIA", "SERIE", "SERON", "SIDRA", "SIFON", "SIGLA", "SIGLO", "SIGNO", "SILLA", "SITIO", "SOBRE", "SOCIO", "SOLAR", "SONDA", "SOPLO", "SUDOR", "SUELA",
        "SUELO", "SUERO", "SURCO", "SUSTO", "TACON", "TACTO", "TALCO", "TALLO", "TALON", "TALUD", "TAPIZ", "TAPON", "TARDE", "TARRO", "TARTA", "TASCA", "TAZON", "TECHO", "TECLA", "TEJON", "TELAR", "TELON", "TEMOR", "TENIS", "TENOR", "TERMO", "TEXTO", "TIBIA", "TIGRE", "TILDE", "TIMON", "TINTA", "TINTE", "TIZON", "TOLDO", "TONEL", "TORAX", "TORNO", "TOTAL", "TRABA", "TRACA", "TRAJE",
        "TRAMA", "TRAPO", "TRATO", "TRAZO", "TRIBU", "TRIGO", "TRIPA", "TRONA", "TRONO", "TROPA", "TROZO", "TRUCO", "TUCAN", "TUMBA", "TUMOR", "TUNEL", "TURBA", "TUTOR", "UNION", "VAGON", "VAINA", "VALLA", "VALLE", "VAPOR", "VARON", "VELLO", "VENDA", "VENTA", "VERBO", "VERJA", "VERSO", "VIAJE", "VICIO", "VIEJO", "VILLA", "VIOLA", "VIRUS", "VIUDO", "VUELO", "XENON", "YEDRA", "YEGUA",
        "YERNO", "ZANCO", "ZARPA", "ZARZA", "ZORRO", "ZUECO")

    private lateinit var iv1 : ImageView
    private lateinit var iv2 : ImageView
    private lateinit var iv3 : ImageView
    private lateinit var iv4 : ImageView
    private lateinit var iv5 : ImageView
    private lateinit var palabra : String
    private lateinit var random : Random
    private var isPrimerClick: Boolean = true
    private var borrar1: Boolean = false
    private var borrar2: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        random = Random(System.currentTimeMillis())
        iv1 = findViewById(R.id.iv1)
        iv2 = findViewById(R.id.iv2)
        iv3 = findViewById(R.id.iv3)
        iv4 = findViewById(R.id.iv4)
        iv5 = findViewById(R.id.iv5)

        val lv = findViewById<LinearLayout>(R.id.lv)
        for (i in 0 until lv.childCount) {//bucle para recorrer los 3 linear layouts
            val lh = lv.getChildAt(i) as LinearLayout //sacamos cada linear horizontal y lo casteamos como linear layout
            for (j in 0 until lh.childCount) {
                val boton = lh.getChildAt(j) as Button
                boton.setOnClickListener(this)
            }
        }
        nuevaPartida()
    }

    private fun nuevaPartida() {
        aciertos = 0
        isPrimerClick = true
        borrar1 = false
        borrar2 = false
        inventaPalabra()
        mostrarPalabra()
        iv2.setImageResource(R.drawable.guion)
        iv4.setImageResource(R.drawable.guion)
    }

    private fun mostrarPalabra() {
        val letra1 = palabra[0].lowercaseChar()
        val imagenletra1 = resources.getIdentifier("${letra1}b", "drawable", packageName)
        iv1.setImageResource(imagenletra1)

        val letra3 = palabra[2].lowercaseChar()
        val imagenletra3 = resources.getIdentifier("${letra3}b", "drawable", packageName)
        iv3.setImageResource(imagenletra3)

        val letra5 = palabra[4].lowercaseChar()
        val imagenletra5 = resources.getIdentifier("${letra5}b", "drawable", packageName)
        iv5.setImageResource(imagenletra5)
    }

    private fun inventaPalabra() {
        palabras.shuffle(random)
        palabra = palabras[0]
    }

    override fun onClick(v: View?) {
        val botonpulsado = v as Button
        val letrapulsada = v.tag as String
        val letra2 = palabra[1].lowercaseChar().toString()
        val letra4 = palabra[3].lowercaseChar().toString()
        if (isPrimerClick) {
            if (letrapulsada == letra2) {
                iv2.setImageResource(resources.getIdentifier("${letrapulsada}v", "drawable", packageName))
                aciertos++
                isPrimerClick = !isPrimerClick
                borrar1 = false
            } else {
                iv2.setImageResource(resources.getIdentifier("${letrapulsada}r", "drawable", packageName))
                borrar1 = true
                borrarLetras()
            }

        } else {
            if (letrapulsada == letra4) {
                iv4.setImageResource(resources.getIdentifier("${letrapulsada}v", "drawable", packageName))
                aciertos++
                borrar2 = false

            } else {
                iv4.setImageResource(resources.getIdentifier("${letrapulsada}r", "drawable", packageName))
                borrar2 = true
                borrarLetras()
            }

        }
        check()
    }

    private fun borrarLetras() {
        GlobalScope.launch() {
            SystemClock.sleep(1000)
            launch(Main) {
                if (borrar1 == true && borrar2 ==  false) {
                    iv2.setImageResource(R.drawable.guion)
                } else if (borrar1 == false && borrar2 == true ) {
                    iv4.setImageResource(R.drawable.guion)
                } else if (borrar1 == true && borrar2 == true) {
                    iv2.setImageResource(R.drawable.guion)
                    iv4.setImageResource(R.drawable.guion)
                }
            }
        }
    }

    private fun check() {
        if (aciertos == 2) {
            AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("You have guessed the word")
                .setPositiveButton("New Game") { dialog, which ->
                    nuevaPartida()
                }
                .setNegativeButton("Exit") { dialog, which ->
                    finish()
                }
                .show()
        }
    }
}