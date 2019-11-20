/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import transferdbdata.classes.Product;
import transferdbdata.classes.ProductImage;

/**
 *
 * @author Admin
 */
public class ConnectionSqlServer {
  
  private static Connection con = null;
  //1 = category, 2 = manufacture, 3 = product, 4=image
  private static String[] tableName = {"Category", "Manufacture", "Product", "ProductImage"};
  
  private static void init() {
    try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      String connectionString = "";
      con = DriverManager.getConnection(connectionString, "", "");
      
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
  
  public static void insertProducts() {
    init();
    AllProducts.getProductList().forEach((product) -> {
      try {
        PreparedStatement stmt = con.prepareStatement("insert into Sbiker.dbo." + tableName[2] + " values(?,?,?,?,?,?,?,?,?,?,?)");
        stmt.setInt(1, product.getId());// id
        stmt.setInt(2, product.getManufacturer()); //manufacturer id
        stmt.setInt(3, product.getCategories().get(0)); //category id
        stmt.setString(4, product.getName()); //name
        stmt.setString(5, product.getShort_description()); //short desc
        stmt.setString(6, product.getDescription()); //description
        stmt.setString(7, product.getSize()); //size
        stmt.setString(8, product.getGuarantee()); //warranty
        stmt.setString(9, product.getImages()); //image
        stmt.setInt(10, product.getPrice()); //price
        stmt.setString(11, product.getStatus()); //status

        int i = stmt.executeUpdate();
        
        System.out.println(i + " records inserted");
      } catch (SQLException ex) {
        Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
        
      }
    });
    closeConnection();
  }
  
  public static void selectProducts() {
    ResultSet rs = null;
    int count = 0;
    try {
      init();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * \n"
              + "FROM Sbiker.dbo." + tableName[2] + "\n");
      while (rs.next()) {
        count++;
        System.out.println(rs.getString(1) + "       " + rs.getString(2) + "          " + rs.getString(10) + "       " + rs.getString(11));
//        System.out.println(count);
      }
      closeConnection();
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static void updateImageFromProduct(Product product) {
    init();
    try {
      PreparedStatement ps = con.prepareStatement(
              "UPDATE Sbiker.dbo." + tableName[2] + "\n"
              + "SET Image = ? WHERE Id = ?");
      ps.setString(1, product.getImages());
      ps.setInt(2, product.getId());
      int row = ps.executeUpdate();
      System.out.println(row + " row updated!");
      ps.close();
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  
  public static void updateImageFromProductImage(ProductImage image) {
    init();
    try {
      PreparedStatement ps = con.prepareStatement(
              "UPDATE Sbiker.dbo." + tableName[3] + "\n"
              + "SET Image = ? WHERE Id = ?");
      ps.setString(1, image.getImage());
      ps.setInt(2, image.getId());
      int row = ps.executeUpdate();
      System.out.println(row + " row updated!");
      ps.close();
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  
  public static void selectCategories() {
    ResultSet rs = null;
    try {
      init();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * \n"
              + "FROM Sbiker.dbo." + tableName[0] + "\n");
      while (rs.next()) {
        System.out.println(rs.getString(1) + "       " + rs.getString(2));
      }
      closeConnection();
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static void selectManufacture() {
    ResultSet rs = null;
    try {
      init();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * \n"
              + "FROM Sbiker.dbo." + tableName[1] + "\n");
      while (rs.next()) {
        System.out.println(rs.getString(1) + "       " + rs.getString(2));
      }
      closeConnection();
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static void selectProductImage() {
    ResultSet rs = null;
    try {
      init();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * \n"
              + "FROM Sbiker.dbo." + tableName[3] + "\n");
      while (rs.next()) {
        System.out.println(rs.getString(1) + "       " + rs.getString(2));
      }
      closeConnection();
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static void insertProductImage() {
    init();
    int count = 0;
    
    for (ProductImage image : AllSubImages.getImageList()) {
      try {
        count++;
        PreparedStatement stmt = con.prepareStatement("insert into Sbiker.dbo." + tableName[3] + " values(?,?,?)");

        stmt.setString(1, image.getImage());
        stmt.setInt(2, 0);
        stmt.setInt(3, image.getNew_product_id());//1 specifies the first parameter in the query
        
        int i = stmt.executeUpdate();
        
        System.out.println(count + " records inserted " + i);
      } catch (SQLException ex) {
        Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    closeConnection();
  }
  
  public static void insertManufacturer() {
    init();
    AllManufacturers.getManufacturerList().forEach((manufacture) -> {
      try {
        PreparedStatement stmt = con.prepareStatement("insert into Sbiker.dbo." + tableName[1] + " values(?,?)");
        stmt.setInt(1, manufacture.getId());//1 specifies the first parameter in the query
        stmt.setString(2, manufacture.getName());
        int i = stmt.executeUpdate();
        
        System.out.println(i + " records inserted");
      } catch (SQLException ex) {
        Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
      }
    });
    closeConnection();
  }
  
  public static void insertCategories() {
    init();
    AllCategories.getCategoryList().forEach((category) -> {
      try {
        PreparedStatement stmt = con.prepareStatement("insert into Sbiker.dbo." + tableName[0] + " values(?,?)");
        stmt.setInt(1, category.getId());//1 specifies the first parameter in the query
        stmt.setString(2, category.getName());
        int i = stmt.executeUpdate();
        
        System.out.println(i + " records inserted");
      } catch (SQLException ex) {
        Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
      }
    });
    closeConnection();
  }
  
  public static void showTables() {
    try {
      
      init();
      DatabaseMetaData meta = con.getMetaData();
      ResultSet rs = meta.getTables("sbiker", "dbo", "%", null);
      System.out.println("List of tables: ");
      
      while (rs.next()) {
        System.out.println(rs.getString(3));
      }
      
      closeConnection();
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static void showDbs() {
    try {
      
      init();
      DatabaseMetaData meta = con.getMetaData();
      ResultSet rs = meta.getCatalogs();
      System.out.println("List of databases: ");
      while (rs.next()) {
        System.out.println(rs.getString("TABLE_CAT"));
      }
      
      closeConnection();
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static void getcolumn(int id) {
    String tablename = tableName[id];
    try {
      init();
      DatabaseMetaData meta = con.getMetaData();
      ResultSet rs = meta.getColumns("sbiker", null, tablename, "%");
      System.out.println("List of columns: ");
      while (rs.next()) {
        System.out.println(rs.getString(4));
      }
      
      closeConnection();

//     resultSet = meta.getColumns(databaseName, null, tableName, "%");
//     while (resultSet.next()) {
//       log.info("Column Name of table " + tableName + " = "
//           + resultSet.getString(4));
//     }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
}
