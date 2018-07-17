package cn.bingerz.android.geopoint;

import java.util.List;

import cn.bingerz.android.geopoint.RayCasting.RayCasting;
import cn.bingerz.android.geopoint.RayCasting.Vector;
import cn.bingerz.android.geopoint.Region.ChinaMainland;
import cn.bingerz.android.geopoint.Utils.GeoPointUtil;

/**
 * Created by hanson on 16/3/18.
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
     *
     * @param region Region Area vertices
     * @param latitude
     * @param longitude
     * @return
     */
    public static boolean insidePoint(List<Vector> region, double latitude, double longitude) {
        return new RayCasting().insidePolygon(latitude, longitude, region);
    }

    /**
     * Calculate distance between two points in latitude and longitude taking into account
     * height difference.
     *
     * @param c1 Start point
     * @param c2 End point
     * @param a1 Start altitude in meters
     * @param a2 End altitude in meters
     * @return Distance in Meters
     */
    public static double distance(GeoCoordinate c1, GeoCoordinate c2, double a1, double a2) {
        if (c1 == null || c2 == null) {
            throw new IllegalArgumentException("GeoCoordinate is null.");
        }
        return GeoPointUtil.distance(c1.getLatitude(), c1.getLongitude(), c2.getLatitude(), c2.getLongitude(), a1, a2);
    }

    /**
     * Calculate distance between two points in latitude and longitude
     * @param c1 Start point
     * @param c2 End point
     * @return Distance in Meters
     */
    public static double distance(GeoCoordinate c1, GeoCoordinate c2) {
        return distance(c1, c2, 0.0, 0.0);
    }

    /**
     * Calculate the center point according to the coordinates
     *
     * @param geoCoordinateList
     * @return The center point coordinate
     */
    public static GeoCoordinate getCenterPoint(List<GeoCoordinate> geoCoordinateList) {
        int total = geoCoordinateList.size();
        double X = 0, Y = 0, Z = 0;
        for (GeoCoordinate g : geoCoordinateList) {
            double lat, lon, x, y, z;
            lat = g.getLatitude() * Math.PI / 180;
            lon = g.getLongitude() * Math.PI / 180;
            x = Math.cos(lat) * Math.cos(lon);
            y = Math.cos(lat) * Math.sin(lon);
            z = Math.sin(lat);
            X += x;
            Y += y;
            Z += z;
        }

        X = X / total;
        Y = Y / total;
        Z = Z / total;
        double Lon = Math.atan2(Y, X);
        double Hyp = Math.sqrt(X * X + Y * Y);
        double Lat = Math.atan2(Z, Hyp);
        return new GeoCoordinate(Lat * 180 / Math.PI, Lon * 180 / Math.PI);
    }

    /**
     * Convert Earth coordinate system(WGS-84) to Mars coordinate system(GCJ-02)
     */
    public static GeoCoordinate WGS84ToGCJ02(GeoCoordinate coordinate) {
        if (coordinate == null) {
            return null;
        }
        double[] result = GeoPointUtil.WGS84ToGCJ02(coordinate.getLatitude(), coordinate.getLongitude());
        return new GeoCoordinate(result[0], result[1]);
    }

    /**
     * Convert Mars coordinate system(GCJ-02) to Earth coordinate system(WGS-84)
     */
    public static GeoCoordinate GCJ02ToWGS84(GeoCoordinate coordinate) {
        if (coordinate == null) {
            return null;
        }
        double[] result = GeoPointUtil.GCJ02ToWGS84(coordinate.getLatitude(), coordinate.getLongitude());
        return new GeoCoordinate(result[0], result[1]);
    }
}
