package com.apareciumlabs.brionsilva.safeplant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apareciumlabs.brionsilva.safeplant.DBHandler.MyDBHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class SOSFragment extends Fragment {

    private EditText phoneNumberET;
    private Button sosButton;
    private TextView selectedNo,sosheading;

    MyDBHandler myDBHandler;

    public SOSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sos,container,false);


        //SOS
        phoneNumberET = (EditText) v.findViewById(R.id.phone_no);
        sosButton = (Button) v.findViewById(R.id.sos_button);
        selectedNo = (TextView) v.findViewById(R.id.emergency_contact);
        sosheading = (TextView) v.findViewById(R.id.sosInputTitle);

        myDBHandler = new MyDBHandler(getContext(),null,null,1);

        if(myDBHandler.getSOS().equals("") || myDBHandler.getSOS().equals(null)){

            sosheading.setText("Please enter a valid mobile number");
            selectedNo.setText("No contact entered");
            sosButton.setText("ADD NUMBER");

        } else {
            sosheading.setText("Please enter a different mobile number if you wish to update");
            selectedNo.setText("You have selected " + myDBHandler.getSOS() + " as your emergency SOS contact.");
            sosButton.setText("UPDATE NUMBER");

        }

        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDBHandler.clearTable("patients");
                myDBHandler.addSOSContact(phoneNumberET.getText().toString());

                //refresh the fragment
                getFragmentManager().beginTransaction().detach(SOSFragment.this).attach(SOSFragment.this).commit();
            }
        });

        return v;
    }

}
