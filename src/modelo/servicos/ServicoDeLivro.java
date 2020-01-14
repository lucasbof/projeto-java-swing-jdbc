package modelo.servicos;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import modelo.dao.DAO;
import modelo.dao.DAOPostgre;
import modelo.entidades.Livro;

public class ServicoDeLivro {

	private DAO dao;
	
	public ServicoDeLivro() {
		this.dao = new DAOPostgre();
	}
	
	public Set<Livro> getLivrosPeloTitulo(String titulo) {
		return dao.getLivrosPeloTitulo(titulo);
	}

	public boolean excluiLivro(Livro livro) {
		Set<Livro> livros = new TreeSet<Livro>(new Comparator<Livro>() {
			@Override
			public int compare(Livro l1, Livro l2) {
				return l1.getTitulo().compareToIgnoreCase(l2.getTitulo());
			}
		});
		livros.add(livro);
		dao.excluiLivrosXAutores(livros);
		return dao.excluiLivros(livros);
	}

	public boolean atualizeLivro(Livro livro) {
		return dao.atualizeLivro(livro);
	}

	public Set<String> getTodosIsbns() {
		return dao.getTodosIsbns();
	}

	public boolean adicionaLivro(Livro livro) {
		 if(dao.adicionaLivro(livro)) {
			 if(dao.adicionaAutoresDoLivro(livro)) {
				 return true;
			 }
		 }
		 return false;
	}
}
