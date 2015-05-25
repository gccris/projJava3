package br.usp.icmc.ssc0103.executavel;

import br.usp.icmc.ssc0103.util.*;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args)
	{
		Scanner leitor = new Scanner(System.in);
		Usuario usuario = new Usuario();
		String nomeArquivoUsuarios = "usuarios.csv";
		Boolean sucessoLogin;
		do
		{
			System.out.println("Insira seu login: ");
			usuario.setLogin(leitor.nextLine().trim());
			System.out.println("Insira sua senha: ");
			usuario.setSenha(leitor.nextLine().trim());
			sucessoLogin = LoginUsuario.logar(new Usuario("aluno","teste","teste"),nomeArquivoUsuarios);
		} while(!sucessoLogin);
		//exibe o menu de opcoes para o usuario
		menu(leitor);
	}
	
	public static void menu(Scanner leitor){
		System.out.println("Bem vindo ao sistema da biblioteca");
		System.out.println("Escolha uma opcao de acesso: ");
		System.out.println("1 - Pegar um livro emprestado.");
		System.out.println("2 - Listar todos os livros disponiveis.");
		System.out.println("3 - Listar todos os usuarios cadastrados.");
		System.out.println("4 - Listar todos os empréstimos.");
		System.out.println("9 - Sair do sistema.");
		Integer opcao = leitor.nextInt();

	}
}
