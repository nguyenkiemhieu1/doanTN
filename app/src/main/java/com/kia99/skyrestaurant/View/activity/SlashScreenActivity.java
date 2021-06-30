package com.kia99.skyrestaurant.View.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.kia99.skyrestaurant.R;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class SlashScreenActivity extends AppCompatActivity {
    TextView txtv_version, txt_loading, txt_CT;
    private FusedLocationProviderClient fusedLocationClient;
    private SharedPreferences sharedPreferences;

    public static final int REQUEST_PERMISSION_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_slashscreen);

        FirebaseAuth.getInstance().signOut();

        init();
        sharedPreferences  = getSharedPreferences("toado", MODE_PRIVATE);
        getLastLocation();

    }

    private void init() {
        txtv_version = (TextView) findViewById(R.id.txtv_version);
        txt_loading = findViewById(R.id.txt_loading);
        txt_CT = findViewById(R.id.txt_CT);

    }
    private void show(){
        try {
            PackageInfo fPackageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//            txtv_version.setText(getString(R.string.phienban) + " " + fPackageInfo.versionName);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SlashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, 2000);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        txt_loading.setText(R.string.Loading);
        txt_CT.setText(R.string.nameCT);

    }
    public void getLastLocation() {
        int checkPermissionCoarseLocaltion = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int checkPermissionCoarseLocaltion1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkPermissionCoarseLocaltion != PackageManager.PERMISSION_GRANTED && checkPermissionCoarseLocaltion1 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        } else {
            fusedLocationClient = getFusedLocationProviderClient(this);
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            Log.d("kiemtratoado", location.getLatitude() + " ");
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("latitude", String.valueOf(location.getLatitude()));
                            edit.putString("longitude", String.valueOf(location.getLongitude()));
                            edit.commit();
                        }
                        show();
                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    fusedLocationClient = getFusedLocationProviderClient(this);
                }
                break;
        }
    }
}