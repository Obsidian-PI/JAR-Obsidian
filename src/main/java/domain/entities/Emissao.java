package domain.entities;

public class Emissao {

    private String gas;
    private String setorEmissao;
    private String estado;
    private Double doisMilDoze;
    private Double doisMilTreze;
    private Double doisMilQuatorze;
    private Double doisMilQuinze;
    private Double doisMilDezesseis;
    private Double doisMilDezessete;
    private Double doisMilDezoito;
    private Double doisMilDezenove;
    private Double doisMilVinte;
    private Double doisMilVinteUm;
    private Double doisMilVinteDois;

    public Emissao() {
    }

    public Emissao(String gas, String setorEmissao, String estado, Double doisMilDoze, Double doisMilTreze, Double doisMilQuatorze, Double doisMilQuinze, Double doisMilDezesseis, Double doisMilDezessete, Double doisMilDezoito, Double doisMilDezenove, Double doisMilVinte, Double doisMilVinteUm, Double doisMilVinteDois) {
        this.gas = gas;
        this.setorEmissao = setorEmissao;
        this.estado = estado;
        this.doisMilDoze = doisMilDoze;
        this.doisMilTreze = doisMilTreze;
        this.doisMilQuatorze = doisMilQuatorze;
        this.doisMilQuinze = doisMilQuinze;
        this.doisMilDezesseis = doisMilDezesseis;
        this.doisMilDezessete = doisMilDezessete;
        this.doisMilDezoito = doisMilDezoito;
        this.doisMilDezenove = doisMilDezenove;
        this.doisMilVinte = doisMilVinte;
        this.doisMilVinteUm = doisMilVinteUm;
        this.doisMilVinteDois = doisMilVinteDois;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getSetorEmissao() {
        return setorEmissao;
    }

    public void setSetorEmissao(String setorEmissao) {
        this.setorEmissao = setorEmissao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getDoisMilDoze() {
        return doisMilDoze;
    }

    public void setDoisMilDoze(Double doisMilDoze) {
        this.doisMilDoze = doisMilDoze;
    }

    public Double getDoisMilTreze() {
        return doisMilTreze;
    }

    public void setDoisMilTreze(Double doisMilTreze) {
        this.doisMilTreze = doisMilTreze;
    }

    public Double getDoisMilQuatorze() {
        return doisMilQuatorze;
    }

    public void setDoisMilQuatorze(Double doisMilQuatorze) {
        this.doisMilQuatorze = doisMilQuatorze;
    }

    public Double getDoisMilQuinze() {
        return doisMilQuinze;
    }

    public void setDoisMilQuinze(Double doisMilQuinze) {
        this.doisMilQuinze = doisMilQuinze;
    }

    public Double getDoisMilDezesseis() {
        return doisMilDezesseis;
    }

    public void setDoisMilDezesseis(Double doisMilDezesseis) {
        this.doisMilDezesseis = doisMilDezesseis;
    }

    public Double getDoisMilDezessete() {
        return doisMilDezessete;
    }

    public void setDoisMilDezessete(Double doisMilDezessete) {
        this.doisMilDezessete = doisMilDezessete;
    }

    public Double getDoisMilDezoito() {
        return doisMilDezoito;
    }

    public void setDoisMilDezoito(Double doisMilDezoito) {
        this.doisMilDezoito = doisMilDezoito;
    }

    public Double getDoisMilDezenove() {
        return doisMilDezenove;
    }

    public void setDoisMilDezenove(Double doisMilDezenove) {
        this.doisMilDezenove = doisMilDezenove;
    }

    public Double getDoisMilVinte() {
        return doisMilVinte;
    }

    public void setDoisMilVinte(Double doisMilVinte) {
        this.doisMilVinte = doisMilVinte;
    }

    public Double getDoisMilVinteUm() {
        return doisMilVinteUm;
    }

    public void setDoisMilVinteUm(Double doisMilVinteUm) {
        this.doisMilVinteUm = doisMilVinteUm;
    }

    public Double getDoisMilVinteDois() {
        return doisMilVinteDois;
    }

    public void setDoisMilVinteDois(Double doisMilVinteDois) {
        this.doisMilVinteDois = doisMilVinteDois;
    }

    @Override
    public String toString() {
        return "Emissoes{" +
                "gas='" + gas + '\'' +
                ", setorEmissao='" + setorEmissao + '\'' +
                ", estado='" + estado + '\'' +
                ", 2012=" + doisMilDoze +
                ", 2013=" + doisMilTreze +
                ", 2014=" + doisMilQuatorze +
                ", 2015=" + doisMilQuinze +
                ", 2016=" + doisMilDezesseis +
                ", 2017=" + doisMilDezessete +
                ", 2018=" + doisMilDezoito +
                ", 2019=" + doisMilDezenove +
                ", 2020=" + doisMilVinte +
                ", 2021=" + doisMilVinteUm +
                ", 2022=" + doisMilVinteDois +
                '}';
    }
}
