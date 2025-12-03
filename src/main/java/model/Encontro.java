package model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Encontro {

    private int idEncontro;
    private LocalDate data;
    private List<ServicoDoEncontro> servicosDoEncontro;
    private StatusEncontro status;

    public Encontro(int idEncontro, LocalDate data, StatusEncontro status) {
        this.idEncontro = idEncontro;
        this.data = data;
        this.servicosDoEncontro = new ArrayList<>();
        this.status = status;
    }

    public Encontro(LocalDate data, StatusEncontro status) {
        this.data = data;
        this.servicosDoEncontro = new ArrayList<>();
        this.status = status;
    }

    public Encontro() {
        this.servicosDoEncontro = new ArrayList<>();
    }

    public int getIdEncontro() {
        return idEncontro;
    }

    public LocalDate getData() {
        return data;
    }

    public List<ServicoDoEncontro> getServicos() {
        return servicosDoEncontro;
    }

    public StatusEncontro getStatus() {
        return status;
    }

    public void setIdEncontro(int idEncontro) {
        this.idEncontro = idEncontro;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    // MÉTODO NOVO: Setter para a lista de serviços
    public void setServicos(List<ServicoDoEncontro> servicos) {
        this.servicosDoEncontro = servicos;
    }

    // MÉTODO ADICIONAL (opcional): Para adicionar um serviço individual
    public void adicionarServico(ServicoDoEncontro servico) {
        if (this.servicosDoEncontro == null) {
            this.servicosDoEncontro = new ArrayList<>();
        }
        this.servicosDoEncontro.add(servico);
    }

    // MÉTODO ADICIONAL (opcional): Para remover um serviço
    public void removerServico(ServicoDoEncontro servico) {
        if (this.servicosDoEncontro != null) {
            this.servicosDoEncontro.remove(servico);
        }
    }

    public void setStatus(StatusEncontro status) {
        this.status = status;
    }

}