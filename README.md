================================================================================
TheKnife – README
================================================================================

Autori  :  Matteo Vigano – 760537 – sede CO
           FABIO  Vecaj  – 761232 – sede CO
Versione: 1.0 (A.A. 2024/2025)
Java    : Testato con JDK 21 (funziona da Java 17 in su)
================================================================================


1. REQUISITI
------------
• JDK 17 (o superiore) installato e presente nel PATH  
  Verifica rapida:
      java -version
      javac -version

• Un terminale (cmd / PowerShell / Windows Terminal / bash / zsh ecc.)

• Facoltativo: IDE (IntelliJ IDEA, Eclipse, VS Code…) – il progetto è
  autosufficiente anche da linea di comando.


2. STRUTTURA DEL PROGETTO
-------------------------

TheKnifeProject/
│
├─ src/                     ← sorgenti .java
│   └─ theknife/…
│
├─ bin/                     ← class files compilati (generata dallo script)
│
├─ data/                    ← file CSV iniziali
│   ├─ utenti.csv
│   ├─ ristoranti.csv
│   ├─ recensioni.csv
│   └─ preferiti.csv
│
├─ lib/           
│
├─ TheKnife.jar             ← JAR eseguibile generato
│
└─ README.txt  


3. COMPILAZIONE MANUALE
-----------------------

⚠️ Per convenienza di chi usa Windows, i comandi sono mostrati in **PowerShell**.
   Sostituisci “\” con “/” se sei su macOS/Linux.

3.1 Pulizia ed esportazione class files
---------------------------------------
    # Apri PowerShell nella cartella radice del progetto
    Remove-Item -Recurse -Force .\bin  -ErrorAction Ignore
    New-Item  -ItemType Directory .\bin | Out-Null

    # Compila TUTTI i .java in bin\  (UTF-8 per evitare errori di accenti)
    javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse -Filter *.java | % FullName)

3.2 Creazione di un JAR eseguibile
----------------------------------
    jar cfe TheKnife.jar theknife.TheKnife -C bin .

    Dove:
      • c  = create
      • f  = name of jar file
      • e  = entry-point (Main-Class) ⇒  theknife.TheKnife
      • -C bin .   = “entra” in bin\ e zippa tutto (.) nel JAR


4. ESECUZIONE
-------------

4.0
----------
   Doppio click su run-TheKnife.vbs

4.1 Da JAR
----------
    java -jar TheKnife.jar

4.2 Direttamente da class files
-------------------------------
    java -cp bin theknife.TheKnife
    (o) java -cp bin theknife.gui.MainApp
    Entrambe avviano la GUI.

Nota Windows: se hai doppio-clickato TheKnife.jar non sarà collegato 
ai file csv , pertanto non sarà utilizzabile.
Usare run-TheKnife.vbs che incorpora i file csv.


--------------------------------------------------------------------------------
Buon divertimento con TheKnife!
================================================================================
