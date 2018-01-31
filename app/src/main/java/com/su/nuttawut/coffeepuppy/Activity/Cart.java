package com.su.nuttawut.coffeepuppy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.su.nuttawut.coffeepuppy.Adapter.CartAdapter;
import com.su.nuttawut.coffeepuppy.R;
import com.su.nuttawut.coffeepuppy.RealmDB.realmOrder;

import io.realm.Realm;

public class Cart extends AppCompatActivity{

    RecyclerView recyclerView;
    realmOrder realmOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Realm.init(getApplicationContext());


        recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        realmOrder order = new realmOrder();

        CartAdapter adapter = new CartAdapter(order.getOrders(),this);
        recyclerView.setAdapter(adapter);


//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        productList = new ArrayList<>();
//
//        loadProducts();
//
        Button Confirm = (Button) findViewById(R.id.ConfirmBTN);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realmOrder = new realmOrder();
                realmOrder.clearAll();
                confirm();
                Toast.makeText(getApplicationContext(),"Hello Test",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void confirm(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}
