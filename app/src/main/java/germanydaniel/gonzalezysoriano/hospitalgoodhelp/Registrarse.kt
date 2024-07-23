package germanydaniel.gonzalezysoriano.hospitalgoodhelp

import Modelo.Conexion
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import germanydaniel.gonzalezysoriano.hospitalgoodhelp.R.id.lblPoseesunacuenta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class Registrarse : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val txtNombreUsuario = findViewById<EditText>(R.id.txtNombreUsuario)
        val txtContrasenaUsuario = findViewById<EditText>(R.id.txtContraseñaUsuario)
        val txtCorreoUsuario = findViewById<EditText>(R.id.txtCorreoUsuario)
        val btnIngresar = findViewById<Button>(R.id.btnRegistrarse)
        val lblPoseesCuenta = findViewById<TextView>(R.id.lblPoseesunacuenta)

        lblPoseesCuenta.setOnClickListener{
            val cambiarpantalla = Intent(this@Registrarse, Inicio::class.java)
            startActivity(cambiarpantalla)

        }




        fun RegistrarUsuario(){

            try{
                CoroutineScope(Dispatchers.IO).launch {

                    val loginir = Intent(this@Registrarse, Login::class.java)

                    val objConexion = Conexion().cadenaConexion()

                    val crearUsuario = objConexion?.prepareStatement("INSERT INTO UsuarioHospital(UUID_Usuario, NombreUsuario, ContraseñaUsuario, CorreoUsuario) VALUES(?, ? , ?, ?)")!!
                    crearUsuario.setString(1, UUID.randomUUID().toString())
                    crearUsuario.setString(2, txtNombreUsuario.text.toString())
                    crearUsuario.setString(3, txtContrasenaUsuario.text.toString())
                    crearUsuario.setString(4, txtCorreoUsuario.text.toString())
                    crearUsuario.executeUpdate()

                    withContext(Dispatchers.Main){
                        startActivity(loginir)
                    }
                }
            }catch (e: Exception){
                Toast.makeText(this@Registrarse, "No se ha podido registrar el usuario", Toast.LENGTH_SHORT).show()
            }

        }
        btnIngresar.setOnClickListener{
            RegistrarUsuario()
            }


        }
    }



