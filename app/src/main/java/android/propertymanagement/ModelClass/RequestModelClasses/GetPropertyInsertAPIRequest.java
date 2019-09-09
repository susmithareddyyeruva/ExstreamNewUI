package android.propertymanagement.ModelClass.RequestModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPropertyInsertAPIRequest {

    @SerializedName("propertyName")
    @Expose
    private String propertyName;
    @SerializedName("propertyCode")
    @Expose
    private String propertyCode;
    @SerializedName("portfolioName")
    @Expose
    private String portfolioName;
    @SerializedName("portfolioCode")
    @Expose
    private String portfolioCode;
    @SerializedName("ownerShip")
    @Expose
    private String ownerShip;
    @SerializedName("propertyManagementCompany")
    @Expose
    private String propertyManagementCompany;
    @SerializedName("suitUnit")
    @Expose
    private String suitUnit;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("addressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("addressLine2")
    @Expose
    private String addressLine2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("stateId")
    @Expose
    private Integer stateId;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("zipCode")
    @Expose
    private String zipCode;
    @SerializedName("taxId")
    @Expose
    private String taxId;
    @SerializedName("numberofUnits")
    @Expose
    private String numberofUnits;
    @SerializedName("yearBuilt")
    @Expose
    private String yearBuilt;
    @SerializedName("propertyLogo")
    @Expose
    private Object propertyLogo;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("inActivatedDate")
    @Expose
    private String inActivatedDate;
    @SerializedName("inActivatedBy")
    @Expose
    private Integer inActivatedBy;
    @SerializedName("lastModifiedDate")
    @Expose
    private String lastModifiedDate;
    @SerializedName("lastModifiedBy")
    @Expose
    private Integer lastModifiedBy;
    @SerializedName("fileExists")
    @Expose
    private Boolean fileExists;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getPortfolioCode() {
        return portfolioCode;
    }

    public void setPortfolioCode(String portfolioCode) {
        this.portfolioCode = portfolioCode;
    }

    public String getOwnerShip() {
        return ownerShip;
    }

    public void setOwnerShip(String ownerShip) {
        this.ownerShip = ownerShip;
    }

    public String getPropertyManagementCompany() {
        return propertyManagementCompany;
    }

    public void setPropertyManagementCompany(String propertyManagementCompany) {
        this.propertyManagementCompany = propertyManagementCompany;
    }

    public String getSuitUnit() {
        return suitUnit;
    }

    public void setSuitUnit(String suitUnit) {
        this.suitUnit = suitUnit;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getNumberofUnits() {
        return numberofUnits;
    }

    public void setNumberofUnits(String numberofUnits) {
        this.numberofUnits = numberofUnits;
    }

    public String getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(String yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public Object getPropertyLogo() {
        return propertyLogo;
    }

    public void setPropertyLogo(Object propertyLogo) {
        this.propertyLogo = propertyLogo;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getInActivatedDate() {
        return inActivatedDate;
    }

    public void setInActivatedDate(String inActivatedDate) {
        this.inActivatedDate = inActivatedDate;
    }

    public Integer getInActivatedBy() {
        return inActivatedBy;
    }

    public void setInActivatedBy(Integer inActivatedBy) {
        this.inActivatedBy = inActivatedBy;
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

    public Boolean getFileExists() {
        return fileExists;
    }

    public void setFileExists(Boolean fileExists) {
        this.fileExists = fileExists;
    }

}
