package es.studium.ControlTurnos;
/**
 *@autor Alvaro Martos
  **/
import javax.swing.*;

public class ClientePantalla extends JFrame {
    
    public ClientePantalla() {
        initialize();
    }
    
    private void initialize() {
        setTitle("Control de Turnos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
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