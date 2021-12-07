all:
	@javac -d bin -classpath lib/ojdbc11.jar -sourcepath src src/main.java
	@java -classpath bin:lib/ojdbc11.jar main

testMenu:
	@javac -d bin -classpath lib/ojdbc11.jar -sourcepath src src/TestMenu.java
	@java -classpath bin:lib/ojdbc11.jar TestMenu	

