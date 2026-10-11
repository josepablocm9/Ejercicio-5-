package vista;

import modelo.Modulo;
import modelo.ModuloTierra;
import modelo.Mision;

import java.util.List;
import java.util.Scanner;

public class VistaConsola {
    private Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    public int mostrarMenu() {
        System.out.println("\n========================================");
        System.out.println("   SIMULADOR TOWER DEFENSE - QTZ2 (UVG)");
        System.out.println("========================================");
        System.out.println("a) Listar todos los módulos de la misión");
        System.out.println("b) Buscar módulo por ID o Nombre");
        System.out.println("c) Consultar catálogo ordenado por Costo");
        System.out.println("d) Avanzar un ciclo del simulador");
        System.out.println("e) Consultar resumen de comunicaciones");
        System.out.println("f) Salir");
        System.out.print("Seleccione una opción: ");
        String op = scanner.nextLine().trim().toLowerCase();
        
        switch (op) {
            case "a": return 1;
            case "b": return 2;
            case "c": return 3;
            case "d": return 4;
            case "e": return 5;
            case "f": return 6;
            default: return -1;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarModulos(List<Modulo> modulos) {
        System.out.println("\n--- LISTADO DE MÓDULOS DE LA MISIÓN ---");
        if (modulos.isEmpty()) {
            System.out.println("No hay módulos registrados.");
            return;
        }
        for (Modulo m : modulos) {
            System.out.println(m);
        }
    }

    public void mostrarModuloDetalle(Modulo modulo) {
        System.out.println("\n--- DETALLE DEL MÓDULO ---");
        if (modulo == null) {
            System.out.println("Módulo no encontrado.");
        } else {
            System.out.println(modulo);
        }
    }

    public void mostrarResumenComunicaciones(Mision mision, int totalTierra, int activosTierra, 
                                             double capDescargaActivos, double historicoTierra, 
                                             List<ModuloTierra> maxModulos) {
        System.out.println("\n========================================");
        System.out.println("     RESUMEN DE COMUNICACIONES QTZ2");
        System.out.println("========================================");
        System.out.println("a. Cantidad total de módulos de tierra: " + totalTierra);
        System.out.println("   Módulos de tierra activos: " + activosTierra);
        System.out.println("b. Capacidad total de descarga (activos): " + capDescargaActivos + " MB/ciclo");
        System.out.println("c. Total histórico descargado (Tierra): " + historicoTierra + " MB");
        System.out.println("   Total histórico descargado (Misión): " + mision.getTotalDescargadoMision() + " MB");
        System.out.println("   Energía actual disponible: " + mision.getEnergiaDisponible() + " unidades");
        System.out.println("   Datos pendientes en órbita: " + mision.getDatosEnOrbita() + " MB");
        System.out.println("d. Módulo(s) de tierra con mayor histórico descargado:");
        if (maxModulos.isEmpty()) {
            System.out.println("   Ninguno.");
        } else {
            for (ModuloTierra mt : maxModulos) {
                System.out.println(String.format("   - ID: %s | Nombre: %s | Total Descargado: %.1f MB", 
                        mt.getId(), mt.getNombre(), mt.getTotalDescargadoDouble()));
            }
        }
        System.out.println("========================================");
    }

    public String solicitarIdOrNombre() {
        System.out.print("Ingrese el ID o Nombre del módulo a buscar: ");
        return scanner.nextLine().trim();
    }
}