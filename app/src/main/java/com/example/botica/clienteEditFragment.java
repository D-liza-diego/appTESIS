package com.example.botica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

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


public class clienteEditFragment extends Fragment {

    EditText Eapellidos,Enombres,Edni;
    Button send;
    String upid,upap,upn,upd;
    public static final String UPC="http://192.168.1.34/tesis/UpdateClientes.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cliente_edit, container, false);
        Cliente cliente =(Cliente) getArguments().getSerializable("key");

        Eapellidos=root.findViewById(R.id.apellidoUP);
        Enombres=root.findViewById(R.id.nombreUP);
        Edni=root.findViewById(R.id.dniUP);
        send=root.findViewById(R.id.EnviarC);

        Eapellidos.setText(cliente.getApellidos());
        Enombres.setText(cliente.getNombre());
        Edni.setText(String.valueOf(cliente.getDni()));
        upid=(String.valueOf(cliente.getIdc()));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirC(root);
            }
        });
        return root;

    }

void subirC(View root)
{
    StringRequest stringRequest= new StringRequest(Request.Method.POST, UPC,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    Navigation.findNavController(root).navigate(R.id.clienteFragment);
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
            parametros.put("idCliente",upid);
            parametros.put("Apellidos",Eapellidos.getText().toString());
            parametros.put("Nombre",Enombres.getText().toString());
            parametros.put("dni",Edni.getText().toString());
            return parametros;
        }
    };
    Volley.newRequestQueue(getContext()).add(stringRequest);


}
}