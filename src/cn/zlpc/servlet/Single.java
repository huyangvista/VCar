package cn.zlpc.servlet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.fabric.xmlrpc.base.Data;

import cn.zlpc.dao.impl.AuctionDaoImpl;
import cn.zlpc.exception.ErrorException;
import cn.zlpc.po.User;
import cn.zlpc.service.SingleService;

/**
 * Servlet implementation class Single
 */
@WebServlet("/Single")
public class Single extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Single()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		List<String> info = new ArrayList<String>();
		// 获得表单中的元素
		List<Object> beans = (List<Object>) request.getAttribute("beans");
		// 接收完成后移除
		request.removeAttribute("beans");
		String[] operate = request.getParameter("operate").split("\\.");
		SingleService service = new SingleService(beans);
		try
		{
			//service.execute(operate[1]);
		}
		catch (ErrorException e)
		{
			info.add(e.getMessage());
		}
		if (info.size() == 0)
		{
			info.add("恭喜您,操作成功!");
		}

		String dispatcherPath = null;
		if (operate[1].equalsIgnoreCase("add"))
		{
			dispatcherPath = "backstage/" + operate[0] + "/" + request.getParameter("operate").replace(".", "_") + ".jsp";
		}
		else
		{
			dispatcherPath = "QueryServlet?view=" + operate[0];
		}

		switch (operate[1])
		{
			case "add":
				//添加公告
				String vstitle = new String(request.getParameter("title").getBytes("ISO-8859-1"),"UTF-8");
				String vstext = new String(request.getParameter("content").getBytes("ISO-8859-1"),"UTF-8");
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
				String hehe = dateFormat.format(now);
				new AuctionDaoImpl().vexeUpdate(String.format("INSERT INTO `t_notice` (`content`, `title`, `n_time`) VALUES ('%s', '%s', '%s')", vstext, vstitle, hehe));

				//System.err.println(String.format("INSERT INTO `t_notice` (`content`, `title`, `n_time`) VALUES ('%s', '%s', '%s')", vstext, vstitle, hehe));
				break;
			case "delete":
				String n_id = request.getParameter("n_id");

				new AuctionDaoImpl().vexeUpdate(String.format("DELETE FROM `t_notice` WHERE (`n_id`='%s')"
						, n_id));

				break;
			case "tt":

				break;

			default:
				break;
		}
		
		request.setAttribute("info", info);
		request.getRequestDispatcher(dispatcherPath).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
