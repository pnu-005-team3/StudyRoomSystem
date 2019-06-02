package com.example.studyroomsystem;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class ManagerReservationActivity extends AppCompatActivity {

    int ayear, amonth, aday;
    int cyear, cmonth, cday;
    EditText buid, clas, reas;
    FirebaseDatabase database;
    Calendar c;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_reservation);

        buid = (EditText)findViewById(R.id.buildingname);
        clas = (EditText)findViewById(R.id.studyroomname);
        reas = (EditText)findViewById(R.id.etreason);
        b = (Button)findViewById(R.id.btnreservation3);

        c = Calendar.getInstance();
        cyear = c.get(Calendar.YEAR);
        cmonth = c.get(Calendar.MONTH) + 1;
        cday = c.get(Calendar.DAY_OF_MONTH);

        database = FirebaseDatabase.getInstance();

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Toast.makeText(view.getContext(), String.valueOf(monthOfYear), Toast.LENGTH_SHORT).show();
                ayear= year;
                amonth = monthOfYear+1;
                aday = dayOfMonth;
            }
        };

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference("building").child(buid.getText().toString())
                        .child("Class"+clas.getText().toString()).child("noday")
                        .setValue(String.valueOf(ayear) + ' ' + String.valueOf(amonth) + ' ' + String.valueOf(aday));
                database.getReference("building").child(buid.getText().toString())
                        .child("Class"+clas.getText().toString()).child("nodaywhy")
                        .setValue(reas.getText().toString());

                if(ayear == cyear && amonth == cmonth && aday == cday) {
                    database.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot d : dataSnapshot.getChildren()) {
                                if(d.child("reservation").getValue() != null &&
                                        d.child("reservation").getValue().equals(buid.getText().toString()+"#"+clas.getText().toString())) {
                                    database.getReference("users").child(d.getKey()).child("reservation").setValue("예약 취소");
                                    database.getReference("users").child(d.getKey()).child("reservationwhy").setValue(reas.getText().toString());
                                }
                            }
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                } else {
                    finish();
                }
            }
        });

        new DatePickerDialog(this, listener, 2019, 5, 2).show();


    }
}
