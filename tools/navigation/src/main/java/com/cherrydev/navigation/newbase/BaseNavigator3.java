package com.cherrydev.navigation.newbase;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.cherrydev.navigation.PendingOpHolder;
import com.cherrydev.navigation.TransactionSafetyHolder;


public abstract class BaseNavigator3<T extends Enum<T> & IDestination> implements DefaultLifecycleObserver {

    @NonNull
    private NavigatorConfiguration navigatorConfiguration;
    private MutableLiveData<PendingOpHolder.BiMap<T, Intent>> pendingOpMap = new MutableLiveData<>();
    @NonNull
    protected LifecycleOwner lifecycleOwner;
    @NonNull
    protected FragmentManager fragmentManager;

    public enum TransactionType {REPLACE}

    protected T activeTag;
    protected T pendingTransactionTag;
    protected TransactionType pendingType;
    protected Callback<T> callback;


    public BaseNavigator3(@NonNull NavigatorConfiguration navigatorConfiguration) {
        this.navigatorConfiguration = navigatorConfiguration;
        this.fragmentManager = navigatorConfiguration.getFragmentManager();
        this.lifecycleOwner = navigatorConfiguration.getLifecycleOwner();
        if (this.lifecycleOwner != null) this.lifecycleOwner.getLifecycle().addObserver(this);
    }


    @NonNull
    protected Fragment getFragment(T tag, Bundle bundle) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag.name());
        if (fragment == null) fragment = initFragment(tag, bundle);
        return fragment;
    }


    protected void replace(T tag, Bundle bundle) {
        if (TransactionSafetyHolder.isTransactionSafe()) {
            boolean addToBack = tag.isAddToBackstack(); // (bundle != null) && bundle.getBoolean("backstack");


            Fragment fragment = getFragment(tag, bundle);

            if (!fragment.isAdded()) {
                FragmentTransaction transaction = getTransaction();
                transaction.replace(navigatorConfiguration.getLayoutID(), fragment, tag.name());
                if (addToBack) transaction.addToBackStack(tag.name());
                transaction.commit();
            }


            activeTag = tag;
            if (callback != null) callback.onNavigate(activeTag);
            TransactionSafetyHolder.setIsTransactionPending(false);

        } else {
            TransactionSafetyHolder.setIsTransactionPending(true);
            setPendingTransaction(tag, TransactionType.REPLACE);
        }
    }


    protected void replace(T tag) {
        replace(tag, null);
    }


    protected void setPendingTransaction(T tag, TransactionType type) {
        pendingType = type;
        pendingTransactionTag = tag;
    }


    @NonNull
    protected FragmentTransaction getTransaction() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        if (navigatorConfiguration.usesAnimations())
            transaction.setCustomAnimations(
                    navigatorConfiguration.getAnimationIn(),
                    navigatorConfiguration.getAnimationOut(),
                    navigatorConfiguration.getAnimationIn(),
                    navigatorConfiguration.getAnimationOut()
            );

        return transaction;
    }


    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onResume(owner);

        if (pendingTransactionTag == null) return;

        if (pendingType == TransactionType.REPLACE) {
            replace(pendingTransactionTag, null);
        }
    }


    public boolean goBack() {
        //java.lang.NullPointerException: Attempt to invoke virtual method 'int androidx.fragment.app.FragmentManager.getBackStackEntryCount()' on a null object reference
        //the odd bug where going back about 10 times (pressing back button, notes goes in background), shows a white screen and then crash.
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();

            //after popping backstack the fragment we're popping is still in the list, as the last element
            activeTag = getActiveFragmentTag();
            if (callback != null) callback.onNavigate(activeTag);

            return true;
        }

        return false;
    }


    public Intent getPendingOp(String stringTag) {
        T tag = toEnum(stringTag);
        Intent intent = pendingOpMap.getValue().getValue(tag);
        return intent;
    }


    public void setPendingOp(T tag, Intent intent) {
        pendingOpMap.getValue().put(tag, intent);
    }

    public void clearPendingOp(T tag) {
        pendingOpMap.getValue().remove(tag);
    }

    public void clearPendingOp(String stringTag) {
        T tag = toEnum(stringTag);
        clearPendingOp(tag);
    }

    public T getActiveFragmentTag() {
        return activeTag;


//        T activeTag = null;
//
//        boolean stop = false;
//
//        for (int m = fragmentManager.getFragments().size() - 1; m >= 0; m--) {
//            Fragment fragment = fragmentManager.getFragments().get(m);
//
//            String fragmentTag = fragment.getTag();
//            for (T enumTag : enumValuesAll()) {
//                if (enumTag.name().equals(fragmentTag) && !fragment.isVisible()) {
//                    activeTag = enumTag;
//                    stop = true;
//                    break;
//                }
//            }
//
//            if (stop) break;
//        }
//        return activeTag;
    }


    // region
    // Things that need to be implemented by subclass

    public abstract void goTo(T tag);

    public abstract T toEnum(String string);

    @NonNull
    protected abstract Fragment initFragment(T tag, Bundle bundle);


    protected abstract T[] enumValuesAll();

    //endregion


    public void setCallback(Callback<T> callback) {
        this.callback = callback;
    }

    public interface Callback<T> {
        void onNavigate(T tag);
    }
}
