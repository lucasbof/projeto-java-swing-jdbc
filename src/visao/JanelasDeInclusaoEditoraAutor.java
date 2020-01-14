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

class JanelasDeInclusaoEditoraAutor {

	private JDialog janelaIncluiAutor;
	private JTextField tfNomeAutor;
	private JTextField tfSobrenome;
	private JDialog janelaIncluiEditora;
	private JTextField tfNomeEditora;
	private JTextField tfUrl;

	//Janela de inclusão de autor
	void mostraJanelaInclusaoAutor(JFrame janelaPai, ActionListener al) {
		janelaIncluiAutor = new JDialog(janelaPai);
		janelaIncluiAutor.setLayout(new BorderLayout());
		janelaIncluiAutor.setModal(true);
		janelaIncluiAutor.setTitle("Inclusão de Autor");
		JPanel painelCampos = new JPanel(new GridBagLayout());
		JLabel cNome = new JLabel("Nome");
		tfNomeAutor = new JTextField();
		tfNomeAutor.setPreferredSize(new Dimension(200, 20));
		JLabel erroNome = new JLabel("O nome não pode ser só espaço(s) ou vazio");
		JLabel cSobrenome = new JLabel("Sobrenome");
		JLabel erroSobrenome = new JLabel("o sobrenome não pode ser só espaço(s) ou vazio");
		tfSobrenome = new JTextField();
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
		janelaIncluiAutor.add(painelCampos, BorderLayout.PAGE_START);
		
		JPanel painelBotoesSalvarCancelar = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton btSalvarInclusaoAutor = new JButton("Adicionar");
		btSalvarInclusaoAutor.setEnabled(false);
		btSalvarInclusaoAutor.addActionListener(al);
		DocumentListener cpInclusaoAutor = new DocumentListener() {
			private void habitaBotao() {
				if(tfNomeAutor.getText().trim().isEmpty() || tfSobrenome.getText().trim().isEmpty()) {
					btSalvarInclusaoAutor.setEnabled(false);
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
					btSalvarInclusaoAutor.setEnabled(true);
				}
				
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				habitaBotao();
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
				habitaBotao();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				habitaBotao();				
			}
		};
		tfNomeAutor.getDocument().addDocumentListener(cpInclusaoAutor);
		tfSobrenome.getDocument().addDocumentListener(cpInclusaoAutor);
		JButton btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				janelaIncluiAutor.dispose();
			}
		});
		painelBotoesSalvarCancelar.add(btSalvarInclusaoAutor);
		painelBotoesSalvarCancelar.add(btCancelar);
		
		janelaIncluiAutor.add(painelBotoesSalvarCancelar, BorderLayout.PAGE_END);
		
		janelaIncluiAutor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janelaIncluiAutor.setSize(new Dimension(600, 120));
		janelaIncluiAutor.setVisible(true);	
	}
	
	void fechaJanelaInclusaoAutor() {
		janelaIncluiAutor.dispose();
	}

	Autor getNovoAutor() {
		Autor autor = new Autor(-1, tfNomeAutor.getText(), tfSobrenome.getText());
		return autor;
	}
	
	//Janela de inclusão de editora
	void mostraJanelaInclusaoEditora(JFrame janelaPai, ActionListener al) {
		janelaIncluiEditora = new JDialog(janelaPai);
		janelaIncluiEditora.setLayout(new BorderLayout());
		janelaIncluiEditora.setModal(true);
		janelaIncluiEditora.setTitle("Inclusão de Editora");
		JPanel painelCampos = new JPanel(new GridBagLayout());
		JLabel cNome = new JLabel("Nome");
		tfNomeEditora = new JTextField();
		tfNomeEditora.setPreferredSize(new Dimension(300, 20));
		JLabel erroNome = new JLabel("O nome não pode ser só espaço(s) ou vazio");
		JLabel cUrl = new JLabel("URL");
		JLabel erroUrl = new JLabel("a url não pode ser só espaço(s) ou vazio");
		tfUrl = new JTextField();
		tfUrl.setPreferredSize(new Dimension(300, 20));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 5;
		painelCampos.add(cNome, c);
		c.gridx = 1;
		c.gridy = 0;
		painelCampos.add(tfNomeEditora, c);
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
		janelaIncluiEditora.add(painelCampos, BorderLayout.PAGE_START);
		
		JPanel painelBotoesSalvarCancelar = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton btSalvarInclusaoEditora = new JButton("Adicionar");
		btSalvarInclusaoEditora.addActionListener(al);
		btSalvarInclusaoEditora.setEnabled(false);
		
		//Comportamento de validação de inclusão de editora
		DocumentListener cpInclusaoValidacaoEditora = new DocumentListener() {
			private void habitaBotao(DocumentEvent e) {
				if(tfNomeEditora.getText().trim().isEmpty() || tfUrl.getText().trim().isEmpty()) {
					btSalvarInclusaoEditora.setEnabled(false);
					if(tfNomeEditora.getText().trim().isEmpty()) {
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
					btSalvarInclusaoEditora.setEnabled(true);
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				habitaBotao(e);				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				Runnable formato1 = new Runnable() {
					@Override
					public void run() {
						String text = tfNomeEditora.getText();
						if (text.length() > 30 ) {
							tfNomeEditora.setText(text.substring(0, text.length() - 1));
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
				habitaBotao(e);				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				habitaBotao(e);				
			}
		};
		tfNomeEditora.getDocument().addDocumentListener(cpInclusaoValidacaoEditora);
		tfUrl.getDocument().addDocumentListener(cpInclusaoValidacaoEditora);
		JButton btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				janelaIncluiEditora.dispose();
			}
		});
		painelBotoesSalvarCancelar.add(btSalvarInclusaoEditora);
		painelBotoesSalvarCancelar.add(btCancelar);
		
		janelaIncluiEditora.add(painelBotoesSalvarCancelar, BorderLayout.PAGE_END);
		
		janelaIncluiEditora.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janelaIncluiEditora.setSize(new Dimension(650, 120));
		janelaIncluiEditora.setVisible(true);	
	}
	
	Editora getNovaEditora() {
		Editora editora = new Editora(-1, tfNomeEditora.getText(), tfUrl.getText());
		return editora;
	}
	
	void fechaJanelaInclusaoEditora() {
		janelaIncluiEditora.dispose();
	}
}
