package MasterWorkerSystem;

import java.util.Vector;
import DataSpaceElements.*;

public class TaskGenerator {
    Master master;

    public TaskGenerator(Master master) {
        this.master = master;
    }

// write the tasks,codes,data to the DataSpace
    public void start() {
		for (int i = 0; i < master.tasks.size(); i++) {
			boolean writtenTask = false;
			while (!writtenTask) {
                // Write task to DataSpace
				writtenTask = master.space.writeTask((Task)master.tasks.get(i));
			}
		}
		for (int i = 0; i < master.codes.size(); i++) {
			boolean writtenCode = false;
			while (!writtenCode) {
                // Write code to DataSpace
				writtenCode = master.space.writeCode((Code)master.codes.get(i));
			}
		}
		for (int i = 0; i < master.data.size(); i++) {
			boolean writtenData = false;
			while (!writtenData) {
                // Write data to DataSpace
				writtenData = master.space.writeData((Data)master.data.get(i));
			}
		}
    }
}
