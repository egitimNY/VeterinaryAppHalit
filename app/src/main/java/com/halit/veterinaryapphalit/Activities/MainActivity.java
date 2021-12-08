package com.halit.veterinaryapphalit.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.halit.veterinaryapphalit.Fragments.HomeFragment;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.Utils.ChangeFragments;
import com.halit.veterinaryapphalit.Utils.GetSharedPreferences;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences getSharedPreferences;
    private GetSharedPreferences preferences;
    private MaterialButton anasayfaButton;
    private ChangeFragments changeFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragment();
        tanimla();
        kontrol();
        action();
    }

    private void action() {
        anasayfaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new HomeFragment());
            }
        });
    }

    private void getFragment() {
        changeFragments = new ChangeFragments(MainActivity.this);
        changeFragments.change(new HomeFragment());
    }

    private void tanimla() {
        preferences = new GetSharedPreferences(MainActivity.this);
        getSharedPreferences = preferences.getSession();
        anasayfaButton = findViewById(R.id.anasayfaButton);
    }

    public void kontrol(){
        if (getSharedPreferences.getString("id", null)==null && getSharedPreferences.getString("mailadress",null)==null
                && getSharedPreferences.getString("username",null)==null){
            Intent intent = new Intent(MainActivity.this,GirisYapActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
