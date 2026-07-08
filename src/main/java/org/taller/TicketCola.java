package org.taller;

public class TicketCola {
    private NodoTicket frente;
    private NodoTicket final1;
    private int tamanio;

    // encolar agregar el ticket al final de la cola
    public void enqueve(Ticket ticket) {
        NodoTicket nuevo = new NodoTicket(ticket);

        // si la cola está vacía, el nuevo nodo es frente y final a la vez
        if (estaVacia()) {
            frente = nuevo;
            final1 = nuevo;
        } else {
            // conecto el nodo actual del final con el nuevo
            final1.setSiguiente(nuevo);
            final1 = nuevo;
        }
        tamanio++;
    }

    // sacar el ticket del frente
    public Ticket desencolar() {
        if (estaVacia()) {
            return null;
        }

        Ticket ticketRevisado = frente.getTicket();
        frente = frente.getSiguiente();
        if (frente == null) {
            final1 = null;
        }

        tamanio--;
        return ticketRevisado;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void verPendientes() {
        if (estaVacia()) {
            System.out.println("no hay tickets pendientes por atender");
            return;
        }

        NodoTicket actual = frente;
        System.out.println("Tickets pendientes: ");
        while (actual != null) {
            Ticket ticketActual = actual.getTicket();
            System.out.println("- Nro:" + ticketActual.getNroTicket() + "  " + ticketActual.getNombreCliente() + "   " + ticketActual.getDescripcion());
            actual = actual.getSiguiente();
        }
    }

    public String listarComoTexto() {
        if (estaVacia()) return "No hay tickets pendientes por atender.";

        StringBuilder sb = new StringBuilder();
        NodoTicket actual = frente;
        while (actual != null) {
            Ticket t = actual.getTicket();
            sb.append("Nro: ").append(t.getNroTicket())
                    .append("  ").append(t.getNombreCliente())
                    .append("  ").append(t.getDescripcion())
                    .append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }
}
