package com.example.botica.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.botica.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VentaDetalleAdaptador extends RecyclerView.Adapter<VentaDetalleAdaptador.VentaDetalleViewHolder>
{
    private Context mCtx;
    private List<VentaDetalle> ListaVD;

    public VentaDetalleAdaptador(Context mCtx, List<VentaDetalle> listaVD) {
        this.mCtx = mCtx;
        ListaVD = listaVD;
    }

    @NonNull
    @NotNull
    @Override
    public VentaDetalleAdaptador.VentaDetalleViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card_venta_detalle, null);
        return new VentaDetalleAdaptador.VentaDetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VentaDetalleAdaptador.VentaDetalleViewHolder holder, int position) {
        VentaDetalle ventaDetalle = ListaVD.get(position);
        holder.txtnombre.setText(ventaDetalle.getProduct_name());
        holder.txtprecio.setText(ventaDetalle.getProduct_precio());
        holder.txtcantidad.setText(ventaDetalle.getProduct_cantidad());
    }

    @Override
    public int getItemCount() {
        return ListaVD.size();
    }


    class VentaDetalleViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtnombre,txtprecio,txtcantidad;
        public VentaDetalleViewHolder(View itemView) {
            super(itemView);
            txtnombre=itemView.findViewById(R.id.salesDetail_name);
            txtprecio=itemView.findViewById(R.id.salesDetail_precio);
            txtcantidad=itemView.findViewById(R.id.salesDetail_cantidad);
        }
    }
}
