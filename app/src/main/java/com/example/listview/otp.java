package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class otp extends AppCompatActivity {

    EditText edit2;
    TextView edit1;
    Button btn;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

       edit1 = (TextView) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);

       edit1.setText(getIntent().getStringExtra("number"));






    }




    public void btnclick(View view) {

        String url = "http://139.59.65.186/demo/seekho-jeeto/public/api/user/verify-otp";



        final String pass = edit2.getText().toString();

        RequestQueue requestQueue  = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(otp.this, response, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(otp.this,MainActivity.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(otp.this, " Invalid password", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("mobile",getIntent().getStringExtra("number"));
                map.put("otp", pass);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

        requestQueue.add(stringRequest);

    }
}
