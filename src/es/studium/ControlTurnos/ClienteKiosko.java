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
import java.awt.Font;

/**
 * @author Pablo Neira
 * Editado para formato A001
 */
public class ClienteKiosko extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private DefaultListModel<String> modeloLista;
    private JList<String> lista;
    
    // Contador para llevar el orden de los tickets
    private int contadorTickets = 1;
    private char letraSerie = 'A'; // Empezamos con la serie A

    public ClienteKiosko() {
        // Configuración de la ventana
        setSize(451, 318);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Kiosko de Tickets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // 1. Inicializamos el modelo
        modeloLista = new DefaultListModel<>();
        
        // 2. Rellenamos la lista inicial con el formato A001, A002, etc.
        for (int i = 0; i < 4; i++) {
            modeloLista.addElement(generarFormatoTicket());
        }

        // Botón para pedir cita
        JButton btnTicket = new JButton("Pedir cita");
        btnTicket.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para dar el primer ticket de la lista
                if (!modeloLista.isEmpty()) {
                    // Obtenemos el primero
                    String ticketAsignado = modeloLista.getElementAt(0);
                    
                    // Lo eliminamos de la lista visual
                    modeloLista.remove(0);
                    
                    // Mostramos el mensaje
                    JOptionPane.showMessageDialog(null, "Su turno es el: " + ticketAsignado);
                    
                    // OPCIONAL: Añadimos uno nuevo al final para que siempre haya 4 disponibles
                    modeloLista.addElement(generarFormatoTicket());
                } else {
                    JOptionPane.showMessageDialog(null, "No hay más citas en la cola.");
                }
            }
        });
        btnTicket.setBounds(103, 11, 224, 42);
        getContentPane().add(btnTicket);

        // 3. Configuración de la JList
        lista = new JList<>(modeloLista);
        lista.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Fuente monoespaciada para que luzca mejor
        
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setBounds(10, 64, 415, 204);
        getContentPane().add(scrollPane);
    }

    /**
     * Genera un String con formato "A" seguido de 3 dígitos con ceros a la izquierda.
     * Ejemplo: A001, A015, A120
     */
    private String generarFormatoTicket() {
        // Si llegamos al límite del formato (999)
        if (contadorTickets > 999) {
            contadorTickets = 1;      // Reiniciamos el número
            letraSerie++;             // Aumenta la letra (de 'A' a 'B', de 'B' a 'C'...)
            
            // Opcional: Si quieres que después de la 'Z' vuelva a la 'A'
            if (letraSerie > 'Z') {
                letraSerie = 'A';
            }
        }
        
        // Generamos el ticket con la letra actual y el número con 3 ceros
        return String.format("%c%03d", letraSerie, contadorTickets++); //
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ClienteKiosko().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}