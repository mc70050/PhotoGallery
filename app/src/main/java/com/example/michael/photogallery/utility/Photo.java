package com.example.michael.photogallery.utility;


import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Photo {
    /*
     * Creates a image file as a JPG and save it to external file folder.
     * Timestamp is created as well and used as part of the file name.
     */
    public static File createImageFile(double latitude, double longitude, File storageDir) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + latitude + "_" + longitude + "_";
        File image = File.createTempFile(
                imageFileName,     /* prefix */
                ".jpg",     /* suffix */
                storageDir         /* directory */
        );

        return image;
    }

    public static Date photoGetDate(String name) {
        String[] texts = name.split("_");
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(texts[1]);
        } catch (Exception e) {
            Log.d("Exception: ", e.toString());
        }
        return date;
    }

    public static double[] photoGetLocation(String name) throws ParseException {
        String[] texts = name.split("_");
        double[] location = new double[2];
        location[0] = Double.parseDouble(texts[3]);
        location[1] = Double.parseDouble(texts[4]);
        return location;
    }
}
