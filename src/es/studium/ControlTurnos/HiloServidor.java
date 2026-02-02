package es.studium.ControlTurnos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HiloServidor extends Thread
{
    Socket socket;
    DataOutputStream salida;
    DataInputStream entrada;
    ArrayList<Ticket> listaTickets;

    public HiloServidor(Socket s, ArrayList<Ticket> lista) throws IOException
    {
        this.socket = s;
        this.listaTickets = lista;
        salida = new DataOutputStream(s.getOutputStream());
        entrada = new DataInputStream(s.getInputStream());
    }

    public void run()
    {
        try
        {
            String mensajeRecibido = entrada.readUTF();

            if (mensajeRecibido.equals("SOLICITAR_TICKET"))
            {
                synchronized (listaTickets)
                {
                    // Recbo los numeros del servidor
                    String numero = Servidor.generarTurno();

                    String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    Ticket nuevoTicket = new Ticket(numero, fechaActual, horaActual);

                    listaTickets.add(nuevoTicket);
                    salida.writeUTF(numero);

                    System.out.println("Ticket generado: " + numero + " a las " + horaActual);
                }
            }
            else if (mensajeRecibido.equals("SIGUIENTE_PUESTO"))
            {
                synchronized (listaTickets)
                {
                    if (!listaTickets.isEmpty())
                    {
                        Ticket ticketAtendido = listaTickets.remove(0);
                        salida.writeUTF(ticketAtendido.getNumero());
                        System.out.println("Atendiendo ticket: " + ticketAtendido.getNumero());
                    }
                    else
                    {
                        salida.writeUTF("COLA_VACIA");
                    }
                }
            }

            System.out.println("Operación finalizada con socket " + socket.toString());

            salida.close();
            entrada.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
