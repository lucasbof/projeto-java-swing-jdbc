package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import modelo.entidades.Autor;
import modelo.entidades.Editora;
import modelo.entidades.Livro;
import modelo.servicos.ServicoDeAutor;
import modelo.servicos.ServicoDeEditora;
import modelo.servicos.ServicoDeLivro;
import visao.JanelaPrincipal;

public class Controlador {

	private ServicoDeLivro sLivro;
	private ServicoDeAutor sAutor;
	private ServicoDeEditora sEditora;
	private JanelaPrincipal janela;

	String nomeAutor;
	String sobrenomeAutor;
	String nomeEditoraBuscada;
	String tituloLivroBuscado;

	public Controlador() {
		sLivro = new ServicoDeLivro();
		sAutor = new ServicoDeAutor();
		sEditora = new ServicoDeEditora();
		janela = new JanelaPrincipal(new ComportamentoAdicionaLivro(), new ComportamentoAdicionaAutor(),
				new ComportamentoAdicionaEditora());
		janela.addComportamentoBuscaLivro(new ComportamentoBuscaLivros());
		janela.addComportamentoBuscaAutor(new ComportamentoBuscaAutor());
		janela.addComportamentoBuscaNomeEditora(new ComportamentoBuscaEditora());
		janela.addComportamentoTabelaLivrosLivro(new ComportamentoTabelasAutoresEEditoraLivro());
		janela.addComportamentoTabelaAutoresAutor(new ComportamentoTabelaLivrosAutor());
		janela.addComportamentoTabelaLivrosDoAutor(new ComportamentoTabelaEditoraAutor());
		janela.addComportamentoTabelaEditorasEditora(new ComportamentoTabelaLivrosEditora());
		janela.addComportamentoTabelaLivrosDaEditora(new ComportamentoTabelaAutoresEditora());
		janela.addComportamentoExcluiEEditaAutor(new ComportamentoEditaAutor(), new ComportamentoExcluiAutor());
		janela.addComportamentoExcluiEEditaEditora(new ComportamentoEditaEditora(), new ComportamentoExcluiEditora());
		janela.addComportamentoExcluiEEditaLivro(new ComportamentoEditaLivro(), new ComportamentoExcluiLivro());
	}

	// Comportamento de busca de livros
	private class ComportamentoBuscaLivros implements DocumentListener {

		public void atualizaTabela() {
			tituloLivroBuscado = janela.getTituloDigitado();
			Set<Livro> livros = sLivro.getLivrosPeloTitulo(tituloLivroBuscado);
			janela.atualizaDadosLivros(livros);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			atualizaTabela();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			atualizaTabela();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			atualizaTabela();
		}
	}

