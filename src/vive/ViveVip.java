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
 * 返回给 我要卖
 * Servlet implementation class ViveVip
 */
@WebServlet("/ViveVip")
public class ViveVip extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViveVip() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//	request.setAttribute("message", "暂无登记车辆，请先登记车辆并缴纳保证金！");
		


		
		List<CurrContest> auctionInfo = null;
		
		List<CurrContest> auctionInfoFinish = null;

		try {
			if (request.getSession().getAttribute("user") == null) {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				auctionInfo = new AuctionDaoImpl()
						//.getAuctionVehicle(((User) request.getSession().getAttribute("user")).getU_id(), 1, "state");  
				.vgetAuctionVehicle3(((User) request.getSession().getAttribute("user")).getU_id(),"1", null);  //Vive state
				
				
				if (auctionInfo.size() != 0) {
					request.setAttribute("auctionInfo", auctionInfo);
					
					//求价钱总和
					long vlsum = 0;
					for (CurrContest item : auctionInfo) {
						vlsum += item.getBidSpri();
					}
					request.setAttribute("vlsum", vlsum);

				} else {
					request.setAttribute("message", "没有新的订单！");
				}
				
				
				//完成的购物
				auctionInfoFinish = new AuctionDaoImpl()
				.vgetAuctionVehicle3Finish(((User) request.getSession().getAttribute("user")).getU_id(),"1", null);  //Vive state
				if (auctionInfoFinish.size() != 0) {
					request.setAttribute("auctionInfoFinish", auctionInfoFinish);
					

				} else {
					request.setAttribute("messageFinish", "您还没有完成的交易记录.");
				}
				
				request.getRequestDispatcher("vvip.jsp").forward(request, response);
			}
		} catch (DBException e) {

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
