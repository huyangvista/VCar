package cn.zlpc.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zlpc.dao.impl.AuctionDaoImpl;
import cn.zlpc.exception.ErrorException;
import cn.zlpc.po.User;
import cn.zlpc.service.SingleService;
import org.apache.poi.hssf.record.PageBreakRecord;

/**
 * 用于针对由单个po组成统一提交的Servlet
 * @author Hocean
 *
 */
public class SingleServlet extends HttpServlet
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		List<String> info = new ArrayList<String>();
		// 获得表单中的元素
		List<Object> beans = (List<Object>) request.getAttribute("beans");
		// 接收完成后移除
		request.removeAttribute("beans");
		String[] operate = request.getParameter("operate").split("\\.");
		SingleService service = new SingleService(beans);
		int vcount = 0;

		
		if (operate[1].equals("add"))//如果是添加数据
		{
			User user = (User) request.getSession().getAttribute("user");
			if(user == null )
			{
                info.add("操作失败,请登录!");
				request.setAttribute("info", info);
                String dispatcherPath = "";
                if (operate[1].equalsIgnoreCase("add"))
                {
                    dispatcherPath = "backstage/" + operate[0] + "/" + request.getParameter("operate").replace(".", "_") + ".jsp";
                }
                else
                {
                    dispatcherPath = "QueryServlet?view=" + operate[0];
                }
                request.getRequestDispatcher(dispatcherPath).forward(request, response);

				return ;
			}
			String vsuid = user.getU_id();

			//SELECT count(*) FROM `v_caruser` where u_id='q'
			//String vout = new AuctionDaoImpl().vexeSql("SELECT count(*) vcount FROM `v_caruser` where u_id='" + vsuid + "'", "vcount");
			//System.out.println("\\----" + vout);
			//vcount = Integer.parseInt(vout);
//			if (vcount >= 3)
//			{
//				info.add("操作失败,每个用户最多发布3辆二手车信息!");
//				request.setAttribute("info", info);
//			}
		}
		String dispatcherPath = null;
		try
		{
			service.execute(operate[1]);
		}
		catch (ErrorException e)
		{
			info.add(e.getMessage());
		}
		if (info.size() == 0)
		{
			info.add("恭喜您,操作成功!");
		}
		
		if ( operate[1].equals("add") && vcount < 3)//如果是添加数据
		{
			if (operate[1].equalsIgnoreCase("add"))
			{
				dispatcherPath = "backstage/" + operate[0] + "/" + request.getParameter("operate").replace(".", "_") + ".jsp";
			}
			else
			{
				dispatcherPath = "QueryServlet?view=" + operate[0];
			}
			request.setAttribute("info", info);

			//添加第二个表  用户表
			String vsuid = ((User) request.getSession().getAttribute("user")).getU_id();
			new AuctionDaoImpl().vtabCarUser(vsuid);
			//System.out.println(request.getParameter("pledge") +".."+request.getParameter("regTime"));
			//添加第三个 表  购物表
			new AuctionDaoImpl().vtabShop();
		}

		if (operate[1].equalsIgnoreCase("add"))
		{
			dispatcherPath = "backstage/" + operate[0] + "/" + request.getParameter("operate").replace(".", "_") + ".jsp";
		}
		else
		{
			dispatcherPath = "QueryServlet?view=" + operate[0];
		}
		request.getRequestDispatcher(dispatcherPath).forward(request, response);
	}

}
