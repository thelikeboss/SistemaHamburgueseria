package dao;

import modelo.Horario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorarioDAO {
    
    public boolean agregarHorario(Horario horario) {
        String sql = "INSERT INTO Horarios (ID_Empleado, Fecha, Turno, Hora_Inicio, Hora_Fin, Horas_Extra, Estado_Asistencia) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, horario.getIdEmpleado());
            pstmt.setDate(2, Date.valueOf(horario.getFecha()));
            pstmt.setString(3, horario.getTurno());
            pstmt.setTime(4, Time.valueOf(horario.getHoraInicio()));
            pstmt.setTime(5, Time.valueOf(horario.getHoraFin()));
            pstmt.setDouble(6, horario.getHorasExtra());
            pstmt.setString(7, horario.getEstadoAsistencia());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Horario> obtenerHorariosPorEmpleado(int idEmpleado) {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT h.*, e.Nombre as NombreEmpleado FROM Horarios h " +
                    "JOIN Empleados e ON h.ID_Empleado = e.ID_Empleado " +
                    "WHERE h.ID_Empleado = ? ORDER BY h.Fecha DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idEmpleado);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Horario horario = new Horario();
                horario.setIdHorario(rs.getInt("ID_Horario"));
                horario.setIdEmpleado(rs.getInt("ID_Empleado"));
                horario.setFecha(rs.getDate("Fecha").toLocalDate());
                horario.setTurno(rs.getString("Turno"));
                horario.setHoraInicio(rs.getTime("Hora_Inicio").toLocalTime());
                horario.setHoraFin(rs.getTime("Hora_Fin").toLocalTime());
                horario.setHorasExtra(rs.getDouble("Horas_Extra"));
                horario.setEstadoAsistencia(rs.getString("Estado_Asistencia"));
                
                horarios.add(horario);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return horarios;
    }
    
    public boolean actualizarEstadoHorario(int idHorario, String nuevoEstado) {
        String sql = "UPDATE Horarios SET Estado_Asistencia = ? WHERE ID_Horario = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idHorario);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}