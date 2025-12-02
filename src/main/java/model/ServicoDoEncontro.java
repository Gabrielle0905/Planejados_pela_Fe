package model;

public class ServicoDoEncontro {
	
	private int idServico;
	private TipoServico tipo;
	private Mae maeResponsavel;
	private String descricao;
	
	public ServicoDoEncontro(int idServico, TipoServico tipo, Mae maeResponsavel, String descricao) {
		this.idServico=idServico;
		this.tipo=tipo;
		this.maeResponsavel=maeResponsavel;
		this.descricao=descricao;
	}
	
	public ServicoDoEncontro(TipoServico tipo, Mae maeResponsavel, String descricao) {
		this.tipo=tipo;
		this.maeResponsavel=maeResponsavel;
		this.descricao=descricao;
	}
	
	public ServicoDoEncontro() {
		
	}
	
	public int getIdServico() {
		return idServico;
	}
	
	public TipoServico getServico() {
		return tipo;
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
	
	public void setServico(TipoServico tipo) {
		this.tipo=tipo;
	}
	
	public void setMaeResponsavel(Mae maeResponsavel){
		this.maeResponsavel=maeResponsavel;	
	}
	
	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}
	
}
