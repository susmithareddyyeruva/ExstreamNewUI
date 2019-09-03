package android.propertymanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.propertymanagement.Adapter.UsersListAdapter;
import android.propertymanagement.ModelClass.RequestModelClasses.GetCreateUserAPIRequestModel;
import android.propertymanagement.ModelClass.RequestModelClasses.GetUpdateUserAPIRequest;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllAccountUsersAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllPermissionAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetCreateUserAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetUpdateUserAPIResponse;
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

                getCreateUser();
            }
        });


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
                        adapter_permission = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, permissionsList);
                        adapter_permission.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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


    private void getAllAccountUsers() {
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

                        usersListAdapter = new UsersListAdapter(mContext, mResponse);
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
        GetCreateUserAPIRequestModel model = new GetCreateUserAPIRequestModel();
        model.setFirstName(firstnameEdt.getText().toString());
        model.setLastName(lastnameEdt.getText().toString());
        model.setEmail(emailEdt.getText().toString());
        model.setPhoneNumber(phonenoEdt.getText().toString());
        model.setPermissionGroupId(allPermissionAPIResponse.get(spinnerEdt.getSelectedItemPosition() - 1).getPermissionGroupId());
        model.setPassword("Admin123");
        model.setActive(true);
        model.setAllProperties(true);

        return new Gson().toJsonTree(model).getAsJsonObject();

    }


    @Override
    public void setCartClickListener(String status, int position, String firstNameStr, String lastNameStr,
                                     String emailIdStr, String phonenoStr,int spinnerSelectId ) {

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

    private void getUpdateUsers() {
        JsonObject object = addUpdateUserRequest();
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.putUpdateUser(object, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetUpdateUserAPIResponse>() {
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
                    public void onNext(GetUpdateUserAPIResponse mResponse) {

                        CommonUtil.customToast(mResponse.toString(), mContext);

                    }

                });
    }

    /**
     * Json Object of addUpdateUserRequest
     *
     * @return
     */

    private JsonObject addUpdateUserRequest() {
        GetUpdateUserAPIRequest model = new GetUpdateUserAPIRequest();
        model.setUserId(userId);
        model.setFirstName(firstStr);
        model.setLastName(lastStr);
        model.setEmail(emailStr);
        model.setPhoneNumber(phoneStr);
        model.setPermissionGroupsId(spinnerSelectedId);
        model.setPassword("Admin123");
        model.setIsActive(true);
        model.setIsAllProperties(true);

        return new Gson().toJsonTree(model).getAsJsonObject();

    }

    private void  getDeactivateUser(){
        authorizationToken = SharedPrefsData.getString(mContext, Constants.access_token, Constants.PREF_NAME);
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.GetAllAccountUsers(APIConstantURL.GetDeleteUser+userId, "bearer" + " " + authorizationToken)
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


                    }
                });

    }

}
