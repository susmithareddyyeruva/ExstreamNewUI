package android.propertymanagement.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAccountOwnerDetailsAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllStatesAPIResponse;
import android.propertymanagement.R;
import android.propertymanagement.Services.APIConstantURL;
import android.propertymanagement.Services.ExStreamApiService;
import android.propertymanagement.Services.ServiceFactory;
import android.propertymanagement.Utils.Constants;
import android.propertymanagement.Utils.SharedPrefsData;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AccountOwnerFragment extends Fragment {

    private Context mContext;
    View rootView;
    private EditText firstnameEdt, lastnameEdt, companyEdt, mailingEdt, suite_unit_Edt, zip_Edt,
            cityEdt, emailEdt, phonenoEdt;
    private TextView firstLastNameTextView, companyNameText, mailingText, suite_unitText, zip_unitText,
            city_unitText, spinnerStateText, emailText, phonenoText;
    private Spinner spinnerState;
    private ImageView uploadImageView, okImageView, editImageView;
    private Subscription mSubscription;
    private int spinnerStateStr;
    private String authorizationToken, imageString, extension;
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
        zip_unitText = rootView.findViewById(R.id.zip_unitText);
        city_unitText = rootView.findViewById(R.id.city_unitText);
        spinnerStateText = rootView.findViewById(R.id.spinnerStateText);
        emailText = rootView.findViewById(R.id.emailText);
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

                        statesList.add(getString(R.string.select_state));
                        for (int i = 0; i < mResponse.size(); i++)
                            statesList.add(mResponse.get(0).getStateCode());
                        adapter_state = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, statesList);
                        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerState.setAdapter(adapter_state);
                        if (getAccountOwnerDetailsAPIResponse != null && getAccountOwnerDetailsAPIResponse.getAccountOwnerList().getStateId() != null &&
                                !getAccountOwnerDetailsAPIResponse.getAccountOwnerList().getStateId().equals(""))
                            for (int j = 0; j < mResponse.size(); j++)
                                if (mResponse.get(0).getStateId().equals(spinnerStateStr))
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
                        spinnerStateStr = mResponse.getAccountOwnerList().getStateId();
                        Glide.with(mContext).load(mResponse.getAccountOwnerList().getAccountLogo())
                                .fitCenter()
                                .error(R.drawable.icon_upload)
                                .into(uploadImageView);

                    }
                });
    }

    private void getUpdateAccountOwner() {

    }

}
