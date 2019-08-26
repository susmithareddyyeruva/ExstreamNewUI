package android.propertymanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.propertymanagement.Adapter.UsersListAdapter;
import android.propertymanagement.ModelClass.RequestModelClasses.GetCreateUserAPIRequestModel;
import android.propertymanagement.ModelClass.RequestModelClasses.GetUpdateAccountOwnerAPIRequestModel;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllAccountUsersAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetUpdateAccountOwnerAPIResponse;
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
import android.widget.AdapterView;
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

public class UsersFragment extends Fragment {

    private Context mContext;
    View rootView;
    private EditText firstnameEdt, lastnameEdt, emailEdt, phonenoEdt;
    private ImageView addImageView;
    private Spinner spinnerEdt;
    private RecyclerView recyclerViewUsers;
    private String firstStr, lastStr, emailStr, phoneStr;
    UsersListAdapter usersListAdapter;
    private String authorizationToken;
    private Subscription mSubscription;
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


        /*ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("JAVA");
        arrayList.add("ANDROID");
        arrayList.add("C Language");
        arrayList.add("CPP Language");
        arrayList.add("Go Language");
        arrayList.add("AVN SYSTEMS");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEdt.setAdapter(arrayAdapter);
        spinnerEdt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        getAllAccountUsers();

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
                .subscribe(new Subscriber<String>() {
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
                    public void onNext(String mResponse) {

                       /* firstnameEdt.setText(mResponse.);
                        firstStr = firstnameEdt.getText().toString();
                        lastStr = lastnameEdt.getText().toString();
                        emailStr = emailEdt.getText().toString();
                        phoneStr = phonenoEdt.getText().toString();
                        CommonUtil.customToast(mResponse.getMessage(), mContext);*/

                        getAllAccountUsers();

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
        model.setFirstName(firstStr);
        model.setLastName(lastStr);
        model.setEmail(emailStr);
        model.setPhoneNumber(phoneStr);
        model.setPermissionGroups(null);
        model.setPassword("Admin123");
        model.setActive(true);
        model.setAllProperties(true);

        return new Gson().toJsonTree(model).getAsJsonObject();

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

                    }
                });
    }


}
