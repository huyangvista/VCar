package vive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zlpc.dao.impl.AuctionDaoImpl;
import cn.zlpc.po.User;
import cn.zlpc.vo.CurrContest;
import cn.zlpc.vo.VCarUser;
import tool.mastery.exception.DBException;

/**
 * Servlet implementation class VMylist
 */
@WebServlet("/vmylist.html")
public class VMylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VMylist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		List<CurrContest> auctionInfo = null;
		
		List<CurrContest> auctionInfoFinish = null;


			if (request.getSession().getAttribute("user") == null) {
				//response.sendRedirect("/");

				//request.getRequestDispatcher("/Vive/vmylist.jsp").forward(request, response);
				request.getRequestDispatcher("/login.jsp").forward(request, response);

			} else {
				try {
					auctionInfo = new AuctionDaoImpl()
                            //.getAuctionVehicle(((User) request.getSession().getAttribute("user")).getU_id(), 1, "state");
                    .vgetAuctionVehicle3(((User) request.getSession().getAttribute("user")).getU_id(),"1", null);  //Vive state
				} catch (DBException e) {
					e.printStackTrace();
				}


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
				try {
					auctionInfoFinish = new AuctionDaoImpl()	.vgetAuctionVehicle4Finish(((User) request.getSession().getAttribute("user")).getU_id(),"-1", null);  //Vive state
				} catch (DBException e) {
					e.printStackTrace();
				}
				if (auctionInfoFinish.size() != 0) {
					request.setAttribute("auctionInfoFinish", auctionInfoFinish);
					

				} else {
					request.setAttribute("messageFinish", "您还没有完成的交易记录.");
				}
				
				
				//添加商品数量
				String uid = ((User) request.getSession().getAttribute("user")).getU_id();


				String sq0 = String.format("SELECT * FROM `t_userpartfinish`  WHERE (`u_id`='%s') ", uid);
				List<String> vlist = new AuctionDaoImpl().vexeSqlList(sq0, "price");
				List<String> vlists = new AuctionDaoImpl().vexeSqlList(sq0, "state");
				List<VCarUser> vlistu = new ArrayList<>();
				int vvi = 0;
				for (String string : vlist)
				{
					VCarUser user = new VCarUser();
					user.setVersion(string);
					user.setV_vid(vlists.get(vvi++));
					vlistu.add(user);
				}					
				request.setAttribute("vlist", vlistu);

				request.getRequestDispatcher("/Vive/vmylist.jsp").forward(request, response);
				//request.getRequestDispatcher("vlibrary.jsp").forward(request, response);

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
