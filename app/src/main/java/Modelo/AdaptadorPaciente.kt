package Modelo

import ViewHolderHelpers.ViewHolderPaciente
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import germanydaniel.gonzalezysoriano.hospitalgoodhelp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdaptadorPaciente(private var Datos: List<dataClassPaciente>): RecyclerView.Adapter<ViewHolderHelpers.ViewHolderPaciente>(){

    fun ActualizarLista(nuevaLista: List<dataClassPaciente>){
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun ActualizarPantallaPaciente(UUID: String, nuevoNombrePaciente:String, nuevoApellidoPaciente:String, nuevoEdadPaciente:Int, nuevoNumeroHabitacion: Int, nuevoNumeroCama: Int, nuevoMedicamentoAsignado: String, nuevoFechaIngreso: String, nuevoHoraAplicacionMed: String) {
        val index = Datos.indexOfFirst { it.UUID == UUID }
        Datos[index].NombrePaciente = nuevoNombrePaciente
        Datos[index].ApellidoPaciente = nuevoApellidoPaciente
        Datos[index].Edad = nuevoEdadPaciente
        Datos[index].NumeroHabitacion = nuevoNumeroHabitacion
        Datos[index].NumeroCama = nuevoNumeroCama
        Datos[index].MedicamentoAsignado = nuevoMedicamentoAsignado
        Datos[index].FechaIngreso = nuevoFechaIngreso
        Datos[index].HoraAplicacionMed = nuevoHoraAplicacionMed

        notifyDataSetChanged()
    }

    fun EliminarPaciente(UUID: String, position: Int){
        val datalist = Datos.toMutableList()
        datalist.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {

            val objconexion = Conexion().cadenaConexion()

            val deletePaciente = objconexion?.prepareStatement("DELETE FROM Paciente WHERE UUID_Paciente = ?")!!
            deletePaciente.setString(1, UUID)

            deletePaciente.executeUpdate()

            val commit = objconexion.prepareStatement("Commit")!!
            commit.executeUpdate()


        }
        Datos = datalist.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }

    fun ActualizarPaciente(UUID: String, nuevoNombrePaciente:String, nuevoApellidoPaciente:String, nuevoEdadPaciente:Int, nuevoNumeroHabitacion: Int, nuevoNumeroCama: Int, nuevoMedicamentoAsignado: String, nuevoFechaIngreso: String, nuevoHoraAplicacionMed: String, position: Int){
        val datalist = Datos.toMutableList()
        val pacienteActualizado = datalist[position].copy(
            NombrePaciente = nuevoNombrePaciente,
            ApellidoPaciente = nuevoApellidoPaciente,
            Edad = nuevoEdadPaciente,
            NumeroHabitacion = nuevoNumeroHabitacion,
            NumeroCama = nuevoNumeroCama,
            MedicamentoAsignado = nuevoMedicamentoAsignado,
            FechaIngreso = nuevoFechaIngreso,
            HoraAplicacionMed = nuevoHoraAplicacionMed
        )

        datalist[position] = pacienteActualizado

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = Conexion().cadenaConexion()

            val updatePaciente = objConexion?.prepareStatement("UPDATE Paciente SET Nombre = ?, Apellido = ?, Edad = ?, NumeroHabitacion = ?, NumeroCama = ?, MedicamentoAsignado = ? , FechaIngreso = ?, HoraAplicacionMedicamento = ? WHERE UUID_Paciente = ?")!!
            updatePaciente.setString(1, nuevoNombrePaciente)
            updatePaciente.setString(2, nuevoApellidoPaciente)
            updatePaciente.setInt(3, nuevoEdadPaciente)
            updatePaciente.setInt(4, nuevoNumeroHabitacion)
            updatePaciente.setInt(5, nuevoNumeroCama)
            updatePaciente.setString(6, nuevoMedicamentoAsignado)
            updatePaciente.setString(7, nuevoFechaIngreso)
            updatePaciente.setString(8, nuevoHoraAplicacionMed)
            updatePaciente.setString(9, UUID)
            updatePaciente.executeUpdate()

            withContext(Dispatchers.Main){
                ActualizarPantallaPaciente(UUID, nuevoNombrePaciente, nuevoApellidoPaciente, nuevoEdadPaciente, nuevoNumeroHabitacion, nuevoNumeroCama, nuevoMedicamentoAsignado, nuevoFechaIngreso, nuevoHoraAplicacionMed)
            }
        }
        Datos = datalist.toList()
        notifyItemChanged(position)


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPaciente {

        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)

        return ViewHolderPaciente(vista)

    }

    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: ViewHolderPaciente, position: Int) {

        val Paciente = Datos[position]
        holder.NombrePaciente.text = Paciente.NombrePaciente

        //todo: clic al icono de eliminar
        holder.imgBorrar.setOnClickListener {

            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Desea eliminar la mascota?")

            //Botones
            builder.setPositiveButton("Si") { dialog, which ->
                EliminarPaciente(Paciente.NombrePaciente, position)
            }

            builder.setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }

        holder.imgEditar.setOnClickListener{
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar")
            builder.setMessage("¿Desea actualizar la mascota?")


            val cuadroTexto = EditText(context)
            cuadroTexto.setHint(Paciente.NombrePaciente)
            cuadroTexto.setHint(Paciente.ApellidoPaciente)
            cuadroTexto.setHint(Paciente.Edad)
            cuadroTexto.setHint(Paciente.NumeroHabitacion)
            cuadroTexto.setHint(Paciente.NumeroCama)
            cuadroTexto.setHint(Paciente.MedicamentoAsignado)
            cuadroTexto.setHint(Paciente.FechaIngreso)
            cuadroTexto.setHint(Paciente.HoraAplicacionMed)
            builder.setView(cuadroTexto)

            //Botones
            builder.setPositiveButton("Actualizar") { dialog, which ->

            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

    }

}