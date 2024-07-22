package Modelo

import java.sql.Connection
import java.sql.DriverManager

class Conexion {

    fun cadenaConexion(): Connection?{
        try {

            val ip = "jdbc:oracle:thin:@192.168.56.1:1521:xe"
            val user = "SYSTEM"
            val password = "ITR2024"

            val strCon = DriverManager.getConnection(ip, user, password)
            return strCon

        }catch (e: Exception){
            println("Error: $e")
            return null
        }
    }
}