package br.usp.icmc.ssc0103.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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
	
	//ARQUIVO CSV: TIPO,NOME,CODIGO
	public ArrayList<Livro> loadLivros(){
		ArrayList<Livro> listLivros = new ArrayList<Livro>();
		BufferedReader leitor = null;
		String linha;
		
		try {
			leitor = new BufferedReader(new FileReader(this.nomeArquivoLivros));
			while((linha = leitor.readLine()) != null){
				String[] livro = linha.split(",");
				Livro l = new Livro(livro[0],livro[1],livro[2]);
				listLivros.add(l);
			}
			leitor.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return listLivros;
	}
	
	//ARQUIVO CSV: TIPO,NOME,CPF,DATASUSPENSAO
	public ArrayList<Pessoa> loadUsuarios(){
		ArrayList<Pessoa> listUsers = new ArrayList<Pessoa>();
		BufferedReader leitor = null;
		String linha;
		
		try {
			leitor = new BufferedReader(new FileReader(this.nomeArquivoUsuarios));
			while((linha = leitor.readLine()) != null){
				String[] user = linha.split(",");
				Date d = new Date(user[3]);
				if((user[0].compareTo("0")) == 0){//aluno
					Aluno a = new Aluno(user[1],user[2],d);
					listUsers.add(a);
				}
				else if((user[0].compareTo("1")) == 0){//professor
					Professor p = new Professor(user[1],user[2],d);
					listUsers.add(p);
				}
				else if((user[0].compareTo("2")) == 0){//comunidade
					Comunidade c = new Comunidade(user[1],user[2],d);
					listUsers.add(c);
				}
			}
			leitor.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

		return listUsers;
	}
	
	//ARQUIVO CSV: CODIGO LIVRO, CPF, DATE
	public ArrayList<Emprestimo> loadEmprestimos(ArrayList<Pessoa> listUsers,ArrayList<Livro> listLivros){
		ArrayList<Emprestimo> listEmprestimos = new ArrayList<Emprestimo>();
		BufferedReader leitor = null;
		String linha;
		
		try {
			leitor = new BufferedReader(new FileReader(this.nomeArquivoEmprestimos));
			while((linha = leitor.readLine()) != null){
				String[] emprestimo = linha.split(",");
				Pessoa p = findPessoaPorCPF(listUsers,emprestimo[1]);
				Livro l = findLivroPorCodigo(listLivros,emprestimo[0]);
				p.pegaEmprestadoLivro(l);
				Date d = new Date(emprestimo[2]);
				Emprestimo e = new Emprestimo(l,p,d);
				listEmprestimos.add(e);
			}
			leitor.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return listEmprestimos;
	}
	
	private Pessoa findPessoaPorCPF(ArrayList<Pessoa> listUsers,String CPF){
		for(Pessoa p:listUsers){
			if ((p.getCpf().compareTo(CPF)) == 0)
				return p;
		}
		return null;
	}
	
	private Livro findLivroPorCodigo(ArrayList<Livro> listLivros,String codigo){
		for(Livro l:listLivros){
			if ((l.getCodigo().compareTo(codigo)) == 0)
				return l;
		}
		return null;
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
