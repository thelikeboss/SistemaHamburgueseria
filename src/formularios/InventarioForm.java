package formularios;

import modelo.Ingrediente;
import dao.InventarioDAO;
import util.Validaciones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventarioForm extends JFrame {
    private JTextField txtNombre, txtCantidad, txtUnidad, txtProveedor, txtStockMinimo;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnVerBajoStock;
    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;
    private InventarioDAO inventarioDAO;
    
    public InventarioForm() {
        setTitle("Gestión de Inventario - La Buena Hamburguesa");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        inventarioDAO = new InventarioDAO();
        initComponents();
        layoutComponents();
        addEventListeners();
        cargarInventario();
    }
    
    private void initComponents() {
        // Campos de texto
        txtNombre = new JTextField(20);
        txtCantidad = new JTextField(10);
        txtUnidad = new JTextField(10);
        txtProveedor = new JTextField(20);
        txtStockMinimo = new JTextField(10);
        
        // Botones
        btnAgregar = new JButton("Agregar Ingrediente");
        btnActualizar = new JButton("Actualizar Ingrediente");
        btnEliminar = new JButton("Eliminar Ingrediente");
        btnLimpiar = new JButton("Limpiar");
        btnVerBajoStock = new JButton("Ver Bajo Stock");
        
        // Estilo botones
        Color buttonColor = new Color(52, 152, 219);
        btnAgregar.setBackground(buttonColor);
        btnActualizar.setBackground(buttonColor);
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnLimpiar.setBackground(new Color(241, 196, 15));
        btnVerBajoStock.setBackground(new Color(230, 126, 34));
        
        btnAgregar.setForeground(Color.WHITE);
        btnActualizar.setForeground(Color.WHITE);
        btnEliminar.setForeground(Color.WHITE);
        btnLimpiar.setForeground(Color.WHITE);
        btnVerBajoStock.setForeground(Color.WHITE);
        
        // Tabla
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Unidad");
        modeloTabla.addColumn("Proveedor");
        modeloTabla.addColumn("Stock Mínimo");
        modeloTabla.addColumn("Estado");
        
        tablaInventario = new JTable(modeloTabla);
        tablaInventario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Ingrediente"));
        panelFormulario.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCantidad, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Unidad Medida:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtUnidad, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Proveedor:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtProveedor, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Stock Mínimo:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtStockMinimo, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(236, 240, 241));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVerBajoStock);
        panelBotones.add(btnLimpiar);
        
        // Panel norte (formulario + botones)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        // Tabla
        JScrollPane scrollTabla = new JScrollPane(tablaInventario);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Inventario de Ingredientes"));
        
        add(panelNorte, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarIngrediente();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarIngrediente();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarIngrediente();
            }
        });
        
        btnVerBajoStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verIngredientesBajoStock();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        
        // Listener para selección en tabla
        tablaInventario.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosIngredienteSeleccionado();
            }
        });
    }
    
    private void agregarIngrediente() {
        if (validarCampos()) {
            Ingrediente ingrediente = new Ingrediente(
                txtNombre.getText(),
                Double.parseDouble(txtCantidad.getText()),
                txtUnidad.getText(),
                txtProveedor.getText(),
                Double.parseDouble(txtStockMinimo.getText())
            );
            
            if (inventarioDAO.agregarIngrediente(ingrediente)) {
                JOptionPane.showMessageDialog(this, "Ingrediente agregado exitosamente");
                limpiarFormulario();
                cargarInventario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar ingrediente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarIngrediente() {
        int filaSeleccionada = tablaInventario.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ingrediente para actualizar");
            return;
        }
        
        if (validarCampos()) {
            int idIngrediente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Ingrediente ingrediente = new Ingrediente(
                txtNombre.getText(),
                Double.parseDouble(txtCantidad.getText()),
                txtUnidad.getText(),
                txtProveedor.getText(),
                Double.parseDouble(txtStockMinimo.getText())
            );
            ingrediente.setIdIngrediente(idIngrediente);
            
            if (inventarioDAO.actualizarIngrediente(ingrediente)) {
                JOptionPane.showMessageDialog(this, "Ingrediente actualizado exitosamente");
                limpiarFormulario();
                cargarInventario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar ingrediente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarIngrediente() {
        int filaSeleccionada = tablaInventario.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ingrediente para eliminar");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este ingrediente?", "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            int idIngrediente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            // Necesitaríamos un método delete en InventarioDAO
            JOptionPane.showMessageDialog(this, "Funcionalidad de eliminación pendiente");
        }
    }
    
    private void verIngredientesBajoStock() {
        List<Ingrediente> ingredientesBajoStock = inventarioDAO.obtenerIngredientesBajoStock();
        
        if (ingredientesBajoStock.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ingredientes con stock bajo");
        } else {
            StringBuilder mensaje = new StringBuilder("Ingredientes con stock bajo:\n\n");
            for (Ingrediente ingrediente : ingredientesBajoStock) {
                mensaje.append(String.format("- %s: %.2f %s (Mínimo: %.2f)%n",
                    ingrediente.getNombreIngrediente(),
                    ingrediente.getCantidadDisponible(),
                    ingrediente.getUnidadMedida(),
                    ingrediente.getStockMinimo()));
            }
            JOptionPane.showMessageDialog(this, mensaje.toString(), "Ingredientes Bajo Stock", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void cargarInventario() {
        modeloTabla.setRowCount(0);
        List<Ingrediente> ingredientes = inventarioDAO.obtenerTodosIngredientes();
        
        for (Ingrediente ingrediente : ingredientes) {
            String estado = ingrediente.necesitaReposicion() ? "BAJO STOCK" : "OK";
            Color rowColor = ingrediente.necesitaReposicion() ? Color.RED : Color.BLACK;
            
            modeloTabla.addRow(new Object[]{
                ingrediente.getIdIngrediente(),
                ingrediente.getNombreIngrediente(),
                String.format("%.2f", ingrediente.getCantidadDisponible()),
                ingrediente.getUnidadMedida(),
                ingrediente.getProveedor(),
                String.format("%.2f", ingrediente.getStockMinimo()),
                estado
            });
        }
    }
    
    private void cargarDatosIngredienteSeleccionado() {
        int filaSeleccionada = tablaInventario.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtCantidad.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtUnidad.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtProveedor.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtStockMinimo.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCantidad.setText("");
        txtUnidad.setText("");
        txtProveedor.setText("");
        txtStockMinimo.setText("");
        tablaInventario.clearSelection();
    }
    
    private boolean validarCampos() {
        if (!Validaciones.validarTextoNoVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "El nombre del ingrediente es obligatorio");
            txtNombre.requestFocus();
            return false;
        }
        
        if (!Validaciones.validarNumeroPositivo(txtCantidad.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida");
            txtCantidad.requestFocus();
            return false;
        }
        
        if (!Validaciones.validarTextoNoVacio(txtUnidad.getText())) {
            JOptionPane.showMessageDialog(this, "La unidad de medida es obligatoria");
            txtUnidad.requestFocus();
            return false;
        }
        
        if (!Validaciones.validarNumeroPositivo(txtStockMinimo.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese un stock mínimo válido");
            txtStockMinimo.requestFocus();
            return false;
        }
        
        return true;
    }
}