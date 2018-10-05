package com.apareciumlabs.brionsilva.safeplant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apareciumlabs.brionsilva.safeplant.api.models.Feedback;
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
public class AskQuestionFragment extends Fragment {

    private EditText askQuestionET = null;
    private Button submitQuestionBtn;

    public AskQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_question, container, false);

        askQuestionET = (EditText) view.findViewById(R.id.askQuestionEditText);
        submitQuestionBtn = (Button) view.findViewById(R.id.submitQuestionBtn);

        submitQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an instance of date time class
                DateTime dateTime = new DateTime();

                if(TextUtils.isEmpty(askQuestionET.getText())){

                    askQuestionET.setError("Please enter a question first");

                } else {
                    //Send question to the rest api
                    Feedback feedback = new Feedback(dateTime.getDate(), dateTime.getTime(), askQuestionET.getText().toString() , 0);
                    postFeedback(feedback);
                    Toast.makeText(getActivity(), "Question Posted Successfully", Toast.LENGTH_SHORT).show();
                    askQuestionET.setText("");
                }
            }
        });

        return view;
    }

    /**
     * This method makes a POST request to the api to save the question
     * @param feedback Instance of the feedback class
     */
    private void postFeedback(Feedback feedback) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AppConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<Feedback> call = apiClient.sendFeedback(feedback);

        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {

            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                //Log.d("Feedback POST failure " , t.toString());
            }
        });

    }
}
