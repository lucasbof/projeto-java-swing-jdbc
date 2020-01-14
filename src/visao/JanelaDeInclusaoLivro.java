package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import modelo.entidades.Autor;
import modelo.entidades.Editora;
import modelo.entidades.Livro;

class JanelaDeInclusaoLivro {

	private JDialog janelaInclusaoDeLivro;
	private JTextField tfTitulo;
	private JTextField tfPreco;
	private JTextField tfIsbn1;
	private JTextField tfIsbn2;
	private JTextField tfIsbn3;
	private JTextField tfIsbn4;
	private JComboBox<Editora> cBEditoras;

	private DefaultTableModel dtmAutoresDoLivro;
	private Autor autorSelecionadoDoLivro;
	private DefaultTableModel dtmOutrosAutores;
	private Autor autorSelecionadoOutrosAutores;
	private int linhaOutrosAutores;
	private int linhaAutorDoLivro;
	private Set<Autor> autores;
	private JButton btSalvarEdicaoEditora;
	private boolean tabelaOk;
	private boolean camposOk;
	private JLabel erroIsbn;
	private JLabel erroTitulo;
	private JLabel erroPreco;
	private JLabel erroAutores;

	void mostraJanelaAdionacionaLivro(Set<Editora> editoras, Set<Autor> listaAutores, JFrame janelaPai,
			Set<String> isbns, ActionListener cBtAdicionar) {
		tabelaOk = false;
		camposOk = false;
		autores = listaAutores;
		janelaInclusaoDeLivro = new JDialog(janelaPai);
		janelaInclusaoDeLivro.setLayout(new BorderLayout());
		janelaInclusaoDeLivro.setModal(true);
		janelaInclusaoDeLivro.setTitle("Inclusão de Livro");
		JPanel painelCampos = new JPanel(new GridBagLayout());
		JPanel painelIsbn = new JPanel();
		painelIsbn.setPreferredSize(new Dimension(345, 20));
		painelIsbn.setLayout(new BoxLayout(painelIsbn, BoxLayout.LINE_AXIS));
		JLabel cIsbn = new JLabel("ISBN");
		tfIsbn1 = new JTextField();
		JLabel ctIsbn1 = new JLabel("-");
		tfIsbn2 = new JTextField();
		JLabel ctIsbn2 = new JLabel("-");
		tfIsbn3 = new JTextField();
		JLabel ctIsbn4 = new JLabel("-");
		tfIsbn4 = new JTextField();
		Editora[] aEditoras = editoras.toArray(new Editora[editoras.size()]);
		JLabel cEditoras = new JLabel("Editora");
		cBEditoras = new JComboBox<>(aEditoras);
		cBEditoras.setPreferredSize(new Dimension(345, 20));
		erroAutores = new JLabel("Escolha pelo menos 1 autor para o livro");

		erroIsbn = new JLabel("Todos os campos do isbn devem estar preenchidos e ter 10 digitos no total");
		painelIsbn.add(tfIsbn1);
		painelIsbn.add(ctIsbn1);
		painelIsbn.add(tfIsbn2);
		painelIsbn.add(ctIsbn2);
		painelIsbn.add(tfIsbn3);
		painelIsbn.add(ctIsbn4);
		painelIsbn.add(tfIsbn4);
		JLabel cTitulo = new JLabel("Título");
		tfTitulo = new JTextField();
		tfTitulo.setPreferredSize(new Dimension(345, 20));
		erroTitulo = new JLabel("O titulo não pode ser só espaço ou vazio e tem até 60 caracteres");
		JLabel cPreco = new JLabel("Preço");
		erroPreco = new JLabel("o preço não pode ser vazio ou só espaço");
		tfPreco = new JTextField();
		tfPreco.setPreferredSize(new Dimension(345, 20));
		GridBagConstraints c1 = new GridBagConstraints();
		c1.anchor = GridBagConstraints.WEST;
		c1.gridx = 0;
		c1.gridy = 0;
		c1.ipadx = 5;
		painelCampos.add(cIsbn, c1);
		c1.gridx = 1;
		c1.gridy = 0;
		painelCampos.add(painelIsbn, c1);
		c1.gridx = 2;
		c1.gridy = 0;
		painelCampos.add(erroIsbn, c1);
		c1.gridx = 0;
		c1.gridy = 1;
		painelCampos.add(cTitulo, c1);
		c1.gridx = 1;
		c1.gridy = 1;
		painelCampos.add(tfTitulo, c1);
		c1.gridx = 2;
		c1.gridy = 1;
		painelCampos.add(erroTitulo, c1);
		c1.gridx = 0;
		c1.gridy = 2;
		painelCampos.add(cPreco, c1);
		c1.gridx = 1;
		c1.gridy = 2;
		painelCampos.add(tfPreco, c1);
		c1.gridx = 2;
		c1.gridy = 2;
		painelCampos.add(erroPreco, c1);
		c1.gridx = 0;
		c1.gridy = 3;
		painelCampos.add(cEditoras, c1);
		c1.gridx = 1;
		c1.gridy = 3;
		painelCampos.add(cBEditoras, c1);
		c1.gridx = 2;
		c1.gridy = 3;
		c1.weightx = 1;
		c1.anchor = GridBagConstraints.EAST;
		painelCampos.add(erroAutores, c1);
		janelaInclusaoDeLivro.add(painelCampos, BorderLayout.PAGE_START);
		JPanel painelBotoesSalvarCancelar = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		btSalvarEdicaoEditora = new JButton("Adicionar");
		btSalvarEdicaoEditora.setEnabled(false);
		btSalvarEdicaoEditora.addActionListener(cBtAdicionar);

		// Comportamento de validação dos JTextFields
		DocumentListener cpValidacaoInclusaoLivro = new DocumentListener() {
			private void habitaBotao() {
				boolean isbnOk = true;
				String texto1 = tfIsbn1.getText();
				String texto2 = tfIsbn2.getText();
				String texto3 = tfIsbn3.getText();
				String texto4 = tfIsbn4.getText();
				int soma = texto1.length() + texto2.length() + texto3.length() + texto4.length();
				if (soma != 10 || tfIsbn1.getText().trim().isEmpty() || tfIsbn2.getText().trim().isEmpty()
						|| tfIsbn3.getText().trim().isEmpty() || tfIsbn4.getText().trim().isEmpty()) {
					erroIsbn.setText("Todos os campos do isbn devem estar preenchidos e ter 10 digitos no total");
					btSalvarEdicaoEditora.setEnabled(false);
					camposOk = false;
				} else {
					erroIsbn.setText("");
					String isbn = tfIsbn1.getText() + "-" + tfIsbn2.getText() + "-" + tfIsbn3.getText() + "-"
							+ tfIsbn4.getText();
					for (String is : isbns) {
						if (is.equals(isbn)) {
							erroIsbn.setText("O ISBN informado já existe");
							isbnOk = false;
							btSalvarEdicaoEditora.setEnabled(false);
							camposOk = false;
							break;
						}
					}
				}
				if (tfTitulo.getText().trim().isEmpty()) {
					erroTitulo.setText("O titulo não pode ser só espaço(s) ou vazio");
					btSalvarEdicaoEditora.setEnabled(false);
					camposOk = false;
				} else {
					erroTitulo.setText("");
				}
				if (tfPreco.getText().trim().isEmpty()) {
					erroPreco.setText("o preço não pode ser vazio ou só espaço(s)");
					btSalvarEdicaoEditora.setEnabled(false);
					camposOk = false;
				} else {
					erroPreco.setText("");
				}
				if (isbnOk && !tfPreco.getText().trim().isEmpty() && !tfTitulo.getText().trim().isEmpty() && soma == 10
						&& !tfIsbn1.getText().trim().isEmpty() && !tfIsbn2.getText().trim().isEmpty() && !tfIsbn3.getText().trim().isEmpty()
						&& !tfIsbn4.getText().trim().isEmpty()) {
					camposOk = true;
				}
				if (tabelaOk && camposOk) {
					erroPreco.setText("");
					erroTitulo.setText("");
					erroIsbn.setText("");
					btSalvarEdicaoEditora.setEnabled(true);
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
						String texto1 = tfIsbn1.getText();
						String texto2 = tfIsbn2.getText();
						String texto3 = tfIsbn3.getText();
						String texto4 = tfIsbn4.getText();
						int soma = texto1.length() + texto2.length() + texto3.length() + texto4.length();
						if (!texto1.matches("^[0-9]*$") || texto1.length() > 5) {
							tfIsbn1.setText(texto1.substring(0, texto1.length() - 1));
						}
						if (!texto2.matches("^[0-9]*$") || (soma > 10 && tfIsbn2.isFocusOwner())) {
							tfIsbn2.setText(texto2.substring(0, texto2.length() - 1));
						}
						if (!texto3.matches("^[0-9]*$") || (soma > 10 && tfIsbn3.isFocusOwner())) {
							tfIsbn3.setText(texto3.substring(0, texto3.length() - 1));
						}
						if (!texto4.matches("^[X 0-9]*$") || texto4.length() > 1) {
							tfIsbn4.setText(texto4.substring(0, texto4.length() - 1));
						}
					}
				};
				SwingUtilities.invokeLater(formato1);
				Runnable formato2 = new Runnable() {
					@Override
					public void run() {
						String texto = tfTitulo.getText();
						if (texto.length() > 60) {
							tfTitulo.setText(texto.substring(0, texto.length() - 1));
							erroTitulo.setText("o título só tem 60 caracteres");
						}
					}
				};
				SwingUtilities.invokeLater(formato2);
				Runnable formato3 = new Runnable() {
					@Override
					public void run() {
						String text = tfPreco.getText();
						if (!text.matches("\\d*(\\.\\d{0,2})?") || (text.length() > 10)
								|| (text.length() > 9 && text.substring(text.length() - 1).contains("."))) {
							tfPreco.setText(text.substring(0, text.length() - 1));
							erroPreco.setText(
									"o preço é um valor numérico de até 10 caracteres com até 2 casas decimais");
						}
					}
				};
				SwingUtilities.invokeLater(formato3);
				habitaBotao();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				habitaBotao();
			}

		};
		tfIsbn1.getDocument().addDocumentListener(cpValidacaoInclusaoLivro);
		tfIsbn2.getDocument().addDocumentListener(cpValidacaoInclusaoLivro);
		tfIsbn3.getDocument().addDocumentListener(cpValidacaoInclusaoLivro);
		tfIsbn4.getDocument().addDocumentListener(cpValidacaoInclusaoLivro);
		tfTitulo.getDocument().addDocumentListener(cpValidacaoInclusaoLivro);
		tfPreco.getDocument().addDocumentListener(cpValidacaoInclusaoLivro);
		JButton btCancelar = new JButton("Cancelar");
		// Comportamento botão cancelar
		btCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				janelaInclusaoDeLivro.dispose();
			}
		});

		JPanel painelTituloOutrosAutores = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel cTituloOutrosAutores = new JLabel("Outros Autores");
		painelTituloOutrosAutores.add(cTituloOutrosAutores);
		JPanel painelOutrosAutores = new JPanel(new BorderLayout());
		JScrollPane tabelaOutrosLivros = crieTabelaOutrosAutores();
		painelOutrosAutores.add(painelTituloOutrosAutores, BorderLayout.PAGE_START);
		painelOutrosAutores.add(tabelaOutrosLivros, BorderLayout.CENTER);
		JPanel painelOutrosLivros2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelOutrosLivros2.add(painelOutrosAutores);

		JPanel painelBotoes = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JButton btDireito = new JButton("--->");
		btDireito.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dtmOutrosAutores.getRowCount() > 0 && linhaOutrosAutores != -1
						&& autorSelecionadoOutrosAutores != null) {
					dtmOutrosAutores.removeRow(linhaOutrosAutores);
					Object[] dadosAutor = new Object[3];
					dadosAutor[0] = dtmAutoresDoLivro.getRowCount() + 1;
					dadosAutor[1] = autorSelecionadoOutrosAutores;
					dadosAutor[2] = autorSelecionadoOutrosAutores.getSobrenome();
					autorSelecionadoOutrosAutores = null;
					dtmAutoresDoLivro.addRow(dadosAutor);
				}
			}
		});
		JButton btEsquerdo = new JButton("<---");
		btEsquerdo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dtmAutoresDoLivro.getRowCount() > 0 && linhaAutorDoLivro != -1 && autorSelecionadoDoLivro != null) {
					dtmAutoresDoLivro.removeRow(linhaAutorDoLivro);
					for (int i = 0; i < dtmAutoresDoLivro.getRowCount(); i++) {
						dtmAutoresDoLivro.setValueAt(i + 1, i, 0);
					}
					Object[] dadosAutor = new Object[2];
					dadosAutor[0] = autorSelecionadoDoLivro;
					dadosAutor[1] = autorSelecionadoDoLivro.getSobrenome();
					autorSelecionadoDoLivro = null;
					dtmOutrosAutores.addRow(dadosAutor);
				}
			}
		});
		c.gridy = 0;
		painelBotoes.add(btDireito, c);
		c.gridy = 1;
		painelBotoes.add(btEsquerdo, c);

		JPanel painelTituloAutoresDoLivro = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel cTituloLivrosDaEditora = new JLabel("Autores do Livro");
		painelTituloAutoresDoLivro.add(cTituloLivrosDaEditora);
		JPanel painelLivrosDaEditora = new JPanel(new BorderLayout());
		JScrollPane tabelaLivrosDaEditora = crieTabelaAutoresDoLivro();
		painelLivrosDaEditora.add(painelTituloAutoresDoLivro, BorderLayout.PAGE_START);
		painelLivrosDaEditora.add(tabelaLivrosDaEditora, BorderLayout.CENTER);
		JPanel painelAutoresDoLivro2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelAutoresDoLivro2.add(painelLivrosDaEditora);
		painelBotoesSalvarCancelar.add(btSalvarEdicaoEditora);
		painelBotoesSalvarCancelar.add(btCancelar);
		preenchaTabelaOutrosAutores();
		janelaInclusaoDeLivro.add(painelOutrosLivros2, BorderLayout.LINE_START);
		janelaInclusaoDeLivro.add(painelBotoes, BorderLayout.CENTER);
		janelaInclusaoDeLivro.add(painelAutoresDoLivro2, BorderLayout.LINE_END);
		janelaInclusaoDeLivro.add(painelBotoesSalvarCancelar, BorderLayout.PAGE_END);

		janelaInclusaoDeLivro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janelaInclusaoDeLivro.setSize(new Dimension(1050, 650));
		janelaInclusaoDeLivro.setVisible(true);
	}

	private JScrollPane crieTabelaOutrosAutores() {
		Object[][] dadosLivros = new Object[0][2];
		Object[] colsNomesLivros = { "NOME", "SOBRENOME" };
		dtmOutrosAutores = new DefaultTableModel(dadosLivros, colsNomesLivros) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable tableOutrosLivros = new JTable(dtmOutrosAutores);
		tableOutrosLivros.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				linhaOutrosAutores = tableOutrosLivros.getSelectedRow();
				autorSelecionadoOutrosAutores = (Autor) tableOutrosLivros.getValueAt(linhaOutrosAutores, 0);
			}

		});
		tableOutrosLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroolPaneOutrosLivros = new JScrollPane(tableOutrosLivros);
		return scroolPaneOutrosLivros;
	}

	private JScrollPane crieTabelaAutoresDoLivro() {
		Object[][] dadosAutores = new Object[0][3];
		Object[] colsNomesAutores = { "SEQ-Nº", "NOME", "SOBRENOME" };
		dtmAutoresDoLivro = new DefaultTableModel(dadosAutores, colsNomesAutores) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//Validação dos autores
		dtmAutoresDoLivro.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.DELETE) {
					if (dtmAutoresDoLivro.getRowCount() == 0) {
						tabelaOk = false;
						erroAutores.setText("Escolha pelo menos 1 autor para o livro");
						btSalvarEdicaoEditora.setEnabled(false);
					} else {
						erroAutores.setText("");
						tabelaOk = true;
						if (camposOk) {
							btSalvarEdicaoEditora.setEnabled(true);
						}
					}

				}
			}
		});
		JTable tableAutoresDoLivro = new JTable(dtmAutoresDoLivro);
		tableAutoresDoLivro.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				linhaAutorDoLivro = tableAutoresDoLivro.getSelectedRow();
				autorSelecionadoDoLivro = (Autor) tableAutoresDoLivro.getValueAt(linhaAutorDoLivro, 1);
			}

		});
		tableAutoresDoLivro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroolPaneLivrosDaEditora = new JScrollPane(tableAutoresDoLivro);
		return scroolPaneLivrosDaEditora;
	}

	private void preenchaTabelaOutrosAutores() {
		Object[] dadosOutrosAutores = new Object[2];
		for (Autor a : autores) {
			dadosOutrosAutores[0] = a;
			dadosOutrosAutores[1] = a.getSobrenome();
			dtmOutrosAutores.addRow(dadosOutrosAutores);
		}
	}

	Livro getNovoLivro() {
		String titulo = tfTitulo.getText();
		String isbn = tfIsbn1.getText() + "-" + tfIsbn2.getText() + "-" + tfIsbn3.getText() + "-" + tfIsbn4.getText();
		double preco = Double.parseDouble(tfPreco.getText());
		Editora editora = (Editora) cBEditoras.getSelectedItem();
		Livro livro = new Livro(titulo, isbn, preco, editora);
		int i;
		Autor autor;
		for (i = 0; i < dtmAutoresDoLivro.getRowCount(); i++) {
			autor = (Autor) dtmAutoresDoLivro.getValueAt(i, 1);
			livro.addAutor(i + 1, autor);
		}
		return livro;
	}

	void fechaJanelaInclusaoLivro() {
		janelaInclusaoDeLivro.dispose();
	}
}
