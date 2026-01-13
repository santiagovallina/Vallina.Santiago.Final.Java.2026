package service;

import Model.Vehiculo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;


public class GestorVehiculos<T extends Comparable<T>> implements Gestionable<T>{
    
    private List<T> vehiculos = new ArrayList<>();

    @Override
    public void agregar(T item) {
        if(item == null){
            throw new IllegalArgumentException("Eso es un null.");
        }
        else if(!(item instanceof Vehiculo)) {
            throw new IllegalArgumentException("Eso no es un vehiculo.");
        }
        vehiculos.add(item);
    }

    @Override
    public void leer() {
        if(vehiculos.isEmpty()){
            System.out.println("La lista esta vacia.");
        }
        System.out.println("Lista de vehiculos:");
        Iterator<T> it = new VehiculoIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }


    @Override
    public void actualizar(String patente, T nuevoVehiculo) {
        for (int i = 0; i < vehiculos.size(); i++) {
        T vehiculo = vehiculos.get(i);
        if (vehiculo instanceof Vehiculo && ((Vehiculo) vehiculo).getPatente().equalsIgnoreCase(patente)) {
            vehiculos.set(i, nuevoVehiculo);
            System.out.println("Vehículo con patente " + patente + " actualizado correctamente.");
            return;
        }
    }
    System.out.println("No se encontró un vehículo con la patente " + patente + ".");
    }


    @Override
    public void eliminar(String patente) {
        vehiculos.remove(patente);
    }
    
    
    @Override
    public void ordenar(){
        Collections.sort(vehiculos);
    }
    
    @Override
    public void ordenar(Comparator<T> comparator){
        Collections.sort(vehiculos, comparator);
    }
    
    @Override
    public List<T> filtrar(Predicate<T> predicate){
        List<T> toReturn = new ArrayList<>();
        for(T item : vehiculos){
            if(predicate.test(item)){
                toReturn.add(item);
            }
        }
        return toReturn;
    }
    
    private class VehiculoIterator implements Iterator<T> {
        private int indice = 0;

        @Override
        public boolean hasNext() {
            return indice < vehiculos.size();
        }

        @Override
        public T next() {
            return vehiculos.get(indice++);
        }
    }

    public Iterator<T> iterator() {
        return new VehiculoIterator();
    }
    
}
