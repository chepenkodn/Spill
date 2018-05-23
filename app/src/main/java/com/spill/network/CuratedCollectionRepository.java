package com.spill.network;

import com.spill.domain.CuratedCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuratedCollectionRepository {
    private static final CuratedCollectionRepository INSTANCE = new CuratedCollectionRepository();
    private final Map<Integer, CuratedCollection> storage;

    private CuratedCollectionRepository() {
        storage = new HashMap<>();
    }

    public static CuratedCollectionRepository getInstance() {
        return INSTANCE;
    }

    public CuratedCollection getById(int id) {
        return storage.get(id);
    }

    public void save(CuratedCollection collection) {
        storage.put(collection.getId(), collection);
    }

    public void save(Collection<CuratedCollection> collections) {
        for (CuratedCollection collection : collections) {
            save(collection);
        }
    }

    public List<CuratedCollection> getAll() {
        return new ArrayList<>(storage.values());
    }
}
