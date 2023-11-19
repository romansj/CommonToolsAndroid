package com.cherrydev.navigation;

import static com.cherrydev.navigation.PendingOpHolder.pendingOpMap;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;


public class BaseNavigator implements LifecycleObserver {

    private int contentID;
    private boolean useTransactionAnimations = true;

    public enum Tag {
        //navigator login
        LOGIN_OPTIONS, LOGIN_SUCCESS, LOGIN_EMAIL, LOGIN_EMAIL_RESET,

        //settings
        PROFILE_VIEW, PROFILE_EDIT,

        //navigator Notes
        TAG_COLLECTIONS, TAG_FAVORITES, TAG_REMINDERS, TAG_SEARCH,

        //navigator backup
        TAG_IMPORT_EXPORT, TAG_IMPORT, TAG_EXPORT,

        //navigator main
        MAIN, SETTINGS, COLLECTION_EDITOR, DELETED_NOTES, EDITING, MORE, LABELS, LOGIN,

        // settings
        PROFILE, SYNC, SIGN_UP, BACKUP, TAG_OLD_BACKUP, PRIVACY,

        // dialogs
        DIALOG_CHOOSE_LABELS, DIALOG_CHOOSE_COLLECTION,
    }

    public enum TransactionType {REPLACE, ADD}

    //todo protected or public?
    protected LifecycleOwner lifecycleOwner;
    protected FragmentManager fragmentManager;
    //protected Fragment fragmentActive;
    protected Tag activeTag;
    protected Tag pendingTransactionTag;
    protected TransactionType pendingType;


    @NonNull
    protected Fragment getFragment(Tag tag, Bundle bundle) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag.name());

        if (fragment == null) {
            //Timber.i("getFragment " + tag + " : NULL after lookup in fm");
            fragment = initFragment(tag, bundle);
        } else {
            //Timber.i("getFragment " + tag + " : NOT null after lookup in fm");

        }


        return fragment;
    }

    protected Fragment initFragment(Tag tag, Bundle bundle) {
        return null;
    }


    protected void replace(Tag tag, Bundle bundle) {
        if (TransactionSafetyHolder.isTransactionSafe()) {
            //Timber.d("is safe");

            boolean addToBack = (bundle != null) && bundle.getBoolean("backstack");


            //Timber.d("replace " + tag);
            Fragment fragment = getFragment(tag, bundle);
            //Fragment fragmentByTag = fragmentManager.findFragmentByTag(tag.toString());

            //Ref #JR005
            //Timber.d("replace with: " + tag + ", fragment.isAdded() " + fragment.isAdded() +
            //        ". fragByTag.added " + (fragmentByTag == null ? "null" : fragmentByTag.isAdded()));

            if (!fragment.isAdded()) {
                FragmentTransaction transaction = getTransaction();
                transaction.replace(contentID, fragment, tag.name());
                if (addToBack) transaction.addToBackStack(tag.name());
                transaction.commit();
            }


            //fragmentActive = fragment;
            activeTag = tag;
            TransactionSafetyHolder.setIsTransactionPending(false);

        } else {
            //Timber.d("not safe");
            TransactionSafetyHolder.setIsTransactionPending(true);
            setPendingTransaction(tag, TransactionType.REPLACE);
        }
    }


    public Tag getCurrentTag() {
        return activeTag;
    }

    protected void replace(Tag tag) {
        replace(tag, null);
    }


