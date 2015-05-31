package br.usp.icmc.ssc0103.executavel;

import java.util.*;

import br.usp.icmc.ssc0103.util.*;
import br.usp.icmc.scc0103.model.*;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Principal extends Application {
	ManipulaCSV alteracoesArquivo;
	Stage window;
	Scene home, sceneUser, sceneLivro, sceneEmprestimos;
	Button select, addUser, addLivro, addEmp, addDev;
	Button backUser, backLivro, backEmprestimos;
	Image img;
	ImageView imgView;
	Label textHome, textUser, textLivro, textEmprestimos;
	Label textAddUser, textAddLivro, textAddEmp, textAddDev;
	Label msgAddUser, msgAddLivro, msgEmp;
	TextField nameUser, nameEmp, nameDev, doc;
	TextField bookLivro, bookEmp, bookDev, code;
	ComboBox<String> options, typeUser, typeLivro;
	ComboBox<Pessoa> userCombo,userComboDev;
	ComboBox<Livro> livroCombo,livroComboDev;
	ArrayList<Pessoa> listPessoas;
	ArrayList<Livro> listLivros;
	ArrayList<Emprestimo> listEmprestimos;
	
	TableView<Pessoa> tableUser = new TableView<>();
	TableView<Livro> tableLivro = new TableView<>();
	TableView<Emprestimo> tableEmprestimo = new TableView<>();
	ObservableList<Pessoa> dataUser = FXCollections.observableArrayList();
	ObservableList<Livro> dataLivro = FXCollections.observableArrayList();
	ObservableList<Emprestimo> dataEmprestimo = FXCollections.observableArrayList();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		alteracoesArquivo = new ManipulaCSV("usuarios.csv","emprestimos.csv","livros.csv");
		//toObsList(alteracoesArquivo);
		
		listPessoas = alteracoesArquivo.loadUsuarios();
		listLivros = alteracoesArquivo.loadLivros();
		listEmprestimos = alteracoesArquivo.loadEmprestimos(listPessoas, listLivros);
		
		window = primaryStage;
		
		//PAGE: Home (Imagem, texto, menu de escolha, botão ok)
		img = new Image("http://icons.iconarchive.com/icons/itzikgur/my-seven/512/Books-1-icon.png");
		imgView = new ImageView();
		imgView.setImage(img);
		imgView.setFitWidth(230);
		imgView.setPreserveRatio(true);
		
		textHome = new Label("Sistema para Bibliotecas");
		textHome.setFont(new Font(30));
		
		options = new ComboBox<>();
		options.getItems().addAll("Usuários", "Livros", "Emprestimos");
		options.setPromptText("Selecione uma opção");
		
		select = new Button("SELECIONAR");
		select.setOnAction(e -> {
			if(options.getValue() == "Usuários"){
				window.setScene(sceneUser);
			}
			if(options.getValue() == "Livros"){
				window.setScene(sceneLivro);
			}
			if(options.getValue() == "Emprestimos"){
				window.setScene(sceneEmprestimos);
			}
		});
		
		VBox layoutHome = new VBox(30);
		layoutHome.getChildren().addAll(imgView, textHome, options, select);
		layoutHome.setAlignment(Pos.CENTER);
		
		//PAGE: Usuários (Tabela(Lista), campos para cadastro, botao add)
		textUser = new Label("Usuários");
		textUser.setFont(new Font(25));
		
		TableColumn tcNameUser = new TableColumn("NOME");
		tcNameUser.setMinWidth(150);
		tcNameUser.setMaxWidth(150);
		tcNameUser.setCellValueFactory(
				new PropertyValueFactory<>("nome"));
		
		TableColumn tcDocUser = new TableColumn("CPF");
		tcDocUser.setMinWidth(150);
		tcDocUser.setMaxWidth(150);
		tcDocUser.setCellValueFactory(
				new PropertyValueFactory<>("cpf"));
		
		TableColumn tcTypeUser = new TableColumn("TIPO");
		tcTypeUser.setMinWidth(100);
		tcTypeUser.setMaxWidth(100);
		tcTypeUser.setCellValueFactory(
				new PropertyValueFactory<>("tipo"));
		
		TableColumn tcBanUser = new TableColumn("SUSPENSÃO");
		tcBanUser.setMinWidth(100);
		tcBanUser.setMaxWidth(200);
		tcBanUser.setCellValueFactory(
				new PropertyValueFactory<>("diaSuspensao"));
		
		tableUser.getColumns().addAll(tcNameUser, tcDocUser, tcTypeUser, tcBanUser);
		tableUser.setItems(dataUser);

		
		textAddUser = new Label("Novo usuário:");
		textAddUser.setFont(new Font(16));
		
		nameUser = new TextField();
		nameUser.setPromptText("NOME");
		
		doc = new TextField();
		doc.setPromptText("CPF");
		
		typeUser = new ComboBox<>();
		typeUser.getItems().addAll("Aluno", "Professor", "Comunidade");
		typeUser.setPromptText("TIPO");
				
		addUser = new Button("ADD");
		addUser.setOnAction(e -> cadastrarUsuario(nameUser.getText(), doc.getText(), typeUser.getValue()));
		
		HBox hbUser = new HBox(3);
		hbUser.getChildren().addAll(nameUser, doc, typeUser, addUser);
		hbUser.setAlignment(Pos.CENTER);
		
		msgAddUser = new Label();
		
		backUser = new Button("VOLTAR");
		backUser.setOnAction(e -> {
			doc.clear();
			nameUser.clear();
			window.setScene(home);
		});
	
		VBox layoutUser = new VBox(7);
		layoutUser.getChildren().addAll(textUser, tableUser, textAddUser, hbUser, msgAddUser, backUser);
		layoutUser.setAlignment(Pos.TOP_CENTER);
		
		//PAGE: Livros (tabela(lista), campos para cadastro, botao add)
		textLivro = new Label("Livros");
		textLivro.setFont(new Font(25));
		
		TableColumn tcNameLivro = new TableColumn("NOME");
		tcNameLivro.setMinWidth(300);
		tcNameLivro.setMaxWidth(300);
		tcNameLivro.setCellValueFactory(
				new PropertyValueFactory<>("nome"));
		
		TableColumn tcCodeLivro = new TableColumn("CÓDIGO");
		tcCodeLivro.setMinWidth(100);
		tcCodeLivro.setMaxWidth(100);
		tcCodeLivro.setCellValueFactory(
				new PropertyValueFactory<>("codigo"));
		
		TableColumn tcTypeLivro = new TableColumn("TIPO");
		tcTypeLivro.setMinWidth(100);
		tcTypeLivro.setMaxWidth(100);
		tcTypeLivro.setCellValueFactory(
				new PropertyValueFactory<>("tipo"));
		
		tableLivro.getColumns().addAll(tcNameLivro, tcCodeLivro, tcTypeLivro);
		tableLivro.setItems(dataLivro);
		
		textAddLivro = new Label("Novo livro:");
		textAddLivro.setFont(new Font(16));
		
		bookLivro = new TextField();
		bookLivro.setPromptText("LIVRO");
		
		code = new TextField();
		code.setPromptText("CÓDIGO");
		
		typeLivro = new ComboBox<>();
		typeLivro.getItems().addAll("Texto", "Geral");
		typeLivro.setPromptText("TIPO");
		
		addLivro = new Button("ADD");
		addLivro.setOnAction(e -> cadastrarLivro(bookLivro.getText(), code.getText(), typeLivro.getValue()));
		
		HBox hbLivro = new HBox(3);
		hbLivro.getChildren().addAll(bookLivro, code, typeLivro, addLivro);
		hbLivro.setAlignment(Pos.CENTER);
		
		msgAddLivro = new Label();
		
		backLivro = new Button("VOLTAR");
		backLivro.setOnAction(e -> {
			bookLivro.clear();
			code.clear();
			window.setScene(home);
		});
		
		VBox layoutLivro = new VBox(7);
		layoutLivro.getChildren().addAll(textLivro, tableLivro, textAddLivro, hbLivro, msgAddLivro, backLivro);
		layoutLivro.setAlignment(Pos.TOP_CENTER);
		
		//PAGE: Emprestar livro (campo usuario, campo livro/codigo)
		textEmprestimos = new Label("Empréstimos");
		textEmprestimos.setFont(new Font(25));
		
		TableColumn tcBookEmp = new TableColumn("LIVRO");
		tcBookEmp.setMinWidth(250);
		tcBookEmp.setMaxWidth(250);
		tcBookEmp.setCellValueFactory(
				new PropertyValueFactory<>("livroEmprestado"));
		
		TableColumn tcUserEmp = new TableColumn("USUÁRIO");
		tcUserEmp.setMinWidth(150);
		tcUserEmp.setMaxWidth(150);
		tcUserEmp.setCellValueFactory(
				new PropertyValueFactory<>("pessoaComLivro"));
		
		TableColumn tcDateEmp = new TableColumn("DATA");
		tcDateEmp.setMinWidth(100);
		tcDateEmp.setMaxWidth(100);
		tcDateEmp.setCellValueFactory(
				new PropertyValueFactory<>("dataAluguel"));
		
		tableEmprestimo.getColumns().addAll(tcBookEmp, tcUserEmp, tcDateEmp);
		tableEmprestimo.setItems(dataEmprestimo);		
		
		textAddEmp = new Label("Novo empréstimo:");
		textAddEmp.setFont(new Font(16));
		
		userCombo = new ComboBox<>();
		userCombo.setOnAction((event) -> {
				loadLivrosNaoAlugados();
		});
		
		livroCombo = new ComboBox<>();
		
		userCombo.setVisibleRowCount(5);
		livroCombo.setVisibleRowCount(5);
		addEmp = new Button("Empréstimo");
		addEmp.setOnAction(e -> emprestarLivro(userCombo.getValue(), livroCombo.getValue()));
		
		HBox hbEmp = new HBox(3);
		hbEmp.getChildren().addAll(userCombo, livroCombo, addEmp);
		hbEmp.setAlignment(Pos.CENTER);
		
		textAddDev = new Label("Devolução:");
		textAddDev.setFont(new Font(16));
		
		userComboDev = new ComboBox<>();
		userComboDev.setOnAction((event) -> {
				loadLivrosUsuario(userComboDev.getValue());
		});
		
		livroComboDev = new ComboBox<>();
		
		userComboDev.setVisibleRowCount(5);
		livroComboDev.setVisibleRowCount(5);
		addDev = new Button("Devolução");
		addDev.setOnAction(e -> devolverLivro(userComboDev.getValue(), livroComboDev.getValue()));
		
		HBox hbDev = new HBox(3);
		hbDev.getChildren().addAll(userComboDev, livroComboDev, addDev);
		hbDev.setAlignment(Pos.CENTER);
		
		
		msgEmp = new Label();
		
		backEmprestimos = new Button("VOLTAR");
		backEmprestimos.setOnAction(e -> {
			userCombo.setValue(null);
			userComboDev.setValue(null);
			livroCombo.setValue(null);
			livroComboDev.setValue(null);
			window.setScene(home);
		});
		
		VBox layoutEmprestimos = new VBox(7);
		layoutEmprestimos.getChildren().addAll(textEmprestimos, tableEmprestimo, textAddEmp, hbEmp,
				textAddDev, hbDev, msgEmp, backEmprestimos);
		layoutEmprestimos.setAlignment(Pos.TOP_CENTER);
		
		//Verifica se quer salvar o programa antes de fechar
		window.setOnCloseRequest(e -> {
			e.consume();
			fecharPrograma();
		});
		
		atualizaListUser();
		atualizaListLivro();
		home = new Scene(layoutHome, 500, 650);
		sceneUser = new Scene(layoutUser, 500, 650);
		sceneLivro = new Scene(layoutLivro, 500, 650);
		sceneEmprestimos = new Scene(layoutEmprestimos, 500, 650);
		window.setScene(home);
		window.setTitle("BIBLIOTECA DOS PARÇA");
		window.show();
	}
	
	// Método cadastro de usuário
	private void cadastrarUsuario(String nome, String cpf, String tipo){
		Pessoa novo = GerenciaBiblioteca.findPessoaPorCPF(listPessoas, cpf);
		if (novo == null){
			if (tipo.compareTo("Aluno") == 0)
				novo = new Aluno(nome,cpf);
			if (tipo.compareTo("Professor") == 0)
			    novo = new Professor(nome,cpf);
			if (tipo.compareTo("Comunidade")== 0)
				novo = new Comunidade(nome,cpf);
			
			listPessoas.add(novo);
			atualizaListUser();
			msgAddUser.setText("Usuário cadastrado: " + nome + " (" + cpf + ")");
			nameUser.clear();
			doc.clear();
		}
		else
			msgAddUser.setText("CPF:"+ cpf + " já cadastrado!");
	}
	
	// Método cadastro de livro
	private void cadastrarLivro(String nome, String codigo, String tipo){
		Livro novo = GerenciaBiblioteca.findLivroPorCodigo(listLivros, codigo);
		if (novo == null){
			novo = new Livro(tipo,nome,codigo);
			listLivros.add(novo);
			atualizaListLivro();
			msgAddLivro.setText("Livro cadastrado: " + nome + " (" + codigo + " / " + tipo + ")");
			bookLivro.clear();
			code.clear();
		}
		else
			msgAddLivro.setText("Codigo:"+ codigo +" já cadastrado!");
	}
	
	// Método cadastro emprestimo
	private void emprestarLivro(Pessoa user, Livro livro){
		if (userCombo.getValue() == null && livroCombo.getValue() == null){
			msgEmp.setText("Selecione uma pessoa/livro para empréstimo.");
			return;
		}
		if(livro.getTipo().compareTo("texto") == 0 && user.getTipo().compareTo("comunidade") == 0){
			msgEmp.setText("Esta pessoa não pode pegar esse tipo de livro.");
			return;
		}
		Date d = new Date();//TODO
		Emprestimo novo = new Emprestimo(livro, user, d);
		user.getLivrosEmprestados().add(livro);
		listEmprestimos.add(novo);
		atualizaListEmprestimo();
		msgEmp.setText("Empréstimo do livro '" + livro + "' para " + user + " efetuado!");
		userCombo.setValue(null);
		livroCombo.setValue(null);
	}
	
	// Método cadastro devolução
	private void devolverLivro(Pessoa user, Livro livro){
		if (userComboDev.getValue() == null && livroComboDev.getValue() == null){
			msgEmp.setText("Selecione uma pessoa/livro para empréstimo.");
			return;
		}
		Date d = new Date();//TODO
		Emprestimo aRemover = new Emprestimo(livro, user, d);
		listEmprestimos.remove(aRemover);
		atualizaListEmprestimo();
		msgEmp.setText("Devolução do livro '" + livro + "' de " + user + " efetuada!");
		userComboDev.setValue(null);
		livroComboDev.setValue(null);
	}
	
	private void atualizaListUser(){
		dataUser.clear();
		userCombo.getItems().clear();
		userComboDev.getItems().clear();
		for (Pessoa p:listPessoas){
			dataUser.add(p);
			userCombo.getItems().add(p);
			userComboDev.getItems().add(p);
		}
	}
	
	private void atualizaListLivro(){
		dataLivro.clear();
		for (Livro l:listLivros)
			dataLivro.add(l);
	}
	
	private void atualizaListEmprestimo(){
		dataEmprestimo.clear();
		for(Emprestimo e:listEmprestimos)
			dataEmprestimo.add(e);
	}	
	
	private void loadLivrosUsuario(Pessoa p){
		livroComboDev.getItems().clear();
		if (p == null)
			return;
		for (Livro l:p.getLivrosEmprestados())
			livroComboDev.getItems().add(l);
	}
	
	private void loadLivrosNaoAlugados(){
		livroCombo.getItems().clear();
		ArrayList<Livro> livrosNaoAlugados = GerenciaBiblioteca.findLivrosNaoAlugados(listLivros, listEmprestimos);
		for(Livro l : livrosNaoAlugados)
			livroCombo.getItems().add(l);
	}
	
	// Método chama ConfirmBox para salvar o programa antes de fechar
	private void fecharPrograma(){
		int resp = ConfirmBox.display("Fechar programa","Deseja salvar as alterações antes de sair?");
		if(resp == 1){
			salvarPrograma();
			window.close();
		}
		if(resp == 0)
			window.close();
	}
	
	// Método para salvar alterações no CSV
	private void salvarPrograma(){
		
	alteracoesArquivo.cadastrarPessoa(listPessoas);
		
		System.out.println("As alterações foram salvas!");
	}
}
