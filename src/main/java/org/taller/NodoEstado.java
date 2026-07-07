package org.taller;

public class NodoEstado {
    private String estado;
    private NodoEstado siguiente;

    public NodoEstado(String estado) {
        this.estado = estado;
        this.siguiente = null;
    }

    public String getEstado() {
        return estado;
    }

    public NodoEstado getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoEstado siguiente) {
        this.siguiente = siguiente;
    }

}
