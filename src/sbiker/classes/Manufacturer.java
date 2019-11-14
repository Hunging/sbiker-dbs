/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbiker.classes;

import java.util.ArrayList; // import the ArrayList class

/**
 *
 * @author Admin
 */
public class Manufacturer {

  private int id;
  private String name;
  private ArrayList<String> products;
  
  public Manufacturer() {
  }

  public Manufacturer(int id, String name, ArrayList<String> products) {
    this.id = id;
    this.name = name;
    this.products = products;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<String> getProducts() {
    return products;
  }

  public void addProducts(String product) {
    this.products.add(product);
  }

}
