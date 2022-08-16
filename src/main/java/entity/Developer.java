package entity;

import java.util.List;

public class Developer 
{
	private int atrid;
	private String atrnm;
	private String atrdes;
	private double atrcrd;
	private List<Integer> Wid;
	private String Wids;
	public Developer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Developer(int atrid, String atrnm, String atrdes, 
	double atrcrd, List<Integer> Wid) {
		super();
		this.atrid = atrid;
		this.atrnm = atrnm;
		this.atrdes = atrdes;
		this.atrcrd = atrcrd;
		this.Wid = Wid;
	}
	
	public Developer(int atrid, String Wids, String atrnm, double atrcrd) {
		super();
		this.atrid = atrid;
		this.Wids = Wids;
		this.atrnm = atrnm;
		this.atrcrd = atrcrd;
	}
	
	public String getWids() {
		return Wids;
	}
	public void setWids(int atrid) {
		this.atrid = atrid;
	}
	
	public List<Integer> getWid() {
		return Wid;
	}
	public void setWid(List<Integer> Wid) {
		this.Wid = Wid;
	}
	
	public int getAtrid() {
		return atrid;
	}
	public void setAtrid(int atrid) {
		this.atrid = atrid;
	}
	public String getAtrnm() {
		return atrnm;
	}
	public void setAtrnm(String atrnm) {
		this.atrnm = atrnm;
	}
	public String getAtrdes() {
		return atrdes;
	}
	public void setAtrdes(String atrdes) {
		this.atrdes = atrdes;
	}
	public double getAtrcrd() {
		return atrcrd;
	}
	public void setAtrcrd(double atrcrd) {
		this.atrcrd = atrcrd;
	}
	
	public static Developer getDpUsedID(int id)
	{
		Developer d = new Developer();
		for(Developer dp : Sets.Dpr)
		{
			if(dp.getAtrid() == id)
			{
				d = dp;
				break;
			}
		}
		return d;
	}
	public static Developer getDpUsedNM(String nm)
	{
		Developer d = new Developer();
		for(Developer dp : Sets.Dpr)
		{
			if(dp.getAtrnm().equals(nm))
			{
				d = dp;
				break;
			}
		}
		return d;
	}
	

}
