package modelo;

import java.time.LocalDate;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String puesto;
    private String telefono;
    private String email;
    private String direccion;
    private LocalDate fechaContratacion;
    private double salarioBase;
    private String turnoAsignado;
    
    public Empleado() {}
    
    public Empleado(String nombre, String puesto, String telefono, String email, String direccion, 
                   LocalDate fechaContratacion, double salarioBase, String turnoAsignado) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fechaContratacion = fechaContratacion;
        this.salarioBase = salarioBase;
        this.turnoAsignado = turnoAsignado;
    }
    
    // Getters y Setters
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }
    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
    public String getTurnoAsignado() { return turnoAsignado; }
    public void setTurnoAsignado(String turnoAsignado) { this.turnoAsignado = turnoAsignado; }
    
    @Override
    public String toString() {
        return nombre + " - " + puesto;
    }
}