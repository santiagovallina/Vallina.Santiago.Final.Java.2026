package service;

import AppConfig.AppConfig;
import Model.Auto;
import Model.Camion;
import Model.Moto;
import Model.Vehiculo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
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
import java.util.Map;
import java.util.function.Predicate;
import service.CSVSerializable;


public class GestorVehiculos<T extends CSVSerializable & Comparable<T>> implements Gestionable<T>{
    
    private List<T> vehiculos = new ArrayList<>();
    
    public List<T> getLista() { 
        return vehiculos; 
    }

    @Override
    public void agregar(T item) {
        if(item == null){
            throw new IllegalArgumentException("Eso es un null.");
        }
        else if(!(item instanceof Vehiculo)) {
            throw new IllegalArgumentException("Eso no es un vehiculo.");
        }
        for (T t : vehiculos) {
            Vehiculo v = (Vehiculo) t;
            if (v.getPatente().equalsIgnoreCase(((Vehiculo)item).getPatente())) {
                throw new PatenteExistenteException();
            }
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
    throw new VehiculoNoEncontradoException();
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
        throw new VehiculoNoEncontradoException();
    }
    
    @Override
    public void ordenar(){
        Collections.sort(vehiculos);
    }
    
    @Override
    public void ordenar(Comparator<? super T> comparator){
        Collections.sort(vehiculos, comparator);
    }
    
    @Override
    public List<T> filtrar(Predicate<? super T> predicate){
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
        vehiculos.addAll((List<T>) entrada.readObject());
    }   catch (IOException ex) {
        System.out.println("Error al leer el archivo: " + ex.getMessage());
        ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
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
                toReturn.add(Vehiculo.fromCSV(linea));
            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return toReturn;
        }
    
    
    @Override
    public void guardarEnJSON(String path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            JsonArray array = new JsonArray();
            for (T e : vehiculos) {
                JsonObject obj = new JsonObject();
                obj.addProperty("tipo", e.getClass().getSimpleName());
                JsonElement elem = gson.toJsonTree(e);
                for (Map.Entry<String, JsonElement> entry : elem.getAsJsonObject().entrySet()) {
                    obj.add(entry.getKey(), entry.getValue());
                }
                array.add(obj);
            }
            gson.toJson(array, bw);
            System.out.println("Vehículos guardados correctamente en JSON: " + path);
        } catch (IOException ex) {
            System.out.println("Error al guardar en JSON: " + ex.getMessage());
        }
    }

    
    @Override
    public List<T> cargarDesdeJSON(String path) {
        List<T> toReturn = new ArrayList<>();
        try (FileReader reader = new FileReader(path)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            vehiculos.clear();
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                Vehiculo v = Vehiculo.fromJSON(obj);
                toReturn.add((T) v);
            }
            System.out.println("Vehículos cargados correctamente desde JSON.");
        } catch (IOException ex) {
            System.out.println("Error al cargar desde JSON: " + ex.getMessage());
        }
        return toReturn;
    }
    
    @Override
    public void exportarPorTipoATXT(String path, String tipo) {
    File archivo = new File(path);
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
    
        bw.write("Listado de Vehículos filtrados\n");
        bw.write("Tipo seleccionado: " + tipo + "\n");
        bw.write("====================================\n");

        for (T v : vehiculos) {
            
            if (v.getClass().getSimpleName().equalsIgnoreCase(tipo)) {
                Vehiculo vehiculo = (Vehiculo) v;
                bw.write("Tipo: " + v.getClass().getSimpleName() + "\n");
                bw.write("Patente: " + vehiculo.getPatente() + "\n");
                bw.write("Modelo: " + vehiculo.getModelo() + "\n");
                bw.write("Marca: " + vehiculo.getMarca() + "\n");
                bw.write("Color: " + vehiculo.getColor() + "\n");
                bw.write("Precio: " + vehiculo.getPrecio() + "\n");

                if (v instanceof Auto a) {
                    bw.write("Puertas: " + a.getPuertas() + "\n");
                    bw.write("Motor: " + a.getMotor() + "\n");
                } else if (v instanceof Moto m) {
                    bw.write("Cilindrada: " + m.getCilindrada() + "\n");
                    bw.write("Peso: " + m.getTipoMoto() + "\n");
                } else if (v instanceof Camion c) {
                    bw.write("Cantidad de ejes: " + c.getCantidadEjes() + "\n");
                }

                bw.write("------------------------------\n");
            }
        }
        System.out.println("Listado filtrado exportado correctamente a TXT: " + path);
    } catch (IOException ex) {
        System.out.println("Error al exportar a TXT: " + ex.getMessage());
    }
    }
    
    @Override
    public void guardarTodo() { 
        guardarEnJSON(AppConfig.PATH_JSON);
        guardarEnCSV(AppConfig.PATH_CSV);
        guardarEnBinario(AppConfig.PATH_SER); 
    }
    
}
