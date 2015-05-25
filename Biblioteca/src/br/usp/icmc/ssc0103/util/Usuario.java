package br.usp.icmc.ssc0103.util;

import java.util.ArrayList;

public class Usuario {
	
	private String tipo;	//aluno, professor ou comunidade
	private String login;	//nome de identificacao do usuario
	private String senha;	//senha do usuario
	private ArrayList<Livro> livrosEmprestados;	//lista de livros emprestados
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public ArrayList<Livro> getLivrosEmprestados() {
		return livrosEmprestados;
	}

	public void setLivrosEmprestados(ArrayList<Livro> livrosEmprestados) {
		this.livrosEmprestados = livrosEmprestados;
	}

	public Usuario(){
		super();
		this.tipo = "";
		this.login = "";
		this.senha = "";
		this.livrosEmprestados = new ArrayList<Livro>();
	}
	
	public Usuario(String tipo, String login, String senha) {
		super();
		this.tipo = tipo;
		this.login = login;
		this.senha = senha;
		this.livrosEmprestados = new ArrayList<Livro>();
	}
	
}
