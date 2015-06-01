package br.usp.icmc.scc0103.model;

import java.util.Date;

public class Aluno extends Pessoa{
	public Aluno(String nome, String cpf) {
		super(nome,cpf);
	}
	public Aluno(String nome, String cpf,Date d) {
		super(nome,cpf,d);
	}
	
	public boolean pegaEmprestadoLivro(Livro l){
		if(getLivrosEmprestados().size()<4){
			this.getLivrosEmprestados().add(l);
			return true;
		}
		else
			return false;
	}
	
	public String getTipo() {
		return "Aluno";
	}

	
}
