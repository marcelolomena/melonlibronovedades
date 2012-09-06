package cl.loso.melon.server.negocio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.TipoLN;
import cl.loso.melon.server.persistencia.TipoLNDAO;

public class TipoLNBO {
	private static Log log = LogFactory.getLog(TipoLNBO.class);

	public static void guardarTipoLN(String tipo,String horini,String horter,String orden) throws Exception {
		try {
			
			DateFormat formato = new SimpleDateFormat("HH:mm");
			Date hora_inicio = (Date) formato.parse(horini);
			Date hora_termino = (Date) formato.parse(horter);
		
			TipoLN iLVS=new TipoLN(tipo.toLowerCase(), hora_inicio, hora_termino,Integer.valueOf(orden));
			TipoLNDAO.insertar(iLVS);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	public static void actualizarTipoLN(String id,String tipo,String horini,String horter,String orden) throws Exception {
		try {
			DateFormat formato = new SimpleDateFormat("HH:mm");
			Date hora_inicio = (Date) formato.parse(horini);
			Date hora_termino = (Date) formato.parse(horter);
			
			TipoLNDAO.actualizar(Long.valueOf(Long.parseLong(id)),tipo,hora_inicio,hora_termino,Integer.valueOf(orden));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}	
	
	public static List<TipoLN> obtenerTipoLN() throws Exception {
		try {
			return TipoLNDAO.obtener();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	public static long obtenerTurnoDefault() throws Exception {
		long orden=0;
		try {
			final String  DATE_TIME_FORMAT = "yyyyMMdd-HH:mm:ss";
			SimpleDateFormat  sdf = new SimpleDateFormat (DATE_TIME_FORMAT);
			
			TimeZone correctTZ = TimeZone.getTimeZone("America/Santiago");

			sdf.setTimeZone(correctTZ);

			Date currentTime= sdf.parse(sdf.format(new Date()));
			
	
			Calendar fechadehoy = Calendar.getInstance(correctTZ);
			fechadehoy.setTime(currentTime);
			
			log.info("la hora chilenisima es : " + currentTime);

			//Date fechadehoy= new Date();
 			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			
			//fechadehoy.set(fechadehoy.get(Calendar.YEAR), fechadehoy.get(Calendar.MONTH), fechadehoy
					//.get(Calendar.DAY_OF_MONTH), fechadehoy.get(Calendar.HOUR_OF_DAY), fechadehoy.get(Calendar.MINUTE));
			 
			//log.info("HOYDIA : " + new Date());
			List<TipoLN> turnolst=TipoLNDAO.obtener();
			
			for(TipoLN turno:turnolst){
				Calendar ini=Calendar.getInstance();
				Calendar ter=Calendar.getInstance();
				ini.setTime(turno.getHorainicio());
				ter.setTime(turno.getHoratermino());
				
				cal1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1
						.get(Calendar.DAY_OF_MONTH), ini.get(Calendar.HOUR_OF_DAY), ini.get(Calendar.MINUTE));
				cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2
						.get(Calendar.DAY_OF_MONTH), ter.get(Calendar.HOUR_OF_DAY), ter.get(Calendar.MINUTE));
				
				log.info("cal1 : " + cal1.getTime());
				log.info("cal2 : " + cal2.getTime());
				//log.info("la hora actual es : " + fechadehoy.getTime());
				
				
				if( timeIsBefore(cal1.getTime(),fechadehoy.getTime()) && timeIsBefore(fechadehoy.getTime(),cal2.getTime()) ){
					//if( timeIsBefore(cal1.getTime(),fechadehoy) && timeIsBefore(fechadehoy,cal2.getTime()) ){
					orden=turno.getId();
					log.info("orden : " + orden);	
					break;
								
				}
				
				/*
				log.info("horini : " + cal1.getTimeInMillis() + " ahora : "  + fechadehoy.getTimeInMillis() + " horter : " + cal2.getTimeInMillis());
				if(cal1.getTimeInMillis() < fechadehoy.getTimeInMillis() && fechadehoy.getTimeInMillis()<cal2.getTimeInMillis() ){
					orden=turno.getId();
					log.info("orden : " + orden);
					break;
				}
				*/

			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return orden;
	}	
	
	public static void borrarTipoLN(String id) throws Exception {
		try {
			TipoLNDAO.borrar(Long.valueOf(Long.parseLong(id)));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}	
	
	public static TipoLN editarTipoLN(String id) throws Exception {
		try {
			return TipoLNDAO.getTipoLVSbyId(Long.valueOf(Long.parseLong(id)));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;

		}
	}
	
	private static boolean timeIsBefore(Date d1, Date d2) {
		  DateFormat f = new SimpleDateFormat("HH:mm");
		  return f.format(d1).compareTo(f.format(d2)) < 0;
		}
	
}
