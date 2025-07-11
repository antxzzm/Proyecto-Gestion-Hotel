package dao;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Connection getConexion(){
        String conexionUrl = "jdbc:sqlserver://localhost:1433;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "databaseName=loginjava;"
                + "user=sa;"
                + "password=12345;";
        
        try{
            Connection con = DriverManager.getConnection(conexionUrl);
            return con;
        } catch(SQLException ex){
            System.out.println(ex.toString());
            return null;
        }
        
    }
}
