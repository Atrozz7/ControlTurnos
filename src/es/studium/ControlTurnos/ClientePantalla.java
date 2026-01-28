package es.studium.ControlTurnos;

import java.awt.BorderLayout;

import javax.swing.*;

public class ClientePantalla extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JTextArea textArea;

    public ClientePantalla() {
        initialize();
        mostrarTickets();
    }
    
    private void initialize() {
        setTitle("Control de Turnos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        
        contentPane.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }
    
    private void mostrarTickets() {
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