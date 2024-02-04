package cafehows.model;

import lombok.Data;

@Data
public class CategoryDTO {
	private int cano;
	private String kind;
	
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
	
}
