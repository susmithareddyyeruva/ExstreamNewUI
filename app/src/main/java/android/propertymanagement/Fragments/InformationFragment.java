package android.propertymanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.propertymanagement.ModelClass.RequestModelClasses.GetCreateUserAPIRequestModel;
import android.propertymanagement.ModelClass.RequestModelClasses.GetPropertyInsertAPIRequest;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllStatesAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetCreateUserAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetPropertyInsertAPIResponse;
import android.propertymanagement.R;
import android.propertymanagement.Services.APIConstantURL;
import android.propertymanagement.Services.ExStreamApiService;
import android.propertymanagement.Services.ServiceFactory;
import android.propertymanagement.Utils.CommonUtil;
import android.propertymanagement.Utils.Constants;
import android.propertymanagement.Utils.SharedPrefsData;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InformationFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    View rootView;
    private EditText propertyNameEdt, portfolioNameEdt, portfolioCodeEdt,
            ownerShipEdt, propertyMngCoEdt, street_addressEdt, suite_unitEdt,
            zip_Edt, cityEdt, phonenoEdt, faxNoEdt, taxIdEdt, number_of_unitEdt, yearBuiltEdt;
    private Spinner spinnerState;
    private Subscription mSubscription;
    private int propertyId;
    private ImageView okImageView;
    private String authorizationToken;
    ArrayList<GetAllStatesAPIResponse> mStatesModel;
    ArrayAdapter<String> adapter_state;
    GetPropertyInsertAPIResponse getPropertyInsertAPIResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_information, container, false);

        /* intializing and assigning ID's */
        initViews();

        return rootView;
    }

    private void initViews() {
        propertyNameEdt = rootView.findViewById(R.id.propertyNameEdt);
        portfolioNameEdt = rootView.findViewById(R.id.portfolioNameEdt);
        portfolioCodeEdt = rootView.findViewById(R.id.portfolioCodeEdt);
        ownerShipEdt = rootView.findViewById(R.id.ownerShipEdt);
        propertyMngCoEdt = rootView.findViewById(R.id.propertyMngCoEdt);
        street_addressEdt = rootView.findViewById(R.id.street_addressEdt);
        suite_unitEdt = rootView.findViewById(R.id.suite_unitEdt);
        zip_Edt = rootView.findViewById(R.id.zip_Edt);
        cityEdt = rootView.findViewById(R.id.cityEdt);
        phonenoEdt = rootView.findViewById(R.id.phonenoEdt);
        faxNoEdt = rootView.findViewById(R.id.faxNoEdt);
        taxIdEdt = rootView.findViewById(R.id.taxIdEdt);
        number_of_unitEdt = rootView.findViewById(R.id.number_of_unitEdt);
        yearBuiltEdt = rootView.findViewById(R.id.yearBuiltEdt);
        spinnerState = rootView.findViewById(R.id.spinnerState);
        okImageView = rootView.findViewById(R.id.okImageView);
        okImageView.setOnClickListener(this);


        getAllStates();

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.okImageView:

                /*
                 *  insert property
                 */
                getPropertyInsert();

                break;

        }

    }
    private void getPropertyInsert() {
        JsonObject object = addPropertyInsertRequest();
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.GetPropertyInsert(object, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetPropertyInsertAPIResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(GetPropertyInsertAPIResponse mResponse) {
                        propertyId = mResponse.getPropertyId();
                        Toast.makeText(mContext, mResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void getAllStates() {
        authorizationToken = SharedPrefsData.getString(mContext, Constants.access_token, Constants.PREF_NAME);
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.GetAllStates(APIConstantURL.GetAllStates, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<GetAllStatesAPIResponse>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(ArrayList<GetAllStatesAPIResponse> mResponse) {

                        ArrayList statesList = new ArrayList();
                        mStatesModel = mResponse;
                        statesList.add(getString(R.string.select_state));
                        for (int i = 0; i < mStatesModel.size(); i++)
                            statesList.add(mStatesModel.get(i).getStateCode());
                        adapter_state = new ArrayAdapter<String>(mContext, R.layout.spinner_text_black, statesList);
                        adapter_state.setDropDownViewResource(R.layout.spinner_text_black);
                        spinnerState.setAdapter(adapter_state);
/*
                        int spinner_txt = getAccountOwnerDetailsAPIResponse.getAccountOwnerList().getStateId();
                        if (getAccountOwnerDetailsAPIResponse.getAccountOwnerList() != null &&
                                getAccountOwnerDetailsAPIResponse.getAccountOwnerList().getStateId() != null &&
                                !getAccountOwnerDetailsAPIResponse.getAccountOwnerList().getStateId().equals(""))
*/
                     /*   for (int j = 0; j < mStatesModel.size(); j++) {
                            if (mStatesModel.get(j).getStateId().equals(""))
                                spinnerState.setSelection(j + 1);

                        }*/
                    }
                });
    }

    /**
     * Json Object of addPropertyInsertRequest
     *
     * @return
     */
    private JsonObject addPropertyInsertRequest() {
        //int spinner_selected = allPermissionAPIResponse.get(spinnerEdt.getSelectedItemPosition() - 1).getPermissionGroupId();
        GetPropertyInsertAPIRequest model = new GetPropertyInsertAPIRequest();
        model.setPropertyName(propertyNameEdt.getText().toString());
        model.setPortfolioName(portfolioNameEdt.getText().toString());
        model.setPortfolioCode(portfolioCodeEdt.getText().toString());
        model.setOwnerShip(ownerShipEdt.getText().toString());
        model.setPropertyManagementCompany(propertyMngCoEdt.getText().toString());
        model.setAddressLine1(street_addressEdt.getText().toString());
        model.setAddressLine2(street_addressEdt.getText().toString());
        model.setSuitUnit(suite_unitEdt.getText().toString());
        model.setZipCode(zip_Edt.getText().toString());
        model.setCity(cityEdt.getText().toString());
        model.setStateId(mStatesModel.get(spinnerState.getSelectedItemPosition() - 1).getStateId());
        model.setPhoneNumber(phonenoEdt.getText().toString());
        model.setFax(faxNoEdt.getText().toString());
        model.setTaxId(taxIdEdt.getText().toString());
        model.setNumberofUnits(number_of_unitEdt.getText().toString());
        model.setYearBuilt(yearBuiltEdt.getText().toString());
        model.setPropertyLogo(null);
        model.setFileExists(false);
        return new Gson().toJsonTree(model).getAsJsonObject();

    }


}
