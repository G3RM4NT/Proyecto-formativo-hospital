package Modelo

import java.sql.Connection
import java.sql.DriverManager

class Conexion {

    fun cadenaConexion(): Connection?{
        try {

            val ip = "jdbc:oracle:thin:@192.168.2.17:1521:xe"
            val user = "system"
            val password = "danny"

            val strCon = DriverManager.getConnection(ip, user, password)
            return strCon

        }catch (e: Exception){
            println("Error: $e")
            return null
        }
    }
}