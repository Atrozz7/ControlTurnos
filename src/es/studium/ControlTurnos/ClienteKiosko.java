package es.studium.ControlTurnos;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.awt.Font;

public class ClienteKiosko extends JFrame {
    private static final long serialVersionUID = 1L;

    public ClienteKiosko() {
        setSize(400, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Kiosko de Tickets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JButton btnTicket = new JButton("Pedir cita");
        btnTicket.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnTicket.setBounds(80, 50, 220, 60);
        
        btnTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solicitarTicketServidor();
            }
        });
        getContentPane().add(btnTicket);
    }

    private void solicitarTicketServidor() {
        try {
            // Conectamos al servidor (ajusta IP y puerto si es necesario)
            Socket socket = new Socket("localhost", 6666);
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            DataInputStream entrada = new DataInputStream(socket.getInputStream());

            // Enviamos la petición
            salida.writeUTF("SOLICITAR_TICKET");

            // Recibimos el número ya formateado (Ej: A001)
            String ticketAsignado = entrada.readUTF();

            JOptionPane.showMessageDialog(this, "Su turno es el: " + ticketAsignado);

            socket.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: No se pudo conectar con el servidor.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteKiosko().setVisible(true));
    }
}