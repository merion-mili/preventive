package al.mili.preventive.client;

import java.io.Serializable;

public class ResourceTypeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1331309092373984046L;
	private int typeId;
	private String emri;

	public ResourceTypeBean() {

	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getEmri() {
		return emri;
	}

	public void setEmri(String emri) {
		this.emri = emri;
	}

}
