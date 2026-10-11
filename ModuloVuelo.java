package modelo;

public class ModuloVuelo extends Modulo {
    private String tipoInstrumento; // "cámara" o "sensor"
    private double datosPorCiclo; // MB
    private double consumoEnergiaCiclo;
    private int ciclosAcumulados;

    public ModuloVuelo(String id, String nombre, double estadoSalud, boolean activo, double costoConstruccion,
                       String tipoInstrumento, double datosPorCiclo, double consumoEnergiaCiclo) {
        super(id, nombre, estadoSalud, activo, costoConstruccion);
        this.tipoInstrumento = tipoInstrumento;
        this.datosPorCiclo = datosPorCiclo;
        this.consumoEnergiaCiclo = consumoEnergiaCiclo;
        this.ciclosAcumulados = 0;
    }

    @Override
    public void procesarCiclo(Mision mision) {
        if (!activo) {
            System.out.println("-> Módulo de Vuelo [" + nombre + "] está inactivo. No opera.");
            return;
        }
        if (mision.getEnergiaDisponible() < consumoEnergiaCiclo) {
            System.out.println("-> Módulo de Vuelo [" + nombre + "] no tiene energía suficiente (Requiere: " + 
                    consumoEnergiaCiclo + ", Disponibles: " + mision.getEnergiaDisponible() + "). No opera.");
            return;
        }

        // Operación exitosa
        mision.setEnergiaDisponible(mision.getEnergiaDisponible() - consumoEnergiaCiclo);
        mision.setDatosEnOrbita(mision.getDatosEnOrbita() + datosPorCiclo);
        ciclosAcumulados++;
        System.out.println(String.format("-> Módulo de Vuelo [%s] consumió %.1f unidades de energía y recolectó %.1f MB.", 
                nombre, consumoEnergiaCiclo, datosPorCiclo));
    }

    public String getTipoInstrumento() { return tipoInstrumento; }
    public double getDatosPorCiclo() { return datosPorCiclo; }
    public double getConsumoEnergiaCiclo() { return consumoEnergiaCiclo; }
    public int getCiclosAcumulados() { return ciclosAcumulados; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Tipo: Vuelo (%s) | Datos/Ciclo: %.1f MB | Consumo: %.1f | Ciclos Op.: %d", 
                tipoInstrumento, datosPorCiclo, consumoEnergiaCiclo, ciclosAcumulados);
    }
}