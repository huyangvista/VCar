package cn.zlpc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.mastery.exception.DBException;
import cn.zlpc.dao.impl.AuctionDaoImpl;
import cn.zlpc.po.User;
import cn.zlpc.vo.CurrContest;
/***
 * 返回给 购物车
 * @author Hocean
 *
 */
public class AutionForListServlet extends HttpServlet {

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * 返回到 提交订单
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CurrContest> auctionInfo = null;

		try {
			if (request.getSession().getAttribute("user") == null) {
				//request.getRequestDispatcher("login.jsp").forward(request, response);
				
				
			} else {
				auctionInfo = new AuctionDaoImpl()
						// .getAuctionVehicle(((User)
						// request.getSession().getAttribute("user")).getU_id(),
						// 1, "state");
						// .getAuctionVehicleAll(((User)
						// request.getSession().getAttribute("user")).getU_id());
						// //Vive state
						.vgetAuctionVehicle(((User) request.getSession().getAttribute("user")).getU_id(), "0", null); // Vive
																														// state

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
				request.getRequestDispatcher("user_file/NetworkAuction/AuctionForList.jsp").forward(request, response);
			}
		} catch (DBException e) {

			e.printStackTrace();
		}

	}

	public void init() throws ServletException {
		// Put your code here
	}

}
