@echo off
rem -------------------------------------------------------------
rem  TheKnife – launcher Windows (.bat)
rem  Si porta nella cartella dove risiede lo script
rem  e poi avvia il JAR con java.exe (non javaw.exe: così vedi
rem  eventuali errori su console)
rem -------------------------------------------------------------
cd /d "%~dp0"
java -jar TheKnife.jar
