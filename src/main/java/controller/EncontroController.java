package controller;

import dao.EncontroDAO;
import model.Encontro;
import java.util.List;

public class EncontroController {
	
	private EncontroDAO encontroDAO;
	
	public EncontroController() {
		this.encontroDAO = new EncontroDAO();
	}
	
	public void cadastrar(Encontro encontro) {
		encontroDAO.cadastrar(encontro);
	}
	
	public void atualizar(Encontro encontro) {
		encontroDAO.atualizar(encontro);
	};

	public void excluir(Encontro encontro) {
		encontroDAO.deletar(encontro);
	}
	
	public List<Encontro> listar(){
		return encontroDAO.listartodos();
	}
	
	public Encontro buscarId(int idEncontro) {
		return encontroDAO.buscarId(idEncontro);
	}
}
