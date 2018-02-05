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
import pl.coderslab.util.Utils;

/**
 * Servlet implementation class UserForm
 */
@WebServlet("/UserForm")
public class UserForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserForm() {
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

		String idString = request.getParameter("id");
		String groupString = request.getParameter("group");
		int group = 0;
		int id = 0;
		
		if (Utils.isNumeric(groupString) && Utils.isNumeric(idString)) {
			id = Integer.parseInt(idString);
			group = Integer.parseInt(groupString);
			if (id < 0) {
				writer.append("Id must be positive value!<br>");
				writer.append("<a href='UserAdmin'>Return</a>");
			} else {
				Group[] groups = Group.loadAllGroups();
				if (!Utils.groupExists(groups, group) && group != 0) {
					writer.append("Group doesn't exist!<br>");
					writer.append("<a href='UserAdmin'>Return</a>");
				} else {
					session.setAttribute("userId", idString);
					session.setAttribute("groupId", groupString);
					request.getRequestDispatcher("/WEB-INF/panel/UserForm.jsp")
					.forward(request, response);	
				}
			}
			
		} else {
			writer.append("Id must be a integer!<br>");
			writer.append("<a href='UserAdmin'>Return</a>");
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
