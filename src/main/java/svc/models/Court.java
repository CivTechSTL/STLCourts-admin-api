package svc.models;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Court {
	private Long court_id;
	private String court_name;
	private String phone;
	private String website;
	private String extension;
	private String address;
	private String paymentSystem;
	private String city;
	private String state;
	private String zip_code;
	private BigDecimal latitude;
	private BigDecimal longitude;
	//private List<Judge> judges;
	private int citation_expires_after_days;
	private  String rights_type;
	private String rights_value;	
	
	@Id
	@GeneratedValue
	public Long getCourt_id() {
		return court_id;
	}
	public void setCourt_id(Long court_id) {
		this.court_id = court_id;
	}
	public String getCourt_name() {
		return court_name;
	}
	public void setCourt_name(String court_name) {
		this.court_name = court_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPaymentSystem() {
		return paymentSystem;
	}
	public void setPaymentSystem(String paymentSystem) {
		this.paymentSystem = paymentSystem;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	/*public List<Judge> getJudges() {
		return judges;
	}
	public void setJudges(List<Judge> judges) {
		this.judges = judges;
	}*/
	public int getCitation_expires_after_days() {
		return citation_expires_after_days;
	}
	public void setCitation_expires_after_days(int citation_expires_after_days) {
		this.citation_expires_after_days = citation_expires_after_days;
	}
	public String getRights_type() {
		return rights_type;
	}
	public void setRights_type(String rights_type) {
		this.rights_type = rights_type;
	}
	public String getRights_value() {
		return rights_value;
	}
	public void setRights_value(String rights_value) {
		this.rights_value = rights_value;
	}
}
