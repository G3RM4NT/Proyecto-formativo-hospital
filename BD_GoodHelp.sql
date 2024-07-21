Create table UsuarioHospital(
UUID_Usuario Varchar2(36) Primary key,
NombreUsuario VARCHAR2(50) not null,
ContraseñaUsuario VARCHAR2(70) not null,
CorreoUsuario VARCHAR2(100) not null
)


Create table Paciente(
UUID_Paciente Varchar2(36) Primary key,
Nombre VARCHAR2(100) not null,
Apellido VARCHAR2(100) not null,
Edad INT CHECK(Edad > 18) not null,
NumeroHabitacion INT not null,
NumeroCama INT not null, 
MedicamentoAsignado VARCHAR2(100) not null,
FechaIngreso VARCHAR2(10) not null,
HoraDeAplicacionMedicamento VARCHAR2(5)
)
