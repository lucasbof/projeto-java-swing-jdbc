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

class TabelasEditora {
	private DefaultTableModel dtmAutoresEditora;
	private DefaultTableModel dtmLivrosEditora;
	private DefaultTableModel dtmEditorasEditora;
	private JScrollPane scroolPaneAutoresAutor;
	private JScrollPane scroolPaneLivrosAutor;
	private JScrollPane scroolPaneEditoraAutor;
	private JTable tableLivrosEditora;
	private JTable tableEditora;

	TabelasEditora() {
		crieTabelaLivros();
		crieTabelaAutores();
		crieTabelaEditora();
	}

	private void crieTabelaLivros() {
		Object[][] dadosLivros = new Object[0][3];
		Object[] colsNomesLivros = { "ISBN", "TÍTULO", "PREÇO" };
		dtmLivrosEditora = new DefaultTableModel(dadosLivros, colsNomesLivros) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableLivrosEditora = new JTable(dtmLivrosEditora);
		TableColumnModel tcm = tableLivrosEditora.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(100);
		tcm.getColumn(1).setPreferredWidth(360);
		tcm.getColumn(2).setPreferredWidth(70);
		tableLivrosEditora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolPaneLivrosAutor = new JScrollPane(tableLivrosEditora);
	}

	private void crieTabelaAutores() {
		Object[][] dadosAutores = new Object[0][3];
		Object[] colsNomesAutores = {"SEQ-Nº", "NOME", "SOBRENOME"};
		dtmAutoresEditora = new DefaultTableModel(dadosAutores, colsNomesAutores) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable tableAutores = new JTable(dtmAutoresEditora);
		tableAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolPaneAutoresAutor = new JScrollPane(tableAutores);
	}

	private void crieTabelaEditora() {
		Object[][] dadosEditora = new Object[0][4];
		Object[] colsNomesEditora = { "NOME", "URL", "EDITAR", "EXCLUIR" };
		dtmEditorasEditora = new DefaultTableModel(dadosEditora, colsNomesEditora) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 2 || column == 3) {
					return true;
				}
				return false;
			}
		};
		tableEditora = new JTable(dtmEditorasEditora);
		TableColumnModel tcm = tableEditora.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(160);
		tcm.getColumn(1).setPreferredWidth(160);
		tcm.getColumn(2).setPreferredWidth(90);
		tcm.getColumn(3).setPreferredWidth(90);
		tableEditora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolPaneEditoraAutor = new JScrollPane(tableEditora);
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
	
	void addComportamentoTabelaEditorasEditora(MouseListener ml){
		tableEditora.addMouseListener(ml);
	}

	void addComportamentoTabelaLivrosDaEditora(MouseListener ml) {
		tableLivrosEditora.addMouseListener(ml);		
	}

	void atualizaDadosEditora(Set<Editora> editoras) {
		Locale.setDefault(Locale.US);
		dtmLivrosEditora.setRowCount(0);
		dtmAutoresEditora.setRowCount(0);
		dtmEditorasEditora.setRowCount(0);
		Object[] dadosEditora = new Object[4];
		for (Editora editora : editoras) {
			dadosEditora[0] = editora;
			dadosEditora[1] = editora.getUrl();
			dadosEditora[2] = "EDITAR";
			dadosEditora[3] = "EXCLUIR";
			dtmEditorasEditora.addRow(dadosEditora);
		}
	}

	void preenchaTabelaLivrosEditora(Set<Livro> livrosDaEditora) {
		dtmLivrosEditora.setRowCount(0);
		dtmAutoresEditora.setRowCount(0);
		Object[] dadosLivrosDaEditora = new Object[3];
		for (Livro livro : livrosDaEditora) {;
			dadosLivrosDaEditora[0] = livro;
			dadosLivrosDaEditora[1] = livro.getTitulo();
			dadosLivrosDaEditora[2] = String.format("%.2f", livro.getPreco());
			dtmLivrosEditora.addRow(dadosLivrosDaEditora);
		}
	}

	void preenchaTabelaAutoresEditora(Map<Integer, Autor> autores) {
		dtmAutoresEditora.setRowCount(0);
		Object[] dadosAutor = new Object[3];
		Autor autor;
		for (int i = 1; i <= autores.size(); i++) {
			autor = autores.get(i);
			dadosAutor[0] = i;
			dadosAutor[1] = autor.getNome();
			dadosAutor[2] = autor.getSobrenome();
			dtmAutoresEditora.addRow(dadosAutor);
		}
	}
	
	Editora getEditoraSelecionada() {
		int linha = tableEditora.getSelectedRow();
		Editora editora = (Editora)tableEditora.getValueAt(linha, 0);
		return editora;
		
	}
	
	Livro getLivroDaEditoraSelecionado() {
		int linha = tableLivrosEditora.getSelectedRow();
		Livro livro = (Livro)tableLivrosEditora.getValueAt(linha, 0);
		return livro;
	}

	Editora getEditoraParaEditar(ActionEvent e) {
		int linha = tableEditora.getSelectedRow();
		Editora editora = (Editora)tableEditora.getValueAt(linha, 0);
		return editora;
	}

	
	void limpaTabelasEditora() {
		dtmLivrosEditora.setRowCount(0);
		dtmAutoresEditora.setRowCount(0);
		dtmEditorasEditora.setRowCount(0);		
	}

	void addComportamentoExcluiEEditaEditora(AbstractAction cEditaEditora, AbstractAction cExcluiEditora) {
		new ColunaBotao(tableEditora, cEditaEditora, 2);
		new ColunaBotao(tableEditora, cExcluiEditora, 3);
	}
}
