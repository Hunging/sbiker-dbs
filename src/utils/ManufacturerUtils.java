package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
