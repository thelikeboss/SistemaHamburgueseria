package formularios;

import modelo.Asistencia;
import modelo.Empleado;
import dao.AsistenciaDAO;
import dao.EmpleadoDAO;
import util.Validaciones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AsistenciaForm extends JFrame {
    private JComboBox<String> cmbEmpleados;
    private JTextField txtFecha, txtHoraEntrada, txtHoraSalida, txtRetraso;
    private JComboBox<String> cmbFalta;
    private JButton btnRegistrarEntrada, btnRegistrarSalida, btnRegistrarFalta, btnLimpiar, btnHoraActual;
    private JTable tablaAsistencias;
    private DefaultTableModel modeloTabla;
    private AsistenciaDAO asistenciaDAO;
    private EmpleadoDAO empleadoDAO;
    
    public AsistenciaForm() {
        setTitle("Gestión de Asistencias - La Buena Hamburguesa");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        asistenciaDAO = new AsistenciaDAO();
        empleadoDAO = new EmpleadoDAO();
        initComponents();
        layoutComponents();
        addEventListeners();
        cargarEmpleados(); // ESTE MÉTODO ESTÁ IMPLEMENTADO AHORA
        cargarAsistencias();
    }
    
    private void initComponents() {
        // Combos y campos
        cmbEmpleados = new JComboBox<>();
        txtFecha = new JTextField(10);
        txtHoraEntrada = new JTextField(8);
        txtHoraSalida = new JTextField(8);
        txtRetraso = new JTextField(5);
        cmbFalta = new JComboBox<>(new String[]{"", "Retraso", "Ausencia", "Enfermedad"});
        
        // Configurar fecha actual por defecto
        txtFecha.setText(LocalDate.now().toString());
        txtRetraso.setText("0");
        
        // Botones
        btnRegistrarEntrada = new JButton("Registrar Entrada");
        btnRegistrarSalida = new JButton("Registrar Salida");
        btnRegistrarFalta = new JButton("Registrar Falta");
        btnLimpiar = new JButton("Limpiar");
        btnHoraActual = new JButton("Hora Actual");
        
        // Estilo botones
        btnRegistrarEntrada.setBackground(new Color(46, 204, 113));
        btnRegistrarSalida.setBackground(new Color(52, 152, 219));
        btnRegistrarFalta.setBackground(new Color(231, 76, 60));
        btnLimpiar.setBackground(new Color(241, 196, 15));
        btnHoraActual.setBackground(new Color(149, 165, 166));
        
        btnRegistrarEntrada.setForeground(Color.WHITE);
        btnRegistrarSalida.setForeground(Color.WHITE);
        btnRegistrarFalta.setForeground(Color.WHITE);
        btnLimpiar.setForeground(Color.WHITE);
        btnHoraActual.setForeground(Color.WHITE);
        
        // Tabla
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Empleado");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora Entrada");
        modeloTabla.addColumn("Hora Salida");
        modeloTabla.addColumn("Retraso (min)");
        modeloTabla.addColumn("Falta");
        
        tablaAsistencias = new JTable(modeloTabla);
        tablaAsistencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registro de Asistencias"));
        panelFormulario.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Empleado:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbEmpleados, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtFecha, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Hora Entrada (HH:MM):"), gbc);
        gbc.gridx = 1;
        JPanel panelHoraEntrada = new JPanel(new BorderLayout());
        panelHoraEntrada.add(txtHoraEntrada, BorderLayout.CENTER);
        panelHoraEntrada.add(btnHoraActual, BorderLayout.EAST);
        panelFormulario.add(panelHoraEntrada, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Hora Salida (HH:MM):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtHoraSalida, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Retraso (minutos):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtRetraso, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        panelFormulario.add(new JLabel("Tipo de Falta:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbFalta, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(236, 240, 241));
        panelBotones.add(btnRegistrarEntrada);
        panelBotones.add(btnRegistrarSalida);
        panelBotones.add(btnRegistrarFalta);
        panelBotones.add(btnLimpiar);
        
        // Panel norte (formulario + botones)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        // Tabla
        JScrollPane scrollTabla = new JScrollPane(tablaAsistencias);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Historial de Asistencias"));
        
        add(panelNorte, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        btnRegistrarEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarEntrada();
            }
        });
        
        btnRegistrarSalida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarSalida();
            }
        });
        
        btnRegistrarFalta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarFalta();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        
        btnHoraActual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtHoraEntrada.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        });
        
        // Listener para selección de empleado
        cmbEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarAsistenciasEmpleado();
            }
        });
    }
    
    // MÉTODO IMPLEMENTADO - cargarEmpleados
    private void cargarEmpleados() {
        cmbEmpleados.removeAllItems();
        cmbEmpleados.addItem("-- Seleccione un empleado --");
        List<Empleado> empleados = empleadoDAO.obtenerTodosEmpleados();
        for (Empleado empleado : empleados) {
            cmbEmpleados.addItem(empleado.getNombre() + " - " + empleado.getPuesto() + " (ID: " + empleado.getIdEmpleado() + ")");
        }
    }
    
    private void registrarEntrada() {
        if (validarCamposEntrada()) {
            try {
                int idEmpleado = obtenerIdEmpleadoSeleccionado();
                if (idEmpleado == -1) {
                    JOptionPane.showMessageDialog(this, "Seleccione un empleado válido");
                    return;
                }
                
                Asistencia asistencia = new Asistencia(
                    idEmpleado,
                    LocalDate.parse(txtFecha.getText())
                );
                asistencia.setHoraEntradaReal(LocalTime.parse(txtHoraEntrada.getText()));
                asistencia.setRetrasoMinutos(Integer.parseInt(txtRetraso.getText()));
                
                if (asistenciaDAO.registrarAsistencia(asistencia)) {
                    JOptionPane.showMessageDialog(this, "Entrada registrada exitosamente");
                    limpiarFormulario();
                    cargarAsistenciasEmpleado();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar entrada", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en formato de fecha u hora", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void registrarSalida() {
        int filaSeleccionada = tablaAsistencias.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de asistencia para registrar salida");
            return;
        }
        
        try {
            int idAsistencia = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            LocalTime horaSalida = LocalTime.parse(txtHoraSalida.getText());
            
            if (asistenciaDAO.actualizarSalida(idAsistencia, horaSalida)) {
                JOptionPane.showMessageDialog(this, "Salida registrada exitosamente");
                limpiarFormulario();
                cargarAsistenciasEmpleado();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar salida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en formato de hora", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void registrarFalta() {
        if (validarCamposFalta()) {
            try {
                int idEmpleado = obtenerIdEmpleadoSeleccionado();
                if (idEmpleado == -1) {
                    JOptionPane.showMessageDialog(this, "Seleccione un empleado válido");
                    return;
                }
                
                Asistencia asistencia = new Asistencia(
                    idEmpleado,
                    LocalDate.parse(txtFecha.getText())
                );
                asistencia.setFalta(cmbFalta.getSelectedItem().toString());
                
                if (asistenciaDAO.registrarAsistencia(asistencia)) {
                    JOptionPane.showMessageDialog(this, "Falta registrada exitosamente");
                    limpiarFormulario();
                    cargarAsistenciasEmpleado();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar falta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en formato de fecha", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void cargarAsistencias() {
        modeloTabla.setRowCount(0);
        // Cargar todas las asistencias o las del empleado seleccionado
        if (cmbEmpleados.getSelectedIndex() > 0) {
            cargarAsistenciasEmpleado();
        }
    }
    
    private void cargarAsistenciasEmpleado() {
        int idEmpleado = obtenerIdEmpleadoSeleccionado();
        if (idEmpleado == -1) {
            modeloTabla.setRowCount(0);
            return;
        }
        
        modeloTabla.setRowCount(0);
        List<Asistencia> asistencias = asistenciaDAO.obtenerAsistenciasPorEmpleado(idEmpleado);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        for (Asistencia asistencia : asistencias) {
            Empleado empleado = obtenerEmpleadoPorId(asistencia.getIdEmpleado());
            String nombreEmpleado = empleado != null ? empleado.getNombre() : "Empleado no encontrado";
            
            String horaEntrada = asistencia.getHoraEntradaReal() != null ? 
                asistencia.getHoraEntradaReal().format(timeFormatter) : "--";
            String horaSalida = asistencia.getHoraSalidaReal() != null ? 
                asistencia.getHoraSalidaReal().format(timeFormatter) : "--";
            String retraso = asistencia.getRetrasoMinutos() > 0 ? 
                String.valueOf(asistencia.getRetrasoMinutos()) : "0";
            String falta = asistencia.getFalta() != null ? asistencia.getFalta() : "";
            
            modeloTabla.addRow(new Object[]{
                asistencia.getIdAsistencia(),
                nombreEmpleado,
                asistencia.getFecha().format(dateFormatter),
                horaEntrada,
                horaSalida,
                retraso,
                falta
            });
        }
    }
    
    private int obtenerIdEmpleadoSeleccionado() {
        String seleccion = cmbEmpleados.getSelectedItem().toString();
        if (seleccion.equals("-- Seleccione un empleado --")) {
            return -1;
        }
        
        try {
            // Extraer ID del formato "Nombre - Puesto (ID: X)"
            int startIndex = seleccion.lastIndexOf("(ID: ") + 5;
            int endIndex = seleccion.lastIndexOf(")");
            String idStr = seleccion.substring(startIndex, endIndex).trim();
            return Integer.parseInt(idStr);
        } catch (Exception e) {
            System.err.println("Error al extraer ID del empleado: " + e.getMessage());
            return -1;
        }
    }
    
    private Empleado obtenerEmpleadoPorId(int id) {
        List<Empleado> empleados = empleadoDAO.obtenerTodosEmpleados();
        for (Empleado empleado : empleados) {
            if (empleado.getIdEmpleado() == id) {
                return empleado;
            }
        }
        return null;
    }
    
    private void limpiarFormulario() {
        txtFecha.setText(LocalDate.now().toString());
        txtHoraEntrada.setText("");
        txtHoraSalida.setText("");
        txtRetraso.setText("0");
        cmbFalta.setSelectedIndex(0);
        tablaAsistencias.clearSelection();
    }
    
    private boolean validarCamposEntrada() {
        if (cmbEmpleados.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado");
            cmbEmpleados.requestFocus();
            return false;
        }
        
        try {
            LocalDate.parse(txtFecha.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD");
            txtFecha.requestFocus();
            return false;
        }
        
        try {
            LocalTime.parse(txtHoraEntrada.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de hora inválido. Use HH:MM");
            txtHoraEntrada.requestFocus();
            return false;
        }
        
        if (!Validaciones.validarEnteroPositivo(txtRetraso.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor válido para retraso");
            txtRetraso.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private boolean validarCamposFalta() {
        if (cmbEmpleados.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado");
            cmbEmpleados.requestFocus();
            return false;
        }
        
        try {
            LocalDate.parse(txtFecha.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD");
            txtFecha.requestFocus();
            return false;
        }
        
        if (cmbFalta.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de falta");
            cmbFalta.requestFocus();
            return false;
        }
        
        return true;
    }
}