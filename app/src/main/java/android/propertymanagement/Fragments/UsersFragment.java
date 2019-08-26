package android.propertymanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.propertymanagement.Adapter.UsersListAdapter;
import android.propertymanagement.ModelClass.UserModel;
import android.propertymanagement.R;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private Context mContext;
    View rootView;
    private EditText firstnameEdt, lastnameEdt, emailEdt, phonenoEdt;
    private ImageView addImageView;
    private Spinner sipnnerEdt;
    private RecyclerView recyclerViewUsers;
    private String firstStr, lastStr, emailStr, phoneStr;
    UsersListAdapter usersListAdapter;
    List<UserModel> userModels;

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
        userModels = new ArrayList<>();

        firstnameEdt = rootView.findViewById(R.id.firstnameEdt);
        lastnameEdt = rootView.findViewById(R.id.lastnameEdt);
        emailEdt = rootView.findViewById(R.id.emailEdt);
        phonenoEdt = rootView.findViewById(R.id.phonenoEdt);
        addImageView = rootView.findViewById(R.id.addImageView);
        sipnnerEdt = rootView.findViewById(R.id.sipnnerEdt);
        recyclerViewUsers = rootView.findViewById(R.id.recyclerViewUsers);


    }

    private void setViews() {

        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstStr = firstnameEdt.getText().toString();
                lastStr = lastnameEdt.getText().toString();
                emailStr = emailEdt.getText().toString();
                phoneStr = phonenoEdt.getText().toString();
                userModels.add(new UserModel(firstStr, lastStr, emailStr, phoneStr));
                usersListAdapter.notifyDataSetChanged();
            }
        });

        usersListAdapter = new UsersListAdapter(mContext, userModels);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewUsers.setAdapter(usersListAdapter);

    }


}
