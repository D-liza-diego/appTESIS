package com.example.botica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.Adaptador.Cliente;
import com.example.botica.Adaptador.ClienteAdaptador;

import com.example.botica.Adaptador.Proveedor;
import com.example.botica.Adaptador.ProveedorAdaptador;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class proveedor_add extends Fragment {
    Button buscar, mandar;
    EditText rucbuscar,ruc,nombre,direccion,estado,contacto;
    TextView truc,trazon,tdir,tstat,tcontact;
    String ruc_comparar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_proveedor_add, container, false);



        buscar=(Button) root.findViewById(R.id.EnviarP);
        mandar=(Button)root.findViewById(R.id.SubirP);
        rucbuscar=(EditText)root.findViewById(R.id.ruc_buscar);
        ruc=(EditText)root.findViewById(R.id.ruc_add);
        nombre=(EditText)root.findViewById(R.id.Rsocial_add);
        direccion=(EditText)root.findViewById(R.id.Dfiscal_add);
        estado=(EditText)root.findViewById(R.id.Estado_add);
        contacto=(EditText) root.findViewById(R.id.Contacto_add);
        truc=(TextView)root.findViewById(R.id.txtruc);
        trazon=(TextView)root.findViewById(R.id.txtrazon);
        tdir=(TextView)root.findViewById(R.id.txtdir);
        tstat=(TextView)root.findViewById(R.id.txtstat);
        tcontact=(TextView)root.findViewById(R.id.txtcontact);

        invisibilidad(root);
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
    void Subir(View root) {

        if (contacto.getText().toString().equals("")) { Toast.makeText(getContext(), "Ingrese numero", Toast.LENGTH_SHORT).show();
        } else {
            final String SubirP = "http://192.168.1.34/tesis/InsertProveedor.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, SubirP,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Navigation.findNavController(root).navigate(R.id.proveedorFragment);
                            Log.i("subir", response);
                            Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("ruc", ruc.getText().toString());
                    parametros.put("name", nombre.getText().toString());
                    parametros.put("state", estado.getText().toString());
                    parametros.put("address", direccion.getText().toString());
                    parametros.put("phone", contacto.getText().toString());
                    //parametros.put("created_at", created_at);
                    //parametros.put("updated_at", updated_at);
                    return parametros;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);

        }
    }
    void Consultar(View root)
    {
            final String ApiRuc = "https://dniruc.apisperu.com/api/v1/ruc/" + rucbuscar.getText() + "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImRsaXphQHVuaXRydS5lZHUucGUifQ.XHHhGXWhnln-s7Y6NBGnorvDyqJ2dR2L9uLiyHZjG6w";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiRuc,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("Mensaje", response);
                                JSONObject datos = new JSONObject(response.toString());
                                ruc_comparar = datos.getString("ruc");
                                ruc.setText(datos.getString("ruc"));
                                nombre.setText(datos.getString("razonSocial"));
                                direccion.setText(datos.getString("direccion"));
                                estado.setText(datos.getString("estado"));
                                visibilidad(root);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    invisibilidad(root);
                    Toast.makeText(getContext(), "Error al buscar", Toast.LENGTH_LONG).show();


                }
            });
            Volley.newRequestQueue(getContext()).add(stringRequest);
        }
        void visibilidad(View root)
    {
        truc.setVisibility(View.VISIBLE);
        trazon.setVisibility(View.VISIBLE);
        tdir.setVisibility(View.VISIBLE);
        tstat.setVisibility(View.VISIBLE);
        tcontact.setVisibility(View.VISIBLE);
        ruc.setVisibility(View.VISIBLE);
        nombre.setVisibility(View.VISIBLE);
        direccion.setVisibility(View.VISIBLE);
        estado.setVisibility(View.VISIBLE);
        contacto.setVisibility(View.VISIBLE);
        mandar.setVisibility(View.VISIBLE);

    }
    void invisibilidad(View root)
    {
        truc.setVisibility(View.INVISIBLE);
        trazon.setVisibility(View.INVISIBLE);
        tdir.setVisibility(View.INVISIBLE);
        tstat.setVisibility(View.INVISIBLE);
        tcontact.setVisibility(View.INVISIBLE);
        ruc.setVisibility(View.INVISIBLE);
        nombre.setVisibility(View.INVISIBLE);
        direccion.setVisibility(View.INVISIBLE);
        estado.setVisibility(View.INVISIBLE);
        contacto.setVisibility(View.INVISIBLE);
        mandar.setVisibility(View.INVISIBLE);

    }
}