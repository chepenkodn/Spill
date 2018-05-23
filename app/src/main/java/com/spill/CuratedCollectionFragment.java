package com.spill;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spill.domain.CuratedCollection;
import com.spill.network.CuratedCollectionRepository;
import com.squareup.picasso.Picasso;


public class CuratedCollectionFragment extends Fragment {
    private static final String TAG = CuratedCollectionFragment.class.getSimpleName();
    private static final String ARGS_COLLECTION_ID = "args:collection_id";

    private CuratedCollection collection;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView previewImage;


    public CuratedCollectionFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param collectionId .
     * @return A new instance of fragment CuratedCollectionFragment.
     */
    public static CuratedCollectionFragment newInstance(Integer collectionId) {
        CuratedCollectionFragment fragment = new CuratedCollectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_COLLECTION_ID, collectionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Integer collectionId = getArguments().getInt(ARGS_COLLECTION_ID);
            collection = CuratedCollectionRepository.getInstance().getById(collectionId);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curated_collection, container, false);
        titleTextView = view.findViewById(R.id.fragment_curated_collection_title);
        descriptionTextView = view.findViewById(R.id.fragment_curated_collection_description);
        previewImage = view.findViewById(R.id.fragment_curated_collection_preview_image);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (collection != null) {
            titleTextView.setText(collection.getTitle());
            descriptionTextView.setText(collection.getDescription());
            final String imageUrl = collection.getPreviewPhotos().get(0).getUrlForSmall();
            Picasso.get().load(imageUrl).into(previewImage);

        }
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