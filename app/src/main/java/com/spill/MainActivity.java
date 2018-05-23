package com.spill;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.spill.domain.CuratedCollection;
import com.spill.network.CuratedCollectionRepository;
import com.spill.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.activity_main_view_pager);

        NetworkManager.getUnsplashApi().getFeaturedCollections()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(curatedCollections -> {
                    CuratedCollectionRepository.getInstance().save(curatedCollections);
                    setupViewPagerAdapter();
                }, throwable -> {
                    Log.e(TAG, "error", throwable);
                });
    }

    private void setupViewPagerAdapter() {
        List<CuratedCollectionFragment> fragments = new ArrayList<>();
        for (CuratedCollection collection : CuratedCollectionRepository.getInstance().getAll()) {
            fragments.add(CuratedCollectionFragment.newInstance(collection.getId()));
        }
        CuratedCollectionPagerAdapter pagerAdapter = new CuratedCollectionPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }
}
