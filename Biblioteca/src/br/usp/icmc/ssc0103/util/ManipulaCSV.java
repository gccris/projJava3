package br.usp.icmc.ssc0103.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
			File file = new File(this.nomeArquivoLivros);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			loadLivros();
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
				Date d = new Date();
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				d = df.parse(user[3]);
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
			File file = new File(this.nomeArquivoUsuarios);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			loadUsuarios();	
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
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
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date d = new Date();
				try {
					d = df.parse(emprestimo[2]);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				Emprestimo e = new Emprestimo(l,p,d);
				listEmprestimos.add(e);
			}
			leitor.close();
		} catch (FileNotFoundException e) {
			File f = new File(this.nomeArquivoEmprestimos);
			try {
				f.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			loadEmprestimos(listUsers,listLivros);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return listEmprestimos;
	}
	
	
	public void cadastrarPessoa(ArrayList<Pessoa> listPessoas)
	{
		FileWriter escritor;
		Integer tipoInserido;	
		try {
			escritor = new FileWriter(new File(this.nomeArquivoUsuarios));
			for(Pessoa p:listPessoas){
				if(p.getTipo().compareTo("Aluno") == 0) tipoInserido = 0;
				else if (p.getTipo().compareTo("Professor") == 0) tipoInserido = 1;
				else tipoInserido = 2;
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String data = dateFormat.format(p.getDiaSuspensao());
				escritor.append(tipoInserido+","+p.getNome()+","+p.getCpf()+","+data+'\n');
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
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String data = dateFormat.format(p.getDiaSuspensao());
					escritor.append(tipoInserido+","+p.getNome()+","+p.getCpf()+","+data+'\n');
				}
				escritor.close();
			} catch (IOException ex) {
				System.out.println("erro: "+ex.getMessage());
			}
			
		} catch (IOException ex) {
			System.out.println("erro: "+ex.getMessage());
		}
		
	}
	
	public void cadastrarLivro(ArrayList<Livro> listLivro)
	{
		FileWriter escritor;	
		try {
			escritor = new FileWriter(new File(this.nomeArquivoLivros));
			for(Livro l:listLivro){
				escritor.append(l.getTipo()+","+l.getNome()+","+l.getCodigo()+'\n');
			}
				escritor.close();
				
		} catch (FileNotFoundException e) {
			File f = new File(this.nomeArquivoLivros);
			
			try {
				f.createNewFile();
				escritor = new FileWriter(f);
				for(Livro l:listLivro){
					escritor.append(l.getTipo()+","+l.getNome()+","+l.getCodigo()+'\n');
				}
					escritor.close();
			} catch (IOException ex) {
				System.out.println("erro: "+ex.getMessage());
			}
			
		} catch (IOException ex) {
			System.out.println("erro: "+ex.getMessage());
		}
		
	}
	
	public void cadastrarEmprestimo(ArrayList<Emprestimo> listEmprestimos)
	{
		FileWriter escritor;
		try {
			escritor = new FileWriter(new File(this.nomeArquivoEmprestimos));
			for(Emprestimo e:listEmprestimos){
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String data = dateFormat.format(e.getDataAluguel());
				escritor.append(e.getLivroEmprestado().getCodigo()+','+e.getPessoaComLivro().getCpf()+','+data+'\n');
			}
				escritor.close();
				
		} catch (FileNotFoundException e) {
			File f = new File(this.nomeArquivoEmprestimos);
			
			try {
				f.createNewFile();
				escritor = new FileWriter(f);
				for(Emprestimo emp:listEmprestimos){
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String data = dateFormat.format(emp.getDataAluguel());
					escritor.append(emp.getLivroEmprestado().getCodigo()+','+emp.getPessoaComLivro().getCpf()+','+data+'\n');
				}
					escritor.close();
			} catch (IOException ex) {
				System.out.println("erro: "+ex.getMessage());
			}
			
		} catch (IOException ex) {
			System.out.println("erro: "+ex.getMessage());
		}
		
	}
	
	
}
