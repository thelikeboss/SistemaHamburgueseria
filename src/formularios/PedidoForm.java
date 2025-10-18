package formularios;

import modelo.Pedido;
import modelo.Cliente;
import dao.PedidoDAO;
import dao.ClienteDAO;
import util.Validaciones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PedidoForm extends JFrame {
    private JComboBox<String> cmbClientes;
    private JComboBox<String> cmbTipoHamburguesa;
    private JTextField txtPrecioTotal;
    private JComboBox<String> cmbEstado;
    private JTextArea txtObservaciones;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnActualizarEstado;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;
    
    public PedidoForm() {
        setTitle("Gestión de Pedidos - La Buena Hamburguesa");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        pedidoDAO = new PedidoDAO();
        clienteDAO = new ClienteDAO();
        initComponents();
        layoutComponents();
        addEventListeners();
        cargarClientes();
        cargarPedidos();
    }
    
    private void initComponents() {
        // Combos y campos
        cmbClientes = new JComboBox<>();
        cmbTipoHamburguesa = new JComboBox<>(new String[]{"clásica", "especial", "del día"});
        txtPrecioTotal = new JTextField(10);
        cmbEstado = new JComboBox<>(new String[]{"recibido", "en preparación", "entregado"});
        txtObservaciones = new JTextArea(3, 20);
        
        // Botones
        btnAgregar = new JButton("Agregar Pedido");
        btnActualizar = new JButton("Actualizar Pedido");
        btnEliminar = new JButton("Eliminar Pedido");
        btnLimpiar = new JButton("Limpiar");
        btnActualizarEstado = new JButton("Actualizar Estado");
        
        // Estilo botones
        Color buttonColor = new Color(52, 152, 219);
        btnAgregar.setBackground(buttonColor);
        btnActualizar.setBackground(buttonColor);
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnLimpiar.setBackground(new Color(241, 196, 15));
        btnActualizarEstado.setBackground(new Color(46, 204, 113));
        
        btnAgregar.setForeground(Color.WHITE);
        btnActualizar.setForeground(Color.WHITE);
        btnEliminar.setForeground(Color.WHITE);
        btnLimpiar.setForeground(Color.WHITE);
        btnActualizarEstado.setForeground(Color.WHITE);
        
        // Tabla
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Cliente");
        modeloTabla.addColumn("Tipo Hamburguesa");
        modeloTabla.addColumn("Precio Total");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Observaciones");
        
        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));
        panelFormulario.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbClientes, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Tipo Hamburguesa:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbTipoHamburguesa, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Precio Total:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtPrecioTotal, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbEstado, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        gbc.gridheight = 2;
        panelFormulario.add(new JScrollPane(txtObservaciones), gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(236, 240, 241));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizarEstado);
        panelBotones.add(btnLimpiar);
        
        // Panel norte (formulario + botones)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        // Tabla
        JScrollPane scrollTabla = new JScrollPane(tablaPedidos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Pedidos"));
        
        add(panelNorte, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPedido();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPedido();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPedido();
            }
        });
        
        btnActualizarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstadoPedido();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        
        // Listener para selección en tabla
        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosPedidoSeleccionado();
            }
        });
    }
    
    private void agregarPedido() {
        if (validarCampos()) {
            int idCliente = obtenerIdClienteSeleccionado();
            if (idCliente == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente válido");
                return;
            }
            
            Pedido pedido = new Pedido(
                idCliente,
                cmbTipoHamburguesa.getSelectedItem().toString(),
                Double.parseDouble(txtPrecioTotal.getText()),
                cmbEstado.getSelectedItem().toString()
            );
            pedido.setObservaciones(txtObservaciones.getText());
            
            if (pedidoDAO.agregarPedido(pedido)) {
                JOptionPane.showMessageDialog(this, "Pedido agregado exitosamente");
                limpiarFormulario();
                cargarPedidos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar pedido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para actualizar");
            return;
        }
        
        if (validarCampos()) {
            int idPedido = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            int idCliente = obtenerIdClienteSeleccionado();
            
            if (idCliente == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente válido");
                return;
            }
            
            Pedido pedido = new Pedido(
                idCliente,
                cmbTipoHamburguesa.getSelectedItem().toString(),
                Double.parseDouble(txtPrecioTotal.getText()),
                cmbEstado.getSelectedItem().toString()
            );
            pedido.setIdPedido(idPedido);
            pedido.setObservaciones(txtObservaciones.getText());
            
            // Para actualización completa necesitaríamos un método update en DAO
            if (pedidoDAO.actualizarEstadoPedido(idPedido, cmbEstado.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Estado del pedido actualizado exitosamente");
                limpiarFormulario();
                cargarPedidos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar pedido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarEstadoPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para actualizar estado");
            return;
        }
        
        int idPedido = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nuevoEstado = cmbEstado.getSelectedItem().toString();
        
        if (pedidoDAO.actualizarEstadoPedido(idPedido, nuevoEstado)) {
            JOptionPane.showMessageDialog(this, "Estado del pedido actualizado exitosamente");
            cargarPedidos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar estado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este pedido?", "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            int idPedido = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            // Necesitaríamos un método delete en PedidoDAO
            JOptionPane.showMessageDialog(this, "Funcionalidad de eliminación pendiente");
        }
    }
    
    private void cargarClientes() {
        cmbClientes.removeAllItems();
        cmbClientes.addItem("-- Seleccione un cliente --");
        List<Cliente> clientes = clienteDAO.obtenerTodosClientes();
        for (Cliente cliente : clientes) {
            cmbClientes.addItem(cliente.getNombre() + " (" + cliente.getTelefono() + ") - ID: " + cliente.getIdCliente());
        }
    }
    
    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        List<Pedido> pedidos = pedidoDAO.obtenerTodosPedidos();
        
        for (Pedido pedido : pedidos) {
            Cliente cliente = clienteDAO.obtenerClientePorId(pedido.getIdCliente());
            String nombreCliente = cliente != null ? cliente.getNombre() : "Cliente no encontrado";
            
            modeloTabla.addRow(new Object[]{
                pedido.getIdPedido(),
                nombreCliente,
                pedido.getTipoHamburguesa(),
                String.format("$%.2f", pedido.getPrecioTotal()),
                pedido.getEstado(),
                pedido.getFechaHora().toString(),
                pedido.getObservaciones()
            });
        }
    }
    
    private void cargarDatosPedidoSeleccionado() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Buscar y seleccionar el cliente correspondiente
            String clienteInfo = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
            for (int i = 0; i < cmbClientes.getItemCount(); i++) {
                if (cmbClientes.getItemAt(i).contains(clienteInfo)) {
                    cmbClientes.setSelectedIndex(i);
                    break;
                }
            }
            
            cmbTipoHamburguesa.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            
            String precioStr = modeloTabla.getValueAt(filaSeleccionada, 3).toString().replace("$", "");
            txtPrecioTotal.setText(precioStr);
            
            cmbEstado.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            
            Object observaciones = modeloTabla.getValueAt(filaSeleccionada, 6);
            txtObservaciones.setText(observaciones != null ? observaciones.toString() : "");
        }
    }
    
    private int obtenerIdClienteSeleccionado() {
        String seleccion = cmbClientes.getSelectedItem().toString();
        if (seleccion.equals("-- Seleccione un cliente --")) {
            return -1;
        }
        
        try {
            // Extraer ID del formato "Nombre (Teléfono) - ID: X"
            int startIndex = seleccion.lastIndexOf("ID: ") + 4;
            String idStr = seleccion.substring(startIndex).trim();
            return Integer.parseInt(idStr);
        } catch (Exception e) {
            return -1;
        }
    }
    
    private void limpiarFormulario() {
        cmbClientes.setSelectedIndex(0);
        cmbTipoHamburguesa.setSelectedIndex(0);
        txtPrecioTotal.setText("");
        cmbEstado.setSelectedIndex(0);
        txtObservaciones.setText("");
        tablaPedidos.clearSelection();
    }
    
    private boolean validarCampos() {
        if (cmbClientes.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            cmbClientes.requestFocus();
            return false;
        }
        
        if (!Validaciones.validarNumeroPositivo(txtPrecioTotal.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese un precio total válido");
            txtPrecioTotal.requestFocus();
            return false;
        }
        
        return true;
    }
}