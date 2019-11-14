/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ConnectionMySql {

  private static Connection con = null;

  private static void init() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/sbiker", "root", "x?key=786DAA807CF196D");

    } catch (ClassNotFoundException | SQLException ex) {
      Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static void closeConnection() {
    try {
      con.close();
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}
