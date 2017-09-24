package com.crunchify.restjersey;
//import com.postgresqltutorial.Apps;
import java.applet.AppletStub;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * @author Crunchify
 */
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider.App;
 
@Path("/ftocservice")
public class FtoCService {
	
	 	/*private final String url = "jdbc:postgresql://localhost/postgres";
	    private final String user = "postgres";
	    private final String password = "Kumar@99";*/
	
	private final String url = "jdbc:postgresql://stampy.db.elephantsql.com:5432/rxanedsc";
    private final String user = "rxanedsc";
    private final String password = "LoQI7v3bV6WxxoezZ2BdjWKeHMDjlGav";
    
	    String rs=null;

	@Path("{img}")
	@GET
	  @Produces("image/png")
	  public Response convertFtoC(@PathParam("img") String img) throws JSONException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
 
		System.out.println(img);
		JSONObject jsonObject = new JSONObject();
		
		/*Double fahrenheit = 98.24;
		Double celsius;
		celsius = (fahrenheit - 32)*5/9; */
		
		
		FtoCService app=new FtoCService();
		Connection conn = app.connect();
		InputStream rs=app.getImage(img,conn);
		conn.close();
		System.out.println("Connection closed \n");
		/*int i=1;
		try{
//		while (rs.next()) {
//            rs.getString("first_name");
			for(int j=0;j<rs.split(",").length;j++)
            jsonObject.put(j+"", rs.split(",")[j]); 
//        }
		}catch(Exception e)
		{
			e.printStackTrace();
		}*/
		/*jsonObject.put("F Value", fahrenheit); 
		jsonObject.put("C Value", celsius);*/
 
		String result = ""+jsonObject;
		return Response.status(200).entity(rs).build();
	  }
 
	  @Path("{f}")
	  @GET
	  @Produces("application/json")
	  public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {
 
		JSONObject jsonObject = new JSONObject();
		float celsius;
		celsius =  (f - 32)*5/9; 
		jsonObject.put("F Value", f); 
		jsonObject.put("C Value", celsius);
 
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
	  
	  public Connection connect() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
	        Connection conn = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
	            conn = DriverManager.getConnection(url, user, password);
	            System.out.println("Connected to the PostgreSQL server successfully.");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	 
	        return conn;
	    }
	    
	    /*public String getActorCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	        String SQL = "SELECT first_name FROM newtable";
	        int count = 0;
	 
	        try (Connection conn = connect();
	                Statement stmt = conn.createStatement();
	                ResultSet rs1 = stmt.executeQuery(SQL)) {
//	        	this.rs=rs;
//	            rs.next();
//	            count = rs.getInt(1);
//	            System.out.println("Count= "+count);
	            
	        	this.rs=displayActor(rs1);
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	 
	        return rs;
	    }*/
	    
	    /*private String displayActor(ResultSet rs) throws SQLException  {
	    	String k="";
	    	try{
	        while (rs.next()) {
	        	k=rs.getString("first_name")+","+k;
	            System.out.println(k);
	 
	        }
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return k;
	    }*/
	    public InputStream getImage(String img,Connection conn) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
//	    	Connection conn = connect();
	    	PreparedStatement ps = conn.prepareStatement("SELECT img FROM images WHERE imgname = ?");
	    	ps.setString(1, img);
	    	ResultSet rs = ps.executeQuery();
	    	InputStream in=null;
	    	if (rs != null) {
	    	    while (rs.next()) {
	    	         in = rs.getBinaryStream(1);
	    	        System.out.println(in);
	    	    }
	    	    rs.close();
	    	}
	    	ps.close();
	return in;
	    }
}
