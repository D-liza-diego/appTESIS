package com.example.botica.Adaptador;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Comparator;

public class Proveedor implements Serializable {
    private String RUC;
    private String RazonSocial;
    private String estado;

    //ordenar por
    public static Comparator<Proveedor> NombreASC = new Comparator<Proveedor>() {
        @Override
        public int compare(Proveedor p1, Proveedor p2) {
            return p1.getRazonSocial().compareTo(p2.getRazonSocial());
        }
    };
    public static Comparator<Proveedor> NombreDES = new Comparator<Proveedor>() {
        @Override
        public int compare(Proveedor p1, Proveedor p2) {
            return p2.getRazonSocial().compareTo(p1.getRazonSocial());
        }
    };
    public static Comparator<Proveedor> RucASC = new Comparator<Proveedor>() {
        @Override
        public int compare(Proveedor p1, Proveedor p2) {
            return p1.getRUC().compareTo(p2.getRUC());
        }
    };
    public static Comparator<Proveedor> RucDES = new Comparator<Proveedor>() {
        @Override
        public int compare(Proveedor p1, Proveedor p2) {
            return p2.getRUC().compareTo(p1.getRUC());
        }
    };

    public Proveedor(String RUC, String razonSocial, String estado) {
        this.RUC = RUC;
        RazonSocial = razonSocial;
        this.estado = estado;
    }

    public String getRUC() {
        return RUC;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public String getEstado() {
        return estado;
    }
}



