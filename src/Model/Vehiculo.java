package Model;

public abstract class Vehiculo {
    
    private String patente;
    private int modelo;
    private String marca;
    private Color color;

    public Vehiculo(String patente, int modelo, String marca, Color color) {
        this.patente = patente;
        this.modelo = modelo;
        this.marca = marca;
        this.color = color;
    }


    public void encender(){
        System.out.println("Encender vehiculo");
    }
    
    public void acelerar(){
        System.out.println("Acelerar vehiculo");
    }
    
    public void frenar(){
        System.out.println("Frenar vehiculo");
    }

    public String getPatente() {
        return patente;
    }
    
    
    
}
