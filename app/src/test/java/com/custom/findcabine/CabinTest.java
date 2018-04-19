package com.custom.findcabine;

import com.google.android.gms.maps.model.LatLng;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by AmmarRabie on 22/03/2018.
 */
public class CabinTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isValid() throws Exception {
        Cabin c = new Cabin(cableId);
        boolean result = c.isValid();
        assertEquals(result, false);

        c.setName("jkfjngk");
        c.setAddress("jkfjngk");
        c.setLocation(new LatLng(564, 6546));

        c.setCabinId("a2D");
        result = c.isValid();
        assertEquals(result, false);

        c.setCabinId("548");
        result = c.isValid();
        assertEquals(result, true);


        c.setCabinId("54as");
        result = c.isValid();
        assertEquals(result, false);

        c.setCabinId("21395");
        result = c.isValid();
        assertEquals(result, false);

    }

}