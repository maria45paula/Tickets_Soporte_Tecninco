package org.taller;

public class Ticket {
    private int nroTicket;
    private String nombreCliente;
    private String descripcion;
    private EnumPrioridad prioridad;
    private String estadoActual;
    private PilaDeEstados historial;

    public Ticket(int nroTicket, String nombreCliente, String descripcion, EnumPrioridad prioridad) {
        this.nroTicket = nroTicket;
        this.nombreCliente = nombreCliente;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estadoActual = "Nuevo";
        this.historial = new PilaDeEstados();
        this.historial.pushEstado("Nuevo");
    }

    public void cambiarEstado(String estado) {
        this.estadoActual = estado;
        this.historial.pushEstado(estado);
    }


    public boolean deshacerEstado() {
        if (historial.unElemento()) {
            System.out.println("No se puede deshacer el estado inicial");
            return false;
        }
        historial.popEstado();
        this.estadoActual = historial.peekEstado();
        return true;
    }

    private static final String[] ORDEN_ESTADOS = {
            "Nuevo",
            "En atención",
            "En proceso",
            "Esperando respuesta del cliente",
            "Resuelto"
    };


    public boolean avanzarEstado() {
        int indiceActual = indiceDeEstado(estadoActual);

        if (indiceActual == -1 || indiceActual == ORDEN_ESTADOS.length - 1) {
            return false; // ya está en "Resuelto", o el estado no está en la secuencia
        }

        String siguienteEstado = ORDEN_ESTADOS[indiceActual + 1];
        cambiarEstado(siguienteEstado);
        return true;
    }

    public boolean esEstadoFinal() {
        return estadoActual.equals(ORDEN_ESTADOS[ORDEN_ESTADOS.length - 1]);
    }

    private int indiceDeEstado(String estado) {
        for (int i = 0; i < ORDEN_ESTADOS.length; i++) {
            if (ORDEN_ESTADOS[i].equals(estado)) {
                return i;
            }
        }
        return -1;
    }


    public void mostrarHistorial() {
        historial.mostrarHistorial();
    }

    public int getNroTicket() {
        return nroTicket;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EnumPrioridad getPrioridad() {
        return prioridad;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public PilaDeEstados getHistorial() {
        return historial;
    }

    public void setPrioridad(EnumPrioridad prioridad) {
        this.prioridad = prioridad;
    }
}
