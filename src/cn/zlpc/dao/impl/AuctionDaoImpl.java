package cn.zlpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;

import tool.mastery.annotation.Util;
import tool.mastery.db.DBUtil;
import tool.mastery.exception.DBException;
import cn.zlpc.dao.MultiTableDao;
import cn.zlpc.po.User;
import cn.zlpc.util.ImageUtil;
import cn.zlpc.vo.CurrContest;
import cn.zlpc.vo.Page;
import vdll.tools.DateTime;

/**
 * 拍卖的Dao类
 * 
 * @author Hocean
 *
 */
public class AuctionDaoImpl extends MultiTableDao
{
	private  Connection conn = DBUtil.getConnection();
	private  Util util = new Util();

	/*
 * Vive New 查询 完成订单
 *
 * @param u_id
 * @param stateFlag
 * @param condition
 * @return
 * @throws DBException
 */
	public List<CurrContest> vgetAuctionVehicle4Finish(String u_id, String stateFlag, String condition) throws DBException
	{
		String sql = "SELECT p.price,p.u_id,c.tel,c.v_id,c.plateNo,c.vname,c.regTime,c.source,c.v_source,b.bidTime,b.bidEndTime,b.bidSpri,b.plusPri,b.beginAuction,b.stopAuction FROM t_userpartfinish p,t_vehicle c,v_caruser u,t_bid b where p.v_id=c.v_id && c.v_id=u.v_id  && c.v_id=b.v_id && p.u_id='" + u_id + "'";

		if (stateFlag == "-1")
			;
		if (stateFlag == "0")
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (stateFlag == "1")
		{
			sql = sql + " and state=" + stateFlag;
		}
		else
		{
			sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// ResultSet rs0 = stm.executeQuery(sql0);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				// 第二次查询
				// rs.next();
				// aucVeh.setU_name(rs0.getString("u_name"));
				aucVeh.setU_tel(rs.getString("tel"));
				aucVeh.setU_name(rs.getString("p.u_id"));

				aucVeh.setU_pledge(rs.getString("p.price"));


				// 查找用户  提供商
				// String vstel = rs.getString("tel");
				// String sql1 = String.format("SELECT u_id FROM
				// t_user where tel='%s'", vstel);
				String sql1 = String.format("SELECT u_id FROM v_caruser where v_id='%s'", rs.getInt("v_id"));
				Statement stm1 = conn.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql1);
				if (rs1.next()) ;
				//aucVeh.setU_name(rs1.getString("u_id"));

				// 查找用户  购买者
				String sql2 = String.format("SELECT tel FROM t_user where u_id='%s';", rs.getString("p.u_id"));
				Statement stm2 = conn.createStatement();
				ResultSet rs2 = stm1.executeQuery(sql2);
				if (rs2.next())
				{
					aucVeh.setU_tel(rs2.getString("tel"));
					//System.out.println(rs2.getString("u_id"));
				}

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}
				// select t_user.u_name,t_user.tel,
				// t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source
				// from t_user,v_caruser,t_userpart,t_bid where
				// t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id
				// and u_id='q' and t_user.u_id = v_caruser.v_uid and
				// v_vehicle.v_id = v_caruser.v_vid
				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	/**
	 * 以车牌号查询车辆信息；(即当前竞拍车辆)
	 * 
	 * @param v_id
	 * @return
	 * @throws DBException
	 */
	public CurrContest getCurrContest(int v_id, String u_id) throws DBException
	{

		CurrContest entity = null;
		try
		{
			entity = new CurrContest();
			Statement stm = conn.createStatement();
			String sql = "select t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidSpri,plusPri,bidTime,bidEndTime,beginAuction,stopAuction,v_source from t_vehicle ,t_bid,t_userpart where t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id and t_vehicle.v_id ='" + v_id + "' and u_id='" + u_id + "' and state =1";
			// System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			entity.ConvertResultSet(rs, entity);
			if (entity.getV_id() == null || entity.getV_id().equals(""))
			{

			}
			else
			{
				entity.setAttCou(getAttCount(v_id));
				entity.setPeCou(getPeCount(v_id));
			}
		}
		catch (SQLException e)
		{

		}
		return entity;
	}

