package com.example.reproductormusica

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reproductormusica.databinding.RowSongBinding

class AdaptadorCanciones (val elementos:List<String>, val con:MainActivity) :
    RecyclerView.Adapter<AdaptadorCanciones.ViewHolder>() {

        var selected = -1

        class ViewHolder(val bind: RowSongBinding)
            :RecyclerView.ViewHolder(bind.root)

        override fun onCreateViewHolder(paret: ViewGroup, viewType: Int): ViewHolder {
            val v = RowSongBinding.inflate(LayoutInflater.from(paret.context), paret, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val elem = elementos[position]
            with(holder.bind) {
                rowNombreCancion.text = elem
                if(position == selected) {
                    rowCancion.setBackgroundColor(Color.LTGRAY)
                } else {
                    rowCancion.setBackgroundColor(Color.WHITE)
                }
                rowCancion.setOnClickListener{
                    con.cancionActualIndex = position
                    con.refreshSong()
                    selected = position
                    notifyDataSetChanged()
                }
            }
        }

        override fun getItemCount(): Int {
            return elementos.size
        }
    }
