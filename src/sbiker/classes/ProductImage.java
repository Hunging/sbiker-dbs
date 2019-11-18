/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbiker.classes;

/**
 *
 * @author Admin
 */
public class ProductImage {

  private int id;
  private int post_id;
  private int new_product_id;
  private int image_post_id;
  private String name;
  private String image;

  public ProductImage(int id, int post_id, int image_id) {
    this.id = id;
    this.post_id = post_id;
    this.image_post_id = image_id;
  }

  public ProductImage(int id, int post_id, String name, String image) {
    this.id = id;
    this.post_id = post_id;
    this.name = name;
    this.image = image;
  }

  public int getNew_product_id() {
    return new_product_id;
  }

  public void setNew_product_id(int new_image_id) {
    this.new_product_id = new_image_id;
  }

  public int getImage_post_id() {
    return image_post_id;
  }

  public void setImage_post_id(int image_id) {
    this.image_post_id = image_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPost_id() {
    return post_id;
  }

  public void setPost_id(int post_id) {
    this.post_id = post_id;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

}
