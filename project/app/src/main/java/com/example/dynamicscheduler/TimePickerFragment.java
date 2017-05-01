package com.example.dynamicscheduler;



        import android.app.AlertDialog;
        import android.app.TimePickerDialog;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.app.Fragment;
        import android.text.format.DateFormat;
        import android.view.Gravity;
        import android.widget.Button;
        import android.widget.TextView;
        import android.app.DialogFragment;
        import android.app.Dialog;
        import java.util.Calendar;
        import android.widget.TimePicker;



/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private String timeString;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        /*
            public constructor.....
            TimePickerDialog(Context context, int theme,
             TimePickerDialog.OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView)

            The 'theme' parameter allow us to specify the theme of TimePickerDialog

            .......List of Themes.......
            THEME_DEVICE_DEFAULT_DARK
            THEME_DEVICE_DEFAULT_LIGHT
            THEME_HOLO_DARK
            THEME_HOLO_LIGHT
            THEME_TRADITIONAL

         */
        TimePickerDialog tpd = new TimePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK
                ,this, hour, minute, DateFormat.is24HourFormat(getActivity()));

        //You can set a simple text title for TimePickerDialog
        //tpd.setTitle("Title Of Time Picker Dialog");

        /*.........Set a custom title for picker........*/
        TextView tvTitle = new TextView(getActivity());
        tvTitle.setText("TimePickerDialog Title");
        tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
        tvTitle.setPadding(5, 3, 5, 3);
        tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        tpd.setCustomTitle(tvTitle);
        /*.........End custom title section........*/

        return tpd;
    }




    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        //TextView tv = (TextView) getActivity().findViewById(R.id.tv);
        //Set a message for user
        String currentHour;
        String currentMin;
        //Get the AM or PM for current time
//        String aMpM = "AM";
//        if(hourOfDay >11)
//        {
//            aMpM = "PM";
//        }
//
//        //Make the 24 hour time format to 12 hour time format
//        int currentHour;
        if(hourOfDay>9)
        {
            currentHour = String.valueOf(hourOfDay);
        }
        else
        {
            currentHour = "0" + String.valueOf(hourOfDay);
        }

        if(minute>9)
        {
            currentMin = String.valueOf(minute);
        }
        else
        {
            currentMin = "0" + String.valueOf(minute);
        }
        timeString = currentHour + ":" + currentMin;
        TextView status = (TextView)getActivity().findViewById(R.id.time_button);
        Button button1 = (Button)getActivity().findViewById(R.id.ce_lengthtime);
        Button button2 = (Button)getActivity().findViewById(R.id.ce_stoptime);
        if(status.getText().toString().equals("1")){
            button1.setText(timeString);
        }else{
            button2.setText(timeString);
        }

//        tv.setText("Your chosen time is...\n\n");
//        //Display the user changed time on TextView
//        tv.setText(tv.getText()+ String.valueOf(currentHour)
           //     + " : " + String.valueOf(minute) + " " + aMpM + "\n");



    }

    public String getTimeString(){return timeString;}
}