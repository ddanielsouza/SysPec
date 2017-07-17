package br.com.ads.syspec.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class AplicacaoRemedio implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@NotNull
	@Column(name="dt_cadastro")
	@Temporal(TemporalType.DATE)
	private Date dtCadastro = new Date();
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="dt_aplicacao")
	private Date dtAplicao = new Date();
	private String obs;
	@NotNull
	@Column(name="qtd_dose")
	private int qtdDose = 0;
	private String campanha;
	@NotNull
	@Column(name="tipo_aplicacao")
	private String tipoAplicacao;
	
	@NotNull
	@OneToOne
	private Animal animal;
	
	@NotNull
	@OneToOne
	private Insumo remedio;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDtCadastro() {
		return dtCadastro;
	}
	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	public Date getDtAplicao() {
		return dtAplicao;
	}
	public void setDtAplicao(Date dtAplicao) {
		this.dtAplicao = dtAplicao;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public int getQtdDose() {
		return qtdDose;
	}
	public void setQtdDose(int qtdDose) {
		this.qtdDose = qtdDose;
	}
	public String getCampanha() {
		return campanha;
	}
	public void setCampanha(String campanha) {
		this.campanha = campanha;
	}
	public String getTipoAplicacao() {
		return tipoAplicacao;
	}
	public void setTipoAplicacao(String tipoAplicacao) {
		this.tipoAplicacao = tipoAplicacao;
	}
	public Animal getAnimal() {
		return animal;
	}
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	public Insumo getRemedio() {
		return remedio;
	}
	public void setRemedio(Insumo remedio) {
		this.remedio = remedio;
	}
}
