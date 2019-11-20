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
import transferdbdata.classes.Category;
import static transferdbdata.utils.ConnectionMySql.getAllCategories;

/**
 *
 * @author Admin
 */
public class CategoryUtil {

  public static void processCategory() {
    try {
      ResultSet rs = getAllCategories();
      int index = 1;
      while (rs.next()) {

        AllCategories.addToCategoryList(new Category(index, rs.getString(1), rs.getString(2), rs.getString(3), new ArrayList<>()));
        index++;
      }
//      AllCategories.getCategoryList().forEach((Category temp) -> {
//
//        System.out.println(temp.getId() + "           " + temp.getName() + "         " + temp.getTerm_id());
//      });

      AllCategories.categoryDictionaryInit();
      ConnectionMySql.closeConnection();

    } catch (SQLException ex) {
      Logger.getLogger(CategoryUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}

class AllCategories {

  private static final ArrayList<Category> categoryList = new ArrayList<Category>();
  private static final Map<Integer, Integer> categoryDictionary = new HashMap();

  public static ArrayList<Category> getCategoryList() {
    return categoryList;
  }

  public static int getCategoryIdFromTermId(int term_id) {
    return categoryDictionary.get(term_id);
  }

  public static Map getCategoryDictionary() {
    return categoryDictionary;
  }

  public static void addToCategoryList(Category e) {
    categoryList.add(e);
  }

  public static void categoryDictionaryInit() {

    getCategoryList().forEach((category) -> {
      categoryDictionary.put(Integer.parseInt(category.getTerm_id().trim()), category.getId());
    });
  }

}
