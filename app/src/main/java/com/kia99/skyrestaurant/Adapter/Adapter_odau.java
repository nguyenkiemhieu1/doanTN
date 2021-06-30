package com.kia99.skyrestaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kia99.skyrestaurant.Model.BinhluanModel;
import com.kia99.skyrestaurant.Model.ChiNhanhQuanAnModel;
import com.kia99.skyrestaurant.Model.QuanAnModels;
import com.kia99.skyrestaurant.R;
import com.kia99.skyrestaurant.View.activity.ChiTietQuanAnActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_odau extends RecyclerView.Adapter<Adapter_odau.ViewHolder> {
    List<QuanAnModels> quanAnModelsList;
    int resource;
    Context context;

    public Adapter_odau(Context context, List<QuanAnModels> quanAnModelsList, int resource) {
        this.context = context;
        this.quanAnModelsList = quanAnModelsList;
        this.resource = resource;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button custom_fragmentodau_datmon;
        TextView txt_custom_odau_tenquanan, txt_custom_khoangcach_odau, txt_custom_odau_diachi,  txt_noidung_bl2, txt_diemTbquanan, txt_tieudebl2, txt_noidung_bl1, txt_tieudebl1, txt_chamdiem1, txt_tonghinhbl, txt_chamdiem2, txt_sl_bl;
        ImageView img_custom_fragmentodau;
        CircleImageView circleimg_odau_bl1, circleimg_odau_bl2;
        LinearLayout containerBl, containerbl2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            custom_fragmentodau_datmon = (Button) itemView.findViewById(R.id.btn_custom_fragmentodau_datmon);
            
            img_custom_fragmentodau = (ImageView) itemView.findViewById(R.id.img_custom_fragmentodau);

            txt_custom_odau_tenquanan = (TextView) itemView.findViewById(R.id.txt_custom_odau_tenquanan);
            txt_noidung_bl1 = (TextView) itemView.findViewById(R.id.txt_noidung_bl1);
            txt_noidung_bl2 = (TextView) itemView.findViewById(R.id.txt_noidung_bl2);
            txt_tieudebl1  = (TextView) itemView.findViewById(R.id.txt_tieudebl1);
            txt_tieudebl2  = (TextView) itemView.findViewById(R.id.txt_tieudebl2);
            txt_chamdiem1 = (TextView) itemView.findViewById(R.id.txt_chamdiembl1);
            txt_chamdiem2 = (TextView) itemView.findViewById(R.id.txt_chamdiembl2);
            txt_sl_bl = (TextView)  itemView.findViewById(R.id.txt_sl_bl);
            txt_tonghinhbl = (TextView) itemView.findViewById(R.id.txt_tonghinhbl);
            txt_diemTbquanan = (TextView) itemView.findViewById(R.id.txt_diemTbquanan);
            txt_custom_odau_diachi = (TextView) itemView.findViewById(R.id.txt_custom_odau_diachi);
            txt_custom_khoangcach_odau = (TextView) itemView.findViewById(R.id.txt_custom_khoangcach_odau);

            circleimg_odau_bl1 = (CircleImageView) itemView.findViewById(R.id.circleimg_odau_bl1);
            circleimg_odau_bl2 = (CircleImageView) itemView.findViewById(R.id.circleimg_odau_bl2);

            containerBl = (LinearLayout) itemView.findViewById(R.id.containerBl1);
            containerbl2 = (LinearLayout) itemView.findViewById(R.id.containerBl2);

        }
    }

    @NonNull
    @Override
    public Adapter_odau.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_odau.ViewHolder holder, int position) {
        QuanAnModels quanAnModels = quanAnModelsList.get(position);

        holder.txt_custom_odau_tenquanan.setText(quanAnModels.getTenquanan());
        if (quanAnModels.isGiaohang()) {
            holder.custom_fragmentodau_datmon.setVisibility(View.VISIBLE);
        }
        if(quanAnModels.getBitmapList().size() > 0){

            holder.img_custom_fragmentodau.setImageBitmap(quanAnModels.getBitmapList().get(0));
        }


        if(quanAnModels.getBinhluanModelList().size() > 0 && quanAnModels.getBinhluanModelList().size() < 2){
            BinhluanModel binhluanModel = quanAnModels.getBinhluanModelList().get(0);
            holder.txt_tieudebl1.setText(binhluanModel.getTieudebl());
            holder.txt_noidung_bl1.setText(binhluanModel.getNoidungbl());
            holder.txt_chamdiem1.setText(binhluanModel.getChamdiem() + "");
            setHinhAnhBinhLuan(holder.circleimg_odau_bl1, binhluanModel.getThanhVienModels().getHinhanh());
            holder.containerbl2.setVisibility(View.GONE);

            if(quanAnModels.getBinhluanModelList().size() > 2){
                BinhluanModel binhluanModel2 = quanAnModels.getBinhluanModelList().get(1);
                holder.txt_tieudebl2.setText(binhluanModel2.getTieudebl());
                holder.txt_noidung_bl2.setText(binhluanModel2.getNoidungbl());

                holder.txt_chamdiem2.setText(binhluanModel2.getChamdiem() + "");
                setHinhAnhBinhLuan(holder.circleimg_odau_bl2, binhluanModel.getThanhVienModels().getHinhanh());
            }
            holder.txt_sl_bl.setText(quanAnModels.getBinhluanModelList().size() + "");

            int tongslbinhluan = 0;
            double tongdiemQuanAn = 0;
            for(BinhluanModel binhluanModel1 : quanAnModels.getBinhluanModelList()){
                tongslbinhluan += binhluanModel1.getListHinhanh().size();
                tongdiemQuanAn += binhluanModel.getChamdiem();
            }

            double diemtb = tongdiemQuanAn/quanAnModels.getBinhluanModelList().size();
            holder.txt_diemTbquanan.setText(String.format("%.2f", diemtb));
            if(tongslbinhluan > 0 ) {
                holder.txt_tonghinhbl.setText(tongslbinhluan + "");

            }

        }else {
            holder.containerBl.setVisibility(View.GONE);
            holder.containerbl2.setVisibility(View.GONE);

        }
// lấy chi nhánh quán ăn và hiển thị địa chỉ quán ăn theo km
        if(quanAnModels.getChiNhanhQuanAnModelList().size() > 0){
            ChiNhanhQuanAnModel chiNhanhQuanAnModelTAM = quanAnModels.getChiNhanhQuanAnModelList().get(0);
            for(ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModels.getChiNhanhQuanAnModelList()){
                if(chiNhanhQuanAnModelTAM.getKhoangCach() > chiNhanhQuanAnModel.getKhoangCach()){
                    chiNhanhQuanAnModelTAM = chiNhanhQuanAnModel;
                }
            }
            holder.txt_custom_odau_diachi.setText(chiNhanhQuanAnModelTAM.getDiachi());

            holder.txt_custom_khoangcach_odau.setText(String.format("%.1f",chiNhanhQuanAnModelTAM.getKhoangCach()) + " km");
        }

        holder.txt_custom_odau_tenquanan.setOnClickListener(new View.OnClickListener() {    
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChiTietQuanAnActivity.class);
                intent.putExtra("quanan", quanAnModels);
                context.startActivity(intent);
            }
        });
    }
    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quanAnModelsList.size();
    }


}
