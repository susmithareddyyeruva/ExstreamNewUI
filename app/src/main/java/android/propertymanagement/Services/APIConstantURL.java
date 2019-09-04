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

    //GetPermissionGroups
    String GetPermissionGroups = BASE_URL + "api/PermissionGroups/GetAll";


    // GetUpdateAccountOwner
    String GetUpdateAccountOwner = BASE_URL + "api/Account/UpdateAccountOwner";

    //GetAllAccountUsers
    String GetAllAccountUsers = BASE_URL + "api/User/GetAllAccountUsers";

    //GetAllPropertys
    String GetAllPropertys = BASE_URL + "api/Property/GetPropertySetupProgress";

    //GetCreateUser
    String GetCreateUser = BASE_URL + "api/User/CreateUser";

    //GetUpdateUser
    String GetUpdateUser = BASE_URL + "api/User/UpdateUser";

    // GetDeleteUser
    String GetDeleteUser = BASE_URL + "api/User/DeleteUser/";

    //GetActiveUser
    String GetActiveUser = BASE_URL + "api/User/ActiveUser/";

    //GetResetPasswordByAdmin
    String GetResetPasswordByAdmin = BASE_URL + "api/User/ResetPasswordByAdmin";

}
