package entity;

import java.util.ArrayList;
import java.util.List;

public class Service {
	private int srcid;
	private String name;
	private String des;
	private int dpid;//作者
	private String wids;
	private double crd;
	private double ctm;
	private int dwl;//下载次数
	private int vw;//浏览次数
	private double rt;//平台评分
	private double st;//创建时间
	private double et;//当前时间
	private int cmid;
	private List<Integer> Wids;
	private List<String> nms;
	private double x;
	private double y;
	private String dsc;
	public Service() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Service(int srcid, String name, int dwl, int vw, double rt, double st) {
		super();
		this.srcid = srcid;
		this.name = name;
		this.dwl = dwl;
		this.vw = vw;
		this.rt = rt;
		this.st = st;
	}
	
	public Service(int srcid, String wids, int dpid, String nm) {
		super();
		this.srcid = srcid;
		this.wids = wids;
		this.dpid = dpid;
		this.name = nm;
	}
	
	public Service(int srcid, String wids) {
		super();
		this.srcid = srcid;
		this.wids = wids;
	}
	
	public Service(int srcid, int cmid) {
		super();
		this.srcid = srcid;
		this.cmid = cmid;
	}
	
	public Service(int srcid, List<Integer> Wids) {
		super();
		this.srcid = srcid;
		this.Wids = Wids;
	}
	
	public Service(int srcid, List<Integer> Wids, int dpid, String nm) {
		super();
		this.srcid = srcid;
		this.Wids = Wids;
		this.dpid = dpid;
		this.name = nm;
	}
	
	public Service(int srcid, int dpid, double srccrd, double ct) {
		super();
		this.srcid = srcid;
		this.dpid = dpid;
		this.crd = srccrd;
		this.st = ct;
	}
	
	//最终节点类型
	public Service(int srcid, int dpid, double srccrd, String dsc, double st, double x, double y) {
		super();
		this.srcid = srcid;
		this.dpid = dpid;
		this.crd = srccrd;
		this.des = dsc;
		this.st = st;
		this.x = x;
		this.y = y;
	}
	
	//new evaluation
	public Service(int srcid, String wids, String nm, String dsc) {
		super();
		this.srcid = srcid;
		this.wids = wids;
		this.name = nm;
		this.des = dsc;
		
	}
	public Service(int srcid, List<Integer> wids, List<String> nm, String dsc) {
		super();
		this.srcid = srcid;
		this.Wids = wids;
		this.nms = nm;
		this.des = dsc;
		
	}
	
	
	public Service(int srcid, int dpid, double srccrd, String dsc, double ct)
	{
		super();
		this.srcid = srcid;
		this.dpid = dpid;
		this.crd = srccrd;
		this.des = dsc;
		this.st = ct;
	}
	
	public Service(int srcid, String name, String des, double crd, double ctm, int dpid, List<Integer> Wids) {
		super();
		this.srcid = srcid;
		this.name = name;
		this.des = des;
		this.crd = crd;
		this.ctm = ctm;
		this.dpid = dpid;
		this.Wids = Wids;
	}
	//最新使用的类型
	public Service(int srcid, String name, String des, double crd, double ctm, int dpid, String Wids) {
		super();
		this.srcid = srcid;
		this.name = name;
		this.des = des;
		this.crd = crd;
		this.ctm = ctm;
		this.dpid = dpid;
		this.wids = Wids;
	}
	
	public Service(int srcid, String name, String des, double crd, int dpid, List<Integer> Wids) {
		super();
		this.srcid = srcid;
		this.name = name;
		this.des = des;
		this.crd = crd;
		this.dpid = dpid;
		this.Wids = Wids;
	}
	
	public Service(int srcid, String name, String des, double crd, int dpid, String Wids) {
		super();
		this.srcid = srcid;
		this.name = name;
		this.des = des;
		this.crd = crd;
		this.dpid = dpid;
		this.wids = Wids;
	}
	
	public String getwids() {
		return wids;
	}

	public void setDpid(String wids) {
		this.wids = wids;
	}
	
	public int getDpid() {
		return dpid;
	}

	public void setDpid(int dpid) {
		this.dpid = dpid;
	}
	
	public int getDwl() {
		return dwl;
	}

	public void setDwl(int dwl) {
		this.dwl = dwl;
	}

	public int getVw() {
		return vw;
	}

	public void setVw(int vw) {
		this.vw = vw;
	}

	public double getRt() {
		return rt;
	}

	public void setRt(double rt) {
		this.rt = rt;
	}

	public double getSt() {
		return st;
	}

	public void setSt(double st) {
		this.st = st;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public List<Integer> getWids() {
		return Wids;
	}

	public void setWids(List<Integer> wids) {
		Wids = wids;
	}

	public int getSrcid() {
		return srcid;
	}
	public void setSrcid(int actid) {
		this.srcid = actid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public double getCrd() {
		return crd;
	}
	public void setCrd(double crd) {
		this.crd = crd;
	}
	public double getCtm() {
		return ctm;
	}
	public void setCtm(double ctm) {
		this.ctm = ctm;
	}
	
	public static Service getSrcUsId(int id)
	{
		for(Service src:Sets.iniSrc)
		{
			if(src.getSrcid() == id)
			{
				return src;
			}
		}
		return null;
	}
	

}
