package com.example.botica;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Product_addFragment extends Fragment {
    public static final String URL="http://192.168.1.34/tesis/ListarCategoria.php";
    public static  final String URLinsert="http://192.168.1.34/tesis/InsertProducto.php";
    public static  final String urlimg="http://192.168.1.34/tesis/InsertProductoImagen.php";
    EditText pname,pdesc,pcant,pprice;
    String imagecode;
    ImageView pfoto;
    Button mandarProduct;
    Spinner spinnercategoria;
    List<String> listacategoria;
    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_add, container, false);
        spinnercategoria = (Spinner) root.findViewById(R.id.spinnercat);
        pname=(EditText)root.findViewById(R.id.product_name_add);
        pdesc=(EditText)root.findViewById(R.id.product_des_add);
        pcant=(EditText)root.findViewById(R.id.product_stock_add);
        pprice=(EditText)root.findViewById(R.id.product_price_add);
        pfoto=(ImageView)root.findViewById(R.id.product_image_add);
        mandarProduct=(Button)root.findViewById(R.id.SubirProducto);
        pfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"tittle"),1);
            }
        });
        mandarProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirdatos(root);
            }
        });
        llenar(root);
        return root;
    }
    void subirimg (View root)
    {

    }
    void subirdatos(View root)
    {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URLinsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url=response;
                if(response!="error")
                {
                    StringRequest requestimg = new StringRequest(Request.Method.POST,urlimg, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show(); }
                    }
                    ) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            imagecode= convertirimg(bitmap);
                            Map<String,String> params = new HashMap<>();
                            params.put("url",url);
                            params.put("image",imagecode);
                            return params;
                        }

                    };
                    RequestQueue requestQueueimg = Volley.newRequestQueue(getContext());
                    requestQueueimg.add(requestimg);
                }
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> parametros=new HashMap<>();
            parametros.put("product_name",pname.getText().toString());
            parametros.put("descripcion",pdesc.getText().toString());
            parametros.put("prize",pprice.getText().toString());
            parametros.put("stock",pcant.getText().toString());
            parametros.put("cat_name",spinnercategoria.getSelectedItem().toString());
            return parametros; }
        };Volley.newRequestQueue(getContext()).add(stringRequest);

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
                                int id_cat = jsonObject.getInt("id_cat");
                                String cat_name=jsonObject.getString("cat_name");
                                Log.i("manda2",cat_name);
                                listacategoria.add(cat_name);
                            }
                            spinnercategoria.setAdapter(new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, listacategoria));
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show(); }
        });Volley.newRequestQueue(getContext()).add(stringRequest); }

    public String convertirimg(Bitmap bitmap)
    {
        ByteArrayOutputStream array= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte [] imagenByte= array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return imagenString;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode==1)
        {
            Uri uri=data.getData();
            pfoto.setImageURI(uri);
        }
    }
}
