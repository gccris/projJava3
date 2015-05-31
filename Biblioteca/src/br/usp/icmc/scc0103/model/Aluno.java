package br.usp.icmc.scc0103.model;

import java.util.Date;

public class Aluno extends Pessoa{
	public Aluno(String nome, String cpf) {
		super(nome,cpf);
	}
	public Aluno(String nome, String cpf,Date d) {
		super(nome,cpf,d);
	}
	
	public boolean estaSuspenso(Date diaAtual){
		if(getDiaSuspensao().compareTo(diaAtual) < 0)
			return false;
		return true;
	}
	
	public boolean pegaEmprestadoLivro(Livro l){
		if(getLivrosEmprestados().size()<4){
			emprestarLivro(l);
			return true;
		}
		else
			return false;
	}
	
	public String getTipo() {
		return "Aluno";
	}

	
}
