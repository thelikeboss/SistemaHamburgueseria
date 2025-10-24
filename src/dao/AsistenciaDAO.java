package dao;

import modelo.Asistencia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {
    
    public boolean registrarAsistencia(Asistencia asistencia) {
        String sql = "INSERT INTO Asistencias (ID_Empleado, Fecha, Hora_Entrada_Real, Hora_Salida_Real, Retraso_Minutos, Falta) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, asistencia.getIdEmpleado());
            pstmt.setDate(2, Date.valueOf(asistencia.getFecha()));
            
            if (asistencia.getHoraEntradaReal() != null) {
                pstmt.setTime(3, Time.valueOf(asistencia.getHoraEntradaReal()));
            } else {
                pstmt.setNull(3, Types.TIME);
            }
            
            if (asistencia.getHoraSalidaReal() != null) {
                pstmt.setTime(4, Time.valueOf(asistencia.getHoraSalidaReal()));
            } else {
                pstmt.setNull(4, Types.TIME);
            }
            
            pstmt.setInt(5, asistencia.getRetrasoMinutos());
            
            if (asistencia.getFalta() != null && !asistencia.getFalta().isEmpty()) {
                pstmt.setString(6, asistencia.getFalta());
            } else {
                pstmt.setNull(6, Types.VARCHAR);
            }
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar asistencia: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Asistencia> obtenerAsistenciasPorEmpleado(int idEmpleado) {
        List<Asistencia> asistencias = new ArrayList<>();
        String sql = "SELECT a.*, e.Nombre as NombreEmpleado FROM Asistencias a " +
                    "JOIN Empleados e ON a.ID_Empleado = e.ID_Empleado " +
                    "WHERE a.ID_Empleado = ? ORDER BY a.Fecha DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idEmpleado);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setIdAsistencia(rs.getInt("ID_Asistencia"));
                asistencia.setIdEmpleado(rs.getInt("ID_Empleado"));
                asistencia.setFecha(rs.getDate("Fecha").toLocalDate());
                
                Time horaEntrada = rs.getTime("Hora_Entrada_Real");
                if (horaEntrada != null) {
                    asistencia.setHoraEntradaReal(horaEntrada.toLocalTime());
                }
                
                Time horaSalida = rs.getTime("Hora_Salida_Real");
                if (horaSalida != null) {
                    asistencia.setHoraSalidaReal(horaSalida.toLocalTime());
                }
                
                asistencia.setRetrasoMinutos(rs.getInt("Retraso_Minutos"));
                asistencia.setFalta(rs.getString("Falta"));
                
                asistencias.add(asistencia);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener asistencias: " + e.getMessage());
            e.printStackTrace();
        }
        
        return asistencias;
    }
    
    public boolean actualizarSalida(int idAsistencia, java.time.LocalTime horaSalida) {
        String sql = "UPDATE Asistencias SET Hora_Salida_Real = ? WHERE ID_Asistencia = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setTime(1, Time.valueOf(horaSalida));
            pstmt.setInt(2, idAsistencia);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar salida: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}