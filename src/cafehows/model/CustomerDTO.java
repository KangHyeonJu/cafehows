package cafehows.model;

import java.sql.Date;

import lombok.Data;

@Data
public class CustomerDTO {
	private int cno;
	private int point;
	private Date recdate;
	private boolean visibility;
	
	public void setVisibility(int visibility) {
		this.visibility = (visibility!=0);
	}

}
//boolean b = (i != 0);

//int i = (b)? 1 : 0;
//int i = (b)? 1 : 0;