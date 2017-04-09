package vive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.mysql.fabric.xmlrpc.base.Array;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import cn.zlpc.dao.impl.AuctionDaoImpl;
import cn.zlpc.po.User;
import cn.zlpc.vo.PrTaNotice;
import cn.zlpc.vo.VCarUser;

public class VScreen
{
	public String vname = "";
	public String vtype = "";
	public String vmin = "";
	public String vmax = "";

	HttpServletRequest request;

	public VScreen(HttpServletRequest request)
	{
		// TODO Auto-generated constructor stub
		this.request = request;
	}

	//搜索
	public void vscreen()
	{
		List<PrTaNotice> PrTaNoticeList = (List<PrTaNotice>) request.getAttribute("PrTaNoticeList");
		List<VCarUser> vlistUser = (List<VCarUser>) request.getAttribute("vlistUser");
		vname = request.getParameter("vname");
		vtype = request.getParameter("vtype");
		vmin = request.getParameter("vmin");
		vmax = request.getParameter("vmax");
		//System.out.print("::" + vname);
		if (vname != null)
		{
			if (vname != "") for (int i = PrTaNoticeList.size() - 1; i >= 0; i--)
			{
				if (PrTaNoticeList.get(i).getVname().indexOf(vname) < 0)
				{
					PrTaNoticeList.remove(i);
					vlistUser.remove(i);
				}
			}

			if (vtype != "") for (int i = PrTaNoticeList.size() - 1; i >= 0; i--)
			{
				String version = vlistUser.get(i).getVersion();
				if (version == null || version.indexOf(vtype) < 0)
				{
					//System.out.print("::" + vlistUser.get(i).getVersion());

					PrTaNoticeList.remove(i);
					vlistUser.remove(i);
				}
			}

			if (vmin != "" || vmax != "") for (int i = PrTaNoticeList.size() - 1; i >= 0; i--)
			{
				int vimin = vmin == "" ? 0 : Integer.parseInt(vmin);
				int vimax = vmax == "" ? Integer.MAX_VALUE : Integer.parseInt(vmax);
				int vispri = PrTaNoticeList.get(i).getBidSpri();
				//System.out.print("::" + vispri);
				//System.out.print("~~~" + vimin);
				//System.out.print("---" + vimax);

				if (vispri >= vimin && vispri <= vimax)
				{

				}
				else
				{
					PrTaNoticeList.remove(i);
					vlistUser.remove(i);
				}
			}
		}
		
		
		
	}

	//在自己的订单
	public void vrepe()
	{
		List<PrTaNotice> PrTaNoticeList = (List<PrTaNotice>) request.getAttribute("PrTaNoticeList");
		List<VCarUser> vlistUser = (List<VCarUser>) request.getAttribute("vlistUser");
		if (request.getSession().getAttribute("user") == null ) return;
		String vuser = ((User) request.getSession().getAttribute("user")).getU_id();
		
		
		for (int i = PrTaNoticeList.size() - 1; i >= 0; i--)
		{
			String sql = String.format("SELECT * FROM `t_userpart` where u_id='%s' && v_id='%s';", vuser, PrTaNoticeList.get(i).getV_id());
			String vString = new AuctionDaoImpl().vexeSql(sql, "u_id");

			//System.out.println("\\" + sql);
			if (vString != "")
			{
				PrTaNoticeList.remove(i);
				vlistUser.remove(i);
			}

			//System.out.println(vac);
		}
	}

	//已经售出的订单
	public void vrepeSell()
	{
		List<PrTaNotice> PrTaNoticeList = (List<PrTaNotice>) request.getAttribute("PrTaNoticeList");
		List<VCarUser> vlistUser = (List<VCarUser>) request.getAttribute("vlistUser");
		if (request.getSession().getAttribute("user") == null ) return;

		String vuser = ((User) request.getSession().getAttribute("user")).getU_id();

		for (int i = PrTaNoticeList.size() - 1; i >= 0; i--)
		{
			String sql = String.format("SELECT * FROM `t_userpartfinish` where  v_id='%s';", PrTaNoticeList.get(i).getV_id());
			String vString = new AuctionDaoImpl().vexeSql(sql, "v_id");

			//System.out.println("\\" + sql);
			if (vString != "")
			{
				PrTaNoticeList.remove(i);
				vlistUser.remove(i);
			}

			//System.out.println(vac);
		}
	}

	//已经售出的订单
	public void vdesc()
	{
		List<PrTaNotice> PrTaNoticeList = (List<PrTaNotice>) request.getAttribute("PrTaNoticeList");
		List<VCarUser> vlistUser = (List<VCarUser>) request.getAttribute("vlistUser");
		//java.util.Collections.reverse(PrTaNoticeList);
		//java.util.Collections.reverse(vlistUser);		
		VReorder(PrTaNoticeList, vlistUser);
	}
	
	public int vcount()
	{
		List<PrTaNotice> PrTaNoticeList = (List<PrTaNotice>) request.getAttribute("PrTaNoticeList");
		return PrTaNoticeList.size();
	}

	
	public void VReorder(List<PrTaNotice> PrTaNoticeList, List<VCarUser> vlistUser)
	{
		for (int i = 0; i < PrTaNoticeList.size(); i++)
		{
			for (int j = PrTaNoticeList.size() - 1; j > i; j--)
			{
				Integer vi = PrTaNoticeList.get(j).getV_id();
				Integer vi2 = PrTaNoticeList.get(j-1).getV_id();
				if(vi > vi2)
				{
					PrTaNotice item = PrTaNoticeList.get(j);
					PrTaNoticeList.set(j, PrTaNoticeList.get(j-1));
					PrTaNoticeList.set(j-1, item);
					VCarUser itemu = vlistUser.get(j);
					vlistUser.set(j, vlistUser.get(j-1));
					vlistUser.set(j-1, itemu);
				}				
			}
		}
	}
	
	
}
