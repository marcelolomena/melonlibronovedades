package cl.loso.melon.server.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import cl.loso.melon.server.gae.PMF;
import com.google.appengine.api.datastore.DatastoreNeedIndexException;
import com.google.appengine.api.datastore.DatastoreTimeoutException;

public class BusquedaML {

	private static final Logger log = Logger.getLogger(BusquedaML.class
			.getName());

	public static final int MAXIMUM_NUMBER_OF_WORDS_TO_SEARCH = 5;

	public static final int MAX_NUMBER_OF_WORDS_TO_PUT_IN_INDEX = 200;

	public static List<FallaLN> busquedaFallas(Date fecha_ini, Date fecha_ter,
			String queryString, String idEquipo) {
		PersistenceManager pm = null;
		List<FallaLN> result = null;
		try {
			Map<String, Object> pars = new HashMap<String, Object>();
			StringBuffer queryBuffer = new StringBuffer();

			Set<String> queryTokens = BusquedaMLUtils
					.getTokensForIndexingOrQuery(queryString,
							MAXIMUM_NUMBER_OF_WORDS_TO_SEARCH);

			Iterator<String> ite = queryTokens.iterator();

			int parameterCounter = 0;

			if (idEquipo.equals("0")) {
				queryBuffer
						.append("fecha >= :fechaini && fecha <= :fechater && ");
				pars.put("fechaini", fecha_ini);
				pars.put("fechater", fecha_ter);
			} else {
				queryBuffer
						.append("fecha >= :fechaini && fecha <= :fechater && equipo == :idequipo && ");
				pars.put("fechaini", fecha_ini);
				pars.put("fechater", fecha_ter);
				pars.put("idequipo", Long.valueOf(idEquipo));
			}

			while (ite.hasNext()) {

				String param = ite.next();

				queryBuffer.append("fts == :param" + parameterCounter);
				if (parameterCounter + 1 < queryTokens.size()) {
					queryBuffer.append(" && ");
				}

				pars.put("param" + parameterCounter, param);

				parameterCounter++;

			}

			pm = PMF.get().getPersistenceManager();

			Query query = pm.newQuery(FallaLN.class);

			query.setFilter(queryBuffer.toString());
			query.setOrdering("fecha DESC");//ASC
			result = (List<FallaLN>) pm.detachCopyAll((List<FallaLN>) (query
					.executeWithMap(pars)));

		} catch (DatastoreTimeoutException e) {
			log.severe(e.getMessage());
			log.severe("datastore timeout at: " + queryString);
		} catch (DatastoreNeedIndexException e) {
			log.severe(e.getMessage());
			log.severe("datastore need index exception at: " + queryString);
		} finally {
			pm.close();
		}

		return result;

	}

	public static List<BitacoraLN> busquedaNovedades(Date fecha_ini,
			Date fecha_ter, String queryString, String idEquipo) {
		PersistenceManager pm = null;
		List<BitacoraLN> result = null;
		try {
			Map<String, Object> pars = new HashMap<String, Object>();
			StringBuffer queryBuffer = new StringBuffer();

			Set<String> queryTokens = BusquedaMLUtils
					.getTokensForIndexingOrQuery(queryString,
							MAXIMUM_NUMBER_OF_WORDS_TO_SEARCH);

			Iterator<String> ite = queryTokens.iterator();

			int parameterCounter = 0;

			if (idEquipo.equals("0")) {
				queryBuffer
						.append("fecha >= :fechaini && fecha <= :fechater && ");
				pars.put("fechaini", fecha_ini);
				pars.put("fechater", fecha_ter);
			} else {
				queryBuffer
						.append("fecha >= :fechaini && fecha <= :fechater && equipo == :idequipo && ");
				pars.put("fechaini", fecha_ini);
				pars.put("fechater", fecha_ter);
				pars.put("idequipo", Long.valueOf(idEquipo));
			}

			while (ite.hasNext()) {

				String param = ite.next();

				queryBuffer.append("fts == :param" + parameterCounter);
				if (parameterCounter + 1 < queryTokens.size()) {
					queryBuffer.append(" && ");
				}

				pars.put("param" + parameterCounter, param);

				parameterCounter++;

			}

			pm = PMF.get().getPersistenceManager();

			Query query = pm.newQuery(BitacoraLN.class);

			query.setFilter(queryBuffer.toString());
			query.setOrdering("fecha DESC");//ASC
			result = (List<BitacoraLN>) pm
					.detachCopyAll((List<BitacoraLN>) (query
							.executeWithMap(pars)));

		} catch (DatastoreTimeoutException e) {
			log.severe(e.getMessage());
			log.severe("datastore timeout at: " + queryString);
		} catch (DatastoreNeedIndexException e) {
			log.severe(e.getMessage());
			log.severe("datastore need index exception at: " + queryString);
		} finally {
			pm.close();
		}

		return result;

	}

	public static void actualizaFTSFalla(FallaLN fueraServicio) {

		StringBuffer sb = new StringBuffer();

		sb.append(fueraServicio.getProblema());

		Set<String> new_ftsTokens = BusquedaMLUtils
				.getTokensForIndexingOrQuery(sb.toString(),
						MAX_NUMBER_OF_WORDS_TO_PUT_IN_INDEX);

		Set<String> ftsTokens = fueraServicio.getFts();

		ftsTokens.clear();

		for (String token : new_ftsTokens) {
			ftsTokens.add(token);

		}
	}

	public static void actualizarFTSNovedad(BitacoraLN novedadEquipo) {

		StringBuffer sb = new StringBuffer();

		sb.append(novedadEquipo.getComentario());

		Set<String> new_ftsTokens = BusquedaMLUtils
				.getTokensForIndexingOrQuery(sb.toString(),
						MAX_NUMBER_OF_WORDS_TO_PUT_IN_INDEX);

		Set<String> ftsTokens = novedadEquipo.getFts();

		ftsTokens.clear();

		for (String token : new_ftsTokens) {
			ftsTokens.add(token);

		}
	}

}
