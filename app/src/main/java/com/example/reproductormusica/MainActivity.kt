// Proyecto: Reproductor de musica
// De: Rodrigo Alonso Ruelas Lope
// Fecha de Creacion: 28/09/2024 - 12:50 am
// Ultima modificacion: 28/09/2024 -  am

package com.example.reproductormusica

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        mp.start()
    }
}