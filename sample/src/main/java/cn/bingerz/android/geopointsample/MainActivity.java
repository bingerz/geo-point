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

import cn.bingerz.android.geopoint.GeoPoint;

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
            }
        });
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
