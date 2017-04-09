package vive;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zlpc.dao.impl.AuctionDaoImpl;
import cn.zlpc.po.User;
import cn.zlpc.vo.CurrContest;
import tool.mastery.exception.DBException;

/**
 * 返回给 取消购物
 * Servlet implementation class VCancleIndent
 */
@WebServlet("/VCancleIndent")
public class VCancleIndent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VCancleIndent() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      返回到 订单取消页
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: " +
		// request.getParameter("vuser").toString()+
		// request.getParameter("v_id").toString()
		// ).append(request.getContextPath());

		List<CurrContest> auctionInfo = null;
		try {
			if (request.getSession().getAttribute("user") == null) {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				auctionInfo = new AuctionDaoImpl()
						.vgetAuctionVehicle(((User) request.getSession().getAttribute("user")).getU_id(), "0", null); // Vive
				if (auctionInfo.size() != 0) {
					request.setAttribute("auctionInfo", auctionInfo);
					// 求价钱总和
					long vlsum = 0;
					for (CurrContest item : auctionInfo) {
						vlsum += item.getBidSpri();
					}
					request.setAttribute("vlsum", vlsum);

				} else {
					request.setAttribute("message", "您的购物车没有物品！");
				}

				// 删除数据
				System.out.println("----" + request.getParameter("v_id").toString());
				int vindex = Integer.parseInt(   request.getParameter("v_id").toString())  ;
				new AuctionDaoImpl().vdelIndent(((User) request.getSession().getAttribute("user")).getU_id(),
						auctionInfo.get(vindex).getV_id().toString()); // Vive state
				auctionInfo.remove(vindex);
				request.getRequestDispatcher("user_file/NetworkAuction/AuctionForList.jsp").forward(request, response);

			}

		} catch (DBException e) {

			e.printStackTrace();
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
