package com.halit.veterinaryapphalit.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.halit.veterinaryapphalit.Fragments.HomeFragment;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.Utils.ChangeFragments;
import com.halit.veterinaryapphalit.Utils.GetSharedPreferences;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences getSharedPreferences;
    private GetSharedPreferences preferences;
    private ChangeFragments changeFragments;
    private ImageView cikisYap,aramaYapButton,anasayfaButton;
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
        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSharedPreferences getSharedPreferences = new GetSharedPreferences(MainActivity.this);
                getSharedPreferences.deleteToSession();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        aramaYapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:00000000000"));
                startActivity(intent);
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
        cikisYap = findViewById(R.id.cikisYap);
        aramaYapButton = findViewById(R.id.aramaYapButton);
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
