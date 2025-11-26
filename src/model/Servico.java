package model;

public class Servico {
	
	private int idServico;
	private TipoServico servico;
	private Mae maeResponsavel;
	private String descricao;
	
	public Servico(int idServico, TipoServico servico, Mae maeResponsavel, String descricao) {
		this.idServico=idServico;
		this.servico=servico;
		this.maeResponsavel=maeResponsavel;
		this.descricao=descricao;
	}
	
	public Servico(TipoServico servico, Mae maeResponsavel, String descricao) {
		this.servico=servico;
		this.maeResponsavel=maeResponsavel;
		this.descricao=descricao;
	}
	
	public int getIdServico() {
		return idServico;
	}
	
	public TipoServico getServico() {
		return servico;
	}
	
	public Mae getMaeResponsavel() {
		return maeResponsavel;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setIdServico(int idServico) {
		this.idServico=idServico;
	}
	
	public void setServico(TipoServico servico) {
		this.servico=servico;
	}
	
	public void setMaeResponsavel(Mae maeResponsavel){
		this.maeResponsavel=maeResponsavel;	
	}
	
	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}
	
}
