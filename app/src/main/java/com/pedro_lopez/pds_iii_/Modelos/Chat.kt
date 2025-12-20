package com.pedro_lopez.pds_iii_.Modelos

class Chat {

    var idMensaje : String = ""
    var tipoMensaje : String = ""
    var mensaje : String = ""
    var emisorUid : String = ""
    var receptorUid : String = ""
    var tiempo : Long = 0

    constructor()

    constructor(idMensaje: String, tipoMensaje: String, mensaje: String, emisorUid: String, receptorUid: String, tiempo: Long) {
        this.idMensaje = idMensaje
        this.tipoMensaje = tipoMensaje
        this.mensaje = mensaje
        this.emisorUid = emisorUid
        this.receptorUid = receptorUid
        this.tiempo = tiempo
    }

    override fun toString(): String {
        return "Chat(idMensaje='$idMensaje', tipoMensaje='$tipoMensaje', mensaje='$mensaje', emisorUid='$emisorUid', receptorUid='$receptorUid', tiempo=$tiempo)"
    }

}