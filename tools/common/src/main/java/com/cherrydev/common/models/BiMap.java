package com.cherrydev.common.models;

import java.util.HashMap;

//https://stackoverflow.com/a/35219750/4673960
public class BiMap<K, V> {

    private HashMap<K, V> map = new HashMap<>();
    private HashMap<V, K> inversedMap = new HashMap<>();

    public void put(K k, V v) {
        map.put(k, v);
        inversedMap.put(v, k);
    }

    public V getValue(K k) {
        return map.get(k);
    }

    public K getKey(V v) {
        return inversedMap.get(v);
    }


    public void remove(K k) {
        V value = getValue(k);
        inversedMap.remove(value);
        map.remove(k);
    }

}