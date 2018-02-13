package com.su.nuttawut.coffeepuppy.Activity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.su.nuttawut.coffeepuppy.Adapter.CartAdapter;
import com.su.nuttawut.coffeepuppy.R;
import com.su.nuttawut.coffeepuppy.RealmDB.realmOrder;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

public class Cart extends AppCompatActivity{

    private RequestQueue requestQueue;
    private StringRequest request;
    private static String URL_addCart = "http://192.168.1.12/register_login/addOrders.php";

    RecyclerView recyclerView;
    realmOrder realmOrder;
    int price,count;
    int countProduct;
    HashMap<String,String> stringHashMap;
    ArrayList<HashMap> map = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Realm.init(getApplicationContext());


        getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN

        );

        recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        realmOrder order = new realmOrder();

        CartAdapter adapter = new CartAdapter(order.getOrders(),this);
        recyclerView.setAdapter(adapter);


        for (int i = 0;i<order.getOrders().size();i++) {
            Log.e(Cart.class.getName(), "Price in order = " + order.getOrders().get(i).getFood_count() * order.getOrders().get(i).getFood_price() + "\n");
            price += (order.getOrders().get(i).getFood_count() * order.getOrders().get(i).getFood_price());

            countProduct += order.getOrders().get(i).getFood_count();
        }

        Log.e(Cart.class.getName(),"TotalPrice = "+price);
        TextView totalPrice =(TextView)findViewById(R.id.totalPrice);
        totalPrice.setText(String.valueOf(price)+" ฿");

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
                realmOrder order = new realmOrder();
                for (int i = 0; i < order.getOrders().size(); i++){
                    addData(String.valueOf(0004),
                            String.valueOf(order.getOrders().get(i).getFood_id()),
                            String.valueOf(order.getOrders().get(i).getFood_count()),
                            String.valueOf(order.getOrders().get(i).getFood_count()*order.getOrders().get(i).getFood_price()));
                    Log.e("Status : ","Add data "+"["+i+"] successful!");
                }
                order.clearAll();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(),"ยืนยันการสั่ง",Toast.LENGTH_LONG).show();
            }
        });

        EditText timePicker = (EditText) findViewById(R.id.pickTime);

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFlagment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });


    }

    public static class TimePickerFlagment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }

    public void addData (final String Order_id, final String Product_ID, final String Quantity, final String Amount){
        requestQueue = Volley.newRequestQueue(getBaseContext());
        request = new StringRequest(StringRequest.Method.POST, URL_addCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                realmOrder order = new realmOrder();
                HashMap<String,String> hashMap = new HashMap<>();

                    hashMap.put("Order_ID",Order_id);
                    hashMap.put("Product_ID",Product_ID);
                    hashMap.put("Quantity",Quantity);
                    hashMap.put("Amount",Amount);

                return hashMap;
            }

        };
        requestQueue.add(request);
    }

}

