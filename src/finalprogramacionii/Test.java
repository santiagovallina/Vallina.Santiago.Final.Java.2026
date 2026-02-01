package finalprogramacionii;

import AppConfig.AppConfig;
import Model.Auto;
import Model.Camion;
import Model.Color;
import Model.Moto;
import Model.Vehiculo;
import java.util.Comparator;
import java.util.List;
import service.GestorVehiculos;


public class Test {

    public static void main(String[] args) {
        
        GestorVehiculos<Vehiculo> gestor = new GestorVehiculos<>();

        Vehiculo v = new Auto("ZAE321", 2020, "Chevrolet", Color.AZUL, 6000000, 5, 1.6);
        Vehiculo v1 = new Auto("ABC123", 2020, "Toyota", Color.ROJO, 5000000, 5, 1.6);
        Vehiculo v2 = new Auto("DEF456", 2019, "Ford", Color.AZUL, 4500000, 3, 2.0);
        Vehiculo v3 = new Auto("GHI789", 2021, "Fiat", Color.BLANCO, 3800000, 5, 1.4);
        Vehiculo v4 = new Auto("GHI789", 2021, "Fiat", Color.VERDE, 3800000, 5, 1.4);
        
        Vehiculo m1 = new Moto("RTE123", 2021, "Honda", Color.VERDE, 1800000, 500, 200);
        
        gestor.agregar(v);
        gestor.agregar(v1);
        gestor.agregar(v2);
        gestor.agregar(v3);
        gestor.agregar(m1);

        System.out.println("=== Lista original ===");
        gestor.leer();
        
        gestor.actualizar("GHI789", v4);
        gestor.leer();
        
        //Ordenar por patente (usando comparable)
        gestor.ordenar();
        gestor.leer();
        
        //ordenar por precio de mayor a menor (usando comparator)
        Comparator<Vehiculo> ordenarPorPrecio = (ve1, ve2) -> Double.compare(ve2.getPrecio(), ve1.getPrecio());
        gestor.ordenar(ordenarPorPrecio);
        gestor.leer();
        
        //ordenar por precio de menor a mayor(usando comparator)
        Comparator<Vehiculo> ordenarPorPrecio2 = (ve1, ve2) -> Double.compare(ve1.getPrecio(), ve2.getPrecio());
        gestor.ordenar(ordenarPorPrecio);
        gestor.leer();
        
        
        // aumentar precio usando consumer
        gestor.aplicarCambios(ve -> {
            if (ve instanceof Vehiculo) { 
                double nuevoPrecio = ve.getPrecio() * 1.1;
                ve.setPrecio(nuevoPrecio);
            }
        });
        System.out.println("\n=== Lista después de aumentar precios 10% ===");
        gestor.leer();
        
        
        // Guardar y cargar en archivo binario
        gestor.guardarEnBinario(AppConfig.PATH_SER);
        GestorVehiculos<Vehiculo> gestorBinario = new GestorVehiculos<>();
        gestorBinario.cargarDesdeBinario(AppConfig.PATH_SER);
        System.out.println("\nEventos cargados desde archivo binario:");
        gestorBinario.leer();
        
        

        // Guardar y cargar en archivo CSV
        gestor.guardarEnCSV(AppConfig.PATH_CSV);
        GestorVehiculos<Vehiculo> gestorCSV = new GestorVehiculos<>();
        List<Vehiculo> listaCargada = gestorCSV.cargarDesdeCSV(AppConfig.PATH_CSV);
        for (Vehiculo vh : listaCargada) {
            gestorCSV.agregar(vh);
        }
        System.out.println("\nEventos cargados desde archivo CSV:");
        gestorCSV.leer();

    }
    
}
