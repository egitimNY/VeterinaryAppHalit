package com.halit.veterinaryapphalit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.halit.veterinaryapphalit.Models.RegisterPojo;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.RestApi.ManagerAll;
import com.halit.veterinaryapphalit.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitOlActivity extends AppCompatActivity {
    private Button kayitOlButton;
    private EditText registerPassword,registerUserName,registerMailAddress;
    private TextView registerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        tanimla();
        registerToUser();
        changeActivity();
    }

    private void tanimla() {
        kayitOlButton = findViewById(R.id.kayitOLButon);
        registerPassword = findViewById(R.id.registerPassword);
        registerUserName = findViewById(R.id.registerUserName);
        registerMailAddress = findViewById(R.id.registerMailAddress);
        registerText = findViewById(R.id.registerText);
    }

    public void registerToUser(){

        kayitOlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = registerMailAddress.getText().toString();
                String userN = registerUserName.getText().toString();
                String pass = registerPassword.getText().toString();
                register(mail,userN,pass);

                delete();

            }
        });

    }

    public void changeActivity(){
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KayitOlActivity.this, GirisYapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void delete(){
        registerPassword.setText("");
        registerUserName.setText("");
        registerMailAddress.setText("");
    }


    public void register(String userMailAdress, String userName, String userPass){
        Call<RegisterPojo> req = ManagerAll.getInstance().kayitOl(userMailAdress,userName,userPass);
        req.enqueue(new Callback<RegisterPojo>() {

            @Override
            public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                if (response.body().isTf())
                {
                    Toast.makeText(getApplicationContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(KayitOlActivity.this, GirisYapActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterPojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }
}
