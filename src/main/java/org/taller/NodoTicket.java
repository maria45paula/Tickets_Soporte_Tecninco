package org.taller;

public class NodoTicket {
    private Ticket ticket;
    private NodoTicket siguiente;

    public NodoTicket(Ticket ticket) {
        this.ticket = ticket;
        this.siguiente = null;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public NodoTicket getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoTicket siguiente) {
        this.siguiente = siguiente;
    }
}
