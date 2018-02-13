package com.su.nuttawut.coffeepuppy.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.su.nuttawut.coffeepuppy.Adapter.ProductAdapter;
import com.su.nuttawut.coffeepuppy.Data.Product;
import com.su.nuttawut.coffeepuppy.R;
import com.su.nuttawut.coffeepuppy.RealmDB.realmOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener{

    private static final String URL_PRODUCT = "http://192.168.1.12/register_login/get_products.php";
    private DrawerLayout bDrawerLayout;
    private ActionBarDrawerToggle bToggle;

    //ใหม่2
    public static final String EXTRA_URL ="imageurl";
    public static final String EXTRA_name ="namee";
    public static final String EXTRA_price ="price";
    final String PREF_NAME = "LoginPreferences";


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    List<Product> productList;
    RecyclerView recyclerView;

    TextView Nametxt,Emailtxt;
    TextView textCartItemCount;
    MenuItem statusOrder,Login,Logout;
    int countProduct;
    ImageView cartpage;
    boolean statuslogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        editor = preferences.edit();

        Realm.init(getApplicationContext());
        realmOrder order = new realmOrder();
        for (int i = 0;i<order.getOrders().size();i++) {
            countProduct += order.getOrders().get(i).getFood_count();
        }
        Log.e("Cart ",""+countProduct);


//        เนฟบาร์ ทำให้ลิ้งค์หน้าหลัก
        bDrawerLayout = (DrawerLayout) findViewById(R.id.contentpage);
        bToggle = new ActionBarDrawerToggle(this,bDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        bDrawerLayout.addDrawerListener(bToggle);
        bToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Emailtxt = (TextView)findViewById(R.id.Emailtxt);
        statuslogin = preferences.getBoolean("CheckLogin",false);
        Log.e("Show in MainActivity",preferences.getString("CustomerName","Email"));
//        Nametxt.setText(preferences.getString("CustomerName","Name"));
//        Emailtxt.setText(preferences.getString("Email","Email"));



        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        changedStatusItemMenu(navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        Nametxt = (TextView) headerView.findViewById(R.id.CusNametxt);
        Emailtxt = (TextView) headerView.findViewById(R.id.Emailtxt);
        Nametxt.setText(preferences.getString("CustomerName","Name"));
        Emailtxt.setText(preferences.getString("Email","Email"));





//        ส่วนของการเรียกข้อมูลในดาต้าเบสมาโชว์
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        productList = new ArrayList<>();


        loadProducts();



//        ImageView cartpage = (ImageView) findViewById(R.id.cart_withe);
//        cartpage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,Cart.class);
//                startActivity(intent);
//            }
//        });




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
        intentt.putExtra("foodid",clickedItem.getProduct_ID());
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
        if (id == R.id.orderstatus){
            Toast.makeText(getApplicationContext(), "News", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.logout){
            Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(this);
            setting.edit().clear().commit();
            startActivity(new Intent(this, MainActivity.class));
            Log.e("Check Status Login",""+this.statuslogin);
        }


        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trash,menu);


        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Cart.class));
                onOptionsItemSelected(menuItem);
            }
        });

        return true;

//        return super.onCreateOptionsMenu(menu);
    }



    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.action_cart: {
//                // Do something
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (countProduct == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(countProduct, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void changedStatusItemMenu(NavigationView navigationView){
        Login = navigationView.getMenu().getItem(0);
        statusOrder = navigationView.getMenu().getItem(1);
        Logout = navigationView.getMenu().getItem(4);

          Login.setVisible(!this.statuslogin);
          statusOrder.setVisible(this.statuslogin);
          Logout.setVisible(this.statuslogin);


      Log.e("Check Status Login",""+this.statuslogin);
    }






    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.cartmenu,menu);
//        mCartIconMenuItem=menu.findItem(R.id.cart_count_menu_item);
////        View actionView=mCartIconMenuItem.getActionView();
//        View action = MenuItemCompat.getActionView(mCartIconMenuItem);
//        mCountTv = (TextView) action.findViewById(R.id.cout_tv_layout);



//        Cart cart = new Cart();
//        Bundle bundle = getIntent().getExtras();



//        aImageBtn = (ImageButton) actionView.findViewById(R.id.image_btn_layout);
//        if (actionView != null){
//            mCountTv=(TextView) actionView.findViewById(R.id.cout_tv_layout);
//            aImageBtn=(ImageButton) actionView.findViewById(R.id.image_btn_layout);
//            mCountTv.setText(String.valueOf(countProduct));
//        }

//        aImageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getBaseContext(), Cart.class));
//            }
//        });


//        return super.onCreateOptionsMenu(menu);
//    }






//    void openCart(){startActivity(new Intent(this,Cart.class));}





}
