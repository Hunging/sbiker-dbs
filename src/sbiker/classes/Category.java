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
public class Category {

  private final int id;
  private final String term_id;
  private String name;
  private String slug;
  private final ArrayList<String> products;

  public Category(int id, String term_id, String name, String slug, ArrayList<String> products) {
    this.id = id;
    this.term_id = term_id;
    this.name = name;
    this.slug = slug;
    this.products = products;
  }

  public ArrayList<String> getProducts() {
    return products;
  }

  public void addProducts(String product) {
    this.products.add(product);
  }

  public int getId() {
    return id;
  }

  public String getTerm_id() {
    return term_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

}