	/**
	 * 以用户ID来查询，根据不同的标志位来查询该用户登记或者购物车的车辆；
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> getAuctionVehicleAll(String u_id, String stateFlag, String condition) throws DBException
	{
		String sql = "select t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source from t_vehicle,t_userpart,t_bid where t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id and u_id='" + u_id + "'";
		if (stateFlag != null)
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (condition != null)
		{
			sql = sql + " and attention= 1";
		}
		System.out.println("-----" + sql);
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}

				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	/**
	 * Vive New 返回 用户购物车信息
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> vgetAuctionVehicle(String u_id, String stateFlag, String condition) throws DBException
	{
		String sql = "select t_vehicle.tel,t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source from t_vehicle,t_userpart,t_bid where t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id and u_id='" + u_id + "'";
		String sql0 = String.format("select t_user.u_id,t_user.u_name,t_user.tel,t_vehicle.v_id  from   t_user,v_caruser, t_vehicle where  t_vehicle.v_id=v_caruser.v_id   and t_user.u_id=v_caruser.u_id and v_caruser.u_id='%s'", u_id);

		if (stateFlag == "0")
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (stateFlag == "1")
		{
			sql = sql + " and state=" + stateFlag;
		}
		else
		{
			sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
//			Statement stm = conn.createStatement();
//			ResultSet rs = stm.executeQuery(sql);
			ResultSet rs = DBUtil.getMsq().exeQ(sql);

			// ResultSet rs0 = stm.executeQuery(sql0);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				// 第二次查询
				// rs.next();
				// aucVeh.setU_name(rs0.getString("u_name"));
				aucVeh.setU_tel(rs.getString("tel"));

				// 查找用户
				// String vstel = rs.getString("tel");
				// String sql1 = String.format("SELECT u_id FROM
				// sectraauction.t_user where tel='%s'", vstel);

				String sql1 = String.format("SELECT u_id FROM sectraauction.v_caruser where v_id='%s'", rs.getInt("v_id"));

				Statement stm1 = conn.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql1);
				rs1.next();

				aucVeh.setU_name(rs1.getString("u_id"));

				System.out.println(rs1.getString("u_id"));

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}
				// select t_user.u_name,t_user.tel,
				// t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source
				// from t_user,v_caruser,t_userpart,t_bid where
				// t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id
				// and u_id='q' and t_user.u_id = v_caruser.v_uid and
				// v_vehicle.v_id = v_caruser.v_vid
				auctionVehicleList.add(aucVeh);
			}



		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	/**
	 * Vive New 返回订单信息
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> vgetAuctionVehicle2(String u_id, String stateFlag, String condition) throws DBException
	{
		String sql = "SELECT p.u_id,c.tel,c.v_id,c.plateNo,c.vname,c.regTime,c.source,c.v_source,b.bidTime,b.bidEndTime,b.bidSpri,b.plusPri,b.beginAuction,b.stopAuction FROM sectraauction.t_userpart p,sectraauction.t_vehicle c,sectraauction.v_caruser u,t_bid b where p.v_id=c.v_id &&c.v_id=u.v_id  && c.v_id=b.v_id && u.u_id='" + u_id + "'";

		if (stateFlag == "0")
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (stateFlag == "1")
		{
			sql = sql + " and state=" + stateFlag;
		}
		else
		{
			sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// ResultSet rs0 = stm.executeQuery(sql0);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				// 第二次查询
				// rs.next();
				// aucVeh.setU_name(rs0.getString("u_name"));
				aucVeh.setU_tel(rs.getString("tel"));
				aucVeh.setU_name(rs.getString("p.u_id"));

				// 查找用户
				// String vstel = rs.getString("tel");
				// String sql1 = String.format("SELECT u_id FROM
				// sectraauction.t_user where tel='%s'", vstel);

				String sql1 = String.format("SELECT u_id FROM sectraauction.v_caruser where v_id='%s'", rs.getInt("v_id"));

				Statement stm1 = conn.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql1);
				rs1.next();

				aucVeh.setU_name(rs1.getString("u_id"));

				System.out.println(rs1.getString("u_id"));

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}
				// select t_user.u_name,t_user.tel,
				// t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source
				// from t_user,v_caruser,t_userpart,t_bid where
				// t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id
				// and u_id='q' and t_user.u_id = v_caruser.v_uid and
				// v_vehicle.v_id = v_caruser.v_vid
				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	/**
	 * Vive New 订单提交的
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> vgetAuctionVehicle3(String u_id, String stateFlag, String condition) throws DBException
	{
		String sql = "SELECT p.u_id,c.tel,c.v_id,c.plateNo,c.vname,c.regTime,c.source,c.v_source,b.bidTime,b.bidEndTime,b.bidSpri,b.plusPri,b.beginAuction,b.stopAuction FROM sectraauction.t_userpart p,sectraauction.t_vehicle c,sectraauction.v_caruser u,t_bid b where p.v_id=c.v_id &&c.v_id=u.v_id  && c.v_id=b.v_id && p.u_id='" + u_id + "'";

		if (stateFlag == "0")
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (stateFlag == "1")
		{
			sql = sql + " and state=" + stateFlag;
		}
		else
		{
			sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// ResultSet rs0 = stm.executeQuery(sql0);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				// 第二次查询
				// rs.next();
				// aucVeh.setU_name(rs0.getString("u_name"));
				aucVeh.setU_tel(rs.getString("tel"));
				aucVeh.setU_name(rs.getString("p.u_id"));

				// 查找用户  提供商
				// String vstel = rs.getString("tel");
				// String sql1 = String.format("SELECT u_id FROM
				// sectraauction.t_user where tel='%s'", vstel);
				String sql1 = String.format("SELECT u_id FROM sectraauction.v_caruser where v_id='%s'", rs.getInt("v_id"));
				Statement stm1 = conn.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql1);
				if (rs1.next()) ;
				//aucVeh.setU_name(rs1.getString("u_id"));

				// 查找用户  购买者			
				String sql2 = String.format("SELECT tel FROM sectraauction.t_user where u_id='%s';", rs.getString("p.u_id"));
				Statement stm2 = conn.createStatement();
				ResultSet rs2 = stm1.executeQuery(sql2);
				if (rs2.next())
				{
					aucVeh.setU_tel(rs2.getString("tel"));
					//System.out.println(rs2.getString("u_id"));
				}

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}
				// select t_user.u_name,t_user.tel,
				// t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source
				// from t_user,v_caruser,t_userpart,t_bid where
				// t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id
				// and u_id='q' and t_user.u_id = v_caruser.v_uid and
				// v_vehicle.v_id = v_caruser.v_vid
				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	/**
	 * Vive New 订单提交的
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> vgetAuctionVehicle3Finish(String u_id, String stateFlag, String condition) throws DBException
	{
		String sql = "SELECT p.u_id,c.tel,c.v_id,c.plateNo,c.vname,c.regTime,c.source,c.v_source,b.bidTime,b.bidEndTime,b.bidSpri,b.plusPri,b.beginAuction,b.stopAuction FROM sectraauction.t_userpartfinish p,sectraauction.t_vehicle c,sectraauction.v_caruser u,t_bid b where p.v_id=c.v_id &&c.v_id=u.v_id  && c.v_id=b.v_id && u.u_id='" + u_id + "'";

		if (stateFlag == "0")
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (stateFlag == "1")
		{
			sql = sql + " and state=" + stateFlag;
		}
		else
		{
			sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// ResultSet rs0 = stm.executeQuery(sql0);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				// 第二次查询
				// rs.next();
				// aucVeh.setU_name(rs0.getString("u_name"));
				aucVeh.setU_tel(rs.getString("tel"));
				aucVeh.setU_name(rs.getString("p.u_id"));

				// 查找用户  提供商
				// String vstel = rs.getString("tel");
				// String sql1 = String.format("SELECT u_id FROM
				// sectraauction.t_user where tel='%s'", vstel);
				String sql1 = String.format("SELECT u_id FROM sectraauction.v_caruser where v_id='%s'", rs.getInt("v_id"));
				Statement stm1 = conn.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql1);
				if (rs1.next()) ;
				//aucVeh.setU_name(rs1.getString("u_id"));

				// 查找用户  购买者			
				String sql2 = String.format("SELECT tel FROM sectraauction.t_user where u_id='%s';", rs.getString("p.u_id"));
				Statement stm2 = conn.createStatement();
				ResultSet rs2 = stm1.executeQuery(sql2);
				if (rs2.next())
				{
					aucVeh.setU_tel(rs2.getString("tel"));
					//System.out.println(rs2.getString("u_id"));
				}

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}
				// select t_user.u_name,t_user.tel,
				// t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source
				// from t_user,v_caruser,t_userpart,t_bid where
				// t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id
				// and u_id='q' and t_user.u_id = v_caruser.v_uid and
				// v_vehicle.v_id = v_caruser.v_vid
				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	/**
	 * defunt
	 * 以用户ID来查询，根据不同的标志位来查询该用户登记或者购物车的车辆；
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> getAuctionVehicle(String u_id, int stateFlag, String condition) throws DBException
	{
		String sql = "select t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source from t_vehicle,t_userpart,t_bid where t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id and u_id='" + u_id + "'";
		if (condition.equals("state"))
			sql = sql + " and state=" + stateFlag;
		else if (condition.equals("attention"))
		{
			sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}

				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	/**
	 * Vive 以用户ID来查询，根据不同的标志位来查询该用户登记或者购物车的车辆；
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> getAuctionVehicleAll(String vsuser) throws DBException
	{
		String sql = "select t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source from t_vehicle,t_userpart,t_bid where t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id and u_id='" + vsuser + "'";// and u_id='" + u_id + "'";

		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));
				// Vive Add

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}

				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	// 获取总价
	public int vgetSum(String vsuser) throws DBException
	{
		String sql = "select  sum() t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source from t_vehicle,t_userpart,t_bid where t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id and u_id='" + vsuser + "'";// and u_id='" + u_id + "'";

		int vsum = 0;
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next())
			{
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return vsum;
	}

	/**
	 * 以车牌号查询车辆的购物车人数
	 * 
	 * @param v_id
	 * @return
	 */
	public int getAttCount(int v_id)
	{
		int attCou = 0;
		try
		{
			String sql = "select count(*) as count from t_userpart where v_id=" + v_id + " and attention = 1";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next())
			{
				attCou = rs.getInt("count");
			}
		}
		catch (SQLException e)
		{

		}
		return attCou;
	}

	/**
	 * 以车辆编号来查询登记车辆的人数
	 * 
	 * @param v_id
	 * @return
	 */
	public int getPeCount(int v_id)
	{
		int PeCou = 0;
		try
		{
			String sql = "select count(*) as count from t_userpart where v_id=" + v_id + " and state = 1";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next())
			{
				PeCou = rs.getInt("count");
			}
		}
		catch (SQLException e)
		{

		}
		return PeCou;
	}

	public Boolean saveForAuction(String u_id, int v_id, String price)
	{

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String updatesql = "update t_userpart set price ='" + price + "', state=2, currentTime='" + format.format(new Date()) + "' where u_id='" + u_id + "' and v_id=" + v_id + "";
		// System.out.println(updatesql);
		try
		{
			PreparedStatement pstm = conn.prepareStatement(updatesql, Statement.RETURN_GENERATED_KEYS);
			conn.setAutoCommit(false);
			int saveFlag = pstm.executeUpdate(updatesql);
			conn.commit();

			if (saveFlag == 0)
			{
				return false;
			}
			else
			{
				deleteForAuctionPart(v_id);
				updateForBidFlag(v_id);
				return true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void updateForBidFlag(int v_id)
	{
		String updateSql = "update  t_bid set beginAuction = 0 ,stopAuction = 1 where  v_id=" + v_id;
		try
		{
			PreparedStatement pstm = conn.prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS);
			conn.setAutoCommit(false);
			pstm.executeUpdate(updateSql);
			conn.commit();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void deleteForAuctionPart(int v_id)
	{
		String deleteSql = "delete from t_userpart where state = 1 and v_id=" + v_id;
		try
		{
			PreparedStatement pstm = conn.prepareStatement(deleteSql, Statement.RETURN_GENERATED_KEYS);
			conn.setAutoCommit(false);
			pstm.executeUpdate(deleteSql);
			conn.commit();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<Object> listVo(Page page, String condition, String value) throws DBException
	{
		// TODO Auto-generated method stub
		return null;
	}

	// 订单状态设定
	public void vsetIndent(String vsuser, String vstate, String vstateWhere)
	{

		String sql = String.format("update sectraauction.t_userpart set state='%s' where u_id='%s' and (state='%s' or state is null)", vstate, vsuser, vstateWhere);

		try
		{
			Statement stm = conn.createStatement();
			int vi = stm.executeUpdate(sql);

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	// DELETE FROM `sectraauction`.`t_userpart` WHERE `u_id`='q' and`v_id`='2'
	// 订单取消  订单  Finish
	public void vdelIndentFinish(String vsuser, String vid)
	{
		// update sectraauction.t_userpart set state='1',state='6' where
		// u_id='q'; /// or state is null

		// List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{

			String sql = String.format("DELETE FROM `sectraauction`.`t_userpartfinish` WHERE `u_id`='%s' and`v_id`='%s'", vsuser, vid);
			// String sql = "update sectraauction.t_userpart set state='1' where
			// u_id='q'";
			System.out.println("delete " + sql);

			Statement stm = conn.createStatement();
			int vi = stm.executeUpdate(sql);
			// System.out.println(vsuser);

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	// 订单取消 购物车
	public void vdelIndent(String vsuser, String vid)
	{
		// update sectraauction.t_userpart set state='1',state='6' where
		// u_id='q'; /// or state is null

		// List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{

			String sql = String.format("DELETE FROM `sectraauction`.`t_userpart` WHERE `u_id`='%s' and`v_id`='%s'", vsuser, vid);
			// String sql = "update sectraauction.t_userpart set state='1' where
			// u_id='q'";
			System.out.println("delete " + sql);

			Statement stm = conn.createStatement();
			int vi = stm.executeUpdate(sql);
			// System.out.println(vsuser);

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	//订单修改 已完成的
	public void vupdateIndent(String vsuser, String vid)
	{
		try
		{

			String sql = String.format("INSERT INTO t_userpartfinish SELECT * FROM t_userpart WHERE u_id='%s' && v_id='%s'", vsuser, vid);
			// String sql = "update sectraauction.t_userpart set state='1' where
			// u_id='q'";
			System.out.println("delete " + sql);

			Statement stm = conn.createStatement();
			int vi = stm.executeUpdate(sql);
			// System.out.println(vsuser);

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	// 返回车辆表最大ID v_id
	public int vCountCar()
	{
		int count = 0;
		try
		{
			String sql = "SELECT v_id FROM sectraauction.t_vehicle order by v_id desc limit 1;";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next())
			{
				count = rs.getInt("v_id");
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return count;
	}

	// 插入 caruser 表
	public int vtabCarUser(String u_id)
	{
		int PeCou = 0;
		try
		{
			String sql = "SELECT v_id FROM sectraauction.t_vehicle order by v_id desc limit 1;";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next())
			{
				PeCou = rs.getInt("v_id");
			}
			System.out.println(u_id + PeCou);
			sql = String.format("insert INTO sectraauction.v_caruser (u_id,v_id) values('%s','%s')", u_id, "" + PeCou);
			int vi = stm.executeUpdate(sql);

		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return PeCou;
	}

	// 添加到购物表
	public int vtabShop()
	{
		int PeCou = 0; // 车辆表 最大ID
		String pledge = "";
		try
		{
			String sql = "SELECT v_id,pledge FROM sectraauction.t_vehicle order by v_id desc limit 1;";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next())
			{
				PeCou = rs.getInt("v_id");
				pledge = rs.getString("pledge");
			}
			DateTime dt = new DateTime();
			String st = dt.format();
			dt.addYear(1);
			String et = dt.format();
			sql = String.format("insert INTO sectraauction.t_bid (bidTime,bidEndTime,bidSpri,v_id) values('%s','%s','%s','%s')", st, et, pledge, "" + PeCou);
			//System.err.println("----" + sql);
			int vi = stm.executeUpdate(sql);

			
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return PeCou;
	}

	public String Vu_idFormv_id(String v_id)
	{

		try
		{
			String sql = String.format("SELECT u_id FROM sectraauction.v_caruser where v_id='%s'", v_id);

			Statement stm1 = conn.createStatement();
			ResultSet rs1 = stm1.executeQuery(sql);
			rs1.next();

			return rs1.getString("u_id");
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		return "";

	}

	public String vexeSql(String sql, String row)
	{

		try
		{
			//String sql = String.format("SELECT u_id FROM sectraauction.v_caruser where v_id='%s'", v_id);

			Statement stm1 = conn.createStatement();
			ResultSet rs1 = stm1.executeQuery(sql);
			rs1.next();

			return rs1.getString(row);
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		return "";

	}

	public List<String> vexeSqlList(String sql, String row)
	{
		try
		{
			//String sql = String.format("SELECT u_id FROM v_caruser where v_id='%s'", v_id);

			Statement stm1 = conn.createStatement();
			ResultSet rs1 = stm1.executeQuery(sql);
			List<String> vlist = new ArrayList<>();
			while (rs1.next())
			{
				vlist.add(rs1.getString(row));
			}
			return vlist;
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		return null;

	}

	public int vexeUpdate(String sql)
	{

		try
		{
			//String sql = String.format("SELECT row FROM `%s` where",tab ,row);

			Statement stm1 = conn.createStatement();
			int rs1 = stm1.executeUpdate(sql);

			return rs1;
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		return -1;

	}

	/*
	 * Vive New 订单提交的
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> vgetAuctionVehicle3Admin(String stateFlag, String condition) throws DBException
	{
		String sql = "SELECT p.u_id,c.tel,c.v_id,c.plateNo,c.vname,c.regTime,c.source,c.v_source,b.bidTime,b.bidEndTime,b.bidSpri,b.plusPri,b.beginAuction,b.stopAuction FROM sectraauction.t_userpart p,sectraauction.t_vehicle c,sectraauction.v_caruser u,t_bid b where p.v_id=c.v_id &&c.v_id=u.v_id  && c.v_id=b.v_id ";

		if (stateFlag == "0")
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (stateFlag == "1")
		{
			sql = sql + " and state=" + stateFlag;
		}
		else
		{
			sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// ResultSet rs0 = stm.executeQuery(sql0);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				// 第二次查询
				// rs.next();
				// aucVeh.setU_name(rs0.getString("u_name"));
				aucVeh.setU_tel(rs.getString("tel"));
				aucVeh.setU_name(rs.getString("p.u_id"));

				// 查找用户  提供商
				// String vstel = rs.getString("tel");
				// String sql1 = String.format("SELECT u_id FROM
				// sectraauction.t_user where tel='%s'", vstel);
				String sql1 = String.format("SELECT u_id FROM sectraauction.v_caruser where v_id='%s'", rs.getInt("v_id"));
				Statement stm1 = conn.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql1);
				if (rs1.next()) ;
				//aucVeh.setU_name(rs1.getString("u_id"));

				// 查找用户  购买者			
				String sql2 = String.format("SELECT tel FROM sectraauction.t_user where u_id='%s';", rs.getString("p.u_id"));
				Statement stm2 = conn.createStatement();
				ResultSet rs2 = stm1.executeQuery(sql2);
				if (rs2.next())
				{
					aucVeh.setU_tel(rs2.getString("tel"));
					//System.out.println(rs2.getString("u_id"));
				}

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}
				// select t_user.u_name,t_user.tel,
				// t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source
				// from t_user,v_caruser,t_userpart,t_bid where
				// t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id
				// and u_id='q' and t_user.u_id = v_caruser.v_uid and
				// v_vehicle.v_id = v_caruser.v_vid
				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	/**
	 * Vive New 订单提交的
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> vgetAuctionVehicle3FinishAdmin(String stateFlag, String condition) throws DBException
	{
		String sql = "SELECT p.u_id,c.tel,c.v_id,c.plateNo,c.vname,c.regTime,c.source,c.v_source,b.bidTime,b.bidEndTime,b.bidSpri,b.plusPri,b.beginAuction,b.stopAuction FROM sectraauction.t_userpartfinish p,sectraauction.t_vehicle c,sectraauction.v_caruser u,t_bid b where p.v_id=c.v_id &&c.v_id=u.v_id  && c.v_id=b.v_id ";

		if (stateFlag == "0")
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (stateFlag == "1")
		{
			sql = sql + " and state=" + stateFlag;
		}
		else
		{
			sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// ResultSet rs0 = stm.executeQuery(sql0);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				// 第二次查询
				// rs.next();
				// aucVeh.setU_name(rs0.getString("u_name"));
				aucVeh.setU_tel(rs.getString("tel"));
				aucVeh.setU_name(rs.getString("p.u_id"));

				// 查找用户  提供商
				// String vstel = rs.getString("tel");
				// String sql1 = String.format("SELECT u_id FROM
				// sectraauction.t_user where tel='%s'", vstel);
				String sql1 = String.format("SELECT u_id FROM sectraauction.v_caruser where v_id='%s'", rs.getInt("v_id"));
				Statement stm1 = conn.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql1);
				if (rs1.next()) ;
				//aucVeh.setU_name(rs1.getString("u_id"));

				// 查找用户  购买者			
				String sql2 = String.format("SELECT tel FROM sectraauction.t_user where u_id='%s';", rs.getString("p.u_id"));
				Statement stm2 = conn.createStatement();
				ResultSet rs2 = stm1.executeQuery(sql2);
				if (rs2.next())
				{
					aucVeh.setU_tel(rs2.getString("tel"));
					//System.out.println(rs2.getString("u_id"));
				}

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}
				// select t_user.u_name,t_user.tel,
				// t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source
				// from t_user,v_caruser,t_userpart,t_bid where
				// t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id
				// and u_id='q' and t_user.u_id = v_caruser.v_uid and
				// v_vehicle.v_id = v_caruser.v_vid
				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	public List<CurrContest> vgetAuctionVehicle3FinishUser(String stateFlag, String condition) throws DBException
	{
		String sql = "SELECT p.u_id,c.tel,c.v_id,c.plateNo,c.vname,c.regTime,c.source,c.v_source,b.bidTime,b.bidEndTime,b.bidSpri,b.plusPri,b.beginAuction,b.stopAuction FROM t_userpartfinish p,t_vehicle c,v_caruser u,t_bid b where p.v_id=c.v_id &&c.v_id=u.v_id  && c.v_id=b.v_id ";

		if (stateFlag == "0")
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (stateFlag == "1")
		{
			sql = sql + " and state=" + stateFlag + " and p.u_id='" + condition + "'";
		}
		else
		{
			sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// ResultSet rs0 = stm.executeQuery(sql0);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				// 第二次查询
				// rs.next();
				// aucVeh.setU_name(rs0.getString("u_name"));
				aucVeh.setU_tel(rs.getString("tel"));
				aucVeh.setU_name(rs.getString("p.u_id"));

				// 查找用户  提供商
				// String vstel = rs.getString("tel");
				// String sql1 = String.format("SELECT u_id FROM
				// t_user where tel='%s'", vstel);
				String sql1 = String.format("SELECT u_id FROM v_caruser where v_id='%s'", rs.getInt("v_id"));
				Statement stm1 = conn.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql1);
				if (rs1.next()) ;
				//aucVeh.setU_name(rs1.getString("u_id"));

				// 查找用户  购买者			
				String sql2 = String.format("SELECT tel FROM t_user where u_id='%s';", rs.getString("p.u_id"));
				Statement stm2 = conn.createStatement();
				ResultSet rs2 = stm1.executeQuery(sql2);
				if (rs2.next())
				{
					aucVeh.setU_tel(rs2.getString("tel"));
					//System.out.println(rs2.getString("u_id"));
				}

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}
				// select t_user.u_name,t_user.tel,
				// t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source
				// from t_user,v_caruser,t_userpart,t_bid where
				// t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id
				// and u_id='q' and t_user.u_id = v_caruser.v_uid and
				// v_vehicle.v_id = v_caruser.v_vid
				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	
	
	
	/**
	 * Vive New 返回 我的  用户购物车信息
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> vgetAuctionVehicleMy(String u_id, String stateFlag, String condition) throws DBException
	{
		String sql = "select state,t_vehicle.tel,t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source from t_vehicle,t_userpart,t_bid where t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id and u_id='" + u_id + "'";
		String sql0 = String.format("select t_user.u_id,t_user.u_name,t_user.tel,t_vehicle.v_id  from   t_user,v_caruser, t_vehicle where  t_vehicle.v_id=v_caruser.v_id   and t_user.u_id=v_caruser.u_id and v_caruser.u_id='%s'", u_id);

		if (stateFlag == "0")
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (stateFlag == "1")
		{
			sql = sql + " and state=" + stateFlag;
		}
		else
		{
			sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// ResultSet rs0 = stm.executeQuery(sql0);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				aucVeh.setBidSpri(rs.getInt("bidSpri"));
				aucVeh.setPlusPri(rs.getInt("plusPri"));
				aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));

				//添加车辆状态
				String vs = rs.getString("state");
				aucVeh.setVsState(vs==null?"0":vs);
				//System.err.println(vs);
				// 第二次查询
				// rs.next();
				// aucVeh.setU_name(rs0.getString("u_name"));
				aucVeh.setU_tel(rs.getString("tel"));

				// 查找用户
				// String vstel = rs.getString("tel");
				// String sql1 = String.format("SELECT u_id FROM
				// sectraauction.t_user where tel='%s'", vstel);

				String sql1 = String.format("SELECT u_id FROM sectraauction.v_caruser where v_id='%s'", rs.getInt("v_id"));

				Statement stm1 = conn.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql1);
				rs1.next();

				aucVeh.setU_name(rs1.getString("u_id"));

				System.out.println(rs1.getString("u_id"));

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}
				// select t_user.u_name,t_user.tel,
				// t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source
				// from t_user,v_caruser,t_userpart,t_bid where
				// t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id
				// and u_id='q' and t_user.u_id = v_caruser.v_uid and
				// v_vehicle.v_id = v_caruser.v_vid
				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}

	/**
	 * Vive New 返回 我的  用户  登记信息
	 * 
	 * @param u_id
	 * @param stateFlag
	 * @param condition
	 * @return
	 * @throws DBException
	 */
	public List<CurrContest> vgetAuctionVehicleMyAction(String u_id, String stateFlag, String condition) throws DBException
	{
		String sql = "SELECT	* FROM	t_vehicle c,	v_caruser u WHERE	 u.v_id = c.v_id  AND u.u_id = '" + u_id + "'";
		//String sql0 = String.format("select t_user.u_id,t_user.u_name,t_user.tel,t_vehicle.v_id  from   t_user,v_caruser, t_vehicle where  t_vehicle.v_id=v_caruser.v_id   and t_user.u_id=v_caruser.u_id and v_caruser.u_id='%s'", u_id);

		if (stateFlag == "0")
			sql = sql + " and (state=" + stateFlag + " or state is null)";
		else if (stateFlag == "1")
		{
			sql = sql + " and state=" + stateFlag;
		}
		else
		{
			//sql = sql + " and attention= 1";
		}
		List<CurrContest> auctionVehicleList = new ArrayList<CurrContest>();
		try
		{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// ResultSet rs0 = stm.executeQuery(sql0);
			while (rs.next())
			{
				CurrContest aucVeh = new CurrContest();
				aucVeh.setV_id(rs.getInt("v_id"));
				aucVeh.setPlateNo(rs.getString("plateNo"));
				System.err.println(rs.getString("plateNo"));
				aucVeh.setVname(rs.getString("vname"));
				aucVeh.setRegTime(rs.getDate("regTime"));
				//aucVeh.setBidSpri(rs.getInt("bidSpri"));
				//aucVeh.setPlusPri(rs.getInt("plusPri"));
				//aucVeh.setBidTime(util.transferStringToDate(String.valueOf(rs.getObject("bidTime"))));
				//aucVeh.setBidEndTime(util.transferStringToDate(String.valueOf(rs.getObject("bidEndTime"))));
				//aucVeh.setBeginAuction(rs.getInt("beginAuction"));
				//aucVeh.setStopAuction(rs.getInt("stopAuction"));
				aucVeh.setSource(rs.getString("source"));
				aucVeh.setV_source(rs.getString("v_source"));
				aucVeh.setAttCou(getAttCount(rs.getInt("v_id")));
				aucVeh.setPeCou(getPeCount(rs.getInt("v_id")));
				 aucVeh.setU_name(rs.getString("v_version"));
				 aucVeh.setU_tel(rs.getString("tel"));
				
/*
				//添加车辆状态
				String vs = rs.getString("state");
				aucVeh.setVsState(vs==null?"0":vs);
				//System.err.println(vs);
				// 第二次查询
				// rs.next();
				// aucVeh.setU_name(rs0.getString("u_name"));
				aucVeh.setU_tel(rs.getString("tel"));

				// 查找用户
				// String vstel = rs.getString("tel");
				// String sql1 = String.format("SELECT u_id FROM
				// sectraauction.t_user where tel='%s'", vstel);

				String sql1 = String.format("SELECT u_id FROM sectraauction.v_caruser where v_id='%s'", rs.getInt("v_id"));

				Statement stm1 = conn.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql1);
				rs1.next();

				aucVeh.setU_name(rs1.getString("u_id"));

				System.out.println(rs1.getString("u_id"));

				List<String> imageList = ImageUtil.getImage(ImageUtil.GET_PATH + rs.getString("v_id") + "\\", ImageUtil.SHOW_PATH + rs.getString("v_id") + "/");
				if (imageList.size() != 0)
				{
					aucVeh.setImagePath(imageList.get(0));
				}
				else
				{
					aucVeh.setImagePath("img/nophoto.jpg");
				}
				// select t_user.u_name,t_user.tel,
				// t_vehicle.v_id,t_vehicle.plateNo,vname,regTime,source,bidTime,bidEndTime,bidSpri,plusPri,beginAuction,stopAuction,v_source
				// from t_user,v_caruser,t_userpart,t_bid where
				// t_vehicle.v_id=t_bid.v_id and t_vehicle.v_id=t_userpart.v_id
				// and u_id='q' and t_user.u_id = v_caruser.v_uid and
				// v_vehicle.v_id = v_caruser.v_vid*/
				auctionVehicleList.add(aucVeh);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auctionVehicleList;
	}
}
