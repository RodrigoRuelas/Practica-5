// Proyecto: Reproductor de musica
// De: Rodrigo Alonso Ruelas Lope
// Fecha de Creacion: 28/09/2024 - 12:50 am
// Ultima modificacion: 01/10/2024 - 12:49 am

package com.example.reproductormusica

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    // Creando fichero de audio
    val fd by lazy {
        assets.openFd(cancionActual)
    }

    // Objero MediaPlayer
    val mp by lazy {
        val m = MediaPlayer()
        m.setDataSource(
            fd.fileDescriptor,
            fd.startOffset,
            fd.length
        )
        fd.close()
        m.prepare()
        m
    }

    // Lista de botones
    val controllers by lazy {
        listOf(R.id.prev, R.id.stop, R.id.play, R.id.next).map {
            findViewById<MaterialButton>(it)
        }
    }

    // Indice de botones
    object ci{
        val prev = 0
        val stop = 1
        val play = 2
        val next = 3
    }

    // Nombre de la cancion
    val nombreCancion by lazy {
        findViewById<TextView>(R.id.nombreCancion)
    }

    // Lista de canciones
    val canciones by lazy {
        val nombreFicheros = assets.list("")?.toList() ?: listOf()
        nombreFicheros.filter { it.contains(".mp3") }
    }

    // Control de recorrido de canciones
    var cancionActualIndex = 0
        set(value) {
            var v = if (value == -1) {
                canciones.size-1
            } else {
                value % canciones.size
            }
            field = v
            cancionActual = canciones[v]
        }

    lateinit var cancionActual:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        controllers[ci.play].setOnClickListener(this::playClick)
        controllers[ci.stop].setOnClickListener(this::stopClick)
        controllers[ci.prev].setOnClickListener(this::prevClick)
        controllers[ci.next].setOnClickListener(this::nextClick)
        cancionActual = canciones[cancionActualIndex]
        nombreCancion.text = cancionActual
    }

    override fun onStart() {
        super.onStart()
        findViewById<RecyclerView>(R.id.rv).apply {
            adapter = AdaptadorCanciones(canciones, this@MainActivity)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    // Funcion boton Play
    fun playClick(view: View) {
        if (!mp.isPlaying) {
            mp.start()
            controllers[ci.play].setIconResource(R.drawable.baseline_pause_48)
            nombreCancion.visibility = View.VISIBLE
        } else {
            mp.pause()
            controllers[ci.play].setIconResource(R.drawable.baseline_play_arrow_48)
        }
    }

    // Funcion boton Stop
    fun stopClick(view: View) {
        if (mp.isPlaying) {
            mp.pause()
            controllers[ci.play].setIconResource(R.drawable.baseline_play_arrow_48)
            nombreCancion.visibility = View.INVISIBLE
        }
        mp.seekTo(0)
    }

    // Funcion boton Next
    fun nextClick(view: View) {
        cancionActualIndex++
        refreshSong()
    }

    // Funcion boton Prev
    fun prevClick(view: View) {
        cancionActualIndex--
        refreshSong()
    }

    // Funcion reiniciar cancion
    fun refreshSong() {
        mp.reset()
        val fd = assets.openFd(cancionActual)
        mp.setDataSource(
            fd.fileDescriptor,
            fd.startOffset,
            fd.length
        )
        mp.prepare()
        playClick(controllers[ci.play])
        nombreCancion.text = cancionActual
    }
}