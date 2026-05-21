-- =============================================================================
-- TheKnife – Script dati di test
-- File   : src/db/data.sql
-- Scopo  : Popolamento del database con dati di esempio per testing e demo.
--          Eseguire DOPO init.sql su un database già inizializzato.
--
-- Autori : Vigano Matteo      – 760537 – sede CO
--          Vecaj Fabio        – 761232 – sede CO
--          De Zuane Samuele   – 763267 – sede CO
--
-- Corso  : Laboratorio Interdisciplinare B – a.a. 2025/2026
--
-- Utilizzo:
--   psql -U postgres -d theknife -f src/db/data.sql
--
-- PASSWORD di tutti gli utenti di test: "password123"
-- Hash SHA-256 preCalcolato:
--   sha256("password123") = ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f
--
-- Utenti di test:
--   Clienti    : mario_r, giulia_v, luca_b
--   Ristoratori: riccardo_r, anna_m, marco_f
-- =============================================================================


-- =============================================================================
-- SEZIONE 1: UTENTI
-- 3 clienti + 3 ristoratori
-- =============================================================================

INSERT INTO utenti (username, nome, cognome, password_hash,
                    data_nascita, domicilio, ruolo)
VALUES
    -- -------------------------------------------------------------------------
    -- CLIENTI
    -- -------------------------------------------------------------------------

    -- Cliente 1: domicilio Milano -> la Home carica automaticamente i ristoranti di Milano
    ('mario_r',
     'Mario', 'Rossi',
     'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f',
     '1990-05-15', 'Milano', 'cliente'),

    -- Cliente 2: domicilio Roma
    ('giulia_v',
     'Giulia', 'Verdi',
     'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f',
     '1995-08-22', 'Roma', 'cliente'),

    -- Cliente 3: domicilio Torino, senza data di nascita (campo opzionale)
    ('luca_b',
     'Luca', 'Bianchi',
     'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f',
     NULL, 'Torino', 'cliente'),

    -- -------------------------------------------------------------------------
    -- RISTORATORI
    -- -------------------------------------------------------------------------

    -- Ristoratore 1: gestisce locali a Milano e Como
    ('riccardo_r',
     'Riccardo', 'Rossi',
     'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f',
     '1975-03-10', 'Milano', 'ristoratore'),

    -- Ristoratore 2: gestisce locali a Milano
    ('anna_m',
     'Anna', 'Manzoni',
     'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f',
     '1982-11-30', 'Milano', 'ristoratore'),

    -- Ristoratore 3: gestisce locali a Roma e Torino
    ('marco_f',
     'Marco', 'Ferrari',
     'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f',
     '1978-07-04', 'Roma', 'ristoratore');


-- =============================================================================
-- SEZIONE 2: RISTORANTI
-- 9 ristoranti in 4 città: Milano (3), Roma (2), Torino (2), Como (2)
-- =============================================================================

INSERT INTO ristoranti (nome, nazione, citta, indirizzo,
                        latitudine, longitudine,
                        fascia_prezzo, delivery, prenotazione,
                        tipo_cucina, proprietario)
VALUES
    -- -------------------------------------------------------------------------
    -- MILANO (3 ristoranti) – proprietario: riccardo_r e anna_m
    -- -------------------------------------------------------------------------

    -- Ristorante top rated di Milano (alta valutazione attesa)
    ('Trattoria da Marco',
     'Italia', 'Milano', 'Via Torino 14, 20123 Milano',
     45.4641,   9.1919,
     35.00, FALSE, TRUE,
     'Italiana', 'riccardo_r'),

    -- Giapponese con delivery
    ('Sushi Milano Central',
     'Italia', 'Milano', 'Corso Buenos Aires 42, 20124 Milano',
     45.4773,   9.2056,
     55.00, TRUE, TRUE,
     'Giapponese', 'anna_m'),

    -- Pizzeria con delivery e prenotazione
    ('Pizza Napoletana DOC',
     'Italia', 'Milano', 'Via Brera 8, 20121 Milano',
     45.4714,   9.1873,
     25.00, TRUE, FALSE,
     'Italiana', 'anna_m'),

    -- -------------------------------------------------------------------------
    -- ROMA (2 ristoranti) – proprietario: marco_f
    -- -------------------------------------------------------------------------

    ('Osteria Romana',
     'Italia', 'Roma', 'Via del Pantheon 55, 00186 Roma',
     41.8986,  12.4769,
     40.00, FALSE, TRUE,
     'Romana', 'marco_f'),

    ('Il Forno di Campo de Fiori',
     'Italia', 'Roma', 'Campo de Fiori 22, 00186 Roma',
     41.8955,  12.4722,
     20.00, FALSE, FALSE,
     'Italiana', 'marco_f'),

    -- -------------------------------------------------------------------------
    -- TORINO (2 ristoranti) – proprietario: marco_f
    -- -------------------------------------------------------------------------

    ('Ristorante Subalpino',
     'Italia', 'Torino', 'Via Po 46, 10123 Torino',
     45.0677,   7.6910,
     60.00, FALSE, TRUE,
     'Piemontese', 'marco_f'),

    ('Pizzeria Torino Centro',
     'Italia', 'Torino', 'Piazza Castello 15, 10122 Torino',
     45.0703,   7.6869,
     22.00, TRUE, FALSE,
     'Italiana', 'marco_f'),

    -- -------------------------------------------------------------------------
    -- COMO (2 ristoranti) – proprietario: riccardo_r
    -- -------------------------------------------------------------------------

    ('Vino Rosso',
     'Italia', 'Como', 'Via Volta 28, 22100 Como',
     45.8100,   9.0852,
     100.00, FALSE, TRUE,
     'Italiana', 'riccardo_r'),

    ('Trattoria del Lago',
     'Italia', 'Como', 'Lungolago Trento 3, 22100 Como',
     45.8078,   9.0822,
     45.00, FALSE, TRUE,
     'Pesce di lago', 'riccardo_r');


