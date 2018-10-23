package mx.com.rosti.to;

public class Unidades {
    private int idUnidad;
    private String nombre;
	public final int getIdUnidad() {
		return idUnidad;
	}
	public final void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}
	public final String getNombre() {
		return nombre;
	}
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	} 
	public String toString() { 
		return getIdUnidad() + " " + getNombre();
	}
}
