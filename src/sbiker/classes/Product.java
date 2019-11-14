/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbiker.classes;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Product {

  private final int id;
  private final String images;
  private ArrayList<Integer> categories;
  private int manufacturer;
  private String guarantee;

  public Product(int id, String images) {
    this.id = id;
    this.images = images;
  }

  public Product(int id, String images, ArrayList<Integer> categories, int manufacturer, String guarantee) {
    this.id = id;
    this.images = images;
    this.categories = categories;
    this.manufacturer = manufacturer;
    this.guarantee = guarantee;
  }

}
