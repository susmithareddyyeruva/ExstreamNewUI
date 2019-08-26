package android.propertymanagement.Services;


import android.propertymanagement.ModelClass.ResponseModelClasses.GetAccountOwnerDetailsAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllStatesAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.LoginPostAPIResponse;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;
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

    /*
     * GetAccountOwnerDetails
     * */
    @GET
    Observable<GetAccountOwnerDetailsAPIResponse> GetAccountOwnerDetails(@Url String url, @Header("Authorization") String Authorization);

    /*
     * GetAllStates
     * */
    @GET
    Observable<ArrayList<GetAllStatesAPIResponse>> GetAllStates(@Url String url, @Header("Authorization") String Authorization);
}
