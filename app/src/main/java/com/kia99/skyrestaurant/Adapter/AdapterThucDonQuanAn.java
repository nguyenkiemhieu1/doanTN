package com.kia99.skyrestaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Model.ThucDonModel;
import com.kia99.skyrestaurant.R;

import java.util.List;

public class AdapterThucDonQuanAn extends RecyclerView.Adapter<AdapterThucDonQuanAn.ViewHolderThucDon> {
    Context context;
    List<ThucDonModel> thucDonModelList;

    public AdapterThucDonQuanAn(Context context, List<ThucDonModel> thucDonModelList) {
        this.context = context;
        this.thucDonModelList = thucDonModelList;
    }

    @NonNull
    @Override
    public ViewHolderThucDon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_thucdon, parent, false);
        return new ViewHolderThucDon(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderThucDon holder, int position) {

        ThucDonModel thucDonModel = thucDonModelList.get(position);
        holder.txt_TenThucDon.setText("+" + thucDonModel.getTenthucdon());

        holder.recycler_DanhSach_MonAn.setLayoutManager(new LinearLayoutManager(context));

        AdapterDanhSachMonAn adapterDanhSachMonAn = new AdapterDanhSachMonAn(context, thucDonModel.getMonAnModelList());

        holder.recycler_DanhSach_MonAn.setAdapter(adapterDanhSachMonAn);
        adapterDanhSachMonAn.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return thucDonModelList.size();
    }

    public class ViewHolderThucDon extends RecyclerView.ViewHolder {
        TextView txt_TenThucDon;
        RecyclerView recycler_DanhSach_MonAn;

        public ViewHolderThucDon(@NonNull View itemView) {
            super(itemView);
            txt_TenThucDon = (TextView) itemView.findViewById(R.id.txt_TenThucDon);
            recycler_DanhSach_MonAn  = (RecyclerView) itemView.findViewById(R.id.recycler_DanhSach_MonAn);
        }
    }
}
