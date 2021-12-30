set /P JDK_PATH=Enter jdk bin folder path:
echo "%JDK_PATH%"
setx JAVA_HOME "%JDK_PATH%" /m