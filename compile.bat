javac -d "." -encoding "utf-8" ./eterna/uni/secondsem/*.java
javac -d "." -encoding "utf-8" ./eterna/uni/secondsem/commands/*.java
javac -d "." -encoding "utf-8" ./eterna/uni/secondsem/networking/*.java
javac -d "." -encoding "utf-8" ./eterna/uni/secondsem/client/*.java
javac -d "." -encoding "utf-8" ./eterna/uni/secondsem/server/*.java
jar -cfm Lab7.jar manifest7.txt .
pause