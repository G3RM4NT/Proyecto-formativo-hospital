package ViewHolderHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import germanydaniel.gonzalezysoriano.hospitalgoodhelp.R

class ViewHolderPaciente(view: View): RecyclerView.ViewHolder(view) {
    val NombrePaciente: TextView =view.findViewById(R.id.lblNombrePaciente)
    val ApellidoPaciente: TextView =view.findViewById(R.id.lblApellidoPaciente)
    val EdadPaciente : TextView =view.findViewById(R.id.lblEdadPaciente)
    val EnfermedadPaciente: TextView =view.findViewById(R.id.lblEnfermedadPaciente)
    val NumerodeHabitacion : TextView =view.findViewById(R.id.lblNumerodeHabitacion)
    val NumeroDecama : TextView =view.findViewById(R.id.lblNumeroCama)
    val MedicamentosAsignados: TextView =view.findViewById(R.id.lblMedicamentosAsignados)
    val FechadeIngreso : TextView =view.findViewById(R.id.lblFechaIngreso)
    val HoraAplicacion : TextView =view.findViewById(R.id.lblHoradeAplicacion)

    val imgEditar: ImageView = view.findViewById(R.id.btnEditCliente)
    val imgBorrar: ImageView = view.findViewById(R.id.btnDeleteCliente)


}