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
public class SugarReportFragment extends Fragment {

    private EditText fbsET = null;
    private Button submitBtn;

    public SugarReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sugar_report, container, false);

        fbsET = (EditText) view.findViewById(R.id.fbsEditText);
        submitBtn = (Button) view.findViewById(R.id.submitSugarReportBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an instance of date time class
                DateTime dateTime = new DateTime();

                if(TextUtils.isEmpty(fbsET.getText())){

                    fbsET.setError("Please fill this field");

                } else {
                    BloodSugar bloodSugar = new BloodSugar(dateTime.getDate(), dateTime.getTime(),
                            Double.parseDouble(fbsET.getText().toString()));

                    postSugarReport(bloodSugar);

                    Toast.makeText(getActivity(), "Blood Sugar Report uploaded successfully", Toast.LENGTH_SHORT).show();

                    fbsET.setText("");
                }
            }
        });

        return view;
    }

    /**
     * This method makes a POST request to the api to save the blood sugar report
     * @param bloodSugar Instance of the BloodSugar class
     */
    private void postSugarReport(BloodSugar bloodSugar) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AppConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<BloodSugar> call = apiClient.sendBloodSugar(bloodSugar);

        call.enqueue(new Callback<BloodSugar>() {
            @Override
            public void onResponse(Call<BloodSugar> call, Response<BloodSugar> response) {

            }

            @Override
            public void onFailure(Call<BloodSugar> call, Throwable t) {
                //Log.d("Report POST failure " , t.toString());
            }
        });

    }
}
