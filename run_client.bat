set /p PORT="Enter port: "
java -Xdiag -jar Lab7/Lab7.jar "client" "localhost" %PORT%
pause