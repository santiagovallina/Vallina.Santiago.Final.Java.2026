package Model;

public class Moto extends Vehiculo{
    
    private int cilindrada;
    private int peso;

    public Moto(String patente, int modelo, String marca, int cilindrada, int peso,  Color color) {
        super(patente, modelo, marca, color);
        this.cilindrada = cilindrada;
        this.peso = peso;
    }
    
    public Moto(String patente, int modelo, String marca, int cilindrada,  Color color) {
        super(patente, modelo, marca, color);
        this.cilindrada = cilindrada;
    }
    
    public Moto(String patente, int modelo, String marca,  Color color) {
        super(patente, modelo, marca, color);
    }
    
    @Override
    public void encender(){
        System.out.println("Encendiendo la moto");
    }
    
    @Override
    public void acelerar(){
        System.out.println("Acelerando la moto");
    }
    
    @Override
    public void frenar(){
        System.out.println("Frenando la moto");
    }
    
    
    
    
}
