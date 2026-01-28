package es.studium.ControlTurnos;

import javax.swing.*;

public class ClientePantalla extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public ClientePantalla() {
        initialize();
        mostrarTickets();
    }
    
    private void initialize() {
        setTitle("Control de Turnos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 20, 740, 530);
        contentPane.add(scrollPane);
    }
    
    private void mostrarTickets() {
        textArea.setText("");
        textArea.append("Ticket 001 - Fecha: 2024-01-01 - Hora: 10:00\n");
        textArea.append("Ticket 002 - Fecha: 2024-01-01 - Hora: 10:15\n");
        textArea.append("Ticket 003 - Fecha: 2024-01-01 - Hora: 10:30\n");
        textArea.append("Ticket 004 - Fecha: 2024-01-01 - Hora: 10:45\n");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { 
                ClientePantalla frame = new ClientePantalla();
                frame.setVisible(true);
            }
        });
    }
}