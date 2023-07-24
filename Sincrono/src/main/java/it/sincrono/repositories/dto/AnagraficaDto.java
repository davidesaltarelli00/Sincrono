package it.sincrono.repositories.dto;

import java.util.List;

import it.sincrono.entities.Anagrafica;
import it.sincrono.entities.Commessa;
import it.sincrono.entities.Contratto;
import it.sincrono.entities.Ruolo;
import it.sincrono.entities.StoricoCommesse;
import it.sincrono.entities.StoricoContratti;

public class AnagraficaDto {

	private Anagrafica anagrafica;

	private Contratto contratto;

	private List<Commessa> commessa;
	
	private Ruolo ruolo;
	


	public AnagraficaDto(Anagrafica anagrafica, Contratto contratto, List<Commessa> commessa, Ruolo ruolo) {
		super();
		this.anagrafica = anagrafica;
		this.contratto = contratto;
		this.commessa = commessa;
		this.ruolo = ruolo;
	}

	
	public AnagraficaDto() {
		super();
	}


	public Anagrafica getAnagrafica() {
		return anagrafica;
	}


	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}


	public Contratto getContratto() {
		return contratto;
	}


	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}


	public List<Commessa> getCommessa() {
		return commessa;
	}


	public void setCommessa(List<Commessa> commessa) {
		this.commessa = commessa;
	}


	public Ruolo getRuolo() {
		return ruolo;
	}


	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	
}
