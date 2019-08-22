package android.propertymanagement.ModelClass.RequestModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginPageAPIRequestModel {


    @SerializedName("username")
    @Expose
    private String userName;
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("grant_type")
    private String grant_type;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

}
