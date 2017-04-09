package cn.zlpc.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zlpc.dao.impl.AuctionDaoImpl;
import cn.zlpc.po.User;
import cn.zlpc.service.QueryService;
import cn.zlpc.util.ImageUtil;
import cn.zlpc.vo.Page;
import cn.zlpc.vo.PrTaNotice;
import cn.zlpc.vo.VCarUser;
import tool.mastery.db.DBUtil;
import vdll.data.msql.MySql;

/**
 * 交易大厅
 * 
 * @author Hocean
 *
 * @param <E>
 */
public class PrTaNoticeServlet<E> extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PrTaNoticeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		ImageUtil.setPath(request);
		if (request.getSession().getAttribute("user") == null) {
			//request.getRequestDispatcher("login.jsp").forward(request, response);
			
			//doPost2( request, response) ;
			//return;
		}
		String viewname = request.getParameter("view");
		Page page = new Page();
		page.setMaxSize(8);
		if (viewname.equalsIgnoreCase("PrTaNotice")) {
			// 公告显示
			QueryService queryService = new QueryService("Notice");
			List<Object> list = queryService.getResult(null, null);
			request.setAttribute("list", list);
			// 公告
			QueryService PrTaNoticeService = new QueryService(viewname);

			List<Object> PrTaNoticeList1 = PrTaNoticeService.getResult(null, page);
			List<String> imageList = null;// 存取不同车牌号下文件夹的图片
			// Map<String,List<String>> map = new HashMap();//一个车牌号对应多个图片
			List<PrTaNotice> PrTaNoticeList = new ArrayList<PrTaNotice>();
			for (int i = 0; i < PrTaNoticeList1.size(); i++) {
				PrTaNotice prtanotice = (PrTaNotice) PrTaNoticeList1.get(i);
				imageList = ImageUtil.getImage(ImageUtil.GET_PATH + prtanotice.getV_id() + "\\",
						ImageUtil.SHOW_PATH + prtanotice.getV_id() + "/");
				// map.put(prtanotice.getV_id(), imageList);
				if (imageList.size() != 0) {
					prtanotice.setImageName(imageList.get(0));
				} else {
					prtanotice.setImageName("img/nophoto.jpg");
				}
				PrTaNoticeList.add(prtanotice);
			}
			request.setAttribute("page", page);
			request.setAttribute("PrTaNoticeList", PrTaNoticeList);
			request.getRequestDispatcher("index1.jsp").forward(request, response);
		}
		if (viewname.equalsIgnoreCase("PrTaNoticePage")) {
			List<String> imageList = null;
			QueryService PrTaNoticeService1 = new QueryService("PrTaNotice");
			/*
			 * Page page = new Page(); page.setMaxSize(5);
			 */
			// 获得从页面中传递过来的数据
			String firstIndex = request.getParameter("firstIndex");
			if (firstIndex != null) {
				int temp = Integer.parseInt(firstIndex);
				page.setPage(temp);
			}
			// 此处是为了遍历出出售的车子
			List<String> query = new ArrayList<String>();
			query.add("all");
			query.add("all");

			List<Object> PrTaNoticeList1 = PrTaNoticeService1.getResult(query, page);
			List<PrTaNotice> PrTaNoticeList = new ArrayList<PrTaNotice>();

			MySql msq = DBUtil.msq;
			msq.exeQ("SELECT * FROM `t_userpartfinish`");
			List<Map<String, Object>> parms = msq.getParms();
			go:for (int i = 0; i < PrTaNoticeList1.size(); i++) {
				PrTaNotice prtanotice = (PrTaNotice) PrTaNoticeList1.get(i);
				imageList = ImageUtil.getImage(ImageUtil.GET_PATH + prtanotice.getV_id() + "\\",
						ImageUtil.SHOW_PATH + prtanotice.getV_id() + "/");

				for (Map<String, Object>  item : parms					 ) {
					if(item.get("v_id").equals(prtanotice.getV_id())){
						continue go;
					}
				}

				// map.put(prtanotice.getV_id(), imageList);
				if (imageList.size() != 0) {
					prtanotice.setImageName(imageList.get(0));
				} else {
					prtanotice.setImageName("img/nophoto.jpg");
				}
				PrTaNoticeList.add(prtanotice);
			}

			// 查询车主
			List<VCarUser> vlistUser = new ArrayList<VCarUser>();
			for (PrTaNotice prTaNotice : PrTaNoticeList) {
				String vsu_id = new AuctionDaoImpl().Vu_idFormv_id(prTaNotice.getV_id().toString());
				String version = new AuctionDaoImpl().vexeSql("SELECT * FROM `t_vehicle` where v_id='"+prTaNotice.getV_id().toString()+"';", "v_version");

				
				VCarUser vu = new VCarUser();
				vu.setV_uid(vsu_id);
				vu.setVersion(version);
				vlistUser.add(vu);
				//System.out.println(vsu_id);
			}
			// System.out.println();
			
			
			request.setAttribute("page", page);
			request.setAttribute("PrTaNoticeList", PrTaNoticeList);
			request.setAttribute("vlistUser", vlistUser);
			request.getRequestDispatcher("user_file/AdvanceCar/AdvanceCar.jsp").forward(request, response);
		}

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	public void doPost2(HttpServletRequest request, HttpServletResponse response) 
	{
		String viewname = request.getParameter("view");
		Page page = new Page();
		page.setMaxSize(8);
		if (viewname.equalsIgnoreCase("PrTaNotice")) {
			// 公告显示
			QueryService queryService = new QueryService("Notice");
			List<Object> list = queryService.getResult(null, null);
			request.setAttribute("list", list);
			// 公告
			QueryService PrTaNoticeService = new QueryService(viewname);

			List<Object> PrTaNoticeList1 = PrTaNoticeService.getResult(null, page);
			List<String> imageList = null;// 存取不同车牌号下文件夹的图片
			// Map<String,List<String>> map = new HashMap();//一个车牌号对应多个图片
			List<PrTaNotice> PrTaNoticeList = new ArrayList<PrTaNotice>();
			for (int i = 0; i < PrTaNoticeList1.size(); i++) {
				PrTaNotice prtanotice = (PrTaNotice) PrTaNoticeList1.get(i);
				imageList = ImageUtil.getImage(ImageUtil.GET_PATH + prtanotice.getV_id() + "\\",
						ImageUtil.SHOW_PATH + prtanotice.getV_id() + "/");
				// map.put(prtanotice.getV_id(), imageList);
				if (imageList.size() != 0) {
					prtanotice.setImageName(imageList.get(0));
				} else {
					prtanotice.setImageName("img/nophoto.jpg");
				}
				PrTaNoticeList.add(prtanotice);
			}
			request.setAttribute("page", page);
			request.setAttribute("PrTaNoticeList", PrTaNoticeList);
			try
			{
				request.getRequestDispatcher("index1.jsp").forward(request, response);
			}
			catch (ServletException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (viewname.equalsIgnoreCase("PrTaNoticePage")) {
			List<String> imageList = null;
			QueryService PrTaNoticeService1 = new QueryService("PrTaNotice");
			/*
			 * Page page = new Page(); page.setMaxSize(5);
			 */
			// 获得从页面中传递过来的数据
			String firstIndex = request.getParameter("firstIndex");
			if (firstIndex != null) {
				int temp = Integer.parseInt(firstIndex);
				page.setPage(temp);
			}
			// 此处是为了遍历出出售的车子
			List<String> query = new ArrayList<String>();
			query.add("all");
			query.add("all");

			List<Object> PrTaNoticeList1 = PrTaNoticeService1.getResult(query, page);
			List<PrTaNotice> PrTaNoticeList = new ArrayList<PrTaNotice>();
			for (int i = 0; i < PrTaNoticeList1.size(); i++) {
				PrTaNotice prtanotice = (PrTaNotice) PrTaNoticeList1.get(i);
				imageList = ImageUtil.getImage(ImageUtil.GET_PATH + prtanotice.getV_id() + "\\",
						ImageUtil.SHOW_PATH + prtanotice.getV_id() + "/");
				// map.put(prtanotice.getV_id(), imageList);
				if (imageList.size() != 0) {
					prtanotice.setImageName(imageList.get(0));
				} else {
					prtanotice.setImageName("img/nophoto.jpg");
				}
				PrTaNoticeList.add(prtanotice);
			}

			// 查询车主
			List<VCarUser> vlistUser = new ArrayList<VCarUser>();
			for (PrTaNotice prTaNotice : PrTaNoticeList) {
				String vsu_id = new AuctionDaoImpl().Vu_idFormv_id(prTaNotice.getV_id().toString());
				String version = new AuctionDaoImpl().vexeSql("SELECT * FROM `t_vehicle` where v_id='"+prTaNotice.getV_id().toString()+"';", "v_version");

				
				VCarUser vu = new VCarUser();
				vu.setV_uid(vsu_id);
				vu.setVersion(version);
				vlistUser.add(vu);
				//System.out.println(vsu_id);
			}
			// System.out.println();
			
			
			request.setAttribute("page", page);
			request.setAttribute("PrTaNoticeList", PrTaNoticeList);
			request.setAttribute("vlistUser", vlistUser);
			try
			{
				request.getRequestDispatcher("user_file/AdvanceCar/AdvanceCar.jsp").forward(request, response);
			}
			catch (ServletException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
}
