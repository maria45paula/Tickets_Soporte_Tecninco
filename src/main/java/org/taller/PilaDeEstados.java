package org.taller;

public class PilaDeEstados {
    private NodoEstado tope;

    // añade un nuevo estado arriba de todos los demas estados
    public void pushEstado(String estado) {
        NodoEstado nuevo = new NodoEstado(estado);
        nuevo.setSiguiente(tope);
        tope = nuevo;
    }

    //pop  saca el estado de arriba y apunta al anterior
    public String popEstado() {
        if (estaVacia()) {
            return "El tiquete no tiene estados";
        }
        String estadoActual = tope.getEstado();
        tope = tope.getSiguiente();
        return estadoActual;
    }

    // ver el estado en el que se encuentra el ticket
    public String peekEstado() {
        if (estaVacia()) return null;
        return tope.getEstado();
    }

    public boolean estaVacia() {
        return tope == null;
    }

    // Recorrer el historial completo del ticket
    public void mostrarHistorial() {
        NodoEstado actual = tope;
        System.out.println("Historial de estados de del mas reciente hasta el mas antiguo:");
        while (actual != null) {
            System.out.println("- " + actual.getEstado());
            actual = actual.getSiguiente();
        }
    }

    //para saber si solo tiene un estado el tickete
    public boolean unElemento() {
        return tope != null && tope.getSiguiente() == null;
    }


}
