package br.usp.icmc.ssc0103.executavel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

import javax.swing.JOptionPane;

import br.usp.icmc.ssc0103.util.*;
import br.usp.icmc.scc0103.model.*;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Principal extends Application {
	Date dataAtualPrograma = new Date();
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
		
		//carrega as listas de usuário, livro e empréstimos
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
		
		HBox hbPrinc = new HBox(2);
		options = new ComboBox<>();
		options.getItems().addAll("Usuários", "Livros", "Emprestimos");
		options.setPromptText("Selecione uma opção");
		
		select = new Button("SELECIONAR");
		select.setOnAction(e -> {
			if(options.getValue() == "Usuários"){
				atualizaListUser();
				window.setScene(sceneUser);
			}
			if(options.getValue() == "Livros"){
				atualizaListLivro();
				window.setScene(sceneLivro);
			}
			if(options.getValue() == "Emprestimos"){
				atualizaListEmprestimo();
				atualizaListUser();
				window.setScene(sceneEmprestimos);
			}
		});
		hbPrinc.getChildren().addAll(options,select);
		hbPrinc.setAlignment(Pos.CENTER);
		
		HBox hbData = new HBox(2);
		Label lblData = new Label();
		lblData.setText("Data do sistema:   ");
		DatePicker dataPicker = new DatePicker();
		dataPicker.setValue(dataAtualPrograma.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dataPicker.setEditable(false);
		dataPicker.setOnAction(e -> {
			dataAtualPrograma.setDate(dataPicker.getValue().getDayOfMonth()); 
			dataAtualPrograma.setMonth(dataPicker.getValue().getMonthValue()-1);
			dataAtualPrograma.setYear(dataPicker.getValue().getYear()-1900);
		});
		hbData.getChildren().addAll(lblData,dataPicker);
		hbData.setAlignment(Pos.CENTER);
		
		VBox layoutHome = new VBox(30);
		layoutHome.getChildren().addAll(imgView, textHome, hbPrinc,hbData);
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
		
		TableColumn<Pessoa,Date> tcBanUser = new TableColumn("SUSPENSÃO");
		tcBanUser.setMinWidth(100);
		tcBanUser.setMaxWidth(200);
		tcBanUser.setCellValueFactory(
				new PropertyValueFactory<>("diaSuspensao"));
		tcBanUser.setCellFactory(column -> {
		    return new TableCell<Pessoa,Date>() {
		        @Override
		        protected void updateItem(Date item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		                setText(null);
		                setStyle("");
		            } else {
		            	if(item.before(dataAtualPrograma))
		                	setText("");
		            	else{
		            		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		            		setText(df.format(item));
		            	}
		            }
		        }
		    };
		});
		
		
		tableUser.getColumns().addAll(tcNameUser, tcDocUser, tcTypeUser, tcBanUser);
		tableUser.setItems(dataUser);

		HBox hbSalvarUser = new HBox(1);
		Image imgSalvarUser = new Image("http://icon-icons.com/icons2/9/PNG/16/diskette_save_saveas_1514.png");
		Button btnSalvarUser = new Button();
		btnSalvarUser.setGraphic(new ImageView(imgSalvarUser));
		btnSalvarUser.setMinSize(18, 18);
		btnSalvarUser.setMaxSize(22, 22);
		btnSalvarUser.setTooltip(new Tooltip("Salvar usuários no arquivo CSV"));
		btnSalvarUser.setOnAction(e -> {
			alteracoesArquivo.cadastrarPessoa(listPessoas);
			JOptionPane.showMessageDialog(null, "Usuários salvos com sucesso.");
		});
		
		hbSalvarUser.getChildren().addAll(btnSalvarUser);
		hbSalvarUser.setAlignment(Pos.CENTER_RIGHT);
		
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
			msgAddUser.setText("");
			doc.clear();
			nameUser.clear();
			window.setScene(home);
		});
	
		VBox layoutUser = new VBox(7);
		layoutUser.getChildren().addAll(textUser, tableUser, hbSalvarUser,textAddUser, hbUser, msgAddUser, backUser);
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
		
		HBox hbSalvar = new HBox();
		Image imgSalvarLivro = new Image("http://icon-icons.com/icons2/9/PNG/16/diskette_save_saveas_1514.png");
		Button btnSalvarLivro = new Button();
		btnSalvarLivro.setGraphic(new ImageView(imgSalvarLivro));
		btnSalvarLivro.setMinSize(18, 18);
		btnSalvarLivro.setMaxSize(22, 22);
		btnSalvarLivro.setTooltip(new Tooltip("Salvar livros no arquivo CSV"));
		hbSalvar.getChildren().addAll(btnSalvarLivro);
		hbSalvar.setAlignment(Pos.CENTER_RIGHT);
		
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
			msgAddLivro.setText("");
			bookLivro.clear();
			code.clear();
			window.setScene(home);
		});
		
		VBox layoutLivro = new VBox(7);
		layoutLivro.getChildren().addAll(textLivro, tableLivro, hbSalvar,textAddLivro, hbLivro, msgAddLivro, backLivro);
		layoutLivro.setAlignment(Pos.TOP_CENTER);
		
		//PAGE: Emprestar livro (campo usuario, campo livro/codigo)
		textEmprestimos = new Label("Empréstimos");
		textEmprestimos.setFont(new Font(25));
		
		TableColumn tcBookEmp = new TableColumn("LIVRO");
		tcBookEmp.setMinWidth(150);
		tcBookEmp.setMaxWidth(200);
		tcBookEmp.setCellValueFactory(
				new PropertyValueFactory<>("livroEmprestado"));
		
		TableColumn tcUserEmp = new TableColumn("USUÁRIO");
		tcUserEmp.setMinWidth(150);
		tcUserEmp.setMaxWidth(150);
		tcUserEmp.setCellValueFactory(
				new PropertyValueFactory<>("pessoaComLivro"));
		
		TableColumn<Emprestimo,Date> tcDateEmp = new TableColumn("DATA");
		tcDateEmp.setMinWidth(100);
		tcDateEmp.setMaxWidth(200);
		tcDateEmp.setCellValueFactory(
				new PropertyValueFactory<>("dataAluguel"));
		tcDateEmp.setCellFactory(column -> {
		    return new TableCell<Emprestimo,Date>() {
		        @Override
		        protected void updateItem(Date item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		                setText(null);
		                setStyle("");
		            } else {
		            	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		                setText(df.format(item));
		            }
		        }
		    };
		});
		
		TableColumn<Emprestimo,Date> tcDateEmpDevolucao = new TableColumn("DATA DEVOLUCAO");
		tcDateEmpDevolucao.setMinWidth(100);
		tcDateEmpDevolucao.setMaxWidth(200);
		tcDateEmpDevolucao.setCellValueFactory(
				new PropertyValueFactory<>("dataDevolucao"));
		tcDateEmpDevolucao.setCellFactory(column -> {
		    return new TableCell<Emprestimo,Date>() {
		        @Override
		        protected void updateItem(Date item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		                setText(null);
		                setStyle("");
		            } else {
		            	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		                setText(df.format(item));
		            }
		        }
		    };
		});
		
		tableEmprestimo.getColumns().addAll(tcBookEmp, tcUserEmp, tcDateEmp,tcDateEmpDevolucao);
		tableEmprestimo.setItems(dataEmprestimo);		
		
		Label textAtrasado = new Label("Mostrar somente atrasados");
		CheckBox chkAtrasado = new CheckBox();
		chkAtrasado.setOnAction((event) -> {
			carregaAtrasados(chkAtrasado.isSelected());
		});
		Image imgSalvar = new Image("http://icon-icons.com/icons2/9/PNG/16/diskette_save_saveas_1514.png");
		Button btnSalvar = new Button();
		btnSalvar.setGraphic(new ImageView(imgSalvar));
		btnSalvar.setMinSize(18, 18);
		btnSalvar.setMaxSize(22, 22);
		btnSalvar.setTooltip(new Tooltip("Salvar empréstimos no arquivo CSV"));
		
		HBox hbAtrasados = new HBox(2);
		hbAtrasados.getChildren().addAll(textAtrasado,chkAtrasado,btnSalvar);
		hbAtrasados.setAlignment(Pos.CENTER_RIGHT);
		
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
			msgEmp.setText("");
			userCombo.setValue(null);
			userComboDev.setValue(null);
			livroCombo.setValue(null);
			livroComboDev.setValue(null);
			window.setScene(home);
		});
		
		VBox layoutEmprestimos = new VBox(5);
		layoutEmprestimos.getChildren().addAll(textEmprestimos, tableEmprestimo,hbAtrasados, textAddEmp, hbEmp,
				textAddDev, hbDev, msgEmp, backEmprestimos);
		layoutEmprestimos.setAlignment(Pos.TOP_CENTER);
		
		//Verifica se quer salvar o programa antes de fechar
		window.setOnCloseRequest(e -> {
			e.consume();
			fecharPrograma();
		});
		
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
		Date d = (Date) dataAtualPrograma.clone();
		if(user.estaSuspenso(d)){
			msgEmp.setText("Esta pessoa está suspensa.");
			return;
		}
		if(!user.pegaEmprestadoLivro(livro)){
			msgEmp.setText("Esta pessoa já está com muitos livros");
			return;
		}
		Emprestimo novo = new Emprestimo(livro, user, d);
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
		Date d = (Date) dataAtualPrograma.clone();
		Emprestimo aRemover = GerenciaBiblioteca.findEmprestimo(user, livro, listEmprestimos);
		int diasAtrasados = GerenciaBiblioteca.calculaTempoAtrasado(aRemover, d);
		if (diasAtrasados > 0){
			GerenciaBiblioteca.suspendeUsuario(user, d, diasAtrasados);
			JOptionPane.showMessageDialog(null, "Usuário suspenso por: "+diasAtrasados+" dias");
		}
		user.devolverLivro(livro);
		listEmprestimos.remove(aRemover);
		atualizaListEmprestimo();
		msgEmp.setText("Devolução do livro '" + livro + "' de " + user + " efetuada!");
		userComboDev.setValue(null);
		livroComboDev.setValue(null);
	}
	
	
	//atualiza a lista de usuarios
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
	
	//atualiza lista de livros
	private void atualizaListLivro(){
		dataLivro.clear();
		for (Livro l:listLivros)
			dataLivro.add(l);
	}
	
	//atualiza lista de emprestimos
	private void atualizaListEmprestimo(){
		dataEmprestimo.clear();
		for(Emprestimo e:listEmprestimos)
			dataEmprestimo.add(e);
	}	
	
	//load dos livros que o usuario pegou para adicionar no combobox de devolucao
	private void loadLivrosUsuario(Pessoa p){
		livroComboDev.getItems().clear();
		if (p == null)
			return;
		for (Livro l:p.getLivrosEmprestados())
			livroComboDev.getItems().add(l);
	}
	
	//carrega tabela com somente livros atrasados
	private void carregaAtrasados(Boolean deveCarregar){
		if(deveCarregar){
			Date d = dataAtualPrograma;
			dataEmprestimo.clear();
			ArrayList<Emprestimo> listaAtrasados = GerenciaBiblioteca.findLivrosAtrasados(listEmprestimos, d);
			for(Emprestimo e:listaAtrasados)
				dataEmprestimo.add(e);
		}
		else
			atualizaListEmprestimo();	
	}
	
	//load dos livros que nao foram pegos no combobox de empréstimo
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
