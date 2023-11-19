package com.cherrydev.sharing;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;


public class ShareViewModel extends ViewModel {

    public ShareViewModel() {
        apps = new MutableLiveData<>();
    }

    private MyAsync myAsync;

    // Create a LiveData with a String
    private MutableLiveData<List<DialogItemShare>> apps;

    public MutableLiveData<List<DialogItemShare>> getApps(Context app, Intent intent) {
        loadApps(app, intent);
        return apps;
    }

    private void loadApps(Context app, Intent intent) {
        myAsync = new MyAsync(app, intent);
        myAsync.execute();
    }


    private class MyAsync extends AsyncTask<Void, Void, List<DialogItemShare>> {

        public MyAsync(Context app, Intent intent) {
            this.app = app;
            this.intent = intent;
        }

        Context app;
        Intent intent;

        @Override
        protected List<DialogItemShare> doInBackground(Void... voids) {
            return AppListHelper.getInstance().getApps(app, intent);
        }

        @Override
        protected void onPostExecute(List<DialogItemShare> dialogItemShares) {
            super.onPostExecute(dialogItemShares);

            setApps(dialogItemShares);
        }
    }

    public void setApps(List<DialogItemShare> apps) {
        //if (this.apps != null)
        this.apps.setValue(apps);
    }


    @Override
    protected void onCleared() {
        if (myAsync != null && !myAsync.isCancelled()) {
            myAsync.cancel(true);
        }
        super.onCleared();
    }

}


