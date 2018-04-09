package al.mili.preventive.client;

import java.io.Serializable;

public class UnitBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4973278030174463021L;
	private int unitId;
	private String unitName;

	public UnitBean() {
		// TODO Auto-generated constructor stub
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
