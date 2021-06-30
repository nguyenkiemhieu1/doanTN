package com.kia99.skyrestaurant.View.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kia99.skyrestaurant.Controller.DanDuongQuanAnController;
import com.kia99.skyrestaurant.R;

public class DuongDiQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    private double latitude;
    private double longitude;

    SharedPreferences sharedPreferences;
    Location vitrihientai;
    String duongdan = "";

    DanDuongQuanAnController danDuongQuanAnController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danduongquanan);

        danDuongQuanAnController = new DanDuongQuanAnController();

        latitude = getIntent().getDoubleExtra("Latitude", 0);
        longitude = getIntent().getDoubleExtra("Longitude", 0);

        sharedPreferences  = getSharedPreferences("toado", Context.MODE_PRIVATE);
        vitrihientai = new Location("");
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","o")));
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","o")));

        duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin=" + vitrihientai.getLatitude() + "," + vitrihientai.getLongitude() + "&destination=" +latitude+"," + longitude + "&language=vi&key=AIzaSyBXdgIYQxEkX7B0PfgFoMbz_WsaJouKpuM";
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
//AIzaSyCOvcCLbFPOHllhV3FjTUPh-UpTxB0NqHQ
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(vitrihientai.getLatitude(), vitrihientai.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);

        LatLng vitriquanan = new LatLng(latitude, longitude);
        MarkerOptions markerOptionsVitriquanan = new MarkerOptions();
        markerOptionsVitriquanan.position(vitriquanan);
        googleMap.addMarker(markerOptionsVitriquanan);


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
        danDuongQuanAnController.HienThiDuongDiQuanAn(googleMap, duongdan);
    }
}
