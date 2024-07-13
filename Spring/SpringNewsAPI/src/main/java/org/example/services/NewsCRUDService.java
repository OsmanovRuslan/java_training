package org.example.services;

import org.example.dto.NewsDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NewsCRUDService implements CRUDService<NewsDto>{

    private final ConcurrentHashMap<Integer, NewsDto> storage = new ConcurrentHashMap<>();

    @Override
    public NewsDto getById(Long id) {
        return storage.get(id.intValue());
    }

    @Override
    public Collection<NewsDto> getAll() {
        return storage.values();
    }

    @Override
    public void create(NewsDto item) {
        long nextId = 0L;
        if (!storage.isEmpty()){
            Enumeration<Integer> keys = storage.keys();
            Integer key = keys.nextElement();
            while (keys.hasMoreElements()) {
                key = keys.nextElement();
            }
            nextId = key + 1;
        }
        item.setId(nextId);
        item.setDate(Instant.now());
        storage.put((int) nextId, item);
    }

    @Override
    public void update(Long id, NewsDto item) {
        item.setId(id);
        item.setDate(Instant.now());
        storage.put(id.intValue(), item);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id.intValue());
    }
}