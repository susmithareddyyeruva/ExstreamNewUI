package android.propertymanagement.ModelClass.ResponseModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCreateUserAPIResponse {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("userName")
    @Expose
    private Object userName;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("emailConfirmed")
    @Expose
    private Object emailConfirmed;
    @SerializedName("phoneNumber")
    @Expose
    private Object phoneNumber;
    @SerializedName("phoneNumberConfirmed")
    @Expose
    private Object phoneNumberConfirmed;
    @SerializedName("accountId")
    @Expose
    private Object accountId;
    @SerializedName("accountName")
    @Expose
    private Object accountName;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("passwordHash")
    @Expose
    private Object passwordHash;
    @SerializedName("securityStamp")
    @Expose
    private Object securityStamp;
    @SerializedName("twoFactorEnabled")
    @Expose
    private Object twoFactorEnabled;
    @SerializedName("lockoutEnabled")
    @Expose
    private Object lockoutEnabled;
    @SerializedName("lockoutEndDateUtc")
    @Expose
    private Object lockoutEndDateUtc;
    @SerializedName("accessFailedCount")
    @Expose
    private Object accessFailedCount;
    @SerializedName("isActive")
    @Expose
    private Object isActive;
    @SerializedName("isSuperUser")
    @Expose
    private Object isSuperUser;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("middleName")
    @Expose
    private Object middleName;
    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("profilePicture")
    @Expose
    private Object profilePicture;
    @SerializedName("webProfilePicture")
    @Expose
    private Object webProfilePicture;
    @SerializedName("propertyId")
    @Expose
    private Object propertyId;
    @SerializedName("permissionGroupId")
    @Expose
    private Integer permissionGroupId;
    @SerializedName("permissionGroupName")
    @Expose
    private Object permissionGroupName;
    @SerializedName("propertyUserTypeId")
    @Expose
    private Object propertyUserTypeId;
    @SerializedName("lastModifiedBy")
    @Expose
    private Object lastModifiedBy;
    @SerializedName("lastModifiedDate")
    @Expose
    private String lastModifiedDate;
    @SerializedName("needPortalAccess")
    @Expose
    private Object needPortalAccess;
    @SerializedName("isAllProperties")
    @Expose
    private Boolean isAllProperties;
    @SerializedName("accountLogo")
    @Expose
    private Object accountLogo;
    @SerializedName("title")
    @Expose
    private Object title;
    @SerializedName("permissionGroups")
    @Expose
    private Object permissionGroups;
    @SerializedName("properties")
    @Expose
    private Object properties;
    @SerializedName("isAccountUser")
    @Expose
    private Boolean isAccountUser;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Object emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(Object phoneNumberConfirmed) {
        this.phoneNumberConfirmed = phoneNumberConfirmed;
    }

    public Object getAccountId() {
        return accountId;
    }

    public void setAccountId(Object accountId) {
        this.accountId = accountId;
    }

    public Object getAccountName() {
        return accountName;
    }

    public void setAccountName(Object accountName) {
        this.accountName = accountName;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(Object passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Object getSecurityStamp() {
        return securityStamp;
    }

    public void setSecurityStamp(Object securityStamp) {
        this.securityStamp = securityStamp;
    }

    public Object getTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(Object twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public Object getLockoutEnabled() {
        return lockoutEnabled;
    }

    public void setLockoutEnabled(Object lockoutEnabled) {
        this.lockoutEnabled = lockoutEnabled;
    }

    public Object getLockoutEndDateUtc() {
        return lockoutEndDateUtc;
    }

    public void setLockoutEndDateUtc(Object lockoutEndDateUtc) {
        this.lockoutEndDateUtc = lockoutEndDateUtc;
    }

    public Object getAccessFailedCount() {
        return accessFailedCount;
    }

    public void setAccessFailedCount(Object accessFailedCount) {
        this.accessFailedCount = accessFailedCount;
    }

    public Object getIsActive() {
        return isActive;
    }

    public void setIsActive(Object isActive) {
        this.isActive = isActive;
    }

    public Object getIsSuperUser() {
        return isSuperUser;
    }

    public void setIsSuperUser(Object isSuperUser) {
        this.isSuperUser = isSuperUser;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getMiddleName() {
        return middleName;
    }

    public void setMiddleName(Object middleName) {
        this.middleName = middleName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Object profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Object getWebProfilePicture() {
        return webProfilePicture;
    }

    public void setWebProfilePicture(Object webProfilePicture) {
        this.webProfilePicture = webProfilePicture;
    }

    public Object getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Object propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(Integer permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    public Object getPermissionGroupName() {
        return permissionGroupName;
    }

    public void setPermissionGroupName(Object permissionGroupName) {
        this.permissionGroupName = permissionGroupName;
    }

    public Object getPropertyUserTypeId() {
        return propertyUserTypeId;
    }

    public void setPropertyUserTypeId(Object propertyUserTypeId) {
        this.propertyUserTypeId = propertyUserTypeId;
    }

    public Object getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Object lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Object getNeedPortalAccess() {
        return needPortalAccess;
    }

    public void setNeedPortalAccess(Object needPortalAccess) {
        this.needPortalAccess = needPortalAccess;
    }

    public Boolean getIsAllProperties() {
        return isAllProperties;
    }

    public void setIsAllProperties(Boolean isAllProperties) {
        this.isAllProperties = isAllProperties;
    }

    public Object getAccountLogo() {
        return accountLogo;
    }

    public void setAccountLogo(Object accountLogo) {
        this.accountLogo = accountLogo;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getPermissionGroups() {
        return permissionGroups;
    }

    public void setPermissionGroups(Object permissionGroups) {
        this.permissionGroups = permissionGroups;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

    public Boolean getIsAccountUser() {
        return isAccountUser;
    }

    public void setIsAccountUser(Boolean isAccountUser) {
        this.isAccountUser = isAccountUser;
    }

}
