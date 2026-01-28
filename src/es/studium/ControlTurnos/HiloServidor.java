package es.studium.ControlTurnos;

/**
 * @author Raúl
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException; 
import java.net.Socket;

public class HiloServidor extends Thread
{ 
	Socket socket; 
	DataOutputStream salida; 
	DataInputStream entrada;  
	public HiloServidor(Socket s) throws IOException 
	{   
		this.socket = s;   
		salida = new DataOutputStream(s.getOutputStream()); 
		entrada = new DataInputStream(s.getInputStream()); 
	} 
	public void run() 
	{   
		String cadena = ""; 
		try   
		{   
			while(!cadena.trim().equals("*"))  
			{    
				System.out.println("Comunico con socket " + socket.toString());  
				cadena = entrada.readUTF();
				salida.writeUTF(cadena.toUpperCase());
			}    
			System.out.println("Fin de comunicación con socket " + socket.toString());    
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