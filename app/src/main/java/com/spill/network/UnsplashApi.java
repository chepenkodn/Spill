package com.spill.network;

import com.spill.domain.CuratedCollection;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UnsplashApi {

    @GET("/collections/curated")
    Observable<List<CuratedCollection>> getFeaturedCollections();
}
