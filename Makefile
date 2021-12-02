all:
	@javac -d bin -classpath lib/ojdbc6.jar -sourcepath src src/TestConnection.java
