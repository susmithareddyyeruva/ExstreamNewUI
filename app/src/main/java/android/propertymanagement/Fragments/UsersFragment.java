package android.propertymanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.propertymanagement.Adapter.UsersListAdapter;
import android.propertymanagement.ModelClass.RequestModelClasses.GetCreateUserAPIRequestModel;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllAccountUsersAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllPermissionAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetCreateUserAPIResponse;
import android.propertymanagement.R;
import android.propertymanagement.Services.APIConstantURL;
import android.propertymanagement.Services.ExStreamApiService;
import android.propertymanagement.Services.ServiceFactory;
import android.propertymanagement.Utils.CommonUtil;
import android.propertymanagement.Utils.Constants;
import android.propertymanagement.Utils.SharedPrefsData;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

public class UsersFragment extends Fragment implements UsersListAdapter.OnCartChangedListener {

    private Context mContext;
    View rootView;
    private EditText firstnameEdt, lastnameEdt, emailEdt, phonenoEdt;
    private ImageView addImageView;
    private Spinner spinnerEdt;
    private RecyclerView recyclerViewUsers;
    private String firstStr, lastStr, emailStr, phoneStr;
    int spinnerSelectedId;
    UsersListAdapter usersListAdapter;
    private String authorizationToken;
    private Subscription mSubscription;
    ArrayList<GetAllPermissionAPIResponse> allPermissionAPIResponse;
    GetCreateUserAPIResponse getCreateUserAPIResponse;
    ArrayAdapter<String> adapter_permission;
    private int spinnerPermissionStr, userId;
    private ArrayList<GetAllAccountUsersAPIResponse> listResults = new ArrayList<>();
    private ArrayList<GetAllAccountUsersAPIResponse> BIndDatalistResults = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_users, container, false);

        /* intializing and assigning ID's */
        initViews();
        setViews();

        return rootView;
    }

    private void initViews() {


        firstnameEdt = rootView.findViewById(R.id.firstnameEdt);
        lastnameEdt = rootView.findViewById(R.id.lastnameEdt);
        emailEdt = rootView.findViewById(R.id.emailEdt);
        phonenoEdt = rootView.findViewById(R.id.phonenoEdt);
        addImageView = rootView.findViewById(R.id.addImageView);
        spinnerEdt = rootView.findViewById(R.id.spinnerEdt);
        recyclerViewUsers = rootView.findViewById(R.id.recyclerViewUsers);

        getAllAccountUsers();

        getSpinnerPermission();


    }

    private void setViews() {

        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validations()){
                    getCreateUser();
                    getAllAccountUsers();
                }
            }
        });


    }

    /**
     * Validations for user name and password
     *
     * @return
     */
    public boolean validations() {
        if (TextUtils.isEmpty(firstnameEdt.getText().toString())) {
            firstnameEdt.setError(getString(R.string.err_fisrt_name));
            firstnameEdt.requestFocus();
            firstnameEdt.setEnabled(true);
            return false;
        } else if (TextUtils.isEmpty(lastnameEdt.getText().toString())) {
            lastnameEdt.setError(getString(R.string.err_last_name));
            lastnameEdt.requestFocus();
            lastnameEdt.setEnabled(true);
            return false;
        } else if (isEmptySpinner(spinnerEdt)) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(spinnerEdt.getWindowToken(), 0);
            Toast.makeText(mContext, getString(R.string.err_select_state), Toast.LENGTH_SHORT).show();
            spinnerEdt.requestFocusFromTouch();
            spinnerEdt.setEnabled(true);

            return false;
        }
        if (TextUtils.isEmpty(emailEdt.getText().toString())) {
            emailEdt.setError(getString(R.string.err_please_enter_email));
            emailEdt.requestFocus();
            emailEdt.setEnabled(true);
            return false;
        } else if (!isValidEmail(emailEdt.getText().toString())) {
            emailEdt.setError(getString(R.string.err_valid_emailid));
            emailEdt.requestFocus();
            emailEdt.setEnabled(true);
            return false;
        } else if (TextUtils.isEmpty(phonenoEdt.getText().toString().trim())) {
            phonenoEdt.setError(getString(R.string.err_phno));
            phonenoEdt.setEnabled(true);
            phonenoEdt.requestFocus();
            return false;
        } else if (!isValidPhone() || phonenoEdt.getText().toString().startsWith("0")) {
            phonenoEdt.setError(getString(R.string.err_please_enter_valid_mobile_number));
            phonenoEdt.requestFocusFromTouch();
            phonenoEdt.setEnabled(true);

        }
        return true;
    }

    // to validate phone number
    private boolean isValidPhone() {
        String target = phonenoEdt.getText().toString().trim();
        if (target.length()!=10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }


    // to validate EmptySpinner
    public static boolean isEmptySpinner(final Spinner inputSpinner) {
        if (null == inputSpinner) return true;
        if (inputSpinner.getSelectedItemPosition() == -1 || inputSpinner.getSelectedItemPosition() == 0) {
            return true;
        }
        return false;
    }


    /**
     * Valid email or not
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(final String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();


    }


    private void getCreateUser() {
        JsonObject object = addCreateUserRequest();
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.GetCreateUser(object, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCreateUserAPIResponse>() {
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
                    public void onNext(GetCreateUserAPIResponse mResponse) {
                        CommonUtil.customToast("Account user created successfully", mContext);
                        firstnameEdt.setText("");
                        lastnameEdt.setText("");
                        spinnerEdt.setSelection(0);
                        emailEdt.setText("");
                        phonenoEdt.setText("");

                        getAllAccountUsers();

                    }

                });
    }

    private void getSpinnerPermission() {
        authorizationToken = SharedPrefsData.getString(mContext, Constants.access_token, Constants.PREF_NAME);
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.GetPermissionGroups(APIConstantURL.GetPermissionGroups, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<GetAllPermissionAPIResponse>>() {
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
                    public void onNext(ArrayList<GetAllPermissionAPIResponse> mResponse) {

                        ArrayList permissionsList = new ArrayList();
                        allPermissionAPIResponse = mResponse;
                        permissionsList.add(getString(R.string.select_permission));
                        for (int i = 0; i < allPermissionAPIResponse.size(); i++)
                            permissionsList.add(allPermissionAPIResponse.get(i).getPermissionGroupName());
                        adapter_permission = new ArrayAdapter<String>(mContext, R.layout.spinner_text, permissionsList);
                        adapter_permission.setDropDownViewResource(R.layout.spinner_text);
                        spinnerEdt.setAdapter(adapter_permission);
                        /*if (getCreateUserAPIResponse != null && getCreateUserAPIResponse.getPermissionGroupId() != null &&
                                !getCreateUserAPIResponse.getPermissionGroupId().equals(""))
                            for (int j = 0; j < allPermissionAPIResponse.size(); j++)
                                if (allPermissionAPIResponse.get(j).getPermissionGroupId().equals(spinnerPermissionStr))
                                    spinnerEdt.setSelection(j + 1);
*/
                    }
                });

    }


    public void getAllAccountUsers() {
        authorizationToken = SharedPrefsData.getString(mContext, Constants.access_token, Constants.PREF_NAME);
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.GetAllAccountUsers(APIConstantURL.GetAllAccountUsers, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<GetAllAccountUsersAPIResponse>>() {
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
                    public void onNext(ArrayList<GetAllAccountUsersAPIResponse> mResponse) {
                        listResults = new ArrayList<>();
                        listResults = mResponse;
                        usersListAdapter = new UsersListAdapter(mContext, listResults, UsersFragment.this);
                        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        recyclerViewUsers.setAdapter(usersListAdapter);
                        usersListAdapter.setOnCartChangedListener(UsersFragment.this);

                        userId = mResponse.get(0).getUserId();

                    }
                });
    }

    /**
     * Json Object of addCreateUserRequest
     *
     * @return
     */
    private JsonObject addCreateUserRequest() {
        int spinner_selected = allPermissionAPIResponse.get(spinnerEdt.getSelectedItemPosition() - 1).getPermissionGroupId();
        GetCreateUserAPIRequestModel model = new GetCreateUserAPIRequestModel();
        model.setFirstName(firstnameEdt.getText().toString());
        model.setLastName(lastnameEdt.getText().toString());
        model.setEmail(emailEdt.getText().toString());
        model.setPhoneNumber(phonenoEdt.getText().toString());
        model.setPermissionGroupId(spinner_selected);
        model.setPassword("Admin123");
        model.setActive(true);
        model.setAllProperties(true);

        return new Gson().toJsonTree(model).getAsJsonObject();

    }


    @Override
    public void setCartClickListener(String status, int position, String firstNameStr, String lastNameStr,
                                     String emailIdStr, String phonenoStr, int spinnerSelectId) {

      /*  firstStr = firstNameStr;
        lastStr = lastNameStr;
        emailStr = emailIdStr;
        phoneStr = phonenoStr;
        spinnerSelectedId = spinnerSelectId;
        if (status.equalsIgnoreCase("edit")) {
            getUpdateUsers();

            if ("" + userId != null && userId != 0) {

            }
        }*/
    }


}
