package model;

import java.time.LocalDate;

public class Mae {
	
	private int idMae;
	private String nome;
	private LocalDate dataNasci;
	private int telefone;
	private String endereco;
	
	public Mae(int idMae, String nome, LocalDate dataNasci, int telefone, String endereco) {
		this.idMae = idMae;
		this.nome=nome;
		this.dataNasci=dataNasci;
		this.telefone=telefone;
		this.endereco=endereco;
	}
	
	public Mae(String nome, LocalDate dataNasci, int telefone, String endereco) {
		this.nome=nome;
		this.dataNasci=dataNasci;
		this.telefone=telefone;
		this.endereco=endereco;
	}
	
	public int getIdMae() {
		return idMae;
	}
	
	public String getNome() {
		return nome;
	}
	
	public LocalDate getDataNasci() {
		return dataNasci;
	}
	
	public int getTelefone() {
		return telefone;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	
	public void setIdMae(int idMae) {
		this.idMae=idMae;
	}
	
	public void setNome(String nome) {
		this.nome=nome;
	}
	
	public void setDataNasci(LocalDate dataNasci) {
		this.dataNasci=dataNasci;
	}
	
	public void setTelefone(int telefone) {
		this.telefone=telefone;
	}
	
	public void setEndereco(String endereco) {
		this.endereco=endereco;		
	}
	
}
