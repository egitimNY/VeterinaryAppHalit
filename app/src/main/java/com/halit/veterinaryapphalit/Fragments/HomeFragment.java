package com.halit.veterinaryapphalit.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.Utils.ChangeFragments;

public class HomeFragment extends Fragment {

    private View view;
    private LinearLayout petlerimLayout;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        tanimla();
        action();
        return view;
    }

    public void tanimla(){
        petlerimLayout = view.findViewById(R.id.petlerimLayout);
        changeFragments = new ChangeFragments(getContext());
    }

    public void action(){
        petlerimLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new UserPetsFragment());
            }
        });
    }


}
