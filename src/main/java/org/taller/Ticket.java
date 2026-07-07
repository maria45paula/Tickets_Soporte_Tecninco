package org.taller;

public class Ticket {
    private int nroTicket;
    private String nombreCliente;
    private String descripcion;
    private EnumPrioridad prioridad;
    private String estadoActual;

    public Ticket(int nroTicket, String nombreCliente, String descripcion, EnumPrioridad prioridad) {
        this.nroTicket = nroTicket;
        this.nombreCliente = nombreCliente;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estadoActual = "Nuevo";
    }


}
