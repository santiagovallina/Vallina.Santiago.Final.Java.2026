package service;

public class VehiculoNoEncontradoException extends RuntimeException{
    
    private static final String MESSAGE = "No se encuentra vehículo registrado con esa patente.";
    
    public void VehiculoNoEncontradoException(){
        System.out.println(MESSAGE);
    }
    
}
