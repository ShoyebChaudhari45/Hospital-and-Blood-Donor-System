package com.example.hospitalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


        public class MyProfileActivity extends AppCompatActivity {

            TextView tv_name, tv_mobile_no,tv_emai_id,tv_username,tv_blood_group,tv_dob;
            Button btn_back;


            SharedPreferences preferences;
            SharedPreferences.Editor editor;
            String login_username;

            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_my_profile);

                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                editor = preferences.edit();
                login_username = preferences.getString("username","");

                tv_name = findViewById(R.id.tv_my_profile_name);
                tv_mobile_no = findViewById(R.id.tv_my_profile_mobile_no);
                tv_blood_group = findViewById(R.id.tv_my_profile_blood_group);
                tv_emai_id = findViewById(R.id.tv_my_profile_email_id);
                tv_username = findViewById(R.id.tv_my_profile_user_name);
                tv_dob = findViewById(R.id.tv_my_profile_user_dob);
                btn_back=findViewById(R.id.btn_my_profile_back);


                getMyInfo();


            }

            private void getMyInfo(){
                AsyncHttpClient client= new AsyncHttpClient();
                RequestParams params = new RequestParams();

                params.put("username",login_username);

                client.post(Urls.urlGetMyDetails,params,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try{
                            JSONArray jsonArray = response.getJSONArray("getMyDetails");
                            for(int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject= jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String mobile_no = jsonObject.getString("mobile_no");
                                String email_id = jsonObject.getString("email_id");
                                String username = jsonObject.getString("username");
                                String blood_group = jsonObject.getString("blood_group");
                                String dob = jsonObject.getString("date_of_birth");


                                tv_name.setText(name);
                                tv_mobile_no.setText(mobile_no);
                                tv_blood_group.setText(blood_group);
                                tv_emai_id.setText(email_id);
                                tv_username.setText(username);
                                tv_dob.setText(dob);
                                Toast.makeText(MyProfileActivity.this,""+name,Toast.LENGTH_SHORT).show();


                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });


                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MyProfileActivity.this,HomeFragment.class);
                        startActivity(intent);
                    }
                });
            }



}