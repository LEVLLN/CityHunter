package lk.simplecode.kz.cityhunter;

import android.app.Application;

import lk.simplecode.kz.cityhunter.network.RetrofitFacade;

public class CApplication extends Application {

    private static CApplication sSingleton;

    public static CApplication getInstance() {
        return sSingleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitFacade.init();
        sSingleton = this;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
