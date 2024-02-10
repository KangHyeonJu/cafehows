package cafehows.model;

import lombok.Data;

@Data
public class CategoryDTO {
	private int cano;
	private String kind;
	private int visibility;
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
	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	
	
	
}
