package com.halit.veterinaryapphalit.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.halit.veterinaryapphalit.Models.LoginModel;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.RestApi.ManagerAll;
import com.halit.veterinaryapphalit.Utils.GetSharedPreferences;
import com.halit.veterinaryapphalit.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GirisYapActivity extends AppCompatActivity {

    private EditText loginMailAdres, loginPassword;
    private TextView loginText;
    private Button loginButton;

    public static final String TERMS_AND_CONDITIONS = "TERMS_AND_CONDITIONS";
    public static final String PLANT_PLACES_PREFS = "PLANT_PLACES_PREFS";
//    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);

        tanimla();
        click();

        SharedPreferences sharedPreferences = getSharedPreferences(PLANT_PLACES_PREFS, Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(TERMS_AND_CONDITIONS, false)) {
            TermsAndConditionsDialogFragment tsandcs = new TermsAndConditionsDialogFragment();
            Dialog dialog = tsandcs.onCreateDialog(savedInstanceState);
            dialog.show();
            ((TextView) dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public class TermsAndConditionsDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(GirisYapActivity.this);
            builder.setCancelable(false);
//            builder.setNegativeButton("Cancel",null);
            builder.setMessage(Html.fromHtml("By using this app, you agree to our <a href=\"http://halitpractice.tech/legal/term.php\">Terms and Conditions</a> and <a href=\"http://halitpractice.tech/legal/policy.php\"> Privacy Policy</a>"))
                    .setPositiveButton("I agree", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //
                            SharedPreferences prefs = GirisYapActivity.this.getSharedPreferences(PLANT_PLACES_PREFS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = prefs.edit();
                            edit.putBoolean(TERMS_AND_CONDITIONS, true);
                            edit.commit();
                        }
                    });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            return builder.create();
        }
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
                   GetSharedPreferences getSharedPreferences = new GetSharedPreferences(GirisYapActivity.this);
                   getSharedPreferences.setSession(response.body().getId(),response.body().getUsername(),response.body().getMailadres());
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
