package com.cherrydev.navigation;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentCallbacks {

    public static void register(FragmentManager fragmentManager) {

        FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                super.onFragmentAttached(fm, f, context);
                System.out.println(f + " onFragmentAttached");
            }

            @Override
            public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                System.out.println(f + " onFragmentViewCreated");
            }

            @Override
            public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentViewDestroyed(fm, f);
                System.out.println(f + " onFragmentViewDestroyed");
            }

            @Override
            public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDestroyed(fm, f);
                System.out.println(f + " onFragmentDestroyed");
            }

            @Override
            public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDetached(fm, f);
                System.out.println(f + " onFragmentDetached");
            }
        };

        fragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true);
    }
}
