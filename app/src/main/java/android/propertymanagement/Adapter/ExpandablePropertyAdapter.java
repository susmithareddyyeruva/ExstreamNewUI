package android.propertymanagement.Adapter;

import android.content.Context;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetPropertySetupResponce;
import android.propertymanagement.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandablePropertyAdapter extends BaseExpandableListAdapter {
    public static final String TAG= ExpandablePropertyAdapter.class.getSimpleName();
    private ArrayList<GetPropertySetupResponce> propertyList;
    private Context mCtx;

    public ExpandablePropertyAdapter(ArrayList<GetPropertySetupResponce> propertyList, Context mCtx) {
        this.propertyList = propertyList;
        this.mCtx = mCtx;
    }

    @Override
    public int getGroupCount() {

        return propertyList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return propertyList.get(groupPosition).getSetupProcess();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle =propertyList.get(groupPosition).getPropertyName();
        Log.e(TAG,"---- analysis --- Title :"+listTitle);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mCtx.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lst_property_mainitem, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        GetPropertySetupResponce property = propertyList.get(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mCtx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lst_property_item, null);
        }
        RadioButton rd_info = convertView.findViewById(R.id.rd_info);
        RadioButton rd_property = convertView.findViewById(R.id.rd_property);
        RadioButton rd_unit = convertView.findViewById(R.id.rd_unit);
        RadioButton rd_property_signage = convertView.findViewById(R.id.rd_property_signage);
        RadioButton rd_users = convertView.findViewById(R.id.rd_users);
        RadioButton rd_rent_ = convertView.findViewById(R.id.rd_rent_);
        RadioButton rd_property_walk = convertView.findViewById(R.id.rd_property_walk);

        rd_info.setChecked(property.getSetupProcess().getPropertyInformation());
        rd_property.setChecked(property.getSetupProcess().getPropertyValueAdd());
        rd_unit.setChecked(property.getSetupProcess().getUnitDemographics());
        rd_property_signage.setChecked(property.getSetupProcess().getPropertySignage());
        rd_users.setChecked(property.getSetupProcess().getPropertyUsers());
        rd_rent_.setChecked(property.getSetupProcess().getRentRollSetup());
        rd_property_walk.setChecked(property.getSetupProcess().getPropertyWalk());

      /*  TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
       // expandedListTextView.setText(expandedListText);*/
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
