package com.pedro_lopez.pds_iii_

import android.text.format.DateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Locale

object Constantes {

    const val MENSAJE_TIPO_TEXTO = "TEXTO"
    const val MENSAJE_TIPO_IMAGEN = "IMAGEN"

    fun obtenerTiempoD() : Long {
        return System.currentTimeMillis()
    }

    fun formatoFecha(tiempo : Long) : String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = tiempo

        return DateFormat.format("dd/MM/yyyy", calendar).toString()
    }

    fun obtenerFechaHora(tiempo : Long) : String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = tiempo

        return DateFormat.format("dd/MM/yyyy hh:mm:a", calendar).toString()
    }

    fun rutaChat(receptorUid : String, emisorUid : String) : String {
        val arrayUid = arrayOf(receptorUid, emisorUid)
        Arrays.sort(arrayUid)

        // Uid del usuario con quien entablamos la conversación [Receptor] = gl6HruhO8me2aMM9OYDBjIkEvvx1
        // Nuestro Uid [Emisor] = Ot6wuAcPLXezY6vuj8wzN8AcP7C3
        // La ruta seria = Ot6wuAcPLXezY6vuj8wzN8AcP7C3_gl6HruhO8me2aMM9OYDBjIkEvvx1
        return "${arrayUid[0]}_${arrayUid[1]}"
    }
}