package com.example.botica;

import android.graphics.Color;
import android.os.Bundle;

import android.telephony.BarringInfo;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.anychart.*;
import com.example.botica.Adaptador.*;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import lecho.lib.hellocharts.model.*;
import lecho.lib.hellocharts.view.LineChartView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PrincipalFragment extends Fragment {
    public static final String URL="http://192.168.1.34/tesis/ListarProducto.php";
    public static final String URL1="http://192.168.1.34/tesis/ListarVentasHechas.php";
    public static final String URL2="http://192.168.1.34/tesis/ListarCantidadVentasTotal.php";
    View root;
    //grafico ventas * producto
    List<GraficoVentas> Lventas = new ArrayList<>();
    List<DataEntry> datos2= new ArrayList<>();
    List<DataEntry> datos3 = new ArrayList<>();
    AnyChartView anyChartView, anyChartView2;
    //grafico ventas * mes
    List<CantidadVentas> LcantidadV= new ArrayList<>();

    //grafico barras
    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_principal, container, false);
        anyChartView=(AnyChartView)root.findViewById(R.id.grafico_ventasproducto);
        anyChartView2=(AnyChartView) root.findViewById(R.id.graficoventasmes);
        barChart=(BarChart)root.findViewById(R.id.grafico_barras);
        graficoProductoVentas(root);
        graficoVentasMes(root);
        prueba(root);
        return root;
    }
    void prueba(View root)
    {
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray product  = new JSONArray(response);
                            for (int i=0; i<product.length(); i++)
                            {
                                JSONObject productobject = product.getJSONObject(i);
                                String mes=productobject.getString("Mes");
                                String total=productobject.getString("Total");
                                String cantidad=productobject.getString("Cantidad");
                                CantidadVentas cantidadVentas= new CantidadVentas(mes,total,cantidad);
                                LcantidadV.add(cantidadVentas);
                                barEntryArrayList.add(new BarEntry(i,Integer.valueOf(total)));
                                labels.add(mes);
                            }
                            BarDataSet barDataSet= new BarDataSet(barEntryArrayList,labels.toString());
                            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            barDataSet.setValueTextSize(20);
                            /*Description description = new Description();
                            description.setText("Meses");
                            barChart.setDescription(description);*/
                            BarData barData = new BarData(barDataSet);
                            barChart.setData(barData);
                            //
                            XAxis xAxis= barChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                            //
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
                            xAxis.setTextSize(50);
                            xAxis.setTextColor(ColorTemplate.MATERIAL_COLORS.length);
                            xAxis.setDrawGridLines(true);
                            xAxis.setDrawAxisLine(true);
                            xAxis.setGranularity(1f);
                            xAxis.setLabelCount(labels.size());
                            xAxis.setLabelRotationAngle(270);
                            barChart.animateY(2000);
                            barChart.invalidate();
                        } catch (JSONException e) {e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();}
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
    void graficoVentasMes(View root)
    {
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray product  = new JSONArray(response);
                            for (int i=0; i<product.length(); i++)
                            {
                                JSONObject productobject = product.getJSONObject(i);
                                String mes=productobject.getString("Mes");
                                String total=productobject.getString("Total");
                                String cantidad=productobject.getString("Cantidad");

                                CantidadVentas cantidadVentas= new CantidadVentas(mes,total,cantidad);
                                LcantidadV.add(cantidadVentas);
                                datos3.add(new ValueDataEntry(mes,Integer.valueOf(total)));

                                Pie pie=AnyChart.pie();
                                pie.data(datos3);
                                pie.setTitle("VENTAS AL MES");
                                anyChartView2.setChart(pie);
                            }

                        } catch (JSONException e) {e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();}
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
    void graficoProductoVentas(View root)
    {
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray  = new JSONArray(response);
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String pro_name=jsonObject.getString("P.name");
                                String pro_can=jsonObject.getString("TotalQuantity");
                                GraficoVentas graficoVentas= new GraficoVentas(pro_name,pro_can);
                                Lventas.add(graficoVentas);
                                datos2.add(new ValueDataEntry(pro_name, Integer.valueOf(pro_can)));
                            }
                            Pie pie=AnyChart.pie();
                            pie.data(datos2);
                            pie.setTitle("TOP PRODUCTOS VENDIDOS");
                            anyChartView.setChart(pie);
                        } catch (JSONException e) {e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();}
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item1 = menu.findItem(R.id.item_help);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Navigation.findNavController(root).navigate(R.id.ayudaFragment);
                return false;
            }
        });
    }
}