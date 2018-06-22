package cn.bingerz.android.geopoint;

import java.util.ArrayList;

import cn.bingerz.android.geopoint.Region.ChinaMainland;

/**
 * Created by hanbing on 16/3/18.
 */
public class GeoPoint {

    /**
     * This method is only used to quickly determine if the point is outside of China.
     * If it returns false, this point is not necessarily inside China.
     */
    public static boolean outOfChina(double latitude, double longitude) {
        if (longitude < 72.004 || longitude > 137.8347)
            return true;
        if (latitude < 0.8293 || latitude > 55.8271)
            return true;
        return false;
    }

    /**
     * This method is only used to accurately determine if the point is within China.
     */
    public static boolean insideChina(double latitude, double longitude) {
        if (outOfChina(latitude, longitude)) {
            return false;
        }
        return insidePoint(ChinaMainland.getVectors(), latitude, longitude);
    }

    /**
     * This method is only used to determine if the point is within a certain boundary
     * @param region Region Area vertices
     * @param latitude
     * @param longitude
     * @return
     */
    public static boolean insidePoint(ArrayList<Vector> region, double latitude, double longitude) {
        return new RayCasting().insidePolygon(latitude, longitude, region);
    }
}
