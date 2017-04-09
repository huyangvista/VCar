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
 * Servlet implementation class VCancelindentFinish
 */
@WebServlet("/VCancelindentFinish")
public class VCancelindentFinish extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VCancelindentFinish()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		List<CurrContest> auctionInfo = null;
		List<CurrContest> auctionInfoFinish = null;
		try
		{
			if (request.getSession().getAttribute("user") == null)
			{
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			else
			{
				auctionInfo = new AuctionDaoImpl()
						//.getAuctionVehicle(((User) request.getSession().getAttribute("user")).getU_id(), 1, "state");  
						.vgetAuctionVehicle3(((User) request.getSession().getAttribute("user")).getU_id(), "1", null); //Vive state
				if (auctionInfo.size() != 0)
				{
					request.setAttribute("auctionInfo", auctionInfo);
					// 求价钱总和
					long vlsum = 0;
					for (CurrContest item : auctionInfo)
					{
						vlsum += item.getBidSpri();
					}
					request.setAttribute("vlsum", vlsum);

				}
				else
				{
					request.setAttribute("message", "您的购物车没有物品！");
				}

				//完成的购物
				auctionInfoFinish = new AuctionDaoImpl()
						//.getAuctionVehicle(((User) request.getSession().getAttribute("user")).getU_id(), 1, "state");  
						.vgetAuctionVehicle3Finish(((User) request.getSession().getAttribute("user")).getU_id(), "1", null); //Vive state
			

				//是否删除 完成表
				if (request.getParameter("vstate").toString().equals("1"))
				{ //删除 完成订单

					int vindex = Integer.parseInt(request.getParameter("v_id").toString());
					new AuctionDaoImpl().vdelIndentFinish(auctionInfoFinish.get(vindex).getU_name().toString(), //((User) request.getSession().getAttribute("user")).getU_id(),
							auctionInfoFinish.get(vindex).getV_id().toString()); // Vive
					
					
					
						new AuctionDaoImpl().vexeUpdate("DELETE FROM `t_bid` WHERE (`v_id`='"+ auctionInfoFinish.get(vindex).getV_id().toString()+"')");
					// state
					auctionInfoFinish.remove(vindex);
				}
				else if (request.getParameter("vstate").toString().equals("-2"))
				{// 删除未完成订单
					int vindex = Integer.parseInt(request.getParameter("v_id").toString());
				
					new AuctionDaoImpl().vdelIndent(auctionInfo.get(vindex).getU_name().toString(), //((User) request.getSession().getAttribute("user")).getU_id(),
							auctionInfo.get(vindex).getV_id().toString()); // Vive
					// state
					auctionInfo.remove(vindex);
				}
				else
				{ //成功提交订单

					int vindex = Integer.parseInt(request.getParameter("v_id").toString());

					new AuctionDaoImpl().vupdateIndent(auctionInfo.get(vindex).getU_name().toString(), //((User) request.getSession().getAttribute("user")).getU_id(),
							auctionInfo.get(vindex).getV_id().toString()); // Vive

					new AuctionDaoImpl().vdelIndent(auctionInfo.get(vindex).getU_name().toString(), //((User) request.getSession().getAttribute("user")).getU_id(),
							auctionInfo.get(vindex).getV_id().toString()); // Vive
					// state
					auctionInfo.remove(vindex);
				}

				
				//完成的购物
				auctionInfoFinish = new AuctionDaoImpl()
						//.getAuctionVehicle(((User) request.getSession().getAttribute("user")).getU_id(), 1, "state");  
						.vgetAuctionVehicle3Finish(((User) request.getSession().getAttribute("user")).getU_id(), "1", null); //Vive state
				if (auctionInfoFinish.size() != 0)
				{
					request.setAttribute("auctionInfoFinish", auctionInfoFinish);

				}
				else
				{
					request.setAttribute("messageFinish", "您还没有完成的交易记录.");
				}
				
				
				
				request.getRequestDispatcher("vvip.jsp").forward(request, response);

			}

		}
		catch (DBException e)
		{

			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
