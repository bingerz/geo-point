# Geo-Point

A library to determine if the geographic coordinates are within an area.
Using Ray Casting algorithm to realize the internal and external relations of points and polygons.

## Adding to your project
You should add this to your dependencies:

```groovy
implementation 'cn.bingerz.android:geopoint:1.0.5'
```

## Code Example 1

```java

    //Determine whether the current latitude and longitude in mainland China
    boolean result = GeoPoint.insideChina(latLng.getLatitude(), latLng.getLongitude());

```

## Code Example 2
```java

    ArrayList<Vector> region = new ArrayList<>();
    //At least three vectors
    region.add(vector1);
    region.add(vector2);
    region.add(vector3);

    boolean result = GeoPoint.insidePoint(region, latLng.getLatitude(), latLng.getLongitude());

```