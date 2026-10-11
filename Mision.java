package modelo;

import java.util.ArrayList;
import java.util.List;

public class Mision {
    private double energiaDisponible;
    private double datosEnOrbita;
    private double totalDescargadoMision;
    private List<Modulo> modulos;

    public Mision(double energiaInicial, double datosOrbitaInicial, double totalDescargadoInicial) {
        this.energiaDisponible = energiaInicial;
        this.datosEnOrbita = datosOrbitaInicial;
        this.totalDescargadoMision = totalDescargadoInicial;
        this.modulos = new ArrayList<>();
    }

    public void agregarModulo(Modulo modulo) {
        modulos.add(modulo);
    }

    public double getEnergiaDisponible() { return energiaDisponible; }
    public void setEnergiaDisponible(double energiaDisponible) { this.energiaDisponible = energiaDisponible; }

    public double getDatosEnOrbita() { return datosEnOrbita; }
    public void setDatosEnOrbita(double datosEnOrbita) { this.datosEnOrbita = datosEnOrbita; }

    public double getTotalDescargadoMision() { return totalDescargadoMision; }
    public void setTotalDescargadoMision(double totalDescargadoMision) { this.totalDescargadoMision = totalDescargadoMision; }

    public List<Modulo> getModulos() { return modulos; }
}