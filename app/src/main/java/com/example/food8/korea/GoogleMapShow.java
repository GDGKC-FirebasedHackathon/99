package com.example.food8.korea;

/**
 * Created by hch on 2017-02-17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tozzim.korea.R;

public class GoogleMapShow extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String maps="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        maps = intent.getStringExtra("values");
        Log.i("mapx", maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*mMap = googleMap;
        Double mapx_int, mapy_int;
        String[] map_split = maps.split("!");
        Log.i("map_split[0]", map_split[0]);
        Log.i("map_split[1]", map_split[1]);
        mapx_int = Double.parseDouble(map_split[0]);
        mapy_int = Double.parseDouble(map_split[1]);

        // Add a marker in Sydney and move the camera
        Log.i("mapx_int", mapx_int+"");
        Log.i("mapy_int", mapy_int+"");
        //LatLng location = new LatLng(mapx_int, mapy_int);
        LatLng location = new LatLng(126.98, 37.56);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));*/

        mMap = googleMap;

        Double mapx_int, mapy_int;
        String[] map_split = maps.split("!");
        Log.i("map_split[0]", map_split[0]);
        Log.i("map_split[1]", map_split[1]);
        mapx_int = Double.parseDouble(map_split[0]);
        mapy_int = Double.parseDouble(map_split[1]);

        // Add a marker in Sydney and move the camera
        Log.i("mapx_int", mapx_int+"");
        Log.i("mapy_int", mapy_int+"");

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(37.555744, 126.970431);
        //LatLng location = new LatLng(mapx_int, mapy_int);
        LatLng location = new LatLng(mapy_int, mapx_int);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
        mMap.animateCamera(zoom);
    }
}