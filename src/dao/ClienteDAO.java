package dao;

import modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public boolean agregarCliente(Cliente cliente) {
        // Usar parámetros con nombres más claros y formato Azure SQL
        String sql = "INSERT INTO Clientes (Nombre, Telefono, Email, Direccion) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getDireccion());
            
            int affectedRows = pstmt.executeUpdate();
            
            // Obtener el ID generado
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setIdCliente(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al agregar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    public List<Cliente> obtenerTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes ORDER BY Fecha_Registro DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("ID_Cliente"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setTelefono(rs.getString("Telefono"));
                cliente.setEmail(rs.getString("Email"));
                cliente.setDireccion(rs.getString("Direccion"));
                
                // Manejar Fecha_Registro que puede ser NULL
                Timestamp fechaRegistro = rs.getTimestamp("Fecha_Registro");
                if (fechaRegistro != null) {
                    cliente.setFechaRegistro(fechaRegistro.toLocalDateTime());
                }
                
                clientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener clientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }
    
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE Clientes SET Nombre=?, Telefono=?, Email=?, Direccion=? WHERE ID_Cliente=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setInt(5, cliente.getIdCliente());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarCliente(int idCliente) {
        String sql = "DELETE FROM Clientes WHERE ID_Cliente=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCliente);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public Cliente obtenerClientePorId(int idCliente) {
        String sql = "SELECT * FROM Clientes WHERE ID_Cliente=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("ID_Cliente"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setTelefono(rs.getString("Telefono"));
                cliente.setEmail(rs.getString("Email"));
                cliente.setDireccion(rs.getString("Direccion"));
                
                Timestamp fechaRegistro = rs.getTimestamp("Fecha_Registro");
                if (fechaRegistro != null) {
                    cliente.setFechaRegistro(fechaRegistro.toLocalDateTime());
                }
                
                return cliente;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}