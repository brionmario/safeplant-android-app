package com.apareciumlabs.brionsilva.safeplant;


import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apareciumlabs.brionsilva.safeplant.DBHandler.MyDBHandler;
import com.apareciumlabs.brionsilva.safeplant.api.models.BodyTemperature;
import com.apareciumlabs.brionsilva.safeplant.api.models.HeartRate;
import com.apareciumlabs.brionsilva.safeplant.api.service.ApiClient;
import com.apareciumlabs.brionsilva.safeplant.config.AppConfig;
import com.apareciumlabs.brionsilva.safeplant.utility.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brionsilva on 07/03/2017.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //DB Handler instance
    MyDBHandler myDBHandler;

    TextView heartRate, bodyTemperature , bloodPressure;
    Handler bluetoothIn;

    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    // String for MAC address, passed from the previous intent
    private static String address;
    private int UNIQUE_ID;

    //Permission for SMS api
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =100 ;
    String phoneNo;
    String message;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Intent intent = getActivity().getIntent();
        address = intent.getStringExtra("EXTRA_DEVICE_ADDRESS");
        Toast.makeText(getActivity(),"Connected to "+address,Toast.LENGTH_SHORT).show();


        //Link textViews to respective views
        heartRate = (TextView) view.findViewById(R.id.tv_heartRate);
        bodyTemperature = (TextView) view.findViewById(R.id.tv_temp_value);
        bloodPressure = (TextView) view.findViewById(R.id.tv_pressure_status);

        //creating a new db handler object
        myDBHandler = new MyDBHandler(getContext(),null,null,1);


        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) { //if message is what we want
                    String readMessage = (String) msg.obj; // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");// determine the end-of-line
                    if (endOfLineIndex > 0) { // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);// extract string
                        //txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length(); //get length of data received
                        //txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')   //if it starts with # we know it is what we are looking for
                        {
                            String[] msgArray = recDataString.toString().split(",");
                            String heartBeat = msgArray[0];
                            String temperature = msgArray[1];
                            String pressure = msgArray[2];
                            String stateSOS = msgArray[3];

                            //remove the # from the heartbeat
                            String BPM = heartBeat.replaceAll("[#]","");

                            //Notifying the user in case of any abnormalities
                            if (Integer.parseInt(BPM) > 100) {

                                createNotification("Heart rate is high", "Alert - Heart Rate",
                                        "Your heart rate is quite high. Please consult a doctor." , HomeScreen.class , R.raw.alert_sound);

                            } else if (Integer.parseInt(BPM) < 60) {

                                createNotification("Heart rate is Low", "Alert - Heart Rate",
                                        "Your heart rate is getting pretty low. Please consult a doctor." , HomeScreen.class , R.raw.alert_sound);

                            }

                            if(stateSOS.equals("1")){
                                //Toast.makeText(getContext(),"SOS not clicked",Toast.LENGTH_SHORT).show();
                            } else if (stateSOS.equals("0")){
                                //Creates a notification and emits tthe alert sound
                                createNotification("Emergency", "Alert - SOS",
                                        "Hang on. Help is on the way. SMS sent to the emergency contact." , HomeScreen.class , R.raw.alert_sos);

                                //send the distress SMS
                                sendSMSMessage();

                                //Makes a call after 5 seconds
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        emergencyCall();
                                    }
                                }, 5000);

                            }

                            //update the textviews with sensor values
                            heartRate.setText(BPM);
                            bodyTemperature.setText(temperature);
                            bloodPressure.setText(pressure);

                            //create an instance of date time class
                            DateTime dateTime = new DateTime();

                            //Send heart rate to the rest api
                            HeartRate heartRate = new HeartRate(dateTime.getDate(), dateTime.getTime(), Integer.parseInt(BPM));
                            postHeartRate(heartRate);

                            //send body temperature to the rest api
                            BodyTemperature bodyTemperature = new BodyTemperature(dateTime.getDate(), dateTime.getTime(), Double.parseDouble(temperature));
                            postBodyTemperature(bodyTemperature);

                            new UpdateThingspeakTask("https://api.thingspeak.com/update?api_key=KGH22CN1ARR9BERB&field1="+temperature).execute();

                            new UpdateThingspeakTask2("https://api.thingspeak.com/update?api_key=KGH22CN1ARR9BERB&field2="+BPM).execute();

                            new UpdateThingspeakTask2("https://api.thingspeak.com/update?api_key=KGH22CN1ARR9BERB&field3="+ pressureToThingspeak(pressure)).execute();

                        }

                        recDataString.delete(0, recDataString.length());                    //clear all string data

                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        return view;
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getActivity().getIntent();
        address = intent.getStringExtra("EXTRA_DEVICE_ADDRESS");
        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getActivity(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                //Toast.makeText(getActivity(), "Connection Failure", Toast.LENGTH_LONG).show();
                //finish();

            }
        }

    }

    /**
     * This method returns an integer value based on the blood
     * pressure to be sent to the thingspeak cloud.
     * @param pressure Blood pressure of the patient
     * @return 0 if Normal, 1 if High, -1 if Low and -1000 in any other cases
     */
    public int pressureToThingspeak(String pressure){

        int a;

        switch (pressure){
            case "NORMAL":{
                a = 0;
                break;
            }
            case "HIGH":{
                a = 1;
                break;
            }
            case "LOW":{
                a = -1;
                break;
            }
            default:{
                a = -1000;
                break;
            }
        }

        return a;
    }

    /**
     * This method creates a notification
     *
     * @param notificationTitle Title of the notification
     * @param notificationTicker Ticker Title
     * @param notificationBody Notification body context
     * @param targetActivity Activity that the notification opens when it's clicked
     *
     */
    public void createNotification(String notificationTitle, String notificationTicker,
                                   String notificationBody , Class targetActivity , int alertType) {

        //creating a random id
        Random random = new Random();
        UNIQUE_ID = random.nextInt(10000+1);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker(notificationTicker)
                        .setSound(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + alertType))
                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                        .setContentTitle(notificationTitle)
                        .setWhen(System.currentTimeMillis())
                        .setContentText(notificationBody);

        //creating the target intent
        Intent notificationIntent = new Intent(getContext(), targetActivity);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        //build the notification
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(UNIQUE_ID, builder.build());

    }

    /**
     * This method sends a SMS when the SOS button is clicked
     **/
    protected void sendSMSMessage() {
        phoneNo = myDBHandler.getSOS();
        message = "SOS button clicked. Patient is in danger.";

        //Getting intent and PendingIntent instance
        Intent intent=new Intent(this.getContext(),HomeScreen.class);
        PendingIntent pi=PendingIntent.getActivity(getContext(), 0, intent,0);

        //Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, message, null,null);

        errorDialog("A distress SMS has been sent to the emergency contact.");
    }

    /**
     * This method makes an automatic call when the SOS button is clicked
     **/
    protected void emergencyCall() {
        phoneNo = myDBHandler.getSOS();

        Intent mIntent = new Intent(Intent.ACTION_CALL);
        mIntent.setData(Uri.parse("tel:" + phoneNo));
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            try {
                startActivity(mIntent);
            } catch(SecurityException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    emergencyCall();
                } else {

                    // permission denied
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * This function creates a dialog box which takes
     * @param error String parameter which is passed
     * @Author Brion Mario
     */
    public void errorDialog(String error)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext() , R.style.BrionDialogTheme);
        builder.setMessage(error);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * This method makes a POST request to the api to save heart rate data
     * @param heartRate Instance of the heart rate class
     */
    private void postHeartRate(HeartRate heartRate) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AppConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<HeartRate> call = apiClient.sendHeartRate(heartRate);

        call.enqueue(new Callback<HeartRate>() {
            @Override
            public void onResponse(Call<HeartRate> call, Response<HeartRate> response) {

            }

            @Override
            public void onFailure(Call<HeartRate> call, Throwable t) {
                //Log.d("Heart Rate POST failure " , t.toString());
            }
        });

    }

    /**
     * This method makes a POST request to the api to save body temperature data
     * @param bodyTemperature Instance of the bodyTemperature class
     */
    private void postBodyTemperature(BodyTemperature bodyTemperature) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AppConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<BodyTemperature> call = apiClient.sendBodyTemperature(bodyTemperature);

        call.enqueue(new Callback<BodyTemperature>() {
            @Override
            public void onResponse(Call<BodyTemperature> call, Response<BodyTemperature> response) {

            }

            @Override
            public void onFailure(Call<BodyTemperature> call, Throwable t) {
                //Log.d("Temperature POST failure " , t.toString());
            }
        });

    }
}