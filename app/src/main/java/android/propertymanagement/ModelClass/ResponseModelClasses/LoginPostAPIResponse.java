package android.propertymanagement.ModelClass.ResponseModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginPostAPIResponse {


    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("features")
    @Expose
    private String features;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("accountName")
    @Expose
    private String accountName;
    @SerializedName("AccountId")
    @Expose
    private String accountId;
    @SerializedName("tokenIssued")
    @Expose
    private String tokenIssued;
    @SerializedName("tokenExpires")
    @Expose
    private String tokenExpires;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTokenIssued() {
        return tokenIssued;
    }

    public void setTokenIssued(String tokenIssued) {
        this.tokenIssued = tokenIssued;
    }

    public String getTokenExpires() {
        return tokenExpires;
    }

    public void setTokenExpires(String tokenExpires) {
        this.tokenExpires = tokenExpires;
    }



    @SerializedName("error_description")
    @Expose
    private String error_description;
    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @SerializedName("error")
    @Expose
    private String error;


}

