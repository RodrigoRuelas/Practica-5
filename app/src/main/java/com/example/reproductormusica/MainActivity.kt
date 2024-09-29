// Proyecto: Reproductor de musica
// De: Rodrigo Alonso Ruelas Lope
// Fecha de Creacion: 28/09/2024 - 12:50 am
// Ultima modificacion: 28/09/2024 -  am

package com.example.reproductormusica

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    // Creando fichero de audio
    val fd by lazy {
        assets.openFd("Fragil - Avenida Larco.mp3")
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        controllers[ci.play].setOnClickListener(this::playClick)
        controllers[ci.stop].setOnClickListener(this::stopClick)
    }

    // Funcion boton Play
    fun playClick(view: View) {
        if (!mp.isPlaying) {
            mp.start()
            controllers[ci.play].setIconResource(R.drawable.baseline_pause_48)
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
        }
        mp.seekTo(0)
    }
}