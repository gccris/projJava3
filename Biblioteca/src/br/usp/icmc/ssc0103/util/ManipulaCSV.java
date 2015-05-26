package br.usp.icmc.ssc0103.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import br.usp.icmc.scc0103.model.*;;

public class ManipulaCSV {
	
	//classe responsavel por cadastro e recuperacao de dados no arquivo csv
	private String nomeArquivoUsuarios;	//representa o nome do arquivo csv a gravar e ler informacoes
	private String nomeArquivoEmprestimos; //representa o nome do arquivo csv com os emprestimos
	private String nomeArquivoLivros;
	
	public String getNomeArquivo() {
		return nomeArquivoUsuarios;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivoUsuarios = nomeArquivo;
	}

	public ManipulaCSV(String nomeArquivoUsuario,String nomeArquivoEmprestimos,String nomeArquivoLivros) {
		super();
		this.nomeArquivoUsuarios = nomeArquivoUsuario;
		this.nomeArquivoEmprestimos = nomeArquivoEmprestimos;
		this.nomeArquivoLivros = nomeArquivoLivros;
	}
	
	//ARQUIVO CSV: TIPO,NOME,CPF
	public ArrayList<Pessoa> loadUsuarios(){
		ArrayList<Pessoa> listUsers = new ArrayList<Pessoa>();
		BufferedReader leitor = null;
		String linha;
		
		try {
			leitor = new BufferedReader(new FileReader(this.nomeArquivoUsuarios));
			while((linha = leitor.readLine()) != null){
				String[] user = linha.split(",");
				
				if((user[0].compareTo("0")) == 0){//aluno
					Aluno a = new Aluno(user[1],user[2]);
					listUsers.add(a);
				}
				else if((user[0].compareTo("1")) == 0){//professor
					Professor p = new Professor(user[1],user[2]);
					listUsers.add(p);
				}
				else if((user[0].compareTo("2")) == 0){//comunidade
					Comunidade c = new Comunidade(user[1],user[2]);
					listUsers.add(c);
				}
			}
			leitor.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listUsers;
	}
	
	
	public void cadastrarUsuario(Pessoa usuario)
	{
		//TODO
	}
	
	public void cadastrarEmprestimo(Pessoa usuario,Livro livro)
	{
		//TODO
	}
	
	public ArrayList<Livro> getLivrosPorUsuario(Pessoa user)
	{
		return null;
	}
	
}
