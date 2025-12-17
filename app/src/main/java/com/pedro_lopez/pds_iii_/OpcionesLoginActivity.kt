package com.pedro_lopez.pds_iii_

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.pedro_lopez.pds_iii_.databinding.ActivityOpcionesLoginBinding
import kotlinx.coroutines.launch

class OpcionesLoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOpcionesLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var credentialManager : CredentialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        credentialManager = CredentialManager.create(this)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        comprobarSesion()

        binding.opcionEmail.setOnClickListener {
            startActivity(Intent(applicationContext, LoginEmailActivity::class.java))
        }

        binding.opcionGoogle.setOnClickListener {
            iniciarGoogle()
        }
    }

    private fun iniciarGoogle() {
        progressDialog.setMessage("Iniciando sesion con Google")
        progressDialog.show()

        // 1. Instantiate a Google sign-in request
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(getString(R.string.default_web_client_id))
            .build()

        // 2. Create the Credential Manager request
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        // 3. Launch the Credential Manager UI using a Coroutine
        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = this@OpcionesLoginActivity,
                    request = request,
                )

                // 4. Handle the successful credential result
                handleSignIn(result.credential)
            } catch (e: GetCredentialException) {
                progressDialog.dismiss()
                Toast.makeText(
                    applicationContext,
                    "Error de credencial: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun handleSignIn(credential: androidx.credentials.Credential) {
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
        } else {
            progressDialog.dismiss()
            Toast.makeText(
                this,
                "Error: Tipo de credencial no reconocido.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResultado ->
                progressDialog.dismiss()
                if (authResultado.additionalUserInfo!!.isNewUser) {
                    actualizarInfoUsuario()
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()

                Toast.makeText(
                    this,
                    "Falló autenticación Firebase: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun actualizarInfoUsuario() {
        progressDialog.setMessage("Guardando información")

        val uidU = firebaseAuth.uid
        val nombresU = firebaseAuth.currentUser!!.displayName
        val emailU = firebaseAuth.currentUser!!.email
        val tiempoR = Constantes.obtenerTiempoD()

        val datosUsuario = HashMap<String, Any>()

        datosUsuario["uid"] = "$uidU"
        datosUsuario["nombres"] = "$nombresU"
        datosUsuario["email"] = "$emailU"
        datosUsuario["tiempoR"] = "$tiempoR"
        datosUsuario["proveedor"] = "Google"
        datosUsuario["estado"] = "Online"
        datosUsuario["imagen"] = ""

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidU!!)
            .setValue(datosUsuario)
            .addOnSuccessListener {
                progressDialog.dismiss()

                startActivity(Intent(applicationContext, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Falló la creación de la cuenta debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun comprobarSesion() {
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }
}