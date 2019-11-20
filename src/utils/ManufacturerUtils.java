package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sbiker.classes.Manufacturer;
import static utils.ConnectionMySql.getAllProducts;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public class ManufacturerUtils {

  public static void processManufacturer() {
    try {
      ResultSet rs = getAllProducts();
      List<String> allManufacturer = new ArrayList<>();
      //add first unknown manufacturer to list
      AllManufacturers.addToManufacturerList(UnknownManufacturer.getUnknownManufacturer());
      while (rs.next()) { // loop through all products to get manufacturer's name
        //get string that contains manufacturer's name
        String manufacturerInfo = rs.getString(4);
        //get id of products in old DBs
        String productId = rs.getString(1);
        String manufacturerName = null;
        //process to get manufacturer naem
        manufacturerInfo = manufacturerInfo.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ").trim().toLowerCase();
        if (manufacturerInfo.contains("sản xuất:") && !manufacturerInfo.contains("sản xuất:<") && !manufacturerInfo.contains("sản xuất: <")) {

          int indexOfSubstring = manufacturerInfo.trim().indexOf("sản xuất:");
          try {
            manufacturerInfo = manufacturerInfo.trim().substring(indexOfSubstring + 10).trim();

          } catch (Exception e) {
            //catch end of line exception
//            System.out.println("Exception");
            UnknownManufacturer.addNewProducts(productId);
            continue;
          }
          indexOfSubstring = manufacturerInfo.indexOf(" ");
          //known manufacturer

          if (indexOfSubstring > 0) {
            manufacturerName = manufacturerInfo.substring(0, indexOfSubstring);
          } else {
            manufacturerName = manufacturerInfo;
          }
          if ("thùng".equals(manufacturerName)) {
            manufacturerName = "givi";
          }

          if (manufacturerName.contains("size:")) {
            // size is not a manufacturer
            UnknownManufacturer.addNewProducts(productId);
            continue;
          }

          int manufacturerIndex = allManufacturer.indexOf(manufacturerName);
          if (manufacturerIndex < 0) {
            //add new manufacturer to array list
            allManufacturer.add(manufacturerName);
            manufacturerIndex = allManufacturer.size() + 1;
            AllManufacturers.addToManufacturerList(new Manufacturer(manufacturerIndex, manufacturerName, new ArrayList<String>()));
          }
          manufacturerIndex = allManufacturer.indexOf(manufacturerName) + 1;
          AllManufacturers.getManufacturerList().get(manufacturerIndex).addProducts(productId);

        } else {
          //unknown manufacturer
          UnknownManufacturer.addNewProducts(productId);
        }
      }

      AllManufacturers.getManufacturerList().forEach((Manufacturer temp) -> {

//        System.out.println(temp.getId() + "           " + temp.getName() + "         " + temp.getProducts());
      });
      ConnectionMySql.closeConnection();
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

}

class UnknownManufacturer {

  private static final Manufacturer unknownManufacturer = new Manufacturer(1, "unknown", new ArrayList<String>());

  public static Manufacturer getUnknownManufacturer() {
    return unknownManufacturer;
  }

  public static void addNewProducts(String product) {
    unknownManufacturer.addProducts(product);
  }
}

class AllManufacturers {

  private static final ArrayList<Manufacturer> manuList = new ArrayList<Manufacturer>();

  public static ArrayList<Manufacturer> getManufacturerList() {
    return manuList;
  }

  public static void addToManufacturerList(Manufacturer e) {
    manuList.add(e);
  }
}
