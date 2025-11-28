package model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Encontro {
	
	private int idEncontro;
	private LocalDate data;
	private List<ServicoEncontro> servicosDoEncontro;
	private StatusEncontro status;

	public Encontro(int idEncontro, LocalDate data, StatusEncontro status) {
		this.idEncontro=idEncontro;
		this.data=data;
		this.servicosDoEncontro= new ArrayList<>();
		this.status=status;
	}
	
	public Encontro(LocalDate data, StatusEncontro status) {
		this.data=data;
		this.servicosDoEncontro= new ArrayList<>();
		this.status=status;
	}
	
	public int getIdEncontro() {
		return idEncontro;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public List<ServicoEncontro> getServicos() {
		return servicosDoEncontro;
	}
	
	public StatusEncontro getStatus() {
		return status;
	}
	
	public void setIdEncontro(int idEncontro) {
		this.idEncontro=idEncontro;
	}
	
	public void setData(LocalDate data) {
		this.data=data;
	}
	
	public void setStatus(StatusEncontro status) {
		this.status = status;
	}
	
}
