package android.propertymanagement.ModelClass.ResponseModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAccountOwnerDetailsAPIResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("accountOwnerList")
    @Expose
    private AccountOwnerList accountOwnerList;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AccountOwnerList getAccountOwnerList() {
        return accountOwnerList;
    }

    public void setAccountOwnerList(AccountOwnerList accountOwnerList) {
        this.accountOwnerList = accountOwnerList;
    }

    public class AccountOwnerList {

        @SerializedName("accountId")
        @Expose
        private Integer accountId;
        @SerializedName("accountName")
        @Expose
        private String accountName;
        @SerializedName("suitUnit")
        @Expose
        private String suitUnit;
        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mailingAdress")
        @Expose
        private String mailingAdress;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("stateId")
        @Expose
        private Integer stateId;
        @SerializedName("stateName")
        @Expose
        private String stateName;
        @SerializedName("stateCode")
        @Expose
        private String stateCode;
        @SerializedName("zipCode")
        @Expose
        private String zipCode;
        @SerializedName("accountLogo")
        @Expose
        private String accountLogo;
        @SerializedName("lastModifiedDate")
        @Expose
        private String lastModifiedDate;
        @SerializedName("lastModifiedBy")
        @Expose
        private Integer lastModifiedBy;
        @SerializedName("fileExists")
        @Expose
        private Boolean fileExists;

        public Integer getAccountId() {
            return accountId;
        }

        public void setAccountId(Integer accountId) {
            this.accountId = accountId;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMailingAdress() {
            return mailingAdress;
        }

        public void setMailingAdress(String mailingAdress) {
            this.mailingAdress = mailingAdress;
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

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getAccountLogo() {
            return accountLogo;
        }

        public void setAccountLogo(String accountLogo) {
            this.accountLogo = accountLogo;
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
}
