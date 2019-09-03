package android.propertymanagement.Adapter;

import android.content.Context;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllAccountUsersAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllPermissionAPIResponse;
import android.propertymanagement.ModelClass.UserModel;
import android.propertymanagement.R;
import android.propertymanagement.Services.APIConstantURL;
import android.propertymanagement.Services.ExStreamApiService;
import android.propertymanagement.Services.ServiceFactory;
import android.propertymanagement.Utils.Constants;
import android.propertymanagement.Utils.SharedPrefsData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UsersListAdapter extends RecyclerView.Adapter {

    Context mContext;
    ArrayList<GetAllAccountUsersAPIResponse> userModels;
    private OnCartChangedListener onCartChangedListener;
    String spinnerString;
    private String authorizationToken;
    private Subscription mSubscription;
    ArrayList<GetAllPermissionAPIResponse> allPermissionAPIResponse;
    ArrayAdapter<String> adapter_permission;

    public UsersListAdapter(Context mContext, ArrayList<GetAllAccountUsersAPIResponse> userModels) {
        this.mContext = mContext;
        this.userModels = userModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_list_adapter, null);
        TextViewHolder vh = new TextViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        ((TextViewHolder) holder).firstnameText.setText(userModels.get(position).getFirstName() + " " +
                userModels.get(position).getLastName());
        ((TextViewHolder) holder).spinnerText.setText(userModels.get(position).getPermissionGroupName());
        ((TextViewHolder) holder).emailText.setText(userModels.get(position).getEmail());
        ((TextViewHolder) holder).phonenoText.setText(userModels.get(position).getPhoneNumber());


        ((TextViewHolder) holder).editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextViewHolder) holder).firstnameText.setVisibility(View.GONE);
                ((TextViewHolder) holder).emailText.setVisibility(View.GONE);
                ((TextViewHolder) holder).phonenoText.setVisibility(View.GONE);
                ((TextViewHolder) holder).spinnerText.setVisibility(View.GONE);

                ((TextViewHolder) holder).firstnameEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).lastnameEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).emailEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).spinnerEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).phonenoEdt.setVisibility(View.VISIBLE);

                ((TextViewHolder) holder).firstnameEdt.setText(userModels.get(position).getFirstName());
                ((TextViewHolder) holder).lastnameEdt.setText(userModels.get(position).getLastName());
                ((TextViewHolder) holder).emailEdt.setText(userModels.get(position).getEmail());

                ((TextViewHolder) holder).phonenoEdt.setText(userModels.get(position).getPhoneNumber());

                for (int j = 0; j < userModels.size(); j++)
                    if (userModels.get(j).getPermissionGroupId().equals(spinnerString))
                        ((TextViewHolder) holder).spinnerEdt.setSelection(j + 1);

                ((TextViewHolder) holder).editImageView.setVisibility(View.GONE);
                ((TextViewHolder) holder).dotImageView.setVisibility(View.GONE);
                ((TextViewHolder) holder).okImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).closeImageView.setVisibility(View.VISIBLE);
            }
        });
        ((TextViewHolder) holder).okImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((TextViewHolder) holder).firstnameText.setText(userModels.get(position).getFirstName() + " " +
                        userModels.get(position).getLastName());
                ((TextViewHolder) holder).emailText.setText(userModels.get(position).getEmail());
                ((TextViewHolder) holder).phonenoText.setText(userModels.get(position).getPhoneNumber());
                ((TextViewHolder) holder).spinnerText.setText(userModels.get(position).getPermissionGroupName());

                ((TextViewHolder) holder).firstnameText.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).emailText.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).phonenoText.setVisibility(View.VISIBLE);

                ((TextViewHolder) holder).firstnameEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).lastnameEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).emailEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).spinnerEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).phonenoEdt.setVisibility(View.GONE);


                ((TextViewHolder) holder).editImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).dotImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).okImageView.setVisibility(View.GONE);
                ((TextViewHolder) holder).closeImageView.setVisibility(View.GONE);

                onCartChangedListener.setCartClickListener("edit", position,
                        ((TextViewHolder) holder).firstnameEdt.getText().toString(),
                        ((TextViewHolder) holder).lastnameEdt.getText().toString(),
                        ((TextViewHolder) holder).emailEdt.getText().toString(),
                        ((TextViewHolder) holder).phonenoEdt.getText().toString());
            }
        });


        ((TextViewHolder) holder).closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextViewHolder) holder).firstnameText.setText(userModels.get(position).getFirstName() + " " +
                        userModels.get(position).getLastName());
                ((TextViewHolder) holder).spinnerText.setText(userModels.get(position).getPermissionGroupName());
                ((TextViewHolder) holder).emailText.setText(userModels.get(position).getEmail());
                ((TextViewHolder) holder).phonenoText.setText(userModels.get(position).getPhoneNumber());

                ((TextViewHolder) holder).firstnameText.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).emailText.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).phonenoText.setVisibility(View.VISIBLE);


                ((TextViewHolder) holder).firstnameEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).lastnameEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).emailEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).phonenoEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).spinnerEdt.setVisibility(View.GONE);

                ((TextViewHolder) holder).editImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).dotImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).okImageView.setVisibility(View.GONE);
                ((TextViewHolder) holder).closeImageView.setVisibility(View.GONE);
            }
        });

        ((TextViewHolder) holder).dotImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(mContext, ((TextViewHolder) holder).dotImageView);

                popup.inflate(R.menu.popup_menu);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.reset_pwdText:
                                Toast.makeText(mContext, "reset password", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.deactive_userText:
                                Toast.makeText(mContext, "DEACTIVATE USER", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

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
                        permissionsList.add(mContext.getString(R.string.select_permission));
                        for (int i = 0; i < allPermissionAPIResponse.size(); i++)
                            permissionsList.add(allPermissionAPIResponse.get(i).getPermissionGroupName());
                        adapter_permission = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, permissionsList);
                        adapter_permission.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ((TextViewHolder) holder).spinnerEdt.setAdapter(adapter_permission);
                        for (int j = 0; j < userModels.size(); j++)
                            if (userModels.get(j).getPermissionGroupId().equals(spinnerString))
                                ((TextViewHolder) holder).spinnerEdt.setSelection(j + 1);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        TextView firstnameText, spinnerText, emailText, phonenoText;
        EditText firstnameEdt, lastnameEdt, emailEdt, phonenoEdt;
        Spinner spinnerEdt;
        ImageView addImageView, okImageView, closeImageView, dotImageView, editImageView;


        public TextViewHolder(@NonNull View itemView) {
            super(itemView);

            firstnameText = itemView.findViewById(R.id.firstnameText);
            spinnerText = itemView.findViewById(R.id.spinnerText);
            emailText = itemView.findViewById(R.id.emailText);
            phonenoText = itemView.findViewById(R.id.phonenoText);
            firstnameEdt = itemView.findViewById(R.id.firstnameEdt);
            lastnameEdt = itemView.findViewById(R.id.lastnameEdt);
            emailEdt = itemView.findViewById(R.id.emailEdt);
            phonenoEdt = itemView.findViewById(R.id.phonenoEdt);
            spinnerEdt = itemView.findViewById(R.id.spinnerEdt);
            addImageView = itemView.findViewById(R.id.addImageView);
            okImageView = itemView.findViewById(R.id.okImageView);
            closeImageView = itemView.findViewById(R.id.closeImageView);
            editImageView = itemView.findViewById(R.id.editImageView);
            dotImageView = itemView.findViewById(R.id.dotImageView);
        }
    }

    /**
     * Container Activity must implement this interface
     * you can define any parameter as per your requirement
     */
    public interface OnCartChangedListener {
        void setCartClickListener(String status, int position, String firstNameStr, String lastNameStr, String emailIdStr, String phonenoStr);

    }

    /**
     * @param onCartChangedListener
     */
    public void setOnCartChangedListener(OnCartChangedListener onCartChangedListener) {
        this.onCartChangedListener = onCartChangedListener;
    }
}
