package com.example.mbank;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.mbank.adapters.BalanceViewPagerAdapter;
import com.example.mbank.fragments.BalanceArcFragment;
import com.example.mbank.utils.UIUtils;
import com.google.android.material.appbar.AppBarLayout;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //views
    TextView inputPinTV;
    TextView availableFundsTV;
    Toolbar toolbar;

    //vars
    String pinNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);

        setAppBarHeight();
        inputPinTV = findViewById(R.id.inputPIN);
        availableFundsTV = findViewById(R.id.availableFundsTV);

        SpannableStringBuilder builder = new SpannableStringBuilder("Dostępne środki\n rachunek: 100%");
        builder.setSpan(new StyleSpan(Typeface.BOLD), 27, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        availableFundsTV.setText(builder);

    }

    private void setAppBarHeight() {
        toolbar.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        UIUtils.getStatusBarHeight(getBaseContext()) + UIUtils.dpToPx(56, getBaseContext()))
        );
    }

    public void pinInsert(View v) {
        if (v.getTag().toString().equals("del")) {
            if (!pinNum.equals(""))
                pinNum = pinNum.substring(0, pinNum.length() - 1);
        } else if (pinNum.length() < 8) {
            pinNum = pinNum + v.getTag().toString();
        }
        inputPinTV.setText(pinNum);
    }

    public void login(View v) {
        if (pinNum.equals("38602")) {
            findViewById(R.id.mainKeyboard).setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
                    startActivity(i);
                }
            }, 4000);

        } else {
            pinNum = "";
            inputPinTV.setText(pinNum);
        }
    }

}
