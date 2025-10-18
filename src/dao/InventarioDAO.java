package dao;

import modelo.Ingrediente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {
    
    public boolean agregarIngrediente(Ingrediente ingrediente) {
        String sql = "INSERT INTO Inventario (Nombre_Ingrediente, Cantidad_Disponible, Unidad_Medida, Proveedor, Stock_Minimo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ingrediente.getNombreIngrediente());
            pstmt.setDouble(2, ingrediente.getCantidadDisponible());
            pstmt.setString(3, ingrediente.getUnidadMedida());
            pstmt.setString(4, ingrediente.getProveedor());
            pstmt.setDouble(5, ingrediente.getStockMinimo());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Ingrediente> obtenerTodosIngredientes() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        String sql = "SELECT * FROM Inventario ORDER BY Nombre_Ingrediente";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setIdIngrediente(rs.getInt("ID_Ingrediente"));
                ingrediente.setNombreIngrediente(rs.getString("Nombre_Ingrediente"));
                ingrediente.setCantidadDisponible(rs.getDouble("Cantidad_Disponible"));
                ingrediente.setUnidadMedida(rs.getString("Unidad_Medida"));
                ingrediente.setProveedor(rs.getString("Proveedor"));
                ingrediente.setStockMinimo(rs.getDouble("Stock_Minimo"));
                
                ingredientes.add(ingrediente);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ingredientes;
    }
    
    public boolean actualizarIngrediente(Ingrediente ingrediente) {
        String sql = "UPDATE Inventario SET Nombre_Ingrediente=?, Cantidad_Disponible=?, Unidad_Medida=?, Proveedor=?, Stock_Minimo=? WHERE ID_Ingrediente=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ingrediente.getNombreIngrediente());
            pstmt.setDouble(2, ingrediente.getCantidadDisponible());
            pstmt.setString(3, ingrediente.getUnidadMedida());
            pstmt.setString(4, ingrediente.getProveedor());
            pstmt.setDouble(5, ingrediente.getStockMinimo());
            pstmt.setInt(6, ingrediente.getIdIngrediente());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean actualizarCantidad(int idIngrediente, double nuevaCantidad) {
        String sql = "UPDATE Inventario SET Cantidad_Disponible = ? WHERE ID_Ingrediente = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, nuevaCantidad);
            pstmt.setInt(2, idIngrediente);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Ingrediente> obtenerIngredientesBajoStock() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        String sql = "SELECT * FROM Inventario WHERE Cantidad_Disponible <= Stock_Minimo";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setIdIngrediente(rs.getInt("ID_Ingrediente"));
                ingrediente.setNombreIngrediente(rs.getString("Nombre_Ingrediente"));
                ingrediente.setCantidadDisponible(rs.getDouble("Cantidad_Disponible"));
                ingrediente.setUnidadMedida(rs.getString("Unidad_Medida"));
                ingrediente.setProveedor(rs.getString("Proveedor"));
                ingrediente.setStockMinimo(rs.getDouble("Stock_Minimo"));
                
                ingredientes.add(ingrediente);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ingredientes;
    }
}