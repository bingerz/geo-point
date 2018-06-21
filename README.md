# InteriorPoint

A library to determine if the geographic coordinates are within an area.
Using Ray Casting algorithm to realize the internal and external relations of points and polygons.

## Code Example 1

```java

    //Determine whether the current latitude and longitude in mainland China
    boolean result = InteriorPoint.insideChina(latLng.getLatitude(), latLng.getLongitude());

```

## Code Example 2
```java

    ArrayList<Vector> region = new ArrayList<>();
    //At least three vectors
    region.add(vector1);
    region.add(vector2);
    region.add(vector3);

    boolean result = InteriorPoint.interiorPoint(region, latLng.getLatitude(), latLng.getLongitude());

```