/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author caio
 */
public class ConnectionFactory {

    public Connection getConnection() throws SQLException {

        try{
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/biblioteca?useTimezone=true&serverTimezone=UTC", "root", "cdc121022");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
