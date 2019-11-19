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
import java.util.ArrayList;
import javax.imageio.ImageIO;
import sbiker.classes.ProductImage;

/**
 *
 * @author Admin
 */
public class ImageUtil {

  public static void processImageAllinOne() {
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

