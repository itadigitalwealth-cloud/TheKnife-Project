================================================================================
 TheKnife – README
 Guida all'installazione, configurazione e avvio
================================================================================

 Autori:
   Vigano   Matteo       – matricola 760537 – sede CO
   Vecaj    Fabio        – matricola 761232 – sede CO
   De Zuane Samuele      – matricola 763267 – sede CO

 Corso  : Laboratorio Interdisciplinare B
 Java   : JDK 17 LTS o superiore

================================================================================
 INDICE
================================================================================

  1.  Descrizione del progetto
  2.  Requisiti di sistema
  3.  Struttura del repository
  4.  Configurazione database PostgreSQL
  5.  Comandi Maven
  6.  Avvio del server
  7.  Avvio del client
  8.  Dati di test (data.sql)
  9.  Librerie esterne
 10.  Note su particolarità tecniche

================================================================================
 1. DESCRIZIONE DEL PROGETTO
================================================================================

TheKnife e una piattaforma distribuita per la ricerca e gestione di ristoranti,
ispirata a TheFork. E composta da:

  - theknife-server  : backend Java con socket TCP e accesso JDBC a PostgreSQL
  - theknife-client  : GUI Swing con sistema di design personalizzato (UITheme)
  - theknife-common  : modello di dominio e protocollo condivisi tra client e server

L'applicazione implementa un'architettura client/server distribuita con:
  - Comunicazione tramite socket TCP e oggetti Java serializzati
  - Concorrenza gestita con ExecutorService (pool fisso 50 thread)
  - Sessione utente mantenuta lato server per tutta la durata della connessione
  - Cifratura SHA-256 delle password lato client prima della trasmissione

================================================================================
 2. REQUISITI DI SISTEMA
================================================================================

  Componente           Versione minima    Note
  -------------------  -----------------  ------------------------------------
  Java JDK             17 LTS             Verificare con: java -version
  Apache Maven         3.8+               Verificare con: mvn -version
  PostgreSQL           14+                Deve essere in esecuzione prima
                                          di avviare il server
  RAM libera           512 MB             1 GB consigliato
  Sistema operativo    Windows 10 /       Testato su Windows 11 x64
                       macOS 12 /
                       Ubuntu 20.04

  Verifica rapida dei prerequisiti:
    java -version
    javac -version
    mvn -version
    psql --version

================================================================================
 3. STRUTTURA DEL REPOSITORY
================================================================================

  VIGANO_760537/
  |
  +-- pom.xml                        <- POM padre Maven (versioni centralizzate)
  +-- autori.txt                     <- Autori, matricole, sede
  +-- README.txt                     <- Questo file
  |
  +-- bin/
  |   +-- serverTK.jar               <- Fat-JAR eseguibile del server
  |   +-- clientTK.jar               <- Fat-JAR eseguibile del client
  |
  +-- src/
  |   +-- db/
  |       +-- init.sql               <- DDL: crea tabelle e vincoli
  |       +-- data.sql               <- DML: dati di test
  |
  +-- lib/
  |   +-- postgresql-42.7.3.jar      <- Driver JDBC PostgreSQL
  |
  +-- doc/
  |   +-- manuale-utente.pdf         <- Manuale utente (22 pagine)
  |   +-- manuale-tecnico.pdf        <- Manuale tecnico (20 pagine)
  |   +-- diagrammi-ER-UML.docx      <- Schemi ER e diagrammi UML
  |   +-- javadoc/                   <- Documentazione JavaDoc generata
  |
  +-- theknife-common/               <- Modulo comune (modello + protocollo)
  |   +-- pom.xml
  |   +-- src/main/java/it/uninsubria/theknife/common/
  |       +-- CommandType.java
  |       +-- Request.java
  |       +-- Response.java
  |       +-- model/
  |           +-- Ristorante.java
  |           +-- Utente.java
  |           +-- Recensione.java
  |
  +-- theknife-server/               <- Modulo server (backend JDBC)
  |   +-- pom.xml
  |   +-- src/main/java/it/uninsubria/theknife/server/
  |       +-- ServerTK.java
  |       +-- ClientHandler.java
  |       +-- DatabaseManager.java
  |
  +-- theknife-client/               <- Modulo client (GUI Swing)
      +-- pom.xml
      +-- src/main/java/it/uninsubria/theknife/client/
          +-- ClientTK.java
          +-- ServerConnection.java
          +-- gui/
              +-- UITheme.java
              +-- FancyFrame.java
              +-- GradientPanel.java
              +-- LoginDialog.java
              +-- RegisterDialog.java
              +-- panels/
                  +-- HomePanel.java
                  +-- SearchPanel.java
                  +-- RestaurantDetailPanel.java
                  +-- RecensioniPanel.java
                  +-- PreferitiPanel.java
                  +-- RistorantiPanel.java

