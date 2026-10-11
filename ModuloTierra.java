package modelo;

public class ModuloTierra extends Modulo {
    private double capacidadDescarga; // MB por ciclo
    private double energiaNecesariaDescarga;
    private double totalDescargado; // Histórico en MB

    public ModuloTierra(String id, String nombre, double estadoSalud, boolean activo, double costoConstruccion,
                        double capacidadDescarga, double energiaNecesariaDescarga) {
        super(id, nombre, estadoSalud, activo, costoConstruccion);
        this.capacidadDescarga = capacidadDescarga;
        this.energiaNecesariaDescarga = energiaNecesariaDescarga;
        this.totalDescargado = 0.0;
    }

    @Override
    public void procesarCiclo(Mision mision) {
        if (!activo) {
            System.out.println("-> Módulo de Tierra [" + nombre + "] está inactivo. No opera.");
            return;
        }
        if (mision.getEnergiaDisponible() < energiaNecesariaDescarga) {
            System.out.println("-> Módulo de Tierra [" + nombre + "] no tiene energía suficiente (Requiere: " + 
                    energiaNecesariaDescarga + ", Disponibles: " + mision.getEnergiaDisponible() + "). No opera.");
            return;
        }
        if (mision.getDatosEnOrbita() <= 0) {
            System.out.println("-> Módulo de Tierra [" + nombre + "] no encontró datos pendientes en órbita para descargar.");
            return;
        }

        // Operación exitosa: descarga la cantidad permitida sin superar lo disponible en órbita
        double datosADescargar = Math.min(capacidadDescarga, mision.getDatosEnOrbita());
        
        mision.setEnergiaDisponible(mision.getEnergiaDisponible() - energiaNecesariaDescarga);
        mision.setDatosEnOrbita(mision.getDatosEnOrbita() - datosADescargar);
        mision.setTotalDescargadoMision(mision.getTotalDescargadoMision() + datosADescargar);
        totalDescargado += datosADescargar;

        System.out.println(String.format("-> Módulo de Tierra [%s] consumió %.1f unidades de energía y descargó %.1f MB.", 
                nombre, energiaNecesariaDescarga, datosADescargar));
    }

    public double getCapacidadDescarga() { return capacidadDescarga; }
    public double getEnergiaNecesariaDescarga() { return energiaNecesariaDescarga; }
    public getTotalDescargado getTotalDescargado() { return totalDescargado; } // o double
    public double getTotalDescargadoDouble() { return totalDescargado; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Tipo: Tierra | Cap. Descarga: %.1f MB | Energía Nec.: %.1f | Total Desc.: %.1f MB", 
                capacidadDescarga, energiaNecesariaDescarga, totalDescargado);
    }
}