package com.example.shift.caches;

import com.example.shift.entity.ShiftData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HashCalculation {
    private final Map<ShiftData, String> hashMap = new HashMap<>();

    public boolean isContain(ShiftData key) {
        return hashMap.containsKey(key);
    }

    public void addToMap(ShiftData key, String result) {
        hashMap.put(key, result);
    }

    public String getParameters(ShiftData key) {
        return hashMap.get(key);
    }
}
