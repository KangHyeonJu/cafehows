package cafehows.model;

import java.sql.Date;
import lombok.Data;

@Data
public class menuDTO {
	private String mname;
	private int price;
	private String kind;
	
	public String getMname() {
		return mname;
	}
	public int getPrice() {
		return price;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	
}
