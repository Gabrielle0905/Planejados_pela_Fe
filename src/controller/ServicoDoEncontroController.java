package controller;

import dao.ServicodoEncontroDAO;
import model.ServicoDoEncontro;
import model.Encontro;
import java.util.List;

public class ServicoDoEncontroController {
	
	private ServicodoEncontroDAO servicoDAO;
	
	public ServicoDoEncontroController() {
		this.servicoDAO = new ServicodoEncontroDAO();
	}
	
	public void cadastrar(ServicoDoEncontro servico, Encontro encontro) {
		servicoDAO.cadastrar(servico, encontro);
	};
	
	public void atualizar(ServicoDoEncontro servico, Encontro encontro) {
		servicoDAO.atualizar(servico, encontro);
	}
	
	public void deletar(ServicoDoEncontro servico) {
		servicoDAO.deletar(servico);
	}
	
	public ServicoDoEncontro buscarId(int idServico) {
		return servicoDAO.buscarId(idServico);
	}
	
	public List<ServicoDoEncontro> listarTodos(){
		return servicoDAO.listartodos();
	}

}
