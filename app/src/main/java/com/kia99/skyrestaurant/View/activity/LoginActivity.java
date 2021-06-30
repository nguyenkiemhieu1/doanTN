package com.kia99.skyrestaurant.View.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.kia99.skyrestaurant.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, FirebaseAuth.AuthStateListener {
    private static final String TAG = "GoogleActivity";
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    //    CallbackManager callbackManager;
    private static int CHECK_AUTH_PROVIDER_LOGIN = 0;
    Button btn_dangnhap;

    TextView Forgt_password, Registration, Forgot_password;
    EditText edt_username, edt_password;

    SignInButton sign_in_btn;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.layout_login);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        sharedPreferences = getSharedPreferences("luudangnhap", Context.MODE_PRIVATE);
        init();

    }

    private void init() {
        Registration = (TextView) findViewById(R.id.Registration);
        btn_dangnhap = (Button) findViewById(R.id.btn_dangnhap);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        Forgot_password = (TextView) findViewById(R.id.Forgot_password);

        Forgot_password.setOnClickListener(this);
        btn_dangnhap.setOnClickListener(this);
        Registration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Registration:
                startActivity(new Intent(LoginActivity.this, Dangki_Activity.class));
                break;
            case R.id.btn_dangnhap:
                LoginEmailAndPassword();
                break;
            case R.id.Forgot_password:
                startActivity(new Intent(LoginActivity.this, khoiPhucPasswordActivity.class));
                break;
        }

    }

    private void LoginEmailAndPassword() {
        String Email = edt_username.getText().toString();
        String Password = edt_password.getText().toString();

        if (edt_username.getText().toString().equals("aaaa@gmail.com")&&edt_password.getText().toString().equals("123456")) {
            mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Bạn đã đăng nhập với trạng thái là Admin", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, TrangChuAdminActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.kiemtradangnhap), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {

            mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(LoginActivity.this, TrangChuActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.kiemtradangnhap), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mauser", user.getUid());
            editor.commit();
            editor.apply();

            Intent iTrangchu = new Intent(this, TrangChuActivity.class);
            startActivity(iTrangchu);
        }

    }

}


