package br.usp.icmc.ssc0103.executavel;

import com.sun.xml.internal.bind.v2.model.core.ID;

import br.usp.icmc.ssc0103.util.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Principal extends Application {

	Stage window;
	Scene home, cadUser, cadLivro, emprestar, devolver;
	Button select, confirm, confirm2, confirm3, confirm4;
	Button back, back2, back3, back4;
	Image img;
	ImageView imgView;
	Label textHome, textCadUser, textCadLivro, textEmprestar, textDevolver;
	Label msgCadUser, msgCadLivro, msgEmprestar, msgDevolver;
	TextField name, name2, name3, doc;
	TextField livro, livro2, livro3, code;
	ComboBox<String> options, type;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		window = primaryStage;
		//PAGE: Home (Imagem, texto, menu de escolha, botão ok)
		img = new Image("http://icons.iconarchive.com/icons/itzikgur/my-seven/512/Books-1-icon.png");
		imgView = new ImageView();
		imgView.setImage(img);
		imgView.setFitWidth(200);
		imgView.setPreserveRatio(true);
		
		textHome = new Label("Sistema para Bibliotecas");
		textHome.setFont(new Font(30));
		
		options = new ComboBox<>();
		options.getItems().addAll("Cadastrar usuário",
				"Cadastrar livro", "Emprestar livro", "Devolver livro");
		options.setPromptText("Selecione uma opção");
		
		select = new Button("OK");
		select.setOnAction(e -> {
			if(options.getValue() == "Cadastrar usuário"){
				window.setScene(cadUser);
			}
			if(options.getValue() == "Cadastrar livro"){
				window.setScene(cadLivro);
			}
			if(options.getValue() == "Emprestar livro"){
				window.setScene(emprestar);
			}
			if(options.getValue() == "Devolver livro"){
				window.setScene(devolver);
			}
		});
		
		VBox layoutHome = new VBox(30);
		layoutHome.getChildren().addAll(imgView, textHome, options, select);
		layoutHome.setAlignment(Pos.CENTER);
		
		//PAGE: Cadastro usuário (menu de escolha, campos de texto, botão ok e voltar)
		
		textCadUser = new Label("Cadastro de usuário");
		textCadUser.setFont(new Font(25));
		
		name = new TextField();
		name.setPromptText("Nome");
		
		doc = new TextField();
		doc.setPromptText("CPF");
		
		msgCadUser = new Label();
		
		confirm = new Button("CONFIRMAR");
		confirm.setOnAction(e -> cadastrarUsuario(name.getText(), doc.getText()));
		
		back = new Button("VOLTAR");
		back.setOnAction(e -> {
			doc.clear();
			name.clear();
			window.setScene(home);
		});
		
		VBox layoutCadUser = new VBox(20);
		layoutCadUser.getChildren().addAll(textCadUser, name, doc, msgCadUser, confirm, back);
		layoutCadUser.setAlignment(Pos.CENTER);
		
		//PAGE: Cadastro livro (campo nome, campo tipo, campo codigo, botao ok e voltar)
		
		textCadLivro = new Label("Cadastro de livro");
		textCadLivro.setFont(new Font(25));
		
		livro = new TextField();
		livro.setPromptText("LIVRO");
		
		code = new TextField();
		code.setPromptText("CÓDIGO");
		
		type = new ComboBox<>();
		type.getItems().addAll("Texto", "Geral");
		type.setPromptText("TIPO");
		
		msgCadLivro = new Label();
		
		confirm2 = new Button("CONFIRMAR");
		confirm2.setOnAction(e -> cadastrarLivro(livro.getText(), code.getText(), type.getValue()));
		
		back2 = new Button("VOLTAR");
		back2.setOnAction(e -> {
			livro.clear();
			code.clear();
			window.setScene(home);
		});
		
		VBox layoutCadLivro = new VBox(20);
		layoutCadLivro.getChildren().addAll(textCadLivro, livro, code, type, msgCadLivro, confirm2, back2);
		layoutCadLivro.setAlignment(Pos.CENTER);
		
		//Verifica se quer salvar o programa antes de fechar
		window.setOnCloseRequest(e -> {
			e.consume();
			fecharPrograma();
		});
		
		back3 = new Button("VOLTAR");
		back3.setOnAction(e -> {
			options.setPromptText("Selecione uma opção");
			window.setScene(home);
		});
		
		VBox layoutEmprestar = new VBox(20);
		layoutEmprestar.getChildren().addAll(back3);
		
		back4 = new Button("VOLTAR");
		back4.setOnAction(e -> {
			options.setPromptText("Selecione uma opção");
			window.setScene(home);
		});
				
		VBox layoutDevolver = new VBox(20);
		layoutDevolver.getChildren().addAll(back4);
		
		home = new Scene(layoutHome, 500, 500);
		cadUser = new Scene(layoutCadUser, 500, 500);
		cadLivro = new Scene(layoutCadLivro, 500, 500);
		emprestar = new Scene(layoutEmprestar, 500, 500);
		devolver = new Scene(layoutDevolver, 500, 500);
		
		window.setScene(home);
		window.setTitle("BIBLIOTECA DOS PARÇA");
		window.show();
	}
	
	private void cadastrarUsuario(String nome, String cpf){
		msgCadUser.setText("Usuário cadastrado: " + nome + " (" + cpf + ")");
		name.clear();
		doc.clear();
	}
	
	private void cadastrarLivro(String nome, String codigo, String tipo){
		msgCadLivro.setText("Livro cadastrado: " + nome + " (" + codigo + " / " + tipo + ")");
		livro.clear();
		code.clear();
	}
	
	private void fecharPrograma(){
		int resp = ConfirmBox.display("Fechar programa","Deseja salvar as alterações antes de sair?");
		if(resp == 1){
			salvarPrograma();
			window.close();
		}
		if(resp == 0)
			window.close();
	}
	
	private void salvarPrograma(){
		System.out.println("As alterações foram salvas!");
	}
}
