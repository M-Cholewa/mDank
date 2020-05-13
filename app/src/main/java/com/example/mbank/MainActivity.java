package com.example.mbank;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
        // TODO: 12.05.2020 add flag secure 

        SpannableStringBuilder builder = new SpannableStringBuilder("Dostępne środki\n rachunek: 100%");
        builder.setSpan(new StyleSpan(Typeface.BOLD), 27, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        availableFundsTV.setText(builder);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void setAppBarHeight() {
        int statusBarHeight = UIUtils.getStatusBarHeight(getBaseContext());
        toolbar.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                         statusBarHeight + UIUtils.dpToPx(56, getBaseContext()))
        );
        toolbar.setPadding(0,statusBarHeight,0,0);
    }

    public void pinInsert(View v) {
        if (v.getTag().toString().equals("del")) {
            if (!pinNum.equals(""))
                pinNum = pinNum.substring(0, pinNum.length() - 1);
        } else if (pinNum.length() < 8) {
            pinNum = pinNum + v.getTag().toString();
        }
        StringBuilder obfuscatedPin = new StringBuilder();
        for (int i=0; i<pinNum.length(); i++){
            obfuscatedPin.append("⬤");
        }
        inputPinTV.setText(obfuscatedPin.toString());
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
