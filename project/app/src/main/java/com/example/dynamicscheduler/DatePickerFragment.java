package com.example.dynamicscheduler;



import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.TimePicker;



/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private String dateString;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);

        /*.........Set a custom title for picker........*/
        TextView tvTitle = new TextView(getActivity());
        tvTitle.setText("TimePickerDialog Title");
        tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
        tvTitle.setPadding(5, 3, 5, 3);
        tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        dpd.setCustomTitle(tvTitle);
        /*.........End custom title section........*/


        return dpd;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        String year_string = Integer.toString(year);
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        if(month_string.length() == 1){
            month_string = "0"+month_string;
        }
        if(day_string.length() == 1){
            day_string = "0"+day_string;
        }
        dateString = year_string + "-" + month_string + "-" +day_string;
       // System.out.println(dateString);
//        TextView status = (TextView)getActivity().findViewById(R.id.time_button);
//        Button button1 = (Button)getActivity().findViewById(R.id.ce_starttime);
//        Button button2 = (Button)getActivity().findViewById(R.id.ce_stoptime);
        Button button3 = (Button)getActivity().findViewById(R.id.ce_setdate);
        button3.setText(dateString);
//        if(status.getText().toString().equals("1")){
//            button1.setText(timeString);
//        }else{
//            button2.setText(timeString);
//        }

    }
}