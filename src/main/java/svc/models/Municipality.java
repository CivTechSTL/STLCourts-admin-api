
package svc.models;

import java.util.List;

import com.stlcourts.common.types.HashableEntity;


public class Municipality {
	public long id;
	public String name;
	public List<HashableEntity<Court>> courts;
	public String paymentUrl;
	public Boolean isSupported;
}