package com.kia99.skyrestaurant.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kia99.skyrestaurant.Controller.odau.ThucDon_Interrface;

import java.util.ArrayList;
import java.util.List;

public class ThucDonModel {
    private String mathucdon;
    private String tenthucdon;
    List<MonAnModel> monAnModelList;

    public List<MonAnModel> getMonAnModelList() {
        return monAnModelList;
    }

    public void setMonAnModels(List<MonAnModel> monAnModels) {
        this.monAnModelList = monAnModels;
    }

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }

    public void getDanhSachThucDonTheoMa(String maquanan, ThucDon_Interrface thucDon_interrface) {

        DatabaseReference nodeThucDonQuanAN = FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maquanan);
        nodeThucDonQuanAN.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {

                final List<ThucDonModel> thucDonModelList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ThucDonModel thucDonModel = new ThucDonModel();

                    DatabaseReference nodeThucDon = FirebaseDatabase.getInstance().getReference().child("thucdons").child(dataSnapshot.getKey());
                    nodeThucDon.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotthucdon) {

                            String mathucdon = snapshotthucdon.getKey();
                            thucDonModel.setMathucdon(mathucdon);
                            thucDonModel.setTenthucdon(snapshotthucdon.getValue(String.class));
                            List<MonAnModel> monAnModels = new ArrayList<>();

                            for (DataSnapshot valueMonAn : snapshot.child(mathucdon).getChildren()){
                               MonAnModel monAnModel = valueMonAn.getValue(MonAnModel.class);
                               monAnModel.setMamon(valueMonAn.getKey());
                               monAnModels.add(monAnModel);
                            }
                            thucDonModel.setMonAnModels(monAnModels);
                            thucDonModelList.add(thucDonModel);
                            thucDon_interrface.getThucDonThanhCong(thucDonModelList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
