package android.propertymanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.propertymanagement.R;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UsersNewPropertyFragment extends Fragment {


    private Context mContext;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user_new_property, container, false);

        /* intializing and assigning ID's */
        initViews();

        return rootView;
    }

    private void initViews() {

    }

}
