// Teerapat Voradanunt 5709650047
// Tanapat Sriautrarawong 5709650286

package com.example.zako.assignment1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private int mYear;
    private int mMonth;
    private int mDay;

    private TextView mDateDisplay;
    private Button mPickDate;

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDateDisplay = (TextView) findViewById(R.id.showMyDate);
        mPickDate = (Button) findViewById(R.id.myDatePickerButton);

        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date
        updateDisplay();

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Resources resources = getResources();
                final String[] fields = resources.getStringArray(R.array.field_main1);

                int i, editTextSize = fields.length;

                // Create EditText Variable
                final int[] res = {R.id.editText1, R.id.editText2, R.id.editText4, R.id.editText5};
                EditText[] edt = new EditText[editTextSize];
                for (i=0; i<editTextSize; i++){
                    edt[i] = (EditText) findViewById(res[i]);
                }

                final String[] regex = {"^[A-Z]{1}([A-Z]{2,}|[a-z]{2,})$",
                                        "^[A-Z]{1}([A-Z]{2,}|[a-z]{2,})$",
                                        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                                        "^0[0-9]{9}$"};
                final String[] error_msg = {"Name must contain at least three character and start with uppercase character",
                                            "Lastname must contain at least three character and start with uppercase character",
                                            "Invalid email address.",
                                            "Phone number must contain ten digits"};
                for (i=0; i<editTextSize; i++){
                    String str = edt[i].getText().toString();
                    // Check empty.
                    if(str.equals("")){
                        edt[i].setError("Error! " + fields[i] + " is empty.");
                        edt[i].requestFocus();
                        return;
                    }
                    // Check RegEx.
                    Pattern p = Pattern.compile(regex[i]);
                    Matcher matcher = p.matcher(str);
                    if(!matcher.find()){
                        edt[i].setError(error_msg[i]);
                        edt[i].requestFocus();
                        return;
                    }
                }

                int age = calculateAge(mYear, mMonth, mDay);
                if(age < 0 || age > 150) return;

                // Send Value to Main2Activity
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                for (i=0; i<editTextSize; i++){
                    intent.putExtra(fields[i], edt[i].getText().toString());
                }
                intent.putExtra(getString(R.string.field3), age+"");
                startActivity(intent);

            }
        });
    }

    private void updateDisplay() {
        this.mDateDisplay.setText(
                new StringBuilder()
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private int calculateAge(int byear, int bmonth, int bday){

        Calendar today = Calendar.getInstance();
        int todayYear  = today.get(Calendar.YEAR);
        int todayMonth = today.get(Calendar.MONTH);
        int todayDay   = today.get(Calendar.DAY_OF_MONTH);
        today.clear();
        today.set(todayYear, todayMonth, todayDay);

        Calendar birthDate = Calendar.getInstance();
        birthDate.set(byear, bmonth, bday);
        if (birthDate.after(today)) {
            Toast.makeText(getBaseContext(), "Error! Can't be born in the future", Toast.LENGTH_SHORT).show();
            return -1;
        }

        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
            age--;

        }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
                (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
            age--;
        }

        return age;
    }
}