-- =============================================================================
-- SEZIONE 3: RECENSIONI
-- 9 recensioni distribuite sui ristoranti
-- Stelle da 1 a 5 (vincolo CHECK garantito da init.sql)
-- =============================================================================

INSERT INTO recensioni (nome_ristorante, username_cliente, stelle, testo, risposta)
VALUES
    -- mario_r recensisce 3 ristoranti
    ('Trattoria da Marco',
     'mario_r', 5,
     'Cucina casalinga eccellente, il risotto alla milanese era perfetto. Ambiente accogliente e servizio attento. Tornerò sicuramente!',
     -- Il ristoratore ha già risposto
     'Grazie Mario! Il risotto è il nostro piatto forte. La aspettiamo presto!'),

    ('Sushi Milano Central',
     'mario_r', 4,
     'Ottimo sushi, pesce freschissimo. Il servizio è stato un po lento ma la qualità compensa.',
     NULL),  -- Il ristoratore non ha ancora risposto

    ('Pizza Napoletana DOC',
     'mario_r', 5,
     'La migliore pizza napoletana che abbia mai mangiato a Milano. Impasto perfetto, pomodoro di qualità.',
     'Grazie mille! Usiamo farina tipo 00 e pomodori San Marzano DOP. A presto!'),

    -- giulia_v recensisce 3 ristoranti
    ('Trattoria da Marco',
     'giulia_v', 4,
     'Ottima cucina, mi ha sorpresa la qualità dei dolci fatti in casa. Tornerò sicuramente.',
     'Grazie Giulia! I dolci li prepariamo ogni mattina. La aspettiamo!'),

    ('Osteria Romana',
     'giulia_v', 4,
     'Ottima pasta cacio e pepe. Il vino della casa era un po deludente ma il cibo compensava abbondantemente.',
     NULL),

    ('Ristorante Subalpino',
     'giulia_v', 5,
     'Il miglior brasato al Barolo che abbia mai assaggiato. Ambiente elegante, servizio impeccabile.',
     'Grazie di cuore! Il brasato è la nostra ricetta di famiglia. A presto!'),

    -- luca_b recensisce 3 ristoranti
    ('Pizza Napoletana DOC',
     'luca_b', 3,
     'Pizza buona ma tempi di attesa molto lunghi. Locale caotico nel weekend. Forse tornerò in settimana.',
     'Ci scusiamo per l attesa! Nel weekend siamo molto richiesti. Proviamo il martedì sera!'),

    ('Vino Rosso',
     'luca_b', 5,
     'Esperienza gastronomica di altissimo livello. Carta dei vini straordinaria, cucina raffinata. Prezzi alti ma ne vale ogni centesimo.',
     'Grazie Luca! Per noi la qualità è tutto. La aspettiamo per la degustazione di autunno!'),

    ('Trattoria del Lago',
     'luca_b', 4,
     'Pesce di lago freschissimo, lavarello alla comasca eccellente. Vista sul lago impagabile. Consigliato per una cena romantica.',
     NULL);


-- =============================================================================
-- SEZIONE 4: PREFERITI
-- 9 preferiti distribuiti tra i 3 clienti
-- =============================================================================

INSERT INTO preferiti (username, nome_ristorante)
VALUES
    -- mario_r ha 3 preferiti
    ('mario_r', 'Trattoria da Marco'),
    ('mario_r', 'Sushi Milano Central'),
    ('mario_r', 'Pizza Napoletana DOC'),

    -- giulia_v ha 3 preferiti
    ('giulia_v', 'Osteria Romana'),
    ('giulia_v', 'Ristorante Subalpino'),
    ('giulia_v', 'Trattoria da Marco'),

    -- luca_b ha 3 preferiti
    ('luca_b', 'Vino Rosso'),
    ('luca_b', 'Trattoria del Lago'),
    ('luca_b', 'Ristorante Subalpino');
