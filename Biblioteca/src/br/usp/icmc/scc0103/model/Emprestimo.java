package br.usp.icmc.scc0103.model;

import java.util.Date;

public class Emprestimo {
	private Livro livroEmprestado;
	private Pessoa pessoaComLivro;
	private Date dataAluguel;
	private Date dataDevolucao;
	
	public Emprestimo(Livro l, Pessoa p, Date d){
		super();
		this.setLivroEmprestado(l);
		this.setPessoaComLivro(p);
		this.setDataAluguel(d);
		this.setDataDevolucao(d);
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
	
	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
			this.dataDevolucao = new Date();
			this.dataDevolucao.setDate(dataDevolucao.getDate());
			this.dataDevolucao.setMonth(dataDevolucao.getMonth());
			this.dataDevolucao.setYear(dataDevolucao.getYear());
			if(this.getPessoaComLivro().getTipo().compareTo("Aluno")== 0)
				this.dataDevolucao.setDate(this.dataDevolucao.getDate()+15);
			if(this.getPessoaComLivro().getTipo().compareTo("Professor")== 0)
				this.dataDevolucao.setDate(this.dataDevolucao.getDate()+60);
			if(this.getPessoaComLivro().getTipo().compareTo("Comunidade")== 0)
				this.dataDevolucao.setDate(this.dataDevolucao.getDate()+15);

	}

	public Pessoa getPessoaComLivro() {
		return pessoaComLivro;
	}

	public void setPessoaComLivro(Pessoa pessoaComLivro) {
		this.pessoaComLivro = pessoaComLivro;
	}
	
	public boolean equals(Object e){
		if (e == null)
			return false;
		if(((Emprestimo) e).getPessoaComLivro().getCpf() == this.getPessoaComLivro().getCpf())
			if(((Emprestimo) e).getLivroEmprestado().getCodigo() == this.getLivroEmprestado().getCodigo())
					return true;
		return false;
	}
}
