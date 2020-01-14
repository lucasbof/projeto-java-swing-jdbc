package visao;

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

class TabelasLivro {

	private DefaultTableModel dtmAutores;
	private JTable tableLivros;
	private DefaultTableModel dtmLivros;
	private DefaultTableModel dtmEditora;
	private JScrollPane scroolPaneAutores;
	private JScrollPane scroolPaneLivros;
	private JScrollPane scroolPaneEditora;

	TabelasLivro() {
		crieTabelaLivros();
		crieTabelaAutores();
		crieTabelaEditora();
	}

	private void crieTabelaLivros() {
		Object[][] dadosLivros = new Object[0][5];
		Object[] colsNomesLivros = { "ISBN", "TÍTULO", "PREÇO", "EDITAR", "EXCLUIR" };
		dtmLivros = new DefaultTableModel(dadosLivros, colsNomesLivros) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 3 || column == 4) {
					return true;
				}
				return false;
			}
		};
		tableLivros = new JTable(dtmLivros);
		TableColumnModel tcm = tableLivros.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(100);
		tcm.getColumn(1).setPreferredWidth(400);
		tcm.getColumn(2).setPreferredWidth(80);
		tcm.getColumn(3).setPreferredWidth(100);
		tcm.getColumn(4).setPreferredWidth(100);
		tableLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolPaneLivros = new JScrollPane(tableLivros);
	}

	void addComportamentoExcluirEEditarLivro(AbstractAction cEditaLivro, AbstractAction cExcluiLivro) {
		new ColunaBotao(tableLivros, cEditaLivro, 3);
		new ColunaBotao(tableLivros, cExcluiLivro, 4);
	}

	private void crieTabelaAutores() {
		Object[][] dadosAutores = new Object[0][3];
		Object[] colsNomesAutores = { "SEQ-Nº", "NOME", "SOBRENOME" };
		dtmAutores = new DefaultTableModel(dadosAutores, colsNomesAutores) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tableAutores = new JTable(dtmAutores);
		tableAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolPaneAutores = new JScrollPane(tableAutores);
	}

	private void crieTabelaEditora() {
		Object[][] dadosEditora = new Object[0][2];
		Object[] colsNomesEditora = { "NOME", "URL" };
		dtmEditora = new DefaultTableModel(dadosEditora, colsNomesEditora) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tableEditora = new JTable(dtmEditora);
		tableEditora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolPaneEditora = new JScrollPane(tableEditora);
	}

	JScrollPane getJScroolPaneLivros() {
		return scroolPaneLivros;
	}

	JScrollPane getJScroolPaneAutores() {
		return scroolPaneAutores;
	}

	JScrollPane getJScroolPaneEditora() {
		return scroolPaneEditora;
	}

	void addComportamentoTabelaLivros(MouseListener ml) {
		tableLivros.addMouseListener(ml);
	}

	void atualizaDadosLivros(Set<Livro> livros) {
		Locale.setDefault(Locale.US);
		dtmLivros.setRowCount(0);
		dtmAutores.setRowCount(0);
		dtmEditora.setRowCount(0);
		Object[] dadosLivro = new Object[5];
		for (Livro livro : livros) {
			dadosLivro[0] = livro;
			dadosLivro[1] = livro.getTitulo();
			dadosLivro[2] = String.format("%.2f", livro.getPreco());
			dadosLivro[3] = "EDITAR";
			dadosLivro[4] = "EXCLUIR";
			dtmLivros.addRow(dadosLivro);
		}
	}

	Livro getLivroSelecionado() {
		int linha = tableLivros.getSelectedRow();
		Livro livro = (Livro) tableLivros.getValueAt(linha, 0);
		return livro;
	}

	void preenchaTabelaAutoresLivro(Map<Integer, Autor> autores) {
		dtmAutores.setRowCount(0);
		Object[] dadosAutores = new Object[3];
		Autor autor;
		for (int i = 1; i <= autores.size(); i++) {
			autor = autores.get(i);
			dadosAutores[0] = i;
			dadosAutores[1] = autor.getNome();
			dadosAutores[2] = autor.getSobrenome();
			dtmAutores.addRow(dadosAutores);
		}
	}

	void preenchaTabelaEditoraLivro(Editora editora) {
		dtmEditora.setRowCount(0);
		Object[] dadosEditora = new Object[2];
		dadosEditora[0] = editora.getNome();
		dadosEditora[1] = editora.getUrl();
		dtmEditora.addRow(dadosEditora);
	}

	void limpaTabelasLivro() {
		dtmLivros.setRowCount(0);
		dtmAutores.setRowCount(0);
		dtmEditora.setRowCount(0);
	}

}
