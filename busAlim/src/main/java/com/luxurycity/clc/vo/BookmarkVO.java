package com.luxurycity.clc.vo;

import com.luxurycity.clc.util.*;
import java.sql.Date;
import java.sql.Time;
import java.text.*;
public class BookmarkVO {
	private int bmno, mno, route_id, station_id, route_cd, bdistrict_cd, sdistrict_cd, area;
	private String sid, station_nm, mobile_no, route_nm, ed_station_nm, region, route_tp, id, sdate;
	private PageUtil page;
	private Date adddate;
	private Time addtime;
	
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public void setSdate() {
		SimpleDateFormat form1 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat form2 = new SimpleDateFormat("HH:mm:ss");
		sdate = form1.format(adddate) + " " + form2.format(addtime);
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public Date getAdddate() {
		return adddate;
	}
	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}
	public Time getAddtime() {
		return addtime;
	}
	public void setAddtime(Time addtime) {
		this.addtime = addtime;
		setSdate();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PageUtil getPage() {
		return page;
	}
	public void setPage(PageUtil page) {
		this.page = page;
	}
	public String getRoute_tp() {
		return route_tp;
	}
	public void setRoute_tp(String route_tp) {
		this.route_tp = route_tp;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getEd_station_nm() {
		return ed_station_nm;
	}
	public void setEd_station_nm(String ed_station_nm) {
		this.ed_station_nm = ed_station_nm;
	}
	public int getBmno() {
		return bmno;
	}
	public void setBmno(int bmno) {
		this.bmno = bmno;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public int getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}
	public int getStation_id() {
		return station_id;
	}
	public void setStation_id(int station_id) {
		this.station_id = station_id;
	}
	public int getRoute_cd() {
		return route_cd;
	}
	public void setRoute_cd(int route_cd) {
		this.route_cd = route_cd;
	}
	public int getBdistrict_cd() {
		return bdistrict_cd;
	}
	public void setBdistrict_cd(int bdistrict_cd) {
		this.bdistrict_cd = bdistrict_cd;
	}
	public int getSdistrict_cd() {
		return sdistrict_cd;
	}
	public void setSdistrict_cd(int sdistrict_cd) {
		this.sdistrict_cd = sdistrict_cd;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getStation_nm() {
		return station_nm;
	}
	public void setStation_nm(String station_nm) {
		this.station_nm = station_nm;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getRoute_nm() {
		return route_nm;
	}
	public void setRoute_nm(String route_nm) {
		this.route_nm = route_nm;
	}
	
}
