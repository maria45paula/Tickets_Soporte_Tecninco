package org.taller;

import javax.swing.*;

public class Home {
    private JPanel panelPrincipal;
    private JLabel TituloMenu;
    private JButton cerrarUnTicketButton;
    private JButton registrarTicketButton;
    private JButton deshacerÚltimoCambioDeButton;
    private JButton mostrarHistorialDeEstadosButton;
    private JButton cambiarEstadoDeUnButton;
    private JButton iniciarAtenciónDelSiguienteButton;
    private JButton cambiarPrioridadDeUnButton;
    private JButton buscarTicketEnAtenciónButton;
    private JButton mostrarTicketsPendientesButton;
    private JButton mostrarTicketsEnAtenciónButton;
    private JButton mostrarTicketsEnAtenciónButton2;
    private JButton salirButton;
    private JPanel menu;

    private TicketCola pendientes;
    private ListaDoblementeEnlazadaTicket enAtencion;
    private int contadorTickets;

    public Home() {
        pendientes = new TicketCola();
        enAtencion = new ListaDoblementeEnlazadaTicket();
        contadorTickets = 1;

        agregarListeners();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    private void agregarListeners() {
        registrarTicketButton.addActionListener(e -> registrarTicket());
        iniciarAtenciónDelSiguienteButton.addActionListener(e -> iniciarAtencionSiguiente());
        mostrarTicketsPendientesButton.addActionListener(e -> mostrarPendientes());
        buscarTicketEnAtenciónButton.addActionListener(e -> buscarTicket());
        cambiarPrioridadDeUnButton.addActionListener(e -> cambiarPrioridad());
        cambiarEstadoDeUnButton.addActionListener(e -> cambiarEstado());
        mostrarHistorialDeEstadosButton.addActionListener(e -> mostrarHistorial());
        deshacerÚltimoCambioDeButton.addActionListener(e -> deshacerCambio());
        cerrarUnTicketButton.addActionListener(e -> cerrarTicket());

        // corregido: el método real se llama verDeInicioAFin, no mostrarDeInicioAFin
        mostrarTicketsEnAtenciónButton.addActionListener(e -> mostrarEnAtencionInicioAFin());
        mostrarTicketsEnAtenciónButton2.addActionListener(e -> mostrarEnAtencionFinAInicio());

        salirButton.addActionListener(e -> System.exit(0));
    }

    private void registrarTicket() {
        String cliente = JOptionPane.showInputDialog(panelPrincipal, "Nombre del cliente:");
        if (cliente == null || cliente.isBlank()) return;

        String descripcion = JOptionPane.showInputDialog(panelPrincipal, "Descripción del problema:");
        if (descripcion == null || descripcion.isBlank()) return;

        EnumPrioridad[] opciones = EnumPrioridad.values();
        EnumPrioridad prioridad = (EnumPrioridad) JOptionPane.showInputDialog(
                panelPrincipal, "Prioridad:", "Prioridad",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (prioridad == null) return;

        Ticket nuevo = new Ticket(contadorTickets++, cliente, descripcion, prioridad);
        pendientes.enqueve(nuevo); // ojo: tu método se llama enqueve, no encolar

        JOptionPane.showMessageDialog(panelPrincipal,
                "Ticket #" + nuevo.getNroTicket() + " registrado correctamente.");
    }

    private void iniciarAtencionSiguiente() {
        Ticket ticket = pendientes.desencolar();

        if (ticket == null) {
            JOptionPane.showMessageDialog(panelPrincipal, "No hay tickets pendientes.");
            return;
        }

        ticket.cambiarEstado("En atención");
        enAtencion.insertar(ticket);

        JOptionPane.showMessageDialog(panelPrincipal,
                "Ahora se está atendiendo el ticket #" + ticket.getNroTicket());
    }

    private void mostrarPendientes() {
        // TicketCola.verPendientes() usa System.out.println, no sirve en GUI.
        // Ver la sección de abajo: te recomiendo agregar un método que devuelva String.
        String texto = pendientes.listarComoTexto();
        mostrarEnVentana(texto, "Tickets pendientes");
    }

    private void buscarTicket() {
        int numero = pedirNumeroTicket();
        if (numero == -1) return;

        NodoDoble nodo = enAtencion.buscarNroTicket(numero);
        if (nodo == null) {
            JOptionPane.showMessageDialog(panelPrincipal, "No se encontró el ticket #" + numero);
            return;
        }

        Ticket t = nodo.getTicket();
        String info = "Cliente: " + t.getNombreCliente()
                + "\nDescripción: " + t.getDescripcion()
                + "\nPrioridad: " + t.getPrioridad()
                + "\nEstado actual: " + t.getEstadoActual();
        JOptionPane.showMessageDialog(panelPrincipal, info, "Ticket #" + numero, JOptionPane.INFORMATION_MESSAGE);
    }

    private void cambiarPrioridad() {
        int numero = pedirNumeroTicket();
        if (numero == -1) return;

        NodoDoble nodo = enAtencion.buscarNroTicket(numero);
        if (nodo == null) {
            JOptionPane.showMessageDialog(panelPrincipal, "No se encontró el ticket #" + numero);
            return;
        }

        // Nota: tu clase Ticket no tiene un setter de prioridad todavía.
        // Necesitas agregar "public void setPrioridad(EnumPrioridad p)" en Ticket.java
        EnumPrioridad[] opciones = EnumPrioridad.values();
        EnumPrioridad nueva = (EnumPrioridad) JOptionPane.showInputDialog(
                panelPrincipal, "Nueva prioridad:", "Cambiar prioridad",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (nueva == null) return;

        nodo.getTicket().setPrioridad(nueva);
        JOptionPane.showMessageDialog(panelPrincipal, "Prioridad actualizada.");
    }

    private void cambiarEstado() {
        int numero = pedirNumeroTicket();
        if (numero == -1) return;

        NodoDoble nodo = enAtencion.buscarNroTicket(numero);
        if (nodo == null) {
            JOptionPane.showMessageDialog(panelPrincipal, "No se encontró el ticket #" + numero + " en atención.");
            return;
        }

        Ticket ticket = nodo.getTicket();
        boolean avanzo = ticket.avanzarEstado();

        if (avanzo) {
            JOptionPane.showMessageDialog(panelPrincipal,
                    "Estado del ticket #" + numero + " avanzó a: " + ticket.getEstadoActual());
        } else {
            JOptionPane.showMessageDialog(panelPrincipal,
                    "El ticket #" + numero + " ya está en el estado final (Resuelto). No se puede avanzar más.");
        }
    }

    private void mostrarHistorial() {
        int numero = pedirNumeroTicket();
        if (numero == -1) return;

        NodoDoble nodo = enAtencion.buscarNroTicket(numero);
        if (nodo == null) {
            JOptionPane.showMessageDialog(panelPrincipal, "No se encontró el ticket #" + numero);
            return;
        }

        // Igual que con la cola: PilaDeEstados.mostrarHistorial() usa println.
        // Te conviene agregar un método que devuelva String (ver abajo).
        String historial = nodo.getTicket().getHistorial().listarComoTexto();
        mostrarEnVentana(historial, "Historial del ticket #" + numero);
    }

    private void deshacerCambio() {
        int numero = pedirNumeroTicket();
        if (numero == -1) return;

        NodoDoble nodo = enAtencion.buscarNroTicket(numero);
        if (nodo == null) {
            JOptionPane.showMessageDialog(panelPrincipal, "No se encontró el ticket #" + numero);
            return;
        }

        boolean ok = nodo.getTicket().deshacerEstado();
        if (ok) {
            JOptionPane.showMessageDialog(panelPrincipal,
                    "Estado revertido a: " + nodo.getTicket().getEstadoActual());
        } else {
            JOptionPane.showMessageDialog(panelPrincipal, "No se puede deshacer el estado inicial (Nuevo).");
        }
    }

    private void cerrarTicket() {
        int numero = pedirNumeroTicket();
        if (numero == -1) return;

        boolean eliminado = enAtencion.eliminarTicketAtencion(numero);
        if (eliminado) {
            JOptionPane.showMessageDialog(panelPrincipal, "Ticket #" + numero + " cerrado correctamente.");
        } else {
            JOptionPane.showMessageDialog(panelPrincipal, "No se encontró ese ticket en atención.");
        }
    }

    private void mostrarEnAtencionInicioAFin() {
        String texto = enAtencion.listarInicioAFinComoTexto(); // ver método nuevo abajo
        mostrarEnVentana(texto, "Tickets en atención (inicio → fin)");
    }

    private void mostrarEnAtencionFinAInicio() {
        String texto = enAtencion.listarFinAInicioComoTexto(); // ver método nuevo abajo
        mostrarEnVentana(texto, "Tickets en atención (fin → inicio)");
    }

    private int pedirNumeroTicket() {
        String input = JOptionPane.showInputDialog(panelPrincipal, "Número de ticket:");
        if (input == null || input.isBlank()) return -1;
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panelPrincipal, "Ingresa un número válido.");
            return -1;
        }
    }

    private void mostrarEnVentana(String contenido, String titulo) {
        JTextArea area = new JTextArea(contenido);
        area.setEditable(false);
        JOptionPane.showMessageDialog(panelPrincipal, new JScrollPane(area), titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}