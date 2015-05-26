package br.usp.icmc.scc0103.model;

import java.util.Date;

public class Comunidade extends Pessoa{
	public Comunidade(String nome, String cpf) {
		super(nome,cpf);
		// TODO Auto-generated constructor stub
	}
	
	public boolean estaSuspenso(Date diaAtual){
		if(getDiaSuspensao().compareTo(diaAtual) < 0)
			return false;
		return true;
	}
	
	public boolean pegaEmprestadoLivro(Livro l){
		if(getLivrosEmprestados().size()<2){
			emprestarLivro(l);
			return true;
		}
		else
			return false;
	}
	
}
