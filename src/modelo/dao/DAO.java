package modelo.dao;

import java.util.Set;

import modelo.entidades.Autor;
import modelo.entidades.Editora;
import modelo.entidades.Livro;

public interface DAO {
	
	//m�todos de busca
	public Set<Livro> getLivrosPeloTitulo(String titulo);
	
	public Set<Autor> getAutores(String nome, String sobrenome);

	public Set<Editora> getEditorasPeloNome(String nome);
	
	//m�todos de inclus�o
	public boolean adicionaLivro(Livro livro);

	public boolean adicionaAutoresDoLivro(Livro livro);

	public boolean adicionaAutor(Autor autor);

	public boolean adicionaEditora(Editora editora);
	
	//m�todos de exclus�o
	public boolean excluiLivros(Set<Livro> livros);

	public boolean excluiAutor(Autor autor);

	public void excluiLivrosXAutores(Set<Livro> livros);

	public boolean excluiEditora(Editora editora);
	
	//m�todos de atualiza��o
	public boolean atualizeLivro(Livro livro);
	
	public boolean atualizeAutor(Autor autor);
	
	public boolean atualizeEditora(Editora editora);

	//m�todos para a janela de inclus�o de livro
	public Set<String> getTodosIsbns();
	
	public Set<Autor> getTodosAutores();
	
	public Set<Editora> getTodasEditoras();
}
