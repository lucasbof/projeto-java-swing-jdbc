package modelo.servicos;

import java.util.Set;

import modelo.dao.DAO;
import modelo.dao.DAOPostgre;
import modelo.entidades.Editora;

public class ServicoDeEditora {

private DAO dao;
	
	public ServicoDeEditora() {
		this.dao = new DAOPostgre();
	}

	public Set<Editora> getEditorasPeloNome(String nome) {
		return dao.getEditorasPeloNome(nome);
	}

	public boolean excluiEditora(Editora editora) {
		return dao.excluiEditora(editora);
	}

	public boolean atualizeEditora(Editora editora) {
		return dao.atualizeEditora(editora);
	}

	public Set<Editora> getTodasEditoras() {
		return dao.getTodasEditoras();
	}

	public boolean adicionaEditora(Editora editora) {
		return dao.adicionaEditora(editora);
	}
	
}
