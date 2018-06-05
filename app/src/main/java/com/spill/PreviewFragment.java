package com.spill;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView)view;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new PhotosAdapter(collection.getPreviewPhotos()));
    }
}
