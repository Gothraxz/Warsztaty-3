package pl.coderslab.panel;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.model.Group;
import pl.coderslab.model.User;

/**
 * Servlet implementation class UserAdmin
 */
@WebServlet("/UserAdmin")
public class UserAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		Writer writer = response.getWriter();
		
		User[] users = User.loadAllUsers();

		if (users == null ) {
			writer
			.append("There aren't any users!<br>")
			.append("<a href='" + request.getContextPath() 
			+ "' link='red'>Return to main site</a>");
		} else {
			session.setAttribute("users", users);
			request.getRequestDispatcher("/WEB-INF/panel/UserList.jsp")
			.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		Writer writer = response.getWriter();
		
		String userId = (String) session.getAttribute("userId");
		String userName = request.getParameter("userName");
		String userEmail = request.getParameter("userEmail");
		String groupId = request.getParameter("groupId");
		String password = request.getParameter("password");
		
		Group group = Group.loadById(0);
		if (groupId != null && groupId != "") {
			group = Group.loadById(Integer.parseInt(groupId));
		}
		
		int id = Integer.parseInt(userId);
		
		User user;
		if (id == 0) {
			user = new User(userName, userEmail, group, password);
			user.setPassword(password);
			user.saveToDB();
			doGet(request, response);
		} else {
			user = User.loadById(id);
			
			if (userName != null && userName != "") {
				user.setUsername(userName);		
			}
			
			if (userEmail != null && userEmail != "") {
				user.setEmail(userEmail);
			}
			
			if (groupId != null && groupId != "") {
				user.setGroup(group);
			}
			
			if (password != null && password != "") {
				user.setPassword(password);
			}
		
			user.saveToDB();
			doGet(request, response);
		}

	}

}
