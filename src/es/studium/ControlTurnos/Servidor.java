package es.studium.ControlTurnos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Gema
 * @date 28/01/2026
 * version 0
 */

public class Servidor 
{
	private static ArrayList<Ticket> listaTickets;
	
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
