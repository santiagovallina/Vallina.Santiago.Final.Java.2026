package service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


public interface Gestionable<T>{
    
    void agregar(T item);
    
    void leer();
    
    void actualizar(String patente, T nuevoVehiculo);
    
    void eliminar(String patente);
    
    public void ordenar();
    
    public void ordenar(Comparator<T> comparator);
    
    public List<T> filtrar(Predicate<T> predicate);
    

    
}
