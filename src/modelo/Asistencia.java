package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Asistencia {
    private int idAsistencia;
    private int idEmpleado;
    private LocalDate fecha;
    private LocalTime horaEntradaReal;
    private LocalTime horaSalidaReal;
    private int retrasoMinutos;
    private String falta;
    
    public Asistencia() {}
    
    public Asistencia(int idEmpleado, LocalDate fecha) {
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
    }
    
    // Getters y Setters
    public int getIdAsistencia() { return idAsistencia; }
    public void setIdAsistencia(int idAsistencia) { this.idAsistencia = idAsistencia; }
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraEntradaReal() { return horaEntradaReal; }
    public void setHoraEntradaReal(LocalTime horaEntradaReal) { this.horaEntradaReal = horaEntradaReal; }
    public LocalTime getHoraSalidaReal() { return horaSalidaReal; }
    public void setHoraSalidaReal(LocalTime horaSalidaReal) { this.horaSalidaReal = horaSalidaReal; }
    public int getRetrasoMinutos() { return retrasoMinutos; }
    public void setRetrasoMinutos(int retrasoMinutos) { this.retrasoMinutos = retrasoMinutos; }
    public String getFalta() { return falta; }
    public void setFalta(String falta) { this.falta = falta; }
}