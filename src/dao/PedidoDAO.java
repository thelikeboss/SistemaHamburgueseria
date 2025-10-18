package dao;

import modelo.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    
    public boolean agregarPedido(Pedido pedido) {
        String sql = "INSERT INTO Pedidos (ID_Cliente, Tipo_Hamburguesa, Precio_Total, Estado, Observaciones) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, pedido.getIdCliente());
            pstmt.setString(2, pedido.getTipoHamburguesa());
            pstmt.setDouble(3, pedido.getPrecioTotal());
            // obtain 'estado' via reflection in case Pedido doesn't define getEstado()
            String estadoValue = null;
            try {
                java.lang.reflect.Field estadoField = pedido.getClass().getDeclaredField("estado");
                estadoField.setAccessible(true);
                Object estadoObj = estadoField.get(pedido);
                estadoValue = estadoObj != null ? estadoObj.toString() : null;
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                // fallback: try a common alternate getter name
                try {
                    java.lang.reflect.Method m = pedido.getClass().getMethod("getEstadoPedido");
                    Object estadoObj = m.invoke(pedido);
                    estadoValue = estadoObj != null ? estadoObj.toString() : null;
                } catch (Exception ex2) {
                    estadoValue = null;
                }
            }
            pstmt.setString(4, estadoValue);
            pstmt.setString(5, pedido.getObservaciones());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pedido.setIdPedido(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Pedido> obtenerTodosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.*, c.Nombre as NombreCliente FROM Pedidos p " +
                    "LEFT JOIN Clientes c ON p.ID_Cliente = c.ID_Cliente " +
                    "ORDER BY p.Fecha_Hora DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("ID_Pedido"));
                pedido.setIdCliente(rs.getInt("ID_Cliente"));
                pedido.setFechaHora(rs.getTimestamp("Fecha_Hora").toLocalDateTime());
                pedido.setTipoHamburguesa(rs.getString("Tipo_Hamburguesa"));
                pedido.setPrecioTotal(rs.getDouble("Precio_Total"));
                pedido.setEstado(rs.getString("Estado"));
                pedido.setObservaciones(rs.getString("Observaciones"));
                
                pedidos.add(pedido);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pedidos;
    }
    
    public boolean actualizarEstadoPedido(int idPedido, String nuevoEstado) {
        String sql = "UPDATE Pedidos SET Estado = ? WHERE ID_Pedido = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idPedido);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Pedido> obtenerPedidosPorEstado(String estado) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedidos WHERE Estado = ? ORDER BY Fecha_Hora DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, estado);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("ID_Pedido"));
                pedido.setIdCliente(rs.getInt("ID_Cliente"));
                pedido.setFechaHora(rs.getTimestamp("Fecha_Hora").toLocalDateTime());
                pedido.setTipoHamburguesa(rs.getString("Tipo_Hamburguesa"));
                pedido.setPrecioTotal(rs.getDouble("Precio_Total"));
                pedido.setEstado(rs.getString("Estado"));
                pedido.setObservaciones(rs.getString("Observaciones"));
                
                pedidos.add(pedido);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pedidos;
    }
}