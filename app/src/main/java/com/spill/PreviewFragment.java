package com.spill;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spill.domain.CuratedCollection;
import com.spill.network.CuratedCollectionRepository;

import static com.spill.CuratedCollectionFragment.ARGS_COLLECTION_ID;

public class PreviewFragment extends Fragment {

    private CuratedCollection collection;

    public static PreviewFragment newInstance(int collectionId) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_COLLECTION_ID, collectionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int collectionId = getArguments().getInt(ARGS_COLLECTION_ID);
            collection = CuratedCollectionRepository.getInstance().getById(collectionId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }

}
