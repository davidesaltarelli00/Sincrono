package it.sincrono.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "storico_commesse")
public class StoricoCommesse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_anagrafica")
	private Anagrafica anagrafica;

	@ManyToOne
	@JoinColumn(name = "id_commessa")
	private Commessa commessa;

	@Column(name = "utente_aggiornamento")
	private String utenteAggiornamento;

	@Column(name = "data_aggiornamento")
	private String dataAggiornamento;

	public StoricoCommesse(Integer id, Anagrafica anagrafica, Commessa commessa, String utenteAggiornamento) {
		super();
		this.id = id;
		this.anagrafica = anagrafica;
		this.commessa = commessa;
		this.utenteAggiornamento = utenteAggiornamento;
	}

	public StoricoCommesse(Integer id, Anagrafica anagrafica, Commessa commessa) {
		super();
		this.id = id;
		this.anagrafica = anagrafica;
		this.commessa = commessa;
	}

	public StoricoCommesse(Anagrafica anagrafica, Commessa commessa) {
		super();
		this.anagrafica = anagrafica;
		this.commessa = commessa;
	}

	public StoricoCommesse(Commessa commessa) {
		super();
		this.commessa = commessa;
	}

	public StoricoCommesse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Anagrafica getAnagrafica() {
		return anagrafica;
	}

	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}

	public Commessa getCommessa() {
		return commessa;
	}

	public void setCommessa(Commessa commessa) {
		this.commessa = commessa;
	}

	public String getUtenteAggiornamento() {
		return utenteAggiornamento;
	}

	public void setUtenteAggiornamento(String utenteAggiornamento) {
		this.utenteAggiornamento = utenteAggiornamento;
	}

	public String getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(String dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

}
