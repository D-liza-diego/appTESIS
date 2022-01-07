package com.example.botica.Adaptador;

public class VentaDetalle {
    private String product_name;
    private String product_cantidad;
    private String product_precio;

    public VentaDetalle(String product_name, String product_cantidad, String product_precio) {
        this.product_name = product_name;
        this.product_cantidad = product_cantidad;
        this.product_precio = product_precio;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_cantidad() {
        return product_cantidad;
    }

    public String getProduct_precio() {
        return product_precio;
    }
}
