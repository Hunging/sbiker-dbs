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

  public static ResultSet getAllPrice() {
    init();
    ResultSet rs = null;
    try {
      //here sonoo is database name, root is username and password
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT pm.post_id, pm.meta_value\n"
              + "FROM sbiker.wp_postmeta as pm\n"
              + "inner join sbiker.wp_posts as post\n"
              + "	on pm.post_id = post.ID\n"
              + "where pm.meta_key ='_regular_price'\n"
              + "and post.post_status = 'publish';");

    } catch (SQLException ex) {
      Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rs;
  }

  public static ResultSet getAllImageId() {
    init();
    ResultSet rs = null;
    try {
      //here sonoo is database name, root is username and password
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT pm.post_id, pm.meta_value\n"
              + "FROM sbiker.wp_postmeta as pm\n"
              + "inner join sbiker.wp_posts as post\n"
              + "	on pm.post_id = post.ID\n"
              + "where post.post_type = 'product'\n"
              + "	and pm.meta_key like '%image_gallery%'\n"
              + "    and post.post_status = 'publish'\n"
              + "    and meta_value != '';");

    } catch (SQLException ex) {
      Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rs;
  }

  public static ResultSet getAllImageLink() {
    init();
    ResultSet rs = null;
    try {
      //here sonoo is database name, root is username and password
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT ID, post_title, guid\n"
              + "FROM sbiker.wp_posts\n"
              + "where post_type = 'attachment';");

    } catch (SQLException ex) {
      Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rs;
  }

  public static ResultSet getAllCategories() {
    init();
    ResultSet rs = null;
    try {
      //here sonoo is database name, root is username and password
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("select term.term_id, term.name, term.slug\n"
              + "from (sbiker.wp_terms as term\n"
              + "inner join sbiker.wp_termmeta as metadata\n"
              + "on term.term_id = metadata.term_id)\n"
              + "where (metadata.meta_key like '%product_cat%' or metadata.meta_key like '%product_tag%' );");

    } catch (SQLException ex) {
      Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rs;
  }

  public static ResultSet getAllProductWithThumbnail() {
    init();
    ResultSet rs = null;
    try {
      //here sonoo is database name, root is username and password
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT post.ID, post.post_content, post.post_title, post.post_excerpt, post2.guid\n"
              + "FROM sbiker.wp_postmeta as pm\n"
              + "inner join sbiker.wp_posts as post\n"
              + "	on pm.post_id = post.ID\n"
              + "inner join sbiker.wp_posts as post2\n"
              + "	on pm.meta_value = post2.ID\n"
              + "where post.post_type = 'product'\n"
              + "and pm.meta_key like '%_thumbnail_id%'\n"
              + "and post.post_status = 'publish';");

    } catch (SQLException ex) {
      Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rs;
  }

  public static ResultSet getProductCategoryRelationship() {
    init();
    ResultSet rs = null;
    try {
      //here sonoo is database name, root is username and password
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("select T.term_id, r.object_id\n"
              + "from sbiker.wp_terms as T\n"
              + "inner join sbiker.wp_termmeta as M\n"
              + "	on T.term_id = M.term_id\n"
              + "inner join sbiker.wp_term_relationships as r\n"
              + "	on T.term_id = r.term_taxonomy_id\n"
              + "inner join sbiker.wp_posts as p\n"
              + "	on r.object_id = p.ID\n"
              + "where M.meta_key like '%product_cat%'\n"
              + "and p.post_status = 'publish';");

    } catch (SQLException ex) {
      Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rs;
  }

}
