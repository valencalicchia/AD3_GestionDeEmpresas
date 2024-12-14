package Models;

public class Empresa {
    private int codEmpre;
    private String nombre;
    private String direccion;
    private String tlf;
    private double presupuesto;
    private int codSector;
    private int sede;

    public Empresa () {}
    
    public Empresa(int codEmpre, String nombre, String direccion, String tlf, double presupuesto, int codSector, int sede) {
        this.codEmpre = codEmpre;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tlf = tlf;
        this.presupuesto = presupuesto;
        this.codSector = codSector;
        this.sede = sede;
    }

    // Getters y Setters
    public int getCodEmpre() {
        return codEmpre;
    }

    public void setCodEmpre(int codEmpre) {
        this.codEmpre = codEmpre;
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

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public int getCodSector() {
        return codSector;
    }

    public void setCodSector(int codSector) {
        this.codSector = codSector;
    }

    public int getSede() {
        return sede;
    }

    public void setSede(int sede) {
        this.sede = sede;
    }

    @Override
    public String toString() {
        return "Empresa [codEmpre=" + codEmpre + ", nombre=" + nombre + ", direccion=" + direccion +
                ", tlf=" + tlf + ", presupuesto=" + presupuesto + ", codSector=" + codSector + ", sede=" + sede + "]";
    }
}
