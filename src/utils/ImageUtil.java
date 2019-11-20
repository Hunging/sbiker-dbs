/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import sbiker.classes.ProductImage;

/**
 *
 * @author Admin
 */
public class ImageUtil {

  public static void processImageAllinOne() {
    processImageID();
    processImageLink();
    mapImage2Product();
    AllSubImages.getImageList().forEach((image) -> {
//      System.out.println(image.getId() + "    " + image.getImage());
//      try {
//        saveImage(image.getImage());
//      } catch (IOException ex) {
//        System.out.println(ex);
//        Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
//
//      }
    });

  }

  public static void saveImage(String imageUrl) throws IOException {
    URL url = new URL(imageUrl);
    Image image = ImageIO.read(url);
    String fileName = url.getFile();
    fileName = "/figures" + fileName.substring(fileName.lastIndexOf('/'));
    String type = fileName.substring(fileName.lastIndexOf(".") + 1);

    ImageIO.write(toBufferedImage(image), type, new File(fileName));

  }

  public static BufferedImage toBufferedImage(Image img) {
    if (img instanceof BufferedImage) {
      return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
  }

  public static void processImageID() {
    ResultSet rs = ConnectionMySql.getAllImageId();

    int index = 0;
    try {
      while (rs.next()) {

        int post_id = rs.getInt(1);
        String listinString = rs.getString(2);
        String[] imageArray = listinString.trim().split(",");

        for (String tmp : imageArray) {
          index++;
          ProductImage image = new ProductImage(index, post_id, Integer.parseInt(tmp));
          AllSubImages.getImageList().add(image);

        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static void mapImage2Product() {
    AllSubImages.getImageList().forEach((tmpImage) -> {
      int product_post_id = tmpImage.getPost_id();
      int productId = AllProducts.getProductIdFromPostId(product_post_id);
      tmpImage.setNew_product_id(productId);
    });
  }

  public static void processImageLink() {
    ResultSet rs = ConnectionMySql.getAllImageLink();

    try {

      while (rs.next()) {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String link = rs.getString(3);
        for (ProductImage image : AllSubImages.getImageList()) {

          if (image.getImage_post_id() == id) {

            String newLink = StringUtils.url2Slug(link);
            image.setName(name);
            image.setImage(newLink);
          }
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}

class AllSubImages {

  private static final ArrayList<ProductImage> imageList = new ArrayList<ProductImage>();

  public static ArrayList<ProductImage> getImageList() {
    return imageList;
  }

  public static void add2ImageList(ProductImage e) {
    imageList.add(e);
  }
}

class StringUtils {

  private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
  private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

  public static String toSlug(String input) {
    String newinput = input.replace("đ", "d");
    newinput = newinput.replace("_", "-");
    String nowhitespace = WHITESPACE.matcher(newinput).replaceAll("-");
    String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
    String slug = NONLATIN.matcher(normalized).replaceAll("");
    return slug.toLowerCase(Locale.ENGLISH);
  }

  public static String url2Slug(String url) {
    String newLink = url.substring(url.lastIndexOf("/") + 1);
    String fileType = newLink.substring(newLink.lastIndexOf("."));
    newLink = toSlug(newLink.substring(0, newLink.length() - fileType.length()));
    newLink += fileType;
    return newLink;
  }
}
