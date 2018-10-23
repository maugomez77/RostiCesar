package mx.com.rosti.to;

import java.sql.Date;
import java.sql.Time;

/**
 * Clase to para dias inhabiles.
 */
public class Inventario {
    private String idInventario;
    private double cantidad;
    private String entradaSalida;
    private Date fechaEntrada;
    private Date fechaSalida;
    private Time horaEntrada;
    private Time horaSalida;
    private String idProducto;
    private int idProveedor;
    private long idEntradaCarga;
    private long idSalidaCarga;
    private long remision;
    private int  idCliente;
    
    public String toString() { 
		return getIdInventario() + " " + getEntradaSalida() + " " + getCantidad() + " " + getIdProducto();
	}
	
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

	public String getEntradaSalida() {
		return entradaSalida;
	}

	public void setEntradaSalida(String entradaSalida) {
		this.entradaSalida = entradaSalida;
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

	public void setHoraEntrada(Time hora) {
		this.horaEntrada = hora;
	}

	public Time getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(Time hora) {
		this.horaSalida = hora;
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public long getIdEntradaCarga() {
		return idEntradaCarga;
	}

	public void setIdEntradaCarga(long idEntradaCarga) {
		this.idEntradaCarga = idEntradaCarga;
	}

	public long getIdSalidaCarga() {
		return idSalidaCarga;
	}

	public void setIdSalidaCarga(long idSalidaCarga) {
		this.idSalidaCarga = idSalidaCarga;
	}

	public long getRemision() {
		return remision;
	}

	public void setRemision(long remision) {
		this.remision = remision;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
}
