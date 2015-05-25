package br.usp.icmc.ssc0103.util;

public class Livro {
	
	public String tipo;	//texto (aluno e professor podem emprestar) ou geral(todos)
	
	public Livro(String tipo) {
		super();
		this.tipo = tipo;
	}
	
	public String getTipoLivro() {
		return this.tipo;
	}
	
	public void setTipoLivro(String tipo){
		this.tipo = tipo;
	}
	
}
