package es.studium.ControlTurnos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor 
{
    private static ArrayList<Ticket> listaTickets;

    // Contador para los turnos
    private static int contadorTurnos = 1;

    // 🔥Aqui genero los numeros de los turnos
    public static synchronized String generarTurno()
    {
        return String.format("A%03d", contadorTurnos++);
    }

    public static void main(String[] args)
    {
        int puerto = 6666;
        listaTickets = new ArrayList<>();

        try
        {
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado... escuchando por el puerto " + servidor.getLocalPort());

            while (true)
            {
                Socket cliente = servidor.accept();
                HiloServidor hilo = new HiloServidor(cliente, listaTickets);
                hilo.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
