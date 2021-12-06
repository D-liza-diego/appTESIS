package com.example.botica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.Adaptador.Cliente;
import com.example.botica.Adaptador.ClienteAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class proveedor_add extends Fragment {

    Button buscar, mandar;
    EditText rucbuscar,ruc,social,fiscal,estado;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_proveedor_add, container, false);
        buscar=(Button) root.findViewById(R.id.EnviarP);
        mandar=(Button)root.findViewById(R.id.SubirP);
        rucbuscar=(EditText)root.findViewById(R.id.ruc_buscar);
        ruc=(EditText)root.findViewById(R.id.ruc_add);
        social=(EditText)root.findViewById(R.id.Rsocial_add);
        fiscal=(EditText)root.findViewById(R.id.Dfiscal_add);
        estado=(EditText)root.findViewById(R.id.Estado_add);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Consultar(root);
            }
        });

        mandar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subir(root);
            }
        });
        return root;
    }
    void Subir(View root)
    {
        final String SubirP ="http://192.168.1.34/tesis/InsertProveedor.php";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, SubirP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Navigation.findNavController(root).navigate(R.id.proveedorFragment);
                        Log.i("subir",response);
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<>();
                parametros.put("RUC",ruc.getText().toString());
                parametros.put("RazonSocial",social.getText().toString());
                parametros.put("direccion",fiscal.getText().toString());
                parametros.put("estado",estado.getText().toString());
                return parametros;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }
    void Consultar(View root)

    {
        final String ApiRuc="https://dniruc.apisperu.com/api/v1/ruc/"+rucbuscar.getText()+"?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImRsaXphQHVuaXRydS5lZHUucGUifQ.XHHhGXWhnln-s7Y6NBGnorvDyqJ2dR2L9uLiyHZjG6w";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, ApiRuc,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("Mensaje",response);
                            JSONObject datos= new JSONObject(response.toString());
                            ruc.setText(datos.getString("ruc"));
                            social.setText(datos.getString("razonSocial"));
                            fiscal.setText(datos.getString("direccion"));
                            estado.setText(datos.getString("estado"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }
}