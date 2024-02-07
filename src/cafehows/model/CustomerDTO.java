package cafehows.model;

import java.sql.Date;

import lombok.Data;

@Data
public class CustomerDTO {
	private int cno;
	private int point;
	private Date recdate;
	private int visibility;
}
//boolean b = (i != 0);

//int i = (b)? 1 : 0;
//int i = (b)? 1 : 0;