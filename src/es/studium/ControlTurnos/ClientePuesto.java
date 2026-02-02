package es.studium.ControlTurnos;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ClientePuesto extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;

    String ipServidor = "localhost";
    int puerto = 6666;

    private int numeroPuesto;

    public ClientePuesto(int numeroPuesto) {
        this.numeroPuesto = numeroPuesto;

        setTitle("Puesto " + numeroPuesto);
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JButton btnNewButton = new JButton("Libre");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                puestoLibre();
            }
        });
        btnNewButton.setBounds(118, 148, 186, 79);
        getContentPane().add(btnNewButton);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setBounds(133, 59, 159, 54);
        getContentPane().add(textField);
        textField.setColumns(10);

        setVisible(true);
    }

    private void puestoLibre() {
        try {
            Socket socket = new Socket(ipServidor, puerto);
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            DataInputStream entrada = new DataInputStream(socket.getInputStream());

            // Enviamos el número del puesto
            salida.writeUTF("SIGUIENTE_PUESTO_" + numeroPuesto);

            String respuesta = entrada.readUTF();

            if (respuesta.equals("COLA_VACIA")) {
                textField.setText("Sin turnos");
            } else {
                textField.setText(respuesta);
            }

            salida.close();
            entrada.close();
            socket.close();

        } catch (IOException ex) {
            textField.setText("Error Conexión");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ClientePuesto(1);
        new ClientePuesto(2);
        new ClientePuesto(3);
        new ClientePuesto(4);
    }
}