//    protected void add(Tag tag, Bundle bundle, RecyclerView.LayoutParams layoutParams) {
//        ViewGroup.LayoutParams fragmentViewLayoutParams = null;
//
//
//        if (TransactionSafetyHolder.isTransactionSafe()) {
//            FragmentTransaction transaction = getTransaction();
//
//            Timber.d("add " +tag);
//            Fragment fragment = getFragment(tag, bundle);
//
//            if (!fragment.isAdded()) {
//                transaction.add(contentID, fragment, tag.name());
//            }
//
//            transaction.show(fragment);
//            transaction.addToBackStack(tag.name());
//            transaction.commit();
//
//
////            new Handler().postDelayed(()->{            View fragmentView = fragment.getView();
////                fragmentView.setLayoutParams(layoutParams);},500);
//
//
////            if (fragmentViewLayoutParams != null) {
////                fragment.getView().setLayoutParams(fragmentViewLayoutParams);
////            }
//
//            Fragment activeFragment = null;
//            if (activeTag != null) {
//                activeFragment = fragmentManager.findFragmentByTag(activeTag.name());
//            }
//
//            if (activeFragment != null) {
//                transaction.hide(activeFragment);
//                Timber.d("activeFragment " + activeTag + " NOT null, " + "new fr " + tag);
//            } else {
//                Timber.d("activeFragment " + activeTag + " is NULL");
//            }
//
//
//            activeTag = tag;
//
//            TransactionSafetyHolder.setIsTransactionPending(false);
//
//        } else {
//            TransactionSafetyHolder.setIsTransactionPending(true);
//            setPendingTransaction(tag, TransactionType.ADD);
//        }
//    }

    protected void add(Tag tag) {
        add(tag, null);
    }

    protected void add(Tag tag, Bundle bundle) {
        //Timber.d("add " + tag.name() + ", safe " + MainActivity.isTransactionSafe);
        if (TransactionSafetyHolder.isTransactionSafe()) {
            FragmentTransaction transaction = getTransaction();


//            if (fragmentActive != null) {
//                Timber.d("active not null, added "+fragmentActive.isAdded());
//                transaction.hide(fragmentActive);
//            }

            //Timber.d("add " + tag);
            Fragment fragment = getFragment(tag, bundle);
            //Timber.d("fragment null " + (fragment == null));

            if (!fragment.isAdded()) {
                //Timber.d("fragment " + fragment.getTag() + " not added");
                transaction.add(contentID, fragment, tag.name());
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
                //Timber.d("activeFragment " + activeTag + " NOT null, " + " new fr " + tag);
            } else {
                //Timber.d("activeFragment " + activeTag + " is NULL");
            }

//            if (fragmentActive != null) {
//                boolean added = fragmentActive.isAdded();
//                boolean visible = fragmentActive.isVisible();
//                boolean hidden = fragmentActive.isHidden();
//
//                Timber.e("activeShit: " + "added " + added + ", visible " + visible + ", hidden " + hidden);
//            }

            //fragmentActive = fragment;
            activeTag = tag;

            TransactionSafetyHolder.setIsTransactionPending(false);

        } else {
            TransactionSafetyHolder.setIsTransactionPending(true);
            setPendingTransaction(tag, TransactionType.ADD);
        }
    }


    protected void setPendingTransaction(Tag tag, TransactionType type) {
        pendingType = type;
        pendingTransactionTag = tag;
    }

    boolean firstTransaction = true;
    int in, out;

    public void setIn(int in) {
        this.in = in;
    }

    public void setOut(int out) {
        this.out = out;
    }

    @NonNull
    protected FragmentTransaction getTransaction() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (useTransactionAnimations) {
            //if (!firstTransaction) {
            transaction.setCustomAnimations(in, out, in, out);
//            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,android.R.animator.fade_in, android.R.animator.fade_out);

            //} else {
            //    firstTransaction = false;
            //}

            //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        }
        return transaction;
    }


    // config methods
    public void setContentID(int contentID) {
        this.contentID = contentID;
    }


    public void setCustomTransitions(boolean b) {
        useTransactionAnimations = !b;
    }


    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    protected Callback callback;

    public interface Callback {
        void onNavigate(Tag tag);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void doPending() {
        //Timber.d("do Pending");

        if (pendingTransactionTag != null) {
            switch (pendingType) {
                case ADD:
                    add(pendingTransactionTag);
                    break;
                case REPLACE:
                    replace(pendingTransactionTag, null);
                    break;
            }

        }
    }


    public boolean goBack() {
        //java.lang.NullPointerException: Attempt to invoke virtual method 'int androidx.fragment.app.FragmentManager.getBackStackEntryCount()' on a null object reference
        //the odd bug where going back about 10 times (pressing back button, notes goes in background), shows a white screen and then crash.
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }

        return false;
    }


    public Intent getPendingOp(Fragment fragment) {
        //Timber.d("fragment.getTag() " + fragment.getTag());
        Tag tag = Tag.valueOf(fragment.getTag());
        Intent intent = pendingOpMap.getValue().getValue(tag);
        return intent;
    }

    public void clearPendingOp(Tag tag) {
        pendingOpMap.getValue().remove(tag);
    }

    public void clearPendingOp(Fragment fragment) {
        Tag tag = Tag.valueOf(fragment.getTag());
        clearPendingOp(tag);
    }


    public void setPendingOp(Tag tag, Intent intent) {
        pendingOpMap.getValue().put(tag, intent);
    }


}
