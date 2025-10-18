package modelo;

public class Ingrediente {
    private int idIngrediente;
    private String nombreIngrediente;
    private double cantidadDisponible;
    private String unidadMedida;
    private String proveedor;
    private double stockMinimo;
    
    public Ingrediente() {}
    
    public Ingrediente(String nombreIngrediente, double cantidadDisponible, String unidadMedida, String proveedor, double stockMinimo) {
        this.nombreIngrediente = nombreIngrediente;
        this.cantidadDisponible = cantidadDisponible;
        this.unidadMedida = unidadMedida;
        this.proveedor = proveedor;
        this.stockMinimo = stockMinimo;
    }
    
    // Getters y Setters
    public int getIdIngrediente() { return idIngrediente; }
    public void setIdIngrediente(int idIngrediente) { this.idIngrediente = idIngrediente; }
    public String getNombreIngrediente() { return nombreIngrediente; }
    public void setNombreIngrediente(String nombreIngrediente) { this.nombreIngrediente = nombreIngrediente; }
    public double getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(double cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    public double getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(double stockMinimo) { this.stockMinimo = stockMinimo; }
    
    public boolean necesitaReposicion() {
        return cantidadDisponible <= stockMinimo;
    }
}