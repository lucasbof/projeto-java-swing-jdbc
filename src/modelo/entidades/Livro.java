package modelo.entidades;

import java.util.Map;
import java.util.TreeMap;

public class Livro {
	 private String titulo; 
	 private String isbn; 
	 private Double preco;
	 private Editora editora;
	 
	 private Map<Integer, Autor> autores; 
	
	 public Livro(String titulo, String isbn, Double preco, Editora editora) {
		this.titulo = titulo;
		this.isbn = isbn;
		this.preco = preco;
		this.editora = editora;
		
		autores = new TreeMap<Integer, Autor>();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public void addAutor(int seqNo, Autor autor) {
		autores.put(seqNo, autor);
	}
	
	public Map<Integer, Autor> getAutoresDoLivro(){
		return autores;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro outroLivro = (Livro) obj;
		if (isbn == null) {
			if (outroLivro.isbn != null)
				return false;
		} else if (!isbn.equals(outroLivro.isbn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return isbn;
	} 
}
