package svc.controllers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import svc.exceptions.NotFoundException;
import svc.models.Court;
import svc.repositories.CourtRepository;


@RunWith(MockitoJUnitRunner.class)
public class CourtControllerTest {
	@InjectMocks
	CourtController controller;

	@Mock
	CourtRepository mockCourtRepository;
	
	@Test
	public void findsAll(){
		Court court1 = new Court();
		court1.setId(1L);
		court1.setName("One Court");
		Court court2 = new Court();
		court2.setId(2L);
		court2.setName("Two Court");
		
		final List<Court> COURTS = Arrays.asList(court1, court2);
		
		when(mockCourtRepository.findAll()).thenReturn(COURTS);
		
		List<Court> returnedCourts = controller.findAll();
		assertThat(returnedCourts, equalTo(COURTS));
	}
	
	@Test
	public void getsOne() throws NotFoundException{
		Court court1 = new Court();
		court1.setId(1L);
		court1.setName("One Court");
		
		Optional<Court> oCourt = Optional.of(court1);
		
		when(mockCourtRepository.findById(anyLong())).thenReturn(oCourt);
		Optional<Court> court = controller.getOne(1L);
		assertThat(court, equalTo(oCourt));
	}
	
	@Test
	public void saves(){
		Court court1 = new Court();
		court1.setId(1L);
		court1.setName("One Court");
		
		when(mockCourtRepository.save(court1)).thenReturn(court1);
		Court court = controller.load(court1);
		assertThat(court, equalTo(court1));
	}
	
	@Test
	public void deletes(){
		controller.delete(2L);
		verify(mockCourtRepository).deleteById(2L);
	}
	
	@Test (expected = NotFoundException.class)
	public void ThrowsExceptionWhenCourtNotFound(){
		when(mockCourtRepository.findById(1L)).thenReturn(Optional.empty());
		controller.getOne(1L);
	}
}
