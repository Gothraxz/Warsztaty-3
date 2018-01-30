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

/**
 * Servlet implementation class GroupAdmin
 */
@WebServlet("/GroupAdmin")
public class GroupAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupAdmin() {
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
		
		Group[] groups = Group.loadAllGroups();

		if (groups == null ) {
			writer
			.append("There aren't any groups!<br>")
			.append("<a href='" + request.getContextPath() 
			+ "' link='red'>Return to main site</a>");
		} else {
			session.setAttribute("groups", groups);
			request.getRequestDispatcher("/WEB-INF/panel/GroupList.jsp")
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
		
	}

}
