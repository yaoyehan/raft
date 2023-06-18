package com.nanami.www;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KVServer {

    private Map<String, String> map = new ConcurrentHashMap<>();

    void put(String key, String value) {
        map.put(key, value);
    }

    String get(String key) {
        return map.get(key);
    }

}
