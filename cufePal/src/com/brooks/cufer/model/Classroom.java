package com.brooks.cufer.model;
import android.os.Parcel;
import android.os.Parcelable;
public class Classroom implements Parcelable
{
	private String jsbh;// 教室编号
	private String jsmc;// 教室名称
	private String jslb;// 教室类别
	private String xq;// 校区
	private String skzws;// 上课座位数
	private String sybm;// 使用部门
	private String kszws;// 考试座位数
	private String yyqk;// 预约情况
	public Classroom()
	{
	}
	public Classroom(Parcel in)
	{
		jsbh = in.readString();
		jsmc = in.readString();
		jslb = in.readString();
		xq = in.readString();
		skzws = in.readString();
		sybm = in.readString();
		kszws = in.readString();
		yyqk = in.readString();
	}
	public String getJsbh()
	{
		return jsbh;
	}
	public void setJsbh(String jsbh)
	{
		this.jsbh = jsbh;
	}
	public String getJsmc()
	{
		return jsmc;
	}
	public void setJsmc(String jsmc)
	{
		this.jsmc = jsmc;
	}
	public String getJslb()
	{
		return jslb;
	}
	public void setJslb(String jslb)
	{
		this.jslb = jslb;
	}
	public String getXq()
	{
		return xq;
	}
	public void setXq(String xq)
	{
		this.xq = xq;
	}
	public String getSkzws()
	{
		return skzws;
	}
	public void setSkzws(String skzws)
	{
		this.skzws = skzws;
	}
	public String getSybm()
	{
		return sybm;
	}
	public void setSybm(String sybm)
	{
		this.sybm = sybm;
	}
	public String getKszws()
	{
		return kszws;
	}
	public void setKszws(String kszws)
	{
		this.kszws = kszws;
	}
	public String getYyqk()
	{
		return yyqk;
	}
	public void setYyqk(String yyqk)
	{
		this.yyqk = yyqk;
	}
	@Override
	public String toString()
	{
		return "Classroom [jsbh=" + jsbh + ", jsmc=" + jsmc + ", jslb=" + jslb
				+ ", xq=" + xq + ", skzws=" + skzws + ", sybm=" + sybm
				+ ", kszws=" + kszws + ", yyqk=" + yyqk + "]";
	}
	@Override
	public int describeContents()
	{
		return 0;
	}
	public static final Parcelable.Creator<Classroom> CREATOR = new Parcelable.Creator<Classroom>()
	{
		@Override
		public Classroom createFromParcel(Parcel source)
		{
			return new Classroom(source);
		}
		@Override
		public Classroom[] newArray(int size)
		{
			return new Classroom[size];
		}
	};
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(jsbh);
		dest.writeString(jsmc);
		dest.writeString(jslb);
		dest.writeString(xq);
		dest.writeString(skzws);
		dest.writeString(sybm);
		dest.writeString(kszws);
		dest.writeString(yyqk);
	}
}
