// Javaspace class
package JavaSpaceImplementation;

import net.jini.space.JavaSpace;
import java.rmi.RMISecurityManager;
import com.sun.jini.mahout.Locator;
import com.sun.jini.outrigger.Finder;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionFactory;
import java.rmi.RemoteException;
import net.jini.core.lease.LeaseDeniedException;
import net.jini.core.transaction.server.TransactionManager;
import net.jini.core.entry.Entry;
import com.sun.jini.outrigger.DiscoveryLocator;
import com.sun.jini.outrigger.LookupFinder;
import MasterWorkerSystem.DataSpace;
import DataSpaceElements.Task;
import DataSpaceElements.Code;
import DataSpaceElements.Data;
import DataSpaceElements.Result;

public class Javaspace extends DataSpace {
    public JavaSpace Space;		// the JavaSpace object
    public TransactionManagerAccessor transactionManager;

	// Constructor
    public Javaspace(String name) {
        transactionManager = new TransactionManagerAccessor();
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }
            if (System.getProperty("com.sun.jini.use.registry") == null) {
                Locator locator = new DiscoveryLocator();
                Finder finder = new LookupFinder();
                // get the JavaSpace service
                Space = (JavaSpace)finder.find(locator, name);
            } else {
                //                RefHolder rh = (RefHolder)Naming.lookup(name);
                //                return (JavaSpace)rh.proxy();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Javaspace() {
        this("JavaSpaces");
    }

    // implementation of the abstract class "DataSpace" methods

    public boolean writeTask(Task task){
		return write((Entry)task);
    }

    public boolean writeCode(Code code){
		return write((Entry)code);
    }

    public boolean writeData(Data data){
		return write((Entry)data);
    }

    public Result takeResult(){
		return (Result)take(new ResultEntry());
    }

    public void deleteCode(Long codeID){
		CodeEntry code = new CodeEntry();
		CodeEntry template = new CodeEntry();
        template.ID = codeID;
		code = (CodeEntry)take(template);
    }

    public void deleteData(Long dataID){
 		DataEntry data = new DataEntry();
		DataEntry template = new DataEntry();
        template.ID = dataID;
		data = (DataEntry)take(template);
    }

    public Task takeTask(){
		return (Task)take(new TaskEntry());
    }

    public Code readCode(Long codeID){
		CodeEntry template = new CodeEntry();
        template.ID = codeID;
		return (Code)read(template);
    }

    public Data readData(Long dataID){
        DataEntry template = new DataEntry();
        template.ID = dataID;
		return (Data)read(template);
    }

    public boolean writeResult(Result result){
		return write((Entry)result);
    }

	// writes an Entry in the JavaSpace under a transaction
    public boolean write(Entry entry) {
        // try to get a transaction with a 10-min lease time
        Transaction txn = transactionManager.getTransaction(1000 * 10 * 60);
        if (txn == null) {
            throw new RuntimeException("Can't obtain a transaction");
        }
        try {
            try {
                Space.write(entry, txn, Lease.FOREVER);
            } catch (Exception e) {
                txn.abort();
                return false;
            }
            txn.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Transaction failed");
            return false;
        }
    }

    // reads an entry from the JavaSpace according to the template
    //  under a transaction
    public Entry read(Entry template) {
        // try to get a transaction with a 10-min lease time
        Transaction txn = transactionManager.getTransaction(1000 * 10 * 60);
        if (txn == null) {
            throw new RuntimeException("Can't obtain a transaction");
        }
        Entry data;
        try {
            try {
                data = Space.read(template, txn, Long.MAX_VALUE);
            } catch (Exception e) {
                txn.abort();
                return null;
            }
            txn.commit();
            return data;
        } catch (Exception e) {
            System.err.println("Transaction failed");
            return null;
        }
    }

    // take = read and delete from JavaSpace
    public Entry take(Entry template) {
        // try to get a transaction with a 10-min lease time
        Transaction txn = transactionManager.getTransaction(1000 * 10 * 60);
        if (txn == null) {
            throw new RuntimeException("Can't obtain a transaction");
        }
        Entry data;
        try {
            try {
                data = Space.take(template, txn, Long.MAX_VALUE);
            } catch (Exception e) {
                txn.abort();
                return null;
            }
            txn.commit();
            return data;
        } catch (Exception e) {
            System.err.println("Transaction failed");
            return null;
        }
    }
}
