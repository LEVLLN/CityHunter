package lk.simplecode.kz.cityhunter;

import lk.simplecode.kz.cityhunter.model.DetailedOrganization;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class TestRunner {
    private static DetailedOrganization detailedOrganization;
    public static void main(String[] args) {
        RetrofitFacade.getInstance().getDetailedOrganization(1020L, new Callback<DetailedOrganization>() {
            @Override
            public void onResponse(Response<DetailedOrganization> response) {
                detailedOrganization = response.body();
                System.out.println(detailedOrganization.toString());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
