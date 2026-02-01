package service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import Model.Vehiculo;

public class Serializadora {
    
    public static void serializarEventos(List<? extends Vehiculo> lista, String path) {
    try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(path))) {
        salida.writeObject(lista);
    } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println("Error al serializar los eventos: " + ex.getMessage());
    }
    }
    
    
    public static List<Vehiculo> deserializarEventos(String path) {
    List<Vehiculo> toReturn = new ArrayList<>();
    try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(path))) {
        toReturn = (List<Vehiculo>) entrada.readObject();
    } catch (IOException | ClassNotFoundException ex) {
        System.out.println("Error al deserializar los eventos: " + ex.getMessage());
        ex.printStackTrace();
    }
    return toReturn;
    }
     
}
