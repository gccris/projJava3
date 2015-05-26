package br.usp.icmc.ssc0103.executavel;

import br.usp.icmc.ssc0103.util.*;

import java.util.Date;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args)
	{
		Scanner leitor = new Scanner(System.in);
		Date data = lerDataUsuario(leitor);
		Integer opcao;
		//Usuario usuario = new Usuario();
		String nomeArquivoUsuarios = "usuarios.csv";
		Boolean sucessoLogin = null;
		
		Integer logOuCad = leitor.nextInt();
		System.out.println("Escolha uma opcao: ");
		System.out.println("1 - Logar no sistema");
		System.out.println("2 - Realizar novo cadastro");
		/*
		if(logOuCad == 1)
			LoginUsuario.logar();
		else if(logOuCad == 2)
			CadastroUsuario.cadastrar();*/
		
		do
		{
			System.out.println("Insira seu login: ");
			//usuario.setLogin(leitor.nextLine().trim());
			System.out.println("Insira sua senha: ");
			//usuario.setSenha(leitor.nextLine().trim());
			//sucessoLogin = LoginUsuario.logar(new Usuario("aluno","teste","teste"),nomeArquivoUsuarios);
		} while(!sucessoLogin);
		
		//exibe o menu de opcoes para o usuario
		do{
			opcao = opcaoUsuario(leitor);
			
		} while(opcao != 9);
	}
	
	public static Date lerDataUsuario(Scanner leitor){
		Date novaData = new Date();
		Integer aux;
		
		System.out.println("Informe o dia para a data do sistema: ");
		aux = leitor.nextInt();
		novaData.setDate(aux);
		
		System.out.println("Informe o mes para a data do sistema: ");
		aux = leitor.nextInt();
		novaData.setMonth(aux);
		
		System.out.println("Informe o ano para a data do sistema: ");
		aux = leitor.nextInt();
		novaData.setYear(aux);
		
		return novaData;
	}
	
	public static Integer opcaoUsuario(Scanner leitor){
		System.out.println("Bem vindo ao sistema da biblioteca");
		System.out.println("Escolha uma opcao de acesso: ");
		System.out.println("1 - Pegar um livro emprestado.");
		System.out.println("2 - Listar todos os livros disponiveis.");
		System.out.println("3 - Listar todos os usuarios cadastrados.");
		System.out.println("4 - Listar todos os empréstimos.");
		System.out.println("9 - Sair do sistema.");
		return leitor.nextInt();
	}
}
