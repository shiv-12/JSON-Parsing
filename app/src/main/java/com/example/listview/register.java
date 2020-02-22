package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class register extends AppCompatActivity {

    EditText ed1,ed2,ed3,ed4,ed5;
    Button button;
    RequestQueue requestQue;
    String url2 = "http://139.59.65.186/demo/seekho-jeeto/public/api/user/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        ed3 = (EditText) findViewById(R.id.ed3);
        ed4 = (EditText) findViewById(R.id.ed4);
        ed5 = (EditText) findViewById(R.id.ed5);
        button = (Button) findViewById(R.id.regb);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerr();
            }
        });



    }

    private void registerr() {

        final String [] arr = new String[5];
        arr[0] = ed1.getText().toString();
        arr[1] = ed2.getText().toString();
        arr[2] = ed3.getText().toString();
        arr[3] = ed4.getText().toString();
        arr[4] = ed5.getText().toString();




        requestQue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject object = new JSONObject(response);
                    Log.d("Tag","respponse - "+response);

                    if (object.has("otp"))
                    {
                        Boolean returnobj = object.getBoolean("return");
                        if (returnobj==true)

                        {
                            String msg = object.getString("message");
                            Toast.makeText(register.this,msg,Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(register.this,otp.class);
                            intent.putExtra("number",arr[2]);
                            startActivity(intent);


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse !=null && networkResponse.data!=null)
                {
                    String str = new String(networkResponse.data);
                    try {
                        JSONObject object = new JSONObject(str);
                        JSONObject errobj = object.getJSONObject("errors");
                        if (errobj.has("email"))
                        {
                            JSONArray emailarray = errobj.getJSONArray("email");
                            if (emailarray!=null)
                            {
                                String emailmsg = String.valueOf(emailarray.get(0));
                                Toast.makeText(register.this,emailmsg,Toast.LENGTH_LONG).show();

                            }
                        }
                        if (errobj.has("mobile"))
                        {
                            JSONArray mobilearray = errobj.getJSONArray("mobile");
                            if (mobilearray != null)
                            {
                                String mobilemsg = String.valueOf(mobilearray.get(0));
                                Toast.makeText(register.this,mobilemsg,Toast.LENGTH_LONG).show();

                            }
                        }
                        if (errobj.has("paytm_phonepay_number"))
                        {
                            JSONArray paytmarray = errobj.getJSONArray("paytm_phonepay_number");
                            if (paytmarray != null)
                            {
                                String mobilemsg = String.valueOf(paytmarray.get(0));
                                Toast.makeText(register.this,mobilemsg,Toast.LENGTH_LONG).show();

                            }
                        }

                        if (errobj.has("password"))
                        {
                            JSONArray paytmarray = errobj.getJSONArray("password");
                            if (paytmarray != null)
                            {
                                String mobilemsg = String.valueOf(paytmarray.get(0));
                                Toast.makeText(register.this,mobilemsg,Toast.LENGTH_LONG).show();

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> mapp = new HashMap<String, String>() ;
                mapp.put("name",arr[0]);
                mapp.put("email",arr[1]);
                mapp.put("mobile",arr[2]);
                mapp.put("paytm_phonepay_number",arr[3]);
                mapp.put("password",arr[4]);

                return mapp;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

        requestQue.add(request);

    }
}
