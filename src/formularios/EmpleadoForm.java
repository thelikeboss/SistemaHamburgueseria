package formularios;

import modelo.Empleado;
import dao.EmpleadoDAO;
import util.Validaciones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmpleadoForm extends JFrame {
    private JTextField txtNombre, txtTelefono, txtEmail, txtDireccion, txtFechaContratacion, txtSalario;
    private JComboBox<String> cmbPuesto, cmbTurno;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private EmpleadoDAO empleadoDAO;
    
    public EmpleadoForm() {
        setTitle("Gestión de Empleados - La Buena Hamburguesa");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        empleadoDAO = new EmpleadoDAO();
        initComponents();
        layoutComponents();
        addEventListeners();
        cargarEmpleados();
    }
    
    private void initComponents() {
        // Campos de texto
        txtNombre = new JTextField(20);
        txtTelefono = new JTextField(15);
        txtEmail = new JTextField(20);
        txtDireccion = new JTextField(20);
        txtFechaContratacion = new JTextField(10);
        txtSalario = new JTextField(10);
        
        // Combos
        cmbPuesto = new JComboBox<>(new String[]{"Cocinero", "Cajero", "Repartidor", "Limpieza"});
        cmbTurno = new JComboBox<>(new String[]{"Matutino", "Nocturno"});
        
        // Botones
        btnAgregar = new JButton("Agregar Empleado");
        btnActualizar = new JButton("Actualizar Empleado");
        btnEliminar = new JButton("Eliminar Empleado");
        btnLimpiar = new JButton("Limpiar");
        
        // Estilo botones
        Color buttonColor = new Color(52, 152, 219);
        btnAgregar.setBackground(buttonColor);
        btnActualizar.setBackground(buttonColor);
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnLimpiar.setBackground(new Color(241, 196, 15));
        
        btnAgregar.setForeground(Color.WHITE);
        btnActualizar.setForeground(Color.WHITE);
        btnEliminar.setForeground(Color.WHITE);
        btnLimpiar.setForeground(Color.WHITE);
        
        // Tabla
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Puesto");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Fecha Contratación");
        modeloTabla.addColumn("Salario Base");
        modeloTabla.addColumn("Turno");
        
        tablaEmpleados = new JTable(modeloTabla);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));
        panelFormulario.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Puesto:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbPuesto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtTelefono, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtDireccion, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        panelFormulario.add(new JLabel("Fecha Contratación (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtFechaContratacion, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        panelFormulario.add(new JLabel("Salario Base:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtSalario, gbc);
        
        gbc.gridx = 0; gbc.gridy = 7;
        panelFormulario.add(new JLabel("Turno:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbTurno, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(236, 240, 241));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        // Panel norte (formulario + botones)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        // Tabla
        JScrollPane scrollTabla = new JScrollPane(tablaEmpleados);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Empleados"));
        
        add(panelNorte, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpleado();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEmpleado();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleado();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        
        // Listener para selección en tabla
        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosEmpleadoSeleccionado();
            }
        });
    }
    
    private void agregarEmpleado() {
        if (validarCampos()) {
            try {
                Empleado empleado = new Empleado(
                    txtNombre.getText(),
                    cmbPuesto.getSelectedItem().toString(),
                    txtTelefono.getText(),
                    txtEmail.getText(),
                    txtDireccion.getText(),
                    LocalDate.parse(txtFechaContratacion.getText()),
                    Double.parseDouble(txtSalario.getText()),
                    cmbTurno.getSelectedItem().toString()
                );
                
                if (empleadoDAO.agregarEmpleado(empleado)) {
                    JOptionPane.showMessageDialog(this, "Empleado agregado exitosamente");
                    limpiarFormulario();
                    cargarEmpleados();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar empleado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en el formato de fecha (use YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarEmpleado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado para actualizar");
            return;
        }
        
        if (validarCampos()) {
            try {
                int idEmpleado = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                Empleado empleado = new Empleado(
                    txtNombre.getText(),
                    cmbPuesto.getSelectedItem().toString(),
                    txtTelefono.getText(),
                    txtEmail.getText(),
                    txtDireccion.getText(),
                    LocalDate.parse(txtFechaContratacion.getText()),
                    Double.parseDouble(txtSalario.getText()),
                    cmbTurno.getSelectedItem().toString()
                );
                empleado.setIdEmpleado(idEmpleado);
                
                if (empleadoDAO.actualizarEmpleado(empleado)) {
                    JOptionPane.showMessageDialog(this, "Empleado actualizado exitosamente");
                    limpiarFormulario();
                    cargarEmpleados();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar empleado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en el formato de fecha (use YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarEmpleado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado para eliminar");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este empleado?", "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            int idEmpleado = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            if (empleadoDAO.eliminarEmpleado(idEmpleado)) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente");
                limpiarFormulario();
                cargarEmpleados();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar empleado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarEmpleados() {
        modeloTabla.setRowCount(0);
        List<Empleado> empleados = empleadoDAO.obtenerTodosEmpleados();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Empleado empleado : empleados) {
            modeloTabla.addRow(new Object[]{
                empleado.getIdEmpleado(),
                empleado.getNombre(),
                empleado.getPuesto(),
                empleado.getTelefono(),
                empleado.getEmail(),
                empleado.getDireccion(),
                empleado.getFechaContratacion().format(formatter),
                String.format("$%.2f", empleado.getSalarioBase()),
                empleado.getTurnoAsignado()
            });
        }
    }
    
    private void cargarDatosEmpleadoSeleccionado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            cmbPuesto.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtTelefono.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtEmail.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtDireccion.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
            txtFechaContratacion.setText(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
            
            String salarioStr = modeloTabla.getValueAt(filaSeleccionada, 7).toString().replace("$", "");
            txtSalario.setText(salarioStr);
            
            cmbTurno.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 8).toString());
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        cmbPuesto.setSelectedIndex(0);
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        txtFechaContratacion.setText("");
        txtSalario.setText("");
        cmbTurno.setSelectedIndex(0);
        tablaEmpleados.clearSelection();
    }
    
    private boolean validarCampos() {
        if (!Validaciones.validarTextoNoVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            txtNombre.requestFocus();
            return false;
        }
        
        if (!Validaciones.validarTelefono(txtTelefono.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese un teléfono válido (10-15 dígitos)");
            txtTelefono.requestFocus();
            return false;
        }
        
        if (!Validaciones.validarEmail(txtEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese un email válido");
            txtEmail.requestFocus();
            return false;
        }
        
        if (!Validaciones.validarTextoNoVacio(txtDireccion.getText())) {
            JOptionPane.showMessageDialog(this, "La dirección es obligatoria");
            txtDireccion.requestFocus();
            return false;
        }
        
        try {
            LocalDate.parse(txtFechaContratacion.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD");
            txtFechaContratacion.requestFocus();
            return false;
        }
        
        if (!Validaciones.validarNumeroPositivo(txtSalario.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese un salario válido");
            txtSalario.requestFocus();
            return false;
        }
        
        return true;
    }
}