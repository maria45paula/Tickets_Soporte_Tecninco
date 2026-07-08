package org.taller;

public class ListaDoblementeEnlazadaTicket {
    private NodoDoble inicio;
    private NodoDoble fin;
    private int tamanio;

    //agregar el ticket en estado de atencion al final
    public void insertar(Ticket ticket) {
        NodoDoble nuevo = new NodoDoble(ticket);

        if (estaVacia()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            nuevo.setAnterior(fin);
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamanio++;
    }

    //buscar el numero de ticket desde el inicio
    public NodoDoble buscarNroTicket(int nroTicket) {
        NodoDoble actual = inicio;
        while (actual != null) {
            if (actual.getTicket().getNroTicket() == nroTicket) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public boolean eliminarTicketAtencion(int nroTicket) {
        NodoDoble nodo = buscarNroTicket(nroTicket);
        if (nodo == null) {
            return false;
        }

        NodoDoble anterior = nodo.getAnterior();
        NodoDoble siguiente = nodo.getSiguiente();

        if (anterior != null) {
            anterior.setSiguiente(siguiente);
        } else {
            inicio = siguiente;
        }

        if (siguiente != null) {
            siguiente.setAnterior(anterior); // el de después salta al de antes
        } else {
            fin = anterior;
        }

        tamanio--;
        return true;
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    public int getTamanio() {
        return tamanio;
    }
/*
    public void verDeInicioAFin() {
        if (estaVacia()) {
            System.out.println("No hay tickets en atencion");
            return;
        }
        NodoDoble actual = inicio;
        System.out.println("Tickets en atencion del mas antiguo al mas reciente :");
        while (actual != null) {
            Ticket ticket = actual.getTicket();
            System.out.println("Numero:" + ticket.getNroTicket() + "  " + ticket.getNombreCliente() + "  " + ticket.getEstadoActual());
            actual = actual.getSiguiente();
        }
    }

    public void mostrarDeFinAInicio() {
        if (estaVacia()) {
            System.out.println("No hay tickets en atención.");
            return;
        }
        NodoDoble actual = fin;
        System.out.println("Tickets en atencion del mas reciente al mas antiguo:");
        while (actual != null) {
            Ticket ticket = actual.getTicket();
            System.out.println("Numero:" + ticket.getNroTicket() + "  " + ticket.getNombreCliente() + "  " + ticket.getEstadoActual());
            actual = actual.getAnterior();
        }
    }*/

    public String listarInicioAFinComoTexto() {
        if (estaVacia()) return "No hay tickets en atención.";
        StringBuilder sb = new StringBuilder();
        NodoDoble actual = inicio;
        while (actual != null) {
            Ticket t = actual.getTicket();
            sb.append("Nro: ").append(t.getNroTicket())
                    .append("  ").append(t.getNombreCliente())
                    .append("  ").append(t.getEstadoActual())
                    .append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }

    public String listarFinAInicioComoTexto() {
        if (estaVacia()) return "No hay tickets en atención.";
        StringBuilder sb = new StringBuilder();
        NodoDoble actual = fin;
        while (actual != null) {
            Ticket t = actual.getTicket();
            sb.append("Nro: ").append(t.getNroTicket())
                    .append("  ").append(t.getNombreCliente())
                    .append("  ").append(t.getEstadoActual())
                    .append("\n");
            actual = actual.getAnterior();
        }
        return sb.toString();
    }
}
