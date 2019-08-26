package android.propertymanagement.ModelClass.RequestModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCreateUserAPIRequestModel {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("permissionGroups")
    @Expose
    private List<Integer> permissionGroups = null;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("permissionGroupId")
    @Expose
    private Integer permissionGroupId;

    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    @SerializedName("isAllProperties")
    @Expose
    private Boolean isAllProperties;

    @SerializedName("password")
    @Expose
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Integer> getPermissionGroups() {
        return permissionGroups;
    }

    public void setPermissionGroups(List<Integer> permissionGroups) {
        this.permissionGroups = permissionGroups;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getAllProperties() {
        return isAllProperties;
    }

    public void setAllProperties(Boolean allProperties) {
        isAllProperties = allProperties;
    }

    public Integer getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(Integer permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

}
