package br.com.ads.syspec.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.ads.syspec.service.AnimalService;
import br.com.ads.syspec.util.FacesMessages;
import br.com.ads.syspec.model.Animal;
import br.com.ads.syspec.model.Gestacao;
import br.com.ads.syspec.model.Procedencia;
import br.com.ads.syspec.model.Raca;
import br.com.ads.syspec.repository.AnimalRepository;
import br.com.ads.syspec.repository.GestacaoRepository;
import br.com.ads.syspec.repository.InseminacaoRepository;
import br.com.ads.syspec.repository.RacaRepository;

@Named
@ViewScoped
public class CadastroAnimalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AnimalService orcamentosService;
	@Inject
	private RacaRepository racaRepository;
	@Inject
	private AnimalRepository animalRepository; 
	@Inject
	private GestacaoRepository gestacaoRepository;
	@Inject
	private InseminacaoRepository inseminacaoRepository;
	@Inject
	private FacesMessages messages;

	private Animal animal = new Animal();
	private List<Raca> racas;
	private List<Animal> animaisFemeas = new ArrayList<>();
	private List<Animal> animaisMachos = new ArrayList<>();
	private Animal pai = new Animal();
	private Animal mae = new Animal();
	private String txtDtEstimada;

	//Habilitar e Desabilitar Campos
	// var == true Disabled
	// var != true Not Disabled
	private boolean isItMaeDisabled = true;
	private boolean isItPaiDisabled = true;
	private boolean isItInseminacaoDisabled = true;
	private boolean dtNascEstimada = true;
	private boolean dtNascExata = false;


	public void initialize(){
		animal = new Animal();
		racas = racaRepository.findAll();
		try{
			Map<String, String> params =FacesContext.getCurrentInstance().
					getExternalContext().getRequestParameterMap();
			String parameterOne = params.get("idGestacao");

			if(!parameterOne.isEmpty()) {
				long id = Long.valueOf(parameterOne);
				Gestacao gestacao = gestacaoRepository.findById(id);

				gestacao.setPartoSucesso(true);
				animal.setGestacao(gestacao);
				animal.setDtNascimento(gestacao.getDtParto());
				animal.setMae(gestacao.getAnimal());
				mae = animal.getMae();
				animal.setPai(gestacao.getPai());
				pai = getPai();
				animal.setInseminacao(gestacao.getInseminacao());
				animal.setProcedencia(gestacao.getProcedencia());
				animal.setRaca(gestacao.getAnimal().getRaca());
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(inseminacaoRepository.findSemCria().size());
	}

	public void salvar() {
		try {
			orcamentosService.salvar(animal, mae, pai, !dtNascEstimada, txtDtEstimada);
			animal = new Animal();
			pai = new Animal();
			mae = new Animal();
			habilitarDesabilitarCampos();
			txtDtEstimada = "";
			messages.info("Animal salvo com sucesso!");
		} catch (Exception e) {
			messages.error(e.getMessage());
		}
	}

	public void listarFemeas(){
		animaisFemeas = animalRepository.findPorSexo("F");
	}

	public void listarMachos(){
		animaisMachos = animalRepository.findPorSexo("M");
	}

	public void validarData(){
		try {
			orcamentosService.textoDataParaObjData(txtDtEstimada, animal);
		} catch (Exception e) {
			messages.error(e.getMessage());
		}
	}

	public Animal getAnimal() {
		return animal;
	}


	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public List<Raca> getRacas() {
		return racas;
	}

	public void setRacas(List<Raca> racas) {
		this.racas = racas;
	}

	public Procedencia[] getProcedencia(){
		return Procedencia.values();
	}

	public void habilitarDesabilitarCampos(){
		Procedencia proc = animal.getProcedencia();
		isItMaeDisabled = true;
		isItPaiDisabled = true;
		isItInseminacaoDisabled = true;

		if(proc == Procedencia.NASCIMENTO_INSEMINACAO){
			isItInseminacaoDisabled = false;
			isItMaeDisabled = false;
		}
		else
			if(proc == Procedencia.NASCIMENTO_NATURAL){
				isItMaeDisabled = false;
				isItPaiDisabled = false;
			}
	}

	public void onRowSelectMae(SelectEvent event) {
		mae = ((Animal) event.getObject());
	}
	public void onRowSelectPai(SelectEvent event) {
		pai = ((Animal) event.getObject());
	}

	public List<Animal> getAnimaisFemeas() {
		return this.animaisFemeas;
	}

	public void setAnimaisFemeas(List<Animal> animaisFemeas) {
		this.animaisFemeas = animaisFemeas;
	}

	public List<Animal> getAnimaisMachos() {
		return animaisMachos;
	}

	public void setAnimaisMachos(List<Animal> animaisMachos) {
		this.animaisMachos = animaisMachos;
	}

	public Animal getPai() {
		return pai;
	}

	public void setPai(Animal pai) {
		this.pai = pai;
	}

	public Animal getMae() {
		return mae;
	}

	public void setMae(Animal mae) {
		this.mae = mae;
	}

	//isIt ... Not Working
	public boolean getItMaeDisabled() {
		return isItMaeDisabled;
	}
	public boolean getItPaiDisabled() {
		return isItPaiDisabled;
	}
	public boolean getItInseminacaoDisabled() {
		return isItInseminacaoDisabled;
	}

	public void addMessage() {
		if(dtNascEstimada == dtNascExata)
			dtNascExata = !dtNascExata;
	}

	public boolean isDtNascEstimada() {
		return dtNascEstimada;
	}

	public void setDtNascEstimada(boolean dtNascEstimada) {
		this.dtNascEstimada = dtNascEstimada;
		this.dtNascExata = !dtNascEstimada;
	}

	public boolean isDtNascExata() {
		return dtNascExata;
	}

	public void setDtNascExata(boolean dtNascExata) {
		this.dtNascExata = dtNascExata;
		this.dtNascEstimada = !dtNascExata;
	}

	public String getTxtDtEstimada() {
		return txtDtEstimada;
	}

	public void setTxtDtEstimada(String txtDtEstimada) {
		this.txtDtEstimada = txtDtEstimada;
	}

}
