package br.usp.icmc.scc0103.model;

import java.util.Date;

public class Professor extends Pessoa{
	public Professor(String nome, String cpf) {
		super(nome,cpf);
	}
	
	public boolean estaSuspenso(Date diaAtual){
		if(getDiaSuspensao().compareTo(diaAtual) < 0)
			return false;
		return true;
	}
	
	public boolean PegaEmprestadoLivro(Livro l){
		if(getLivrosEmprestados().size()<6){
			emprestarLivro(l);
			return true;
		}
		else
			return false;
	}
}
