package br.com.ads.syspec.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ads.syspec.model.Extracao;

public class DashRepository implements Serializable{
	@Inject
	private EntityManager manager;
	
	

	@SuppressWarnings("unchecked")
	public List<Object[]> findProducaoMensal(String anoProducaoBar) {
		String sql = "SELECT (EXTRACT (MONTH FROM dt_extracao_inicio)) AS mes, "
				+ "(EXTRACT (YEAR FROM dt_extracao_inicio)) AS ano, "
				+ "(SUM(qtd)) AS qtd "
				+ "FROM extracao INNER JOIN ordenha ON extracao.id = ordenha.extracao_id "
				+ "WHERE (EXTRACT (YEAR FROM dt_extracao_inicio)) = :anoFind "
				+ "GROUP BY mes, ano ";

		return manager.createNativeQuery(sql)
				.setParameter("anoFind", Double.valueOf(anoProducaoBar))
				.getResultList();
		
	}



	public List<Extracao> findProducaoDiaria(int mes) {
		return manager.createQuery("FROM Extracao WHERE MONTH(dtExtracaoInicio) = :mes")
				.setParameter("mes", mes)
				.getResultList();
	}
}