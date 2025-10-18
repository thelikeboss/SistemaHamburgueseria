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
    private JButton btnRegistrarEntrada, btnRegistrarSalida, btnRegistrarFalta, btnLimpiar;
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
        cargarEmpleados();
        cargarAsistencias();
    }

    private void cargarAsistencias() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cargarAsistencias'");
    }

    private void cargarEmpleados() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cargarEmpleados'");
    }

    private void initComponents() {
        // Combos y campos
        cmbEmpleados = new JComboBox<>();
        txtFecha = new JTextField(10);
        txtHoraEntrada = new JTextField(8);
        txtHoraSalida = new JTextField(8);
        txtRetraso = new JTextField(5);
        cmbFalta = new JComboBox<>(new String[] { "", "Retraso", "Ausencia", "Enfermedad" });

        // Configurar fecha actual por defecto
        txtFecha.setText(LocalDate.now().toString());

        // Botones
        btnRegistrarEntrada = new JButton("Registrar Entrada");
        btnRegistrarSalida = new JButton("Registrar Salida");
        btnRegistrarFalta = new JButton("Registrar Falta");
        btnLimpiar = new JButton("Limpiar");

        // Estilo botones
        btnRegistrarEntrada.setBackground(new Color(46, 204, 113));
        btnRegistrarSalida.setBackground(new Color(52, 152, 219));
        btnRegistrarFalta.setBackground(new Color(231, 76, 60));
        btnLimpiar.setBackground(new Color(241, 196, 15));

        btnRegistrarEntrada.setForeground(Color.WHITE);
        btnRegistrarSalida.setForeground(Color.WHITE);
        btnRegistrarFalta.setForeground(Color.WHITE);
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
        panelFormulario.add(new JLabel("Hora Entrada (HH:MM):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtHoraEntrada, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Hora Salida (HH:MM):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtHoraSalida, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Retraso (minutos):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtRetraso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
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

            public void limpiarFormulario() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'limpiarFormulario'");
            }
        });

        // Listener para selección de empleado
        cmbEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarAsistencias();
            }
        });

        // Auto-completar hora actual
        JButton btnHoraActual = new JButton("Ahora");
        btnHoraActual.addActionListener(e -> {
            txtHoraEntrada.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        });
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
                        LocalDate.parse(txtFecha.getText()));
                asistencia.setHoraEntradaReal(LocalTime.parse(txtHoraEntrada.getText()));
                asistencia.setRetrasoMinutos(Integer.parseInt(txtRetraso.getText()));

                if (asistenciaDAO.registrarAsistencia(asistencia)) {
                    JOptionPane.showMessageDialog(this, "Entrada registrada exitosamente");

                    cargarAsistencias();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar entrada", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en formato de fecha u hora", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int obtenerIdEmpleadoSeleccionado() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerIdEmpleadoSeleccionado'");
    }

    private boolean validarCamposEntrada() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validarCamposEntrada'");
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

                cargarAsistencias();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar salida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en formato de hora", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarFalta() {
        if (validarCamposEntrada()) {
            try {
                int idEmpleado = obtenerIdEmpleadoSeleccionado();
                if (idEmpleado == -1) {
                    JOptionPane.showMessageDialog(this, "Seleccione un empleado válido");
                    return;
                }

                Asistencia asistencia = new Asistencia(
                        idEmpleado,
                        LocalDate.parse(txtFecha.getText()));
                asistencia.setFalta(cmbFalta.getSelectedItem().toString());

                if (asistenciaDAO.registrarAsistencia(asistencia)) {
                    JOptionPane.showMessageDialog(this, "Falta registrada exitosamente");

                    cargarAsistencias();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar falta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en formato de fecha u hora", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
