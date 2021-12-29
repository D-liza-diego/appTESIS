package com.example.botica.Adaptador;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Comparator;

public class Proveedor implements Serializable {
    private String nombre;
    private String ruc;
    private String direccion;
    private String estado;
    private String contacto;

    //ordenar por
    public static Comparator<Proveedor> NombreASC = new Comparator<Proveedor>() {
        @Override
        public int compare(Proveedor p1, Proveedor p2) {
            return p1.getNombre().compareTo(p2.getNombre());
        }
    };
    public static Comparator<Proveedor> NombreDES = new Comparator<Proveedor>() {
        @Override
        public int compare(Proveedor p1, Proveedor p2) {
            return p2.getNombre().compareTo(p1.getNombre());
        }
    };
    public static Comparator<Proveedor> RucASC = new Comparator<Proveedor>() {
        @Override
        public int compare(Proveedor p1, Proveedor p2) {
            return p1.getRuc().compareTo(p2.getRuc());
        }
    };
    public static Comparator<Proveedor> RucDES = new Comparator<Proveedor>() {
        @Override
        public int compare(Proveedor p1, Proveedor p2) {
            return p2.getRuc().compareTo(p1.getRuc());
        }
    };

    public Proveedor(String nombre, String ruc, String direccion, String estado, String contacto) {
        this.nombre = nombre;
        this.ruc = ruc;
        this.direccion = direccion;
        this.estado = estado;
        this.contacto = contacto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEstado() {
        return estado;
    }

    public String getContacto() {
        return contacto;
    }
}



