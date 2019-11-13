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
public class Category {

  private final int id;
  private final String term_id;

  public Category(int id, String term_id) {
    this.id = id;
    this.term_id = term_id;
  }

  public int getId() {
    return id;
  }

  public String getTerm_id() {
    return term_id;
  }

}
