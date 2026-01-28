package es.studium.ControlTurnos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException; 
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Raúl
 */
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
				synchronized (listaTickets) {
					String numero = "T" + (listaTickets.size() + 1);
					Ticket nuevoTicket = new Ticket(numero, "28/01/2026", "10:30");
					listaTickets.add(nuevoTicket);
					salida.writeUTF(numero);
				}
			}
			else if (mensajeRecibido.equals("SIGUIENTE_PUESTO")) 
			{
				synchronized (listaTickets) {
					if (!listaTickets.isEmpty()) {
						Ticket ticketAtendido = listaTickets.remove(0);
						salida.writeUTF(ticketAtendido.getNumero());
					} else {
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