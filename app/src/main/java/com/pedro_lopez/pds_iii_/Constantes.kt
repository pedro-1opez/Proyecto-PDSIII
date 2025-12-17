package com.pedro_lopez.pds_iii_

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

object Constantes {

    fun obtenerTiempoD() : Long {
        return System.currentTimeMillis()
    }

    fun formatoFecha(tiempo : Long) : String{
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = tiempo

        return DateFormat.format("dd/MM/yyyy", calendar).toString()
    }

}