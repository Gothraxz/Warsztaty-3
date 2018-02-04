package pl.coderslab.panel;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.util.Utils;

/**
 * Servlet implementation class GroupForm
 */
@WebServlet("/GroupForm")
public class GroupForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupForm() {
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
		int id = 0;
		if (Utils.isNumeric(idString)) {
			id = Integer.parseInt(idString);
			if (id < 0) {
				writer.append("Id must be positive value!<br>");
				writer.append("<a href='GroupAdmin'>Return</a>");
			} else {
				session.setAttribute("groupId", idString);
				request.getRequestDispatcher("/WEB-INF/panel/GroupForm.jsp")
				.forward(request, response);
			}
			
		} else {
			writer.append("Id must be a integer!<br>");
			writer.append("<a href='GroupAdmin'>Return</a>");
			
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
