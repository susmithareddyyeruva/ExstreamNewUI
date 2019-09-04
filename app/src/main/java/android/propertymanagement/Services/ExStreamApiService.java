package android.propertymanagement.Services;


import android.propertymanagement.ModelClass.ResponseModelClasses.GetAccountOwnerDetailsAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetActiveUserAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllAccountUsersAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllPermissionAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllStatesAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetCreateUserAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetDeleteUserAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetUpdateAccountOwnerAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetUpdateUserAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.LoginPostAPIResponse;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
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


    /*
     * GetAllPermissionAPIResponse
     * */
    @GET
    Observable<ArrayList<GetAllPermissionAPIResponse>> GetPermissionGroups(@Url String url, @Header("Authorization") String Authorization);


    /*
     * GetUpdateAccountOwner
     * */
    @PUT(APIConstantURL.GetUpdateAccountOwner)
    Observable<GetUpdateAccountOwnerAPIResponse> postUpdateAccountOwner(@Body JsonObject data, @Header("Authorization") String Authorization);


    /*
     * GetUpdateUser
     * */
    @PUT(APIConstantURL.GetUpdateUser)
    Observable<GetUpdateUserAPIResponse> putUpdateUser(@Body JsonObject data, @Header("Authorization") String Authorization);



    /*
     * GetResetPasswordByAdmin
     * */
    @PUT(APIConstantURL.GetResetPasswordByAdmin)
    Observable<String> putGetResetPasswordByAdmin(@Body JsonObject data, @Header("Authorization") String Authorization);


    /*
     * GetAllAccountUsers
     * */
    @GET
    Observable<ArrayList<GetAllAccountUsersAPIResponse>> GetAllAccountUsers(@Url String url, @Header("Authorization") String Authorization);


    // GetCreateUser
    @POST(APIConstantURL.GetCreateUser)
    Observable<GetCreateUserAPIResponse> GetCreateUser(@Body JsonObject data, @Header("Authorization") String Authorization);


    /*
     * GetDeleteUser
     * */
    @DELETE
    Observable<GetDeleteUserAPIResponse> GetDeleteUser(@Url String url, @Header("Authorization") String Authorization);


    /*
     * GetActiveUser
     * */
    @DELETE
    Observable<GetActiveUserAPIResponse> GetActiveUser(@Url String url, @Header("Authorization") String Authorization);


}
