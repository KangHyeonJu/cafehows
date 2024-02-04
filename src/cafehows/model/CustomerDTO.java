package cafehows.model;

import java.sql.Date;

import lombok.Data;

@Data
public class CustomerDTO {
	private int cno;
	private int point;
	private Date recdate;
	private boolean visibility;
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
	public boolean isVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {

		this.visibility = (visibility!=0);
	}
	
	
}
//boolean b = (i != 0);

//int i = (b)? 1 : 0;
//int i = (b)? 1 : 0;