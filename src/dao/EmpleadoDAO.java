package dao;

import modelo.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    
    public boolean agregarEmpleado(Empleado empleado) {
        String sql = "INSERT INTO Empleados (Nombre, Puesto, Telefono, Email, Direccion, Fecha_Contratacion, Salario_Base, Turno_asignado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getPuesto());
            pstmt.setString(3, empleado.getTelefono());
            pstmt.setString(4, empleado.getEmail());
            pstmt.setString(5, empleado.getDireccion());
            pstmt.setDate(6, Date.valueOf(empleado.getFechaContratacion()));
            pstmt.setDouble(7, empleado.getSalarioBase());
            pstmt.setString(8, empleado.getTurnoAsignado());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al agregar empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Empleado> obtenerTodosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM Empleados ORDER BY Nombre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("ID_Empleado"));
                empleado.setNombre(rs.getString("Nombre"));
                empleado.setPuesto(rs.getString("Puesto"));
                empleado.setTelefono(rs.getString("Telefono"));
                empleado.setEmail(rs.getString("Email"));
                empleado.setDireccion(rs.getString("Direccion"));
                empleado.setFechaContratacion(rs.getDate("Fecha_Contratacion").toLocalDate());
                empleado.setSalarioBase(rs.getDouble("Salario_Base"));
                empleado.setTurnoAsignado(rs.getString("Turno_asignado"));
                
                empleados.add(empleado);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener empleados: " + e.getMessage());
            e.printStackTrace();
        }
        
        return empleados;
    }
    
    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE Empleados SET Nombre=?, Puesto=?, Telefono=?, Email=?, Direccion=?, Fecha_Contratacion=?, Salario_Base=?, Turno_asignado=? WHERE ID_Empleado=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getPuesto());
            pstmt.setString(3, empleado.getTelefono());
            pstmt.setString(4, empleado.getEmail());
            pstmt.setString(5, empleado.getDireccion());
            pstmt.setDate(6, Date.valueOf(empleado.getFechaContratacion()));
            pstmt.setDouble(7, empleado.getSalarioBase());
            pstmt.setString(8, empleado.getTurnoAsignado());
            pstmt.setInt(9, empleado.getIdEmpleado());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarEmpleado(int idEmpleado) {
        String sql = "DELETE FROM Empleados WHERE ID_Empleado=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idEmpleado);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Empleado> obtenerEmpleadosPorPuesto(String puesto) {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM Empleados WHERE Puesto = ? ORDER BY Nombre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, puesto);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("ID_Empleado"));
                empleado.setNombre(rs.getString("Nombre"));
                empleado.setPuesto(rs.getString("Puesto"));
                empleado.setTelefono(rs.getString("Telefono"));
                empleado.setEmail(rs.getString("Email"));
                empleado.setDireccion(rs.getString("Direccion"));
                empleado.setFechaContratacion(rs.getDate("Fecha_Contratacion").toLocalDate());
                empleado.setSalarioBase(rs.getDouble("Salario_Base"));
                empleado.setTurnoAsignado(rs.getString("Turno_asignado"));
                
                empleados.add(empleado);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener empleados por puesto: " + e.getMessage());
            e.printStackTrace();
        }
        
        return empleados;
    }
    
    // MÉTODO IMPLEMENTADO - obtenerEmpleadoPorId
    public Empleado obtenerEmpleadoPorId(int idEmpleado) {
        String sql = "SELECT * FROM Empleados WHERE ID_Empleado=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idEmpleado);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("ID_Empleado"));
                empleado.setNombre(rs.getString("Nombre"));
                empleado.setPuesto(rs.getString("Puesto"));
                empleado.setTelefono(rs.getString("Telefono"));
                empleado.setEmail(rs.getString("Email"));
                empleado.setDireccion(rs.getString("Direccion"));
                empleado.setFechaContratacion(rs.getDate("Fecha_Contratacion").toLocalDate());
                empleado.setSalarioBase(rs.getDouble("Salario_Base"));
                empleado.setTurnoAsignado(rs.getString("Turno_asignado"));
                return empleado;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener empleado por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}