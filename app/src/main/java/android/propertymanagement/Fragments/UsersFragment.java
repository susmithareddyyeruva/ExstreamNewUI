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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private Context mContext;
    View rootView;
    private EditText firstnameEdt, lastnameEdt, emailEdt, phonenoEdt;
    private ImageView addImageView;
    private Spinner spinnerEdt;
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
        spinnerEdt = rootView.findViewById(R.id.spinnerEdt);
        recyclerViewUsers = rootView.findViewById(R.id.recyclerViewUsers);


        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("JAVA");
        arrayList.add("ANDROID");
        arrayList.add("C Language");
        arrayList.add("CPP Language");
        arrayList.add("Go Language");
        arrayList.add("AVN SYSTEMS");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEdt.setAdapter(arrayAdapter);
        spinnerEdt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


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
