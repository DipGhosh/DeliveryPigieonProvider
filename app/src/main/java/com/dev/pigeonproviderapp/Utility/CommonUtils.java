package com.dev.pigeonproviderapp.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;


import com.dev.pigeonproviderapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CommonUtils {
    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public static File createDirectoryAndSaveFile(Bitmap imageToSave, String imageName, final Context context) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File file = new File(path, imageName);

        try {
            path.mkdirs();
            @SuppressLint("ResourceType") InputStream is = context.getResources().openRawResource(R.drawable.background_image);
            OutputStream os = new FileOutputStream(file);
            byte[] data = new byte[is.available()];
            is.read(data);
            os.write(data);
            is.close();
            os.close();
            MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null, (path1, uri) -> {
                Log.i("ExternalStorage", "Scanned " + path1 + ":");
                Log.i("ExternalStorage", "-> uri=" + uri);
            });
        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing " + file, e);
        }

        try {
            final FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;

    }
}
