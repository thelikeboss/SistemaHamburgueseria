package modelo;

import java.time.LocalDateTime;

public class Pedido {
    private int idPedido;
    private int idCliente;
    private LocalDateTime fechaHora;
    private String tipoHamburguesa;
    private double precioTotal;
    private String estado;
    private String observaciones;
    
    public Pedido() {}
    
    public Pedido(int idCliente, String tipoHamburguesa, double precioTotal, String estado) {
        this.idCliente = idCliente;
        this.tipoHamburguesa = tipoHamburguesa;
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.fechaHora = LocalDateTime.now();
    }
    
    // Getters
    public int getIdPedido() { 
        return idPedido; 
    }
    
    public int getIdCliente() { 
        return idCliente; 
    }
    
    public LocalDateTime getFechaHora() { 
        return fechaHora; 
    }
    
    public String getTipoHamburguesa() { 
        return tipoHamburguesa; 
    }
    
    public double getPrecioTotal() { 
        return precioTotal; 
    }
    
    public String getEstado() { 
        return estado; 
    }
    
    public String getObservaciones() { 
        return observaciones; 
    }
    
    // Setters
    public void setIdPedido(int idPedido) { 
        this.idPedido = idPedido; 
    }
    
    public void setIdCliente(int idCliente) { 
        this.idCliente = idCliente; 
    }
    
    public void setFechaHora(LocalDateTime fechaHora) { 
        this.fechaHora = fechaHora; 
    }
    
    public void setTipoHamburguesa(String tipoHamburguesa) { 
        this.tipoHamburguesa = tipoHamburguesa; 
    }
    
    public void setPrecioTotal(double precioTotal) { 
        this.precioTotal = precioTotal; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado; 
    }
    
    public void setObservaciones(String observaciones) { 
        this.observaciones = observaciones; 
    }
    
    @Override
    public String toString() {
        return String.format("Pedido #%d - %s - $%.2f - %s", 
            idPedido, tipoHamburguesa, precioTotal, estado);
    }
}