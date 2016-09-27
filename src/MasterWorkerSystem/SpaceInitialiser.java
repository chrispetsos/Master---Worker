package MasterWorkerSystem;

import DataSpaceElements.*;

public class SpaceInitialiser {
    Master master;

    public SpaceInitialiser(Master master) {
        this.master = master;
    }

	// delete remaining codes and data from the DataSpace
    public void init() {
		for (int i=0;i<master.codes.size();i++){
			master.space.deleteCode(((Code)master.codes.get(i)).ID);
        }
		for (int i=0;i<master.data.size();i++){
			master.space.deleteData(((Data)master.data.get(i)).ID);
        }
    }
}
