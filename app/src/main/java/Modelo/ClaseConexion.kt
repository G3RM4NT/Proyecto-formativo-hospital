package Modelo

import oracle.ons.Connection
import java.sql.DriverManager

class ClaseConexion {

    fun cadenaConexion(): java.sql.Connection? {

try {
    val url = "jdbc:oracle:thin:@192.168.1.128:1521:xe"
    val usuario = "SYSTEM"
    val contrasena = "gagm23."
    val connection = DriverManager.getConnection(url, usuario, contrasena)
return connection

}catch (e: Exception){
    println("error: $e")
    return null
}
    }



}