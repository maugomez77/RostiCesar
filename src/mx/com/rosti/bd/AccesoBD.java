package mx.com.rosti.bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AccesoBD {
	
	
	public void accesoBd() throws NamingException, SQLException {
		
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)
		  envCtx.lookup("jdbc/RostiDB");

		Connection conn = ds.getConnection();
	    Statement st = conn.createStatement();
	    ResultSet res = st.executeQuery("select * from producto ");
		
	    while (res.next()) {
	    	System.out.println(res.getString("nombre"));
	    }
	    res.close();
	    st.close();
		conn.close();

	}
}
