package JavaSpaceImplementation;

import net.jini.core.transaction.server.TransactionManager;
import com.sun.jini.mahout.Locator;
import com.sun.jini.outrigger.Finder;
import java.rmi.RMISecurityManager;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionFactory;
import java.rmi.RemoteException;
import net.jini.core.lease.LeaseDeniedException;
import com.sun.jini.outrigger.DiscoveryLocator;
import com.sun.jini.outrigger.LookupFinder;
import com.sun.jini.mahalo.TxnManagerImpl;

public class TransactionManagerAccessor {
    private TransactionManager mgr;

    public TransactionManagerAccessor() {
 		mgr = getTrManager();
   }

    public TransactionManager getTrManager(String name) {
            Locator locator = null;
            Finder finder = null;
            
            if (System.getSecurityManager() == null) {
            System.setSecurityManager(
                new RMISecurityManager());
        }
        
            if (System.getProperty("com.sun.jini.use.registry") != null) {
//                locator = new com.sun.jini.mahout.RegistryLocator();
//                finder = new com.sun.jini.outrigger.RegistryFinder();
            } else {
//                locator = new com.sun.jini.outrigger.DiscoveryLocator();
                locator = new DiscoveryLocator();
//                finder = new com.sun.jini.outrigger.LookupFinder();
                finder = new LookupFinder();
            }

        return (TransactionManager)finder.find(locator, name);
    }
    
    public TransactionManager getTrManager() {
        return getTrManager(TxnManagerImpl.DEFAULT_NAME);
    }

	public Transaction getTransaction(long leaseTime) {
        try {
            Transaction.Created created =
                TransactionFactory.create(mgr, leaseTime);
            return created.transaction;
        } catch(RemoteException e) {
            e.printStackTrace();
            return null;
        } catch(LeaseDeniedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
