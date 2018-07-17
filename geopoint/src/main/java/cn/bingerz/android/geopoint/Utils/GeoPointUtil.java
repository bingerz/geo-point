package cn.bingerz.android.geopoint.Utils;

public class GeoPointUtil {

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2,
                                  double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * Convert Earth coordinate system(WGS-84) to Mars coordinate system(GCJ-02)
     */
    public static double[] WGS84ToGCJ02(double latitude, double longitude) {
        double[] latLng = new double[2];
        double[] delta = delta(latitude, longitude);
        latLng[0] = latitude + delta[0];
        latLng[1] = longitude + delta[1];
        return latLng;
    }

    /**
     * Convert Mars coordinate system(GCJ-02) to Earth coordinate system(WGS-84)
     */
    public static double[] GCJ02ToWGS84(double latitude, double longitude) {
        double[] latLng = new double[2];
        double[] delta = delta(latitude, longitude);
        latLng[0] = latitude - delta[0];
        latLng[1] = longitude - delta[1];
        return latLng;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * @return delta[0] 是纬度差，delta[1]是经度差
     */
    private static double[] delta(double lat, double lng) {
        double[] delta = new double[2];
        double a = 6378245.0;
        double ee = 0.00669342162296594323;
        double dLat = transformLat(lng - 105.0, lat - 35.0);
        double dLng = transformLon(lng - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        delta[0] = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        delta[1] = (dLng * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
        return delta;
    }
}
