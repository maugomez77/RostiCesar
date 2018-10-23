package mx.com.rosti.to;

import java.sql.Date;
import java.sql.Time;

/**
 * Clase to para dias inhabiles.
 */
public class ReporteInventario {
	private String idInventario;
	private double cantidad;
	private Date   fechaEntrada;
	private Date   fechaSalida;
	private Time horaEntrada;
	private Time horaSalida;
	private String codeProd;
	private String descCodeProducto;
	private int    prov;
	private String descProveedor;
	private int    tPro;
	private String descTipoProd;
	private int    unid;
	private String descUnid;
	private String remision;
	private int    idCliente;
	private String cliente;
	private String salidaCarga;
	private String entradaCarga;
	private double precioUnitario;
	private double costoUnitario;
	public String getIdInventario() {
		return idInventario;
	}
	public void setIdInventario(String idInventario) {
		this.idInventario = idInventario;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public Time getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(Time horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public Time getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(Time horaSalida) {
		this.horaSalida = horaSalida;
	}
	public String getCodeProd() {
		return codeProd;
	}
	public void setCodeProd(String codeProd) {
		this.codeProd = codeProd;
	}
	public String getDescCodeProducto() {
		return descCodeProducto;
	}
	public void setDescCodeProducto(String descCodeProducto) {
		this.descCodeProducto = descCodeProducto;
	}
	public int getProv() {
		return prov;
	}
	public void setProv(int prov) {
		this.prov = prov;
	}
	public String getDescProveedor() {
		return descProveedor;
	}
	public void setDescProveedor(String descProveedor) {
		this.descProveedor = descProveedor;
	}
	public int getTPro() {
		return tPro;
	}
	public void setTPro(int tPro) {
		this.tPro = tPro;
	}
	public String getDescTipoProd() {
		return descTipoProd;
	}
	public void setDescTipoProd(String descTipoProd) {
		this.descTipoProd = descTipoProd;
	}
	public int getUnid() {
		return unid;
	}
	public void setUnid(int unid) {
		this.unid = unid;
	}
	public String getDescUnid() {
		return descUnid;
	}
	public void setDescUnid(String descUnid) {
		this.descUnid = descUnid;
	}
	public String getRemision() {
		return remision;
	}
	public void setRemision(String remision) {
		this.remision = remision;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getSalidaCarga() {
		return salidaCarga;
	}
	public void setSalidaCarga(String salidaCarga) {
		this.salidaCarga = salidaCarga;
	}
	public String getEntradaCarga() {
		return entradaCarga;
	}
	public void setEntradaCarga(String entradaCarga) {
		this.entradaCarga = entradaCarga;
	}
	public double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public double getCostoUnitario() {
		return costoUnitario;
	}
	public void setCostoUnitario(double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}
	
}