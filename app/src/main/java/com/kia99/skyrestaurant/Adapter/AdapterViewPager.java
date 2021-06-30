package com.kia99.skyrestaurant.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kia99.skyrestaurant.View.fragment.Fragment_angi;
import com.kia99.skyrestaurant.View.fragment.Fragment_odau;

public class AdapterViewPager extends FragmentStatePagerAdapter {
    Fragment_angi fragment_angi;
    Fragment_odau fragment_odau;
    public AdapterViewPager(@NonNull FragmentManager fm) {
        super(fm);
        fragment_angi = new Fragment_angi();
        fragment_odau = new Fragment_odau();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return fragment_odau;

            case 1:
                return fragment_angi;
            default: return  null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
