package cafehows.model;

import java.sql.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDTO {
	private String mname;
	private int price;
	private int cano;
	private String kind;
	private int cumCount;
	private int count=1;
	private String ice;
	private int visibility;
}
