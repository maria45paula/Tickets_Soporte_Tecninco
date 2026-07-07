package org.taller;

public class NodoDoble {
    private Ticket ticket;
    private NodoDoble anterior;
    private NodoDoble siguiente;

    public NodoDoble(Ticket ticket) {
        this.ticket = ticket;
        this.anterior = null;
        this.siguiente = null;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public NodoDoble getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDoble anterior) {
        this.anterior = anterior;
    }

    public NodoDoble getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDoble siguiente) {
        this.siguiente = siguiente;
    }
}
