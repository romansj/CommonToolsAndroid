package com.cherrydev.navigation;

import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;

public class PendingOpHolder {
    public static MutableLiveData<BiMap<BaseNavigator.Tag, Intent>> pendingOpMap = new MutableLiveData<>();

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

//    public static Intent getValue(BaseNavigator.Tag tag) {
//        Intent value = pendingOpMap.getValue().getValue(tag);
//        return value;
//    }
//
//
//    public static BaseNavigator.Tag getKey(Intent value) {
//        BaseNavigator.Tag key = pendingOpMap.getValue().getKey(value);
//        return key;
//    }
}
