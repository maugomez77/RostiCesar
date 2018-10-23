package mx.com.rosti.to;

/**
 * Clase to para dias inhabiles.
 */
public class Roles {
	private int idRol;
	private String nombreRol;
	private String descripcion;
	
	public final String getDescripcion() {
		return descripcion;
	}
	public final void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public final int getIdRol() {
		return idRol;
	}
	public final void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	public final String getNombreRol() {
		return nombreRol;
	}
	public final void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	} 
    public String toString() { 
    	return getIdRol() + " " + getNombreRol() + " " + getDescripcion();
    }
}
