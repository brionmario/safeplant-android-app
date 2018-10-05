package com.apareciumlabs.brionsilva.safeplant;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.apareciumlabs.brionsilva.safeplant.Scheduler.ChangeAppointmentScreen;
import com.apareciumlabs.brionsilva.safeplant.Scheduler.CreateAppointmentScreen;
import com.apareciumlabs.brionsilva.safeplant.DBHandler.MyDBHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchedulerFragment extends Fragment implements View.OnClickListener{


    private Button crtAppoBtn,editAppoBtn,delAppoBtn,moveAppoBtn;
    CalendarView calendarView;

    private String date;
    PopupWindow popupWindow;

    MyDBHandler myDBHandler;

    //delete popup
    Button deleteAllBtn , selectDeleteBtn;

    //create popup
    EditText titleET, timeET, detailsET;
    Button saveBtn;
    MyDBHandler dbHandler;

    public SchedulerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scheduler,container,false);

        //initializing the buttons and adding onclick
        crtAppoBtn = (Button) v.findViewById(R.id.createAppointmentButton);
        crtAppoBtn.setOnClickListener(this);

        editAppoBtn = (Button) v.findViewById(R.id.viewEditButton);
        editAppoBtn.setOnClickListener(this);

        delAppoBtn = (Button) v.findViewById(R.id.deleteAppointmentButton);
        delAppoBtn.setOnClickListener(this);

        moveAppoBtn = (Button) v.findViewById(R.id.moveAppointmentButton);
        moveAppoBtn.setOnClickListener(this);


        calendarView = (CalendarView) v.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateSelected = simpleDateFormat.format(new GregorianCalendar(year, month, dayOfMonth).getTime());
                date = dateSelected;
                //Toast.makeText(getBaseContext(),dateSelected,Toast.LENGTH_SHORT).show();
            }
        });

        //initialize the default date  and assign it to the date variable in case if he user doesn't
        //click on any date.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateSelected = simpleDateFormat.format(new Date(calendarView.getDate()));
        date = dateSelected;

        //creates an instance of the MyDBHandler
        myDBHandler = new MyDBHandler(getActivity(),null,null,1);

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.createAppointmentButton :{

                Intent intent = new Intent(getActivity() , CreateAppointmentScreen.class);
                intent.putExtra("Date" , date ); // format - dd/MM/yyyy
                startActivity(intent);

                break;

            }
            case R.id.deleteAppointmentButton : {
                deleteAppointmentPopup(v);
                break;
            }
            case R.id.viewEditButton:{
                Intent intent = new Intent(getActivity(), ChangeAppointmentScreen.class);
                intent.putExtra("Date" , date ); // format - dd/MM/yyyy
                intent.putExtra("Change Type" , "Edit" );
                startActivity(intent);
                break;
            }
            case R.id.moveAppointmentButton :{
                Intent intent = new Intent(getActivity(), ChangeAppointmentScreen.class);
                intent.putExtra("Date" , date ); // format - dd/MM/yyyy
                intent.putExtra("Change Type" , "Move" );
                startActivity(intent);
                break;
            }
        }

    }

    /**
     * This junction creates just a simple popup window that has two buttons
     *
     * @param v The current view instance is passed
     */
    private void deleteAppointmentPopup (View v) {

        try {
            View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

            // display the popup in the center
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            //Deletes all the appointments for a given date
            deleteAllBtn = (Button) popupView.findViewById(R.id.delAllButton);
            deleteAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getBaseContext(),"Deleted all the appointments on "+ date,Toast.LENGTH_LONG).show();
                    myDBHandler.deleteAppointments(date);
                    popupWindow.dismiss();
                }
            });

            //Opens up the list of appointments for the given date
            selectDeleteBtn = (Button) popupView.findViewById(R.id.selectedDelButton);
            selectDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity().getBaseContext() , ChangeAppointmentScreen.class);
                    intent.putExtra("Date" , date ); // format - dd/MM/yyyy
                    intent.putExtra("Change Type" , "Delete" );
                    startActivity(intent);
                    popupWindow.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
