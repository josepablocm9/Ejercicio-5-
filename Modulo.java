package modelo;

public abstract class Modulo {
    protected String id;
    protected String nombre;
    protected double estadoSalud; // Porcentaje de 0 a 100
    protected boolean activo;
    protected double costoConstruccion;

    public Modulo(String id, String nombre, double estadoSalud, boolean activo, double costoConstruccion) {
        this.id = id;
        this.nombre = nombre;
        this.estadoSalud = estadoSalud;
        this.activo = activo;
        this.costoConstruccion = costoConstruccion;
    }

    public abstract void procesarCiclo(Mision mision);

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getEstadoSalud() { return estadoSalud; }
    public boolean isActivo() { return activo; }
    public double getCostoConstruccion() { return costoConstruccion; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return String.format("ID: %s | Nombre: %s | Estado: %s | Salud: %.1f%% | Costo: Q%.2f", 
                id, nombre, (activo ? "Activo" : "Inactivo"), estadoSalud, costoConstruccion);
    }
}