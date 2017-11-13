package svc.managers;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.stlcourts.common.models.Court;

import svc.data.municipal.CourtDAO;

@Component
public class CourtManager {
	@Inject
	private CourtDAO courtDAO;
		
	public void updateCourt(Court court) {
		courtDAO.updateCourt(court);
	}
	
	public Court createCourt(Court court){
		
		return court;
	}
	
	public void deleteCourt(long courtId){
		
	}
	
}
