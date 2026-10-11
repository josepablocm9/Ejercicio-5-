package modelo;

public class ModuloEnergia extends Modulo {
    private double energiaGeneradaCiclo;
    private int ciclosAcumulados;

    public ModuloEnergia(String id, String nombre, double estadoSalud, boolean activo, double costoConstruccion,
                         double energiaGeneradaCiclo) {
        super(id, nombre, estadoSalud, activo, costoConstruccion);
        this.energiaGeneradaCiclo = energiaGeneradaCiclo;
        this.ciclosAcumulados = 0;
    }

    @Override
    public void procesarCiclo(Mision mision) {
        if (!activo) {
            System.out.println("-> Módulo de Energía [" + nombre + "] está inactivo. No genera energía.");
            return;
        }

        mision.setEnergiaDisponible(mision.getEnergiaDisponible() + energiaGeneradaCiclo);
        ciclosAcumulados++;
        System.out.println(String.format("-> Módulo de Energía [%s] generó %.1f unidades de energía.", 
                nombre, energiaGeneradaCiclo));
    }

    public double getEnergiaGeneradaCiclo() { return energiaGeneradaCiclo; }
    public int getCiclosAcumulados() { return ciclosAcumulados; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Tipo: Energía | Generación/Ciclo: %.1f | Ciclos Op.: %d", 
                energiaGeneradaCiclo, ciclosAcumulados);
    }
}