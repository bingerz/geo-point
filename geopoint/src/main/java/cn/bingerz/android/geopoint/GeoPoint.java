package cn.bingerz.android.geopoint;

import java.util.ArrayList;

import cn.bingerz.android.geopoint.Region.ChinaMainland;

/**
 * Created by hanbing on 16/3/18.
 */
public class GeoPoint {

    public static boolean insideChina(double latitude, double longitude) {
        return interiorPoint(ChinaMainland.getVectors(), latitude, longitude);
    }

    public static boolean interiorPoint(ArrayList<Vector> region, double latitude, double longitude) {
        return new RayCasting().insidePolygon(latitude, longitude, region);
    }
}
