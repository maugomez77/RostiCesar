package mx.com.rosti.to;

public class Usuario {
    private String idUsuario;
    private String nombre;
    private int idRol;
    private String descRol; //not used in queries, just to show in the view
    private String password;
    private String correoElectronico;
    private String telefono;
	public final String getCorreoElectronico() {
		return correoElectronico;
	}
	public final void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public final int getIdRol() {
		return idRol;
	}
	public final void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	public final String getIdUsuario() {
		return idUsuario;
	}
	public final void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public final String getNombre() {
		return nombre;
	}
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public final String getPassword() {
		return password;
	}
	public final void setPassword(String password) {
		this.password = password;
	}
	public final String getTelefono() {
		return telefono;
	}
	public final void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public final String getDescRol() {
		return descRol;
	}
	public final void setDescRol(String dRol) {
		this.descRol = dRol;
	}
    public String toString() { 
        return getIdUsuario() + " " + getNombre() 
             + getIdRol() + " " + getPassword() + " " 
             + getCorreoElectronico() + " " + getTelefono(); 
    }
}
