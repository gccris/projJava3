package br.usp.icmc.scc0103.model;

import java.util.Date;

public class Comunidade extends Pessoa{
	public Comunidade(String nome, String cpf) {
		super(nome,cpf);
	}
	public Comunidade(String nome, String cpf,Date d) {
		super(nome,cpf,d);
	}
	
	public boolean pegaEmprestadoLivro(Livro l){
		if(getLivrosEmprestados().size()<2){
			this.getLivrosEmprestados().add(l);
			return true;
		}
		else
			return false;
	}
	
	public String getTipo() {
		return "Comunidade";
	}

	
}
