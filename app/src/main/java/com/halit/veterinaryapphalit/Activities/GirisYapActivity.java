package com.halit.veterinaryapphalit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.halit.veterinaryapphalit.Models.LoginModel;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.RestApi.ManagerAll;
import com.halit.veterinaryapphalit.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GirisYapActivity extends AppCompatActivity {

    private EditText loginMailAdres, loginPassword;
    private TextView loginText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);

        tanimla();
        click();
    }

    public void tanimla(){
        loginMailAdres = findViewById(R.id.loginMailAddress);
        loginPassword = findViewById(R.id.loginPassword);
        loginText = findViewById(R.id.loginText);
        loginButton = findViewById(R.id.loginButon);
    }

    public void click(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = loginMailAdres.getText().toString();
                String pass = loginPassword.getText().toString();
                login(mail,pass);
                delete();

            }
        });
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GirisYapActivity.this, KayitOlActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void delete(){
        loginMailAdres.setText("");
        loginPassword.setText("");
    }

    public void login(String mailAdres,String parola){
       Call<LoginModel> req = ManagerAll.getInstance().girisYap(mailAdres,parola);

       req.enqueue(new Callback<LoginModel>() {
           @Override
           public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
               if (response.body().isTf()) {
                   Toast.makeText(getApplicationContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(GirisYapActivity.this,MainActivity.class);
                   startActivity(intent);
                   finish();
               }else {
                   Toast.makeText(getApplicationContext(), response.body().getText(), Toast.LENGTH_LONG).show();

               }

           }

           @Override
           public void onFailure(Call<LoginModel> call, Throwable t) {
               Toast.makeText(getApplicationContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
           }
       });
    }



}
