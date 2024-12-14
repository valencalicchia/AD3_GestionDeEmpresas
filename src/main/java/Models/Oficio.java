package Models;

public class Oficio {
    private int codOficio;
    private String nombre;
    private double salarioMes;
    private double precioTrienio;

    public Oficio () {}
    
    public Oficio(int codOficio, String nombre, double salarioMes, double precioTrienio) {
        this.codOficio = codOficio;
        this.nombre = nombre;
        this.salarioMes = salarioMes;
        this.precioTrienio = precioTrienio;
    }

    // Getters y Setters
    public int getCodOficio() {
        return codOficio;
    }

    public void setCodOficio(int codOficio) {
        this.codOficio = codOficio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalarioMes() {
        return salarioMes;
    }

    public void setSalarioMes(double salarioMes) {
        this.salarioMes = salarioMes;
    }

    public double getPrecioTrienio() {
        return precioTrienio;
    }

    public void setPrecioTrienio(double precioTrienio) {
        this.precioTrienio = precioTrienio;
    }

    @Override
    public String toString() {
        return "Oficio [codOficio=" + codOficio + ", nombre=" + nombre + ", salarioMes=" + salarioMes +
                ", precioTrienio=" + precioTrienio + "]";
    }
}
