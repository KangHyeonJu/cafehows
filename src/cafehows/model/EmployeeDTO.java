package cafehows.model;

import java.sql.Date;

import lombok.Data;

@Data
public class EmployeeDTO {
	int eno;
	String ename;
	int status;
	Date date;
	int hour;
	int wage;
	int holidayPay;
	int totalSalary;
	Date startDate;
	Date endDate;
	int datecolumn;
	
	
	
	
}
