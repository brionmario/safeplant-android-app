package com.apareciumlabs.brionsilva.safeplant;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private TextView HelpTV = null;
    private EditText Name = null;
    private EditText Mobile = null;
    private EditText Message = null;
    private Button Send;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        HelpTV = (TextView) view.findViewById(R.id.helpTV);

        Name = (EditText) view.findViewById(R.id.nameEditText);
        Mobile = (EditText) view.findViewById(R.id.mobileEditText);
        Message = (EditText) view.findViewById(R.id.askQuestionEditText);
        Send=(Button) view.findViewById(R.id.submitQuestionBtn) ;

        Send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String message = Message.getText().toString();
                String name = Name.getText().toString();
                String mobile = Mobile.getText().toString();
                sendEmail(message,name,mobile);
            }
        });

        return view;
    }

    private void sendEmail(String message, String name, String mobile) {
        String[] to = new String[]{"teamscorpion@apareciumlabs.com"};
        String subject = ("A message from Safe Plant app " + "( Name - " + name + " / Mobile - " +mobile+ " )");
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,message);
        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent,"Email"));
    }

}
