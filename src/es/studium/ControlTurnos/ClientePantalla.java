package es.studium.ControlTurnos;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientePantalla extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tablaTickets;
    private DefaultTableModel modeloTabla;

    
    public static String ultimoMostrado = "";

    public ClientePantalla() {
        initialize();
        iniciarActualizacion();
    }

    private void initialize() {
        setTitle("Control de Turnos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Tickets", SwingConstants.CENTER);
        lblTitulo.setBounds(0, 0, 786, 87);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 40));
        contentPane.add(lblTitulo);

        String[] columnas = {"Número de Turno", "Número de Puesto"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaTickets = new JTable(modeloTabla);

        tablaTickets.setFont(new Font("Arial", Font.PLAIN, 24));
        tablaTickets.setRowHeight(40);
        tablaTickets.getTableHeader().setFont(new Font("Arial", Font.BOLD, 26));

        JScrollPane scrollPane = new JScrollPane(tablaTickets);
        scrollPane.setBounds(0, 97, 786, 466);
        contentPane.add(scrollPane);
    }

    // Añadir fila a la tabla
    public void agregarTicket(String turno, String puesto) {
        modeloTabla.addRow(new Object[]{turno, puesto});
    }

    // Timer que consulta al servidor cada 1 segundo
    private void iniciarActualizacion() {
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarPantalla();
            }
        });
        timer.start();
    }

    // Consulta al servidor el último turno atendido
    private void actualizarPantalla() {
        try {
            Socket socket = new Socket("localhost", 6666);
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            DataInputStream entrada = new DataInputStream(socket.getInputStream());

            salida.writeUTF("ULTIMO_ATENDIDO");
            String turno = entrada.readUTF();

            if (!turno.equals("") && !turno.equals(ultimoMostrado)) {

                ultimoMostrado = turno;

                String[] datos = turno.split(";");
                String numeroTurno = datos[0];
                String puesto = datos[1];

                agregarTicket(numeroTurno, "Puesto " + puesto);
            }

            socket.close();
        } catch (Exception ex) {
            System.out.println("Pantalla no pudo conectar al servidor");
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClientePantalla frame = new ClientePantalla();
                frame.setVisible(true);
            }
        });
    }
}