================================================================================
 4. CONFIGURAZIONE DATABASE POSTGRESQL
================================================================================

  4.1  Creare il database

    Aprire psql o pgAdmin e creare il database:

      CREATE DATABASE theknife;

    Oppure da terminale:

      createdb -U postgres theknife

  4.2  Inizializzare le tabelle

    Eseguire lo script DDL:

      psql -U postgres -d theknife -f src/db/init.sql

    Lo script crea le quattro tabelle con tutti i vincoli:
      - utenti       (PK: username, CHECK ruolo)
      - ristoranti   (PK: nome, FK proprietario, CHECK lat/lon/prezzo)
      - recensioni   (PK composta, FK cascading, CHECK stelle 1..5)
      - preferiti    (PK composta, FK cascading)

  4.3  Caricare i dati di test (facoltativo)

      psql -U postgres -d theknife -f src/db/data.sql

    Questo inserisce:
      - 6 utenti di test (3 clienti, 3 ristoratori)
      - 9 ristoranti distribuiti in varie citta italiane
      - 9 recensioni con valutazioni diverse
      - 9 preferiti

    Password di tutti gli utenti di test: password123
    (gli hash SHA-256 sono gia precalcolati nello script)

    Username di test disponibili:
      Clienti    : mario_r, giulia_v, luca_b
      Ristoratori: riccardo_r, anna_m, marco_f

================================================================================
 5. COMANDI MAVEN
================================================================================

  IMPORTANTE: eseguire tutti i comandi dalla cartella radice VIGANO_760537/
  dove si trova il pom.xml del progetto padre.

  5.1  Compilazione completa e generazione JAR

      mvn clean package

    Output:
      bin/serverTK.jar  <- fat-JAR server (include tutte le dipendenze)
      bin/clientTK.jar  <- fat-JAR client (include tutte le dipendenze)

    In caso di warning "location of system modules":
    E normale con Maven compiler plugin < 3.14 e Java 17+. Non compromette
    la compilazione. Per eliminarlo aggiungere al pom.xml:
      <release>17</release> in <configuration> del compiler plugin.

  5.2  Generazione documentazione JavaDoc

      mvn javadoc:aggregate

    Output: target/site/apidocs/index.html
    Copiare la cartella in doc/javadoc/ prima della consegna:

      Windows:
        xcopy /E /I target\site\apidocs doc\javadoc

      macOS/Linux:
        cp -r target/site/apidocs doc/javadoc

  5.3  Compilare solo un modulo specifico

      mvn -pl theknife-client package
      mvn -pl theknife-server package
      mvn -pl theknife-common package

  5.4  Pulizia dei file compilati

      mvn clean

  5.5  Eseguire i test (se presenti)

      mvn test

================================================================================
 6. AVVIO DEL SERVER
================================================================================

  PREREQUISITO: PostgreSQL deve essere in esecuzione e il database
  inizializzato con init.sql (vedi sezione 4).

  6.1  Da JAR (consigliato)

      java -jar bin\serverTK.jar          <- Windows
      java -jar bin/serverTK.jar          <- macOS/Linux

    Il server avviera una procedura di configurazione interattiva:

      Host database    [default: localhost]  -> premere INVIO per localhost
      Porta database   [default: 5432]       -> premere INVIO per default
      Password DB      [input nascosto]      -> inserire la password PostgreSQL
      Porta server TCP [default: 9090]       -> premere INVIO per 9090

    Al termine appare il messaggio:
      "[SERVER] In ascolto sulla porta 9090"

  6.2  Con parametri via system properties (avvio silenzioso)

      java -Ddb.host=localhost -Ddb.port=5432 -Ddb.pass=TUAPASSWORD \
           -Dserver.port=9090 -jar bin/serverTK.jar

  6.3  Tenere il server in esecuzione

    Il server deve rimanere attivo per tutta la durata dell'utilizzo
    del client. Non chiudere la finestra del terminale del server.

================================================================================
 7. AVVIO DEL CLIENT
