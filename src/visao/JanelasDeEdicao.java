package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import modelo.entidades.Autor;
import modelo.entidades.Editora;
import modelo.entidades.Livro;

class JanelasDeEdicao {
	
	private JDialog janelaEditaEditora;
	private Editora editora;
	private JTextField tfNome;
	private JTextField tfUrl;
	private Autor autor;
	private JDialog janelaEditaAutor;
	private JTextField tfNomeAutor;
	private JTextField tfSobrenome;
	private Livro livro;
	private JTextField tfTitulo;
	private JTextField tfPreco;
	private JDialog janelaEditaLivro;
	
	//Janela de edição de livro
	void mostraJanelaEditaLivro(Livro obj, JanelaPrincipal janelaPai, ActionListener cBtSalvar) {
		livro = obj;
		janelaEditaLivro = new JDialog(janelaPai);
		janelaEditaLivro.setLayout(new BorderLayout());
		janelaEditaLivro.setModal(true);
		janelaEditaLivro.setTitle("Edição de Livro");
		JPanel painelCampos = new JPanel(new GridBagLayout());
		JLabel cIsbn = new JLabel("ISBN");
		JTextField tfIsbn = new JTextField(livro.getIsbn());
		tfIsbn.setPreferredSize(new Dimension(345, 20));
		tfIsbn.setEditable(false);
		JLabel vazio = new JLabel();
		JLabel cTitulo = new JLabel("Título");
		tfTitulo = new JTextField(livro.getTitulo());
		tfTitulo.setPreferredSize(new Dimension(345, 20));
		JLabel erroTitulo = new JLabel();
		JLabel cPreco = new JLabel("Preço");
		JLabel erroPreco = new JLabel();
		tfPreco = new JTextField(String.format("%.2f", livro.getPreco()));
		tfPreco.setPreferredSize(new Dimension(345, 20));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 5;
		painelCampos.add(cIsbn, c);
		c.gridx = 1;
		c.gridy = 0;
		painelCampos.add(tfIsbn, c);
		c.gridx = 2;
		c.gridy = 0;
		painelCampos.add(vazio, c);
		c.gridx = 0;
		c.gridy = 1;
		painelCampos.add(cTitulo, c);
		c.gridx = 1;
		c.gridy = 1;
		painelCampos.add(tfTitulo, c);
		c.gridx = 2;
		c.gridy = 1;
		painelCampos.add(erroTitulo, c);
		c.gridx = 0;
		c.gridy = 2;
		painelCampos.add(cPreco, c);
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1;
		painelCampos.add(tfPreco, c);
		c.gridx = 2;
		c.gridy = 2;
		painelCampos.add(erroPreco, c);
		janelaEditaLivro.add(painelCampos, BorderLayout.PAGE_START);
		
		JPanel painelBotoesSalvarCancelar = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton btAtualizarLivro = new JButton("Atualizar");
		btAtualizarLivro.addActionListener(cBtSalvar);
		DocumentListener cpEdicaoLivro = new DocumentListener() {
			private void habilitaBotao() {
				if(tfTitulo.getText().trim().isEmpty() || tfPreco.getText().trim().isEmpty()) {
					btAtualizarLivro.setEnabled(false);
					if(tfTitulo.getText().trim().isEmpty() || tfTitulo.getText().length() > 60) {
						erroTitulo.setText("O titulo não pode ser só espaço(s) ou vazio");
					}
					else {
						erroTitulo.setText("");
					}
					if(tfPreco.getText().trim().isEmpty() || (tfPreco.getText().length() > 10)) {
						erroPreco.setText("o preço não pode ser só espaço(s) ou vazio");
					}
					else {
						erroPreco.setText("");
					}
				}
				else if(!tfPreco.getText().trim().isEmpty()){
					erroPreco.setText("");
					erroTitulo.setText("");
					btAtualizarLivro.setEnabled(true);
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				habilitaBotao();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				Runnable formato1 = new Runnable() {
					@Override
					public void run() {
						String texto = tfTitulo.getText();
						if (texto.length() > 60) {
							tfTitulo.setText(texto.substring(0, texto.length() - 1));
							erroTitulo.setText("o título só tem 60 caracteres");
						}
					}
				};
				SwingUtilities.invokeLater(formato1);
				Runnable formato2 = new Runnable() {
		            @Override
		            public void run() {
		                String text = tfPreco.getText();
		                if(!text.matches("\\d*(\\.\\d{0,2})?") || (text.length() > 10) || 
		                		(text.length() > 9 && text.substring(text.length() -1).contains("."))) {
		                	tfPreco.setText(text.substring(0,text.length()-1));
		                	erroPreco.setText("o preço é um valor numérico de até 10 caracteres com até 2 casas decimais");
		                }
		            }
		        };
		        SwingUtilities.invokeLater(formato2);
				habilitaBotao();
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				habilitaBotao();
				
			}
		};
		tfTitulo.getDocument().addDocumentListener(cpEdicaoLivro);
		tfPreco.getDocument().addDocumentListener(cpEdicaoLivro);
		JButton btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				janelaEditaLivro.dispose();
			}
		});
		painelBotoesSalvarCancelar.add(btAtualizarLivro);
		painelBotoesSalvarCancelar.add(btCancelar);
		
		janelaEditaLivro.add(painelBotoesSalvarCancelar, BorderLayout.PAGE_END);
		
		janelaEditaLivro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janelaEditaLivro.setSize(new Dimension(860, 150));
		janelaEditaLivro.setVisible(true);	
	}

	Livro getLivroEditado() {
		livro.setTitulo(tfTitulo.getText());
		livro.setPreco(Double.valueOf(tfPreco.getText()));
		return livro;
	}

	void fechaJanelaEdicaoLivro() {
		janelaEditaLivro.dispose();		
	}
	
	//Janela de Edição de autor
	void mostraJanelaEditaAutor(Autor obj, JFrame janelaPai, ActionListener al) {
		autor = obj;
		janelaEditaAutor = new JDialog(janelaPai);
		janelaEditaAutor.setLayout(new BorderLayout());
		janelaEditaAutor.setModal(true);
		janelaEditaAutor.setTitle("Edição de Autor");
		JPanel painelCampos = new JPanel(new GridBagLayout());
		JLabel cNome = new JLabel("Nome");
		tfNomeAutor = new JTextField(autor.getNome());
		tfNomeAutor.setPreferredSize(new Dimension(200, 20));
		JLabel erroNome = new JLabel();
		JLabel cSobrenome = new JLabel("Sobrenome");
		JLabel erroSobrenome = new JLabel();
		tfSobrenome = new JTextField(autor.getSobrenome());
		tfSobrenome.setPreferredSize(new Dimension(200, 20));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 5;
		painelCampos.add(cNome, c);
		c.gridx = 1;
		c.gridy = 0;
		painelCampos.add(tfNomeAutor, c);
		c.gridx = 2;
		c.gridy = 0;
		painelCampos.add(erroNome, c);
		c.gridx = 0;
		c.gridy = 1;
		painelCampos.add(cSobrenome, c);
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		painelCampos.add(tfSobrenome, c);
		c.gridx = 2;
		c.gridy = 1;
		painelCampos.add(erroSobrenome, c);
		janelaEditaAutor.add(painelCampos, BorderLayout.PAGE_START);
		
		JPanel painelBotoesSalvarCancelar = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton btAtualizarAutor = new JButton("Atualizar");
		btAtualizarAutor.addActionListener(al);
		DocumentListener cpValidacaoEdicaoAutor = new DocumentListener() {
			private void habilitaBotao() {
				if(tfNomeAutor.getText().trim().isEmpty() || tfSobrenome.getText().trim().isEmpty()) {
					btAtualizarAutor.setEnabled(false);
					if(tfNomeAutor.getText().trim().isEmpty()) {
						erroNome.setText("O nome não pode ser só espaço(s) ou vazio");
					}
					else {
						erroNome.setText("");
					}
					if(tfSobrenome.getText().trim().isEmpty()) {
						erroSobrenome.setText("o sobrenome não pode ser só espaço(s) ou vazio");
					}
					else {
						erroSobrenome.setText("");
					}
				}
				else {
					erroNome.setText("");
					erroSobrenome.setText("");
					btAtualizarAutor.setEnabled(true);
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				habilitaBotao();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				Runnable formato1 = new Runnable() {
					@Override
					public void run() {
						String text = tfNomeAutor.getText();
						if (text.length() > 25 ) {
							tfNomeAutor.setText(text.substring(0, text.length() - 1));
							erroNome.setText("O nome tem até 25 caracteres");
						}
					}
				};
				SwingUtilities.invokeLater(formato1);
				Runnable formato2 = new Runnable() {
					@Override
					public void run() {
						String text = tfSobrenome.getText();
						if (text.length() > 25 ) {
							tfSobrenome.setText(text.substring(0, text.length() - 1));
							erroSobrenome.setText("O sobrenome tem até 25 caracteres");
						}
					}
				};
				SwingUtilities.invokeLater(formato2);
				habilitaBotao();
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				habilitaBotao();
				
			}
		};
		tfNomeAutor.getDocument().addDocumentListener(cpValidacaoEdicaoAutor);
		tfSobrenome.getDocument().addDocumentListener(cpValidacaoEdicaoAutor);
		JButton btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				janelaEditaAutor.dispose();
			}
		});
		painelBotoesSalvarCancelar.add(btAtualizarAutor);
		painelBotoesSalvarCancelar.add(btCancelar);
		
		janelaEditaAutor.add(painelBotoesSalvarCancelar, BorderLayout.PAGE_END);
		
		janelaEditaAutor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janelaEditaAutor.setSize(new Dimension(600, 120));
		janelaEditaAutor.setVisible(true);	
	}
	
	Autor getAutorEditado() {
		autor.setNome(tfNomeAutor.getText());
		autor.setSobrenome(tfSobrenome.getText());
		return autor;
	}
	
	void fechaJanelaEdicaoAutor() {
		janelaEditaAutor.dispose();
	}
	
	//Janela de Edição de editora
	void mostraJanelaEditaEditora(Editora obj, JFrame janelaPai, ActionListener al) {
		editora = obj;
		janelaEditaEditora = new JDialog(janelaPai);
		janelaEditaEditora.setLayout(new BorderLayout());
		janelaEditaEditora.setModal(true);
		janelaEditaEditora.setTitle("Edição de Editora");
		JPanel painelCampos = new JPanel(new GridBagLayout());
		JLabel cNome = new JLabel("Nome");
		tfNome = new JTextField(editora.getNome());
		tfNome.setPreferredSize(new Dimension(300, 20));
		JLabel erroNome = new JLabel();
		JLabel cUrl = new JLabel("URL");
		JLabel erroUrl = new JLabel();
		tfUrl = new JTextField(editora.getUrl());
		tfUrl.setPreferredSize(new Dimension(300, 20));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 5;
		painelCampos.add(cNome, c);
		c.gridx = 1;
		c.gridy = 0;
		painelCampos.add(tfNome, c);
		c.gridx = 2;
		c.gridy = 0;
		painelCampos.add(erroNome, c);
		c.gridx = 0;
		c.gridy = 1;
		painelCampos.add(cUrl, c);
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		painelCampos.add(tfUrl, c);
		c.gridx = 2;
		c.gridy = 1;
		painelCampos.add(erroUrl, c);
		janelaEditaEditora.add(painelCampos, BorderLayout.PAGE_START);
		
		JPanel painelBotoesSalvarCancelar = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton btAtualizarEditora = new JButton("Atualizar");
		btAtualizarEditora.addActionListener(al);
		//comportamento de validação dos JTextFields da editora
		DocumentListener cpValidacaoEdicaoEditora = new DocumentListener() {
			private void habilitaBotao() {
				if(tfNome.getText().trim().isEmpty() || tfUrl.getText().trim().isEmpty()) {
					btAtualizarEditora.setEnabled(false);
					if(tfNome.getText().trim().isEmpty()) {
						erroNome.setText("O nome não pode ser só espaço(s) ou vazio");
					}
					else {
						erroNome.setText("");
					}
					if(tfUrl.getText().trim().isEmpty()) {
						erroUrl.setText("a url não pode ser só espaço(s) ou vazio");
					}
					else {
						erroUrl.setText("");
					}
				}
				else {
					erroNome.setText("");
					erroUrl.setText("");
					btAtualizarEditora.setEnabled(true);
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				habilitaBotao();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				Runnable formato1 = new Runnable() {
					@Override
					public void run() {
						String text = tfNome.getText();
						if (text.length() > 30 ) {
							tfNome.setText(text.substring(0, text.length() - 1));
							erroNome.setText("O nome tem até 30 caracteres");
						}
					}
				};
				SwingUtilities.invokeLater(formato1);
				Runnable formato2 = new Runnable() {
					@Override
					public void run() {
						String text = tfUrl.getText();
						if (text.length() > 80 || text.contains(" ")) {
							tfUrl.setText(text.substring(0, text.length() - 1));
							erroUrl.setText("A URL tem até 80 caracteres e é sem espaço(s)");
						}
					}
				};
				SwingUtilities.invokeLater(formato2);
				habilitaBotao();	
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				habilitaBotao();				
			}
		};
		tfNome.getDocument().addDocumentListener(cpValidacaoEdicaoEditora);
		tfUrl.getDocument().addDocumentListener(cpValidacaoEdicaoEditora);
		JButton btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				janelaEditaEditora.dispose();
			}
		});
		painelBotoesSalvarCancelar.add(btAtualizarEditora);
		painelBotoesSalvarCancelar.add(btCancelar);
		
		janelaEditaEditora.add(painelBotoesSalvarCancelar, BorderLayout.PAGE_END);
		
		janelaEditaEditora.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janelaEditaEditora.setSize(new Dimension(650, 120));
		janelaEditaEditora.setVisible(true);	
	}
	
	Editora getEditoraEditada() {
		editora.setNome(tfNome.getText());
		editora.setUrl(tfUrl.getText());
		return editora;
	}
	
	void fechaJanelaEdicaoEditora() {
		janelaEditaEditora.dispose();
	}
}
