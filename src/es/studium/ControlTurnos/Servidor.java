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

    // Atributos para la lógica de generación de turnos
    private static int contadorTurnos = 1;
    private static char letraSerie = 'A';

    /**
     * Genera el siguiente turno con formato Letra + 3 dígitos (Ej: A001).
     * Si llega a 999, pasa a la siguiente letra (B001).
     * Es 'synchronized' para evitar que dos hilos generen el mismo número.
     */
    public static synchronized String generarTurno()
    {
        // 1. Si superamos el límite de 999, saltamos de letra y reiniciamos contador
        if (contadorTurnos > 999) 
        {
            contadorTurnos = 1;
            letraSerie++; // Pasa de 'A' a 'B', etc.
            
            // Opcional: Si llega después de la 'Z', vuelve a la 'A'
            if (letraSerie > 'Z') 
            {
                letraSerie = 'A';
            }
        }
        
        // 2. Formateamos el string (Ej: 'A' y 1 -> "A001")
        return String.format("%c%03d", letraSerie, contadorTurnos++);
    }

    public static void main(String[] args)
    {
        // Puerto de escucha (Asegúrate de que coincida con tus clientes)
        int puerto = 6666;
        listaTickets = new ArrayList<>();

        try (ServerSocket servidor = new ServerSocket(puerto))
        {
            System.out.println("========================================");
            System.out.println("   SERVIDOR DE TURNOS INICIADO");
            System.out.println("   Puerto: " + puerto);
            System.out.println("   Esperando kioskos y puestos...");
            System.out.println("========================================");

            while (true)
            {
                // Esperamos conexión de un cliente (Kiosko, Pantalla o Puesto)
                Socket cliente = servidor.accept();
                
                // Creamos un hilo para atender esa petición específica
                HiloServidor hilo = new HiloServidor(cliente, listaTickets);
                hilo.start();//
            }
        }
        catch (IOException e)
        {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}