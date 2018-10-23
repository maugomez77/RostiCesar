package mx.com.rosti.servlets;

public class ElementsGroupedXMLBox {
    private String codeProduct;
    private String descProduct;
    private String storage;
    private int proveedor;
    private String descProveedor;    
    private String cantidad;
    private int tipoProducto;
    private String tipoProductoDesc;
    private int unidadMedida;
    private String unidadMedidaDesc;
    
    public ElementsGroupedXMLBox(String code, String desc,  int prov, String descProveedor,  String cantidad, int tipoProd, String tipoProdDesc, int unidadMedida, String unidadMedidaDesc, String storage) {
        this.codeProduct = code;
        this.descProduct = desc;
        this.proveedor = prov;
        this.descProveedor = descProveedor;
        this.cantidad = cantidad;
        this.tipoProducto = tipoProd;
        this.tipoProductoDesc = tipoProdDesc;
        this.unidadMedida = unidadMedida;
        this.unidadMedidaDesc = unidadMedidaDesc;
        this.storage = storage;        
	}
    
    public String toString() { 
    	return getCodeProduct() + " " + getDescProduct() + " " + getProveedor() + " " + getDescProveedor() + " " 
    	     + getCantidad() + " " + getTipoProducto() + " " + getTipoProductoDesc() + " " 
    	     + getUnidadMedida() + " " + getUnidadMedidaDesc() + " " + getStorage();
    }

	public String getCodeProduct() {
		return codeProduct;
	}

	public void setCodeProduct(String codeProduct) {
		this.codeProduct = codeProduct;
	}

	public String getDescProduct() {
		return descProduct;
	}

	public void setDescProduct(String descProduct) {
		this.descProduct = descProduct;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public int getProveedor() {
		return proveedor;
	}

	public void setProveedor(int proveedor) {
		this.proveedor = proveedor;
	}

	public String getDescProveedor() {
		return descProveedor;
	}

	public void setDescProveedor(String descProveedor) {
		this.descProveedor = descProveedor;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public int getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(int tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public String getTipoProductoDesc() {
		return tipoProductoDesc;
	}

	public void setTipoProductoDesc(String tipoProductoDesc) {
		this.tipoProductoDesc = tipoProductoDesc;
	}

	public int getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(int unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getUnidadMedidaDesc() {
		return unidadMedidaDesc;
	}

	public void setUnidadMedidaDesc(String unidadMedidaDesc) {
		this.unidadMedidaDesc = unidadMedidaDesc;
	}
}