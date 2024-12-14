package Models;

public class Departamento {
    private int codDepart;
    private String nombre;
    private String direccion;
    private String localidad;
    private int codJefeDepartamento;
    private int codEmpre;

    public Departamento() {}
    
    public Departamento(int codDepart, String nombre, String direccion, String localidad, int codJefeDepartamento, int codEmpre) {
        this.codDepart = codDepart;
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.codJefeDepartamento = codJefeDepartamento;
        this.codEmpre = codEmpre;
    }

    // Getters y Setters
    public int getCodDepart() {
        return codDepart;
    }

    public void setCodDepart(int codDepart) {
        this.codDepart = codDepart;
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

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public int getCodJefeDepartamento() {
        return codJefeDepartamento;
    }

    public void setCodJefeDepartamento(int codJefeDepartamento) {
        this.codJefeDepartamento = codJefeDepartamento;
    }

    public int getCodEmpre() {
        return codEmpre;
    }

    public void setCodEmpre(int codEmpre) {
        this.codEmpre = codEmpre;
    }

    @Override
    public String toString() {
        return "Departamento [codDepart=" + codDepart + ", nombre=" + nombre + ", direccion=" + direccion +
                ", localidad=" + localidad + ", codJefeDepartamento=" + codJefeDepartamento + ", codEmpre=" + codEmpre + "]";
    }
}
