package example.com.tripplanner;
import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    ArrayList<NearByPlace> nearByPlaceArrayList1 = new ArrayList<>();
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 111;
    private MarkerOptions options = new MarkerOptions();
    ArrayList<Polyline> polylineArrayList = new ArrayList<>();
    ArrayList<Marker> markerArrayList = new ArrayList<>();
    PolylineOptions polylineOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));

        mapFragment.getMapAsync(this);

        nearByPlaceArrayList1 = (ArrayList<NearByPlace>) getIntent().getExtras().getSerializable("latlon");

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Location Permission is Required")
                        .setMessage("The application needs the Location permission")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    protected Marker createMarker(double latitude, double longitude, String title) {
        Marker m = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(title));
        markerArrayList.add(m);
        return m;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        polylineOptions = new PolylineOptions()
                .width(5)
                .color(Color.BLUE)
                .geodesic(true);

        double l1 = nearByPlaceArrayList1.get(0).getLat();
        double l2 = nearByPlaceArrayList1.get(0).getLon();
        LatLng latLng = new LatLng(l1,l2);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        for (int i = 0; i < nearByPlaceArrayList1.size(); i++) {
            createMarker(nearByPlaceArrayList1.get(i).getLat(), nearByPlaceArrayList1.get(i).getLon(), nearByPlaceArrayList1.get(i).getNearby_place());

        }
//        drawingManager.addListener('polylinecomplete', function(poly) {
//            var path = poly.getPath();
//            polylines.push(poly);
//            placeIdArray = [];
//            runSnapToRoad(path);
//        });

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markerArrayList) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
            }
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}

//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        for (int i = 0; i < nearByPlaceArrayList1.size(); i++) {
//            double l11 = nearByPlaceArrayList1.get(i).getLat();
//            double l22 = nearByPlaceArrayList1.get(i).getLon();
//            LatLng latLng1 = new LatLng(l11,l22);
//            //polylineOptions.add(new LatLng(l11, l22));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(l11, l22), 15));
//            //polylineArrayList.add(mMap.addPolyline(polylineOptions));
//            //builder.include(latLng1);
//        }



//        CameraUpdate camUpdate1 = CameraUpdateFactory.newLatLngBounds(bounds, 30);
//        googleMap.moveCamera(camUpdate1);


//        for (int i = 0; i < polylineArrayList.get(polylineArrayList.size() - 1).getPoints().size(); i++) {
//            builder.include(polylineArrayList.get(polylineArrayList.size() - 1).getPoints().get(i));
//        }
//        LatLngBounds bounds1 = builder.build();
//        CameraUpdate camUpdate = CameraUpdateFactory.newLatLngBounds(bounds1, 80);
//mMap.animateCamera(camUpdate);
//      private ClusterManager<MyItem> mClusterManager;
//        //Method 1:
//        ArrayList<Marker> markerArrayList = new ArrayList<Marker>();
//        for (int i = 0; i < nearByPlaceArrayList1.size(); i++) {
//            LatLng place = new LatLng(nearByPlaceArrayList1.get(i).getLat(), nearByPlaceArrayList1.get(i).getLon());
//            markerArrayList.add(mMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(nearByPlaceArrayList1.get(i).getLat(), nearByPlaceArrayList1.get(i).getLon()))
//                    .title("Marker in " + " " + nearByPlaceArrayList1.get(i).getNearby_place())));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
//        }

// Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//


//      LatLng point = new LatLng(nearByPlaceArrayList1.get(0).getLat(), nearByPlaceArrayList1.get(0).getLon());
//            options.position(point);
//            options.title(nearByPlaceArrayList1.get(0).getNearby_place());
//            options.snippet("someDesc");
//            googleMap.addMarker(options);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
//
//        nearByPlaceArrayList1.get(1).setLat(47.0);
//        nearByPlaceArrayList1.get(1).setLon(-122.0);
//        LatLng point1 = new LatLng(nearByPlaceArrayList1.get(1).getLat(), nearByPlaceArrayList1.get(1).getLon());
//        options.position(point);
//        options.title(nearByPlaceArrayList1.get(1).getNearby_place());
//        options.snippet("someDesc");
//        googleMap.addMarker(options);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(point1));



//        ArrayList<Marker> markerArrayList = new ArrayList<>();
//
//        LatLng point = new LatLng(nearByPlaceArrayList1.get(0).getLat(), nearByPlaceArrayList1.get(0).getLon());
//
//        markerArrayList.add(mMap.addMarker(new MarkerOptions()
//                .position(point)
//                .title(nearByPlaceArrayList1.get(0).getNearby_place())
//                .draggable(true)));
//        markerArrayList.get(0).setTag(0);
//        markerArrayList.get(0).showInfoWindow();
//
//        LatLng point1 = new LatLng(nearByPlaceArrayList1.get(1).getLat(), nearByPlaceArrayList1.get(1).getLon());
//
//        markerArrayList.add(mMap.addMarker(new MarkerOptions()
//                .position(point1)
//                .title(nearByPlaceArrayList1.get(1).getNearby_place())
//                .draggable(true)));
//        markerArrayList.get(1).setTag(1);
//        markerArrayList.get(1).showInfoWindow();



    //    private void setUpClusterer() {
//        // Position the map.
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));
//
//        // Initialize the manager with the context and the map.
//        // (Activity extends context, so we can pass 'this' in the constructor.)
//        mClusterManager =  new ClusterManager<MyItem>(this, mMap);
//
//        // Point the map's listeners at the listeners implemented by the cluster
//        // manager.
//        mMap.setOnCameraIdleListener(mClusterManager);
//        mMap.setOnMarkerClickListener(mClusterManager);
//
//        // Add cluster items (markers) to the cluster manager.
//        addItems();
//    }
//
//    private void addItems() {
//        mClusterManager =  new ClusterManager<MyItem>(this, mMap);
//        for (int i = 0; i < nearByPlaceArrayList1.size(); i++) {
//            double lat = nearByPlaceArrayList1.get(i).getLat();
//            double lng =  nearByPlaceArrayList1.get(i).getLon();
//            MyItem offsetItem = new MyItem(lat, lng, nearByPlaceArrayList1.get(i).getNearby_place(),"Place1");
//            mClusterManager.addItem(offsetItem);
//        }
//    }



