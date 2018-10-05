package com.apareciumlabs.brionsilva.safeplant;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apareciumlabs.brionsilva.safeplant.Notifications.InAppNotifications;

import java.util.Random;

public class LoginScreen extends AppCompatActivity {

    EditText username, password;
    Button loginButton, joinButton;
    private int UNIQUE_ID; //for in-app notifications

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //initializing the textfields and buttons
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
        joinButton = (Button) findViewById(R.id.join_button);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification("Title","Ticker","Body",SplashScreen.class);
            }
        });


        //setting the password font to default
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*//empty field handling
                if(TextUtils.isEmpty(username.getText())){

                    username.setError("Please enter a username.");

                } else if(TextUtils.isEmpty(password.getText())){

                    password.setError("Please enter a password.");

                } else if (!TextUtils.isEmpty(username.getText()) && !TextUtils.isEmpty(password.getText())){

                    //login validation
                    if(username.getText().toString().equals("") &&
                            password.getText().toString().equals("")) {

                        Toast.makeText(getApplicationContext(),
                                "Redirecting...",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(),
                                Bluetooth.class);
                        startActivity(intent);

                    } else {

                        errorDialog("Login failed. Please enter a valid username and password.");

                    }
                }*/

                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(),
                        Bluetooth.class);
                startActivity(intent);

            }
        });

    }

    /**
     * This function creates a dialog box which takes
     * @param error String parameter which is passed
     * @Author Brion Mario
     */
    public void errorDialog(String error)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this , R.style.BrionDialogTheme);
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
     * This method creates a notification
     *
     * @param notificationTitle Title of the notification
     * @param notificationTicker Ticker Title
     * @param notificationBody Notification body context
     * @param targetActivity Activity that the notification opens when it's clicked
     *
     */
    public void createNotification(String notificationTitle, String notificationTicker, String notificationBody , Class targetActivity) {

        //creating a random id
        Random random = new Random();
        UNIQUE_ID = random.nextInt(10000+1);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker(notificationTicker)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert_sound))
                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                        .setContentTitle(notificationTitle)
                        .setWhen(System.currentTimeMillis())
                        .setContentText(notificationBody);

        //creating the target intent
        Intent notificationIntent = new Intent(this, targetActivity);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        //build the notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(UNIQUE_ID, builder.build());

    }
}