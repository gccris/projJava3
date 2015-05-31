package br.usp.icmc.ssc0103.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		File f = new File(this.nomeArquivoUsuarios);
		if(!f.exists()) 
			return null;
		
		try {
			leitor = new BufferedReader(new FileReader(this.nomeArquivoUsuarios));
			while((linha = leitor.readLine()) != null){
				String[] user = linha.split(",");
				Date d = new Date();//new Date(user[3]);
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
				Pessoa p = GerenciaBiblioteca.findPessoaPorCPF(listUsers,emprestimo[1]);
				Livro l = GerenciaBiblioteca.findLivroPorCodigo(listLivros,emprestimo[0]);
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
	
	
	public void cadastrarPessoa(ArrayList<Pessoa> listPessoas)
	{
		FileWriter escritor;
		Date d = new Date();
		Integer tipoInserido;
		

			
		try {
			escritor = new FileWriter(new File(this.nomeArquivoUsuarios));
			for(Pessoa p:listPessoas){
				if(p.getTipo().compareTo("Aluno") == 0) tipoInserido = 0;
				else if (p.getTipo().compareTo("Professor") == 0) tipoInserido = 1;
				else tipoInserido = 2;
				escritor.append(tipoInserido+","+p.getNome()+","+p.getCpf()+","+d.toString()+'\n');
			}
				escritor.close();
				
		} catch (FileNotFoundException e) {
			File f = new File(this.nomeArquivoUsuarios);
			
			try {
				f.createNewFile();
				escritor = new FileWriter(f);
				for(Pessoa p:listPessoas){
					if(p.equals("Aluno")) tipoInserido = 0;
					else if (p.equals("Professor")) tipoInserido = 1;
					else tipoInserido = 2;
					escritor.append(tipoInserido+","+p.getNome()+","+p.getCpf()+","+d.toString()+'\n');
				}
				escritor.close();
			} catch (IOException ex) {
				System.out.println("erro: "+ex.getMessage());
			}
			
		} catch (IOException ex) {
			System.out.println("erro: "+ex.getMessage());
		}
		
	}
	
	public void cadastrarEmprestimo(Emprestimo emprestimo)
	{
		FileWriter escritor;
		try {
			escritor = new FileWriter(new File(this.nomeArquivoLivros));
			//escritor.append(livro.getNome()+","+usuario.getNome());
			escritor.close();
		} catch (IOException ex) {
			System.out.println("erro: "+ex.getMessage());
		}
	}
	
	
}
