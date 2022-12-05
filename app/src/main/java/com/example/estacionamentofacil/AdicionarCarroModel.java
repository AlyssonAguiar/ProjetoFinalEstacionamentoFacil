package com.example.estacionamentofacil;

public class AdicionarCarroModel {
    private String id;
    private String nomeMotorista;
    private String placa;
    private String numeroMotorista;
    private String marca;
    private long tempo;
    private String taxa;
    private String userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public AdicionarCarroModel() {
    }

    public AdicionarCarroModel(String id, String nomeMotorista, String placa, String numeroMotorista, String marca, long tempo, String taxa, String userId, String Status) {
        this.id = id;
        this.nomeMotorista = nomeMotorista;
        this.placa = placa;
        this.numeroMotorista = numeroMotorista;
        this.marca = marca;
        this.tempo = tempo;
        this.taxa = taxa;
        this.userId = userId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumeroMotorista() {
        return numeroMotorista;
    }

    public void setNumeroMotorista(String numeroMotorista) {
        this.numeroMotorista = numeroMotorista;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public long getTempo() {
        return tempo;
    }

    public void setTempo(long tempo) {
        this.tempo = tempo;
    }

    public String getTaxa() {
        return taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
