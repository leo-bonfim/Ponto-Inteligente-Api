package com.leonardo.pontointeligente.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.leonardo.pontointeligente.api.entities.Lancamento;
import com.leonardo.pontointeligente.api.repositories.LancamentoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {
	
	@MockBean
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Before
	public void setUp() {
		BDDMockito.given(lancamentoRepository.findById(Mockito.anyLong()))
			.willReturn(Optional.ofNullable(new Lancamento()));
		
		BDDMockito.given(lancamentoRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
			.willReturn(new PageImpl<Lancamento>(new ArrayList<Lancamento>()));
		
		BDDMockito.given(lancamentoRepository.save(Mockito.any(Lancamento.class))).willReturn(new Lancamento());
	}
	
	@Test
	public void testBuscarLancamentoPorFuncionarioId() {
		Page<Lancamento> lancamento = lancamentoService.buscarPorFuncionarioId(1L, PageRequest.of(0, 10));
		
		assertNotNull(lancamento);
	}
	
	@Test
	public void testBuscarPorId() {
		Optional<Lancamento> lancamento = lancamentoService.buscarPorId(1L);
		
		assertTrue(lancamento.isPresent());
	}
	
	@Test
	public void testPersistir() {
		Lancamento lancamento = lancamentoService.persistir(new Lancamento());
		
		assertNotNull(lancamento);
	}
	
}
