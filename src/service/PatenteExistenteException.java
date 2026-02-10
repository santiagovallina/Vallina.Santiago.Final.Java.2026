package service;

public class PatenteExistenteException extends RuntimeException{
    
    private static final String MESSAGE = "Ya existe un vehículo registrado con esa patente.";
    
    public void PatenteExistenteException(){
        System.out.println(MESSAGE);
    }
    
}
