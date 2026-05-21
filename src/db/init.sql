-- =============================================================================
-- TheKnife – Script di inizializzazione database
-- File   : src/db/init.sql
-- Scopo  : Creazione di tutte le tabelle con vincoli, indici e chiavi esterne.
--          Eseguire UNA SOLA VOLTA su un database PostgreSQL vuoto.
--
-- Autori : Vigano Matteo      – 760537 – sede CO
--          Vecaj Fabio        – 761232 – sede CO
--          De Zuane Samuele   – 763267 – sede CO
--
-- Corso  : Laboratorio Interdisciplinare B – a.a. 2025/2026
-- DBMS   : PostgreSQL 14+
--
-- Utilizzo:
--   psql -U postgres -d theknife -f src/db/init.sql
--
-- ATTENZIONE: questo script elimina e ricrea tutte le tabelle.
--             Tutti i dati esistenti verranno persi.
-- =============================================================================

-- -----------------------------------------------------------------------------
-- Pulizia (per ricreare da zero senza errori di dipendenza)
-- L'ordine è importante: prima le tabelle figlie, poi le tabelle padre.
-- -----------------------------------------------------------------------------
DROP TABLE IF EXISTS preferiti  CASCADE;
DROP TABLE IF EXISTS recensioni CASCADE;
DROP TABLE IF EXISTS ristoranti CASCADE;
DROP TABLE IF EXISTS utenti     CASCADE;

-- =============================================================================
-- TABELLA: utenti
--
-- Memorizza tutti gli utenti della piattaforma (clienti e ristoratori).
-- La password non viene mai salvata in chiaro: il campo password_hash
-- contiene l'hash SHA-256 calcolato lato client prima della trasmissione.
-- =============================================================================
CREATE TABLE utenti (

    -- Identificatore univoco scelto dall'utente al momento della registrazione.
    -- Non modificabile dopo la creazione.
    username        VARCHAR(50)   NOT NULL,

    nome            VARCHAR(50)   NOT NULL,
    cognome         VARCHAR(50)   NOT NULL,

    -- Hash SHA-256 della password (64 caratteri esadecimali).
    -- La password in chiaro non transita mai sulla rete.
    password_hash   CHAR(64)      NOT NULL,

    -- Data di nascita facoltativa (il client può lasciare il campo vuoto).
    data_nascita    DATE,

    -- Città di domicilio dell'utente. Usata dalla Home per caricare
    -- automaticamente i ristoranti nella città dell'utente loggato.
    domicilio       VARCHAR(100),

    -- Ruolo dell'utente nella piattaforma.
    -- 'cliente'     -> può cercare ristoranti, scrivere recensioni, gestire preferiti.
    -- 'ristoratore' -> può aggiungere ristoranti e rispondere alle recensioni.
    -- I due ruoli sono mutuamente esclusivi.
    ruolo           VARCHAR(20)   NOT NULL
                    CONSTRAINT utenti_ruolo_check
                    CHECK (ruolo IN ('cliente', 'ristoratore')),

    CONSTRAINT utenti_pk PRIMARY KEY (username)
);

-- Indice sulla città di domicilio per la query di caricamento automatico Home.
CREATE INDEX idx_utenti_domicilio ON utenti (LOWER(domicilio));


-- =============================================================================
-- TABELLA: ristoranti
--
-- Memorizza tutti i ristoranti registrati sulla piattaforma.
-- Il campo 'proprietario' è la chiave esterna verso il ristoratore
-- che ha creato il locale.
-- =============================================================================
CREATE TABLE ristoranti (

    -- Nome del ristorante: identificatore univoco sulla piattaforma.
    nome            VARCHAR(100)  NOT NULL,

    nazione         VARCHAR(50)   NOT NULL,
    citta           VARCHAR(100)  NOT NULL,

    -- Indirizzo fisico del locale (opzionale).
    indirizzo       VARCHAR(200),

    -- Coordinate geografiche. Usate per future estensioni (mappa).
    latitudine      FLOAT
                    CONSTRAINT ristoranti_lat_check
                    CHECK (latitudine  BETWEEN -90  AND 90),

    longitudine     FLOAT
                    CONSTRAINT ristoranti_lon_check
                    CHECK (longitudine BETWEEN -180 AND 180),

    -- Prezzo medio per persona in euro. Deve essere un valore positivo.
    fascia_prezzo   NUMERIC(8, 2) NOT NULL
                    CONSTRAINT ristoranti_prezzo_check
                    CHECK (fascia_prezzo > 0),

    -- Servizi disponibili. FALSE di default se il ristoratore non li specifica.
    delivery        BOOLEAN       NOT NULL DEFAULT FALSE,
    prenotazione    BOOLEAN       NOT NULL DEFAULT FALSE,

    tipo_cucina     VARCHAR(100)  NOT NULL,

    -- Username del ristoratore proprietario del locale.
    -- ON DELETE CASCADE: se il ristoratore viene eliminato, vengono
    -- eliminati automaticamente anche tutti i suoi ristoranti.
    proprietario    VARCHAR(50)   NOT NULL
                    REFERENCES utenti (username)
                    ON DELETE CASCADE,

    CONSTRAINT ristoranti_pk PRIMARY KEY (nome)
);

