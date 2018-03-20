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
import svc.models.Judge;
import svc.repositories.JudgeRepository;


@RunWith(MockitoJUnitRunner.class)
public class JudgeControllerTest {
	@InjectMocks
	JudgeController controller;

	@Mock
	JudgeRepository mockJudgeRepository;
	
	@Test
	public void findsAll(){
		Judge judge1 = new Judge();
		judge1.setId(1L);
		judge1.setJudge("One Judge");
		Judge judge2 = new Judge();
		judge2.setId(2L);
		judge2.setJudge("Two Judge");
		
		final List<Judge> JUDGES = Arrays.asList(judge1, judge2);
		
		when(mockJudgeRepository.findAll()).thenReturn(JUDGES);
		
		List<Judge> returnedJudges = controller.findAll();
		assertThat(returnedJudges, equalTo(JUDGES));
	}
	
	@Test
	public void getsOne() throws NotFoundException{
		Judge judge1 = new Judge();
		judge1.setId(1L);
		judge1.setJudge("One Judge");
		Optional<Judge> oJudge = Optional.of(judge1);
		
		when(mockJudgeRepository.findById(anyLong())).thenReturn(oJudge);
		Optional<Judge> judge = controller.getOne(1L);
		assertThat(judge, equalTo(oJudge));
	}
	
	@Test
	public void saves(){
		Judge judge1 = new Judge();
		judge1.setId(1L);
		judge1.setJudge("One Judge");
		
		when(mockJudgeRepository.save(judge1)).thenReturn(judge1);
		Judge judge = controller.load(judge1);
		assertThat(judge, equalTo(judge1));
	}
	
	@Test
	public void deletes(){
		controller.delete(2L);
		verify(mockJudgeRepository).deleteById(2L);
	}
	
	@Test (expected = NotFoundException.class)
	public void ThrowsNotFoundExceptionWhenJudgeNotFound(){
		when(mockJudgeRepository.findById(1L)).thenReturn(Optional.empty());
		controller.getOne(1L);
	}
}
