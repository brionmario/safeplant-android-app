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
import com.apareciumlabs.brionsilva.safeplant.api.models.UrineReport;
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
public class UrineReportFragment extends Fragment {

    private EditText appearenceET = null;
    private EditText phET = null;
    private EditText gravityET = null;
    private EditText proteinET = null;
    private EditText sugarET = null;
    private EditText redcellsET = null;
    private EditText pusscellsET = null;
    private EditText ephicellsET = null;
    private EditText castsET = null;
    private EditText crystalsET = null;
    private EditText bileET = null;
    private EditText othersET = null;
    private Button submitButton;

    public UrineReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_urine_report, container, false);

        appearenceET = (EditText) view.findViewById(R.id.appearenceEditText);
        phET = (EditText) view.findViewById(R.id.phEditText);
        gravityET = (EditText) view.findViewById(R.id.spEditText);
        proteinET = (EditText) view.findViewById(R.id.proteinEditText);
        sugarET = (EditText) view.findViewById(R.id.sugarEditText);
        redcellsET = (EditText) view.findViewById(R.id.redCellsEditText);
        pusscellsET = (EditText) view.findViewById(R.id.pusCellsEditText);
        ephicellsET = (EditText) view.findViewById(R.id.ephiCellsEditText);
        castsET = (EditText) view.findViewById(R.id.castsEditText);
        crystalsET = (EditText) view.findViewById(R.id.crystalsEditText);
        bileET = (EditText) view.findViewById(R.id.bileEditText);
        othersET = (EditText) view.findViewById(R.id.othersEditText);
        submitButton = (Button) view.findViewById(R.id.submitUrineReportBtn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an instance of date time class
                DateTime dateTime = new DateTime();

                if(TextUtils.isEmpty(appearenceET.getText())){

                    appearenceET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(phET.getText())){

                    phET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(gravityET.getText())){

                    gravityET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(proteinET.getText())){

                    proteinET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(sugarET.getText())){

                    sugarET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(redcellsET.getText())){

                    redcellsET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(pusscellsET.getText())){

                    pusscellsET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(ephicellsET.getText())){

                    ephicellsET.setError("Please fill this field");

                } else if(TextUtils.isEmpty(castsET.getText())){

                    castsET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(crystalsET.getText())){

                    crystalsET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(bileET.getText())){

                    bileET.setError("Please fill this field");

                }else if(TextUtils.isEmpty(othersET.getText())){

                    othersET.setError("Please fill this field");

                } else {
                    //send to the rest api
                    UrineReport urineReport = new UrineReport(dateTime.getDate(), dateTime.getTime(),
                            appearenceET.getText().toString(), Double.parseDouble(phET.getText().toString()),
                            Double.parseDouble(gravityET.getText().toString()), proteinET.getText().toString(),
                            sugarET.getText().toString(), redcellsET.getText().toString(), pusscellsET.getText().toString(),
                            ephicellsET.getText().toString(), castsET.getText().toString(), crystalsET.getText().toString(),
                            bileET.getText().toString(), othersET.getText().toString());
                    postUrineReport(urineReport);

                    Toast.makeText(getActivity(), "Urine Report uploaded successfully", Toast.LENGTH_SHORT).show();

                    appearenceET.setText("");
                    phET.setText("");
                    gravityET.setText("");
                    proteinET.setText("");
                    sugarET.setText("");
                    redcellsET.setText("");
                    pusscellsET.setText("");
                    ephicellsET.setText("");
                    castsET.setText("");
                    crystalsET.setText("");
                    bileET.setText("");
                    othersET.setText("");

                }
            }
        });

        return view;
    }

    /**
     * This method makes a POST request to the api to save the urine report
     * @param urineReport Instance of the urineReport class
     */
    private void postUrineReport(UrineReport urineReport) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AppConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<UrineReport> call = apiClient.sendUrineReport(urineReport);

        call.enqueue(new Callback<UrineReport>() {
            @Override
            public void onResponse(Call<UrineReport> call, Response<UrineReport> response) {

            }

            @Override
            public void onFailure(Call<UrineReport> call, Throwable t) {
                //Log.d("Report POST failure " , t.toString());
            }
        });

    }
}
