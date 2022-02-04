package com.example.botica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.Adaptador.Cliente;
import com.example.botica.Adaptador.ClienteAdaptador;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ImageView img;
    EditText email, password;
    String URL="http://192.168.1.34/tesis/login.php";
    String t_email,t_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        img = findViewById(R.id.loginimage);
        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }

    public void login(View view)
    {
        t_email=email.getText().toString().trim();
        t_password=password.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok= jsonRespuesta.getBoolean("success");
                            if (ok==true)
                            {
                                String j_email= jsonRespuesta.getString("email");
                                String j_nombre= jsonRespuesta.getString("name");
                                String j_password=jsonRespuesta.getString("password");
                                //Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(LoginActivity.this,MenuActivity.class);
                                intent.putExtra("email",j_email);
                                intent.putExtra("name",j_nombre);
                                startActivity(intent);
                            }
                            else
                            {Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();}

                        }
                        catch (JSONException e) {e.getMessage();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<>();
                parametros.put("email",t_email);
                parametros.put("password",t_password);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);
    }
}
