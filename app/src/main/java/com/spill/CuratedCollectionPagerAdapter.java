package com.spill;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class CuratedCollectionPagerAdapter extends FragmentStatePagerAdapter {
    List<CuratedCollectionFragment> collectionFragments;

    public CuratedCollectionPagerAdapter(FragmentManager fm, List<CuratedCollectionFragment> collectionFragments) {
        super(fm);
        this.collectionFragments = collectionFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return collectionFragments.get(position);
    }

    @Override
    public int getCount() {
        return collectionFragments.size();
    }
}
