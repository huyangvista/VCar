package vive;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zlpc.dao.impl.AuctionDaoImpl;

/**
 * 返回 订单提交成功
 * Servlet implementation class VIndent
 */
@WebServlet(name = "ViveIndent", urlPatterns = { "/ViveIndent" })
public class VIndent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VIndent() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			if (request.getSession().getAttribute("user") == null) {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				new AuctionDaoImpl().vsetIndent(request.getParameter("vuser").toString(),request.getParameter("vstate").toString(),"0");
				//response.getWriter().append("Served at: 000" + request.getParameter("vstate").toString() + "000" ).append(request.getContextPath());
				request.setAttribute("message", "恭喜您!订单提交成功,卖家会在近期联系您.");
				request.getRequestDispatcher("Vive/VIndent.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
