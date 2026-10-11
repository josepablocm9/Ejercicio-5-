import controlador.ControladorSimulador;
import modelo.Mision;
import vista.VistaConsola;

public class Main {
    public static void main(String[] args) {
        // Inicialización de componentes arquitectónicos (MVC)
        Mision mision = new Mision(0.0, 0.0, 0.0);
        VistaConsola vista = new VistaConsola();
        ControladorSimulador controlador = new ControladorSimulador(mision, vista);

        // Iniciar el simulador
        controlador.iniciar();
    }
}