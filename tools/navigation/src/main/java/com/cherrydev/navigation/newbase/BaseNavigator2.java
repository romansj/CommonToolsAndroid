package com.cherrydev.navigation.newbase;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.cherrydev.navigation.PendingOpHolder;
import com.cherrydev.navigation.TransactionSafetyHolder;


public abstract class BaseNavigator2<T extends Enum> implements LifecycleObserver {

    @NonNull
    private NavigatorConfiguration navigatorConfiguration;


    public interface Callback<T> {
        void onNavigate(T tag);
    }


    public BaseNavigator2(@NonNull NavigatorConfiguration navigatorConfiguration) {
        this.navigatorConfiguration = navigatorConfiguration;
        this.fragmentManager = navigatorConfiguration.getFragmentManager();
        this.lifecycleOwner = navigatorConfiguration.getLifecycleOwner();
    }


    private MutableLiveData<PendingOpHolder.BiMap<T, Intent>> pendingOpMap = new MutableLiveData<>();


    public enum TransactionType {REPLACE, ADD}

    //todo protected or public?
    @NonNull
    protected LifecycleOwner lifecycleOwner;
    @NonNull
    protected FragmentManager fragmentManager;

    protected T activeTag;
    protected T pendingTransactionTag;
    protected TransactionType pendingType;


    @NonNull
    protected Fragment getFragment(T tag, Bundle bundle) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag.name());
        if (fragment == null) fragment = initFragment(tag, bundle);
        return fragment;
    }


    protected void replace(T tag, Bundle bundle) {
        if (!TransactionSafetyHolder.isTransactionSafe()) {
            TransactionSafetyHolder.setIsTransactionPending(true);
            setPendingTransaction(tag, TransactionType.REPLACE);
            return;
        }


        boolean addToBack = (bundle != null) && bundle.getBoolean("backstack");

        Fragment fragment = getFragment(tag, bundle);

        if (!fragment.isAdded()) {
            FragmentTransaction transaction = getTransaction();
            transaction.replace(navigatorConfiguration.getLayoutID(), fragment, tag.name());
            if (addToBack) transaction.addToBackStack(tag.name());
            transaction.commit();
        }


        activeTag = tag;
        TransactionSafetyHolder.setIsTransactionPending(false);
    }


    protected void replace(T tag) {
        replace(tag, null);
    }


    protected void add(T tag) {
        add(tag, null);
    }


    protected void add(T tag, Bundle bundle) {
        if (TransactionSafetyHolder.isTransactionSafe()) {
            FragmentTransaction transaction = getTransaction();


            Fragment fragment = getFragment(tag, bundle);

            if (!fragment.isAdded()) {
                transaction.add(navigatorConfiguration.getLayoutID(), fragment, tag.name());
                transaction.addToBackStack(tag.name());
            }

            transaction.show(fragment);
            transaction.commit();


            Fragment activeFragment = null;
            if (activeTag != null) {
                activeFragment = fragmentManager.findFragmentByTag(activeTag.name());
            }

            if (activeFragment != null) {
                transaction.hide(activeFragment);
            }


            activeTag = tag;

            TransactionSafetyHolder.setIsTransactionPending(false);

        } else {
            TransactionSafetyHolder.setIsTransactionPending(true);
            setPendingTransaction(tag, TransactionType.ADD);
        }
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
            transaction.setCustomAnimations(navigatorConfiguration.getAnimationIn(), navigatorConfiguration.getAnimationOut(), navigatorConfiguration.getAnimationIn(), navigatorConfiguration.getAnimationOut());
        return transaction;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void doPending() {
        if (pendingTransactionTag == null) return;

        switch (pendingType) {
            case ADD:
                add(pendingTransactionTag);
                break;
            case REPLACE:
                replace(pendingTransactionTag, null);
                break;
        }

    }


    public boolean goBack() {
        //java.lang.NullPointerException: Attempt to invoke virtual method 'int androidx.fragment.app.FragmentManager.getBackStackEntryCount()' on a null object reference
        //the odd bug where going back about 10 times (pressing back button, notes goes in background), shows a white screen and then crash.
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
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


    public T getActiveTag() {
        return activeTag;
    }


    // region
    // Things that need to be implemented by subclass


    public abstract void goTo(T tag);

    public abstract T toEnum(String string);

    @NonNull
    protected abstract Fragment initFragment(T tag, Bundle bundle);

    //endregion
}
