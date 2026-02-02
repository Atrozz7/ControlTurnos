package es.studium.ControlTurnos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Pablo Neira
 * Servidor centralizado para el control de turnos.
 */
public class Servidor 
{
    private static ArrayList<Ticket> listaTickets;
    
    // Último turno atendido (formato: "A001;3")
    public static String ultimoAtendido = "";

    // Atributos para la lógica de generación de turnos
    private static int contadorTurnos = 1;
    private static char letraSerie = 'A';

    /**
     * Genera el siguiente turno con formato Letra + 3 dígitos (Ej: A001).
     * Si llega a 999, pasa a la siguiente letra (B001).
     */
    public static synchronized String generarTurno()
    {
        if (contadorTurnos > 999) 
        {
            contadorTurnos = 1;
            letraSerie++;

            if (letraSerie > 'Z') 
            {
                letraSerie = 'A';
            }
        }

        return String.format("%c%03d", letraSerie, contadorTurnos++);
    }

    public static void main(String[] args)
    {
        int puerto = 6666;
        listaTickets = new ArrayList<>();

        try (ServerSocket servidor = new ServerSocket(puerto))
        {
            System.out.println("========================================");
            System.out.println("   SERVIDOR DE TURNOS INICIADO");
            System.out.println("   Puerto: " + puerto);
            System.out.println("   Esperando kioskos, puestos y pantallas...");
            System.out.println("========================================");

            while (true)
            {
                Socket cliente = servidor.accept();
                HiloServidor hilo = new HiloServidor(cliente, listaTickets);
                hilo.start();
            }
        }
        catch (IOException e)
        {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


