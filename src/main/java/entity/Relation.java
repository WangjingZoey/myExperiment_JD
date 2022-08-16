package entity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nlp.lda.com.FileUtil;


public class Relation{
	
	private int sourceId;
	private int sinkId;
	private double weight = 1;//由正负规则的综合影响决定
	private int category = 0;//0friend,1invoke,2develop
	private double rlcrd;
	

	public Relation(int sourceId, int sinkId, double wgt) {
		super();
		this.sourceId = sourceId;
		this.sinkId = sinkId;
		this.weight = wgt;
		
	}
	
	
	
	public Relation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSource() {
		return sourceId;
	}
	public void setSource(int source) {
		this.sourceId = source;
	}
	public int getSink() {
		return sinkId;
	}
	public void setSink(int sink) {
		this.sinkId = sink;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((sinkId == null) ? 0 : sinkId.hashCode());
//		result = prime * result + ((sourceId == null) ? 0 : sourceId.hashCode());
//		return result;
//	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relation other = (Relation) obj;
		if (sinkId != other.sinkId) {
			return false;
		} 
	    else if (sourceId != other.sourceId)
			return false;
		return true;
	}
	
	public static void partition(List<Relation> links){
		int category = 0;
		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).getCategory()==0) {
				category++;
				Set<Integer> node = new HashSet<Integer>();
				node.add(links.get(i).getSource());
				node.add(links.get(i).getSink());
				links.get(i).setCategory(category);
				for (int j = i+1; j < links.size(); j++) {
					if (node.contains(links.get(j).getSource())) {
						node.add(links.get(j).getSink());
					}else if (node.contains(links.get(j).getSink())) {
						node.add(links.get(j).getSource());
					}else {
						continue;
					}
					links.get(j).setCategory(category);
				}
			}
		}
	}
	
	public static boolean isFR(int d1, int d2)
	{
		for(Relation r: Sets.Dprl)
		{
			if(((d1==r.sinkId)&&(d2==r.sourceId))||((d1==r.sourceId)&&(d2==r.sinkId)))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isIN(int d1, int d2)
	{
		for(Relation r: Sets.Srcrl)
		{
			if(((d1==r.sinkId)&&(d2==r.sourceId))||((d1==r.sourceId)&&(d2==r.sinkId)))
			{
				return true;
			}
		}
		return false;
	}

}
