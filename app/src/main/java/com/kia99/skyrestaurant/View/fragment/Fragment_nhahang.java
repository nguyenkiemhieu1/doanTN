package com.kia99.skyrestaurant.View.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Controller.odau.Controller_odau;
import com.kia99.skyrestaurant.R;

public class Fragment_nhahang extends Fragment {
    Controller_odau controller_odau;
    RecyclerView recyclerviewOdau;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    NestedScrollView nestedScrollView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_quanly_quanan, container, false);
        recyclerviewOdau = (RecyclerView) view.findViewById(R.id.recy_fragment_odau);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarODau);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollViewOdau);

        sharedPreferences  = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai = new Location("");
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));

        controller_odau = new Controller_odau(getActivity());
        controller_odau.getDanhSachQuanAnControllerAdmin(getContext(),nestedScrollView,recyclerviewOdau, progressBar, vitrihientai);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
