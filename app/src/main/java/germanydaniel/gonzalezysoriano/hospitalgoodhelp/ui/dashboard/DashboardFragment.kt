package germanydaniel.gonzalezysoriano.hospitalgoodhelp.ui.dashboard

import Modelo.AdaptadorPaciente
import Modelo.Conexion
import Modelo.dataClassPaciente
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import germanydaniel.gonzalezysoriano.hospitalgoodhelp.R
import germanydaniel.gonzalezysoriano.hospitalgoodhelp.databinding.FragmentDashboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

        CoroutineScope(Dispatchers.IO).launch {
            val PacientesDB = obtenerDatos()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorPaciente(PacientesDB)
                rcvPaciente.adapter = adapter
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}