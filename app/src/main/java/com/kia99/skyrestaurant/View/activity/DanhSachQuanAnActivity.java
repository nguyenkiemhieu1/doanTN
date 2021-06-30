package com.kia99.skyrestaurant.View.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Controller.odau.Controller_odau;
import com.kia99.skyrestaurant.R;

public class DanhSachQuanAnActivity  extends AppCompatActivity {
    Controller_odau controller_odau;
    RecyclerView recyclerviewOdau;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    NestedScrollView nestedScrollView;
    Toolbar tb;
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setContentView(R.layout.layout_quanly_quanan);
    }


//        tb = (Toolbar)findViewById(R.id.tb);
//        tb.setTitle("Danh sách quán ăn");
//        setSupportActionBar(tb);
//
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        recyclerviewOdau = (RecyclerView) view.findViewById(R.id.recy_fragment_);
//        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
//        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScro);
//
//        sharedPreferences  = getSharedPreferences("toado", Context.MODE_PRIVATE);
//        Location vitrihientai = new Location("");
//        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
//        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
//
//        controller_odau = new Controller_odau(this);
//        controller_odau.getDanhSachQuanAnControllerAdmin(this,nestedScrollView,recyclerviewOdau, progressBar, vitrihientai);
//
//    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}
