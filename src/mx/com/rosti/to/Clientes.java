package mx.com.rosti.to;

public class Clientes {
    private int idCliente;
    private String nombre;
    private int precioPreferente;
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	
	public int getPrecioPreferente() {
		return precioPreferente;
	}
	public void setPrecioPreferente(int precioPreferente) {
		this.precioPreferente = precioPreferente;
	}
	public String toString() {
		return idCliente + " " + nombre;
	} 
}
