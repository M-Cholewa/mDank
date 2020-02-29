package com.example.mbank.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbank.R;
import com.example.mbank.views.ArcBalanceView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceArcFragment extends Fragment {

    //private TextView tv;
    public BalanceArcFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_balance, container, false);
        ArcBalanceView balanceView = v.findViewById(R.id.arcBalanceView);
//        tv = v.findViewById(R.id.text);
//        tv.setText(getArguments().getString("message"));
        switch (getArguments().getInt("position")) {
            case 1:
                balanceView
                        .setStrokeColor(getResources().getColor(R.color.mBankYellow, null))
                        .setDrawMinMaxLines(false)
                        .setDrawProgressCircle(false)
                        .setDrawFullCircle(true)
                        .init();
                break;
            case 3:
                balanceView
                        .setStrokeColor(getResources().getColor(R.color.mBankGreen, null))
                        .setDrawMinMaxLines(false)
                        .setDrawProgressCircle(false)
                        .init();
                break;
            case 4:
                balanceView
                        .setStrokeColor(getResources().getColor(R.color.mBankYellow, null))
                        .setDrawMinMaxLines(false)
                        .setDrawThinStrokeCircle(true)
                        .setDrawFullCircle(true)
                        .setDrawProgressCircle(false)
                        .setStrokeWidth(18)
                        .init();
                break;
            case 5:
                balanceView
                        .setStrokeColor(getResources().getColor(R.color.mBankBlue, null))
                        .setDrawMinMaxLines(false)
                        .setDrawThinStrokeCircle(true)
                        .setDrawFullCircle(true)
                        .setDrawProgressCircle(false)
                        .setStrokeWidth(18)
                        .init();
                break;
           default:
               balanceView.init();
               break;
        }
        return v;
    }

}
