
package svc.models;

import java.util.List;


public class Municipality {
	public long id;
	public String name;
	public List<Long> courts;
	public String paymentUrl;
	public Boolean isSupported;
}