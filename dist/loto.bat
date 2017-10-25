@echo off

java -XX:+UseParallelGC -Xms1024M -Xmx3072M -jar loto.jar D_lotfac.zip

pause
