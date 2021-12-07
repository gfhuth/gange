all:
	@javac -d bin -classpath lib/ojdbc11.jar -sourcepath src src/main.java
	@java -classpath bin:lib/ojdbc11.jar main

testMenu:
	@javac -d bin -classpath lib/ojdbc11.jar -sourcepath src src/TestConnection.java
	@java -classpath bin:lib/ojdbc11.jar TestConnection

