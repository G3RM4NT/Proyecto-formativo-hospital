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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNombreUserLogin = findViewById<EditText>(R.id.txtUsuarioLogin)
        val txtContrasenaLogin = findViewById<EditText>(R.id.txtContrasenaLogin)
        val btnIniciarSesionLogin = findViewById<Button>(R.id.btnIniciarSesion)


        btnIniciarSesionLogin.setOnClickListener{
            val pantallaPrincipal = Intent(this@Login, Inicio::class.java)

            val objConexion = Conexion().cadenaConexion()

            GlobalScope.launch(Dispatchers.IO) {

                val objConexion = Conexion().cadenaConexion()

                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM UsuarioHospital WHERE NombreUsuario = ? AND ContraseñaUsuario = ?")!!

                comprobarUsuario.setString(1, txtNombreUserLogin.text.toString())
                comprobarUsuario.setString(2, txtContrasenaLogin.text.toString())
                val resultado = comprobarUsuario.executeQuery()

                if (resultado.next()) {

                    startActivity(pantallaPrincipal)

                } else {

                    withContext(Dispatchers.Main)
                    {

                        Toast.makeText(this@Login, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }

    }
}