// ClienteForm.java
package formularios;

import modelo.Cliente;
import dao.ClienteDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClienteForm extends JFrame {
    private JTextField txtNombre, txtTelefono, txtEmail;
    private JTextArea txtDireccion;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ClienteDAO clienteDAO;
    
    public ClienteForm() {
        setTitle("Gestión de Clientes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        clienteDAO = new ClienteDAO();
        initComponents();
        layoutComponents();
        addEventListeners();
        cargarClientes();
    }
    
    private void initComponents() {
        // Campos de texto
        txtNombre = new JTextField(20);
        txtTelefono = new JTextField(20);
        txtEmail = new JTextField(20);
        txtDireccion = new JTextArea(3, 20);
        
        // Botones
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        
        // Tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Fecha Registro");
        
        tablaClientes = new JTable(modeloTabla);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtTelefono, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(new JScrollPane(txtDireccion), gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        // Panel norte (formulario + botones)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        // Tabla
        JScrollPane scrollTabla = new JScrollPane(tablaClientes);
        
        add(panelNorte, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCliente();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        
        // Listener para selección en tabla
        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosClienteSeleccionado();
            }
        });
    }
    
    private void agregarCliente() {
        if (validarCampos()) {
            Cliente cliente = new Cliente(
                txtNombre.getText(),
                txtTelefono.getText(),
                txtEmail.getText(),
                txtDireccion.getText()
            );
            
            if (clienteDAO.agregarCliente(cliente)) {
                JOptionPane.showMessageDialog(this, "Cliente agregado exitosamente");
                limpiarFormulario();
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar");
            return;
        }
        
        if (validarCampos()) {
            int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Cliente cliente = new Cliente(
                txtNombre.getText(),
                txtTelefono.getText(),
                txtEmail.getText(),
                txtDireccion.getText()
            );
            cliente.setIdCliente(idCliente);
            
            if (clienteDAO.actualizarCliente(cliente)) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente");
                limpiarFormulario();
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este cliente?", "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            if (clienteDAO.eliminarCliente(idCliente)) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
                limpiarFormulario();
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarDatosClienteSeleccionado() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtTelefono.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtEmail.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtDireccion.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
        }
    }
    
    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        List<Cliente> clientes = clienteDAO.obtenerTodosClientes();
        
        for (Cliente cliente : clientes) {
            modeloTabla.addRow(new Object[]{
                cliente.getIdCliente(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getEmail(),
                cliente.getDireccion(),
                cliente.getFechaRegistro()
            });
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        tablaClientes.clearSelection();
    }
    
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            txtNombre.requestFocus();
            return false;
        }
        return true;
    }
}