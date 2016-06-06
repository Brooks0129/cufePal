package com.brooks.cufer.model;
import android.os.Parcel;
import android.os.Parcelable;
public class Classroom implements Parcelable
{
	private String jsbh;// ���ұ��
	private String jsmc;// ��������
	private String jslb;// �������
	private String xq;// У��
	private String skzws;// �Ͽ���λ��
	private String sybm;// ʹ�ò���
	private String kszws;// ������λ��
	private String yyqk;// ԤԼ���
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
