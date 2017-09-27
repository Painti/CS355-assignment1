// Teerapat Voradanunt 5709650047
// Tanapat Sriautrarawong 5709650286

package com.example.zako.assignment1;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Resources resources = getResources();
        final String[] fields = resources.getStringArray(R.array.field_main2);

        int i, textViewSize = fields.length;

        // Create TextView Variable and store value from MainActivity
        final int[] res = {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5};
        String val[] = new String[textViewSize];
        TextView[] txt = new TextView[textViewSize];
        for (i=0; i<textViewSize; i++){
            txt[i] = (TextView) findViewById(res[i]);
            val[i] = getIntent().getStringExtra(fields[i]);
            txt[i].setText(val[i]);
        }

        // Append "years".
        int age_field = 2;
        txt[age_field].setText(txt[age_field].getText() + " years");

        // Edit format phone number.
        int phone_field = 4;
        String phone_number = val[phone_field].substring(0,3) + "-"
                + val[phone_field].substring(3,6) + "-"
                + val[phone_field].substring(6,10);
        txt[phone_field].setText(phone_number);

        // Choose Image
        ImageView img = (ImageView)findViewById(R.id.imageView);
        int age = Integer.parseInt(val[2]);
        if(age <= 15){
            img.setImageResource(R.drawable.child);
        } else if(age <= 25){
            img.setImageResource(R.drawable.teen);
        } else if(age <= 60){
            img.setImageResource(R.drawable.man);
        } else {
            img.setImageResource(R.drawable.oldman);
        }

    }
}
