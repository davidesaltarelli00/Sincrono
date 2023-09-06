package it.sincrono.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.sincrono.entities.TipoAzienda;
import it.sincrono.entities.TipoCcnl;
import it.sincrono.entities.TipoContratto;
import it.sincrono.entities.TipoLivelloContratto;
import it.sincrono.repositories.exceptions.RepositoryException;

public interface TipologicheContrattoRepository
		extends JpaRepository<TipoAzienda, Integer>, TipologicheContrattoCustomRepository {

	@Query(value = "SELECT a FROM TipoAzienda a  ORDER BY a.id")
	public List<TipoAzienda> getAziendeMap() throws RepositoryException;

	@Query(value = "SELECT a FROM TipoContratto a  ORDER BY a.id")
	public List<TipoContratto> getTipoContrattoMap() throws RepositoryException;

	@Query(value = "SELECT a FROM TipoCcnl a ORDER BY a.id")
	public List<TipoCcnl> getCcnlMap() throws RepositoryException;

	@Query(value = "SELECT a FROM TipoLivelloContratto a ORDER BY a.id")
	public List<TipoLivelloContratto> getTipoLivelliContrattualiMap() throws RepositoryException;

	@Query(value = "SELECT a FROM TipoContratto a ORDER BY a.id")
	public List<TipoContratto> getTipoContratto() throws RepositoryException;

}