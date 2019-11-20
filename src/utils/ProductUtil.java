/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sbiker.classes.Product;

/**
 *
 * @author Admin
 */
public class ProductUtil {

  public static void processProductAllinOne() {
    processProductAndThumbnail();
    AllProducts.product2DictionaryInit();
    processProductAndCategory();
    processProductAndManufacturer();
    processProductAndPrice();
    AllProducts.getProductList().forEach((product) -> {
      System.out.println(product.getImages());
//      ConnectionSqlServer.updateImageFromProduct(product);
    });
  }

  public static void processProductAndManufacturer() {
    AllManufacturers.getManufacturerList().forEach((manufacturer) -> {
//      System.out.println(manufacturer.getId() + "            " + manufacturer.getProducts());
      ArrayList<String> productList = manufacturer.getProducts();
      if (productList.size() > 0) {
        int manufacturerId = manufacturer.getId();

        productList.forEach((productId) -> {
          int productIdInArrayList = AllProducts.getProductIdFromPostId(Integer.parseInt(productId.trim()));
          AllProducts.setManufacturerIdToProduct(productIdInArrayList, manufacturerId);
        });
      }

    });
  }

  public static void processProductAndPrice() {
    try {
      ResultSet rs = ConnectionMySql.getAllPrice();

      while (rs.next()) {
        int post_id = rs.getInt(1);
        int price = rs.getInt(2);
        int productId = AllProducts.getProductIdFromPostId(post_id);
        AllProducts.setPriceToProduct(productId, price);
      }
      ConnectionMySql.closeConnection();

    } catch (SQLException ex) {
      Logger.getLogger(CategoryUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static void processProductAndCategory() {
    try {
      ResultSet rs = ConnectionMySql.getProductCategoryRelationship();
      while (rs.next()) {
        int categoryTermId = rs.getInt(1);
        int productPostId = rs.getInt(2);
        int productId = AllProducts.getProductIdFromPostId(productPostId);
        int categoryId = AllCategories.getCategoryIdFromTermId(categoryTermId);
        AllProducts.addCategoryToProduct(productId, categoryId);
      }
      ConnectionMySql.closeConnection();

    } catch (Exception ex) {
      Logger.getLogger(CategoryUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static void processProductAndThumbnail() {
    try {
      ResultSet rs = ConnectionMySql.getAllProductWithThumbnail();
      int index = 0;
      while (rs.next()) {
        index++;
        int post_id = rs.getInt(1);
        String post_content = rs.getString(2);
        String name = rs.getString(3);
        String post_excerpt = rs.getString(4);
        String imagePath = rs.getString(5);
        String newimagePath = StringUtils.url2Slug(imagePath);
        Product pro = new Product(index, post_id, name, post_content, post_excerpt, newimagePath);
        if (!"".equals(post_excerpt.trim())) {
          try {
            pro.setStatus(getStatus(post_excerpt));
            pro.setSize(getSize(post_excerpt));
            pro.setType(getType(post_excerpt));
            pro.setGuarantee(getGuaranty(post_excerpt));
          } catch (Exception e) {
            System.out.println(e);
          }
        }

        AllProducts.addToProductList(pro);
      }
      ConnectionMySql.closeConnection();

    } catch (SQLException ex) {
      Logger.getLogger(CategoryUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private static String getStatus(String desc) {
    String status = "";
    if (desc.contains("Tình trạng: <")) {
      int startIndex = desc.indexOf("Tình trạng:");
      String tmp = desc.substring(startIndex);
      startIndex = tmp.indexOf(">") + 1;
      tmp = tmp.substring(startIndex);
      int endIndex = tmp.indexOf("</span>");
      status = tmp.substring(0, endIndex).trim();

    } else if (desc.contains("Tình trạng:")) {
      int startIndex = desc.indexOf("Tình trạng:") + 11;
      String tmp = desc.substring(startIndex);
      int endIndex = tmp.indexOf("<");
      status = tmp.substring(0, endIndex).trim();
    }
    return status;
  }

  private static String getSize(String desc) {
    String size = "";
    if (desc.contains("Size: <")) {
      int startIndex = desc.indexOf("Size:");
      String tmp = desc.substring(startIndex);
      startIndex = tmp.indexOf(">") + 1;
      tmp = tmp.substring(startIndex);
      int endIndex = tmp.indexOf("</span>");
      size = tmp.substring(0, endIndex);

    } else if (desc.contains("Size:")) {
      int startIndex = desc.indexOf("Size:") + 5;
      String tmp = desc.substring(startIndex);
      int endIndex = tmp.indexOf("<");
      size = tmp.substring(0, endIndex);
    }
    return size.trim();
  }

  private static String getGuaranty(String desc) {
    String guarantee = "";
    if (desc.contains("Bảo hành: <")) {
      int startIndex = desc.indexOf("Bảo hành:");
      String tmp = desc.substring(startIndex);
      startIndex = tmp.indexOf(">") + 1;
      tmp = tmp.substring(startIndex);
      int endIndex = tmp.indexOf("</span>");
      guarantee = tmp.substring(0, endIndex).trim();

    } else if (desc.contains("Bảo hành:")) {
      int startIndex = desc.indexOf("Bảo hành:") + 9;
      String tmp = desc.substring(startIndex);
      int endIndex = tmp.indexOf("<");
      guarantee = tmp.substring(0, endIndex).trim();
    }
    return guarantee;
  }

  private static String getType(String desc) {
    String type = "";
    if (desc.contains("Loại sản phẩm: </")) {
      return type;
    }
    if (desc.contains("Loại sản phẩm: <")) {
      int startIndex = desc.indexOf("Loại sản phẩm:");
      String tmp = desc.substring(startIndex);
      startIndex = tmp.indexOf(">") + 1;

      tmp = tmp.substring(startIndex);
      int endIndex = tmp.indexOf("</span>");
      int tmpendIndex = tmp.indexOf("<");
      if (tmpendIndex > 0 && tmpendIndex < endIndex) {
        endIndex = tmpendIndex;
      }
      type = tmp.substring(0, endIndex).trim();

    } else if (desc.contains("Loại sản phẩm:")) {
      int startIndex = desc.indexOf("Loại sản phẩm:") + 14;
      String tmp = desc.substring(startIndex);
      int endIndex = tmp.indexOf("<");
      type = tmp.substring(0, endIndex).trim();
    }
    return type;
  }

}

class AllProducts {

  private static final ArrayList<Product> productList = new ArrayList<Product>();
  private static final Map<Integer, Integer> productDictionary = new HashMap();

  public static ArrayList<Product> getProductList() {
    return productList;
  }

  public static Map<Integer, Integer> getProductDictionary() {
    return productDictionary;
  }

  public static void addCategoryToProduct(int productId, int categoryId) {
    productList.get(productId - 1).addCategories(categoryId);
  }

  public static void setManufacturerIdToProduct(int productId, int manufacturerId) {
    productList.get(productId - 1).setManufacturer(manufacturerId);
  }

  public static void setPriceToProduct(int productId, int price) {
    productList.get(productId - 1).setPrice(price);
  }

  public static void addToProductList(Product e) {
    productList.add(e);
  }

  public static void product2DictionaryInit() {

    getProductList().forEach((product) -> {
      productDictionary.put(product.getPost_id(), product.getId());
    });
  }

  public static int getProductIdFromPostId(int post_id) {
    return productDictionary.get(post_id);
  }

}
