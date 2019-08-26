package android.propertymanagement.ModelClass.ResponseModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllPermissionAPIResponse {

    @SerializedName("permissionGroupId")
    @Expose
    private Integer permissionGroupId;
    @SerializedName("permissionGroupName")
    @Expose
    private String permissionGroupName;
    @SerializedName("permissionGroupCode")
    @Expose
    private String permissionGroupCode;
    @SerializedName("permissionGroupDescription")
    @Expose
    private String permissionGroupDescription;
    @SerializedName("accountId")
    @Expose
    private Integer accountId;
    @SerializedName("isSuperPermissionGroup")
    @Expose
    private Boolean isSuperPermissionGroup;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("lastModifiedDate")
    @Expose
    private String lastModifiedDate;
    @SerializedName("lastModifiedBy")
    @Expose
    private Integer lastModifiedBy;
    @SerializedName("permissionGroupFeatures")
    @Expose
    private Object permissionGroupFeatures;

    public Integer getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(Integer permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    public String getPermissionGroupName() {
        return permissionGroupName;
    }

    public void setPermissionGroupName(String permissionGroupName) {
        this.permissionGroupName = permissionGroupName;
    }

    public String getPermissionGroupCode() {
        return permissionGroupCode;
    }

    public void setPermissionGroupCode(String permissionGroupCode) {
        this.permissionGroupCode = permissionGroupCode;
    }

    public String getPermissionGroupDescription() {
        return permissionGroupDescription;
    }

    public void setPermissionGroupDescription(String permissionGroupDescription) {
        this.permissionGroupDescription = permissionGroupDescription;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Boolean getIsSuperPermissionGroup() {
        return isSuperPermissionGroup;
    }

    public void setIsSuperPermissionGroup(Boolean isSuperPermissionGroup) {
        this.isSuperPermissionGroup = isSuperPermissionGroup;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Object getPermissionGroupFeatures() {
        return permissionGroupFeatures;
    }

    public void setPermissionGroupFeatures(Object permissionGroupFeatures) {
        this.permissionGroupFeatures = permissionGroupFeatures;
    }
}
