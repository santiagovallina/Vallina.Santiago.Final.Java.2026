package service;

import Model.Vehiculo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import service.CSVSerializable;


public class GestorVehiculos<T extends CSVSerializable & Comparable<T>> implements Gestionable<T>{
    
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
        System.out.println("Vehículo agregado correctamente.");
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
            System.out.println("Vehiculo con patente " + patente + " actualizado correctamente.");
            return;
        }
    }
    System.out.println("No se encontró un vehículo con la patente " + patente + ".");
    }
    
    @Override
    public void eliminarPorIndice(int id){
        vehiculos.remove(id);
    }
    
    @Override
    public void eliminarPorPatente(String patente){
        Iterator<T> it = vehiculos.iterator();
        while (it.hasNext()){
            T vehiculo = it.next();
            if (vehiculo instanceof Vehiculo && ((Vehiculo) vehiculo).getPatente().equalsIgnoreCase(patente)){
                it.remove();
                System.out.println("Vehiculo con patente " + patente + " eliminado");
                return;
            }
        }
        System.out.println("No se encontró ningún vehiculo con esa patente");
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
    
    @Override
    public void aplicarCambios(java.util.function.Consumer<T> consumer) {
        for (T vehiculo : vehiculos) {
            consumer.accept(vehiculo);
        }
    }
    
    @Override
    public void guardarEnBinario(String path){
        List<T> lista = vehiculos;
        try(ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(path))){
            salida.writeObject(lista);
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.print(ex.getMessage());
        }    
    }
    
    @Override
    public void cargarDesdeBinario(String path) {
    vehiculos.clear();
    
    try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(path))) {
        // Deserializa la lista de objetos
        vehiculos.addAll((List<T>) entrada.readObject());
    }   catch (IOException ex) {
        // Manejo de excepciones de I/O
        System.out.println("Error al leer el archivo: " + ex.getMessage());
        ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
        // Manejo de excepciones si la clase no es encontrada
        System.out.println("Clase no encontrada durante la deserialización: " + ex.getMessage());
        ex.printStackTrace();
    }
    }  
    
    
    
    @Override
    public void guardarEnCSV(String path ){
        List<T> lista = vehiculos;
        File archivo = new File(path);
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))){
            bw.write("patente,modelo,marca,color,precio\n");
            for(T e: lista){
                String tipo = e.getClass().getSimpleName().toUpperCase();
                bw.write(tipo + ", " + e.toCSV() + "\n");
            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }  
    }
    
    @Override
    public List<Vehiculo> cargarDesdeCSV(String path){
        List<Vehiculo> toReturn = new ArrayList<>();
        File archivo = new File(path);
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null){
                if(linea.endsWith("\n")){
                    linea = linea.substring(0, linea.length() -1);
                }
                String[] values = linea.split(",");
                toReturn.add(Vehiculo.fromCSV(linea));
            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return toReturn;
        }
    
    

    
}
