package com.example.hospitalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    ImageView blood_data;
    ImageView hospital_data;
//    ListView lv_get_all_user_details;
//    ProgressBar progress;


        ArrayList<POJOGetAllUserDetails> pojoGetAllUserDetails;
          GetAlluserDetailsAdapter getAlluserDetailsAdapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.fragment_home, container, false);

          blood_data=view.findViewById(R.id.layout_blood_service);
          hospital_data=view.findViewById(R.id.hospital_image);

//          tv_data_not_found=view.findViewById(R.id.tv_no_data);
//          lv_get_all_user_details=view.findViewById(R.id.lv_get_all_user_datail_list);
//          progress=view.findViewById(R.id.pb_getalluserdata_progress);
//        pojoGetAllUserDetails=new ArrayList<>();


//        getAllUserDetails();





        blood_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),AllDetailActivity.class);
                startActivity(i);

                       }
        });

        hospital_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),MapsActivity.class);
                startActivity(i);

            }
        });
        return view;
    }


//    private void getAllUserDetails() {
//        AsyncHttpClient client= new AsyncHttpClient();
//        RequestParams params=new RequestParams();
//
//
//        client.post(Urls.urlGetAllUserDetails,params,new JsonHttpResponseHandler(){
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
//
//                try {
//                    JSONArray jsonArray=response.getJSONArray(Integer.parseInt("getAllUserDetails"));
//
//
//                    for(int i=0; i<jsonArray.length();i++)
//
//                    {
//                        JSONObject jsonObject=jsonArray.getJSONObject(i);
//                        String id=jsonObject.getString("id");
//                        String name=jsonObject.getString("name");
//                        String mobile_no=jsonObject.getString("mobile_no");
//                        String date_of_birth=jsonObject.getString("date_of_birth");
//                        String blood_group=jsonObject.getString("blood_group");
//                        String email_id=jsonObject.getString("email_id");
//                        String username=jsonObject.getString("username");
//
//                        pojoGetAllUserDetails.add(new POJOGetAllUserDetails(id,name,mobile_no,date_of_birth,blood_group,email_id,username));
//
//
//                    }
//
//                    getAlluserDetailsAdapter=new GetAlluserDetailsAdapter(pojoGetAllUserDetails,HomeFragment.this);
//
//                    lv_get_all_user_details.setAdapter(getAlluserDetailsAdapter);
//                }
//
//
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });


    }
