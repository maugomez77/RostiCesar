package mx.com.rosti.servlets;

import java.io.Serializable;

public class InfoEtiquetasPDF implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4537510651425147654L;
	private String prov;
	private String descProducto;
	private String unidad;
	private String enteros;
	private String decimales;
	private String codProd;
	private String codBarras;
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getDescProducto() {
		return descProducto;
	}
	public void setDescProducto(String descProducto) {
		this.descProducto = descProducto;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public String getEnteros() {
		return enteros;
	}
	public void setEnteros(String enteros) {
		this.enteros = enteros;
	}
	public String getDecimales() {
		return decimales;
	}
	public void setDecimales(String decimales) {
		this.decimales = decimales;
	}
	public String getCodProd() {
		return codProd;
	}
	public void setCodProd(String codProd) {
		this.codProd = codProd;
	}
	public String getCodBarras() {
		return codBarras;
	}
	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}
	
}
