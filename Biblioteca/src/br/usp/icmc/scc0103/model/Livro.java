package br.usp.icmc.scc0103.model;

public class Livro {
	
	private String tipo;	//0 (aluno e professor podem emprestar)1(todos)
	private String nome;
	private String codigo;
	
	public Livro(String tipo,String nome,String codigo) {
		super();
		this.setTipoLivro(tipo);
		this.setCodigo(codigo);
		this.setNome(nome);
	}
	
	public String getTipoLivro() {
		return this.tipo;
	}
	
	public void setTipoLivro(String tipo){
		this.tipo = tipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
