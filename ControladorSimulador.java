package controlador;

import modelo.*;
import vista.VistaConsola;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ControladorSimulador {
    private Mision mision;
    private VistaConsola vista;

    public ControladorSimulador(Mision mision, VistaConsola vista) {
        this.mision = mision;
        this.vista = vista;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        // Carga inicial emulando misión en progreso con 10 módulos de los 3 tipos
        mision.setEnergiaDisponible(25.0);
        mision.setDatosEnOrbita(40.0);
        mision.setTotalDescargadoMision(100.0);

        // Módulos de Energía
        mision.agregarModulo(new ModuloEnergia("EN-01", "Panel Solar Alpha", 95.0, true, 1200.0, 20.0));
        mision.agregarModulo(new ModuloEnergia("EN-02", "Panel Solar Beta", 80.0, true, 950.0, 15.0));
        mision.agregarModulo(new ModuloEnergia("EN-03", "Generador Auxiliar", 100.0, false, 1500.0, 25.0));

        // Módulos de Vuelo
        mision.agregarModulo(new ModuloVuelo("VL-01", "Cámara Óptica Principal", 90.0, true, 3500.0, "cámara", 15.0, 8.0));
        mision.agregarModulo(new ModuloVuelo("VL-02", "Sensor Espectral UV", 85.0, true, 2800.0, "sensor", 10.0, 5.0));
        mision.agregarModulo(new ModuloVuelo("VL-03", "Sensor Térmico Infrarrojo", 40.0, false, 2100.0, "sensor", 12.0, 6.0));
        mision.agregarModulo(new ModuloVuelo("VL-04", "Cámara Alta Resolución", 100.0, true, 4200.0, "cámara", 20.0, 12.0));

        // Módulos de Tierra
        mision.agregarModulo(new ModuloTierra("TR-01", "Antena Campus Central", 95.0, true, 5000.0, 25.0, 10.0));
        mision.agregarModulo(new ModuloTierra("TR-02", "Antena Estación Sur", 88.0, true, 4600.0, 18.0, 7.0));
        mision.agregarModulo(new ModuloTierra("TR-03", "Antena Respaldo Norte", 92.0, false, 4100.0, 15.0, 6.0));
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1:
                    listarModulos();
                    break;
                case 2:
                    buscarModulo();
                    break;
                case 3:
                    mostrarCatalogoCosto();
                    break;
                case 4:
                    ejecutarCiclo();
                    break;
                case 5:
                    mostrarResumenComunicaciones();
                    break;
                case 6:
                    vista.mostrarMensaje("Saliendo del simulador QTZ2. ¡Hasta luego!");
                    break;
                default:
                    vista.mostrarMensaje("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 6);
    }

    private void listarModulos() {
        vista.mostrarModulos(mision.getModulos());
    }

    private void buscarModulo() {
        String criterio = vista.solicitarIdOrNombre().toLowerCase();
        Modulo encontrado = null;
        for (Modulo m : mision.getModulos()) {
            if (m.getId().toLowerCase().equals(criterio) || m.getNombre().toLowerCase().contains(criterio)) {
                encontrado = m;
                break;
            }
        }
        vista.mostrarModuloDetalle(encontrado);
    }

    private void mostrarCatalogoCosto() {
        List<Modulo> copia = new ArrayList<>(mision.getModulos());
        copia.sort(Comparator.comparingDouble(Modulo::getCosConstruccionOrCosto)); // o getCostoConstruccion
        copia.sort(Comparator.comparingDouble(Modulo::getCostoConstruccion));
        
        vista.mostrarMensaje("\n--- CATÁLOGO ORDENADO POR COSTO DE CONSTRUCCIÓN ---");
        for (Modulo m : copia) {
            System.out.println(String.format("Costo: Q%.2f | ID: %s | Nombre: %s", 
                    m.getCostoConstruccion(), m.getId(), m.getNombre()));
        }
    }

    private void ejecutarCiclo() {
        vista.mostrarMensaje("\n--- INICIANDO CICLO DEL SIMULADOR ---");
        System.out.println(String.format("Recursos iniciales -> Energía: %.1f | Órbita: %.1f MB | Descargados: %.1f MB", 
                mision.getEnergiaDisponible(), mision.getDatosEnOrbita(), mision.getTotalDescargadoMision()));
        
        int orden = 1;
        for (Modulo m : mision.getModulos()) {
            System.out.print(orden + ". ");
            m.procesarCiclo(mision);
            orden++;
        }

        vista.mostrarMensaje(String.format("\nFin del ciclo. Recursos resultantes -> Energía: %.1f | Órbita: %.1f MB | Descargados: %.1f MB", 
                mision.getEnergiaDisponible(), mision.getDatosEnOrbita(), mision.getTotalDescargadoMision()));
    }

    private void mostrarResumenComunicaciones() {
        int totalTierra = 0;
        int activosTierra = 0;
        double capDescargaActivos = 0.0;
        double historicoTierra = 0.0;
        double maxDescargado = -1.0;
        List<ModuloTierra> maxModulos = new ArrayList<>();

        for (Modulo m : mision.getModulos()) {
            if (m instanceof ModuloTierra) {
                ModuloTierra mt = (ModuloTierra) m;
                totalTierra++;
                historicoTierra += mt.getTotalDescargadoDouble();
                if (mt.isActivo()) {
                    activosTierra++;
                    capDescargaActivos += mt.getCapacidadDescarga();
                }

                if (mt.getTotalDescargadoDouble() > maxDescargado) {
                    maxDescargado = mt.getTotalDescargadoDouble();
                    maxModulos.clear();
                    maxModulos.add(mt);
                } else if (mt.getTotalDescargadoDouble() == maxDescargado && maxDescargado >= 0) {
                    maxModulos.add(mt);
                }
            }
        }

        vista.mostrarResumenComunicaciones(mision, totalTierra, activosTierra, capDescargaActivos, historicoTierra, maxModulos);
    }
}