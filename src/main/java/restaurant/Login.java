package restaurant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.MyDao;
import Dto.Customer;

//Mapping the URL
@WebServlet("/Login")
public class Login extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//Recive Values from Front-End Email And Password
		String email=req.getParameter("email");
		String password=req.getParameter("pass");
		
		//Verify email exist
		MyDao dao=new MyDao();
		
		// this code is to check & enter the admin block
				if (email.equals("admin@jsp.com") && password.equals("admin")) {
					resp.getWriter().print("<h1> Account Created Succefully</h>");

					// this is the logic to send to the AdminHomePage
					req.getRequestDispatcher("AdminHomePage.html").include(req, resp);
				} else {
		Customer customer =dao.fetchByEmail(email);
		if(customer==null)
		{
			resp.getWriter().print("<h1 style='color:red'>Invalid Email</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		}
		else {
			if(password.equals(customer.getPassword())) {
				resp.getWriter().print("<h1 style='color:green'> Login Success</h1>");
				req.getRequestDispatcher("CustomerHomePage.html").include(req, resp);
			}
			else {
				resp.getWriter().print("<h1 Style='color:red'>Invalid Password!..</h1>");
				req.getRequestDispatcher("Login.html").include(req, resp);
			}
		}
}	
}
}
