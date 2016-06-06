package com.brooks.cufer.model;
public class Grade
{
	private String xn;// 学年
	private String xq;// 学期
	private String kcdm;// 课程代码
	private String kcmc;// 课程名称
	private String kcxz;// 课程性质
	private String kcgs;// 课程归属
	private String xf;// 学分
	private String jd;// 绩点
	private String dj;// 等级
	private String fs;// 分数
	private String fxbj;// 辅修标记
	private String bkcj;// 补考成绩
	private String cxcj;// 重修成绩
	private String kkxy;// 开课学院
	private String bz;// 备注
	private String cxbj;// 重修标记
	public String getXn()
	{
		return xn;
	}
	public void setXn(String xn)
	{
		this.xn = xn;
	}
	public String getXq()
	{
		return xq;
	}
	public void setXq(String xq)
	{
		this.xq = xq;
	}
	public String getKcdm()
	{
		return kcdm;
	}
	public void setKcdm(String kcdm)
	{
		this.kcdm = kcdm;
	}
	public String getKcmc()
	{
		return kcmc;
	}
	public void setKcmc(String kcmc)
	{
		this.kcmc = kcmc;
	}
	public String getKcxz()
	{
		return kcxz;
	}
	public void setKcxz(String kcxz)
	{
		this.kcxz = kcxz;
	}
	public String getKcgs()
	{
		return kcgs;
	}
	public void setKcgs(String kcgs)
	{
		this.kcgs = kcgs;
	}
	public String getXf()
	{
		return xf;
	}
	public void setXf(String xf)
	{
		this.xf = xf;
	}
	public String getJd()
	{
		return jd;
	}
	public void setJd(String jd)
	{
		this.jd = jd;
	}
	public String getDj()
	{
		return dj;
	}
	public void setDj(String dj)
	{
		this.dj = dj;
	}
	public String getFs()
	{
		return fs;
	}
	public void setFs(String fs)
	{
		this.fs = fs;
	}
	public String getFxbj()
	{
		return fxbj;
	}
	public void setFxbj(String fxbj)
	{
		this.fxbj = fxbj;
	}
	public String getBkcj()
	{
		return bkcj;
	}
	public void setBkcj(String bkcj)
	{
		this.bkcj = bkcj;
	}
	public String getCxcj()
	{
		return cxcj;
	}
	public void setCxcj(String cxcj)
	{
		this.cxcj = cxcj;
	}
	public String getKkxy()
	{
		return kkxy;
	}
	public void setKkxy(String kkxy)
	{
		this.kkxy = kkxy;
	}
	public String getBz()
	{
		return bz;
	}
	public void setBz(String bz)
	{
		this.bz = bz;
	}
	public String getCxbj()
	{
		return cxbj;
	}
	public void setCxbj(String cxbj)
	{
		this.cxbj = cxbj;
	}
	@Override
	public String toString()
	{
		return "Grade [学年=" + xn + ", 学期=" + xq + ", 课程代码=" + kcdm + ", 课程名称="
				+ kcmc + ", 课程性质=" + kcxz + ", 课程归属=" + kcgs + ", 学分=" + xf
				+ ", 绩点=" + jd + ", 等级=" + dj + ", 分数=" + fs + ", 辅修标记=" + fxbj
				+ ", 补考成绩=" + bkcj + ", 重修成绩=" + cxcj + ", 开课学院=" + kkxy
				+ ", 备注=" + bz + ", 重修标记=" + cxbj + "]";
	}
}
