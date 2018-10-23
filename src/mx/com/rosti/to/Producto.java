package mx.com.rosti.to;

import java.sql.Date;

/**
 * Clase to para dias inhabiles.
 */
public class Producto implements Comparable<Object> {
	private String idProducto;
	private String nombre;
	private int idTipoProducto;
	private int idUnidad;
	private Date fechaAlta;
	private int idProveedor;
	private int piezas;
	private double costo;
	private double precio1;
	private double precio2;
	private double precio3;
	private double precio4;
	private double precio5;
	/**
	 * Parametros para ser usados en el front nadamas
	 */
	private String tipoProducto;
	private String unidades;
	private String proveedor;
	public final int getPiezas() {
		return piezas;
	}	
	public final String getTipoProducto() {
		return tipoProducto;
	}
	public final String getUnidades() { 
		return unidades;
	}
	public final String getProveedor() { 
		return proveedor;
	}
	public final void setPiezas(int tP) {
		this.piezas = tP;
	}
	public final void setTipoProducto(String tP) {
		this.tipoProducto = tP;
	}
	public final void setUnidades(String unit) {
		this.unidades = unit;
	}
	public final void setProveedor(String prov) {
		this.proveedor = prov;
	}
	public final Date getFechaAlta() {
		return fechaAlta;
	}

	public final void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public final String getIdProducto() {
		return idProducto;
	}

	public final void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public final int getIdProveedor() {
		return idProveedor;
	}

	public final void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public final int getIdTipoProducto() {
		return idTipoProducto;
	}

	public final void setIdTipoProducto(int idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}

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
	
	
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public double getPrecio1() {
		return precio1;
	}
	public void setPrecio1(double precio1) {
		this.precio1 = precio1;
	}
	public double getPrecio2() {
		return precio2;
	}
	public void setPrecio2(double precio2) {
		this.precio2 = precio2;
	}
	public double getPrecio3() {
		return precio3;
	}
	public void setPrecio3(double precio3) {
		this.precio3 = precio3;
	}
	public double getPrecio4() {
		return precio4;
	}
	public void setPrecio4(double precio4) {
		this.precio4 = precio4;
	}
	public double getPrecio5() {
		return precio5;
	}
	public void setPrecio5(double precio5) {
		this.precio5 = precio5;
	}
	public int compareTo(final Object o) {
        Producto prod = (Producto) o;
        boolean flag = this.idProducto == prod.getIdProducto();        
        return flag ? 1 : 0;
    }
	public String toString() { 
		return getIdProducto() + " " + getIdProveedor() + " " + getIdTipoProducto();
	}
}
