package br.usp.icmc.scc0103.model;

import java.util.ArrayList;
import java.util.Date;

public class Pessoa {
	private String nome; 	//nome da pessoa 
	private String cpf;		//cpf da pessoa(primary key pra colocar no arquivo)
	private String tipo;
	private ArrayList<Livro> livrosEmprestados;	//lista de livros emprestados
	private Date diaSuspensao;

	
	public Pessoa(String nome,String cpf) {
		super();
		this.setNome(nome);
		this.diaSuspensao = new Date();
		this.diaSuspensao.setYear(this.diaSuspensao.getYear()-this.diaSuspensao.getYear());//coloca numa data muito antiga
		this.setCpf(cpf);
		this.setTipo(this.getTipo());
		this.livrosEmprestados = new ArrayList<Livro>();
	}
	
	public Pessoa(String nome,String cpf,Date d) {
		super();
		this.setNome(nome);
		this.setDiaSuspensao(d);
		this.setCpf(cpf);
		this.setTipo(this.getTipo());
		this.livrosEmprestados = new ArrayList<Livro>();
	}
		
	public ArrayList<Livro> getLivrosEmprestados() {
		return livrosEmprestados;
	}

	protected void emprestarLivro(Livro l){
		this.livrosEmprestados.add(l);
	}
	
	public boolean estaSuspenso(Date diaAtual){
		if(getDiaSuspensao().after(diaAtual))
			return true;
		return false;
	}
	
	public void devolverLivro(Livro l){
		this.livrosEmprestados.remove(l);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return "Pessoa";
	}

	public Date getDiaSuspensao() {
		return diaSuspensao;
	}

	public void setDiaSuspensao(Date diaSuspensao) {
		this.diaSuspensao = diaSuspensao;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public boolean pegaEmprestadoLivro(Livro l){
		this.emprestarLivro(l);
		return true;
	}
	
	public String toString(){
		return this.getNome()+" - "+this.getCpf();
	}
	
}