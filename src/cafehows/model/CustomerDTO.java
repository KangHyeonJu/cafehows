package cafehows.model;

import java.sql.Date;

import lombok.Data;

@Data
public class CustomerDTO {
	private int cno;
	private int point;
	private Date recdate;
	private int visibility;
	private String phoneNumber;
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public Date getRecdate() {
		return recdate;
	}
	public void setRecdate(Date recdate) {
		this.recdate = recdate;
	}
	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
//	public void setPhoneNumber(int phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//	
//	public int getPhoneNumber() {
//		return phoneNumber;
//	}
}
//boolean b = (i != 0);

//int i = (b)? 1 : 0;
//int i = (b)? 1 : 0;