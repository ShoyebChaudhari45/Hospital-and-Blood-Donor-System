package com.example.hospitalproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GetAlluserDetailsAdapter extends BaseAdapter {

    ArrayList<POJOGetAllUserDetails> pojoGetAllUserDetails;
    Activity activity;


    public GetAlluserDetailsAdapter(ArrayList<POJOGetAllUserDetails> pojoGetAllUserDetails, AllDetailActivity activity) {
        this.pojoGetAllUserDetails = pojoGetAllUserDetails;
        this.activity= activity;
    }



    public  int getCount(){
        return pojoGetAllUserDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoGetAllUserDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final GetAlluserDetailsAdapter.viewHolder holder;
//        AccessibilityService context = null;


        LayoutInflater inflater=(LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view==null)
        {
            holder=new GetAlluserDetailsAdapter.viewHolder();
            view =inflater.inflate(R.layout.lv_get_all_users_data,null);

            holder.tv_name=view.findViewById(R.id.tv_my_profile_name1);
            holder.tv_mobile_no=view.findViewById(R.id.tv_my_profile_mobile_no1);
            holder.tv_date_of_birth=view.findViewById(R.id.tv_my_profile_user_dob1);
            holder.tv_blood_group=view.findViewById(R.id.tv_my_profile_blood_group1);
            holder.tv_email_id=view.findViewById(R.id.tv_my_profile_email_id1);
            holder.tv_username=view.findViewById(R.id.tv_my_profile_user_name1);
            holder.btn_call=view.findViewById(R.id.make_call);

            view.setTag(holder);

        }
        else
        {
            holder=(GetAlluserDetailsAdapter.viewHolder) view.getTag();

        }
        final POJOGetAllUserDetails obj=pojoGetAllUserDetails.get(position);
        holder.tv_name.setText(obj.getName());
        holder.tv_mobile_no.setText(obj.getMobile_no());
        holder.tv_date_of_birth.setText(obj.getDate_of_birth());
        holder.tv_blood_group.setText(obj.getBlood_group());
        holder.tv_email_id.setText(obj.getEmail_id());
        holder.tv_username.setText(obj.getUsername());

        holder.btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                String p = "tel:" + obj.getMobile_no();
                i.setData(Uri.parse(p));
                activity.startActivity(i);
            }
        });




        return view;
    }



    class viewHolder
    {
        TextView tv_name,tv_mobile_no,tv_date_of_birth,tv_blood_group,tv_email_id,tv_username;
        Button btn_call;
    }

}
