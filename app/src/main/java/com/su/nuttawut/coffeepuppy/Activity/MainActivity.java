package com.su.nuttawut.coffeepuppy.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.su.nuttawut.coffeepuppy.Adapter.ProductAdapter;
import com.su.nuttawut.coffeepuppy.Data.Product;
import com.su.nuttawut.coffeepuppy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener{

    private static final String URL_PRODUCT = "http://192.168.1.12/register_login/get_products.php";
    private DrawerLayout bDrawerLayout;
    private ActionBarDrawerToggle bToggle;

    //ใหม่2
    public static final String EXTRA_URL ="imageurl";
    public static final String EXTRA_name ="namee";
    public static final String EXTRA_price ="price";

    List<Product> productList;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        เนฟบาร์ ทำให้ลิ้งค์หน้าหลัก
        bDrawerLayout = (DrawerLayout) findViewById(R.id.contentpage);
        bToggle = new ActionBarDrawerToggle(this,bDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        bDrawerLayout.addDrawerListener(bToggle);
        bToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

//        ส่วนของการเรียกข้อมูลในดาต้าเบสมาโชว์
        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        productList = new ArrayList<>();

        loadProducts();





    }

//    การกดปุ่มของเนฟบารื ทำให้เนฟบาร์สมูท
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (bToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    ส่วนเรียกข้อมูลมาโชว์
    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);

                                productList.add(new Product(
                                        product.getInt("Product_ID"),
                                        product.getString("StatusProduct"),
                                        product.getString("Category_ID"),
                                        product.getString("ProductName"),
                                        product.getDouble("Price"),
                                        product.getString("Picture"),
                                        product.getString("Discription")));

                                ProductAdapter adapter = new ProductAdapter(MainActivity.this, productList, this);
                                recyclerView.setAdapter(adapter);

                                //ใหม่
                                ProductAdapter.setOnItemClickListener(MainActivity.this);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(this).add(stringRequest);





    }
    //ใหม่2 มาการอิมพลิเม้นนะ
    @Override
    public void onItemClick(int position) {
        Intent intentt = new Intent(this,FoodDetail.class);
        Product clickedItem = productList.get(position);
        intentt.putExtra(EXTRA_URL,clickedItem.getPicture());
        intentt.putExtra(EXTRA_name,clickedItem.getProductName());
        intentt.putExtra(EXTRA_price,clickedItem.getPrice());
//        Toast.makeText(this,"Price : "+clickedItem.getPrice(), Toast.LENGTH_LONG).show();
        startActivity(intentt);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.login){
            Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Login.class));
        }
        if(id == R.id.condition){
            Toast.makeText(getApplicationContext(), "Condition", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.news){
            Toast.makeText(getApplicationContext(), "News", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.driver){
            Toast.makeText(getApplicationContext(), "Driver", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
