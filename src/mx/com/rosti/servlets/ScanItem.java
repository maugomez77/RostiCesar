package mx.com.rosti.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.rosti.processor.RostiAD;
import mx.com.rosti.to.Inventario;
import mx.com.rosti.to.Producto;
import mx.com.rosti.to.Proveedor;
import mx.com.rosti.to.TipoProducto;
import mx.com.rosti.to.Unidades;
import mx.com.rosti.util.Cantidad;
import mx.com.rosti.util.Utilerias;


public class ScanItem extends HttpServlet {
    String DELIMITADOR = "|";
    /**
	 * 
	 */
	private static final long serialVersionUID = 4274649656561673504L;
	@SuppressWarnings("unused")
	private ServletContext context;
    //private HashMap accounts = new HashMap();
    
    // Initialize the "accounts" hashmap.  For the sake of this exercise,
    // two accounts are created with names "greg" and "duke" during
    // initialization of the Servlet.
    //
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
        //accounts.put("greg","account data");
        //accounts.put("duke","account data");
    }
    
    public  void doGet(HttpServletRequest request, HttpServletResponse  response)
    throws IOException, ServletException {
        
    	Utilerias.debug(this, "Entrando doGet");
    	        
    }
    
	@SuppressWarnings("unchecked")
	public  void doPost(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
            
	    
    	HttpSession session = request.getSession();
    	if (! session.getId().equals(request.getParameter("sessionid"))) {
    		Utilerias.debug(this, session.getId());
    		Utilerias.debug(this, request.getParameter("sessionid"));
    		
    		session.invalidate();
    		response.sendRedirect("/RostiCesar/index.jsp");
    	}
    	
    	
    	boolean foundMatch = false;
    	boolean foundPrevious = false;
    	
    	RostiAD rosti = new RostiAD();
    	
    	String codeProduct = "";
	    String desc = "";
	    String cantidad = "";
	    String unidadMedida = "";
	    String tipoProducto = "";
	    String storageBox = "";
	    String proveedor = "";
	    	    
	    List<ElementsXMLBox> boxes; 
	    
	    Utilerias.debug(this, "Entramos:");
	    if (session.getAttribute("listaCajas") != null) {
    		boxes = (ArrayList<ElementsXMLBox>) session.getAttribute("listaCajas");	
    	} else { 
    		boxes = new ArrayList<ElementsXMLBox>();
    	}
    	
    	//se logro traer con el enter.
    	
        if (request.getParameter("action") == null) {
        	        	
            //doGet(request, response);
            
        	//bring id of the box
        	String targetId = request.getParameter("myinputid");
            
        	Utilerias.debug(this, "Length: " + targetId.length());        	
        	
        	if ((targetId != null)) {
                       		        		
        		//tanto para bachoco y tyson        		
        		//que no tenga punto la etiqueta
        		if ((targetId.length() == 25 || targetId.length() == 26) && (targetId.indexOf(".") < 0)) {
        			
        			//primer  intento con tyson de 0 a 4
        			codeProduct = targetId.substring(0, 5);
        			codeProduct = Utilerias.removeCharactersLeft(codeProduct, "0");
        			desc = rosti.getDescriptionProducto(codeProduct, "1");
        			
        			if (desc.equals("")) {
        				codeProduct = targetId.substring(0, 4);
            			codeProduct = Utilerias.removeCharactersLeft(codeProduct, "0");
            			desc = rosti.getDescriptionProducto(codeProduct, "1");
        			}
        			
        			Utilerias.debug(this, "Code: " + codeProduct);
        			Utilerias.debug(this, "desc: " + desc);        			
        			
        			if (!desc.equals("")) {
        				Utilerias.debug(this, "Entrada para tyson");        				
        				StringTokenizer st = new StringTokenizer(desc, DELIMITADOR);
        				codeProduct = st.nextToken();
        				proveedor = st.nextToken();
        				tipoProducto = st.nextToken();
        				unidadMedida = st.nextToken();
        				desc = st.nextToken();
        				//2930148310961114080029033
                        //primeros 4 para tipo de producto
            		    //codeProduct = targetId.substring(0, 4);
            		    //codeProduct = codeProduct.replace("0", "");
            		    //desc = "tyson";
        				cantidad = targetId.substring(19, 25);
            		    cantidad = Utilerias.removeCharactersLeft(cantidad, "0");
            		    cantidad = Utilerias.getFourCifrasDecimal(cantidad);
            		    //storageBox = targetId.substring(4, 18);
            		    storageBox = targetId;
            		    
            		    if (rosti.checkPrevious(storageBox + DELIMITADOR + codeProduct + DELIMITADOR + proveedor, "Inventario") 
            		    	|| checkList(storageBox, codeProduct, proveedor, boxes)) {
            		    	foundPrevious = true;
            		    	foundMatch = false;
            		    } else {
            		    	foundPrevious = false;
            		    	foundMatch = true;
            		    }
            		} else { 
            			
            			//validar para bachoco
        				//2930148310961114080029033
                        //primeros 4 para tipo de producto
            		    codeProduct = targetId.substring(2, 6);
            		    codeProduct = Utilerias.removeCharactersLeft(codeProduct, "0");
            		    
            		    desc = rosti.getDescriptionProducto(codeProduct, "2");
            		    
            		    if (!desc.equals("")) {
            		    	Utilerias.debug(this, "Entrada para bachoco");
            		    	
            		    	StringTokenizer st = new StringTokenizer(desc, DELIMITADOR);
            				codeProduct = st.nextToken();
            				proveedor = st.nextToken();
            				tipoProducto = st.nextToken();
            				unidadMedida = st.nextToken();
            				desc = st.nextToken();
            				
            		    	//al parecer es esta
	            		    cantidad = targetId.substring(12, 16);
	            		    cantidad = Utilerias.removeCharactersLeft(cantidad, "0");
	            		    cantidad = Utilerias.getFourCifrasDecimal(cantidad);	            		    
	            		    //storageBox = targetId.substring(17);
	            		    storageBox = targetId;
	            		    if (rosti.checkPrevious(storageBox + DELIMITADOR + codeProduct + DELIMITADOR + proveedor, "Inventario") 
	                		    	|| checkList(storageBox, codeProduct, proveedor, boxes)) {
	                		    foundPrevious = true;
	                		   	foundMatch = false;
	                		} else {
	                		  	foundPrevious = false;
	                		   	foundMatch = true;
	                		}
            		    }
        			}        		            		    
        		}
        		
        		//patsa con 2 longitudes y por el punto
        		if ((targetId.length() == 26 || targetId.length() == 27) && targetId.indexOf(".") != -1) {
        			
        			//0810025027.6260041013220900
        			targetId = Utilerias.replaceCharacters(targetId, ".", "");
        			codeProduct = targetId.substring(0, 3);
        			desc = rosti.getDescriptionProducto(codeProduct, "11");
        				
        			if (!desc.equals("")) {
        					
        				Utilerias.debug(this, "Entrada para patsa");
        				
	        		   	StringTokenizer st = new StringTokenizer(desc, DELIMITADOR);
	        			codeProduct = st.nextToken();
	        			proveedor = st.nextToken();
	        			tipoProducto = st.nextToken();
	        			unidadMedida = st.nextToken();
	        			desc = st.nextToken();
	        			cantidad = targetId.substring(7, 13);
	        		   	//cantidad = Utilerias.replaceCharacters(cantidad, ".", "");
	        		   	cantidad = Utilerias.removeCharactersLeft(cantidad, "0");
	        		   	cantidad = Utilerias.getFourCifrasDecimal(cantidad);
	            	    
	            	    storageBox = targetId;
	        		   	if (rosti.checkPrevious(storageBox + DELIMITADOR + codeProduct + DELIMITADOR + proveedor, "Inventario") 
	                    	|| checkList(storageBox, codeProduct, proveedor, boxes)) {
	                	    foundPrevious = true;
	                	   	foundMatch = false;
	                	} else {
	                	  	foundPrevious = false;
	                	   	foundMatch = true;
	                	}
	        	    }
        		}
        		
        		//aguas aqui, porque si entro con tyson en 26, entonces checar la variable de si 
        		//ya se encontro una caja
        		if ((targetId.length() == 26 || targetId.length() == 27 || targetId.length() == 31)
        			&& (foundMatch == false && foundPrevious == false) //por si se trata de dar entrada a la misma caja, ya
        													          //antes agregada por tyson y modificando las variables de control
        															  //se verifica el estado inicial
        			&& targetId.indexOf(".") < 0 ) {
        			
        			//000010823002114002501428306
        			codeProduct = targetId.substring(6, 9);
        		    codeProduct = Utilerias.removeCharactersLeft(codeProduct, "0");
        		    Utilerias.debug(this, "Code: " + codeProduct);
        		    desc = rosti.getDescriptionProducto(codeProduct, "7");
        		    
        		    if (!desc.equals("")) {
        		    		        		
        		    	Utilerias.debug(this, "Entrada para pilgrims");
        		    	
        		    	StringTokenizer st = new StringTokenizer(desc, DELIMITADOR);
        				codeProduct = st.nextToken();
        				proveedor = st.nextToken();
        				tipoProducto = st.nextToken();
        				unidadMedida = st.nextToken();
        				desc = st.nextToken();
        				
        		    	cantidad = targetId.substring(9, 15);
        		    	cantidad = Utilerias.removeCharactersLeft(cantidad, "0");
            		    cantidad = Utilerias.getFourCifrasDecimal(cantidad);	            		                		    
        		    	//storageBox = targetId.substring(15);
            		    storageBox = targetId;
        		    	if (rosti.checkPrevious(storageBox + DELIMITADOR + codeProduct + DELIMITADOR + proveedor, "Inventario") 
                		    	|| checkList(storageBox, codeProduct, proveedor, boxes)) {
                		    foundPrevious = true;
                		   	foundMatch = false;
                		} else {
                		  	foundPrevious = false;
                		   	foundMatch = true;
                		}
        		    }
        		}
        		
        		//qro
        		if (targetId.length() == 28 && targetId.indexOf(".") < 0) {
        			//1143553103276403010024333333
        			codeProduct = targetId.substring(7, 11);
        			Utilerias.debug(this, "Codigo prod: " + codeProduct);
        		    desc = rosti.getDescriptionProducto(codeProduct, "4");
        			
        			if (!desc.equals("")) {
        				
        				Utilerias.debug(this, "Entrada para queretaro");
        				
        				StringTokenizer st = new StringTokenizer(desc, DELIMITADOR);
        				codeProduct = st.nextToken();
        				proveedor = st.nextToken();
        				tipoProducto = st.nextToken();
        				unidadMedida = st.nextToken();
        				desc = st.nextToken();
        				
        				cantidad = targetId.substring(15, 19);
        				cantidad = Utilerias.removeCharactersLeft(cantidad, "0");
            		    cantidad = Utilerias.getFourCifrasDecimal(cantidad);	            		                		            		    	
        		        //storageBox = targetId.substring(0, 6);
            		    storageBox = targetId;
        		        if (rosti.checkPrevious(storageBox + DELIMITADOR + codeProduct + DELIMITADOR + proveedor, "Inventario") 
                		    	|| checkList(storageBox, codeProduct, proveedor, boxes)) {
                		    foundPrevious = true;
                		   	foundMatch = false;
                		} else {
                		  	foundPrevious = false;
                		   	foundMatch = true;
                		}
        			}
        		}

        		//nueva agregacion para buenaventura
        		if (targetId.length() == 22 && targetId.indexOf(".") < 0) {
        			//1116003650270002228430
        			codeProduct = targetId.substring(14, 17);
        			Utilerias.debug(this, "Codigo prod: " + codeProduct);
        		    desc = rosti.getDescriptionProducto(codeProduct, "3");
        			
        			if (!desc.equals("")) {
        				
        				Utilerias.debug(this, "Entrada para buenaventura");
        				
        				StringTokenizer st = new StringTokenizer(desc, DELIMITADOR);
        				codeProduct = st.nextToken();
        				proveedor = st.nextToken();
        				tipoProducto = st.nextToken();
        				unidadMedida = st.nextToken();
        				desc = st.nextToken();
        				
        				cantidad = targetId.substring(17, 21);
        				cantidad = Utilerias.removeCharactersLeft(cantidad, "0");
            		    cantidad = Utilerias.getFourCifrasDecimal(cantidad);	            		                		            		    	
        		        //storageBox = targetId.substring(0, 6);
            		    storageBox = targetId;
        		        if (rosti.checkPrevious(storageBox + DELIMITADOR + codeProduct + DELIMITADOR + proveedor, "Inventario") 
                		    	|| checkList(storageBox, codeProduct, proveedor, boxes)) {
                		    foundPrevious = true;
                		   	foundMatch = false;
                		} else {
                		  	foundPrevious = false;
                		   	foundMatch = true;
                		}
        			}
        		}

        		//avimarca
        		if (targetId.length() == 21 && targetId.indexOf(".") < 0) {
        			//020028226693659902803
        			codeProduct = targetId.substring(0, 3);
        			Utilerias.debug(this, "Codigo prod: " + codeProduct);
        		    desc = rosti.getDescriptionProducto(codeProduct, "8");
        			
        			if (!desc.equals("")) {
        				Utilerias.debug(this, "Entrada para avimarca");
        				StringTokenizer st = new StringTokenizer(desc, DELIMITADOR);
        				codeProduct = st.nextToken();
        				proveedor = st.nextToken();
        				tipoProducto = st.nextToken();
        				unidadMedida = st.nextToken();
        				desc = st.nextToken();
        				
        				cantidad = targetId.substring(7, 11);
        				cantidad = Utilerias.removeCharactersLeft(cantidad, "0");
            		    cantidad = Utilerias.getFourCifrasDecimal(cantidad);	            		                		            		    	
        		        //storageBox = targetId.substring(0, 6);
            		    storageBox = targetId;
        		        if (rosti.checkPrevious(storageBox + DELIMITADOR + codeProduct + DELIMITADOR + proveedor, "Inventario") 
                		    	|| checkList(storageBox, codeProduct, proveedor, boxes)) {
                		    foundPrevious = true;
                		   	foundMatch = false;
                		} else {
                		  	foundPrevious = false;
                		   	foundMatch = true;
                		}
        			}
        		}
        		
        		//para sabropollo
        		if (targetId.length() == 18 && targetId.indexOf(".") < 0) {
        			//1143553103276403010024333333
        			codeProduct = targetId.substring(7, 10);
        			codeProduct = Utilerias.removeCharactersLeft(codeProduct, "0");
        			Utilerias.debug(this, "Codigo prod: " + codeProduct);
        		    //desc = "Queretaro";
        			desc = rosti.getDescriptionProducto(codeProduct, "9");
        			Utilerias.debug(this, "descr sabro " + desc);
        			if (!desc.equals("")) {
        				Utilerias.debug(this, "Entrada para sabropollo");
        				StringTokenizer st = new StringTokenizer(desc, DELIMITADOR);
        				codeProduct = st.nextToken();
        				proveedor = st.nextToken();
        				tipoProducto = st.nextToken();
        				unidadMedida = st.nextToken();
        				desc = st.nextToken();
        				
        				cantidad = targetId.substring(10, 14);
        				cantidad = Utilerias.removeCharactersLeft(cantidad, "0");
            		    cantidad = Utilerias.getFourCifrasDecimal(cantidad);	            		                		            		    	
        		        //storageBox = targetId.substring(0, 6);
            		    storageBox = targetId;
        		        if (rosti.checkPrevious(storageBox + DELIMITADOR + codeProduct + DELIMITADOR + proveedor, "Inventario") 
                		    	|| checkList(storageBox, codeProduct, proveedor, boxes)) {
                		    foundPrevious = true;
                		   	foundMatch = false;
                		} else {
                		  	foundPrevious = false;
                		   	foundMatch = true;
                		}
        			}
        		}
        		//para las etiquetas creadas manualmente
        		if (targetId.length() == 32 && targetId.indexOf(".") < 0) {
        			        			
	        		//StringValidation.leftPad(idProv, 5, "0")
	    		    //+ StringValidation.leftPad(idProducto, 10, "#")
	    		    //+ StringValidation.leftPad(enteros, 3, "0")
	    		    //+ StringValidation.leftPad(decimales, 2, "0")
	    		    //+ StringValidation.leftPad(storage, 12, "0");
        			proveedor = targetId.substring(0, 5);
        			proveedor = Utilerias.removeCharactersLeft(proveedor, "0");
        			
        			codeProduct = targetId.substring(5, 15);
        			codeProduct = Utilerias.removeCharactersLeft(codeProduct, "?");
        			codeProduct = Utilerias.removeCharactersLeft(codeProduct, "#");
        			
        			desc = rosti.getDescriptionProducto(codeProduct, proveedor);
        			
        			Utilerias.debug(this, "Desc:" + desc);
        			if (!desc.equals("")) {
        				
        				Utilerias.debug(this, "Entrada para etiquetas manuales");            			
        				
        				StringTokenizer st = new StringTokenizer(desc, DELIMITADOR);
        				codeProduct = st.nextToken();
        				proveedor = st.nextToken();
        				tipoProducto = st.nextToken();
        				unidadMedida = st.nextToken();
        				desc = st.nextToken();
        				
        				Cantidad cant = Utilerias.validarCantidad(targetId.substring(15, 18) + "." + targetId.substring(18, 20));
        				cantidad = cant.getEnteros() + "." + cant.getDecimales();	            		                		            		    	
        		        Utilerias.debug(this, "cantidad: " + cantidad);
        				storageBox = targetId;
        				Utilerias.debug(this, "storageBox : " + storageBox);
        		        
        		        if (rosti.checkPrevious(storageBox + DELIMITADOR + codeProduct + DELIMITADOR + proveedor, "Inventario") 
                		    	|| checkList(storageBox, codeProduct, proveedor, boxes)) {
                		    foundPrevious = true;
                		   	foundMatch = false;
                		} else {
                		  	foundPrevious = false;
                		   	foundMatch = true;
                		}
        		        
        		        Utilerias.debug(this, "Variables: " + foundPrevious + "  " + foundMatch);
        			}
        			
        		}
        		
        		boolean length20 = false;
        		
        		
        		//estas son para las que se cargan con codigo aparte
        		//y ponerle la cantidad manualmente 
        		if (targetId.length() == 20 && targetId.indexOf(".") < 0) {
        			
        			//00011000000000000050
        			
        			//ABUSADO AQUI
        			//SE ESTA ENCONTRANDO POR CLAVE DEL PRODUCTO QUITANDO TODOS LOS CEROS A LA IZQUIERDA
        			//NO AGREGAR PRODUCTOS QUE EMPIECEN CON ALGUN CERO, PORQUE NO HARA MATCH CON LA BASE DE DATOS
        			
        			
        			//proveedor        			
        			String prov = Utilerias.removeCharactersLeft(targetId.substring(0, 5), "0");        			
        			//producto
        			String prod = Utilerias.removeCharactersLeft(targetId.substring(6), "0");
        			        			
        			Utilerias.debug(this, "producto: " + prod + "   prove: " + prov);
        			
        			Producto pro = (Producto) rosti.getData(prod + "|" + prov, "Producto");
        			
        			Utilerias.debug(this, "producto: tipo: " + pro.getIdTipoProducto() + "   unidades: " + pro.getIdUnidad() + "   prov: " + pro.getIdProveedor());
        			
        			TipoProducto tPro = (TipoProducto) rosti.getData(Utilerias.intToStr(pro.getIdTipoProducto()), "TipoProducto");
        			Unidades uni = (Unidades) rosti.getData(Utilerias.intToStr(pro.getIdUnidad()), "Unidades");
        			Proveedor provObj = (Proveedor) rosti.getData(Utilerias.intToStr(pro.getIdProveedor()), "Proveedor");
        			
        			Utilerias.debug(this, "Entre tPro: " + tPro.getIdTipoProducto() + "  unidades: " + uni.getIdUnidad() + "   prov: " + provObj.getIdProveedor());
        			
        			String url = "" 
        			    + "<url>" + "/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId() + "-action=Kilogramos-TipoProducto=" 
	            	    + tPro.getIdTipoProducto() + "-DescTipoProducto=" + tPro.getNombre() 
	            	    + "-Unidades=" + uni.getIdUnidad() + "-DescUnidades=" + uni.getNombre() 
	            	    + "-Proveedores=" + provObj.getIdProveedor() + "-DescProveedores=" + provObj.getNombre()
	            	    + "-Productos=" + pro.getIdProducto() + "-DescProductos=" + pro.getNombre()
	            	    + "</url>";
	        		
        			
        			writeListXMLHelper(session, response, boxes, foundMatch, foundPrevious, true, "<important>si</important>" + url);
        			length20 = true;
        			
        				                       	    
            	    
        			/*
        			response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId() + "&action=Kilogramos&TipoProducto=" 
    	            	    + tPro.getIdTipoProducto() + "&DescTipoProducto=" + tPro.getNombre() 
    	            	    + "&Unidades=" + uni.getIdUnidad() + "&DescUnidades=" + uni.getNombre() 
    	            	    + "&Proveedores=" + provObj.getIdProveedor() + "&DescProveedores=" + provObj.getNombre()
    	            	    + "&Productos=" + pro.getIdProducto() + "&DescProductos=" + pro.getNombre());
        			response.sendRedirect("/RostiCesar/pages/hola.jsp");
        			*/
    	        	    	        	
        		}
        		
        		if (foundMatch && !length20) {
        			
        			Utilerias.debug(this, "Found match");
        			
        			Utilerias.debug(this, "Codigo producto: " + codeProduct);
        		    Utilerias.debug(this, "Cantidad: " + cantidad);
        		    Utilerias.debug(this, "Storage: " + storageBox);
        		    
        		    Proveedor prov = (Proveedor) rosti.getData(proveedor, "Proveedor");
        		    TipoProducto tPro = (TipoProducto) rosti.getData(tipoProducto, "TipoProducto");
        		    Unidades uni = (Unidades) rosti.getData(unidadMedida, "Unidades");
        		    
        		    Utilerias.debug(this, "Proveedor: " + prov.getIdProveedor() + " " + prov.getDescripcion());
        		    Utilerias.debug(this, "Tipo Producto: " + tPro.getIdTipoProducto() + " " + tPro.getNombre());
        		    Utilerias.debug(this, "Unidades: " + uni.getIdUnidad() + " " + uni.getNombre());
        		    
        			ElementsXMLBox element = new ElementsXMLBox(codeProduct, desc, prov.getIdProveedor(), prov.getNombre(), cantidad, tPro.getIdTipoProducto(), tPro.getNombre(), uni.getIdUnidad(), uni.getNombre(), storageBox);
        			Utilerias.debug(this, "Storage: " + element.getStorage());
        			boxes.add(element);
        		    
        		    writeListXMLHelper(session, response, boxes, foundMatch, foundPrevious, true, "<important>no</important><url>Y</url>");
        			session.setAttribute("listaCajas", boxes);
        		} else {
        			Utilerias.debug(this, "Found no match " + length20);
        			
        		}
        		
        		if (!foundMatch && !length20){
        			writeListXMLHelper(session, response, boxes, foundMatch, foundPrevious, true, "<important>no</important><url>Y</url>");
        		}        		        	                
            }
        	
        } else if (request.getParameter("action").equals("eliminate")) {        	
        	boxes.remove(Integer.parseInt(request.getParameter("elemento").toString()));
        	session.setAttribute("listaCajas", boxes);
        	Utilerias.debug(this, "Caja eliminada");
        	response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId());
        	//context.getRequestDispatcher("/pages/inventario.jsp").forward(request, response);
        } else if (request.getParameter("action").equals("registerInventory")) {
        	//session.getAttribute("listaCajas");
        	long eSalida = addInventory(rosti, boxes);
        	//request.setAttribute("Added", "Good");
        	session.removeAttribute("listaCajas");
        	response.sendRedirect("/RostiCesar/pages/inventarioFinal.jsp?sessionid=" + session.getId() + "&Added=Good&entradaSalida=" + eSalida);
        } else if (request.getParameter("action").equals("TipoProducto")) { 
        	TipoProducto tipo = (TipoProducto) rosti.getData(request.getParameter("TipoProducto"), "TipoProducto");
        	response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId() + "&action=TipoProducto&TipoProducto=" 
        	    + tipo.getIdTipoProducto() + "&DescTipoProducto=" + tipo.getNombre());
        } else if (request.getParameter("action").equals("Unidades")) {
        	Unidades unidades = (Unidades) rosti.getData(request.getParameter("Unidades"), "Unidades");
        	response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId() + "&action=Unidades&TipoProducto=" 
            	    + request.getParameter("TipoProducto") + "&DescTipoProducto=" + request.getParameter("DescTipoProducto") 
            	    + "&Unidades=" + unidades.getIdUnidad() + "&DescUnidades=" + unidades.getNombre());
        } else if (request.getParameter("action").equals("Proveedores")) {
        	Proveedor prov = (Proveedor) rosti.getData(request.getParameter("Proveedores"), "Proveedor");
        	response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId() + "&action=Proveedores&TipoProducto=" 
            	    + request.getParameter("TipoProducto") + "&DescTipoProducto=" + request.getParameter("DescTipoProducto") 
            	    + "&Unidades=" + request.getParameter("Unidades") + "&DescUnidades=" + request.getParameter("DescUnidades") 
            	    + "&Proveedores=" + prov.getIdProveedor() + "&DescProveedores=" + prov.getNombre());
        } else if (request.getParameter("action").equals("Kilogramos")) {
        	
        	if (request.getParameter("Productos") == null || request.getParameter("Productos").equals("")) {
        		response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId() + "&action=Proveedores&TipoProducto=" 
                	    + request.getParameter("TipoProducto") + "&DescTipoProducto=" + request.getParameter("DescTipoProducto") 
                	    + "&Unidades=" + request.getParameter("Unidades") + "&DescUnidades=" + request.getParameter("DescUnidades") 
                	    + "&Proveedores=" + request.getParameter("Proveedores") + "&DescProveedores=" + request.getParameter("DescProveedores"));
        	} else {
        	
	        	Producto pro = (Producto) rosti.getData(request.getParameter("Productos"), "Producto");
	        	response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId() + "&action=Kilogramos&TipoProducto=" 
	            	    + request.getParameter("TipoProducto") + "&DescTipoProducto=" + request.getParameter("DescTipoProducto") 
	            	    + "&Unidades=" + request.getParameter("Unidades") + "&DescUnidades=" + request.getParameter("DescUnidades") 
	            	    + "&Proveedores=" + request.getParameter("Proveedores") + "&DescProveedores=" + request.getParameter("DescProveedores")
	            	    + "&Productos=" + pro.getIdProducto() + "&DescProductos=" + pro.getNombre());
        	}
        } else if (request.getParameter("action").equals("AgregarItem")) {
        	if (request.getParameter("numerica") == null || request.getParameter("numerica").equals("") || Utilerias.strToDouble(request.getParameter("numerica")) == -1 ) {
        		response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId() + "&action=Kilogramos&TipoProducto=" 
	            	    + request.getParameter("TipoProducto") + "&DescTipoProducto=" + request.getParameter("DescTipoProducto") 
	            	    + "&Unidades=" + request.getParameter("Unidades") + "&DescUnidades=" + request.getParameter("DescUnidades") 
	            	    + "&Proveedores=" + request.getParameter("Proveedores") + "&DescProveedores=" + request.getParameter("DescProveedores")
	            	    + "&Productos=" + request.getParameter("Productos") + "&DescProductos=" + request.getParameter("DescProductos"));
        	} else {
        		
        		storageBox = rosti.generateStorageUnit(request.getParameter("Productos"), 
        	                              request.getParameter("Proveedores"), 
        	                              request.getParameter("Unidades"), 
        	                              request.getParameter("TipoProducto"));
        		
        		if (storageBox.equals("0")) {
        			response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId() + "&wrongBox=Y");
        			
        		} else {
        			ElementsXMLBox element = new ElementsXMLBox(request.getParameter("Productos"), request.getParameter("DescProductos"), 
                            Utilerias.strToInt(request.getParameter("Proveedores")), 
                            request.getParameter("DescProveedores"),
                            request.getParameter("numerica"), 
                            Utilerias.strToInt(request.getParameter("TipoProducto")),
                            request.getParameter("DescTipoProducto"),
                            Utilerias.strToInt(request.getParameter("Unidades")),
                            request.getParameter("DescUnidades"),
                            storageBox);
					boxes.add(element);
					session.setAttribute("listaCajas", boxes);
					
					Utilerias.debug(this, "Item agregado");
					//response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId());
					Proveedor prov = (Proveedor) rosti.getData(request.getParameter("Proveedores"), "Proveedor");
		        	response.sendRedirect("/RostiCesar/pages/inventario.jsp?sessionid=" + session.getId() + "&action=Proveedores&TipoProducto=" 
		            	    + request.getParameter("TipoProducto") + "&DescTipoProducto=" + request.getParameter("DescTipoProducto") 
		            	    + "&Unidades=" + request.getParameter("Unidades") + "&DescUnidades=" + request.getParameter("DescUnidades") 
		            	    + "&Proveedores=" + prov.getIdProveedor() + "&DescProveedores=" + prov.getNombre());
        		}    			
        	}
        }
        
        
    }
    
	public long addInventory(RostiAD rosti, List<ElementsXMLBox> boxes) {
		
		long idEntrada = rosti.getIdVarios("entrada");
		
		for (int i=0; i < boxes.size(); i++) {
			ElementsXMLBox box = (ElementsXMLBox) boxes.get(i);
			Inventario inv = new Inventario();
			inv.setIdInventario(box.getStorage());
			inv.setIdProducto(box.getCodeProduct());
			inv.setIdProveedor(box.getProveedor());
			inv.setCantidad(Utilerias.strToDouble(box.getCantidad()));
			inv.setEntradaSalida("Entrada");
			inv.setIdEntradaCarga(idEntrada);
			inv.setIdSalidaCarga(0);
			inv.setRemision(0);
			inv.setIdCliente(0);
			Calendar cal = Calendar.getInstance();
			
			java.sql.Time time = new java.sql.Time( cal.getTime().getTime());
			inv.setHoraEntrada(time);
			inv.setHoraSalida(time);
			java.sql.Date date = new java.sql.Date(cal.getTime().getTime());
			inv.setFechaEntrada(date);
			inv.setFechaSalida(date);
			rosti.dmlOperations(inv, "insert");
		}
		
		return idEntrada;
	}
	
    public void setHeaderPage(HttpServletResponse response) {
    	response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
    }
    
    public void writeHeaderXML(HttpServletResponse response) { 
    
		try {
			response.getWriter().write("<data version=\"1.0\">");
		} catch (IOException e) {
			Utilerias.error(this, e.toString());
		}
		
    }
    
	public void writeCurrentSize(HttpSession session, HttpServletResponse response, List<ElementsXMLBox> elements, boolean foundMatch, boolean foundPrevious) {
    	try {
			response.getWriter().write("<content>" + "1" + "</content>");
			response.getWriter().write("<sessionid>" + session.getId() + "</sessionid>");
			
			if (foundPrevious) {
				response.getWriter().write("<foundPrevious>" + "true" + "</foundPrevious>");
			} else {
				response.getWriter().write("<foundPrevious>" + "false" + "</foundPrevious>");
			}
			if (foundMatch) {
			    response.getWriter().write("<foundMatch>" + "true" + "</foundMatch>");
			} else {
				response.getWriter().write("<foundMatch>" + "false" + "</foundMatch>");
			}
			
		} catch (IOException e) {
			Utilerias.error(this, e.toString());		
		}
    }
    public void writeFooterXML(HttpServletResponse response, boolean specificField, String dataField) {
    	try {
    		if (specificField) {
    			response.getWriter().write(dataField);
    		}
			response.getWriter().write("</data>");
		} catch (IOException e) {
			Utilerias.error(this, e.toString());
		}
    }
    public void writeFooterXML(HttpServletResponse response) {
    	try {
    		response.getWriter().write("</data>");
		} catch (IOException e) {
			Utilerias.error(this, e.toString());
		}
    }
    /*
	public void writeListXML(HttpSession session, HttpServletResponse response, List<ElementsXMLBox> elements, boolean foundMatch, boolean foundPrevious) {
    	
    	setHeaderPage(response);
    	writeHeaderXML(response);
    	writeCurrentSize(session, response, elements, foundMatch, foundPrevious);    	
    	for (int i=0; i < elements.size(); i++) {
    		ElementsXMLBox box  = (ElementsXMLBox) elements.get(i);
    		writeElementXML(response, box);    		
    	}
    	writeFooterXML(response);    	
    }*/
    
	public void writeListXMLHelper(HttpSession session, HttpServletResponse response, List<ElementsXMLBox> elements, 
	    boolean foundMatch, boolean foundPrevious, boolean specificField, String dataField) {
    	
    	setHeaderPage(response);
    	writeHeaderXML(response);
    	writeCurrentSize(session, response, elements, foundMatch, foundPrevious);    	
    	for (int i=0; i < elements.size(); i++) {
    		ElementsXMLBox box  = (ElementsXMLBox) elements.get(i);
    		writeElementXML(response, box);    		
    	}
    	writeFooterXML(response, specificField, dataField);    	
    }
	public boolean checkList(String storageBox, String codeProduct, String proveedor, List<ElementsXMLBox> lista) {
		for (int i=0; i < lista.size(); i++) {
			ElementsXMLBox box = (ElementsXMLBox) lista.get(i);
			if (box.getCodeProduct().equals(codeProduct) 
				&& box.getStorage().equals(storageBox) 
				&& box.getProveedor() == Integer.parseInt(proveedor) ) {
				return true;
			}
		}
		return false;
	}
	
    public void writeElementXML(HttpServletResponse response, ElementsXMLBox box) { 
    	try {
			response.getWriter().write("<code>" + box.getCodeProduct() + "</code>");
			response.getWriter().write("<desc>" + box.getDescProduct() + "</desc>");
			response.getWriter().write("<proveedor>" + box.getProveedor() + "</proveedor>");
			response.getWriter().write("<descProveedor>" + box.getDescProveedor() + "</descProveedor>");        	
        	response.getWriter().write("<cantidad>" + box.getCantidad() + "</cantidad>");
        	response.getWriter().write("<tipoProducto>" + box.getTipoProducto() + "</tipoProducto>");
        	response.getWriter().write("<tipoProductoDesc>" + box.getTipoProductoDesc() + "</tipoProductoDesc>");
        	response.getWriter().write("<unidadMedida>" + box.getUnidadMedida() + "</unidadMedida>");
        	response.getWriter().write("<unidadMedidaDesc>" + box.getUnidadMedidaDesc() + "</unidadMedidaDesc>");
        	response.getWriter().write("<storage>" + box.getStorage() + "</storage>");
    	} catch (IOException e) {
			Utilerias.error(this, e.toString());
		}
    }
}