	private class ComportamentoTabelasAutoresEEditoraLivro extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			Livro livro = janela.getLivroSelecionado();
			Map<Integer, Autor> autores = livro.getAutoresDoLivro();
			Editora editora = livro.getEditora();
			janela.preenchaTabelaAutoresLivro(autores);
			janela.preenchaTabelaEditoraLivro(editora);
		}
	}

	// Comportamento de busca de autor
	private class ComportamentoBuscaAutor implements DocumentListener {

		public void atualizaTabela() {
			nomeAutor = janela.getNomeDigitado();
			sobrenomeAutor = janela.getSobrenomeDigitado();
			Set<Autor> autoresBuscados = sAutor.getAutores(nomeAutor, sobrenomeAutor);
			janela.atualizaDadosAutores(autoresBuscados);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			atualizaTabela();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			atualizaTabela();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			atualizaTabela();
		}

	}

	private class ComportamentoTabelaLivrosAutor extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			Autor autor = janela.getAutorSelecionado();
			Map<Livro, Integer> livrosDoAutor = autor.getLivrosDoAutor();
			janela.preenchaTabelaLivrosAutor(livrosDoAutor);
		}
	}

	private class ComportamentoTabelaEditoraAutor extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Livro livro = janela.getLivroDoAutorSelecionado();
			Editora editora = livro.getEditora();
			janela.preenchaTabelaEditoraAutor(editora);
		}
	}

	// Comportamento de busca de editora
	private class ComportamentoBuscaEditora implements DocumentListener {

		public void atualizaTabela() {
			nomeEditoraBuscada = janela.getNomeEditoraDigitado();
			Set<Editora> editoras = sEditora.getEditorasPeloNome(nomeEditoraBuscada);
			janela.atualizaDadosEditoras(editoras);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			atualizaTabela();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			atualizaTabela();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			atualizaTabela();
		}

	}

	private class ComportamentoTabelaLivrosEditora extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Editora editora = janela.getEditoraSelecionada();
			Set<Livro> livrosDaEditora = editora.getLivrosDaEditora();
			janela.preenchaTabelaLivrosEditora(livrosDaEditora);
		}
	}

	private class ComportamentoTabelaAutoresEditora extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Livro livro = janela.getLivroDaEditoraSelecionado();
			Map<Integer, Autor> autores = livro.getAutoresDoLivro();
			janela.preenchaTabelaAutoresEditora(autores);
		}
	}

	// Comportamento janela de exclusão de livro
	private class ComportamentoExcluiLivro extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Livro livro;

		@Override
		public void actionPerformed(ActionEvent e) {
			livro = janela.getLivroSelecionado();
			janela.mostraJanelaExclusão(null, livro, null, new ComportamentoBotaoOkExclui());
		}

		private class ComportamentoBotaoOkExclui implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				janela.fechaJanelaDeExclusao();
				boolean res = sLivro.excluiLivro(livro);
				if (res) {
					janela.mostraJanelaDeResultado("Sucesso", "O livro foi excluído com sucesso!");
					Set<Livro> livrosBuscados = sLivro.getLivrosPeloTitulo(tituloLivroBuscado);
					janela.atualizaDadosLivros(livrosBuscados);
				} else {
					janela.mostraJanelaDeResultado("Falha", "A exclusão do livro falhou!");
				}
			}
		}
	}

	// Comportamento de exclusão de autor
	private class ComportamentoExcluiAutor extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private Autor autor;

		@Override
		public void actionPerformed(ActionEvent e) {
			autor = janela.getAutorSelecionado();
			janela.mostraJanelaExclusão(autor, null, null, new ComportamentoBotaoOkExclui());
		}

		public class ComportamentoBotaoOkExclui implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				janela.fechaJanelaDeExclusao();
				boolean res = sAutor.excluiAutor(autor);
				if (res) {
					janela.mostraJanelaDeResultado("Sucesso", "O autor foi excluido com sucesso!");
					Set<Autor> autoresBuscados = sAutor.getAutores(nomeAutor, sobrenomeAutor);
					janela.atualizaDadosAutores(autoresBuscados);
				} else {
					janela.mostraJanelaDeResultado("Falha", "A exlusão do autor falhou!");
				}
			}
		}
	}

	// Comportamento de janela de exclusão de editora
	private class ComportamentoExcluiEditora extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Editora editora;

		@Override
		public void actionPerformed(ActionEvent e) {
			editora = janela.getEditoraSelecionada();
			janela.mostraJanelaExclusão(null, null, editora, new ComportamentoBotaoOkExclui());
		}

		private class ComportamentoBotaoOkExclui implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				janela.fechaJanelaDeExclusao();
				boolean res = sEditora.excluiEditora(editora);
				if (res) {
					janela.mostraJanelaDeResultado("Sucesso", "A editora foi excluida com sucesso!");
					Set<Editora> editorasBuscadas = sEditora.getEditorasPeloNome(nomeEditoraBuscada);
					janela.atualizaDadosEditoras(editorasBuscadas);
				} else {
					janela.mostraJanelaDeResultado("Falha", "A exclusão da editora falhou!");
				}
			}
		}
	}

	// Comportamento de edição de livro
	private class ComportamentoEditaLivro extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Livro livro;

		@Override
		public void actionPerformed(ActionEvent e) {
			livro = janela.getLivroSelecionado();
			janela.mostraJanelaEditaLivro(livro, new ComportamentoBotaoSalvar());
		}

		private class ComportamentoBotaoSalvar implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				janela.fechaJanelaEdicaoLivro();
				livro = janela.getLivroEditado();
				boolean res = sLivro.atualizeLivro(livro);
				if (res) {
					janela.mostraJanelaDeResultado("Sucesso", "O livro foi atualizado com sucesso!");
					Set<Livro> livrosBuscadas = sLivro.getLivrosPeloTitulo(tituloLivroBuscado);
					janela.atualizaDadosLivros(livrosBuscadas);
				} else {
					janela.mostraJanelaDeResultado("Falha", "A atualização do livro falhou!");
				}
			}
		}
	}

	// Comportamento de edição de autor
	private class ComportamentoEditaAutor extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Autor autor;

		@Override
		public void actionPerformed(ActionEvent e) {
			autor = janela.getAutorSelecionado();
			janela.mostraJanelaEditaAutor(autor, new ComportamentoBotaoSalvar());
		}

		private class ComportamentoBotaoSalvar implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				janela.fechaJanelaEdicaoAutor();
				autor = janela.getAutorEditada();
				boolean res = sAutor.atualizeAutor(autor);
				if (res) {
					janela.mostraJanelaDeResultado("Sucesso", "O autor foi atualizado com sucesso!");
					Set<Autor> autoresBuscadas = sAutor.getAutores(nomeAutor, sobrenomeAutor);
					janela.atualizaDadosAutores(autoresBuscadas);
				} else {
					janela.mostraJanelaDeResultado("Falha", "A atualização do autor falhou!");
				}
			}
		}
	}

	// Comportamento de edição de editora
	private class ComportamentoEditaEditora extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Editora editora;

		@Override
		public void actionPerformed(ActionEvent e) {
			editora = janela.getEditoraSelecionada();
			janela.mostraJanelaEditaEditora(editora, new ComportamentoBotaoSalvar());
		}

		private class ComportamentoBotaoSalvar implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				janela.fechaJanelaEdicaoEditora();
				editora = janela.getEditoraEditada();
				boolean res = sEditora.atualizeEditora(editora);
				if (res) {
					janela.mostraJanelaDeResultado("Sucesso", "A editora foi atualizada com sucesso!");
					Set<Editora> editorasBuscadas = sEditora.getEditorasPeloNome(nomeEditoraBuscada);
					janela.atualizaDadosEditoras(editorasBuscadas);
				} else {
					janela.mostraJanelaDeResultado("Falha", "A atualização da editora falhou!");
				}
			}
		}
	}

	// Comportamento de inclusão de livro
	private class ComportamentoAdicionaLivro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Set<Editora> editoras = sEditora.getTodasEditoras();
			Set<Autor> autores = sAutor.getTodosAutores();
			Set<String> isbns = sLivro.getTodosIsbns();
			janela.mostraJanelaAdicionaLivro(editoras, autores, isbns, new ComportamentoBotaoAdicionar());
		}

		private class ComportamentoBotaoAdicionar implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				janela.fechaJanelaInclusaoLivro();
				Livro livro = janela.getNovoLivro();
				boolean res = sLivro.adicionaLivro(livro);
				if (res) {
					janela.mostraJanelaDeResultado("Sucesso", "O livro foi incluído com sucesso!");
					Set<Livro> livrosBuscadas = sLivro.getLivrosPeloTitulo(tituloLivroBuscado);
					janela.atualizaDadosLivros(livrosBuscadas);
				} else {
					janela.mostraJanelaDeResultado("Falha", "A inclusão do livro falhou!");
				}
			}
		}
	}

	// COmportamento de inclusão de autor
	private class ComportamentoAdicionaAutor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			janela.mostraJanelaInclusaoAutor(new ComportamentoBtAdicionarAutor());
		}

		private class ComportamentoBtAdicionarAutor implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				janela.fechaJanelaInclusaoAutor();
				Autor autor = janela.getNovoAutor();
				boolean res = sAutor.adicionaAutor(autor);
				if (res) {
					janela.mostraJanelaDeResultado("Sucesso", "O autor foi incluído com sucesso!");
					Set<Autor> autoresBuscados = sAutor.getAutores(nomeAutor, sobrenomeAutor);
					janela.atualizaDadosAutores(autoresBuscados);
				} else {
					janela.mostraJanelaDeResultado("Falha", "A inclusão do autor falhou!");
				}
			}
		}
	}

	// Comportamento de inclusão de editora
	private class ComportamentoAdicionaEditora implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			janela.mostraJanelaInclusaoEditora(new ComportamentoBtAdicionarEditora());
		}

		private class ComportamentoBtAdicionarEditora implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				janela.fechaJanelaInclusaoEditora();
				Editora editora = janela.getNovaEditora();
				boolean res = sEditora.adicionaEditora(editora);
				if (res) {
					janela.mostraJanelaDeResultado("Sucesso", "A editora foi incluída com sucesso!");
					Set<Editora> editorasBuscadas = sEditora.getEditorasPeloNome(nomeEditoraBuscada);
					janela.atualizaDadosEditoras(editorasBuscadas);
				} else {
					janela.mostraJanelaDeResultado("Falha", "A inclusão da editora falhou!");
				}
			}
		}
	}
}
