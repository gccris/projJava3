package br.usp.icmc.ssc0103.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import br.usp.icmc.scc0103.model.*;
import br.usp.icmc.ssc0103.util.GerenciaBiblioteca;


public class TestBiblioteca {

	@Test
	public void testFindPessoaPorCPF() {
		ArrayList<Pessoa> listPessoas = new ArrayList<Pessoa>();
		Pessoa a = new Pessoa("Pessoa1","424x");
		Pessoa b = new Pessoa("Pessoa2","3x22");
		Pessoa c = new Pessoa("Pessoa1","3bbd");
		listPessoas.add(a);
		listPessoas.add(b);
		listPessoas.add(c);
		Pessoa testResult = GerenciaBiblioteca.findPessoaPorCPF(listPessoas, c.getCpf());
		assertEquals(testResult.getCpf(), c.getCpf());
		testResult = GerenciaBiblioteca.findPessoaPorCPF(listPessoas, b.getCpf());
		assertEquals(testResult.getCpf(), b.getCpf());
		assertNull(GerenciaBiblioteca.findPessoaPorCPF(listPessoas, "1"));
	}
	
	@Test
	public void testFindLivrosPorCodigo() {
		ArrayList<Livro> listLivros = new ArrayList<Livro>();
		Livro a = new Livro("0","Contos de fada","123800");
		Livro b = new Livro("0","Contos de fada2","123801");
		Livro c = new Livro("1","Contos de fada","123802");
		listLivros.add(a);
		listLivros.add(b);
		listLivros.add(c);
		Livro testResult = GerenciaBiblioteca.findLivroPorCodigo(listLivros, a.getCodigo());
		assertEquals(testResult.getCodigo(), a.getCodigo());
		testResult = GerenciaBiblioteca.findLivroPorCodigo(listLivros, c.getCodigo());
		assertEquals(testResult.getCodigo(), c.getCodigo());
		assertNull(GerenciaBiblioteca.findLivroPorCodigo(listLivros, "1"));
	}
	
	@Test
	public void testFindLivrosNaoAlugados(){
		ArrayList<Pessoa> listPessoas = new ArrayList<Pessoa>();
		Pessoa pA = new Pessoa("Pessoa1","424x");
		Pessoa pB = new Pessoa("Pessoa2","3x22");
		Pessoa pC = new Pessoa("Pessoa1","3bbd");
		listPessoas.add(pA);
		listPessoas.add(pB);
		listPessoas.add(pC);
		ArrayList<Livro> listLivros = new ArrayList<Livro>();
		Livro lA = new Livro("0","Contos de fada","123800");
		Livro lB = new Livro("0","Contos de fada2","123801");
		Livro lC = new Livro("1","Contos de fada","123802");
		listLivros.add(lA);
		listLivros.add(lB);
		listLivros.add(lC);
		Date d = new Date(System.currentTimeMillis());
		ArrayList<Emprestimo> listEmprestimo = new ArrayList<Emprestimo>();
		Emprestimo eA = new Emprestimo(lA,pA,d);
		Emprestimo eB = new Emprestimo(lB,pB,d);
		Emprestimo eC = new Emprestimo(lC,pB,d);
		listEmprestimo.add(eA);
		listEmprestimo.add(eB);
		assertEquals((GerenciaBiblioteca.findLivrosNaoAlugados(listLivros, listEmprestimo)).size(),1);
		assertEquals((GerenciaBiblioteca.findLivrosNaoAlugados(listLivros, listEmprestimo)).get(0).getCodigo(),lC.getCodigo());
		listEmprestimo.add(eC);
		assertEquals((GerenciaBiblioteca.findLivrosNaoAlugados(listLivros, listEmprestimo)).size(),0);
	}
	
	@Test
	public void testCalculaTempoAtrasado(){
		Date d = new Date();
		Emprestimo eA = new Emprestimo(null,null,d);
		Date dataAtual = new Date(System.currentTimeMillis());
		dataAtual.setTime(dataAtual.getTime() +  + 1 * 24 * 60 * 60 * 1000);
		//assertEquals(GerenciaBiblioteca.calculaTempoAtrasado(eA, dataAtual),1);
		assertTrue(true);
		//TODO
	}

}
