package mx.com.rosti.to;

public class TipoProducto {
    private int idTipoProducto;
    private String nombre;
    
	public final int getIdTipoProducto() {
		return idTipoProducto;
	}
	public final void setIdTipoProducto(int idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}
	public final String getNombre() {
		return nombre;
	}
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String toString() { 
		return getIdTipoProducto() + " " + getNombre();
	}
}
