package com.example.growingmobilef1.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> listFragmentTitolo=new ArrayList<>() ;
    private ArrayList<Fragment> listFragment=new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listFragmentTitolo.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();

    }
    public void addFragment(String title, Fragment fragment){
        listFragment.add(fragment);
        listFragmentTitolo.add(title);
    }
}
