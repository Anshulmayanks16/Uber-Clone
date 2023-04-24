package com.simcoder.uber.Payment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.simcoder.uber.Adapters.CardAdapter;
import com.simcoder.uber.Objects.CardObject;
import com.simcoder.uber.R;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    ArrayList<CardObject> cardArrayList = new ArrayList<>();
    ImageView mAddCard;

    PaymentUtils.CardListCallback cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cb = cardArrayList -> {
            this.cardArrayList = cardArrayList;
            runOnUiThread(this::initializeRecyclerView);
        };

        setupToolbar();

        mAddCard = findViewById(R.id.add_card_image);
        mAddCard.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, AddPaymentActivity.class);
            startActivity(intent);
        });
    }

   
    public void initializeBottomSheetDialog(CardObject mCard){

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_card, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.cancel_button).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.default_button).setOnClickListener(v -> {
            new PaymentUtils().setDefaultCard(mCard.getId(), cb, getApplicationContext());
            dialog.dismiss();
        });
        dialog.findViewById(R.id.delete_button).setOnClickListener(v -> {
            new PaymentUtils().removeCard(mCard.getId(), cb, getApplicationContext());
            dialog.dismiss();
        });
        dialog.show();

    }
    
    private void setupToolbar() {
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.payment));
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(v -> finish());
    }

   
    public void initializeRecyclerView(){
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new CardAdapter(cardArrayList, PaymentActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new PaymentUtils().fetchCardsList(cb, getApplicationContext());
    }
}
