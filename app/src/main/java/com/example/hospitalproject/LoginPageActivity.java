package com.example.hospitalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class LoginPageActivity extends AppCompatActivity {

    private TextView tv_login_newuser;
    private EditText et_login_username, et_login_password;
    private Button btn_login;
    private CheckBox chk_show;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().hide();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        if (preferences.getBoolean(KEY_IS_LOGIN, false)) {
            Intent intent = new Intent(LoginPageActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        tv_login_newuser = findViewById(R.id.tv_login_newuser);
        et_login_password = findViewById(R.id.et_login_password);
        et_login_username = findViewById(R.id.et_login_username);
        btn_login = findViewById(R.id.btn_loginhere);
        chk_show = findViewById(R.id.chk_log_show_hide);

        chk_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_login_username.getText().toString().isEmpty()) {
                    et_login_username.setError("Please Enter Your Username");
                } else if (et_login_password.getText().toString().isEmpty()) {
                    et_login_password.setError("Please Enter Your Password");
                } else {
                    checkLoginUser();
                }
            }
        });

        tv_login_newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPageActivity.this, RegistationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkLoginUser() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        // Adding parameters for login
        params.put("username", et_login_username.getText().toString());
        params.put("password", et_login_password.getText().toString());

        // Making the HTTP POST request
        client.post(Urls.urlLoginuser, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    // Log the entire response to see what you're receiving
                    Log.d("Response", response.toString());

                    // Check if the "success" key exists in the response
                    if (response.has("success")) {
                        String isSuccess = response.getString("success");

                        if (isSuccess.equals("1")) {
                            // Successful login, proceed to HomeActivity
                            Toast.makeText(LoginPageActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();

                            // Save login state and navigate to HomeActivity
                            editor.putString("username", et_login_username.getText().toString()).commit();
                            editor.putBoolean("isLogin", true).commit();

                            Intent i1 = new Intent(LoginPageActivity.this, HomeActivity.class);
                            i1.putExtra("User", et_login_username.getText().toString());
                            i1.putExtra("Pass", et_login_password.getText().toString());
                            startActivity(i1);
                            finish();
                        } else {
                            // Handle case when login is not successful
                            Toast.makeText(LoginPageActivity.this, "Invalid Data!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        // Handle case when "success" key is missing
                        Toast.makeText(LoginPageActivity.this, "Unexpected response from server", Toast.LENGTH_SHORT).show();
                        Log.e("LoginError", "Key 'success' not found in response");
                    }

                } catch (JSONException e) {
                    // Handle JSON parsing errors
                    e.printStackTrace();
                    Toast.makeText(LoginPageActivity.this, "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("LoginError", "JSON Parsing Error: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                // Log the error response
                if (errorResponse != null) {
                    Log.e("LoginError", "Error Response: " + errorResponse.toString());
                } else {
                    Log.e("LoginError", "Error: " + throwable.getMessage());
                }

                // Show a toast message for server error
                Toast.makeText(LoginPageActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}