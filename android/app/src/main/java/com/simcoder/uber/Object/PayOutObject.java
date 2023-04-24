package com.simcoder.uber.Objects;

import android.content.Context;
import android.text.format.DateFormat;

import com.google.firebase.database.DataSnapshot;

import java.math.BigDecimal;
import java.util.Calendar;

public class PayoutObject {

    String id, amount, date;
    Long timestamp;
    Context context;

    public PayoutObject(Context context){
        this.context = context;
    }

    public void parseData(DataSnapshot dataSnapshot){
        id = dataSnapshot.getKey();

        if(dataSnapshot.child("amount").getValue()!=null){
            float amountInt = Float.parseFloat(dataSnapshot.child("amount").getValue().toString()) / 100;
            amount = String.valueOf(amountInt);
        }
        if(dataSnapshot.child("created").getValue() != null){
            timestamp = Long.valueOf(dataSnapshot.child("created").getValue().toString());
        }

        Calendar cal = Calendar.getInstance(context.getResources().getConfiguration().locale);
        cal.setTimeInMillis(timestamp * 1000);
        date = DateFormat.format("dd-MM-yyyy", cal).toString();
    }
    public String getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public BigDecimal round(int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(Float.parseFloat(amount)));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

}