================================================================================

  PREREQUISITO: il server deve essere gia in ascolto (vedi sezione 6).

  7.1  Da JAR (consigliato)

      java -jar bin\clientTK.jar          <- Windows
      java -jar bin/clientTK.jar          <- macOS/Linux

    Appare il dialog di connessione:

      Host server   [default: localhost]  -> inserire l'IP del server
      Porta server  [default: 9090]       -> deve corrispondere alla porta server

    Cliccare OK. Se la connessione riesce, si apre la finestra principale.

  7.2  Avvio con risoluzione schermo personalizzata

      java -Dsun.java2d.uiScale=1.0 -jar bin\clientTK.jar

    Utile su schermi ad alta densita (HiDPI / 4K) dove le icone
    appaiono troppo piccole.

  7.3  Avvio rapido su Windows (script)

      Doppio click su run-TheKnife.bat nella cartella radice.
      Lo script avvia server e client nella stessa sessione
      (utile per test locali).

================================================================================
 8. DATI DI TEST (data.sql)
================================================================================

  Utenti disponibili dopo l'esecuzione di data.sql:

  Username        Password      Ruolo        Domicilio
  --------------- ------------- ------------ ---------
  mario_r         password123   cliente      Milano
  giulia_v        password123   cliente      Roma
  luca_b          password123   cliente      Torino
  riccardo_r      password123   ristoratore  Como
  anna_m          password123   ristoratore  Milano
  marco_f         password123   ristoratore  Roma

  Ristoranti di test disponibili (citta: Milano, Roma, Torino, Como):
    - Trattoria da Marco (Italiana, Milano)
    - Sushi Milano Central (Giapponese, Milano)
    - Pizza Napoletana DOC (Italiana, Milano)
    - Osteria Romana (Romana, Roma)
    - Ristorante Subalpino (Piemontese, Torino)
    - Vino Rosso (Italiana, Como)
    + altri 3 ristoranti distribuiti

================================================================================
 9. LIBRERIE ESTERNE
================================================================================

  Libreria              Versione   Posizione          Uso
  --------------------  ---------  -----------------  --------------------------
  postgresql JDBC       42.7.3     lib/               Connessione a PostgreSQL
                                   (incluso nel JAR   Dichiarata nel pom.xml del
                                   con maven-assembly) modulo theknife-server

  Nessuna libreria esterna e necessaria per il CLIENT. La GUI usa
  esclusivamente le API standard Java (Swing, Java2D, java.net.Socket).

  Il driver JDBC e incluso automaticamente nel fat-JAR serverTK.jar
  tramite il plugin maven-assembly-plugin con descriptor jar-with-dependencies.

  Per aggiornare il driver JDBC cambiare la versione nel pom.xml del modulo padre:
    <postgresql.version>42.7.3</postgresql.version>

================================================================================
 10. NOTE SU PARTICOLARITA TECNICHE
================================================================================

  10.1  Architettura client/server distribuita
    Il client e il server comunicano via socket TCP sulla porta configurata
    (default 9090). Possono girare su macchine diverse nella stessa rete.
    Per uso su rete locale: inserire l'IP della macchina server nel dialog
    di connessione del client.

  10.2  Concorrenza
    Il server usa ExecutorService.newFixedThreadPool(50). Ogni connessione
    client ottiene un thread dedicato con la propria connessione JDBC.
    Non c'e sincronizzazione condivisa: ogni ClientHandler e indipendente.

  10.3  Sicurezza password
    La password e hashata SHA-256 lato client (ServerConnection.sha256())
    PRIMA della trasmissione via socket. La password in chiaro non transita
    mai sulla rete e non viene mai memorizzata.

  10.4  Font e rendering grafico
    La GUI usa Java2D puro per icone e stelle. Non dipende da font Unicode
    specifici: funziona correttamente su Windows, macOS e Linux.
    Se le stelle appaiono come quadratini vuoti, verificare che UITheme.java
    sia aggiornato all'ultima versione (v3.0+).

  10.5  Warning Maven "--release vs -source/-target"
    Il warning durante la compilazione e atteso con il profilo di build
    corrente. Non compromette la correttezza dei JAR generati.
    Funzionalita verificata su JDK 17, 21 e 24.

  10.6  Avvio su macOS
    Su macOS potrebbe essere necessario aggiungere il flag:
      -Dapple.awt.application.appearance=system
    Per il tema grafico nativo (opzionale).

  10.7  Porta gia occupata
    Se al momento dell'avvio del server appare "Address already in use",
    cambiare la porta TCP (es. 9091) oppure terminare il processo
    che occupa la porta 9090:
      Windows: netstat -ano | findstr 9090
      Linux:   lsof -i :9090

================================================================================
 Fine README.txt
================================================================================