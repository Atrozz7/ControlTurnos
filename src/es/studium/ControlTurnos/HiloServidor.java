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

            if (mensajeRecibido.equals("SOLICITAR_TICKET")) {
                synchronized (listaTickets) {
                    // La lógica del formato A001 y el salto de letra está aquí:
                    String numero = Servidor.generarTurno();

                    String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    Ticket nuevoTicket = new Ticket(numero, fechaActual, horaActual);
                    listaTickets.add(nuevoTicket);

                    // Enviamos al kiosko el ticket final (Ej: B001)
                    salida.writeUTF(numero);
                    System.out.println("Ticket generado exitosamente: " + numero);
                }
            }
            // ... resto de lógica (SIGUIENTE_PUESTO, etc.)
            
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}