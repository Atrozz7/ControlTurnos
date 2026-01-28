package es.studium.ControlTurnos;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
/**
 * @author Pablo Neira
 */
public class ClienteKiosko extends JFrame {
	public ClienteKiosko() {
		setTitle("Cliente");
		getContentPane().setLayout(null);
		
		JButton btnTicket = new JButton("Pedir cita");
		btnTicket.setBounds(103, 11, 224, 42);
		getContentPane().add(btnTicket);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 58, 414, 181);
		getContentPane().add(scrollPane);
		
		JList<String> lista = new JList<String>();
		lista.setBounds(0, 0, 1, 1);
		getContentPane().add(lista);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
