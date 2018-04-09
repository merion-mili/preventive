package al.mili.preventive.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(name="UNIT",uniqueConstraints = { @UniqueConstraint(columnNames = {
		"UNIT_NAME" }) })
@NamedQueries({
	@NamedQuery(name="queryAllUnit", query="from Unit"),
	@NamedQuery(name = "updateUnit", query = "UPDATE Unit u SET u.unitName=:unitName WHERE u.unitId=:unitId"),
	@NamedQuery(name="deleteUnit", query = "DELETE from Unit u WHERE u.unitId=:unitId")})

public class Unit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7029433042000871717L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="UNIT_ID")
	private int unitId;
	@Column(name = "UNIT_NAME")
	private String unitName;
	
	public Unit() {
		
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
