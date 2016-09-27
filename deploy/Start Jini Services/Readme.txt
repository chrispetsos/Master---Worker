Notes for starting the Jini Services (in Windows OS)
------------------------------------

1.Download the Jini 1.2.1 distribution from www.jini.org
2.Install it to the computer where the services will be running
3.Edit the setpaths.bat file in this folder
4.Change the JINIHOME parameter to the path where you installed Jini 1.2.1
5.Change the DOWNLOADHOST parameter to the IP of the computer were the Jini distribution
  exists (which in this case will be the IP of the computer where you are running the services)
  NOTE: Do not change the port number (:8080)
6.Change the JAVAHOME parameter to the path where you have Java installed and specifically
  the ...\bin path were the java.exe,rmid.exe exist.
  NOTE: Java 1.3.1 or later is required by Jini 1.2.1
6.Run "1 runhttpd.bat"
7.Run "2 runrmid.bat"
8.Run "3 runreggie.bat"
9.Run "4 runtransaction.bat"
10.Run "5 runJavaSpace.bat"

Successful outcome
------------------
If everything went OK you should end up with two terminal windows open.
In the httpd window there should be messages that several Jini packages have been requested
by the computer where you are running the services.
Furthermore four new folders must have been created in this folder named:
	rmid.log
	reggie.log
	txn_log
	persistent.logdir
If at anytime you wish to restart the services these folders must be deleted first.