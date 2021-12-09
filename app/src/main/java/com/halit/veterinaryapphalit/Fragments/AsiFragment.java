package com.halit.veterinaryapphalit.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.halit.veterinaryapphalit.Models.AsiModel;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.RestApi.ManagerAll;
import com.halit.veterinaryapphalit.Utils.ChangeFragments;
import com.halit.veterinaryapphalit.Utils.GetSharedPreferences;
import com.squareup.picasso.Picasso;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiFragment extends Fragment {

    private View view;
    private CalendarPickerView calendarPickerView;
    private DateFormat format;
    private Calendar nextYear;
    private Date today;
    private List<AsiModel> asiList;
    private List<Date> dateList;
    private String id;
    private GetSharedPreferences getSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi, container, false);
        tanimla();
        getAsi();
        clickToCalendar();
        return view;
    }

    public void tanimla(){
        calendarPickerView = view.findViewById(R.id.calendarPickerView);
        format = new SimpleDateFormat("dd/MM/yyyy");
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        today = new Date();

        calendarPickerView.init(today,nextYear.getTime());
        asiList = new ArrayList<>();
        dateList = new ArrayList<>();
        getSharedPreferences = new GetSharedPreferences(getActivity());
        id = getSharedPreferences.getSession().getString("id",null);

    }

    public void getAsi(){
        Call<List<AsiModel>> req = ManagerAll.getInstance().getAsi(id);
        req.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).isTf()) {
                        asiList = response.body();
                        for (int i = 0; i < asiList.size(); i++) {
//                            Log.i("asilar", response.body().toString());
                            String dataString = response.body().get(i).getAsitarih().toString();
                            try {
                                Date date = format.parse(dataString);
                                dateList.add(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        calendarPickerView.init(today, nextYear.getTime()).withHighlightedDates(dateList);

                    }
                }else {
                    ChangeFragments changeFragments = new ChangeFragments(getContext());
                    changeFragments.change(new HomeFragment());
                    Toast.makeText(getContext(), "Petinize Ait Gelecek Tarihte Asi Yoktur...", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {

            }
        });
    }
      public void clickToCalendar(){
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                for (int i = 0; i <dateList.size(); i++ ){

                    if (date.toString().equals(dateList.get(i).toString())){
//                        Toast.makeText(getContext(), asiList.get(i).getPetisim().toString(), Toast.LENGTH_LONG).show();
                        openQuestionAlert(asiList.get(i).getPetisim(),asiList.get(i).getPettur(),asiList.get(i).getAsitarih(), asiList.get(i).getAsiisim(),asiList.get(i).getPetresim());
                    }

                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
      }

    public void openQuestionAlert(String petIsmi,String petTur,String tarihim,String asiIsmi, String resimUrl){
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.asi_takip_layout,null);

        TextView petIsimText = view.findViewById(R.id.petIsimText);
        TextView petAsiTakipBilgiText = view.findViewById(R.id.petAsiTakipBilgiText);
        CircleImageView asiTakipCircleImageImageView = view.findViewById(R.id.asiTakipCircleImageView);

        petIsimText.setText(petIsmi);
//        petAsiTakipBilgiText.setText(petIsmi + " isimli petinizin "+ tarihim + " tarihinde " + asiIsmi + " asisi yapilacaktir");
        petAsiTakipBilgiText.setText(petIsmi + " isimli " + petTur + " " + tarihim + " tarihinde " + asiIsmi + " asisi yapilacaktir");

        Picasso.get().load(resimUrl).into(asiTakipCircleImageImageView);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        alertDialog.show();

    }
}
