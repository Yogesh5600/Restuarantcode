package restaurant;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import Dto.Customer;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Dao.MyDao;

//This is to Map the action URL to this class(Should be Same as action - Case sensitive)
@WebServlet("/signup")
//To receive image we need to use this-enctype
@MultipartConfig
//This is to make Class as Servlet Class
public class CustomerSignup extends HttpServlet {
	@Override
	// When there is form and we want data to be secured so doPost
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Logic to Receive Values from Front End
		String fullName = req.getParameter("fullname");
		String password = req.getParameter("password");
		long mobile = Long.parseLong(req.getParameter("mobile"));
		String email = req.getParameter("email");
		String gender = req.getParameter("gender");
		String country = req.getParameter("country");
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		int age = Period.between(dob, LocalDate.now()).getYears();

		// Logic to Receive image and convert to byte[]
		Part pic = req.getPart("picture");
		byte[] picture = null;
		picture = new byte[pic.getInputStream().available()];
		pic.getInputStream().read(picture);

		MyDao dao = new MyDao();

		
			// logic to verify email and mobile is not repeated
			if (dao.fetchByEmail(email) == null && dao.fetchByMobile(mobile) == null) {
				// loading values inside object
				Customer cust = new Customer();
				cust.setAge(age);
				cust.setDob(dob);
				cust.setCountry(country);
				cust.setEmail(email);
				cust.setFullname(fullName);
				cust.setGender(gender);
				cust.setMobile(mobile);
				cust.setPassword(password);
				cust.setPicture(picture);

				//
				dao.save(cust);
				resp.getWriter().print("<h1> Account Created Succefully</h>");
				req.getRequestDispatcher("CustomerHomePage.html").include(req, resp);
			} else {
				resp.getWriter().print("<h1 style='color:green'> Email and Mobile should be Unique</h>");
				// req.getRequestDispatcher this method is used to take the curser from one page
				// to another page
				// include method is used to to bring curser as well as (Email and Mobile should
				// be Unique)content from one page another page
				// Forward method is used to to bring the curser from one page another page but
				// it will not contain any content
				req.getRequestDispatcher("signup1.html").include(req, resp);
			}
		

		System.out.println(fullName);
		System.out.println(password);
		System.out.println(mobile);
		System.out.println(email);
		System.out.println(gender);
		System.out.println(country);
		System.out.println(dob);
		System.out.println(age);
	}

}
