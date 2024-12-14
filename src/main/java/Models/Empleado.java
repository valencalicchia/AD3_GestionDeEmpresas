package Models;

import java.util.Date;

public class Empleado {
    private int codEmple;
    private String nombre;
    private String direccion;
    private String poblacion;
    private Date fechaAlta;
    private int codEncargado;
    private int codDepart;
    private int codOficio;

    public Empleado () { }

    public Empleado(int codEmple, String nombre, String direccion, String poblacion, Date fechaAlta, int codEncargado, int codDepart, int codOficio) {
        this.codEmple = codEmple;
        this.nombre = nombre;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.fechaAlta = fechaAlta;
        this.codEncargado = codEncargado;
        this.codDepart = codDepart;
        this.codOficio = codOficio;
    }

    // Getters y Setters
    public int getCodEmple() {
        return codEmple;
    }

    public void setCodEmple(int codEmple) {
        this.codEmple = codEmple;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public int getCodEncargado() {
        return codEncargado;
    }

    public void setCodEncargado(int codEncargado) {
        this.codEncargado = codEncargado;
    }

    public int getCodDepart() {
        return codDepart;
    }

    public void setCodDepart(int codDepart) {
        this.codDepart = codDepart;
    }

    public int getCodOficio() {
        return codOficio;
    }

    public void setCodOficio(int codOficio) {
        this.codOficio = codOficio;
    }

    @Override
    public String toString() {
        return "Empleado [codEmple=" + codEmple + ", nombre=" + nombre + ", direccion=" + direccion +
                ", poblacion=" + poblacion + ", fechaAlta=" + fechaAlta + ", codEncargado=" + codEncargado +
                ", codDepart=" + codDepart + ", codOficio=" + codOficio + "]";
    }
}
