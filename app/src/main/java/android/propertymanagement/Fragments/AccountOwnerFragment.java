package android.propertymanagement.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Base64;
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

public class AccountOwnerFragment extends Fragment implements OnBackPressed {

    private Context mContext;
    View rootView;
    private EditText firstnameEdt, lastnameEdt, companyEdt, mailingEdt, suite_unit_Edt, zip_Edt,
            cityEdt, emailEdt, phonenoEdt;
    private TextView firstLastNameTextView, companyNameText, mailingText, suite_unitText, zip_Text,
            cityText, spinnerStateText, emailText, phonenoText;
    private Spinner spinnerState;
    private ImageView uploadImageView, okImageView, editImageView;
    private Subscription mSubscription;
    private int accountId;
    private String authorizationToken, imageString, extension, dateandtime, spinnerStateStr;
    public static final int PICK_IMAGE = 2;
    GetAccountOwnerDetailsAPIResponse getAccountOwnerDetailsAPIResponse;
    ArrayList<GetAllStatesAPIResponse> mStatesModel;
    ArrayAdapter<String> adapter_state;


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
                okImageView.setVisibility(View.VISIBLE);
                editImageView.setVisibility(View.GONE);
                getAccountOwnerDetails();
            }
        });
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
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
                        adapter_state = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, statesList);
                        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerState.setAdapter(adapter_state);
                        if (getAccountOwnerDetailsAPIResponse != null && getAccountOwnerDetailsAPIResponse.getAccountOwnerList().getStateId() != null &&
                                !getAccountOwnerDetailsAPIResponse.getAccountOwnerList().getStateId().equals(""))
                            for (int j = 0; j < mStatesModel.size(); j++)
                                if (mStatesModel.get(j).getStateId().equals(spinnerStateStr))
                                    spinnerState.setSelection(j + 1);

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

                        okImageView.setVisibility(View.GONE);
                        editImageView.setVisibility(View.VISIBLE);


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
        accountId = SharedPrefsData.getInt(mContext, Constants.ACCOUNTID, Constants.PREF_NAME);
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
            model.setAccountLogo(imageString);
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

                        Glide.with(mContext).load(mResponse.getAccountOwnerList().getAccountLogo())
                                .fitCenter()
                                .error(R.drawable.icon_upload)
                                .into(uploadImageView);

                    }
                });
    }

    @Override
    public void onBackPressed() {

    }
}
