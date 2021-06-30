package com.kia99.skyrestaurant.View.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kia99.skyrestaurant.Controller.dangki.DangKyController;
import com.kia99.skyrestaurant.Controller.odau.controllerThongTin;
import com.kia99.skyrestaurant.Model.ModelThongTin;
import com.kia99.skyrestaurant.Model.ThanhVienModels;
import com.kia99.skyrestaurant.R;

public class Dangki_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_dangki;
    private FirebaseAuth mAuth;
    EditText edt_username, edt_password, edt_password_again;
    ProgressDialog  progressDialog;

    DangKyController dangKyController;
    controllerThongTin controllerThongTin;
    Toolbar toolbar_DK;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangki);
        init();
        mAuth = FirebaseAuth.getInstance();
        toolbar_DK = (Toolbar) findViewById(R.id.toolbar_DK);

        toolbar_DK.setTitle("");
        setSupportActionBar(toolbar_DK);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void init() {
        progressDialog = new ProgressDialog(this);

        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_password_again = (EditText) findViewById(R.id.edt_password_again);
        btn_dangki = findViewById(R.id.btn_dangki);
        btn_dangki.setOnClickListener(this);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        progressDialog.setMessage(getString(R.string.dangxl));
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        String password_again = edt_password_again.getText().toString();
        String thongbaoloi = getString(R.string.thongbaoloidk);

        if (username.trim().length() == 0) {
            thongbaoloi += " "+ getString(R.string.Email);
            Toast.makeText(Dangki_Activity.this, thongbaoloi, Toast.LENGTH_SHORT).show();
        } else if (password_again.trim().length() == 0) {
            thongbaoloi +=" "+ getString(R.string.Password);
            Toast.makeText(Dangki_Activity.this, thongbaoloi, Toast.LENGTH_SHORT).show();
        } else if (!password_again.equals(password)) {
            Toast.makeText(this, " "+getString(R.string.thongbaoonhaplaiPW), Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();

                        String userID = task.getResult().getUser().getUid();

                        ThanhVienModels thanhVienModels = new ThanhVienModels();
                        thanhVienModels.setHoten(username.toString());
                        thanhVienModels.setHinhanh("user2.png");

                        ModelThongTin modelThongTins = new ModelThongTin();
                        modelThongTins.setHoten("");
                        modelThongTins.setDiachi("");
                        modelThongTins.setHinhanh("user2.png");
                        modelThongTins.setSdt("");

                        dangKyController = new DangKyController();
                        dangKyController.Themthongtinthanhvien(thanhVienModels, userID);

                        controllerThongTin = new controllerThongTin();
                        controllerThongTin.ThemThongTin(modelThongTins, userID);


                        Toast.makeText(Dangki_Activity.this, getString(R.string.dangkithanhcong), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
