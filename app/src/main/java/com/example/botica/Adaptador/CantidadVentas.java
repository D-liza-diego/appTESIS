package com.example.botica.Adaptador;

public class CantidadVentas {
    private String mes;
    private String total;
    private String cantidad;

    public CantidadVentas(String mes, String total, String cantidad) {
        this.mes = mes;
        this.total = total;
        this.cantidad = cantidad;
    }

    public String getMes() {
        return mes;
    }

    public String getTotal() {
        return total;
    }

    public String getCantidad() {
        return cantidad;
    }
}
