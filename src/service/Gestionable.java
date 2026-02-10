package service;

import Model.Vehiculo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;


public interface Gestionable<T>{
    
    void agregar(T item);
    
    void leer();
    
    void actualizar(String patente, T nuevoVehiculo);
    
    void eliminarPorIndice(int id);
    
    void eliminarPorPatente(String patente);
    
    public void ordenar();
    
    public void ordenar(Comparator<? super T> comparator);
    
    public List<T> filtrar(Predicate<? super T> predicate);
    
    void aplicarCambios(Consumer<T> consumer);
    
    void guardarEnBinario(String path);
    
    void cargarDesdeBinario(String path);
    
    void guardarEnCSV(String path);
    
    List<Vehiculo> cargarDesdeCSV(String path);
    
    void guardarEnJSON(String path);
    
    List<T> cargarDesdeJSON(String path);
    
    void exportarPorTipoATXT(String path, String tipo);
    
    void guardarTodo();
}
