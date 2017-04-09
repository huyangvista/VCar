package cn.zlpc.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.mastery.core.CharacterUtil;
import tool.mastery.exception.DBException;
import cn.zlpc.dao.impl.AuctionDaoImpl;
import cn.zlpc.exception.ErrorException;
import cn.zlpc.po.User;
import cn.zlpc.service.QueryService;
import cn.zlpc.util.ImageUtil;
import cn.zlpc.vo.CurrContest;
import cn.zlpc.vo.Page;
import cn.zlpc.vo.SucInfor;

/**
 * 用于查询或者分页的Servlet
 * @author Hocean
 *
 */
public class QueryServlet extends HttpServlet
{

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		List<String> query = new ArrayList<String>();
		String queryCondition = request.getParameter("queryCondition");
		String queryValue = request.getParameter("queryValue");
		if (queryCondition != null && queryValue != null)
		{
			query.add(queryCondition);
			if (!CharacterUtil.isChinese(queryValue))
			{
				queryValue = CharacterUtil.transcoding(queryValue);
			}
			query.add(queryValue);
		}
		// 获得视图名称
		String viewName = request.getParameter("view");

		String param = request.getParameter("param");
		// System.out.println("viewName" + viewName);
		Page page = new Page();
		String maxSize = request.getParameter("size");
		if (maxSize == null)
		{
			page.setMaxSize(8);
		}
		else
		{
			page.setMaxSize(Integer.parseInt(maxSize));
		}

		// 获得从页面中传递过来的数据
		String firstIndex = request.getParameter("firstIndex");
		if (firstIndex == null)
		{
			session.removeAttribute("queryList");
		}
		else
		{
			int temp = Integer.parseInt(firstIndex);
			page.setPage(temp);
		}

		List<Object> list = null;
		List<Object> listFinish = null;

		//完成的订单
		if (viewName.equals("SucInforFinish"))
		{
			List<CurrContest> auctionInfoFinish = null;
			try
			{
				auctionInfoFinish = new AuctionDaoImpl().vgetAuctionVehicle3FinishAdmin("1", null); //Vive state
			}
			catch (DBException e)
			{
				e.printStackTrace();
			}
			listFinish = new ArrayList<Object>();
			for (int j = 0; j < auctionInfoFinish.size(); j++)
			{
				SucInfor sin = new SucInfor();
				sin.setV_id(auctionInfoFinish.get(j).getV_id());
				sin.setPlateNo(auctionInfoFinish.get(j).getPlateNo());
				sin.setTname(auctionInfoFinish.get(j).getU_name());
				sin.setVname(auctionInfoFinish.get(j).getVname());
				sin.setTel(auctionInfoFinish.get(j).getU_tel());
				sin.setPrice(auctionInfoFinish.get(j).getPlusPri());
				if (page.getFirstIndex() <= j && page.getFirstIndex() + page.getMaxSize() > j)
				{
					listFinish.add(sin);
				}
			}
			page.setCount(auctionInfoFinish.size());
			if (listFinish.size() > 0) request.setAttribute("listFinish", listFinish);
			request.setAttribute("page", page);
			request.getRequestDispatcher("backstage/SucInfor/SucInforFinish.jsp").forward(request, response);
			return;
		}

		//Vive 未完成订单
		if (viewName.equals("SucInfor"))
		{
			list = new ArrayList<Object>();

			List<CurrContest> auctionInfo = null;
			try
			{
				auctionInfo = new AuctionDaoImpl().vgetAuctionVehicle3Admin("1", null);
			}
			catch (DBException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //Vive state

			list.clear();
			for (int j = 0; j < auctionInfo.size(); j++)
			{
				SucInfor sin = new SucInfor();
				sin.setV_id(auctionInfo.get(j).getV_id());
				sin.setPlateNo(auctionInfo.get(j).getPlateNo());
				sin.setTname(auctionInfo.get(j).getU_name());
				sin.setVname(auctionInfo.get(j).getVname());
				sin.setTel(auctionInfo.get(j).getU_tel());
				sin.setPrice(auctionInfo.get(j).getPlusPri());
				if (page.getFirstIndex() <= j && page.getFirstIndex() + page.getMaxSize() > j)
				{
					list.add(sin);
				}
			}
			page.setCount(auctionInfo.size());
		}
		else
		{ //原来的方法
			if (session.getAttribute("queryList") != null)
			{
				List<Object> queryList = (List<Object>) session.getAttribute("queryList");

				// System.out.println("高级查询" + queryList.size());
				list = new ArrayList<Object>();
				for (int i = 0; i < queryList.size(); i++)
				{
					if (page.getFirstIndex() <= i && page.getFirstIndex() + page.getMaxSize() > i)
					{
						list.add(queryList.get(i));
					}
				}
				page.setCount(queryList.size());
			}
			else
			{
				QueryService qs = new QueryService(viewName);
				// 得到查询结果集合

				list = qs.getResult(query, page);

				if (query == null)
				{
					session.setAttribute("queryList", qs.getAllList());
				}
				else if (query.size() != 0)
				{
					session.setAttribute("queryList", qs.getAllList());
				}
			}
		}

		request.setAttribute("list", list);

		request.setAttribute("page", page);
		// 查询有数据跳转到的页面
		String dispatcherPath = "";

		if (viewName.equalsIgnoreCase("AttentionVehicle"))
		{
			dispatcherPath = "user_file/UserInfo/User_attention.jsp";
		}
		else if (viewName.equalsIgnoreCase("RegistrationVehicle"))
		{
			dispatcherPath = "user_file/UserInfo/User_enrol.jsp";
		}
		else if (viewName.equalsIgnoreCase("DealVehicle"))
		{
			dispatcherPath = "user_file/UserInfo/User_clinch.jsp";
		}
		else if (viewName.equalsIgnoreCase("VehicleVo"))
		{

			String vid = query.get(1);
			List<String> imageList = new ArrayList<String>();
			imageList = ImageUtil.getImage(ImageUtil.GET_PATH + vid + "\\", ImageUtil.SHOW_PATH + vid + "/");
			if (imageList.size() == 0)
			{
				imageList.add("img/nophoto.jpg");
			}
			request.setAttribute("image", imageList.get(0));
			request.setAttribute("imageList", imageList);
			dispatcherPath = "user_file/NetworkAuction/Vehicle.jsp";
		}
		else if (viewName.equalsIgnoreCase("User"))
		{
			dispatcherPath = "user_file/UserInfo/User_info.jsp";
		}
		else if (viewName.equalsIgnoreCase("businessBooks"))
		{
			dispatcherPath = "backstage/Vehicle/Vehicle_export.jsp";
		}
		else if (viewName.equals("Notice") && param != null)
		{
			String queen = request.getParameter("queen");
			if (queen != null)
			{
				dispatcherPath = "user_file/help/help_0" + queen + ".jsp";
			}
			else
			{
				String laws = request.getParameter("goto");
				if (laws != null)
				{
					dispatcherPath = "user_file/compyIntro/" + laws + ".jsp";
				}
				else
				{
					dispatcherPath = "user_file/help/help.jsp";
				}
			}
		}
		else
		{
			String operate = request.getParameter("operate");
			if (operate != null && operate.equals("update"))
			{ //跳到车辆信息更新页面
				dispatcherPath = "backstage/" + viewName + "/" + viewName + "_update" + ".jsp";
			}
			else
			{
				dispatcherPath = "backstage/" + viewName + "/" + viewName + ".jsp";
			}
		}

		request.getRequestDispatcher(dispatcherPath).forward(request, response);
	}

}
