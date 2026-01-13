package Model;

public class Camion extends Vehiculo implements Cargable{
    
    private int cantidadEjes;
    private int alto;
    private int largo;

    public Camion(int cantidadEjes, int alto, int largo, String patente, int modelo, String marca, Color color) {
        super(patente, modelo, marca, color);
        this.cantidadEjes = cantidadEjes;
        this.alto = alto;
        this.largo = largo;
    }


    public Camion(String patente, int modelo, String marca, int cantidadEjes, int alto, Color color) {
        super(patente, modelo, marca, color);
        this.cantidadEjes = cantidadEjes;
        this.alto = alto;  
    }
    
    public Camion(String patente, int modelo, String marca, Color color) {
        super(patente, modelo, marca, color);
    }
    
    @Override
    public void encender(){
        System.out.println("Encendiendo el camion");
    }
    
    @Override
    public void acelerar(){
        System.out.println("Acelerando el camion");
    }
    
    @Override
    public void frenar(){
        System.out.println("Frenando el camion");
    }

    @Override
    public void cargarVehiculo() {
        System.out.println("Cargando mercadería en el camión");
    }

  
}
