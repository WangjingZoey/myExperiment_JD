package nlp.lda.com;
//实体的LDA向量
public class VecND {
	private double x;
	private double y;
	
	public VecND()
	{
		x = 0;
		y = 0;
	}
	
	public VecND(double _x, double _y)
	{
		x = _x;
		y = _y;
	}
	
	//获取弧度
	public double getRadian()
	{
		return Math.atan2(y, x);
	}
	
	//获取角度
	public double getAngle()
	{
		return getRadian() / Math.PI * 180;
	}
	
	public VecND clone()
	{
		return new VecND(x,y);
	}
	
	public double getLength()
	{
		return Math.sqrt(getLengthSQ());
	}
	
	public double getLengthSQ()
	{
		return x * x + y * y;
	}
	
	//向量置零
	public VecND Zero()
	{
		x = 0;
		y = 0;
		return this;
	}
	
	public boolean isZero()
	{
		return x == 0 && y == 0;
	}
	
	//向量的长度设置为我们期待的value
	public void setLength(double value) 
	{
		double _angle = getAngle();
		x = Math.cos(_angle) * value;
		y = Math.sin(_angle) * value;
	}
	
	//向量的标准化（方向不变，长度为1）
	public VecND normalize()
	{
		double length = getLength();
		x = x / length;
		y = y / length;
		return this;
	}
	//是否已经标准化
	public boolean isNormalized()
	{
		return getLength() == 1.0;
	}
	
	//向量的方向翻转
	public VecND reverse()
	{
		x = -x;
		y = -y;
		return this;
	}
	
	//2个向量的数量积(点积)
	public double dotProduct(VecND v)
	{
		return x * v.x + y * v.y;
	}
	
	//2个向量的向量积(叉积)
	public double crossProduct(VecND v)
	{
		return x * v.y - y * v.x;
	}

	//计算2个向量的夹角弧度
	//参考点积公式:v1 * v2 = cos<v1,v2> * |v1| *|v2|
	public static double radianBetween(VecND v1, VecND v2)
	{
		if(!v1.isNormalized()) v1 = v1.clone().normalize(); // |v1| = 1
		if(!v2.isNormalized()) v2 = v2.clone().normalize(); // |v2| = 1
		return Math.acos(v1.dotProduct(v2)); 
	}
	
	//弧度 = 角度乘以PI后再除以180、 推理可得弧度换算角度的公式
	//弧度转角度
	public static double radian2Angle(double radian)
	{
		return radian / Math.PI * 180;
	}
	//向量加
	public VecND add(VecND v)
	{
		return new VecND(x + v.x, y + v.y);
	}
	//向量减
	public VecND subtract(VecND v)
	{
		return new VecND(x - v.x, y - v.y);
	}
	//向量乘
	public VecND multiply(double value)
	{
		return new VecND(x * value, y * value);
	}
	//向量除
	public VecND divide(double value)
	{
		return new VecND(x / value, y / value);
	}
}
