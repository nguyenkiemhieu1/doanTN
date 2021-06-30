package com.kia99.skyrestaurant.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kia99.skyrestaurant.Model.BinhluanModel;
import com.kia99.skyrestaurant.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDanhSachBinhLuan extends RecyclerView.Adapter<AdapterDanhSachBinhLuan.ViewHolder> {
    private Context context;
    int layout;
    List<BinhluanModel> binhluanModelList;

    public AdapterDanhSachBinhLuan(Context context, int layout, List<BinhluanModel> binhluanModelList) {
        this.context = context;
        this.layout = layout;
        this.binhluanModelList = binhluanModelList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleimg_odau_bl1;
        TextView txt_tieudebl1, txt_noidung_bl1, txt_chamdiembl1;
        RecyclerView recycler_customBl_HinhAnh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleimg_odau_bl1 = (CircleImageView) itemView.findViewById(R.id.circleimg_odau_bl1);
            txt_noidung_bl1 = (TextView) itemView.findViewById(R.id.txt_noidung_bl1);
            txt_tieudebl1 = (TextView) itemView.findViewById(R.id.txt_tieudebl1);
            txt_chamdiembl1 = (TextView) itemView.findViewById(R.id.txt_chamdiembl1);
            recycler_customBl_HinhAnh = (RecyclerView) itemView.findViewById(R.id.recycler_customBl_HinhAnh);

        }
    }


    @NonNull
    @Override
    public AdapterDanhSachBinhLuan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        AdapterDanhSachBinhLuan.ViewHolder viewHolder = new AdapterDanhSachBinhLuan.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDanhSachBinhLuan.ViewHolder holder, int position) {
        BinhluanModel binhluanModel = binhluanModelList.get(position);
        holder.txt_tieudebl1.setText(binhluanModel.getTieudebl());
        holder.txt_noidung_bl1.setText(binhluanModel.getNoidungbl());
        holder.txt_chamdiembl1.setText(binhluanModel.getChamdiem() + "");
        setHinhAnhBinhLuan(holder.circleimg_odau_bl1, binhluanModel.getThanhVienModels().getHinhanh());
        List<Bitmap> bitmapList = new ArrayList<>();
        for (String linkhinh : binhluanModel.getListHinhanh()) {
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    bitmapList.add(bitmap);
                    if(bitmapList.size() == binhluanModel.getListHinhanh().size()){

                        AdapterRecyclerViewHinhBL adapterRecyclerViewHinhBL = new AdapterRecyclerViewHinhBL(context, R.layout.custom_hinhbinhluan, bitmapList, binhluanModel, false);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
                        holder.recycler_customBl_HinhAnh.setLayoutManager(layoutManager);
                        holder.recycler_customBl_HinhAnh.setAdapter(adapterRecyclerViewHinhBL);
                        adapterRecyclerViewHinhBL.notifyDataSetChanged();
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        int soluongbl = binhluanModelList.size();
        if (soluongbl > 5) {
            return 5;

        } else {
            return binhluanModelList.size();
        }
    }

    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh) {
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }


}
