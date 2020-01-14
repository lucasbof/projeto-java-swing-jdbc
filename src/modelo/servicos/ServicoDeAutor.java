package modelo.servicos;

import java.util.Set;

import modelo.dao.DAO;
import modelo.dao.DAOPostgre;
import modelo.entidades.Autor;

public class ServicoDeAutor {
	
	private DAO dao;
	
	public ServicoDeAutor() {
		this.dao = new DAOPostgre();
	}
	
	public boolean excluiAutor(Autor autor) {
		return dao.excluiAutor(autor);
	}

	public boolean atualizeAutor(Autor autor) {
		return dao.atualizeAutor(autor);
		
	}
	
	public Set<Autor> getAutores(String nome, String sobrenome){
		return dao.getAutores(nome, sobrenome);
	}

	public Set<Autor> getTodosAutores() {
		return dao.getTodosAutores();
	}

	public boolean adicionaAutor(Autor autor) {
		return dao.adicionaAutor(autor);
	}
}
