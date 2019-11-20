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
  private final int post_id;
  private final String name;
  private String description;
  private String redundency;
  private String status;
  private String size;
  private String type;
  private int price;
  private String short_description;
  private final String images;
  private ArrayList<Integer> categories;
  private int manufacturer;
  private String guarantee;

  public Product(int id, int post_id, String name, String description, String short_description, String images) {
    this.id = id;
    this.post_id = post_id;
    this.name = name;
    this.description = description;
    this.short_description = short_description;
    this.images = images;
    this.categories = new ArrayList<>();
    this.price = 0;
  }

  public Product(int id, int post_id, String name, String description, String redundency, String status, String size, String type, int price, String short_description, String images, ArrayList<Integer> categories, int manufacturer, String guarantee) {
    this.id = id;
    this.post_id = post_id;
    this.name = name;
    this.description = description;
    this.redundency = redundency;
    this.status = status;
    this.size = size;
    this.type = type;
    this.price = price;
    this.short_description = short_description;
    this.images = images;
    this.categories = categories;
    this.manufacturer = manufacturer;
    this.guarantee = guarantee;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getGuarantee() {
    return guarantee;
  }

  public void setGuarantee(String guarantee) {
    this.guarantee = guarantee;
  }

  public String getName() {
    return name;
  }

  public int getPost_id() {
    return post_id;
  }

  public ArrayList<Integer> getCategories() {
    return categories;
  }

  public void addCategories(int categoryId) {
    this.categories.add(categoryId);
  }

  public int getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(int manufacturer) {
    this.manufacturer = manufacturer;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRedundency() {
    return redundency;
  }

  public void setRedundency(String redundency) {
    this.redundency = redundency;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getShort_description() {
    return short_description;
  }

  public void setShort_description(String short_description) {
    this.short_description = short_description;
  }

  public String getImages() {
    return images;
  }

}
