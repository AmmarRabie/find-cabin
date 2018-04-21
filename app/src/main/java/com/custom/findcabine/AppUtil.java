package com.custom.findcabine;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by AmmarRabie on 22/03/2018.
 */

public class AppUtil {
    private AppUtil() {
    }


    public static ArrayList<Cabin> getInitialCabins() {
        ArrayList<Cabin> cabinesList = new ArrayList<>();
        String[] ids = {
                "1,1", "1,2", "1,3", "1,4",
                "2,1", "2,2", "2,3", "2,4",
                "3,1", "3,2", "3,3", "3,4",
                "4,1", "4,2", "4,3", "4,4",


                "5,1", "5,2", "5,3", "5,4",         // diff ids all are Copper
                "6,1", "6,2", "6,3", "6,4",         // diff ids all are Copper
                "7,1", "7,2", "7,3", "7,4",         // diff ids all are Copper
                "8,1", "8,2", "8,3", "8,4",         // diff ids all are Copper


                "5,1", "5,2", "5,3", "5,4",         // same ids all are Fiber
                "6,1", "6,2", "6,3", "6,4",         // same ids all are Fiber
                "7,1", "7,2", "7,3", "7,4",         // same ids all are Fiber
                "8,1", "8,2", "8,3", "8,4",         // same ids all are Fiber

        };
        String[] addresses = {
                "ش-الامير من المساكن", "ش-الشهيد من المساكن", "ش-الرحمن من المساكن", "ش-ابراهيم محمود من المساكن",
                "ش-ابوبكرالصديق مساكن", "ش- محمد نافع  مساكن", "ش-مسجد الرحمة المساكن", "ش- حمد المساكن",
                "ش-السد المساكن", "ش-عرابى المساكن", "ش-محمد الصاوى المسامن", "ش-حمز المساكن",
                "ش-برنشت من المساكن", "ش-عواد الصناديلى من المساكن", "ش-عمر فروق من المساكن", "ش-مسجد الهدى من المساكن",


                "ش-برنشت من المساكن", "ش-عواد الصناديلى من المساكن", "ش-عمر فروق من المساكن", "ش-مسجد الهدى من المساكن",
                "ش-برنشت من المساكن", "ش-عواد الصناديلى من المساكن", "ش-عمر فروق من المساكن", "ش-مسجد الهدى من المساكن",
                "ش-برنشت من المساكن", "ش-عواد الصناديلى من المساكن", "ش-عمر فروق من المساكن", "ش-مسجد الهدى من المساكن",
                "ش-برنشت من المساكن", "ش-عواد الصناديلى من المساكن", "ش-عمر فروق من المساكن", "ش-مسجد الهدى من المساكن",

                "ش-برنشت من المساكن", "ش-عواد الصناديلى من المساكن", "ش-عمر فروق من المساكن", "ش-مسجد الهدى من المساكن",
                "ش-برنشت من المساكن", "ش-عواد الصناديلى من المساكن", "ش-عمر فروق من المساكن", "ش-مسجد الهدى من المساكن",
                "ش-برنشت من المساكن", "ش-عواد الصناديلى من المساكن", "ش-عمر فروق من المساكن", "ش-مسجد الهدى من المساكن",
                "ش-برنشت من المساكن", "ش-عواد الصناديلى من المساكن", "ش-عمر فروق من المساكن", "ش-مسجد الهدى من المساكن",

        };
        
        CableType[] types = {
                CableType.COPPER, CableType.FIBER, CableType.COPPER, CableType.FIBER,
                CableType.FIBER, CableType.FIBER, CableType.FIBER, CableType.COPPER,
                CableType.FIBER, CableType.COPPER, CableType.COPPER, CableType.COPPER,
                CableType.COPPER, CableType.COPPER, CableType.FIBER, CableType.FIBER,


                CableType.FIBER, CableType.FIBER, CableType.FIBER, CableType.FIBER,
                CableType.FIBER, CableType.FIBER, CableType.FIBER, CableType.FIBER,
                CableType.FIBER, CableType.FIBER, CableType.FIBER, CableType.FIBER,
                CableType.FIBER, CableType.FIBER, CableType.FIBER, CableType.FIBER,


                CableType.COPPER, CableType.COPPER, CableType.COPPER, CableType.COPPER,
                CableType.COPPER, CableType.COPPER, CableType.COPPER, CableType.COPPER,
                CableType.COPPER, CableType.COPPER, CableType.COPPER, CableType.COPPER,
                CableType.COPPER, CableType.COPPER, CableType.COPPER, CableType.COPPER,
                
        };

        LatLng[] locations = {
                new LatLng(324, 646), new LatLng(43, 465), new LatLng(564, 646), new LatLng(654, 467),
                new LatLng(564, 564), new LatLng(876, 546), new LatLng(876, 2315), new LatLng(5646, 676),
                new LatLng(319, 753), new LatLng(462, 46), new LatLng(762, 128), new LatLng(5465, 564),
                new LatLng(138, 456), new LatLng(7654, 46), new LatLng(648, 133), new LatLng(2146, 467),


                new LatLng(138, 456), new LatLng(7654, 46), new LatLng(648, 133), new LatLng(2146, 467),
                new LatLng(138, 456), new LatLng(7654, 46), new LatLng(648, 133), new LatLng(2146, 467),
                new LatLng(138, 456), new LatLng(7654, 46), new LatLng(648, 133), new LatLng(2146, 467),
                new LatLng(138, 456), new LatLng(7654, 46), new LatLng(648, 133), new LatLng(2146, 467),

                new LatLng(138, 456), new LatLng(7654, 46), new LatLng(648, 133), new LatLng(2146, 467),
                new LatLng(138, 456), new LatLng(7654, 46), new LatLng(648, 133), new LatLng(2146, 467),
                new LatLng(138, 456), new LatLng(7654, 46), new LatLng(648, 133), new LatLng(2146, 467),
                new LatLng(138, 456), new LatLng(7654, 46), new LatLng(648, 133), new LatLng(2146, 467),

        };

        for (int i = 0; i < ids.length; i++) {
            Cabin currCabin = new Cabin(ids[i], addresses[i], locations[i], types[i]);
            cabinesList.add(currCabin);
        }
        return cabinesList;
    }

}
