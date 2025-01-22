package com.example.hospitalproject;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospitalproject.GetAlluserDetailsAdapter;
import com.example.hospitalproject.POJOGetAllUserDetails;
import com.example.hospitalproject.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AllDetailActivity extends AppCompatActivity {
    TextView tv_data_not_found;
    ListView lv_get_all_user_details;
    ProgressBar progress;


    ArrayList<POJOGetAllUserDetails> pojoGetAllUserDetails;
    GetAlluserDetailsAdapter getAlluserDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_detail);

        tv_data_not_found = findViewById(R.id.tv_no_data);
        lv_get_all_user_details = findViewById(R.id.lv_get_all_user_datail_list);
        progress = findViewById(R.id.pb_getalluserdata_progress);
        pojoGetAllUserDetails = new ArrayList<>();


        getAllUserDetails();

    }

    private void getAllUserDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        client.post(Urls.urlGetAllUserDetails, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("getAllUserDetails");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String mobile_no = jsonObject.getString("mobile_no");
                        String date_of_birth = jsonObject.getString("date_of_birth");
                        String blood_group = jsonObject.getString("blood_group");
                        String email_id = jsonObject.getString("email_id");
                        String username = jsonObject.getString("username");


                        pojoGetAllUserDetails.add(new POJOGetAllUserDetails(id, name, mobile_no, date_of_birth, blood_group, email_id, username));


                    }

                    getAlluserDetailsAdapter = new GetAlluserDetailsAdapter(pojoGetAllUserDetails, AllDetailActivity.this);

                    lv_get_all_user_details.setAdapter(getAlluserDetailsAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}