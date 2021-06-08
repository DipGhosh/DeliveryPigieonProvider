package com.dev.pigeonproviderapp.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

  //Intent IDCard
  public static String IDCARD_IMAGE = "IMAGE";
  public static String IDCARD_NAME = "NAME";
  public static String IDCARD_ID = "ID";
  public static String IDCARD_CITY="CITY";
  public static String IDCARD_PROVIDER_PHONE="PROVIDERPHONENUMBER";
  public static String IDCARD_COMPANY_PHONE="COMPANYPHONE";
  public static String IDCARD_COMPANY_EMAIL="COMPANYEMAIL";
  public static String IDCARD_PDF_LINK="PDF";

  //Documents Upload
  public static String ADDRESSPROOF_FONT = "Address Proof Front";
  public static String ADDRESSPROOF_BACK = "Address Proof Back";
  public static String ADDRESSPROOF_PAN = "PAN";
  public static String ADDRESSPROOF_OTHERS = "Others";
  public static String DELIVERYPARTNER_LINK = "https://play.google.com/store/apps/details?id=com.dev.pigeonproviderapp";

  //Documents ID
  public static int ADDRESS_FONT_ID = 1;
  public static int ADDRESS_BACK_ID = 2;
  public static int ADDRESS_PAN_ID = 3;
  public static int ADDRESS_OTHERS_ID = 4;
  public static int APP_VERSION=34;

  // Intent Result
  public static String EDIT_NAME = "EDIT_NAME";
  public static String EDIT_EMAIL = "EDIT_EMAIL";
  public static String EDIT_PIC = "EDIT_PIC";

  public static String FLATNAME_KEY="flatname";
  public static String REACHADDRESS_KEY="addresstoreach";

  public static String ADDRESS_KEY = "address";
  public static String TIME_KEY = "time";
  public static String COMMENT_KEY = "comment";
  public static String LAT_KEY = "lat";
  public static String LONG_KEY = "long";
  public static String DROPPOINT_TYPE="TYPE";

  //Point name
  public static String PICK_POINT_KEY="Pickup Point";
  public static String DROP_POINT_KEY="Drop Point";


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

  public static File saveBitmapToFileComprase(File file) {
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
      final int REQUIRED_SIZE = 15;

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

      selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream);

      return file;
    } catch (Exception e) {
      return null;
    }
  }

  public static long getMillis(String givenTime) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    try {
      Date mDate = sdf.parse(givenTime);
      long timeInMilliseconds = mDate.getTime();
      System.out.println("Date in milli :: " + timeInMilliseconds);
      return timeInMilliseconds;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static String getMyPrettyDate(long neededTimeMilis) {
    Calendar nowTime = Calendar.getInstance();
    Calendar neededTime = Calendar.getInstance();
    neededTime.setTimeInMillis(neededTimeMilis);

    if ((neededTime.get(Calendar.YEAR) == nowTime.get(Calendar.YEAR))) {

      if ((neededTime.get(Calendar.MONTH) == nowTime.get(Calendar.MONTH))) {

        if (neededTime.get(Calendar.DATE) - nowTime.get(Calendar.DATE) == 1) {
          //here return like "Tomorrow at 12:00"
          return "Tomorrow " + DateFormat.format("HH:mm aa", neededTime);

        } else if (nowTime.get(Calendar.DATE) == neededTime.get(Calendar.DATE)) {
          //here return like "Today at 12:00"
          return "Today " + DateFormat.format("HH:mm aa", neededTime);

        } else if (nowTime.get(Calendar.DATE) - neededTime.get(Calendar.DATE) == 1) {
          //here return like "Yesterday at 12:00"
          return "Yesterday " + DateFormat.format("HH:mm aa", neededTime);

        } else {
          //here return like "May 31, 12:00"
          return DateFormat.format("MMMM d, HH:mm aa", neededTime).toString();
        }

      } else {
        //here return like "May 31, 12:00"
        return DateFormat.format("MMMM d, HH:mm aa", neededTime).toString();
      }

    } else {
      //here return like "May 31 2010, 12:00" - it's a different year we need to show it
      return DateFormat.format("MMMM dd yyyy, HH:mm aa", neededTime).toString();
    }
  }


}
