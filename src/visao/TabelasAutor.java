package visao;

import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import modelo.entidades.Autor;
import modelo.entidades.Editora;
import modelo.entidades.Livro;

class TabelasAutor {
	private DefaultTableModel dtmAutoresAutor;
	private DefaultTableModel dtmLivrosAutor;
	private DefaultTableModel dtmEditoraAutor;
	private JScrollPane scroolPaneAutoresAutor;
	private JScrollPane scroolPaneLivrosAutor;
	private JScrollPane scroolPaneEditoraAutor;
	private JTable tableAutores;
	private JTable tableLivrosAutor;

	TabelasAutor() {
		crieTabelaLivros();
		crieTabelaAutores();
		crieTabelaEditora();
	}

	private void crieTabelaLivros() {
		Object[][] dadosLivros = new Object[0][4];
		Object[] colsNomesLivros = { "ISBN", "TÍTULO", "PREÇO", "SEQ-Nº" };
		dtmLivrosAutor = new DefaultTableModel(dadosLivros, colsNomesLivros) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableLivrosAutor = new JTable(dtmLivrosAutor);
		TableColumnModel tcm = tableLivrosAutor.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(90);
		tcm.getColumn(1).setPreferredWidth(340);
		tcm.getColumn(2).setPreferredWidth(60);
		tcm.getColumn(3).setPreferredWidth(50);
		tableLivrosAutor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolPaneLivrosAutor = new JScrollPane(tableLivrosAutor);
	}

	private void crieTabelaAutores() {
		Object[][] dadosAutores = new Object[0][4];
		Object[] colsNomesAutores = { "NOME", "SOBRENOME", "EDITAR", "EXCLUIR" };
		dtmAutoresAutor = new DefaultTableModel(dadosAutores, colsNomesAutores) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 2 || column == 3) {
					return true;
				}
				return false;
			}
		};
		tableAutores = new JTable(dtmAutoresAutor);
		tableAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolPaneAutoresAutor = new JScrollPane(tableAutores);
	}

	private void crieTabelaEditora() {
		Object[][] dadosEditora = new Object[0][2];
		Object[] colsNomesEditora = { "NOME", "URL" };
		dtmEditoraAutor = new DefaultTableModel(dadosEditora, colsNomesEditora) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tableEditora = new JTable(dtmEditoraAutor);
		tableEditora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolPaneEditoraAutor = new JScrollPane(tableEditora);
	}

	void addComportamentoExcluiEEditaAutor(AbstractAction cEdita, AbstractAction cExclui) {
		new ColunaBotao(tableAutores, cEdita, 2);
		new ColunaBotao(tableAutores, cExclui, 3);
	}

	JScrollPane getJScroolPaneLivros() {
		return scroolPaneLivrosAutor;
	}

	JScrollPane getJScroolPaneAutores() {
		return scroolPaneAutoresAutor;
	}

	JScrollPane getJScroolPaneEditora() {
		return scroolPaneEditoraAutor;
	}

	void addComportamentoTabelaAutoresAutor(MouseListener ml) {
		tableAutores.addMouseListener(ml);
	}

	void atualizaDadosAutores(Set<Autor> autores) {
		Locale.setDefault(Locale.US);
		dtmLivrosAutor.setRowCount(0);
		dtmAutoresAutor.setRowCount(0);
		dtmEditoraAutor.setRowCount(0);
		Object[] dadosAutor = new Object[4];
		for (Autor autor : autores) {
			dadosAutor[0] = autor;
			dadosAutor[1] = autor.getSobrenome();
			dadosAutor[2] = "EDITAR";
			dadosAutor[3] = "EXCLUIR";
			dtmAutoresAutor.addRow(dadosAutor);
		}
	}

	Autor getAutorSelecionado() {
		dtmEditoraAutor.setRowCount(0);
		int linha = tableAutores.getSelectedRow();
		Autor autor = (Autor) tableAutores.getValueAt(linha, 0);
		return autor;
	}

	Livro getLivroDoAutorSelecionado() {
		int linha = tableLivrosAutor.getSelectedRow();
		Livro livro = (Livro) tableLivrosAutor.getValueAt(linha, 0);
		return livro;
	}

	void preenchaTabelaLivros(Map<Livro, Integer> livrosDoAutor) {
		dtmLivrosAutor.setRowCount(0);
		Object[] dadosLivrosDoAutor = new Object[4];
		for (Livro l : livrosDoAutor.keySet()) {
			dadosLivrosDoAutor[0] = l;
			dadosLivrosDoAutor[1] = l.getTitulo();
			dadosLivrosDoAutor[2] = String.format("%.2f", l.getPreco());
			dadosLivrosDoAutor[3] = livrosDoAutor.get(l);
			dtmLivrosAutor.addRow(dadosLivrosDoAutor);
		}
	}

	void preenchaTabelaEditora(Editora editora) {
		dtmEditoraAutor.setRowCount(0);
		Object[] dadosEditora = new Object[2];
		dadosEditora[0] = editora.getNome();
		dadosEditora[1] = editora.getUrl();
		dtmEditoraAutor.addRow(dadosEditora);
	}

	int getIdAutorExcluido(ActionEvent e) {
		int linha = Integer.valueOf(e.getActionCommand());
		int id = (int) tableAutores.getValueAt(linha, 0);
		return id;

	}

	Autor getAutorParaEditar(ActionEvent e) {
		int linha = Integer.valueOf(e.getActionCommand());
		int id = (int) tableAutores.getValueAt(linha, 0);
		String nome = (String) tableAutores.getValueAt(linha, 1);
		String sobrenome = (String) tableAutores.getValueAt(linha, 2);
		Autor autor = new Autor(id, nome, sobrenome);
		return autor;
	}

	void addComportamentoTabelaLivrosDoAutor(MouseListener ml) {
		tableLivrosAutor.addMouseListener(ml);
	}

	void limpaTabelasAutor() {
		dtmLivrosAutor.setRowCount(0);
		dtmAutoresAutor.setRowCount(0);
		dtmEditoraAutor.setRowCount(0);
	}
}
