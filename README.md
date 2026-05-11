================================================================================
  TheKnife – Piattaforma di ricerca ristoranti
  Laboratorio Interdisciplinare B – a.a. 2024/2025
  Università degli Studi dell'Insubria
================================================================================

AUTORI
------
  Matteo Vigano  – 760537 – sede CO
  Fabio Vecaj    – 761232 – sede CO

REPOSITORY
----------
  https://github.com/[username]/VIGANO_760537

================================================================================
  PREREQUISITI
================================================================================

  - Java JDK 17 o superiore       https://adoptium.net/
  - Apache Maven 3.8+              https://maven.apache.org/download.cgi
  - PostgreSQL 14+                 https://www.postgresql.org/download/
  - psql (client PostgreSQL, incluso nell'installazione di PostgreSQL)

  Verificare l'installazione:
    java  -version
    mvn   -version
    psql  --version

================================================================================
  STRUTTURA DEL PROGETTO
================================================================================

  VIGANO_760537/
  ├── pom.xml                      Parent POM Maven (multi-modulo)
  ├── theknife-common/             Modello + protocollo condivisi
  │   └── src/main/java/it/uninsubria/theknife/common/
  │       ├── CommandType.java
  │       ├── Request.java
  │       ├── Response.java
  │       └── model/
  │           ├── Ristorante.java
  │           ├── Utente.java
  │           └── Recensione.java
  ├── theknife-server/             Modulo server (JDBC + socket)
  │   └── src/main/java/it/uninsubria/theknife/server/
  │       ├── ServerTK.java        Classe main del server
  │       ├── ClientHandler.java   Thread per ogni client
  │       └── DatabaseManager.java Accesso JDBC a PostgreSQL
  ├── theknife-client/             Modulo client (GUI Swing)
  │   └── src/main/java/it/uninsubria/theknife/client/
  │       ├── ClientTK.java        Classe main del client
  │       ├── ServerConnection.java Gestione socket
  │       └── gui/                 Componenti grafici
  ├── src/
  │   └── db/
  │       └── init.sql             Script SQL di creazione del database
  ├── bin/                         JAR eseguibili (generati da Maven)
  │   ├── serverTK.jar
  │   └── clientTK.jar
  ├── doc/                         Documentazione
  │   ├── manuale-utente.pdf
  │   ├── manuale-tecnico.pdf
  │   └── javadoc/                 JavaDoc generata
  ├── data/                        Dati di esempio (CSV, parte A)
  ├── lib/                         Librerie esterne (gestite da Maven)
  └── autori.txt                   Dati autori

================================================================================
  INSTALLAZIONE E CONFIGURAZIONE
================================================================================

  1. CLONARE IL REPOSITORY
  ------------------------
    git clone https://github.com/[username]/VIGANO_760537.git
    cd VIGANO_760537

  2. CREARE IL DATABASE POSTGRESQL
  ---------------------------------
    Aprire pgAdmin o psql e creare un database vuoto:

      CREATE DATABASE theknife;

    Oppure da riga di comando:
      createdb -U postgres theknife

  3. INIZIALIZZARE IL DATABASE (SCHEMA E TABELLE)
  ------------------------------------------------
    Opzione A – tramite Maven (richiede psql nel PATH):
      mvn -pl theknife-server exec:exec@init-db \
          -Ddb.host=localhost \
          -Ddb.port=5432 \
          -Ddb.name=theknife \
          -Ddb.user=postgres \
          -Ddb.password=TUA_PASSWORD

    Opzione B – tramite psql direttamente:
      psql -U postgres -d theknife -f src/db/init.sql

    Opzione C – tramite pgAdmin:
      Aprire pgAdmin → theknife → Query Tool → aprire src/db/init.sql → F5

================================================================================
  COMPILAZIONE
================================================================================

  Compilare tutti i moduli e produrre i JAR eseguibili:

    mvn clean package

  I file prodotti saranno:
    bin/serverTK.jar   – server con driver JDBC incluso
    bin/clientTK.jar   – client GUI Swing

  Per compilare solo un modulo:
    mvn clean package -pl theknife-server -am
    mvn clean package -pl theknife-client -am

================================================================================
  GENERAZIONE JAVADOC
================================================================================

    mvn javadoc:aggregate

  La documentazione viene generata in:
    target/site/apidocs/

  Per includerla nella cartella doc/:
    xcopy /E /I target\site\apidocs doc\javadoc     (Windows)
    cp -r target/site/apidocs doc/javadoc           (Linux/macOS)

================================================================================
  ESECUZIONE
================================================================================

  ORDINE OBBLIGATORIO: database → server → client

  1. AVVIARE IL SERVER
  --------------------
    java -jar bin/serverTK.jar

    Il server chiede interattivamente:
      Host PostgreSQL  [localhost] :  ← Invio per default
      Porta PostgreSQL [5432]      :  ← Invio per default
      Nome database    [theknife]  :  ← Invio per default
      Username DB      [postgres]  :  ← Invio per default
      Password DB                  :  ← inserire la password
      Porta server     [9090]      :  ← Invio per default

    Una volta avviato:
      [Server] Connessione al database riuscita.
      [Server] In ascolto sulla porta 9090...

    Per arrestare il server: digitare  quit  e premere Invio.

  2. AVVIARE IL CLIENT (in un nuovo terminale)
  --------------------------------------------
    java -jar bin/clientTK.jar

    Appare una finestra che chiede:
      Host server: localhost
      Porta:       9090

    Premere OK per connettersi.

  ESECUZIONE TRAMITE MAVEN (sviluppo):
    mvn -pl theknife-server exec:java     ← avvia il server
    mvn -pl theknife-client exec:java     ← avvia il client

================================================================================
  PARAMETRI DI DEFAULT
================================================================================

  PostgreSQL:        localhost:5432   database: theknife   user: postgres
  Server TheKnife:   localhost:9090
  Thread pool:       max 50 client in parallelo

================================================================================
  LIBRERIE UTILIZZATE
================================================================================

  Gestite automaticamente da Maven (vedi pom.xml):
    - postgresql-42.7.3.jar   Driver JDBC PostgreSQL
      https://jdbc.postgresql.org/
    - junit-jupiter-5.10.2    Test unitari (scope: test)
      https://junit.org/junit5/

  Non sono necessarie installazioni manuali di librerie esterne.
  Maven scarica automaticamente le dipendenze dal repository centrale.

================================================================================
  NOTE TECNICHE
================================================================================

  - La password viene hashata con SHA-256 lato client prima della
    trasmissione. Non viene mai inviata in chiaro sulla rete.

  - Il server gestisce più client in parallelo tramite un thread pool
    fisso (ExecutorService). Ogni client ha una connessione JDBC dedicata
    (pattern: una connessione per thread), eliminando race condition senza
    sincronizzazione esplicita.

  - La comunicazione client/server avviene tramite oggetti Java serializzati
    (ObjectOutputStream/ObjectInputStream) su socket TCP persistenti.

  - Il modulo theknife-common contiene le classi condivise tra client e
    server (modello + protocollo). Il client NON include il driver JDBC.

================================================================================
  COMANDI MAVEN – RIEPILOGO
================================================================================

  mvn clean package                        Compila tutto e genera i JAR
  mvn javadoc:aggregate                    Genera la JavaDoc unificata
  mvn -pl theknife-server exec:java        Avvia il server (dev)
  mvn -pl theknife-client exec:java        Avvia il client (dev)
  mvn -pl theknife-server exec:exec@init-db  Inizializza il DB

================================================================================