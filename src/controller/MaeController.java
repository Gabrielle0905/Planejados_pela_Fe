package controller;

import dao.MaeDAO;
import model.Mae;
import java.util.List;

public class MaeController {

	private MaeDAO maeDAO;
	
	public MaeController() {
		this.maeDAO = new MaeDAO();
	}
	
	public void cadastrarMae(Mae mae) {
		maeDAO.cadastrar(mae);
	}
	
	public List<Mae> listarMaes(){
		return maeDAO.listartodos();
	};
	
	public Mae buscarId(int idMae) {
		return maeDAO.buscarId(idMae);
	};
	
	public void atualizarMae (Mae mae){
		maeDAO.atualizar(mae);
	};
	
	public void deletarMae(Mae mae) {
		maeDAO.deletar(mae);
	};

}
