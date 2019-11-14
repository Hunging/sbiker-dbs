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

  public static ResultSet getAllProducts() {
    init();
    ResultSet rs = null;
    try {
      //here sonoo is database name, root is username and password
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT ID, post_content, post_title, post_excerpt \n"
              + "FROM sbiker.wp_posts\n"
              + "where post_type = 'product'\n"
              + "and post_status = 'publish';");

    } catch (SQLException ex) {
      Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rs;
  }

}
