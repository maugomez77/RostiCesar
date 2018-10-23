package mx.com.rosti.to;

public class Remision {
	long idRemision;
	int idCliente;
	String nombreCliente;
	int idProveedor;
	String nombreProv;
	String idProducto;
	String nombreProducto;
	java.sql.Date fechaEntrada;
	java.sql.Time horaEntrada;
	java.sql.Date fechaSalida;
	java.sql.Time horaSalida;
	double cantidad;
	double precioUnitario;
	double precioTotal;
	String idInventario;
	public double getPrecioUnitario() { 
		return precioUnitario;
	}
	public double getPrecioTotal() { 
		return precioTotal;
	}
	public void setPrecioUnitario(double pPrecio) {
		this.precioUnitario = pPrecio;
	}
	public void setPrecioTotal(double pPrecio) {
		this.precioTotal = pPrecio;
	}
	public long getIdRemision() {
		return idRemision;
	}
	public void setIdRemision(long idRemision) {
		this.idRemision = idRemision;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getNombreProv() {
		return nombreProv;
	}
	public void setNombreProv(String nombreProv) {
		this.nombreProv = nombreProv;
	}
	public String getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public java.sql.Date getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(java.sql.Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public java.sql.Time getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(java.sql.Time horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public java.sql.Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(java.sql.Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public java.sql.Time getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(java.sql.Time horaSalida) {
		this.horaSalida = horaSalida;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public String getIdInventario() {
		return idInventario;
	}
	public void setIdInventario(String idInventario) {
		this.idInventario = idInventario;
	}
	
}
