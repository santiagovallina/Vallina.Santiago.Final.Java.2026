package Model;

public class Auto extends Vehiculo implements Cargable{
    
    private int puertas;
    private double motor;

    public Auto(int puertas, double motor, String patente, int modelo, String marca, Color color) {
        super(patente, modelo, marca, color);
        this.puertas = puertas;
        this.motor = motor;
    }


    public Auto(String patente, int modelo, String marca, int puertas,  Color color) {
        super(patente, modelo, marca, color);
        this.puertas = puertas;
    }
    
    public Auto(String patente, int modelo, String marca,  Color color) {
        super(patente, modelo, marca, color);
    }
    
    @Override
    public void encender(){
        System.out.println("Encendiendo el auto");
    }
    
    @Override
    public void acelerar(){
        System.out.println("Acelerando el auto");
    }
    
    @Override
    public void frenar(){
        System.out.println("Frenando el auto");
    }
    
    @Override
    public void cargarVehiculo() {
        System.out.println("Cargando el baul del auto.");
    }
    
    
}