-- Indice sulla città per ottimizzare la ricerca (query più frequente).
CREATE INDEX idx_ristoranti_citta        ON ristoranti (LOWER(citta));
-- Indice sul tipo di cucina per il filtro di ricerca avanzata.
CREATE INDEX idx_ristoranti_tipo_cucina  ON ristoranti (LOWER(tipo_cucina));
-- Indice sul proprietario per il pannello "Miei locali" del ristoratore.
CREATE INDEX idx_ristoranti_proprietario ON ristoranti (proprietario);


-- =============================================================================
-- TABELLA: recensioni
--
-- Memorizza le recensioni degli utenti sui ristoranti.
-- La chiave primaria è COMPOSTA da (nome_ristorante, username_cliente):
-- ogni cliente può scrivere al massimo una recensione per ristorante.
--
-- Il campo 'risposta' è opzionale (NULL se il ristoratore non ha ancora
-- risposto). Il vincolo "max una risposta per recensione" è garantito
-- dalla struttura stessa: la risposta è un attributo della recensione,
-- non una riga separata.
-- =============================================================================
CREATE TABLE recensioni (

    -- Chiave esterna verso il ristorante recensito.
    -- ON DELETE CASCADE: se il ristorante viene eliminato, vengono
    -- eliminate anche tutte le sue recensioni.
    nome_ristorante  VARCHAR(100)  NOT NULL
                     REFERENCES ristoranti (nome)
                     ON DELETE CASCADE,

    -- Chiave esterna verso il cliente autore della recensione.
    -- ON DELETE CASCADE: se il cliente viene eliminato, vengono
    -- eliminate anche tutte le sue recensioni.
    username_cliente VARCHAR(50)   NOT NULL
                     REFERENCES utenti (username)
                     ON DELETE CASCADE,

    -- Valutazione da 1 a 5 stelle. Il vincolo CHECK garantisce
    -- l'integrità a livello di database, indipendentemente dall'applicazione.
    stelle           SMALLINT      NOT NULL
                     CONSTRAINT recensioni_stelle_check
                     CHECK (stelle BETWEEN 1 AND 5),

    -- Testo della recensione. Non può essere vuoto.
    testo            TEXT          NOT NULL,

    -- Risposta del ristoratore. NULL se non ancora risposto.
    -- Se il ristoratore risponde una seconda volta, il valore viene
    -- sovrascritto con UPDATE (non inserita una nuova riga).
    risposta         TEXT,

    -- La PK composta garantisce l'unicità: un cliente non può
    -- lasciare due recensioni per lo stesso ristorante.
    CONSTRAINT recensioni_pk PRIMARY KEY (nome_ristorante, username_cliente)
);

-- Indice sul cliente per il pannello "Le mie recensioni".
CREATE INDEX idx_recensioni_cliente ON recensioni (username_cliente);


-- =============================================================================
-- TABELLA: preferiti
--
-- Tabella di raccordo N:M tra utenti (clienti) e ristoranti.
-- Non ha attributi propri: la coppia (username, nome_ristorante)
-- identifica univocamente ogni preferito.
-- =============================================================================
CREATE TABLE preferiti (

    -- Chiave esterna verso il cliente.
    username         VARCHAR(50)   NOT NULL
                     REFERENCES utenti (username)
                     ON DELETE CASCADE,

    -- Chiave esterna verso il ristorante salvato.
    nome_ristorante  VARCHAR(100)  NOT NULL
                     REFERENCES ristoranti (nome)
                     ON DELETE CASCADE,

    -- La PK composta impedisce di aggiungere lo stesso ristorante
    -- due volte alla lista preferiti dello stesso utente.
    CONSTRAINT preferiti_pk PRIMARY KEY (username, nome_ristorante)
);
