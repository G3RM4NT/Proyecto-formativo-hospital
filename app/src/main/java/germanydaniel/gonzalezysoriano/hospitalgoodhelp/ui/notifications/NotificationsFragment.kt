package germanydaniel.gonzalezysoriano.hospitalgoodhelp.ui.notifications

import Modelo.AdaptadorPaciente
import Modelo.Conexion
import Modelo.dataClassPaciente
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import germanydaniel.gonzalezysoriano.hospitalgoodhelp.R
import germanydaniel.gonzalezysoriano.hospitalgoodhelp.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val txtnombrePaciente = root.findViewById<EditText>(R.id.txtNombrePaciente)
        val txtapellidoPaciente = root.findViewById<EditText>(R.id.txtApellidoPaciente)
        val txtedadPaciente = root.findViewById<EditText>(R.id.txtEdadPaciente)
        val txtnumeroHabitacion = root.findViewById<EditText>(R.id.txtNumeroHabitacion)
        val txtnumeroCama = root.findViewById<EditText>(R.id.txtNumerodeCama)
        val txtmedicamentoAsignado = root.findViewById<EditText>(R.id.txtMedicamentosAsignados)
        val txtfechaIngreso = root.findViewById<EditText>(R.id.txtFechaIngreso)
        val txthoraAplicacionMed = root.findViewById<EditText>(R.id.txtHoraAplicacion)
        val btnIngresarPaciente = root.findViewById<Button>(R.id.btnAgregarPaciente)
        val rcvPaciente = root.findViewById<RecyclerView>(R.id.rcvPacientes)


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

                val nuevoPacientes = obtenerDatos()
                withContext(Dispatchers.Main){
                    (rcvPaciente.adapter as? AdaptadorPaciente)?.ActualizarLista(nuevoPacientes)

                }
            }


        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}