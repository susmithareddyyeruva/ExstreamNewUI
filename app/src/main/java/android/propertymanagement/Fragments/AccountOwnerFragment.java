package android.propertymanagement.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.propertymanagement.Interface.OnBackPressed;
import android.propertymanagement.ModelClass.RequestModelClasses.GetUpdateAccountOwnerAPIRequestModel;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAccountOwnerDetailsAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllStatesAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetUpdateAccountOwnerAPIResponse;
import android.propertymanagement.R;
import android.propertymanagement.Services.APIConstantURL;
import android.propertymanagement.Services.ExStreamApiService;
import android.propertymanagement.Services.ServiceFactory;
import android.propertymanagement.Utils.CommonUtil;
import android.propertymanagement.Utils.Constants;
import android.propertymanagement.Utils.SharedPrefsData;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AccountOwnerFragment extends Fragment implements OnBackPressed, View.OnClickListener {

    public static final String TAG=  AccountOwnerFragment.class.getSimpleName();
    private Context mContext;
    View rootView;
    private EditText firstnameEdt, lastnameEdt, companyEdt, mailingEdt, suite_unit_Edt, zip_Edt,
            cityEdt, emailEdt, phonenoEdt;
    private TextView firstLastNameTextView, companyNameText, mailingText, suite_unitText, zip_Text,
            cityText, spinnerStateText, emailText, phonenoText;
    private Spinner spinnerState;
    private ImageView uploadImageView, okImageView, editImageView;
    private Subscription mSubscription;
    private Integer accountId;
    private AlertDialog alertDialog, alert;
    private String authorizationToken, imageString, extension, dateandtime, spinnerStateStr;
    public static final int PICK_IMAGE = 2;
    GetAccountOwnerDetailsAPIResponse getAccountOwnerDetailsAPIResponse;
    ArrayList<GetAllStatesAPIResponse> mStatesModel;
    ArrayAdapter<String> adapter_state;
    private TextView about_tv, terms_of_use_tv, privacy_policy_tv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_account_owner, container, false);

        /* intializing and assigning ID's */
        initViews();

        setViews();

        dateAndtime();

        return rootView;
    }

    private void initViews() {
        accountId = SharedPrefsData.getInt(mContext, Constants.ACCOUNTID, Constants.PREF_NAME);
        firstnameEdt = rootView.findViewById(R.id.firstnameEdt);
        lastnameEdt = rootView.findViewById(R.id.lastnameEdt);
        companyEdt = rootView.findViewById(R.id.companyEdt);
        mailingEdt = rootView.findViewById(R.id.mailingEdt);
        suite_unit_Edt = rootView.findViewById(R.id.suite_unit_Edt);
        zip_Edt = rootView.findViewById(R.id.zip_Edt);
        cityEdt = rootView.findViewById(R.id.cityEdt);
        emailEdt = rootView.findViewById(R.id.emailEdt);
        phonenoEdt = rootView.findViewById(R.id.phonenoEdt);
        firstLastNameTextView = rootView.findViewById(R.id.firstLastNameTextView);
        companyNameText = rootView.findViewById(R.id.companyNameText);
        mailingText = rootView.findViewById(R.id.mailingText);
        suite_unitText = rootView.findViewById(R.id.suite_unitText);
        zip_Text = rootView.findViewById(R.id.zip_Text);
        cityText = rootView.findViewById(R.id.cityText);
        spinnerStateText = rootView.findViewById(R.id.spinnerStateText);
        emailText = rootView.findViewById(R.id.emailText);
        phonenoText = rootView.findViewById(R.id.phonenoText);
        spinnerState = rootView.findViewById(R.id.spinnerState);
        uploadImageView = rootView.findViewById(R.id.uploadImageView);
        okImageView = rootView.findViewById(R.id.okImageView);
        editImageView = rootView.findViewById(R.id.editImageView);
        about_tv = rootView.findViewById(R.id.about_tv);
        terms_of_use_tv = rootView.findViewById(R.id.terms_of_use_tv);
        privacy_policy_tv = rootView.findViewById(R.id.privacy_policy_tv);

        firstnameEdt.setVisibility(View.VISIBLE);
        lastnameEdt.setVisibility(View.VISIBLE);
        companyEdt.setVisibility(View.VISIBLE);
        mailingEdt.setVisibility(View.VISIBLE);
        suite_unit_Edt.setVisibility(View.VISIBLE);
        zip_Edt.setVisibility(View.VISIBLE);
        cityEdt.setVisibility(View.VISIBLE);
        spinnerState.setVisibility(View.VISIBLE);
        emailEdt.setVisibility(View.VISIBLE);
        phonenoEdt.setVisibility(View.VISIBLE);
        okImageView.setVisibility(View.VISIBLE);

        getAccountOwnerDetails();

        getAllStates();
    }

    private void setViews() {
        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        okImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpdateAccountOwner();
            }
        });
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstnameEdt.setVisibility(View.VISIBLE);
                lastnameEdt.setVisibility(View.VISIBLE);
                companyEdt.setVisibility(View.VISIBLE);
                mailingEdt.setVisibility(View.VISIBLE);
                suite_unit_Edt.setVisibility(View.VISIBLE);
                zip_Edt.setVisibility(View.VISIBLE);
                cityEdt.setVisibility(View.VISIBLE);
                spinnerState.setVisibility(View.VISIBLE);
                emailEdt.setVisibility(View.VISIBLE);
                phonenoEdt.setVisibility(View.VISIBLE);
                okImageView.setVisibility(View.VISIBLE);
                editImageView.setVisibility(View.GONE);
                firstLastNameTextView.setVisibility(View.GONE);
                companyNameText.setVisibility(View.GONE);
                mailingText.setVisibility(View.GONE);
                suite_unitText.setVisibility(View.GONE);
                zip_Text.setVisibility(View.GONE);
                cityText.setVisibility(View.GONE);
                emailText.setVisibility(View.GONE);
                phonenoText.setVisibility(View.GONE);
                spinnerStateText.setVisibility(View.GONE);
                getAccountOwnerDetails();
            }
        });
        about_tv.setOnClickListener(this);
        terms_of_use_tv.setOnClickListener(this);
        privacy_policy_tv.setOnClickListener(this);
    }

    /**
     * open gallery
     */
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, PICK_IMAGE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && null != data) {
            // Get the url from data
            Uri selectedImageUri = data.getData();
            Log.d(TAG,"path :"+selectedImageUri);

            if (selectedImageUri != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = mContext.getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                // Set the image in ImageView
                uploadImageView.setImageURI(selectedImageUri);
                if (picturePath != null) {
                    extension = picturePath.substring(picturePath.lastIndexOf("."));
                }
                final InputStream imageStream;
                try {
                    imageStream = mContext.getContentResolver().openInputStream(selectedImageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    imageString = encodeImage(selectedImage);
                    //imageString = encodeImage(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param bm
     * @return
     */
    private String encodeImage(Bitmap bm) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
           // bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String encImage = Base64.encodeToString(b, Base64.NO_WRAP);
            Log.e(TAG,"base64:"+encImage);

            return encImage;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
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
                        adapter_state = new ArrayAdapter<String>(mContext,R.layout.spinner_text_black, statesList);
                        adapter_state.setDropDownViewResource(R.layout.spinner_text_black);
                        spinnerState.setAdapter(adapter_state);
                        int spinner_txt = getAccountOwnerDetailsAPIResponse.getAccountOwnerList().getStateId();
                        if ( getAccountOwnerDetailsAPIResponse.getAccountOwnerList() != null &&
                                getAccountOwnerDetailsAPIResponse.getAccountOwnerList().getStateId() != null &&
                                !getAccountOwnerDetailsAPIResponse.getAccountOwnerList().getStateId().equals(""))
                            for (int j = 0; j < mStatesModel.size(); j++) {
                                if (mStatesModel.get(j).getStateId().equals(spinner_txt))
                                    spinnerState.setSelection(j + 1);

                            }
                    }
                });
    }


    private void getAccountOwnerDetails() {

        authorizationToken = SharedPrefsData.getString(mContext, Constants.access_token, Constants.PREF_NAME);
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.GetAccountOwnerDetails(APIConstantURL.GetAccountOwnerDetails, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetAccountOwnerDetailsAPIResponse>() {
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
                    public void onNext(GetAccountOwnerDetailsAPIResponse mResponse) {
                        getAccountOwnerDetailsAPIResponse =  mResponse;
                        firstnameEdt.setText(mResponse.getAccountOwnerList().getFirstName());
                        lastnameEdt.setText(mResponse.getAccountOwnerList().getLastName());
                        companyEdt.setText(mResponse.getAccountOwnerList().getAccountName());
                        mailingEdt.setText(mResponse.getAccountOwnerList().getMailingAdress());
                        suite_unit_Edt.setText(mResponse.getAccountOwnerList().getSuitUnit());
                        zip_Edt.setText(mResponse.getAccountOwnerList().getZipCode());
                        cityEdt.setText(mResponse.getAccountOwnerList().getCity());
                        emailEdt.setText(mResponse.getAccountOwnerList().getEmail());
                        phonenoEdt.setText(mResponse.getAccountOwnerList().getPhoneNumber());
                        spinnerStateStr = mResponse.getAccountOwnerList().getStateCode();

                        //spinnerState.setSelection(mResponse.getAccountOwnerList().getStateId());
                        Glide.with(mContext).load(mResponse.getAccountOwnerList().getAccountLogo())
                                .fitCenter()
                                .error(R.drawable.icon_upload)
                                .into(uploadImageView);

                    }
                });
    }

    private void getUpdateAccountOwner() {

        JsonObject object = addUpdateAccountOwnerRequest();
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.postUpdateAccountOwner(object, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetUpdateAccountOwnerAPIResponse>() {
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
                                Log.e(TAG,"---------------------------------------------");
                                Log.e(TAG,e.getLocalizedMessage());
                                Log.e(TAG,"---------------------------------------------");
                            }
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(GetUpdateAccountOwnerAPIResponse mResponse) {

                        firstnameEdt.setVisibility(View.GONE);
                        lastnameEdt.setVisibility(View.GONE);
                        companyEdt.setVisibility(View.GONE);
                        mailingEdt.setVisibility(View.GONE);
                        suite_unit_Edt.setVisibility(View.GONE);
                        zip_Edt.setVisibility(View.GONE);
                        cityEdt.setVisibility(View.GONE);
                        spinnerState.setVisibility(View.GONE);
                        emailEdt.setVisibility(View.GONE);
                        phonenoEdt.setVisibility(View.GONE);
                        getAccountOwnerDetailsUpdated();

                        CommonUtil.customToast(mResponse.getMessage(), mContext);

                    }

                });
    }

    // to get current date and time
    private void dateAndtime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formattedDate = df.format(c.getTime());
        dateandtime = formattedDate;
        SharedPrefsData.getInstance(mContext).updateStringValue(mContext, "datetime", dateandtime);
    }

    /**
     * Json Object of addUpdateAccountOwnerRequest
     *
     * @return
     */
    private JsonObject addUpdateAccountOwnerRequest() {
        GetUpdateAccountOwnerAPIRequestModel model = new GetUpdateAccountOwnerAPIRequestModel();
        model.setFirstName(firstnameEdt.getText().toString());
        model.setLastName(lastnameEdt.getText().toString());
        model.setAccountName(companyEdt.getText().toString());
        model.setMailingAdress(mailingEdt.getText().toString());
        model.setSuitUnit(suite_unit_Edt.getText().toString());
        model.setZipCode(zip_Edt.getText().toString());
        model.setCity(cityEdt.getText().toString());
        model.setEmail(emailEdt.getText().toString());
        model.setPhoneNumber(phonenoEdt.getText().toString());
        model.setAccountId(accountId);
        model.setStateId(mStatesModel.get(spinnerState.getSelectedItemPosition() - 1).getStateId());
        model.setStateName(mStatesModel.get(spinnerState.getSelectedItemPosition() - 1).getStateName());
        model.setStateCode(mStatesModel.get(spinnerState.getSelectedItemPosition() - 1).getStateCode());
        model.setLastModifiedBy(accountId);
        model.setLastModifiedDate(dateandtime);
        if (imageString != null) {
            //String testimage= "data:image/jpg;base64,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";
            String imageStr = "data:image/jpg;base64,"+imageString;

            //model.setAccountLogo(imageStr);
            model.setAccountLogo(imageStr);
            //model.setAccountLogo("data:image/jpg;base64,"+imageString);
            model.setFileExists(true);
        } else {
            model.setFileExists(false);
        }

        return new Gson().toJsonTree(model).getAsJsonObject();

    }

    private void getAccountOwnerDetailsUpdated() {

        authorizationToken = SharedPrefsData.getString(mContext, Constants.access_token, Constants.PREF_NAME);
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.GetAccountOwnerDetails(APIConstantURL.GetAccountOwnerDetails, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetAccountOwnerDetailsAPIResponse>() {
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

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(GetAccountOwnerDetailsAPIResponse mResponse) {
                        firstLastNameTextView.setVisibility(View.VISIBLE);
                        companyNameText.setVisibility(View.VISIBLE);
                        mailingText.setVisibility(View.VISIBLE);
                        suite_unitText.setVisibility(View.VISIBLE);
                        zip_Text.setVisibility(View.VISIBLE);
                        cityText.setVisibility(View.VISIBLE);
                        emailText.setVisibility(View.VISIBLE);
                        phonenoText.setVisibility(View.VISIBLE);
                        spinnerStateText.setVisibility(View.VISIBLE);
                        firstLastNameTextView.setText(mResponse.getAccountOwnerList().getFirstName()
                                + "" + mResponse.getAccountOwnerList().getLastName());
                        companyNameText.setText(mResponse.getAccountOwnerList().getAccountName());
                        mailingText.setText(mResponse.getAccountOwnerList().getMailingAdress());
                        suite_unitText.setText(mResponse.getAccountOwnerList().getSuitUnit());
                        zip_Text.setText(mResponse.getAccountOwnerList().getZipCode());
                        cityText.setText(mResponse.getAccountOwnerList().getCity());
                        emailText.setText(mResponse.getAccountOwnerList().getEmail());
                        phonenoText.setText(mResponse.getAccountOwnerList().getPhoneNumber());
                        spinnerStateText.setText(mResponse.getAccountOwnerList().getStateCode());
                        String imageStringupdated = mResponse.getAccountOwnerList().getAccountLogo();
                        Glide.with(mContext).load(imageStringupdated)
                                .fitCenter()
                                .error(R.drawable.icon_upload)
                                .into(uploadImageView);
                        okImageView.setVisibility(View.GONE);
                        editImageView.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.about_tv:

                /*
                 *  Custom dialog for aboutus
                 */
                aboutUsDialog();

                break;

            case R.id.terms_of_use_tv:

                /*
                 * Custom dialog for terms of use
                 */
                termsOfUseDialog();

                break;

            case R.id.privacy_policy_tv:

                /*
                 * Custom dialog for privacy policy
                 */
                privacyPolicyDialog();

                break;

        }

    }

    private void aboutUsDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_about_us, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void termsOfUseDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_terms_of_use, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void privacyPolicyDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_privacy_policy, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


}
