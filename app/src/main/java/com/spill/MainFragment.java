package com.spill;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spill.domain.CuratedCollection;
import com.spill.network.CuratedCollectionRepository;
import com.spill.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private ViewPager pager;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        pager = view.findViewById(R.id.fragment_main_view_pager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        CuratedCollectionPagerAdapter pagerAdapter = new CuratedCollectionPagerAdapter(getChildFragmentManager(), fragments);
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
