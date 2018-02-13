package com.su.nuttawut.coffeepuppy.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    boolean status_login = false;
    private RequestQueue requestQueue;
    private StringRequest request;
    private static  final String URL = "http://192.168.1.12/register_login/logins.php";
    final String PREF_NAME = "LoginPreferences";


    Button Login;
    EditText email, pass;
    boolean checkedLogin = false;
    SharedPreferences sp;
    int CustomerID;
    String CustomerName,Phone,Address,Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button) findViewById(R.id.loggedin);
        email = (EditText)findViewById(R.id.emailedt);
        pass = (EditText)findViewById(R.id.passedt);
        requestQueue = Volley.newRequestQueue(this);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent login = new Intent(Login.this, MainActivity.class);
//                startActivity(login);
                Log.e("TEST CLICK","Clicked");
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sp.edit();

                                Log.d("Response", response+"\n Amount : "+String.valueOf(response.length())+"\n Get Array : "+array);

                            JSONObject object = array.getJSONObject(0);
                            Log.e("CustomerID",""+object.getInt("Customer_ID"));
                            Log.e("CustomerName",object.getString("CustomerName"));
                            Log.e("EMail",email.getText().toString());
                            Log.e("Address",object.getString("Address"));
                            Log.e("Phone",object.getString("Phone"));

//                            CustomerID = object.getInt("Customer_ID");
//                            CustomerName = object.getString("CustomerName");
//                            Email = email.getText().toString();
//                            Address = object.getString("Address");
//                            Phone = object.getString("Phone");


                            checkedLogin=true;
                            editor.putInt("Customer_ID",object.getInt("Customer_ID"));
                            editor.putString("CustomerName",object.getString("CustomerName"));
                            editor.putString("Email",email.getText().toString());
                            editor.putString("Address",object.getString("Address"));
                            editor.putString("Phone",object.getString("Phone"));
                            editor.putBoolean("CheckLogin",checkedLogin);
                            editor.commit();
                            Log.e("TEST PassedLogin","Clicked");
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TEST FailedLogin","Clicked");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String,String>();
                        hashMap.put("Email", email.getText().toString());
                        hashMap.put("Password", pass.getText().toString());

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });
    }

}
