package com.example.mbank.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mbank.fragments.BalanceArcFragment;

public class BalanceViewPagerAdapter extends FragmentStatePagerAdapter {


    public BalanceViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BalanceArcFragment balanceArcFragment = new BalanceArcFragment();
        Bundle bundle = new Bundle();
        position = position + 1;
        bundle.putInt("position", position);
        balanceArcFragment.setArguments(bundle);
        return balanceArcFragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
