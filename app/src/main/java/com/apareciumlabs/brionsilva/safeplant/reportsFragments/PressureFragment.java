package com.apareciumlabs.brionsilva.safeplant.reportsFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apareciumlabs.brionsilva.safeplant.R;
import com.apareciumlabs.brionsilva.safeplant.api.models.BloodPressure;
import com.apareciumlabs.brionsilva.safeplant.api.models.BloodSugar;
import com.apareciumlabs.brionsilva.safeplant.api.service.ApiClient;
import com.apareciumlabs.brionsilva.safeplant.config.AppConfig;
import com.apareciumlabs.brionsilva.safeplant.utility.DateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PressureFragment extends Fragment {

    private EditText systolicET = null;
    private EditText diastolicET = null;
    private Button submitBtn;

    public PressureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pressure, container, false);

        systolicET = (EditText) view.findViewById(R.id.systolicEditText);
        diastolicET = (EditText) view.findViewById(R.id.diastolicEditText);
        submitBtn = (Button) view.findViewById(R.id.submitPressureReportBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an instance of date time class
                DateTime dateTime = new DateTime();

                if(TextUtils.isEmpty(systolicET.getText())){

                    systolicET.setError("Please fill this field");

                } else if(TextUtils.isEmpty(diastolicET.getText())){

                    diastolicET.setError("Please fill this field");

                } else {
                    BloodPressure bloodPressure = new BloodPressure(dateTime.getDate(), dateTime.getTime(),
                            Integer.parseInt(diastolicET.getText().toString()), Integer.parseInt(systolicET.getText().toString()));

                    postPressureReport(bloodPressure);

                    Toast.makeText(getActivity(), "Blood Pressure Report uploaded successfully", Toast.LENGTH_SHORT).show();

                    systolicET.setText("");
                    diastolicET.setText("");
                }
            }
        });

        return view;
    }

    /**
     * This method makes a POST request to the api to save the blood pressure report
     * @param bloodPressure Instance of the BloodPressure class
     */
    private void postPressureReport(BloodPressure bloodPressure) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AppConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<BloodPressure> call = apiClient.sendBloodPressure(bloodPressure);

        call.enqueue(new Callback<BloodPressure>() {
            @Override
            public void onResponse(Call<BloodPressure> call, Response<BloodPressure> response) {

            }

            @Override
            public void onFailure(Call<BloodPressure> call, Throwable t) {
                //Log.d("Report POST failure " , t.toString());
            }
        });

    }

}
