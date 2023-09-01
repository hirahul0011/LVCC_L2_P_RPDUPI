

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.DBConnection;

/**
 * Servlet implementation class ProductDetails
 */
public class ProductDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int productID=Integer.parseInt(request.getParameter("productID"));
		
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			
			  InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
			  Properties props = new Properties();
			  props.load(in);
			  
			  
			  DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
			  Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			  
			  ResultSet rst;
			      		      
			  rst = stmt.executeQuery("select * from eproduct where ID="+productID);			  
			  			  
			  if(rst.next()) {
//				  while (rst.next()) {
				  out.println("Details of the Product are:<br>");
					  out.println("Product ID: " + rst.getInt("ID") + "<BR>Product Name: " + rst.getString("name") +
							  "<BR>Product Price: " + rst.getBigDecimal("price") +
							  "<BR>Product Added On: " +rst.getDate("date_added") + "<Br>");
//				  }				  
			  }else {
				  request.getRequestDispatcher("index.html").include(request, response);
				  out.println("<SPAN style='color:red'>Invalid Product ID</SPAN>");
			  }
			  
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
