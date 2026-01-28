package es.studium.ControlTurnos;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 * @author Anabel
 */
public class ClientePuesto extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public ClientePuesto() {
		setTitle("Cliente Puesto");
		setSize(450, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Libre");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
			}
		});
		btnNewButton.setBounds(118, 148, 186, 79);
		getContentPane().add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(133, 59, 159, 54);
		getContentPane().add(textField);
		textField.setColumns(10);

		setVisible(true);
	}

	public static void main(String[] args) {
		new ClientePuesto();
	}
}