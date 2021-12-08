package com.example.botica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.Adaptador.Categoria;
import com.example.botica.Adaptador.Producto;
import com.example.botica.Adaptador.ProductoAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Product_addFragment extends Fragment {
    public static final String URL="http://192.168.1.34/tesis/ListarCategoria.php";
    Spinner spinnercategoria;
    List<String> listacategoria;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_add, container, false);
        spinnercategoria = (Spinner) root.findViewById(R.id.spinnercat);
        llenar(root);
        return root;
    }

    void llenar(View root)
    {
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("manda",response);
                            JSONArray jsonArray  = new JSONArray(response);
                            listacategoria= new ArrayList<>();
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String cat_name=jsonObject.getString("cat_name");
                                Log.i("manda2",cat_name);
                                //Categoria categoria = new Categoria(cat_name);
                                listacategoria.add(cat_name);
                            }
                            spinnercategoria.setAdapter(new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, listacategoria));
                        } catch (JSONException e) { e.printStackTrace(); }
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
