package cafehows.model;

import java.sql.Date;

import lombok.Data;

@Data
public class OrderDTO {

	private int ono;
	private int cno;
	private Date date;
	private int price;
	private int finalprice;


}
