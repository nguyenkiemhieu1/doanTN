package com.kia99.skyrestaurant.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.kia99.skyrestaurant.R;

public class khoiPhucPasswordActivity extends AppCompatActivity implements  View.OnClickListener{
    TextView txt_dangkimoi, edt_username_KP;
    Button btn_xacnhan;
    EditText edt_username;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quenpassword);

        mAuth = FirebaseAuth.getInstance();


        init();
    }

    private void init() {
        edt_username_KP = (TextView) findViewById(R.id.edt_username_KP);
        txt_dangkimoi = (TextView) findViewById(R.id.txt_dangkimoi);
        btn_xacnhan = (Button) findViewById(R.id.btn_guimail_XN);
        edt_username = (EditText)findViewById(R.id.edt_username);

        txt_dangkimoi.setOnClickListener(this);
        btn_xacnhan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.txt_dangkimoi:
                startActivity(new Intent(khoiPhucPasswordActivity.this, Dangki_Activity .class));
                break;
            case R.id.btn_guimail_XN:
                String kiemtraEmail = edt_username.getText().toString();
                if(KiemtraEmail(kiemtraEmail)){
                    mAuth.sendPasswordResetEmail(kiemtraEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(khoiPhucPasswordActivity.this, getString(R.string.thongbaothanhcong), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(this, getString(R.string.emailkhonghople), Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    private boolean KiemtraEmail(String Email){
       return Patterns.EMAIL_ADDRESS.matcher(Email).matches();

    }
}
