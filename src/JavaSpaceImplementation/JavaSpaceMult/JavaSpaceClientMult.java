package JavaSpaceImplementation.JavaSpaceMult;

import MasterWorkerSystem.DataSpaceClient;
public class JavaSpaceClientMult extends DataSpaceClient {
    public static void main(String[] args) {
        createTasksCodesData();
    	master.startComputing();
        viewResults();
    }

    private static void createTasksCodesData(){
        MultCode codeElement = new MultCode();
        codeElement.ID = master.giveUniqueID();
        codes.add(codeElement);

        for (int i = 0; i < 10; i++) {
            // Create data for this mult task
            MultData dataElement = new MultData(new Integer(i), new Integer(i));
            // Assign unique ID to data partition
            dataElement.ID = master.giveUniqueID();
            // Create task and assign this ID to this task
            MultTask taskElement = new MultTask();
            taskElement.codeID = codeElement.ID;
			taskElement.dataID = dataElement.ID;

            data.addElement(dataElement);
            tasks.addElement(taskElement);
        }
    }

    private static void viewResults() {
    for (int i=0;i<master.results.size();i++)
		System.out.println(master.results.get(i));
    }
}
