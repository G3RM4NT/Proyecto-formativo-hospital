package germanydaniel.gonzalezysoriano.hospitalgoodhelp

import Modelo.Conexion
import Modelo.dataClassPaciente
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import germanydaniel.gonzalezysoriano.hospitalgoodhelp.databinding.ActivityInicioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class Inicio : AppCompatActivity() {

    private lateinit var binding: ActivityInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_inicio)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val txtnombrePaciente = findViewById<EditText>(R.id.txtNombrePaciente)
        val txtapellidoPaciente = findViewById<EditText>(R.id.txtApellidoPaciente)
        val txtedadPaciente = findViewById<EditText>(R.id.txtEdadPaciente)
        val txtnumeroHabitacion = findViewById<EditText>(R.id.txtNumeroHabitacion)
        val txtnumeroCama = findViewById<EditText>(R.id.txtNumerodeCama)
        val txtmedicamentoAsignado = findViewById<EditText>(R.id.txtMedicamentosAsignados)
        val txtfechaIngreso = findViewById<EditText>(R.id.txtFechaIngreso)
        val txthoraAplicacionMed = findViewById<EditText>(R.id.txtHoraAplicacion)
        val btnIngresarPaciente = findViewById<Button>(R.id.btnAgregarPaciente)
        val rcvPaciente = findViewById<RecyclerView>(R.id.rcvPacientes)

        rcvPaciente.layoutManager = LinearLayoutManager(this@Inicio)

        fun obtenerDatos(): List<dataClassPaciente>{

            val objConexion = Conexion().cadenaConexion()


            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("select * from Paciente")!!


            val Pacientes = mutableListOf<dataClassPaciente>()

            while (resulSet.next()){
                val uuid = resulSet.getString("UUID_Paciente")
                val nombre = resulSet.getString("Nombre")
                val apellido = resulSet.getString("Apellido")
                val edad = resulSet.getInt("Edad")
                val numHabitacion = resulSet.getInt("NumeroHabitacion")
                val NumeroCama = resulSet.getInt("NumeroCama")
                val MedicamentoAsignado = resulSet.getString("MedicamentoAsignado")
                val FechaIngreso = resulSet.getString("FechaIngreso")
                val HoraDeAplicacionMedicamento = resulSet.getString("HoraDeAplicacionMedicamento")


                val Paciente = dataClassPaciente(uuid, nombre, apellido, edad, numHabitacion, NumeroCama, MedicamentoAsignado, FechaIngreso, HoraDeAplicacionMedicamento )
                Pacientes.add(Paciente)
            }
            return Pacientes
        }

        btnIngresarPaciente.setOnClickListener{

            CoroutineScope(Dispatchers.IO).launch {

                val objConexion = Conexion().cadenaConexion()

                val IngresarPaciente = objConexion?.prepareStatement("INSERT INTO Paciente(UUID, Nombre, Apellido, Edad, NumeroHabitacion,NumeroCama, MedicamentoAsignado, FechaIngreso, HoraDeAplicacionMedicamento) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)")!!
                IngresarPaciente.setString(1, UUID.randomUUID().toString())
                IngresarPaciente.setString(2, txtnombrePaciente.text.toString())
                IngresarPaciente.setString(3, txtapellidoPaciente.text.toString())
                IngresarPaciente.setInt(4, txtedadPaciente.text.toString().toInt())
                IngresarPaciente.setInt(5, txtnumeroHabitacion.text.toString().toInt())
                IngresarPaciente.setInt(6, txtnumeroCama.text.toString().toInt())
                IngresarPaciente.setString(7, txtmedicamentoAsignado.text.toString())
                IngresarPaciente.setString(8, txtfechaIngreso.text.toString())
                IngresarPaciente.setString(9, txthoraAplicacionMed.text.toString())
                IngresarPaciente.executeUpdate()

            }


        }
    }




}