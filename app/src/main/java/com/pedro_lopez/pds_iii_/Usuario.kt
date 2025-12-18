package com.pedro_lopez.pds_iii_

class Usuario {

    var uid : String = ""
    var email : String = ""
    var nombres : String = ""
    var imagen : String = ""

    constructor()

    constructor(uid: String, email: String, nombres: String, imagen: String) {
        this.uid = uid
        this.email = email
        this.nombres = nombres
        this.imagen = imagen
    }

    override fun toString(): String {
        return "Usuario(uid='$uid', email='$email', nombres='$nombres', imagen='$imagen')"
    }

}