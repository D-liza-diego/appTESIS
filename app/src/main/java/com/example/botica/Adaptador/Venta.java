package com.example.botica.Adaptador;

import java.util.Comparator;

public class Venta {
    private String idventa;
    private String total;
    private String items;
    private String comprobante;
    private String state;
    private String clname;
    private String usname;
    private String fecha;

    public static Comparator<Venta> VentaMasc = new Comparator<Venta>() {
        @Override
        public int compare(Venta v1, Venta v2) {
            return v1.getTotal().compareTo(v2.getTotal());
        }
    };
    public static Comparator<Venta> VentaMdsc = new Comparator<Venta>() {
        @Override
        public int compare(Venta v1, Venta v2) {
            return v2.getTotal().compareTo(v1.getTotal());
        }
    };

    public static Comparator<Venta> VentaFasc = new Comparator<Venta>() {
        @Override
        public int compare(Venta v1, Venta v2) {
            return v1.getFecha().compareTo(v2.getFecha());
        }
    };
    public static Comparator<Venta> VentaFdesc = new Comparator<Venta>() {
        @Override
        public int compare(Venta v1, Venta v2) {
            return v2.getFecha().compareTo(v1.getFecha());
        }
    };

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
