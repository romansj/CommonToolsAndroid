package com.cherrydev.navigation.newbase;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

public class NavigatorConfiguration {
    private FragmentManager fragmentManager;
    private int layoutID;
    private boolean usesAnimations;
    private int animationIn;
    private int animationOut;
    private LifecycleOwner lifecycleOwner;

    private NavigatorConfiguration(FragmentManager fragmentManager, int layoutID, boolean usesAnimations, int animationIn, int animationOut, LifecycleOwner lifecycleOwner) {
        this.fragmentManager = fragmentManager;
        this.layoutID = layoutID;
        this.usesAnimations = usesAnimations;
        this.animationIn = animationIn;
        this.animationOut = animationOut;
        this.lifecycleOwner = lifecycleOwner;
    }


    public static class Builder {

        private FragmentManager fragmentManager;
        private int layoutID = -1;
        private boolean usesAnimations;
        private int animationIn;
        private int animationOut;
        private LifecycleOwner lifecycleOwner;

        public Builder setFragmentManager(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            return this;
        }

        public Builder setLayoutID(int layoutID) {
            this.layoutID = layoutID;
            return this;
        }

        public Builder setLifecycleOwner(LifecycleOwner lifecycleOwner) {
            this.lifecycleOwner = lifecycleOwner;
            return this;
        }

        public Builder setUsesAnimations(boolean usesAnimations) {
            this.usesAnimations = usesAnimations;
            return this;
        }

        /**
         * Specify if fragment manager will use provided animations during navigation (add, remove, replace, show, hide)
         *
         * @param animationIn  Animation plays when entering fragment
         * @param animationOut Animation plays when exiting fragment
         */
        public Builder setUsesAnimations(int animationIn, int animationOut) {
            this.usesAnimations = true;
            this.animationIn = animationIn;
            this.animationOut = animationOut;
            return this;
        }


        public NavigatorConfiguration build() {
            if (fragmentManager == null)
                throw new IllegalArgumentException("Fragment Manager cannot be null");
            if (layoutID == -1)
                throw new IllegalArgumentException("FragmentContainer's layout ID has not been passed");


            NavigatorConfiguration navigatorConfiguration = new NavigatorConfiguration(fragmentManager, layoutID, usesAnimations, animationIn, animationOut, lifecycleOwner);
            return navigatorConfiguration;
        }


    }


    @NonNull
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public boolean usesAnimations() {
        return usesAnimations;
    }

    public int getAnimationIn() {
        return animationIn;
    }

    public int getAnimationOut() {
        return animationOut;
    }

    @NonNull
    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }
}
