package lk.simplecode.kz.cityhunter;

import android.app.Application;

import lk.simplecode.kz.cityhunter.network.RetrofitFacade;

public class CApplication extends Application {

    private static CApplication singleton;

    public static CApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitFacade.init();
        singleton = this;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
