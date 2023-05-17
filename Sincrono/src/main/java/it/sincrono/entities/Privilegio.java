package it.sincrono.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "privilegi")
public class Privilegio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_ruolo")
	private Ruolo ruolo;

	@ManyToOne
	@JoinColumn(name = "id_funzione")
	private Funzione funzione;

	@Column(name = "data_aggiornamento")
	private Date dataAggiornamento;

	@Column(name = "utente_aggiornamento")
	private String utenteAggiornamento;

	public Privilegio(Integer id, Ruolo ruolo, Funzione funzione, Date dataAggiornamento, String utenteAggiornamento) {
		super();
		this.id = id;
		this.ruolo = ruolo;
		this.funzione = funzione;
		this.dataAggiornamento = dataAggiornamento;
		this.utenteAggiornamento = utenteAggiornamento;
	}

	public Privilegio() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
