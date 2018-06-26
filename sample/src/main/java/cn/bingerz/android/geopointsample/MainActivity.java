package cn.bingerz.android.geopointsample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import cn.bingerz.android.geopoint.GeoCoordinate;
import cn.bingerz.android.geopoint.GeoPoint;
import cn.bingerz.android.geopoint.Utils.GeoPointUtil;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                mapboxMap.setOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng point) {
                        mapboxMap.clear();
                        LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                        boolean result = GeoPoint.insideChina(latLng.getLatitude(), latLng.getLongitude());
                        String text = "Inside China:" + result;
                        result = GeoPoint.outOfChina(latLng.getLatitude(), latLng.getLongitude());
                        text = text + "\n" + "OutOf China:" + result;
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(text)
                        );
                        ((TextView) findViewById(R.id.tv_hint)).setText(text);
                    }
                });
                showCenterPoint(mapboxMap);
            }
        });
    }

    private void showCenterPoint(MapboxMap mapboxMap) {
        if (mapboxMap != null) {
            GeoCoordinate gc1 = new GeoCoordinate(39.932302, 116.344055);
            GeoCoordinate gc2 = new GeoCoordinate(39.932875, 116.373184);
            GeoCoordinate gc3 = new GeoCoordinate(39.916099, 116.373871);
            List<GeoCoordinate> geoCoordinates1 = new ArrayList<>();
            List<GeoCoordinate> geoCoordinates2 = new ArrayList<>();
            List<GeoCoordinate> geoCoordinates3 = new ArrayList<>();
            geoCoordinates1.add(gc2);
            geoCoordinates1.add(gc1);
            geoCoordinates2.add(gc3);
            geoCoordinates2.add(gc2);
            geoCoordinates3.add(gc3);
            geoCoordinates3.add(gc2);
            geoCoordinates3.add(gc1);
            GeoCoordinate center1 = GeoPointUtil.getCenterPoint(geoCoordinates1);
            GeoCoordinate center2 = GeoPointUtil.getCenterPoint(geoCoordinates2);
            GeoCoordinate center3 = GeoPointUtil.getCenterPoint(geoCoordinates3);
            center1 = GeoPoint.GCJ02ToWGS84(center1);
            center2 = GeoPoint.GCJ02ToWGS84(center2);
            center3 = GeoPoint.GCJ02ToWGS84(center3);
            LatLng latLng1 = new LatLng(center1.getLatitude(), center1.getLongitude());
            LatLng latLng2 = new LatLng(center2.getLatitude(), center2.getLongitude());
            LatLng latLng3 = new LatLng(center3.getLatitude(), center3.getLongitude());

            mapboxMap.addMarker(new MarkerOptions()
                    .position(latLng1)
                    .title("center1")
            );
            mapboxMap.addMarker(new MarkerOptions()
                    .position(latLng2)
                    .title("center2")
            );
            mapboxMap.addMarker(new MarkerOptions()
                    .position(latLng3)
                    .title("center3")
            );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
}
