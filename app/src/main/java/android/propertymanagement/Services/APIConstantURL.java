package android.propertymanagement.Services;

public interface APIConstantURL {


    //Global Base URL
    String BASE_URL = "http://exstreamapiqa.azurewebsites.net/";

    //Login
    String LOGINPage = BASE_URL + "token";

    //Password Reset
    String PASSWORDRESET = BASE_URL + "api/User/ForgotPassword";

    // RegisterAccount
    String RegisterAccount = BASE_URL + "api/AnonymousAccess/RegisterAccount";

    // GetAccountOwnerDetails
    String GetAccountOwnerDetails = BASE_URL + "api/Account/GetAccountOwnerDetails";

    //GetAllStates
    String GetAllStates = BASE_URL + "api/State/GetAllStates";


}
