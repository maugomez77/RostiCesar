package mx.com.rosti.to;

/**
 * Clase to para dias inhabiles.
 */
public class Proveedor {
	private int idProveedor;
	private String nombre;
	private String descripcion;
	private String generaCodigo;
	public final String getDescripcion() {
		return descripcion;
	}
	public final void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public final int getIdProveedor() {
		return idProveedor;
	}
	public final void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public final String getNombre() {
		return nombre;
	}
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public final String getGeneraCodigo() { 
		return generaCodigo;
	}
	public final void setGeneraCodigo(final String pGeneraCodigo) {
		this.generaCodigo = pGeneraCodigo;
	}
	public String toString() { 
		return getIdProveedor() + " " + getNombre() + " " + getDescripcion();
	}
}
