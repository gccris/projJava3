package br.usp.icmc.ssc0103.util;

import java.util.ArrayList;

import br.usp.icmc.scc0103.model.Livro;
import br.usp.icmc.scc0103.model.Usuario;
import br.usp.icmc.ssc0103.util.*;

public class CadastroUsuario {
	
	//classe responsavel por cadastro do usuario no arquivo csv
	private String nomeArquivoUsuarios;	//representa o nome do arquivo csv a gravar e ler informacoes
	private String nomeArquivoEmprestimos; //representa o nome do arquivo csv com os emprestimos
	
	public String getNomeArquivo() {
		return nomeArquivoUsuarios;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivoUsuarios = nomeArquivo;
	}

	public CadastroUsuario(String nomeArquivo) {
		super();
		this.nomeArquivoUsuarios = nomeArquivo;
	}
	
	public void cadastrarUsuario(Usuario usuario)
	{
		//TODO
	}
	
	public void cadastrarEmprestimo(Usuario usuario,Livro livro)
	{
		//TODO
	}
	
	public ArrayList<Livro> getLivrosPorUsuario(Usuario user)
	{
		return null;
	}
	
}
