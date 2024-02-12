package cafehows.model;

import java.sql.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDTO {
	private String mname;
	private int mno;
	private int ono;
	private int price;
	private int cano;
//	private boolean visibility;
	
	private String kind;
	private int cumCount;
	private int count=1;
	private int ice;
	private int iceChangeable;
	private Date date;
	private Date startdate;
	private Date enddate;
	private String month;

//	public void setVisibility(int visibility) {
//		this.visibility = (visibility!=0);
//	}


	private int visibility;

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public int getMno() {
		return mno;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public int getOno() {
		return ono;
	}

	public void setOno(int ono) {
		this.ono = ono;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCano() {
		return cano;
	}

	public void setCano(int cano) {
		this.cano = cano;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getCumCount() {
		return cumCount;
	}

	public void setCumCount(int cumCount) {
		this.cumCount = cumCount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getIce() {
		return ice;
	}

	public void setIce(int ice) {
		this.ice = ice;
	}

	public int getIceChangeable() {
		return iceChangeable;
	}

	public void setIceChangeable(int iceChangeable) {
		this.iceChangeable = iceChangeable;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	
	

}
