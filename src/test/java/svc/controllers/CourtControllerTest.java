package svc.controllers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
	public void getsOne(){
		Court court1 = new Court();
		court1.setId(1L);
		court1.setName("One Court");
		
		when(mockCourtRepository.findOne(anyLong())).thenReturn(court1);
		Court court = controller.getOne(1L);
		assertThat(court, equalTo(court1));
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
		verify(mockCourtRepository).delete(2L);
	}
}
