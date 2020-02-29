package com.example.mbank;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mbank.adapters.BalanceViewPagerAdapter;
import com.example.mbank.adapters.MenuListViewAdapter;
import com.example.mbank.adapters.RecyclerViewAdapter;
import com.example.mbank.animations.ViewPagerSwipeTransition;
import com.example.mbank.pojos.AccountOperation;
import com.example.mbank.utils.DateUtils;
import com.example.mbank.utils.UIUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mContext = getApplicationContext();

        //views
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setAppBarHeight();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //init recyclerview and nested listview
        initializeRecyclerView();

        //init view pager and view pager adapter
        initializeViewPager();

        //init drawer and nav
        initializeDrawer();

        //initialize menu ListView
        initializeMenuListView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    private void setAppBarHeight() {
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        UIUtils.getStatusBarHeight(getBaseContext()) + UIUtils.dpToPx(56, getBaseContext()))
        );
    }

    private void initializeMenuListView(){
        ListView menuListView = findViewById(R.id.menuListView);
        UIUtils.setListViewHeightBasedOnItems(menuListView);

        List<String> menuTitles = Arrays.asList("Napisz na czacie", "Mobilna autoryzacja",
                "Co nowego w aplikacji?", "Oceń aplikację", "Ustawienia", "Wyloguj");

        List<Integer> menuImages = Arrays.asList(R.drawable.ic_drawer_chat, R.drawable.ic_drawer_nam, R.drawable.ic_drawer_whats_new,
                R.drawable.ic_drawer_rateme, R.drawable.ic_drawer_settings, R.drawable.ic_drawer_logoff);

        MenuListViewAdapter adapter = new MenuListViewAdapter(mContext, menuTitles, menuImages);
        menuListView.setAdapter(adapter);
    }

    private void initializeDrawer(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // TODO: 13.01.2020 usun tam na dole wszystko i zostaw tylko R.id.nav_home
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int paymentsIcon = R.drawable.ic_fab_payments;
        int cardIcon = R.drawable.ic_fab_card;
        ArrayList<AccountOperation> list1 = new ArrayList<>();
        list1.add(new AccountOperation(cardIcon, "DELIKATESY HITPOL", -15.09));

        ArrayList<AccountOperation> list2 = new ArrayList<>();
        list2.add(new AccountOperation(cardIcon, "DELIKATESY HITPOL", -20.99));

        ArrayList<AccountOperation> list3 = new ArrayList<>();
        list3.add(new AccountOperation(cardIcon, "DELIKATESY HITPOL", -5.29));
        list3.add(new AccountOperation(paymentsIcon, "#SESH_CODERS POLSKA WS. WARSZAWA 12", 385.55));

        ArrayList<AccountOperation> list4 = new ArrayList<>();
        list4.add(new AccountOperation(cardIcon, "DELIKATESY HITPOL", -7.82));

        ArrayList<AccountOperation> list5 = new ArrayList<>();
        list5.add(new AccountOperation(paymentsIcon, "#SESH_CODERS POLSKA WS. WARSZAWA 12", 1000.69));

        ArrayList<String> operationDates = new ArrayList<>();
        operationDates.add(DateUtils.getPastDate(1));
        operationDates.add(DateUtils.getPastDate(3));
        operationDates.add(DateUtils.getPastDate(5));
        operationDates.add(DateUtils.getPastDate(7));
        operationDates.add(DateUtils.getPastDate(8));

        ArrayList<ArrayList<AccountOperation>> operationList = new ArrayList<>();
        operationList.add(list1);
        operationList.add(list2);
        operationList.add(list3);
        operationList.add(list4);
        operationList.add(list5);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(operationDates, operationList, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeViewPager() {
        /**viewpager intro config*/
        ViewPager accBalanceViewPager = findViewById(R.id.accBalanceViewPager);
        BalanceViewPagerAdapter balanceViewPagerAdapter = new BalanceViewPagerAdapter(getSupportFragmentManager());
        accBalanceViewPager.setAdapter(balanceViewPagerAdapter);
        accBalanceViewPager.setCurrentItem(1);

        final FloatingActionButton infoFab = findViewById(R.id.infoFAB);

        accBalanceViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    infoFab.animate().setDuration(300).alpha(1).setInterpolator(new AccelerateInterpolator());
                } else {
                    infoFab.animate().setDuration(300).alpha(0).setInterpolator(new AccelerateInterpolator());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /** viewpager animations and transitions*/
        accBalanceViewPager.setPageTransformer(true, new ViewPagerSwipeTransition(mContext));
    }


}
