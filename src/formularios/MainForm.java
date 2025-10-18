package formularios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {
    private JButton btnClientes, btnPedidos, btnInventario, btnEmpleados, btnHorarios, btnAsistencias;
    
    public MainForm() {
        setTitle("Sistema de Gestión - La Buena Hamburguesa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        initComponents();
        layoutComponents();
        addEventListeners();
    }
    
    private void initComponents() {
        btnClientes = new JButton("Gestión de Clientes");
        btnPedidos = new JButton("Gestión de Pedidos");
        btnInventario = new JButton("Gestión de Inventario");
        btnEmpleados = new JButton("Gestión de Empleados");
        btnHorarios = new JButton("Gestión de Horarios");
        btnAsistencias = new JButton("Gestión de Asistencias");
        
        // Estilo de botones
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        btnClientes.setFont(buttonFont);
        btnPedidos.setFont(buttonFont);
        btnInventario.setFont(buttonFont);
        btnEmpleados.setFont(buttonFont);
        btnHorarios.setFont(buttonFont);
        btnAsistencias.setFont(buttonFont);
        
        // Colores
        Color primaryColor = new Color(41, 128, 185);
        Color buttonColor = new Color(52, 152, 219);
        
        btnClientes.setBackground(buttonColor);
        btnPedidos.setBackground(buttonColor);
        btnInventario.setBackground(buttonColor);
        btnEmpleados.setBackground(buttonColor);
        btnHorarios.setBackground(buttonColor);
        btnAsistencias.setBackground(buttonColor);
        
        btnClientes.setForeground(Color.WHITE);
        btnPedidos.setForeground(Color.WHITE);
        btnInventario.setForeground(Color.WHITE);
        btnEmpleados.setForeground(Color.WHITE);
        btnHorarios.setForeground(Color.WHITE);
        btnAsistencias.setForeground(Color.WHITE);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        JLabel titleLabel = new JLabel("LA BUENA HAMBURGUESA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        buttonPanel.add(btnClientes);
        buttonPanel.add(btnPedidos);
        buttonPanel.add(btnInventario);
        buttonPanel.add(btnEmpleados);
        buttonPanel.add(btnHorarios);
        buttonPanel.add(btnAsistencias);
        
        add(headerPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        btnClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteForm().setVisible(true);
            }
        });
        
        btnPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PedidoForm().setVisible(true);
            }
        });
        
        btnInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InventarioForm().setVisible(true);
            }
        });
        
        btnEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmpleadoForm().setVisible(true);
            }
        });
        
        btnHorarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HorarioForm().setVisible(true);
            }
        });
        
        btnAsistencias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AsistenciaForm().setVisible(true);
            }
        });
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
}