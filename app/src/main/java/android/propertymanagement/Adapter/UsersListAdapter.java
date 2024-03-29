package android.propertymanagement.Adapter;

import android.content.Context;
import android.propertymanagement.Fragments.UsersFragment;
import android.propertymanagement.ModelClass.RequestModelClasses.GetUpdateUserAPIRequest;
import android.propertymanagement.ModelClass.RequestModelClasses.ResetPasswordAPIRequestModel;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetActiveUserAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllAccountUsersAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllPermissionAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetDeleteUserAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetUpdateUserAPIResponse;
import android.propertymanagement.ModelClass.ResponseModelClasses.ResetPasswordAPIResponse;
import android.propertymanagement.ModelClass.UserModel;
import android.propertymanagement.R;
import android.propertymanagement.Services.APIConstantURL;
import android.propertymanagement.Services.ExStreamApiService;
import android.propertymanagement.Services.ServiceFactory;
import android.propertymanagement.Utils.CommonUtil;
import android.propertymanagement.Utils.Constants;
import android.propertymanagement.Utils.SharedPrefsData;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
    private String firstStr, lastStr, emailStr, phoneStr, spinnerStr, editfirstnameStr,
            editlastnameStr, editemailstr, editphnoStr, selectedUseremail, passowrdResetStr, confirmPasswordResetStr;
    int spinnerSelectedId, selecteduserId;
    private String authorizationToken;
    private Subscription mSubscription;
    EditText emailEdt_dialog, password_dialog, confirmpass_dialog;
    Button cancelBtn, submitBtn;
    private AlertDialog alertDialog;
    ArrayList<GetAllPermissionAPIResponse> allPermissionAPIResponse;
    GetUpdateUserAPIResponse getUpdateUserAPIResponses;
    ArrayAdapter<String> adapter_permission;
    Boolean isActive_selected, isActive;
    UsersFragment fragment;

    public UsersListAdapter(Context mContext, ArrayList<GetAllAccountUsersAPIResponse> userModels, UsersFragment fragment) {
        this.mContext = mContext;
        this.userModels = userModels;
        this.fragment = fragment;
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
        ((TextViewHolder) holder).firstnameText.setVisibility(View.VISIBLE);
        ((TextViewHolder) holder).spinnerText.setVisibility(View.VISIBLE);
        ((TextViewHolder) holder).emailText.setVisibility(View.VISIBLE);
        ((TextViewHolder) holder).phonenoText.setVisibility(View.VISIBLE);
        ((TextViewHolder) holder).editImageView.setVisibility(View.VISIBLE);
        ((TextViewHolder) holder).dotImageView.setVisibility(View.VISIBLE);


        ((TextViewHolder) holder).firstnameText.setText(userModels.get(position).getFirstName() + " " +
                userModels.get(position).getLastName());
        ((TextViewHolder) holder).spinnerText.setText(userModels.get(position).getPermissionGroupName());
        ((TextViewHolder) holder).emailText.setText(userModels.get(position).getEmail());
        ((TextViewHolder) holder).phonenoText.setText(userModels.get(position).getPhoneNumber());
        //   selecteduserId = userModels.get(position).getUserId();
        isActive = userModels.get(position).getIsActive();
        if (isActive.equals(true)) {
            ((TextViewHolder) holder).editImageView.setClickable(true);
            ((TextViewHolder) holder).editImageView.setImageResource(R.drawable.icon_edit);
        } else if (isActive.equals(false)) {
            ((TextViewHolder) holder).editImageView.setClickable(false);
            ((TextViewHolder) holder).editImageView.setEnabled(false);
            ((TextViewHolder) holder).editImageView.setImageResource(R.drawable.edit_disable);
        }


        //   selectedUseremail = userModels.get(position).getEmail();

        ((TextViewHolder) holder).editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextViewHolder) holder).firstnameText.setVisibility(View.GONE);
                ((TextViewHolder) holder).emailText.setVisibility(View.GONE);
                ((TextViewHolder) holder).phonenoText.setVisibility(View.GONE);
                ((TextViewHolder) holder).spinnerText.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(userModels.get(position).getFirstName())) {
                    ((TextViewHolder) holder).firstnameEdt.setText(userModels.get(position).getFirstName());
                } else {
                    ((TextViewHolder) holder).firstnameEdt.setError(mContext.getString(R.string.err_fisrt_name));
                }
                ((TextViewHolder) holder).firstnameEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).lastnameEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).emailEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).spinnerEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).phonenoEdt.setVisibility(View.VISIBLE);

                ((TextViewHolder) holder).firstnameEdt.setText(userModels.get(position).getFirstName());
                ((TextViewHolder) holder).lastnameEdt.setText(userModels.get(position).getLastName());
                ((TextViewHolder) holder).emailEdt.setText(userModels.get(position).getEmail());

                ((TextViewHolder) holder).phonenoEdt.setText(userModels.get(position).getPhoneNumber());

                ((TextViewHolder) holder).editImageView.setVisibility(View.GONE);
                ((TextViewHolder) holder).dotImageView.setVisibility(View.GONE);
                ((TextViewHolder) holder).okImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).closeImageView.setVisibility(View.VISIBLE);
            }
        });
        ((TextViewHolder) holder).okImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editfirstnameStr = ((TextViewHolder) holder).firstnameEdt.getText().toString();
                spinnerSelectedId = allPermissionAPIResponse.get(((TextViewHolder) holder)
                        .spinnerEdt.getSelectedItemPosition() - 1).getPermissionGroupId();
                editlastnameStr = ((TextViewHolder) holder).lastnameEdt.getText().toString();
                editemailstr = ((TextViewHolder) holder).emailEdt.getText().toString();
                editphnoStr = ((TextViewHolder) holder).phonenoEdt.getText().toString();


                ((TextViewHolder) holder).firstnameEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).lastnameEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).emailEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).spinnerEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).phonenoEdt.setVisibility(View.GONE);


                ((TextViewHolder) holder).editImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).dotImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).okImageView.setVisibility(View.GONE);
                ((TextViewHolder) holder).closeImageView.setVisibility(View.GONE);

                int spinner_selected_id = allPermissionAPIResponse.get(((TextViewHolder) holder)
                        .spinnerEdt.getSelectedItemPosition() - 1).getPermissionGroupId();
                selecteduserId = userModels.get(position).getUserId();
                getUpdateUsers();


                onCartChangedListener.setCartClickListener("edit", position,
                        ((TextViewHolder) holder).firstnameEdt.getText().toString(),
                        ((TextViewHolder) holder).lastnameEdt.getText().toString(),
                        ((TextViewHolder) holder).emailEdt.getText().toString(),
                        ((TextViewHolder) holder).phonenoEdt.getText().toString(),
                        spinner_selected_id);
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
                ((TextViewHolder) holder).spinnerText.setVisibility(View.VISIBLE);


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
                isActive_selected = userModels.get(position).getIsActive();
                if (isActive_selected.equals(false)) {
                    popup.getMenu().findItem(R.id.active_userText).setVisible(true);
                    popup.getMenu().findItem(R.id.deactive_userText).setVisible(false);
                    popup.getMenu().findItem(R.id.reset_pwdText).setVisible(false);

                } else if (isActive_selected.equals(true)) {
                    popup.getMenu().findItem(R.id.active_userText).setVisible(false);
                    popup.getMenu().findItem(R.id.deactive_userText).setVisible(true);
                    popup.getMenu().findItem(R.id.reset_pwdText).setVisible(true);

                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.reset_pwdText:
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View dialogView = inflater.inflate(R.layout.dialog_rest_password, null);
                                dialogBuilder.setView(dialogView);
                                emailEdt_dialog = dialogView.findViewById(R.id.emailEdt_dialog);
                                password_dialog = dialogView.findViewById(R.id.password_dialog);
                                confirmpass_dialog = dialogView.findViewById(R.id.confirmpass_dialog);
                                cancelBtn = dialogView.findViewById(R.id.cancelBtn);
                                submitBtn = dialogView.findViewById(R.id.submitBtn);
                                emailEdt_dialog.setTypeface(ResourcesCompat.getFont(mContext, R.font.oswald_extralight));
                                password_dialog.setTypeface(ResourcesCompat.getFont(mContext, R.font.oswald_extralight));
                                confirmpass_dialog.setTypeface(ResourcesCompat.getFont(mContext, R.font.oswald_extralight));
                                selectedUseremail = userModels.get(position).getEmail();
                                emailEdt_dialog.setText(selectedUseremail);
                                // selectedUseremail = emailEdt_dialog.getText().toString();


                                alertDialog = dialogBuilder.create();
                                alertDialog.show();
                                /**
                                 * @param OnClickListner
                                 */
                                submitBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        getResetPassword();
                                        alertDialog.dismiss();

                                    }
                                });

                                cancelBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });

                                break;
                            case R.id.deactive_userText:
                                selecteduserId = userModels.get(position).getUserId();
                                getDeactivateUser();
                                break;

                            case R.id.active_userText:
                                selecteduserId = userModels.get(position).getUserId();
                                getactivateUser();
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
                        adapter_permission = new ArrayAdapter<String>(mContext, R.layout.spinner_text_black, permissionsList);

                        adapter_permission.setDropDownViewResource(R.layout.spinner_text_black);
                        ((TextViewHolder) holder).spinnerEdt.setAdapter(adapter_permission);
                        int spinner_txt = userModels.get(position).getPermissionGroupId();
                        if (userModels != null && userModels.get(position).getPermissionGroupId() != null &&
                                !userModels.get(position).getPermissionGroupId().equals(""))
                            for (int j = 0; j < allPermissionAPIResponse.size(); j++) {
                                if (allPermissionAPIResponse.get(j).getPermissionGroupId().equals(spinner_txt))
                                    ((TextViewHolder) holder).spinnerEdt.setSelection(j + 1);

                            }
                    }
                });
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

                        getUpdateUserAPIResponses = mResponse;
                        firstStr = getUpdateUserAPIResponses.getFirstName();
                        lastStr = getUpdateUserAPIResponses.getLastName();
                        emailStr = getUpdateUserAPIResponses.getEmail();
                        phoneStr = getUpdateUserAPIResponses.getPhoneNumber();
                        spinnerStr = getUpdateUserAPIResponses.getPermissionGroupName();
                        /*firstnameText.setVisibility(View.VISIBLE);
                        emailText.setVisibility(View.VISIBLE);
                        phonenoText.setVisibility(View.VISIBLE);
                        spinnerText.setVisibility(View.VISIBLE);*/

                        fragment.getAllAccountUsers();

                        /*firstnameText.setText(firstStr + " " +
                                lastStr);
                         emailText.setText(emailStr);
                         phonenoText.setText(phoneStr);
                         spinnerText.setText(spinnerStr);*/

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
        model.setUserId(selecteduserId);
        model.setFirstName(editfirstnameStr);
        model.setLastName(editlastnameStr);
        model.setEmail(editemailstr);
        model.setPhoneNumber(editphnoStr);
        model.setPermissionGroupId(spinnerSelectedId);
        model.setPassword("Admin123");
        model.setIsActive(true);
        model.setIsAllProperties(true);

        return new Gson().toJsonTree(model).getAsJsonObject();

    }


    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {


        EditText firstnameEdt, lastnameEdt, emailEdt, phonenoEdt;
        Spinner spinnerEdt;
        TextView firstnameText, spinnerText, emailText, phonenoText;
        ImageView okImageView, closeImageView, dotImageView, editImageView;


        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            firstnameEdt = itemView.findViewById(R.id.firstnameEdt);
            lastnameEdt = itemView.findViewById(R.id.lastnameEdt);
            emailEdt = itemView.findViewById(R.id.emailEdt);
            phonenoEdt = itemView.findViewById(R.id.phonenoEdt);
            spinnerEdt = itemView.findViewById(R.id.spinnerEdt);
            okImageView = itemView.findViewById(R.id.okImageView);
            closeImageView = itemView.findViewById(R.id.closeImageView);
            editImageView = itemView.findViewById(R.id.editImageView);
            dotImageView = itemView.findViewById(R.id.dotImageView);
            firstnameText = itemView.findViewById(R.id.firstnameText);
            spinnerText = itemView.findViewById(R.id.spinnerText);
            emailText = itemView.findViewById(R.id.emailText);
            phonenoText = itemView.findViewById(R.id.phonenoText);
        }
    }

    private void getDeactivateUser() {
        authorizationToken = SharedPrefsData.getString(mContext, Constants.access_token, Constants.PREF_NAME);
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.GetDeleteUser(APIConstantURL.GetDeleteUser + selecteduserId, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetDeleteUserAPIResponse>() {
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
                    public void onNext(GetDeleteUserAPIResponse mResponse) {
                        fragment.getAllAccountUsers();
                        CommonUtil.customToast(mResponse.getMessage(), mContext);

                    }
                });

    }

    private void getactivateUser() {
        authorizationToken = SharedPrefsData.getString(mContext, Constants.access_token, Constants.PREF_NAME);
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.GetActiveUser(APIConstantURL.GetActiveUser + selecteduserId, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetActiveUserAPIResponse>() {
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
                    public void onNext(GetActiveUserAPIResponse mResponse) {
                        fragment.getAllAccountUsers();
                        CommonUtil.customToast(mResponse.getMessage(), mContext);

                    }
                });

    }

    private void getResetPassword() {
        JsonObject object = addResetPasswordRequest();
        ExStreamApiService service = ServiceFactory.createRetrofitService(mContext, ExStreamApiService.class);
        mSubscription = service.putGetResetPasswordByAdmin(object, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResetPasswordAPIResponse>() {
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
                    public void onNext(ResetPasswordAPIResponse mResponse) {
                        fragment.getAllAccountUsers();
                        Toast.makeText(mContext, "" + mResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }


    /**
     * Json Object of addUpdateUserRequest
     *
     * @return
     */

    private JsonObject addResetPasswordRequest() {
        passowrdResetStr = password_dialog.getText().toString().trim();
        confirmPasswordResetStr = confirmpass_dialog.getText().toString().trim();
        ResetPasswordAPIRequestModel model = new ResetPasswordAPIRequestModel();
        model.setEmail(selectedUseremail);
        model.setPassword(passowrdResetStr);
        model.setConfirmPassword(confirmPasswordResetStr);
        return new Gson().toJsonTree(model).getAsJsonObject();

    }


    /**
     * Container Activity must implement this interface
     * you can define any parameter as per your requirement
     */
    public interface OnCartChangedListener {
        void setCartClickListener(String status, int position, String firstNameStr, String lastNameStr,
                                  String emailIdStr, String phonenoStr, int spinnerSelectId);

    }

    /**
     * @param onCartChangedListener
     */
    public void setOnCartChangedListener(OnCartChangedListener onCartChangedListener) {
        this.onCartChangedListener = onCartChangedListener;
    }


}
