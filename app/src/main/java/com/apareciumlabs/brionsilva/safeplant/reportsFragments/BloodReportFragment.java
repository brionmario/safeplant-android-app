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
import com.apareciumlabs.brionsilva.safeplant.api.models.BloodReport;
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
public class BloodReportFragment extends Fragment {

    private EditText wbcET = null;
    private EditText rbcET = null;
    private EditText cellVolumeET = null;
    private EditText hemoglobinConcentrationET = null;
    private EditText corpuscularVolumeET = null;
    private EditText corpuscularHemoglobinET = null;
    private EditText corpuscularConcentrationET = null;
    private EditText plateletCountET = null;
    private Button submitButton;

    public BloodReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood_report, container, false);

        wbcET = (EditText) view.findViewById(R.id.wbcEditText);
        rbcET = (EditText) view.findViewById(R.id.rbcEditText);
        cellVolumeET = (EditText) view.findViewById(R.id.cellVolumeEditText);
        hemoglobinConcentrationET = (EditText) view.findViewById(R.id.hemoglobinConEditText);
        corpuscularVolumeET = (EditText) view.findViewById(R.id.corpuscularVolEditText);
        corpuscularHemoglobinET = (EditText) view.findViewById(R.id.corpuscularHemoEditText);
        corpuscularConcentrationET = (EditText) view.findViewById(R.id.corpuscularHemoConcEditText);
        plateletCountET = (EditText) view.findViewById(R.id.plateletEditText);
        submitButton = (Button) view.findViewById(R.id.submitBloodReportBtn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an instance of date time class
                DateTime dateTime = new DateTime();

                if(TextUtils.isEmpty(wbcET.getText())){

                    wbcET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(rbcET.getText())){

                    rbcET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(cellVolumeET.getText())){

                    cellVolumeET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(hemoglobinConcentrationET.getText())){

                    hemoglobinConcentrationET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(corpuscularVolumeET.getText())){

                    corpuscularVolumeET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(corpuscularHemoglobinET.getText())){

                    corpuscularHemoglobinET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(corpuscularConcentrationET.getText())){

                    corpuscularConcentrationET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(plateletCountET.getText())){

                    plateletCountET.setError("Please fill this field");

                }else {
                    //Send question to the rest api
                    BloodReport bloodReport = new BloodReport(dateTime.getDate(), dateTime.getTime(),
                            Double.parseDouble(wbcET.getText().toString()), Double.parseDouble(rbcET.getText().toString()),
                            Double.parseDouble(cellVolumeET.getText().toString()), Double.parseDouble(hemoglobinConcentrationET.getText().toString()),
                            Double.parseDouble(corpuscularVolumeET.getText().toString()), Double.parseDouble(corpuscularHemoglobinET.getText().toString()),
                            Double.parseDouble(corpuscularConcentrationET.getText().toString()), Double.parseDouble(plateletCountET.getText().toString()));
                    postBloodReport(bloodReport);

                    Toast.makeText(getActivity(), "Blood Report uploaded successfully", Toast.LENGTH_SHORT).show();

                    wbcET.setText("");
                    rbcET.setText("");
                    cellVolumeET.setText("");
                    hemoglobinConcentrationET.setText("");
                    corpuscularVolumeET.setText("");
                    corpuscularHemoglobinET.setText("");
                    corpuscularConcentrationET.setText("");
                    plateletCountET.setText("");
                }
            }
        });

        return view;
    }

    /**
     * This method makes a POST request to the api to save the blood report
     * @param bloodReport Instance of the bloodReport class
     */
    private void postBloodReport(BloodReport bloodReport) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AppConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<BloodReport> call = apiClient.sendBloodReport(bloodReport);

        call.enqueue(new Callback<BloodReport>() {
            @Override
            public void onResponse(Call<BloodReport> call, Response<BloodReport> response) {

            }

            @Override
            public void onFailure(Call<BloodReport> call, Throwable t) {
                //Log.d("Report POST failure " , t.toString());
            }
        });

    }

}
