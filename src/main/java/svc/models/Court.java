package svc.models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
// https://vladmihalcea.com/2017/03/29/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/

@Entity
public class Court {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="court_id")
	private Long id;
	
	@Column(name="court_name")
	private String name;
	private String phone;
	private String website;
	private String extension;
	private String address;
	private String paymentSystem;
	private String city;
	private String state;
	
	@Column(name="zip_code")
	private String zipCode;
	private Double latitude;
	private Double longitude;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "court_id")
	private List<Judge> judges;
	
	@Column(name="citation_expires_after_days")
	private int citationExpiresAfterDays;
	
	@Column(name="rights_type")
	private  String rightsType;
	
	@Column(name="rights_value")
	private String rightsValue;	
	
	public Court(){
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public List<Judge> getJudges() {
		return judges;
	}
	public void setJudges(List<Judge> judges) {
		this.judges = judges;
	}
	public int getCitationExpiresAfterDays() {
		return citationExpiresAfterDays;
	}

	public void setCitationExpiresAfterDays(int citationExpiresAfterDays) {
		this.citationExpiresAfterDays = citationExpiresAfterDays;
	}

	public String getRightsType() {
		return rightsType;
	}

	public void setRightsType(String rightsType) {
		this.rightsType = rightsType;
	}

	public String getRightsValue() {
		return rightsValue;
	}

	public void setRightsValue(String rightsValue) {
		this.rightsValue = rightsValue;
	}
}
