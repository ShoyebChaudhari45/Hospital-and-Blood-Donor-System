package com.example.hospitalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistationActivity extends AppCompatActivity {

    EditText et_register_name,  et_register_mobilenumber, et_date_of_birth, et_blood_group, et_email_id, et_username, et_password;
    Button btn_registernow;
    TextView tv_oldAc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
        getSupportActionBar().hide();

        et_register_name = findViewById(R.id.et_register_name);
        et_register_mobilenumber = findViewById(R.id.et_register_mobilenumber);
        et_date_of_birth = findViewById(R.id.et_register_date_of_birth);
        et_blood_group = findViewById(R.id.et_register_blood_group);
        et_email_id = findViewById(R.id.et_register_email_id);
        et_username = findViewById(R.id.et_register_username);
        et_password = findViewById(R.id.et_register_password);
        btn_registernow = findViewById(R.id.btn_registernow);
        tv_oldAc=findViewById(R.id.tv_login_newuser1);

        tv_oldAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3=new Intent(RegistationActivity.this,LoginPageActivity.class);
                startActivity(i3);
            }
        });



        btn_registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_register_name.getText().toString().isEmpty())
                {
                    et_register_name.setError("Please Enter Your Name");
                }
                else if (et_register_mobilenumber.getText().toString().isEmpty())
                {
                    et_register_mobilenumber.setError("Please Enter Your Mobile Number");
                }
                else if (et_date_of_birth.getText().toString().isEmpty())
                {
                    et_date_of_birth.setError("Please enter your correct Date of Birth");
                }
                else if(et_date_of_birth.getText().toString().equals(15)){
                    et_date_of_birth.setError("Age should be greater than 18");
                }


                else if (et_blood_group.getText().toString().isEmpty())
                {
                    et_blood_group.setError("Please enter your Blood group");
                }
                else if (et_username.getText().toString().length() < 8)
                {
                    et_username.setError("Username must be greater then 8");
                }
                else if (!et_email_id.getText().toString().contains("@") || !et_email_id.getText().toString().contains(".com"))
                {
                    et_username.setError("Please Enter Your Email ID");
                }
                else if (et_password.getText().toString().length() < 8)
                {
                    et_password.setError("Password must be greater then 8");
                }
                else
                {
                    Toast.makeText(RegistationActivity.this, "Registration Successfully Done!!",
                                Toast.LENGTH_SHORT).show();
                    Intent i4 =new Intent(RegistationActivity.this,LoginPageActivity.class);
                    startActivity(i4);
//

                    addUserDetails();

                }
            }
        });
    }

    private void addUserDetails() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",et_register_name.getText().toString());
        params.put("mobile_no",et_register_mobilenumber.getText().toString());
        params.put("date_of_birth",et_date_of_birth.getText().toString());
        params.put("blood_group",et_blood_group.getText().toString());
        params.put("email_id",et_email_id.getText().toString());
        params.put("username",et_username.getText().toString());
        params.put("password",et_password.getText().toString());


        client.post(Urls.urlRegisteruser,params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
//                  JSONArray jsonArray = new JSONArray(response);
                    String isSuccess = response.getString("success");

                    if (isSuccess.equals("1"))
                    {
                        Toast.makeText(RegistationActivity.this, "Registration Successfully Done!!",
                                Toast.LENGTH_SHORT).show();



                    }
                    else
                    {
                        Toast.makeText(RegistationActivity.this, "Invalid Data!!", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(RegistationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RegistationActivity.this,LoginPageActivity.class);
        startActivity(i);
        finish();
    }
}
