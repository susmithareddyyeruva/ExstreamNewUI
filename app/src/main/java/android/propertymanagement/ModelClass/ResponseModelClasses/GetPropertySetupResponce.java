package android.propertymanagement.ModelClass.ResponseModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPropertySetupResponce {
    @SerializedName("propertyId")
    @Expose
    private Integer propertyId;
    @SerializedName("propertyName")
    @Expose
    private String propertyName;
    @SerializedName("propertyCode")
    @Expose
    private String propertyCode;
    @SerializedName("setupProcess")
    @Expose
    private SetupProcess setupProcess;

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

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

    public SetupProcess getSetupProcess() {
        return setupProcess;
    }

    public void setSetupProcess(SetupProcess setupProcess) {
        this.setupProcess = setupProcess;
    }

    public class SetupProcess {

        @SerializedName("propertyInformation")
        @Expose
        private Boolean propertyInformation;
        @SerializedName("propertyUsers")
        @Expose
        private Boolean propertyUsers;
        @SerializedName("propertyValueAdd")
        @Expose
        private Boolean propertyValueAdd;
        @SerializedName("rentRollSetup")
        @Expose
        private Boolean rentRollSetup;
        @SerializedName("unitDemographics")
        @Expose
        private Boolean unitDemographics;
        @SerializedName("propertyWalk")
        @Expose
        private Boolean propertyWalk;
        @SerializedName("propertySignage")
        @Expose
        private Boolean propertySignage;

        public Boolean getPropertyInformation() {
            return propertyInformation;
        }

        public void setPropertyInformation(Boolean propertyInformation) {
            this.propertyInformation = propertyInformation;
        }

        public Boolean getPropertyUsers() {
            return propertyUsers;
        }

        public void setPropertyUsers(Boolean propertyUsers) {
            this.propertyUsers = propertyUsers;
        }

        public Boolean getPropertyValueAdd() {
            return propertyValueAdd;
        }

        public void setPropertyValueAdd(Boolean propertyValueAdd) {
            this.propertyValueAdd = propertyValueAdd;
        }

        public Boolean getRentRollSetup() {
            return rentRollSetup;
        }

        public void setRentRollSetup(Boolean rentRollSetup) {
            this.rentRollSetup = rentRollSetup;
        }

        public Boolean getUnitDemographics() {
            return unitDemographics;
        }

        public void setUnitDemographics(Boolean unitDemographics) {
            this.unitDemographics = unitDemographics;
        }

        public Boolean getPropertyWalk() {
            return propertyWalk;
        }

        public void setPropertyWalk(Boolean propertyWalk) {
            this.propertyWalk = propertyWalk;
        }

        public Boolean getPropertySignage() {
            return propertySignage;
        }

        public void setPropertySignage(Boolean propertySignage) {
            this.propertySignage = propertySignage;
        }

    }
}
