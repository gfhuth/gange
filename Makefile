all:
	@javac -d bin -classpath lib/ojdbc6.jar -sourcepath src src/main.java
	@java -classpath bin:lib/ojdbc6.jar main

testMenu:
	@javac -d bin -classpath lib/ojdbc6.jar -sourcepath src src/TestMenu.java
	@java -classpath bin:lib/ojdbc6.jar TestMenu	

