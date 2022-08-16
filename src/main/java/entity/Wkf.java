package entity;

public class Wkf {
	private int wid;
	private int dpid;
	private String wnm;
	private String wdsc;
	private double wcrd;
	private double wdw;//下载次数
	private double wvw;//浏览次数
	private String wst;//创建时间
	private double wct;//创建时长
	public Wkf() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Wkf(int wid, double wcrd, double wvw, String wst, double wct) {
		super();
		this.wid = wid;
		this.wcrd = wcrd;
		this.wvw = wvw;
		this.wst = wst;
		this.wct = wct;
	}
	
	public Wkf(int wid, double wcrd,double wct)
	{
		super();
		this.wid = wid;
		this.wcrd = wcrd;
		this.wct = wct;
	}
	
	public Wkf(int wid, String wdsc, double wcrd,double wct)
	{
		super();
		this.wid = wid;
		this.wdsc = wdsc;
		this.wcrd = wcrd;
		this.wct = wct;
	}

	
	public Wkf(int wid, int dpid,double wcrd,String wdsc ,double wct)
	{
		super();
		this.wid = wid;
		this.dpid = dpid;
		this.wdsc = wdsc;
		this.wcrd = wcrd;
		this.wct = wct;
	}

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}
	
	public int getWdpid() {
		return dpid;
	}

	public void setWdpid(int dpid) {
		this.dpid = dpid;
	}

	public String getWnm() {
		return wnm;
	}

	public void setWnm(String wnm) {
		this.wnm = wnm;
	}

	public String getWdsc() {
		return wdsc;
	}

	public void setWdsc(String wdsc) {
		this.wdsc = wdsc;
	}

	public double getWcrd() {
		return wcrd;
	}

	public void setWcrd(double wcrd) {
		this.wcrd = wcrd;
	}

	public double getWdw() {
		return wdw;
	}

	public void setWdw(double wdw) {
		this.wdw = wdw;
	}

	public double getWvw() {
		return wvw;
	}

	public void setWvw(double wvw) {
		this.wvw = wvw;
	}

	public String getWst() {
		return wst;
	}

	public void setWst(String wst) {
		this.wst = wst;
	}
	
	public double getWct()
	{
		return wct;
	}
	
	public void setWct(double wct)
	{
		this.wct = wct;
	}
	
	public static double getWCRDUsedId(int wid)
	{
		double crd = 0;
		for(Wkf wk: Sets.Wkfl)
		{
			if(wk.getWid() == wid)
			{
				crd = wk.getWcrd();
				break;
			}
		}
		return crd;
	}
	
	public static double getWCTUsedId(int wid)
	{
		double ct = 0;
		for(Wkf wk: Sets.Wkfl)
		{
			if(wk.getWid() == wid)
			{
				ct = wk.getWct();
				break;
			}
		}
		return ct;
	}
	
	
}
