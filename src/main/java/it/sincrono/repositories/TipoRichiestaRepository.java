package it.sincrono.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.sincrono.entities.Richieste;
import it.sincrono.entities.TipoRichieste;
import it.sincrono.repositories.dto.RichiestaDto;
import it.sincrono.requests.RichiestaRequest;

public interface TipoRichiestaRepository extends JpaRepository<TipoRichieste, Integer> {
	

	@Query("SELECT a FROM TipoRichieste a WHERE a.richiesta.anno=?1 and a.richiesta.mese=?2 and a.richiesta.anagrafica.id=?3")
	public List<TipoRichieste> getRichieste(Integer anno,Integer mese,Integer idAnagrafica);
	
	@Query("SELECT a FROM TipoRichieste a WHERE a.richiesta.anno=?1 and a.richiesta.mese=?2 and a.richiesta.anagrafica.id=?3 and a.richiesta.stato=true")
	public List<TipoRichieste> getRichiesteAccettate(Integer anno,Integer mese,Integer idAnagrafica);
	
	@Query("SELECT a FROM TipoRichieste a WHERE a.richiesta.id=?1")
	public List<TipoRichieste> getTipoRichiesteByIdRichieste(Integer idRichiesta);
	
	@Query("select tr from TipoRichieste tr where tr.richiesta.anno=?1 and tr.richiesta.mese=?2 and tr.richiesta.anagrafica.id=?3 and tr.nGiorno=?4 and ((TIME(?5) <= TIME(tr.aOra) AND TIME(?6) >= TIME(tr.daOra)) OR (TIME(?5)) >= TIME(tr.daOra) AND TIME(?6) <= TIME(tr.aOra))")
	public List<TipoRichieste> getRichiesteValidate(Integer anno,Integer mese,Integer idAnagrafica,Integer ngiorno,String aOra,String daOra);



}
