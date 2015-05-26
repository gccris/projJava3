package br.usp.icmc.scc0103.model;

import java.util.Date;

public class Emprestimo {
	private Livro livroEmprestado;
	private Aluno alunoComLivro;
	private Date dataAluguel;
	
	public Emprestimo(Livro l, Aluno a, Date d){
		super();
		this.setLivroEmprestado(l);
		this.setAlunoComLivro(a);
		this.setDataAluguel(d);
	}
	
	public Livro getLivroEmprestado() {
		return livroEmprestado;
	}

	public void setLivroEmprestado(Livro livroEmprestado) {
		this.livroEmprestado = livroEmprestado;
	}

	public Aluno getAlunoComLivro() {
		return alunoComLivro;
	}

	public void setAlunoComLivro(Aluno alunoComLivro) {
		this.alunoComLivro = alunoComLivro;
	}

	public Date getDataAluguel() {
		return dataAluguel;
	}

	public void setDataAluguel(Date dataAluguel) {
		this.dataAluguel = dataAluguel;
	}
	
}
