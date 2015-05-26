package br.usp.icmc.scc0103.model;

import java.util.Date;

public class Emprestimo {
	private Livro livroEmprestado;
	private Pessoa pessoaComLivro;
	private Date dataAluguel;
	
	public Emprestimo(Livro l, Pessoa p, Date d){
		super();
		this.setLivroEmprestado(l);
		this.setPessoaComLivro(p);
		this.setDataAluguel(d);
	}
	
	public Livro getLivroEmprestado() {
		return livroEmprestado;
	}

	public void setLivroEmprestado(Livro livroEmprestado) {
		this.livroEmprestado = livroEmprestado;
	}

	public Date getDataAluguel() {
		return dataAluguel;
	}

	public void setDataAluguel(Date dataAluguel) {
		this.dataAluguel = dataAluguel;
	}

	public Pessoa getPessoaComLivro() {
		return pessoaComLivro;
	}

	public void setPessoaComLivro(Pessoa pessoaComLivro) {
		this.pessoaComLivro = pessoaComLivro;
	}
	
}
