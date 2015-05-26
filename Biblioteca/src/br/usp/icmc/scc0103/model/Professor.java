package br.usp.icmc.scc0103.model;

import java.util.Date;

public class Professor extends Pessoa{
	public Professor(String nome, String cpf) {
		super(nome,cpf);
	}
	public Professor(String nome, String cpf,Date d) {
		super(nome,cpf,d);
	}
	
	public boolean estaSuspenso(Date diaAtual){
		if(getDiaSuspensao().compareTo(diaAtual) < 0)
			return false;
		return true;
	}
	
	public boolean pegaEmprestadoLivro(Livro l){
		if(getLivrosEmprestados().size()<6){
			emprestarLivro(l);
			return true;
		}
		else
			return false;
	}
}
