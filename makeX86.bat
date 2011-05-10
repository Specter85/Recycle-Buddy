"C:\Program Files (x86)\Java\jdk1.6.0_25\bin\javac" -sourcepath src src\recycleBuddy\*.java -d bin
"C:\Program Files (x86)\Java\jdk1.6.0_25\bin\jar" cvfm RecycleBuddy.jar manifest.txt -C bin recycleBuddy
PAUSE