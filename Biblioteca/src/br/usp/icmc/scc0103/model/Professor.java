package br.usp.icmc.scc0103.model;

import java.util.Date;

public class Professor extends Pessoa{
	public Professor(String nome, String cpf) {
		super(nome,cpf);
	}
	public Professor(String nome, String cpf,Date d) {
		super(nome,cpf,d);
	}
	
	public boolean pegaEmprestadoLivro(Livro l){
		if(getLivrosEmprestados().size()<6){
			this.getLivrosEmprestados().add(l);
			return true;
		}
		else
			return false;
	}
	
	public String getTipo() {
		return "Professor";
	}

}
