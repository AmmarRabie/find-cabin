package com.custom.findcabine;

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
                "1,1","1,2","1,3","1,4",
                "2,1","2,2","2,3","2,4",
                "3,1","3,2","3,3","3,4",
                "4,1","4,2","4,3","4,4",};
        String[] addresses = {
                "ش-الامير من المساكن","ش-الشهيد من المساكن","ش-الرحمن من المساكن","ش-ابراهيم محمود من المساكن",
                "ش-ابوبكرالصديق مساكن","ش- محمد نافع  مساكن","ش-مسجد الرحمة المساكن","ش- حمد المساكن",
                "ش-السد المساكن","ش-عرابى المساكن","ش-محمد الصاوى المسامن","ش-حمز المساكن",
                "ش-برنشت من المساكن","ش-عواد الصناديلى من المساكن","ش-عمر فروق من المساكن","ش-مسجد الهدى من المساكن"
        };

        for (int i = 0; i < 10; i++) {
            Cabin currCabin = new Cabin(ids[i],addresses[i]);
            cabinesList.add(currCabin);
        }
        return cabinesList;
    }

}
