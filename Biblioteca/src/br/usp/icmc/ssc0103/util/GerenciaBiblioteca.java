package br.usp.icmc.ssc0103.util;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JSpinner.DateEditor;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;

import br.usp.icmc.scc0103.model.Emprestimo;
import br.usp.icmc.scc0103.model.Livro;
import br.usp.icmc.scc0103.model.Pessoa;

public class GerenciaBiblioteca {

	//procura uma pessoa pelo seu cpf
	public static Pessoa findPessoaPorCPF(ArrayList<Pessoa> listUsers,String CPF){
		for(Pessoa p:listUsers){
			if ((p.getCpf().compareTo(CPF)) == 0)
				return p;
		}
		return null;
	}
	//procura uma pessoa pelo nome e retorna a lista de pessoas com o mesmo nome
	public static ArrayList<Pessoa> findPessoaPorNome(ArrayList<Pessoa> listUsers,String nome){
		ArrayList<Pessoa> pessoasMesmoNome = new ArrayList<Pessoa>();
		for(Pessoa p:listUsers){
			if ((p.getNome().compareTo(nome)) == 0)
				pessoasMesmoNome.add(p);
		}
		return pessoasMesmoNome;
	}
	
	//procura um livro pelo seu código
	public static Livro findLivroPorCodigo(ArrayList<Livro> listLivros,String codigo){
		for(Livro l:listLivros){
			if ((l.getCodigo().compareTo(codigo)) == 0)
				return l;
		}
		return null;
	}
	
	//procura um livro pelo nome e retorna a lista com todos os livros do mesmo nome
	public static ArrayList<Livro> findLivrosPorNome(ArrayList<Livro> listLivros,String nome){
		ArrayList<Livro> livrosNomeIgual = new ArrayList<Livro>();
		for(Livro l:listLivros){
			if ((l.getNome().compareTo(nome)) == 0)
				livrosNomeIgual.add(l);
		}
		return livrosNomeIgual;
	}
	
	//encontra uma pessoa pelo livro
	public static Pessoa findPessoaPorLivro(Livro l,ArrayList<Emprestimo> listEmprestimo){
		for(Emprestimo e:listEmprestimo){
			if ((e.getLivroEmprestado().getCodigo().compareTo(l.getCodigo())) == 0)
				return e.getPessoaComLivro();
		}
		return null;
	}
	
	//encontra uma pessoa pelo codigo do livro
	public static Pessoa findPessoaPorLivro(String codLivro,ArrayList<Emprestimo> listEmprestimo){
		for(Emprestimo e:listEmprestimo){
			if (e.getLivroEmprestado().getCodigo().compareTo(codLivro) == 0)
				return e.getPessoaComLivro();
		}
		return null;
	}
	
	//verifica se o livro ja esta alugado
	public static boolean livroEstaAlugado(Livro l,ArrayList<Emprestimo> listEmprestimo){
		for(Emprestimo e: listEmprestimo){
			if(e.getLivroEmprestado().getCodigo().compareTo(l.getCodigo())== 0)
				return true;
		}
		return false;
	}
	
	//verifica se o livro ja esta alugado pelo codigo
	public static boolean livroEstaAlugado(String codLivro,ArrayList<Emprestimo> listEmprestimo){
		for(Emprestimo e: listEmprestimo){
			if(e.getLivroEmprestado().getCodigo().compareTo(codLivro)== 0)
				return true;
		}
		return false;
	}
	
	//encontra uma lista de livros não alugados
	public static ArrayList<Livro> findLivrosNaoAlugados(ArrayList<Livro> listLivros,ArrayList<Emprestimo> listEmprestimo){
		ArrayList<Livro> livrosNaoAlugados = new ArrayList<Livro>();
		for(Livro l:listLivros){
			if(!livroEstaAlugado(l,listEmprestimo))
				livrosNaoAlugados.add(l);
		}
		return livrosNaoAlugados;
	}
	
	//encontra os emprestimos atrasados numa certa data
	public static ArrayList<Emprestimo> findLivrosAtrasados(ArrayList<Emprestimo> listEmprestimos,Date dataAtual){
		ArrayList<Emprestimo> listAtrasados = new ArrayList<Emprestimo>();
		for(Emprestimo e:listEmprestimos){
			if(dataAtual.after(e.getDataDevolucao()))
				listAtrasados.add(e);
		}
		return listAtrasados;
	}
	
	//verifica se o usuario esta com livros atrasados
	public static Boolean usuarioLivroAtrasados(ArrayList<Emprestimo> listEmprestimos,Pessoa p,Date dataAtual){
		for(Emprestimo e:listEmprestimos){
			if(dataAtual.after(e.getDataDevolucao()) && e.getPessoaComLivro().getCpf().compareTo(p.getCpf()) == 0)
				return true;
		}
		return false;
	}
	
	//calcula o tempo atrasado do usuario
	public static int calculaTempoAtrasado(Emprestimo e,Date dataAtual){
		DateTime dtAtual = new DateTime(dataAtual);
		DateTime dtSuspenso = new DateTime(e.getDataDevolucao());
		int dias = Days.daysBetween(dtSuspenso,dtAtual).getDays();
		if(dtSuspenso.getDayOfMonth() - dtAtual.getDayOfMonth() != 0)
			dias++;
		return dias;
	}
	
	//suspende usuario por determinado tempo
	public static void suspendeUsuario(Pessoa p,Date dataAtual,int diasAtraso){
		dataAtual.setDate(dataAtual.getDate()+diasAtraso);
		p.setDiaSuspensao(dataAtual);
	}
	
	public static Emprestimo findEmprestimo(Pessoa p,Livro l,ArrayList<Emprestimo> listEmprestimos){
		for(Emprestimo e:listEmprestimos){
			if(e.getPessoaComLivro().getCpf().compareTo(p.getCpf()) == 0 && e.getLivroEmprestado().getCodigo().compareTo(l.getCodigo())==0)
				return e;
		}
		return null;
	}
}
