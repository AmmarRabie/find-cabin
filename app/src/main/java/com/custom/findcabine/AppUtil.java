package com.custom.findcabine;

import android.content.Context;
import android.content.SharedPreferences;

import com.custom.findcabine.data.CabineContract;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by AmmarRabie on 22/03/2018.
 */

public class AppUtil {
    private AppUtil() {
    }


    public static ArrayList<Cabin> insertInitialCabins(Context context) {

        SharedPreferences defaultListOptions =
                context.getSharedPreferences("default_list_options", Context.MODE_PRIVATE);
        boolean is_inserted = defaultListOptions.getBoolean("is_inserted", false);

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
        if (!is_inserted)
            insertList(context, cabinesList);
        return cabinesList;
    }

    public static void addLocationToList(ArrayList<Cabin> list, LatLng location) {

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setLocation(location);
        }
    }


    private static void insertList(Context context, ArrayList<Cabin> cabinesList) {
        // [Optimization] TODO: don't loop over the list use bulkInsert() instead

        for (int i = 0; i < cabinesList.size(); i++) {
            Cabin thisCabin = cabinesList.get(i);
            context.getContentResolver().
                    insert(CabineContract.CabineEntry.CONTENT_URI, thisCabin.toContentValues());
        }

        context.getSharedPreferences("default_list_options", Context.MODE_PRIVATE).
                edit().putBoolean("is_inserted", true).apply();
    }
}
