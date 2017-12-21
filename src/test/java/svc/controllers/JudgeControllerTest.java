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

import svc.models.Judge;
import svc.repositories.JudgeJpaRepository;


@RunWith(MockitoJUnitRunner.class)
public class JudgeControllerTest {
	@InjectMocks
	JudgeController controller;

	@Mock
	JudgeJpaRepository mockJudgeJpaRepository;
	
	@Test
	public void findsAll(){
		Judge judge1 = new Judge();
		judge1.setId(1L);
		judge1.setJudge("One Judge");
		Judge judge2 = new Judge();
		judge2.setId(2L);
		judge2.setJudge("Two Judge");
		
		final List<Judge> JUDGES = Arrays.asList(judge1, judge2);
		
		when(mockJudgeJpaRepository.findAll()).thenReturn(JUDGES);
		
		List<Judge> returnedJudges = controller.findAll();
		assertThat(returnedJudges, equalTo(JUDGES));
	}
	
	@Test
	public void getsOne(){
		Judge judge1 = new Judge();
		judge1.setId(1L);
		judge1.setJudge("One Judge");
		
		when(mockJudgeJpaRepository.findOne(anyLong())).thenReturn(judge1);
		Judge judge = controller.getOne(1L);
		assertThat(judge, equalTo(judge1));
	}
	
	@Test
	public void saves(){
		Judge judge1 = new Judge();
		judge1.setId(1L);
		judge1.setJudge("One Judge");
		
		when(mockJudgeJpaRepository.save(judge1)).thenReturn(judge1);
		Judge judge = controller.load(judge1);
		assertThat(judge, equalTo(judge1));
	}
	
	@Test
	public void deletes(){
		controller.delete(2L);
		verify(mockJudgeJpaRepository).delete(2L);
	}
}
