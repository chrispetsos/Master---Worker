Notes for starting the Master and the Worker (in Windows OS)
--------------------------------------------

1.Edit the setpaths.bat file in this folder
2.Change the JAVAHOME parameter to the path where you have Java installed and specifically
  the ...\bin path were the java.exe,rmid.exe exist.
  NOTE: Java 1.3.1 or later is required by Jini 1.2.1

Start the Worker
---------------
1.Run "runWorker.bat"
At the beginning the Worker is trying to resolve the JavaSpace.
You should be notified when the Worker has found the JavaSpace by a "DataSpace found!" message.
This should normally take less than a couple of seconds in a LAN.
Once the worker has found the JavaSpace he can start work by pressing the Start Work button.
He is now waiting for a master to generate some tasks (in this version the Worker will be waiting
for a task not more than 10 minutes;then he will be halted).
You can pause and resume the Worker by pressing the according buttons.
The Init button empties the buffer of the Worker (do not use it!!!)
NOTE: 	For computers where only the Worker will be running only this folder is required
	The Jini distribution needs not to be installed.

Start the Raytrace master
------------------------
1.Run "runMasterRaytrace.bat"
At the beginning the master is trying to resolve the JavaSpace.
You should be notified when the master has found the JavaSpace by a "DataSpace found!" message.
This should normally take less than a couple of seconds in a LAN.
You can set the chunk size for the raytrace tasks using the text field that currently
has the value 500.
NOTE: The chunk size number should divide 500 without remainder (you can use 20,50,100,250).
Once the master has found the JavaSpace he can start generating the tasks by pressing 
the Generate Tasks button (you should be notified when all tasks have been generated).
By pressing the View Results until now button you view the segments of the picture that have been
calculated until now.
When the computation has ended you should get a message and the number of seconds that it lasted.
At this point do not forget to empty the DataSpace by pressing the Empty DataSpace button!!! (you
will be notified with a message when the space has been emptied.

With similar steps you can run the Sin Master too (runMasterSin.bat).

----------------------------------------------------------------------

Normally you should run one master in the network and as many workers as you want to
participate in the computation. You can experiment further...