package com.example.botica.Adaptador;

public class Venta {
    private String idventa;
    private String total;
    private String items;
    private String comprobante;
    private String state;
    private String clname;
    private String usname;
    private String fecha;

    public Venta(String idventa, String total, String items, String comprobante, String state, String clname, String usname, String fecha) {
        this.idventa = idventa;
        this.total = total;
        this.items = items;
        this.comprobante = comprobante;
        this.state = state;
        this.clname = clname;
        this.usname = usname;
        this.fecha = fecha;
    }

    public String getIdventa() {
        return idventa;
    }

    public String getTotal() {
        return total;
    }

    public String getItems() {
        return items;
    }

    public String getComprobante() {
        return comprobante;
    }

    public String getState() {
        return state;
    }

    public String getClname() {
        return clname;
    }

    public String getUsname() {
        return usname;
    }

    public String getFecha() {
        return fecha;
    }
}
