package com.su.nuttawut.coffeepuppy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.su.nuttawut.coffeepuppy.R;
import com.su.nuttawut.coffeepuppy.RealmDB.realmOrder;
import com.su.nuttawut.coffeepuppy.Data.CartDetail;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

import static com.su.nuttawut.coffeepuppy.Activity.MainActivity.EXTRA_URL;
import static com.su.nuttawut.coffeepuppy.Activity.MainActivity.EXTRA_name;
import static com.su.nuttawut.coffeepuppy.Activity.MainActivity.EXTRA_price;

public class FoodDetail extends AppCompatActivity{

    realmOrder Order;
    String imageurl;
    String namee;
    double price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);
        Realm.init(getApplicationContext());


        final Intent intent = getIntent();
        this.imageurl = intent.getStringExtra(EXTRA_URL);
        this.namee = intent.getStringExtra(EXTRA_name);
        this.price = intent.getDoubleExtra(EXTRA_price,0.00);
//        Toast.makeText(this,"Price : "+price, Toast.LENGTH_LONG).show();


        ImageView imageView =(ImageView) findViewById(R.id.f_food_image);
        TextView textViewname =(TextView) findViewById(R.id.f_food_name);
        TextView textViewprice =(TextView) findViewById(R.id.f_food_price);

        Picasso.with(this).load(imageurl).fit().centerInside().into(imageView);
        textViewname.setText(namee);
        textViewprice.setText("ราคา "+ (int)price);
//        textViewPrice.setText(String.valueOf(product.getPrice() + " ฿"));

        Button cartBtn = (Button) findViewById(R.id.cartBTN);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getBaseContext(),"KKKKKKKK",Toast.LENGTH_LONG).show();
                Order = new realmOrder();
                List<CartDetail> detailList = new ArrayList<>();
                Intent send = new Intent(FoodDetail.this,Cart.class);
                CartDetail cartDetail = new CartDetail();
                cartDetail.setOrder_id(Order.nextid());
                cartDetail.setFood_name(namee);
                cartDetail.setFood_price(price);
                cartDetail.setFood_count(3);
                detailList.add(cartDetail);

                for (CartDetail detail : detailList){
                    Order.addOrders(detail);
                    Log.e(FoodDetail.class.getName(),detail.getOrder_id()+"\n"+detail.getFood_id()+"\n"+detail.getFood_name());

                }
//                Toast.makeText(getBaseContext(),"Add "+namee +" "+price +"฿",Toast.LENGTH_LONG).show();
//                finish();
                startActivity(send);
            }
        });



    }



}
