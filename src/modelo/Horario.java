package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Horario {
    private int idHorario;
    private int idEmpleado;
    private LocalDate fecha;
    private String turno;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private double horasExtra;
    private String estadoAsistencia;
    
    public Horario() {}
    
    public Horario(int idEmpleado, LocalDate fecha, String turno, LocalTime horaInicio, LocalTime horaFin) {
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.turno = turno;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estadoAsistencia = "Programado";
    }
    
    // Getters y Setters
    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public double getHorasExtra() { return horasExtra; }
    public void setHorasExtra(double horasExtra) { this.horasExtra = horasExtra; }
    public String getEstadoAsistencia() { return estadoAsistencia; }
    public void setEstadoAsistencia(String estadoAsistencia) { this.estadoAsistencia = estadoAsistencia; }
}
