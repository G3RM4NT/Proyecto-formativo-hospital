package Modelo

data class dataClassPaciente(
    var UUID: String,
    var NombrePaciente: String,
    var ApellidoPaciente: String,
    var Edad: Int,
    var NumeroHabitacion: Int,
    var NumeroCama: Int,
    var MedicamentoAsignado: String,
    var FechaIngreso:String,
    var HoraAplicacionMed: String
)
