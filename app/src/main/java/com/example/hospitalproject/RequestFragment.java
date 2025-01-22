package com.example.hospitalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RequestFragment extends Fragment {

    EditText patient_fname, patient_lname, patient__mobilenumber, patient_date_of_birth, patient_blood_group, patient_email_id, patient_location, additional_note;
    Button btn_send_request;
    CheckBox ch1_read;
    TextView terms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        patient_fname = view.findViewById(R.id.firstname);
        patient_lname = view.findViewById(R.id.lastname);
        patient__mobilenumber = view.findViewById(R.id.request_mobile_no);
        patient_date_of_birth = view.findViewById(R.id.request_date_of_birth);
        patient_blood_group = view.findViewById(R.id.request_blood_group);
        patient_email_id = view.findViewById(R.id.request_email_id);
        patient_location = view.findViewById(R.id.reuest_loc);
        additional_note = view.findViewById(R.id.additional_note);
        btn_send_request = view.findViewById(R.id.send_request2);
        ch1_read = view.findViewById(R.id.ch1);
        terms = view.findViewById(R.id.agree);

        btn_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patient_fname.getText().toString().isEmpty()) {
                    patient_fname.setError("Please Enter Your Name");
                } else if (patient_lname.getText().toString().isEmpty()) {
                    patient_lname.setError("Please Enter Your Last Name");
                } else if (patient__mobilenumber.getText().toString().isEmpty()) {
                    patient__mobilenumber.setError("Please Enter Your Mobile Number");
                } else if (patient_date_of_birth.getText().toString().isEmpty()) {
                    patient_date_of_birth.setError("Please enter your correct Date of Birth");
                } else if (patient_blood_group.getText().toString().isEmpty()) {
                    patient_blood_group.setError("Please enter your Blood group");
                } else if (patient_location.getText().toString().length() < 4) {
                    patient_location.setError("Location must be greater than 4 characters");
                } else if (!patient_email_id.getText().toString().contains("@") || !patient_email_id.getText().toString().contains(".com")) {
                    patient_email_id.setError("Please Enter Your Email ID");
                } else if (additional_note.getText().toString().length() < 3) {
                    additional_note.setError("Note must be greater than 3 characters");
                } else {
                    addUserDetails();
                }
            }
        });
        return view;
    }

    private void addUserDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("patient_fname", patient_fname.getText().toString());
        params.put("patient_lname", patient_lname.getText().toString());
        params.put("patient_mobile", patient__mobilenumber.getText().toString());
        params.put("p_date_of_birth", patient_date_of_birth.getText().toString());
        params.put("p_blood_group", patient_blood_group.getText().toString());
        params.put("p_email", patient_email_id.getText().toString());
        params.put("patient_location", patient_location.getText().toString());
        params.put("additionalnote", additional_note.getText().toString());

        client.post(Urls.urlrequestblood, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String isSuccess = response.getString("success");
                    if (isSuccess.equals("1")) {
                        Toast.makeText(getActivity(), "Request Successfully Sent!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Failed to send request", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getActivity(), "Server Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
