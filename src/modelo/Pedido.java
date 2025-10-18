// Pedido.java
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
    
    // Constructores, getters y setters
    public Pedido() {}
    
    public Pedido(int idCliente, String tipoHamburguesa, double precioTotal, String estado) {
        this.idCliente = idCliente;
        this.tipoHamburguesa = tipoHamburguesa;
        this.precioTotal = precioTotal;
        this.estado = estado;
    }

    public int getIdCliente() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdCliente'");
    }

    public String getTipoHamburguesa() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTipoHamburguesa'");
    }

    public double getPrecioTotal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrecioTotal'");
    }

    public String getObservaciones() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getObservaciones'");
    }

    public void setIdPedido(int int1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIdPedido'");
    }

    public void setIdCliente(int int1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIdCliente'");
    }

    public void setFechaHora(LocalDateTime localDateTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFechaHora'");
    }

    public void setTipoHamburguesa(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTipoHamburguesa'");
    }

    public void setPrecioTotal(double double1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPrecioTotal'");
    }

    public void setEstado(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEstado'");
    }

    public void setObservaciones(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setObservaciones'");
    }

    public Object getIdPedido() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdPedido'");
    }

    public Object getEstado() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstado'");
    }

    public Object getFechaHora() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFechaHora'");
    }
    
    // Getters y setters...
}