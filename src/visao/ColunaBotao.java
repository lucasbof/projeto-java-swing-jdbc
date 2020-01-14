package visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

class ColunaBotao extends AbstractCellEditor
		implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JTable tabela;
	private Action acao;
	private Border bordaOriginal;
	private Border bordaFocada;

	private JButton botaoRenderizador;
	private JButton botaoEditor;
	private Object valorDoBotaoEditor;
	private boolean eEditorDoBotaoDaColuna;

	ColunaBotao(JTable tabela, Action acao, int coluna) {
		this.tabela = tabela;
		this.acao = acao;

		botaoRenderizador = new JButton();
		botaoEditor = new JButton();
		botaoEditor.setFocusPainted(false);
		botaoEditor.addActionListener(this);
		bordaOriginal = botaoEditor.getBorder();
		setBordaDeFoco(new LineBorder(Color.BLUE));

		TableColumnModel modelDaColuna = tabela.getColumnModel();
		modelDaColuna.getColumn(coluna).setCellRenderer(this);
		modelDaColuna.getColumn(coluna).setCellEditor(this);
		tabela.addMouseListener(this);
	}

	void setBordaDeFoco(Border focusBorder) {
		this.bordaFocada = focusBorder;
		botaoEditor.setBorder(focusBorder);
	}

	@Override
	public Component getTableCellEditorComponent(JTable tabela, Object valor, boolean estaSelecionado, int linha,
			int coluna) {
		if (valor == null) {
			botaoEditor.setText("");
			botaoEditor.setIcon(null);
		} else if (valor instanceof Icon) {
			botaoEditor.setText("");
			botaoEditor.setIcon((Icon) valor);
		} else {
			botaoEditor.setText(valor.toString());
			botaoEditor.setIcon(null);
		}

		this.valorDoBotaoEditor = valor;
		return botaoEditor;
	}

	@Override
	public Object getCellEditorValue() {
		return valorDoBotaoEditor;
	}

	@Override
	public Component getTableCellRendererComponent(JTable tabela, Object valor, boolean estaSelecionado,
			boolean possuiFoco, int linha, int coluna) {
		if (estaSelecionado) {
			botaoRenderizador.setForeground(tabela.getSelectionForeground());
			botaoRenderizador.setBackground(tabela.getSelectionBackground());
		} else {
			botaoRenderizador.setForeground(tabela.getForeground());
			botaoRenderizador.setBackground(UIManager.getColor("Button.background"));
		}

		if (possuiFoco) {
			botaoRenderizador.setBorder(bordaFocada);
		} else {
			botaoRenderizador.setBorder(bordaOriginal);
		}

		if (valor == null) {
			botaoRenderizador.setText("");
			botaoRenderizador.setIcon(null);
		} else if (valor instanceof Icon) {
			botaoRenderizador.setText("");
			botaoRenderizador.setIcon((Icon) valor);
		} else {
			botaoRenderizador.setText(valor.toString());
			botaoRenderizador.setIcon(null);
		}

		return botaoRenderizador;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int linha = tabela.convertRowIndexToModel(tabela.getEditingRow());
		fireEditingStopped();

		ActionEvent evento = new ActionEvent(tabela, ActionEvent.ACTION_PERFORMED, "" + linha);
		acao.actionPerformed(evento);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (tabela.isEditing() && tabela.getCellEditor() == this) {
			eEditorDoBotaoDaColuna = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (eEditorDoBotaoDaColuna && tabela.isEditing()) {
			tabela.getCellEditor().stopCellEditing();
		}
		eEditorDoBotaoDaColuna = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
