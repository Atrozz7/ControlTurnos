package es.studium.ControlTurnos;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 * @author Anabel
 */
public class ClientePuesto extends JFrame{
	public ClientePuesto() {
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
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	    }
	
