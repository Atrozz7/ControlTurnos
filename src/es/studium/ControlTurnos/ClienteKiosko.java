package es.studium.ControlTurnos;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteKiosko extends JFrame {
    private static final long serialVersionUID = 1L;
    
    // 1. Definimos el modelo de la lista como un atributo de la clase
    private DefaultListModel<String> modeloLista;
    private JList<String> lista;

    public ClienteKiosko() {
        setSize(451, 318);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Cliente");
        getContentPane().setLayout(null);

        // 2. Inicializamos el modelo y añadimos los números iniciales
        modeloLista = new DefaultListModel<>();
        modeloLista.addElement("Ticket 1");
        modeloLista.addElement("Ticket 2");
        modeloLista.addElement("Ticket 3");
        modeloLista.addElement("Ticket 4");

        JButton btnTicket = new JButton("Pedir cita");
        btnTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 3. Lógica para "pillar" el primero y borrarlo
                if (!modeloLista.isEmpty()) {
                    // Obtenemos el primer elemento (índice 0)
                    String ticketAsignado = modeloLista.getElementAt(0);
                    
                    // Lo eliminamos de la lista
                    modeloLista.remove(0);
                    
                    // Mostramos un mensaje al usuario
                    JOptionPane.showMessageDialog(null, "Su turno es el: " + ticketAsignado);
                } else {
                    JOptionPane.showMessageDialog(null, "No hay más citas disponibles.");
                }
            }
        });
        btnTicket.setBounds(103, 11, 224, 42);
        getContentPane().add(btnTicket);

        // 4. Configuramos la JList para que use nuestro modelo
        lista = new JList<>(modeloLista);
        
        JScrollPane scrollPane = new JScrollPane(lista); // Añadimos la lista al scroll
        scrollPane.setBounds(10, 58, 415, 210);
        getContentPane().add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClienteKiosko().setVisible(true);
            }
        });
    }
}