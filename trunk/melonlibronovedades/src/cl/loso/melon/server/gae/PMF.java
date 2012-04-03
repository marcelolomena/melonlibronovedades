package cl.loso.melon.server.gae;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class PMF {
	private static  Log log = LogFactory.getLog(PMF.class);
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}