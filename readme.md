# Running the application from command line
 * Ready to run executable jar file is contained in the root of this repository - bschomework-0.1.0.jar
 * Run it from command line using java with -jar option
 * Two program arguments can be specified - absolute path to file with package and with fees
 * If you want to see logs on specified path, include JVM param with target directory -Dlogs.path
* Example command (on Windows):
java -Dlogs.path="c:\work\logs" -jar c:\work\bschomework-0.1.0.jar c:\work\testPackagesConfiguration.txt c:\work\testFeesConfiguration.txt
# Brief application description
* Upon startup application tries to load contents of file with fees and packages nad parse them as specified
* After startup application waits for user inputs about packages 
* After entering value user is notified by message in command line whether the input was correctly read or not
* Each minute summary about all packages is written to console as specified
* Program is terminated by typing "quit" command




