package es.studium.ControlTurnos;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HiloServidor extends Thread {
    Socket socket;
    DataOutputStream salida;
    DataInputStream entrada;
    ArrayList<Ticket> listaTickets;

    public HiloServidor(Socket s, ArrayList<Ticket> lista) throws IOException {
        this.socket = s;
        this.listaTickets = lista;
        salida = new DataOutputStream(s.getOutputStream());
        entrada = new DataInputStream(s.getInputStream());
    }

    public void run() {
        try {
            String mensajeRecibido = entrada.readUTF();

            // 1. KIOSKO PIDE UN TICKET
            if (mensajeRecibido.equals("SOLICITAR_TICKET")) {
                synchronized (listaTickets) {

                    String numero = Servidor.generarTurno();

                    String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    Ticket nuevoTicket = new Ticket(numero, fechaActual, horaActual);
                    listaTickets.add(nuevoTicket);

                    salida.writeUTF(numero);
                    System.out.println("Ticket generado exitosamente: " + numero);
                }
            }

            // 2. PUESTO PIDE SIGUIENTE (CON NÚMERO DE PUESTO)
            else if (mensajeRecibido.startsWith("SIGUIENTE_PUESTO_")) {

                int puesto = Integer.parseInt(mensajeRecibido.split("_")[2]);

                synchronized (listaTickets) {
                    if (!listaTickets.isEmpty()) {
                        Ticket ticketAtendido = listaTickets.remove(0);

                        // Guardamos turno + puesto, ej: "A001;3"
                        Servidor.ultimoAtendido = ticketAtendido.getNumero() + ";" + puesto;

                        // Al puesto solo le mandamos el número de turno
                        salida.writeUTF(ticketAtendido.getNumero());
                        System.out.println("Atendiendo ticket: " 
                                + ticketAtendido.getNumero() + " en puesto " + puesto);
                    } else {
                        salida.writeUTF("COLA_VACIA");
                    }
                }
            }

            // 3. PANTALLA PIDE EL ÚLTIMO ATENDIDO
            else if (mensajeRecibido.equals("ULTIMO_ATENDIDO")) {
                salida.writeUTF(Servidor.ultimoAtendido);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

