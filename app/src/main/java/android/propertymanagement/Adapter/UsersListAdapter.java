package android.propertymanagement.Adapter;

import android.content.Context;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetAllAccountUsersAPIResponse;
import android.propertymanagement.ModelClass.UserModel;
import android.propertymanagement.R;
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

import java.util.ArrayList;
import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter {

    Context mContext;
    ArrayList<GetAllAccountUsersAPIResponse> userModels;

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
        ((TextViewHolder) holder).emailText.setText(userModels.get(position).getEmail());
        ((TextViewHolder) holder).phonenoText.setText(userModels.get(position).getPhoneNumber());


        /*((TextViewHolder) holder).editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextViewHolder) holder).firstnameText.setVisibility(View.GONE);
                ((TextViewHolder) holder).emailText.setVisibility(View.GONE);
                ((TextViewHolder) holder).phonenoText.setVisibility(View.GONE);

                ((TextViewHolder) holder).firstnameEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).lastnameEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).emailEdt.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).phonenoEdt.setVisibility(View.VISIBLE);

                ((TextViewHolder) holder).firstnameEdt.setText(userModels.get(position).getFirstName());
                ((TextViewHolder) holder).lastnameEdt.setText(userModels.get(position).getLastName());
                ((TextViewHolder) holder).emailEdt.setText(userModels.get(position).getEmail());
                ((TextViewHolder) holder).phonenoEdt.setText(userModels.get(position).getPhone());


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
                ((TextViewHolder) holder).phonenoText.setText(userModels.get(position).getPhone());

                ((TextViewHolder) holder).firstnameText.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).emailText.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).phonenoText.setVisibility(View.VISIBLE);

                ((TextViewHolder) holder).firstnameEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).lastnameEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).emailEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).phonenoEdt.setVisibility(View.GONE);


                ((TextViewHolder) holder).editImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).dotImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).okImageView.setVisibility(View.GONE);
                ((TextViewHolder) holder).closeImageView.setVisibility(View.GONE);
            }
        });

        ((TextViewHolder) holder).closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextViewHolder) holder).firstnameText.setText(userModels.get(position).getFirstName() + " " +
                        userModels.get(position).getLastName());
                ((TextViewHolder) holder).emailText.setText(userModels.get(position).getEmail());
                ((TextViewHolder) holder).phonenoText.setText(userModels.get(position).getPhone());

                ((TextViewHolder) holder).firstnameText.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).emailText.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).phonenoText.setVisibility(View.VISIBLE);

                ((TextViewHolder) holder).firstnameEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).lastnameEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).emailEdt.setVisibility(View.GONE);
                ((TextViewHolder) holder).phonenoEdt.setVisibility(View.GONE);

                ((TextViewHolder) holder).editImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).dotImageView.setVisibility(View.VISIBLE);
                ((TextViewHolder) holder).okImageView.setVisibility(View.GONE);
                ((TextViewHolder) holder).closeImageView.setVisibility(View.GONE);
            }
        });*/

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
}
