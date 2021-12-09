package com.halit.veterinaryapphalit.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.halit.veterinaryapphalit.Models.AsiModel;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.RestApi.ManagerAll;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi, container, false);
        tanimla();
        getAsi();
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

    }

    public void getAsi(){
        Call<List<AsiModel>> req = ManagerAll.getInstance().getAsi("30");
        req.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {
                if (response.isSuccessful()){
                    asiList = response.body();
                    for (int i = 0; i < asiList.size(); i++) {
                        Log.i( "asilar",response.body().toString());
                        String dataString = response.body().get(i).getAsitarih().toString();
                        try {
                            Date date = format.parse(dataString);
                            dateList.add(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    calendarPickerView.init(today,nextYear.getTime()).withHighlightedDates(dateList);

                }

            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {

            }
        });
    }
}
