package domain.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Plane {
	
	@Id @GeneratedValue
	Integer id;
	
	String serial;
	
	@ManyToOne(optional=false)
	PlaneModel model;

	//TODO POSITION ZELA?
	
	@Temporal(TemporalType.TIMESTAMP)
	Date fabricationDate;
	
	Airline airline;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public PlaneModel getModel() {
		return model;
	}

	public void setModel(PlaneModel model) {
		this.model = model;
	}

	public Date getFabricationDate() {
		return fabricationDate;
	}

	public void setFabricationDate(Date fabricationDate) {
		this.fabricationDate = fabricationDate;
	}
	
	
}