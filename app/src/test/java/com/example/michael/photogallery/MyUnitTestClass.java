package com.example.michael.photogallery;

import com.example.michael.photogallery.utility.Photo;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyUnitTestClass {

    @Test
    public void testPhotoGetDate () throws Exception {
        String test = "ThisIsATest_20190101_the_rest_is_not_important";
        Date date = new SimpleDateFormat("yyyyMMdd").parse("20190101");
        Date testDate = Photo.photoGetDate(test);
        Assert.assertEquals(date, testDate);
    }

    @Test
    public void testPhotoGetLocation() throws Exception {
        String test = "ThisIsATest_14930394394_344431243_49.22211_-123.3344_345434_2334_@#43";
        double[] loc = Photo.photoGetLocation(test);
        Assert.assertEquals(49.22211, loc[0], 0.001);
    }
}
