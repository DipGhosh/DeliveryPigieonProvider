package com.dev.pigeonproviderapp.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Utility {

  public static String DEVICE_NAME = "ANDROID";
  public static String DEVICE_TYPE = "android";
  public static int USERTYPE = 2;

  //Webservices Link
  public static String TERMSSERVICES_LINK ="http://deliverypigeon.in/terms-of-service";
  public static String PRIVACYPOLICY_LINK ="http://deliverypigeon.in/privacy-policy";
  public static String ABOUTUS_LINK ="http://deliverypigeon.in/about";

  //Webservices HEADER
  public static String PRIVACY_POLICY_HEADER = "Privacy Policy";
  public static String ABOUTUS_HEADER = "About Us";
  public static String TERMRS_OF_SERVICE_HEADER = "Terms Of Services";

  //Intent Key
  public static String HEADER_KEY = "HEADER_VAL";
  public static String LINK_KEY = "LINK_VAL";

  //Documents Upload
  public static String ADDRESSPROOF_FONT = "Address Proof Front";
  public static String ADDRESSPROOF_BACK = "Address Proof Back";
  public static String ADDRESSPROOF_PAN = "PAN";
  public static String ADDRESSPROOF_OTHERS = "Others";

  //Documents ID
  public static int ADDRESS_FONT_ID = 1;
  public static int ADDRESS_BACK_ID = 2;
  public static int ADDRESS_PAN_ID = 3;
  public static int ADDRESS_OTHERS_ID = 4;

  // Intent Result
  public static String EDIT_NAME = "EDIT_NAME";
  public static String EDIT_EMAIL = "EDIT_EMAIL";
  public static String EDIT_PIC = "EDIT_PIC";

  public static File saveBitmapToFile(File file) {
    try {

      // BitmapFactory options to downsize the image
      BitmapFactory.Options o = new BitmapFactory.Options();
      o.inJustDecodeBounds = true;
      o.inSampleSize = 6;
      // factor of downsizing the image

      FileInputStream inputStream = new FileInputStream(file);
      //Bitmap selectedBitmap = null;
      BitmapFactory.decodeStream(inputStream, null, o);
      inputStream.close();

      // The new size we want to scale to
      final int REQUIRED_SIZE = 75;

      // Find the correct scale value. It should be the power of 2.
      int scale = 1;
      while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
              o.outHeight / scale / 2 >= REQUIRED_SIZE) {
        scale *= 2;
      }

      BitmapFactory.Options o2 = new BitmapFactory.Options();
      o2.inSampleSize = scale;
      inputStream = new FileInputStream(file);

      Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
      inputStream.close();

      // here i override the original image file
      file.createNewFile();
      FileOutputStream outputStream = new FileOutputStream(file);

      selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

      return file;
    } catch (Exception e) {
      return null;
    }
  }

}
