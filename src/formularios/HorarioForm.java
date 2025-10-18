package formularios;

import modelo.Horario;
import modelo.Empleado;
import dao.HorarioDAO;
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

public class HorarioForm extends JFrame {
    private JComboBox<String> cmbEmpleados;
    private JTextField txtFecha, txtHoraInicio, txtHoraFin, txtHorasExtra;
    private JComboBox<String> cmbTurno, cmbEstado;
    private JButton btnAgregar, btnActualizarEstado, btnLimpiar;
    private JTable tablaHorarios;
    private DefaultTableModel modeloTabla;
    private HorarioDAO horarioDAO;
    private EmpleadoDAO empleadoDAO;

    public HorarioForm() {
        setTitle("Gestión de Horarios - La Buena Hamburguesa");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        horarioDAO = new HorarioDAO();
        empleadoDAO = new EmpleadoDAO();
        initComponents();
        layoutComponents();
        addEventListeners();
        cargarEmpleados();
    }

    private void initComponents() {
        // Combos y campos
        cmbEmpleados = new JComboBox<>();
        txtFecha = new JTextField(10);
        cmbTurno = new JComboBox<>(new String[] { "Matutino", "Nocturno" });
        txtHoraInicio = new JTextField(8);
        txtHoraFin = new JTextField(8);
        txtHorasExtra = new JTextField(5);
        cmbEstado = new JComboBox<>(new String[] { "Programado", "Completado", "Ausente" });

        // Configurar valores por defecto para horarios
        txtHoraInicio.setText("06:00");
        txtHoraFin.setText("14:00");
        txtHorasExtra.setText("0");

        // Botones
        btnAgregar = new JButton("Agregar Horario");
        btnActualizarEstado = new JButton("Actualizar Estado");
        btnLimpiar = new JButton("Limpiar");

        // Estilo botones
        Color buttonColor = new Color(52, 152, 219);
        btnAgregar.setBackground(buttonColor);
        btnActualizarEstado.setBackground(new Color(46, 204, 113));
        btnLimpiar.setBackground(new Color(241, 196, 15));

        btnAgregar.setForeground(Color.WHITE);
        btnActualizarEstado.setForeground(Color.WHITE);
        btnLimpiar.setForeground(Color.WHITE);

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
        modeloTabla.addColumn("Turno");
        modeloTabla.addColumn("Hora Inicio");
        modeloTabla.addColumn("Hora Fin");
        modeloTabla.addColumn("Horas Extra");
        modeloTabla.addColumn("Estado");

        tablaHorarios = new JTable(modeloTabla);
        tablaHorarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Asignación de Horarios"));
        panelFormulario.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Empleado:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbEmpleados, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Turno:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbTurno, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Hora Inicio (HH:MM):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtHoraInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Hora Fin (HH:MM):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtHoraFin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelFormulario.add(new JLabel("Horas Extra:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtHorasExtra, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panelFormulario.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbEstado, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(236, 240, 241));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizarEstado);
        panelBotones.add(btnLimpiar);

        // Panel norte (formulario + botones)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        // Tabla
        JScrollPane scrollTabla = new JScrollPane(tablaHorarios);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Horarios Asignados"));

        add(panelNorte, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
    }

    private void addEventListeners() {
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarHorario();
            }
        });

        btnActualizarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstadoHorario();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        // Listener para cambio de turno
        cmbTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarHorariosPorTurno();
            }
        });

        // Listener para selección de empleado
        cmbEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarHorariosEmpleado();
            }
        });
    }

    private void agregarHorario() {
        if (validarCampos()) {
            try {
                int idEmpleado = obtenerIdEmpleadoSeleccionado();
                if (idEmpleado == -1) {
                    JOptionPane.showMessageDialog(this, "Seleccione un empleado válido");
                    return;
                }

                Horario horario = new Horario(
                        idEmpleado,
                        LocalDate.parse(txtFecha.getText()),
                        cmbTurno.getSelectedItem().toString(),
                        LocalTime.parse(txtHoraInicio.getText()),
                        LocalTime.parse(txtHoraFin.getText()));
                horario.setHorasExtra(Double.parseDouble(txtHorasExtra.getText()));
                horario.setEstadoAsistencia(cmbEstado.getSelectedItem().toString());

                if (horarioDAO.agregarHorario(horario)) {
                    JOptionPane.showMessageDialog(this, "Horario agregado exitosamente");
                    limpiarFormulario();
                    cargarHorariosEmpleado();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar horario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en formato de fecha u hora", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarEstadoHorario() {
        int filaSeleccionada = tablaHorarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un horario para actualizar estado");
            return;
        }

        int idHorario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nuevoEstado = cmbEstado.getSelectedItem().toString();

        if (horarioDAO.actualizarEstadoHorario(idHorario, nuevoEstado)) {
            JOptionPane.showMessageDialog(this, "Estado del horario actualizado exitosamente");
            cargarHorariosEmpleado();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar estado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarHorariosPorTurno() {
        String turno = cmbTurno.getSelectedItem().toString();
        if (turno.equals("Matutino")) {
            txtHoraInicio.setText("06:00");
            txtHoraFin.setText("14:00");
        } else if (turno.equals("Nocturno")) {
            txtHoraInicio.setText("14:00");
            txtHoraFin.setText("22:00");
        }
    }

    private void cargarEmpleados() {
        cmbEmpleados.removeAllItems();
        cmbEmpleados.addItem("-- Seleccione un empleado --");
        List<Empleado> empleados = empleadoDAO.obtenerTodosEmpleados();
        for (Empleado empleado : empleados) {
            cmbEmpleados.addItem(
                    empleado.getNombre() + " - " + empleado.getPuesto() + " - ID: " + empleado.getIdEmpleado());
        }
    }

    private void cargarHorariosEmpleado() {
        int idEmpleado = obtenerIdEmpleadoSeleccionado();
        if (idEmpleado == -1) {
            modeloTabla.setRowCount(0);
            return;
        }

        modeloTabla.setRowCount(0);
        List<Horario> horarios = horarioDAO.obtenerHorariosPorEmpleado(idEmpleado);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Horario horario : horarios) {
            Empleado empleado = empleadoDAO.obtenerEmpleadoPorId(horario.getIdEmpleado());
            String nombreEmpleado = empleado != null ? empleado.getNombre() : "Empleado no encontrado";

            modeloTabla.addRow(new Object[] {
                    horario.getIdHorario(),
                    nombreEmpleado,
                    horario.getFecha().format(dateFormatter),
                    horario.getTurno(),
                    horario.getHoraInicio().format(timeFormatter),
                    horario.getHoraFin().format(timeFormatter),
                    String.format("%.1f", horario.getHorasExtra()),
                    horario.getEstadoAsistencia()
            });
        }
    }

    private int obtenerIdEmpleadoSeleccionado() {
        String seleccion = cmbEmpleados.getSelectedItem().toString();
        if (seleccion.equals("-- Seleccione un empleado --")) {
            return -1;
        }

        try {
            int startIndex = seleccion.lastIndexOf("ID: ") + 4;
            String idStr = seleccion.substring(startIndex).trim();
            return Integer.parseInt(idStr);
        } catch (Exception e) {
            return -1;
        }
    }

    private void limpiarFormulario() {
        cmbEmpleados.setSelectedIndex(0);
        txtFecha.setText("");
        cmbTurno.setSelectedIndex(0);
        actualizarHorariosPorTurno(); // Para resetear horas
        txtHorasExtra.setText("0");
        cmbEstado.setSelectedIndex(0);
        tablaHorarios.clearSelection();
    }

    private boolean validarCampos() {
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
            LocalTime.parse(txtHoraInicio.getText());
            LocalTime.parse(txtHoraFin.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de hora inválido. Use HH:MM");
            txtHoraInicio.requestFocus();
            return false;
        }

        if (!Validaciones.validarNumeroPositivo(txtHorasExtra.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese horas extra válidas");
            txtHorasExtra.requestFocus();
            return false;
        }

        return true;
    }

    // Método auxiliar para EmpleadoDAO
    private Empleado obtenerEmpleadoPorId(int id) {
        List<Empleado> empleados = empleadoDAO.obtenerTodosEmpleados();
        for (Empleado empleado : empleados) {
            if (empleado.getIdEmpleado() == id) {
                return empleado;
            }
        }
        return null;
    }
}