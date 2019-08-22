package android.propertymanagement.Services;


import android.propertymanagement.ModelClass.ResponseModelClasses.LoginPostAPIResponse;

import com.google.gson.JsonObject;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public interface ExStreamApiService {


    // Login
    @POST(APIConstantURL.LOGINPage)
    Observable<LoginPostAPIResponse> postLogin(@Body JsonObject data);


    // PASSWORD RESET
    @PUT(APIConstantURL.PASSWORDRESET)
    Observable<String> putpassword(@Body JsonObject data);


    // Login
    @POST(APIConstantURL.RegisterAccount)
    Observable<String> postRegisterAccount(@Body JsonObject data);
}
