package com.example.listview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText edit1, edit2;
    Button btn;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit1 = (EditText) findViewById(R.id.name);
        edit2 = (EditText) findViewById(R.id.password);
        btn = (Button) findViewById(R.id.button);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnn();
            }
        });
    }


    public void btnn() {

        String url = "http://139.59.65.186/demo/seekho-jeeto/public/api/user/login";
        final String nam = edit1.getText().toString();
        final String pass = edit2.getText().toString();


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);

                try {
                    JSONObject rootobj = new JSONObject(response);
                    boolean returnobj = rootobj.getBoolean("return");

                    if (rootobj.has("otp")) {
                        String message = rootobj.getString("message");
                        if (rootobj.getBoolean("otp")) {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, otp.class);
                            intent.putExtra("number", nam);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } else if (returnobj==true) {
                        JSONObject userObj = rootobj.getJSONObject("user");
                        Toast.makeText(MainActivity.this, userObj.getString("name"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null)
                {
                    String str = new String(networkResponse.data);
                    try {
                        JSONObject object = new JSONObject(str);
                        JSONObject objecterr = object.getJSONObject("errors");

                        if (objecterr.has("mobile"))
                        {
                            JSONArray numberarry = objecterr.getJSONArray("mobile");
                            if (numberarry!=null)
                            {
                                String numbermsg = String.valueOf(numberarry.get(0));
                                Toast.makeText(MainActivity.this,numbermsg,Toast.LENGTH_LONG).show();
                            }

                        }
                        else if (objecterr.has("password"))
                        {
                            JSONArray passwordarray  = objecterr.getJSONArray("password");
                            if (passwordarray!=null)
                            {
                                String passwordmsg = String.valueOf(passwordarray.get(0));
                                Toast.makeText(MainActivity.this,passwordmsg,Toast.LENGTH_LONG).show();
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("mobile", nam);
                map.put("password", pass);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

        requestQueue.add(stringRequest);
    }

    public void click(View view) {
        Intent intent = new Intent(MainActivity.this, register.class);
        startActivity(intent);
    }


}


