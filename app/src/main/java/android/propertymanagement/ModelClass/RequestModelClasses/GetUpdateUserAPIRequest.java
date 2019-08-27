package android.propertymanagement.ModelClass.RequestModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUpdateUserAPIRequest {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("permissionGroupsId")
    @Expose
    private Integer permissionGroupsId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("isAllProperties")
    @Expose
    private Boolean isAllProperties;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getPermissionGroupsId() {
        return permissionGroupsId;
    }

    public void setPermissionGroupsId(Integer permissionGroupsId) {
        this.permissionGroupsId = permissionGroupsId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAllProperties() {
        return isAllProperties;
    }

    public void setIsAllProperties(Boolean isAllProperties) {
        this.isAllProperties = isAllProperties;
    }

}
