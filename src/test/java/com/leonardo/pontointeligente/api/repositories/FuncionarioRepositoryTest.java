package com.leonardo.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.leonardo.pontointeligente.api.entities.Empresa;
import com.leonardo.pontointeligente.api.entities.Funcionario;
import com.leonardo.pontointeligente.api.enums.PerfilEnum;
import com.leonardo.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String EMAIL = "email@email.com";
	private static final String CPF = "24291173474";
	
	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		this.funcionarioRepository.save(obterDadosFuncionario(empresa));
	}
	
	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testBuscarFuncionarioPorEmail() {
		Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
		assertEquals(EMAIL, funcionario.getEmail());
	}
	
	@Test
	public void testBuscarFuncionarioPorCpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
		assertEquals(CPF, funcionario.getCpf());
	}
	
	@Test
	public void testBuscarFuncionarioPorEmailECpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
		assertNotNull(funcionario);
	}
	
	public void testBuscarFuncionarioPorEmailOuCpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "email-invalido@email.com");
		assertNull(funcionario);
	}
	
	private Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(CPF);
		funcionario.setEmail(EMAIL);
		funcionario.setNome("Fulano de tal");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setEmpresa(empresa);
		funcionario.setQtdHorasAlmoco(1f);
		funcionario.setQtdHorasTrabalhoDia(8f);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		return funcionario;
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj("51463645000100");
		return empresa;
	}
	
}
