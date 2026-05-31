-- =============================================================================
-- TheKnife – Script dati di test
-- File   : src/db/data.sql
-- Scopo  : Popolamento del database con tutti i ristoranti italiani
--          della Guida Michelin (fonte: michelin_my_maps.csv – Kaggle 2021).
--          Eseguire DOPO init.sql su un database già inizializzato.
--
-- Autori : Vigano Matteo      – 760537 – sede CO
--          Vecaj Fabio        – 761232 – sede CO
--          De Zuane Samuele   – 763267 – sede CO
--
-- Corso  : Laboratorio Interdisciplinare B – a.a. 2025/2026
--
-- PASSWORD di tutti gli utenti di test: "password123"
-- Hash SHA-256 precalcolato:
--   sha256("password123") = ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f
--
-- Ristoranti italiani inseriti: 1980
--   Fonte: Michelin Guide Restaurant Dataset 2021 – kaggle.com/ngshiheng
--   I nomi duplicati tra città diverse sono disambiguati con (Città).
--   Proprietario assegnato ciclicamente tra i 3 ristoratori di test.
-- =============================================================================


-- =============================================================================
-- SEZIONE 1: UTENTI  (3 clienti + 3 ristoratori)
-- =============================================================================

INSERT INTO utenti (username, nome, cognome, password_hash,
                    data_nascita, domicilio, ruolo)
VALUES
    ('mario_r',   'Mario',    'Rossi',    'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', '1990-05-15', 'Milano',  'cliente'),
    ('giulia_v',  'Giulia',   'Verdi',    'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', '1995-08-22', 'Roma',    'cliente'),
    ('luca_b',    'Luca',     'Bianchi',  'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', NULL,         'Torino',  'cliente'),
    ('riccardo_r','Riccardo', 'Rossi',    'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', '1975-03-10', 'Milano',  'ristoratore'),
    ('anna_m',    'Anna',     'Manzoni',  'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', '1982-11-30', 'Milano',  'ristoratore'),
    ('marco_f',   'Marco',    'Ferrari',  'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', '1978-07-04', 'Roma',    'ristoratore');


-- =============================================================================
-- SEZIONE 2: RISTORANTI  (1980 ristoranti Michelin italiani)
-- =============================================================================

INSERT INTO ristoranti (nome, nazione, citta, indirizzo,
                        latitudine, longitudine,
                        fascia_prezzo, delivery, prenotazione,
                        tipo_cucina, proprietario)
VALUES
    -- 3 Stars | €€€€
    ('Casa Perbellini 12 Apostoli', 'Italia', 'Verona', 'vicolo Corticella San Marco 3',
     45.4426941, 10.9961161,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 3 Stars | €€€€
    ('Le Calandre', 'Italia', 'Rubano', 'via Liguria 1',
     45.4215425, 11.8096633,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 3 Stars | €€€€
    ('Enrico Bartolini al Mudec', 'Italia', 'Milano', 'via Tortona 56',
     45.4516, 9.161865,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 3 Stars | €€€€
    ('Uliassi', 'Italia', 'Senigallia', 'banchina di Levante 6',
     43.7194609, 13.2206503,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 3 Stars | €€€€
    ('Enoteca Pinchiorri', 'Italia', 'Firenze', 'via Ghibellina 87',
     43.770066, 11.2621974,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 3 Stars | €€€€
    ('Atelier Moessmer Norbert Niederkofler', 'Italia', 'Brunico', 'via Walther Von der Vogelweide 17',
     46.7927709, 11.949283,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 3 Stars | €€€€
    ('Villa Crespi', 'Italia', 'Orta San Giulio', 'via Fava 18',
     45.7964778, 8.4159486,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 3 Stars | €€€€
    ('Quattro Passi', 'Italia', 'Marina del Cantone', 'via Vespucci 13/n',
     40.5857091, 14.3537561,
     160.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- 3 Stars | €€€€
    ('La Pergola', 'Italia', 'Roma', 'via Cadlolo 101',
     41.9187946, 12.4461151,
     160.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- 3 Stars | €€€€
    ('Reale', 'Italia', 'Castel di Sangro', 'contrada Santa Liberata',
     41.7800349, 14.094554,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 3 Stars | €€€€
    ('Da Vittorio', 'Italia', 'Brusaporto', 'via Cantalupa 17',
     45.6756778, 9.7692511,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 3 Stars | €€€€
    ('Osteria Francescana', 'Italia', 'Modena', 'via Stella 22',
     44.6448099, 10.9215518,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 3 Stars | €€€€
    ('Dal Pescatore', 'Italia', 'Runate', 'località Runate 15',
     45.1712728, 10.3565788,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 3 Stars | €€€€
    ('Piazza Duomo', 'Italia', 'Alba', 'vicolo dell''Arco 1',
     44.7005555, 8.0359341,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 2 Stars | €€€€
    ('Arnolfo', 'Italia', 'Colle di Val d''Elsa', 'viale della Rimembranza 24',
     43.4221, 11.1165,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 2 Stars | €€€€
    ('Duomo (Ragusa)', 'Italia', 'Ragusa', 'via Cap. Bocchieri 31',
     36.9267762, 14.7408182,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('Harry''s Piccolo', 'Italia', 'Trieste', 'piazza Unità d''Italia 2',
     45.6497242, 13.7675069,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 2 Stars | €€€€
    ('Torre del Saracino', 'Italia', 'Vico Equense', 'via Torretta 9',
     40.6606947, 14.419559,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 2 Stars | €€€€
    ('St. George by Heinz Beck', 'Italia', 'Taormina', 'viale San Pancrazio 46',
     37.8554565, 15.2900353,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('Agli Amici', 'Italia', 'Godia', 'via Liguria 252',
     46.1021988, 13.2661162,
     160.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 2 Stars | €€€€
    ('La Rei Natura by Michelangelo Mammoliti', 'Italia', 'Serralunga d''Alba', 'via Roddino 21',
     44.5942745, 8.0002065,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 2 Stars | €€€€
    ('Caino', 'Italia', 'Montemerano', 'via della Chiesa 4',
     42.6220221, 11.4906283,
     160.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('Seta by Antonio Guida', 'Italia', 'Milano', 'via Andegari 9',
     45.4694121, 9.1910082,
     160.0, FALSE, FALSE,
     'Internazionale', 'anna_m'),
    -- 2 Stars | €€€€
    ('La Madia (Licata)', 'Italia', 'Licata', 'corso Filippo Re Capriata 22',
     37.1070131, 13.9338055,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 2 Stars | €€€€
    ('Madonnina del Pescatore', 'Italia', 'Marzocca', 'via Lungomare Italia 11',
     43.6806944, 13.2821211,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('Campo del Drago', 'Italia', 'Montalcino', 'Località Castiglion del Bosco',
     43.08386, 11.422479,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 2 Stars | €€€€
    ('Castel fine dining', 'Italia', 'Tirol', 'vicolo dei Castagni 18',
     46.6862223, 11.1568653,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 2 Stars | €€€€
    ('Villa Feltrinelli', 'Italia', 'Gargnano', 'via Rimembranza 38/40',
     45.693253, 10.669477,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('Il Pagliaccio', 'Italia', 'Roma', 'via dei Banchi Vecchi 129/a',
     41.89793, 12.4673729,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 2 Stars | €€€€
    ('Locanda Sant''Uffizio Enrico Bartolini', 'Italia', 'Cioccaro', 'strada Sant''Uffizio 1',
     45.0271679, 8.2778294,
     160.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- 2 Stars | €€€€
    ('Santa Elisabetta', 'Italia', 'Firenze', 'piazza Santa Elisabetta 3',
     43.7717322, 11.2558883,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('San Domenico (Imola)', 'Italia', 'Imola', 'via Sacchi 1',
     44.3557172, 11.7124431,
     160.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- 2 Stars | €€€€
    ('La Peca', 'Italia', 'Lonigo', 'via Alberto Giovanelli 2',
     45.3848934, 11.3990641,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 2 Stars | €€€€
    ('daní maison', 'Italia', 'Ischia', 'via Montetignuso 4',
     40.7302578, 13.9487527,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('Terra The Magic Place', 'Italia', 'Sarentino', 'località Prati 21',
     46.6521107, 11.3221975,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 2 Stars | €€€€
    ('Enoteca La Torre', 'Italia', 'Roma', 'lungotevere delle Armi 22/23',
     41.916491, 12.4693268,
     160.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- 2 Stars | €€€€
    ('Taverna Estia', 'Italia', 'Brusciano', 'via Guido De Ruggiero 108',
     40.9083323, 14.4232938,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('Krèsios', 'Italia', 'Telese', 'via San Giovanni 59',
     41.2045624, 14.5000207,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 2 Stars | €€€€
    ('Il Piccolo Principe', 'Italia', 'Viareggio', 'piazza Giacomo Puccini 1',
     43.8809756, 10.2355866,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 2 Stars | €€€€
    ('Verso Capitaneo', 'Italia', 'Milano', 'piazza del Duomo 21',
     45.4648103, 9.189585,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('Antica Osteria Cera', 'Italia', 'Lughetto', 'via Marghera 24',
     45.3881476, 12.1302222,
     160.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- 2 Stars | €€€€
    ('Antica Corona Reale', 'Italia', 'Cervere', 'via Fossano 13',
     44.63505, 7.788635,
     160.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- 2 Stars | €€€€
    ('Glam Enrico Bartolini', 'Italia', 'Venezia', 'calle Tron',
     45.4413057, 12.3298209,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('Andrea Aprea', 'Italia', 'Milano', 'corso Venezia 52',
     45.4734602, 9.2042705,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 2 Stars | €€€€
    ('Miramonti l''Altro', 'Italia', 'Concesio', 'via Crosette 34',
     45.6135819, 10.2043986,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 2 Stars | €€€€
    ('Acquolina', 'Italia', 'Roma', 'via del Vantaggio 14',
     41.9086474, 12.4753719,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('D''O', 'Italia', 'Cornaredo', 'piazza della Chiesa 14',
     45.48711, 9.01175,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 2 Stars | €€€€
    ('L''Olivo', 'Italia', 'Anacapri', 'via Capodimonte 14',
     40.5559154, 14.2220085,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 2 Stars | €€€€
    ('Magnolia', 'Italia', 'Longiano', 'via Pelliciano 35',
     44.0731058, 12.3498464,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 2 Stars | €€€€
    ('Piazzetta Milù', 'Italia', 'Castellammare di Stabia', 'corso Alcide De Gasperi 23',
     40.7018717, 14.4829218,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 2 Stars | €€€€
    ('Villa Elena', 'Italia', 'Bergamo', 'via San Vigilio 56',
     45.7105439, 9.648062,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 2 Stars | €€€€
    ('George Restaurant', 'Italia', 'Napoli', 'corso Vittorio Emanuele 135',
     40.8372677, 14.2300686,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('O Me O Il Mare', 'Italia', 'Gragnano', 'Via Roma 45/47',
     40.6916293, 14.5157334,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€
    ('La Buca', 'Italia', 'Cesenatico', 'corso Garibaldi 45',
     44.200314, 12.396823,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- 1 Star | €€€€
    ('La Trattoria Enrico Bartolini', 'Italia', 'Castiglione della Pescaia', 'Località Badiola',
     42.78823, 10.97122,
     160.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- 1 Star | €€€
    ('LoRo', 'Italia', 'Trescore Balneario', 'via Bruse 2',
     45.6938611, 9.8324198,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Serrae Villa Fiesole', 'Italia', 'Fiesole', 'Via Fra'' Giovanni da Fiesole detto l''Angelico 35',
     43.80197, 11.2980133,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Casin del Gamba', 'Italia', 'Altissimo', 'via Roccolo Pizzati 1',
     45.6178, 11.2615,
     160.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Palais Royal Restaurant', 'Italia', 'Venezia', 'Calle Larga 22 Marzo 2032',
     45.4329, 12.33462,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Kitchen', 'Italia', 'Como', 'via per Cernobbio 41/a',
     45.8334043, 9.0715676,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('Ancòra', 'Italia', 'Cesenatico', 'viale Trento 31',
     44.1955658, 12.4042192,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Abbruzzino Oltre', 'Italia', 'Lamezia Terme', 'Piazza Salvo d''Acquisto 16',
     38.9770969, 16.3202202,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€
    ('Abbruzzino', 'Italia', 'Catanzaro', 'via Fiume Savuto',
     38.8901852, 16.6236902,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€
    ('San Giorgio', 'Italia', 'Genova', 'viale Brigate Bisagno 69r',
     44.4016634, 8.945731,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€
    ('Dalla Gioconda', 'Italia', 'Gabicce Monte', 'via dell''Orizzonte 2',
     43.963886, 12.7725315,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€€
    ('Cannavacciuolo Le Cattedrali Asti', 'Italia', 'Asti', 'frazione Valleandona 1/b',
     44.9078373, 8.1173043,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Pascucci al Porticciolo', 'Italia', 'Fiumicino', 'viale Traiano 85',
     41.770165, 12.2271443,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- 1 Star | €€€
    ('Zur Rose', 'Italia', 'San Michele', 'via Josef Innerhofer 2',
     46.455093, 11.258857,
     85.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- 1 Star | €€€
    ('Votavota', 'Italia', 'Marina di Ragusa', 'lungomare Andrea Doria 48',
     36.7826492, 14.5571721,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€
    ('La Ciau del Tornavento', 'Italia', 'Treiso', 'piazza Leopoldo Baracco 7',
     44.68925, 8.08667,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Johannesstube', 'Italia', 'Nova Levante', 'via San Valentino 3',
     46.4296498, 11.5334034,
     160.0, FALSE, FALSE,
     'Alpine', 'anna_m'),
    -- 1 Star | €€€€
    ('Al Sorriso', 'Italia', 'Soriso', 'via Roma 18',
     45.7406906, 8.4116747,
     160.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- 1 Star | €€
    ('Il Papavero', 'Italia', 'Eboli', 'corso Garibaldi 112/113',
     40.6190612, 15.0539834,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€
    ('Locanda San Lorenzo', 'Italia', 'Puos d''Alpago', 'via IV Novembre 79',
     46.1395908, 12.363875,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Zash', 'Italia', 'Archi', 'SP 2 I/II 60',
     37.7079174, 15.1998992,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Del Cambio', 'Italia', 'Torino', 'piazza Carignano 2',
     45.0695181, 7.6848806,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Balzi Rossi', 'Italia', 'Ventimiglia', 'via Balzi Rossi 2',
     43.78393, 7.533239,
     160.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€€
    ('Primo Restaurant', 'Italia', 'Lecce', 'via 47° Reggimento Fanteria 7',
     40.3526, 18.177374,
     160.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- 1 Star | €€€€
    ('Bluh Furore', 'Italia', 'Furore', 'via Dell''Amore 2',
     40.6152174, 14.5423636,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Castello di Fighine', 'Italia', 'San Casciano dei Bagni', 'borgo di Fighine',
     42.88651, 11.915653,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Il Luogo Aimo e Nadia', 'Italia', 'Milano', 'via Montecuccoli 6',
     45.458435, 9.130776,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('Dolada', 'Italia', 'Pieve d''Alpago', 'via Dolada 21',
     46.1736379, 12.3601001,
     85.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- 1 Star | €€€
    ('Achilli al Parlamento', 'Italia', 'Roma', 'via dei Prefetti 15',
     41.9024619, 12.4769426,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€€
    ('La Magnolia', 'Italia', 'Forte dei Marmi', 'viale Morin 46',
     43.9497169, 10.1774953,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€
    ('Andreina', 'Italia', 'Loreto', 'via Zona Industriale Brodolini',
     43.4404449, 13.6208829,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Laite', 'Italia', 'Sappada', 'borgata Hoffe 10',
     46.5685736, 12.6948157,
     160.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- 1 Star | €€€
    ('Angelo Sabatelli', 'Italia', 'Putignano', 'via Santa Chiara 1',
     40.8500527, 17.1227135,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€€
    ('Il Pievano', 'Italia', 'Gaiole in Chianti', 'Località Spaltenna 13',
     43.4654463, 11.4293865,
     160.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- 1 Star | €€€€
    ('La Locanda del Borgo', 'Italia', 'Telese', 'località Monte Pugliano 1',
     41.2259613, 14.5341855,
     160.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€€
    ('Tre Olivi', 'Italia', 'Paestum', 'via Poseidonia 41',
     40.42511, 14.98659,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Osteria Arbustico', 'Italia', 'Paestum', 'via Francesco Gregorio 40',
     40.451699, 14.9758186,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€
    ('San Martino (Scorzè)', 'Italia', 'Scorzè', 'piazza Cappelletto 1',
     45.5892243, 12.110501,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Il Buco', 'Italia', 'Sorrento', 'II rampa Marina Piccola 5',
     40.6272425, 14.3750106,
     160.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- 1 Star | €€€€
    ('Terrazza Bosquet', 'Italia', 'Sorrento', 'piazza Tasso 34',
     40.6284812, 14.3758036,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Vespasia', 'Italia', 'Norcia', 'via Cesare Battisti 10',
     42.7925634, 13.0920268,
     160.0, FALSE, FALSE,
     'Umbrian', 'anna_m'),
    -- 1 Star | €€€
    ('Atelier', 'Italia', 'Domodossola', 'piazza Matteotti 36',
     46.1149939, 8.2957587,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€€
    ('Locanda Don Serafino', 'Italia', 'Ragusa', 'via Avv. Ottaviano 13',
     36.9245977, 14.7407767,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€
    ('Locanda Mammì', 'Italia', 'Agnone', 'contrada Castelnuovo 86',
     41.8299804, 14.3839118,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Tilia', 'Italia', 'Toblach', 'via Dolomiti 31/b',
     46.7236326, 12.2241114,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€€
    ('La Sala dei Grappoli', 'Italia', 'Montalcino', 'Castello di Poggio alle Mura',
     42.980553, 11.399909,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€
    ('Guidoristorante', 'Italia', 'Serralunga d''Alba', 'via Alba 15',
     44.6414, 7.98265,
     85.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- 1 Star | €€€€
    ('Prezioso', 'Italia', 'Merano', 'via Fragsburg 3',
     46.642062, 11.1910522,
     160.0, FALSE, FALSE,
     'Alpine', 'marco_f'),
    -- 1 Star | €€€€
    ('Borgo San Jacopo', 'Italia', 'Firenze', 'borgo San Jacopo 62 r',
     43.7680838, 11.251472,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€
    ('Il Tiglio', 'Italia', 'Montemonaco', 'via Isola San Biagio 34',
     42.9182376, 13.316814,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Due Camini', 'Italia', 'Savelletri', 'Strada Comunale Egnazia',
     40.8760649, 17.396077,
     160.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- 1 Star | €€€
    ('Veritas', 'Italia', 'Napoli', 'corso Vittorio Emanuele 141',
     40.8372277, 14.2311901,
     85.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- 1 Star | €€€
    ('Shalai', 'Italia', 'Linguaglossa', 'via Guglielmo Marconi 25',
     37.8433039, 15.1419716,
     85.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- 1 Star | €€€
    ('Il Fagiano', 'Italia', 'Fasano del Garda', 'corso Zanardelli 190',
     45.622704, 10.570339,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('Il Cantinone e Sport Hotel Alpina', 'Italia', 'Madesimo', 'via A. De Giacomi 39',
     46.44052, 9.35916,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Maeba Restaurant', 'Italia', 'Ariano Irpino', 'contrada Serra 29',
     41.1211871, 15.0655007,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('L''Argine a Vencò', 'Italia', 'Dolegna del Collio', 'Località Vencò 15',
     46.0025426, 13.4631925,
     160.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€€
    ('Romano', 'Italia', 'Viareggio', 'via Mazzini 120',
     43.8722767, 10.2482771,
     160.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- 1 Star | €€€
    ('Capriccio (Manerba del Garda)', 'Italia', 'Manerba del Garda', 'piazza San Bernardo 6',
     45.55285, 10.56121,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Il Cappero', 'Italia', 'Isola Vulcano', 'via Vulcanello',
     38.4308153, 14.9570928,
     160.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- 1 Star | €€€€
    ('La Tana Gourmet', 'Italia', 'Asiago', 'via Kaberlaba 19',
     45.85289, 11.49528,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€
    ('Sissi', 'Italia', 'Merano', 'via Galilei 44',
     46.6738587, 11.1608085,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Ca'' Matilde', 'Italia', 'Rubbianino', 'via Polita 14',
     44.652336, 10.52569,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('21.9', 'Italia', 'Piobesi d''Alba', 'Località Carretta 4',
     44.7326422, 7.9903827,
     85.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- 1 Star | €€€
    ('Il Patio', 'Italia', 'Pollone', 'via Oremo 14',
     45.5820817, 8.004503,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€
    ('Zappatori', 'Italia', 'Pinerolo', 'corso Torino 34',
     44.8847909, 7.3320196,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€€
    ('Antica Osteria Nonna Rosa', 'Italia', 'Vico Equense', 'via Laudano 1',
     40.66156, 14.43397,
     160.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- 1 Star | €€€€
    ('La Trota', 'Italia', 'Rivodutri', 'via Santa Susanna 33',
     42.4982957, 12.8486278,
     160.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€€
    ('Joia', 'Italia', 'Milano', 'via Panfilo Castaldi 18',
     45.4772538, 9.2016981,
     160.0, FALSE, FALSE,
     'Vegetariana', 'marco_f'),
    -- 1 Star | €€€€
    ('Acquerello', 'Italia', 'Fagnano Olona', 'via Patrioti 5',
     45.6691814, 8.8709213,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Aroma', 'Italia', 'Roma', 'via Labicana 125',
     41.8903751, 12.4948543,
     160.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Saporium Firenze', 'Italia', 'Firenze', 'lungarno Benvenuto Cellini 63r',
     43.7646, 11.2659859,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Dolomieu', 'Italia', 'Madonna di Campiglio', 'via Castelletto Inferiore 10',
     46.223693, 10.8257548,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Re Maurì', 'Italia', 'Salerno', 'via Benedetto Croce',
     40.6720876, 14.734819,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€€
    ('Vitantonio Lombardo', 'Italia', 'Matera', 'via Madonna delle Virtù 13/14',
     40.6671525, 16.6130384,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Contraste', 'Italia', 'Milano', 'via Giuseppe Meda 2',
     45.446095, 9.17929,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€
    ('Sarri', 'Italia', 'Imperia', 'lungomare C. Colombo 108',
     43.87008, 8.0059,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- 1 Star | €€€€
    ('Signum', 'Italia', 'Malfa', 'via Scalo',
     38.57992, 14.83344,
     160.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- 1 Star | €€€
    ('Sud', 'Italia', 'Quarto', 'via Santi Pietro e Paolo 8',
     40.8748426, 14.1234047,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€
    ('Crocifisso', 'Italia', 'Noto', 'via Principe Umberto 46',
     36.8944278, 15.0713855,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€
    ('Nostrano', 'Italia', 'Pesaro', 'piazzale della Libertà 7',
     43.9136749, 12.9193802,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€€
    ('Le Trabe', 'Italia', 'Paestum', 'via Capo di Fiume 4',
     40.4469187, 15.0429487,
     160.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- 1 Star | €€€
    ('La Capinera', 'Italia', 'Taormina', 'via Nazionale 177',
     37.863136, 15.295927,
     85.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- 1 Star | €€€€
    ('Iacobucci', 'Italia', 'Castel Maggiore', 'via Ronco 1',
     44.5602196, 11.3538901,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Carignano', 'Italia', 'Torino', 'via Carlo Alberto 35',
     45.065155, 7.6840119,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Gourmetstube Einhorn', 'Italia', 'Mules', 'Campo di Trens',
     46.8511074, 11.5204481,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€€
    ('La Torre (Tavarnelle Val di Pesa)', 'Italia', 'Tavarnelle Val di Pesa', 'strada Spicciano 7',
     43.5529904, 11.1878246,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Relais Blu', 'Italia', 'Massa Lubrense', 'via Roncato 60',
     40.58996, 14.3303537,
     85.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Anna Stuben', 'Italia', 'Ortisei', 'via Vidalong 3',
     46.5723078, 11.6732881,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Moma', 'Italia', 'Roma', 'via di San Basilio 42',
     41.90569, 12.4909,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Lanterna Verde', 'Italia', 'Villa di Chiavenna', 'frazione San Barnaba 7',
     46.3284, 9.49741,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Magorabin', 'Italia', 'Torino', 'corso San Maurizio 61/d',
     45.0684649, 7.6960435,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Condividere', 'Italia', 'Torino', 'via Bologna 20/a',
     45.0802655, 7.6911056,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Stube Hermitage', 'Italia', 'Madonna di Campiglio', 'via Castelletto Inferiore 69',
     46.2131692, 10.8244764,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Il Ristorante di Guido da Costigliole', 'Italia', 'Santo Stefano Belbo', 'località San Maurizio 39',
     44.7051429, 8.2066746,
     160.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- 1 Star | €€€€
    ('All''Oro', 'Italia', 'Roma', 'via Giuseppe Pisanelli 25',
     41.9140073, 12.4730035,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Berton', 'Italia', 'Milano', 'via Mike Bongiorno 13',
     45.4821805, 9.1944159,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Villa Maiella', 'Italia', 'Guardiagrele', 'via Sette Dolori 30',
     42.188822, 14.2047054,
     85.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'anna_m'),
    -- 1 Star | €€€€
    ('Esplanade', 'Italia', 'Desenzano del Garda', 'via Lario 3',
     45.4658732, 10.5498607,
     160.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- 1 Star | €€€
    ('Cucina Cereda', 'Italia', 'Ponte San Pietro', 'via Piazzini 33',
     45.698355, 9.5909095,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Lido 84', 'Italia', 'Fasano del Garda', 'corso Zanardelli 196',
     45.6251787, 10.5755286,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Vintage 1997', 'Italia', 'Torino', 'piazza Solferino 16/h',
     45.068485, 7.676379,
     85.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- 1 Star | €€€€
    ('Locanda Margon', 'Italia', 'Ravina', 'via Margone 15',
     46.0368038, 11.1066465,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€
    ('I Castagni', 'Italia', 'Vigevano', 'via Ottobiano 8/20',
     45.2927681, 8.8577576,
     85.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- 1 Star | €€€
    ('President', 'Italia', 'Pompei', 'piazza Schettini 12/13',
     40.74731, 14.49951,
     85.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- 1 Star | €€€€
    ('Cracco in Galleria', 'Italia', 'Milano', 'Galleria Vittorio Emanuele II',
     45.46535, 9.189977,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Alpenroyal Gourmet', 'Italia', 'Selva di Val Gardena', 'via Meisules 43',
     46.5588796, 11.7463421,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Zum Löwen', 'Italia', 'Tisens', 'via Tirolo 25',
     46.5635107, 11.1694582,
     85.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- 1 Star | €€€
    ('Schöneck', 'Italia', 'Molini', 'via Schloss Schöneck 11',
     46.8186079, 11.8482868,
     85.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- 1 Star | €€€
    ('Gellivs', 'Italia', 'Oderzo', 'calle Pretoria 6',
     45.7828798, 12.4946107,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Cannavacciuolo Bistrot', 'Italia', 'Torino', 'via Umberto Cosmo 6',
     45.0627906, 7.7009861,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Abocar Due Cucine', 'Italia', 'Rimini', 'Via Carlo Farini 13',
     44.0626895, 12.5663487,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€
    ('La Pineta (Marina di Bibbona)', 'Italia', 'Marina di Bibbona', 'via dei Cavalleggeri Nord 27',
     43.2456729, 10.52825,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- 1 Star | €€€€
    ('Indaco', 'Italia', 'Lacco Ameno', 'piazza Santa Restituta 1',
     40.75377, 13.88421,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Oro Restaurant', 'Italia', 'Venezia', 'isola della Giudecca 10',
     45.4271, 12.3412,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Accursio', 'Italia', 'Modica', 'via Grimaldi 41',
     36.8599639, 14.7606251,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Sadler', 'Italia', 'Milano', 'via dell''Annunciata 14',
     45.4726277, 9.1926191,
     160.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- 1 Star | €€€
    ('Ca'' Vittoria', 'Italia', 'Tigliole', 'via Roma 14',
     44.8866139, 8.075849,
     85.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Iyo', 'Italia', 'Milano', 'via Piero della Francesca 74',
     45.4866231, 9.1595273,
     160.0, FALSE, FALSE,
     'Giapponese', 'anna_m'),
    -- 1 Star | €€€
    ('Sedicesimo Secolo', 'Italia', 'Pudiano', 'via Gerolanuova 4',
     45.3985234, 10.0093247,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Josè Restaurant - Tenuta Villa Guerra', 'Italia', 'Torre del Greco', 'via Nazionale 414',
     40.7739059, 14.3988055,
     85.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Vecchia Malcesine', 'Italia', 'Malcesine', 'via Pisort 6',
     45.7624261, 10.8079125,
     160.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€
    ('Al Metrò', 'Italia', 'San Salvo Marina', 'via Magellano 35',
     42.0681141, 14.7751494,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€
    ('D.One Restaurant', 'Italia', 'Montepagano', 'via del Borgo 1',
     42.6772065, 13.9897625,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Il Convivio Troiani', 'Italia', 'Roma', 'vicolo dei Soldati 31',
     41.9015408, 12.4727932,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Amistà', 'Italia', 'Corrubbio', 'via Cedrare 78',
     45.48968, 10.899484,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('Il Centro', 'Italia', 'Priocca', 'via Umberto I 5',
     44.7905049, 8.0624786,
     85.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- 1 Star | €€€€
    ('La Preséf', 'Italia', 'Mantello', 'via Lungo Adda 12',
     46.1505628, 9.4898006,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('La Credenza', 'Italia', 'San Maurizio Canavese', 'via Cavour 22',
     45.217537, 7.6319706,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('La Tortuga', 'Italia', 'Gargnano', 'via XXIV Maggio 5',
     45.6887411, 10.6652261,
     160.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Bracali', 'Italia', 'Ghirlanda', 'via di Perolla 2',
     43.0584193, 10.8995225,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€
    ('Il Gallo Cedrone', 'Italia', 'Madonna di Campiglio', 'via Cima Tosa 80',
     46.2351343, 10.8242148,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Butterfly', 'Italia', 'Marlia', 'SS 12 del Brennero 192',
     43.894723, 10.5364035,
     160.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Osteria della Brughiera', 'Italia', 'Villa d''Almè', 'via Brughiera 49',
     45.7393224, 9.626508,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Antonello Colonna Labico', 'Italia', 'Labico', 'via di Valle Fredda 52',
     41.78292, 12.873689,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('Oasis - Sapori Antichi', 'Italia', 'Vallesaccarda', 'via Provinciale 8/10',
     41.0633639, 15.251974,
     85.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Venissa', 'Italia', 'Mazzorbo', 'fondamenta Santa Caterina 3',
     45.4886784, 12.4111088,
     160.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€
    ('Atman', 'Italia', 'Vinci', 'Via IV Novembre 20',
     43.7862727, 10.9260458,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Marco Martini Restaurant', 'Italia', 'Roma', 'viale Aventino 121',
     41.880543, 12.484601,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Suinsom', 'Italia', 'Selva di Val Gardena', 'strada Puez 12',
     46.5565821, 11.7597819,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Casamatta', 'Italia', 'Manduria', 'contrada Scrasciosa',
     40.417763, 17.599371,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€
    ('Malga Panna', 'Italia', 'Moena', 'strada de Sort 64',
     46.376804, 11.643984,
     85.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Bistrot (Forte dei Marmi)', 'Italia', 'Forte dei Marmi', 'viale Franceschi 14',
     43.9553929, 10.1700005,
     160.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- 1 Star | €€€€
    ('Per Me Giulio Terrinoni', 'Italia', 'Roma', 'vicolo del Malpasso 9',
     41.8973035, 12.46747,
     160.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- 1 Star | €€€€
    ('Zass', 'Italia', 'Positano', 'via Laurito 2',
     40.623268, 14.5038074,
     160.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Glass Hostaria', 'Italia', 'Roma', 'vicolo del Cinque 58',
     41.890477, 12.4691032,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Cannavacciuolo by the Lake', 'Italia', 'Pettenasco', 'Via Legro 33',
     45.8194894, 8.4047596,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Lux Lucis', 'Italia', 'Forte dei Marmi', 'viale A. Morin 67',
     43.9516497, 10.1758581,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€
    ('La Bandiera', 'Italia', 'Civitella Casanova', 'contrada Pastini 4',
     42.3698762, 13.9082843,
     85.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'anna_m'),
    -- 1 Star | €€€€
    ('Antica Corte Pallavicina', 'Italia', 'Polesine Parmense', 'strada del Palazzo Due Torri 3',
     45.0212099, 10.0859923,
     160.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€€
    ('Anima', 'Italia', 'Milano', 'via Gaspare Rosales 4',
     45.4812376, 9.1884973,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Don Alfonso 1890', 'Italia', 'Sant''Agata sui Due Golfi', 'corso Sant''Agata 11',
     40.6078108, 14.3737397,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Vineria Modì', 'Italia', 'Taormina', 'via Calapitrulli 13',
     37.8525201, 15.28835,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('Re Santi e Leoni', 'Italia', 'Nola', 'via Anfiteatro Laterizio 92',
     40.9282413, 14.5266743,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Italo Bassi Confusion Restaurant', 'Italia', 'Porto Cervo', 'via Aga Khan 1',
     41.1345678, 9.5375439,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€€
    ('Il Pellicano', 'Italia', 'Porto Ercole', 'località Lo Sbarcatello',
     42.380472, 11.1924477,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Pipero Roma', 'Italia', 'Roma', 'corso Vittorio Emanuele II 250',
     41.8977856, 12.468814,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Felix Lo Basso home & restaurant', 'Italia', 'Milano', 'via Carlo Goldoni 36',
     45.4691981, 9.2135984,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€
    ('L''Acciuga (Perugia)', 'Italia', 'Perugia', 'via Settevalli 217',
     43.090152, 12.3684296,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Ada', 'Italia', 'Perugia', 'via del Bovaro 2',
     43.1085426, 12.390168,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Mec Restaurant', 'Italia', 'Palermo', 'via Vittorio Emanuele 452',
     38.1139131, 13.3572594,
     160.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- 1 Star | €€€
    ('Luigi Lepore', 'Italia', 'Lamezia Terme', 'via Ubaldo de'' Medici 50',
     38.9747876, 16.3178609,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Gabbiano 3.0', 'Italia', 'Marina di Grosseto', 'Porto turistico 11',
     42.7137124, 10.9836869,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€
    ('Il Marin', 'Italia', 'Genova', 'Calata Cattaneo',
     44.4087159, 8.9274716,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- 1 Star | €€€
    ('Casa Buono', 'Italia', 'Ventimiglia', 'corso Cuneo 28',
     43.8460898, 7.5886507,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€
    ('Vite (Lancenigo)', 'Italia', 'Lancenigo', 'viale della Repubblica 3',
     45.6981936, 12.2575026,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Osteria degli Assonica', 'Italia', 'Sorisole', 'via Don Santo Carminati 9',
     45.7431411, 9.6526781,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€
    ('Impronta d''Acqua', 'Italia', 'Cavi di Lavagna', 'via Aurelia 2121',
     44.2953631, 9.3696755,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Imàgo', 'Italia', 'Roma', 'piazza Trinità dei Monti 6',
     41.9059417, 12.4836585,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Inkiostro', 'Italia', 'Parma', 'via San Leonardo 124',
     44.8270877, 10.3368273,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Porta di Basso', 'Italia', 'Peschici', 'via Colombo 38',
     41.9493778, 16.0129423,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- 1 Star | €€€
    ('Tancredi', 'Italia', 'Sirmione', 'via XXV Aprile 75',
     45.4771961, 10.6108961,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€
    ('Dolce Stil Novo alla Reggia', 'Italia', 'Venaria Reale', 'piazza della Repubblica 4',
     45.1351147, 7.6249928,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Gucci Osteria da Massimo Bottura', 'Italia', 'Firenze', 'piazza della Signoria 10',
     43.7698043, 11.2567125,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('La Speranzina Restaurant & Relais', 'Italia', 'Sirmione', 'via Dante 16',
     45.4931589, 10.6085873,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Paolo e Barbara', 'Italia', 'San Remo', 'via Roma 47',
     43.8148723, 7.7738025,
     160.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€€
    ('Osteria di Passignano', 'Italia', 'Passignano', 'via Passignano 33',
     43.5768675, 11.2458106,
     160.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€€
    ('Horto', 'Italia', 'Milano', 'via San Protaso 5',
     45.4661897, 9.1875693,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('I Portici', 'Italia', 'Bologna', 'via dell''Indipendenza 69',
     44.5019817, 11.3448122,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Alto', 'Italia', 'Fiorano Modenese', 'via Circondariale San Francesco 2',
     44.5379081, 10.8243773,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Contaminazioni Restaurant', 'Italia', 'Somma Vesuviana', 'via San Sossio 2',
     40.8760367, 14.4426613,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Lazzaro 1915', 'Italia', 'Pontelongo', 'via Roma 351',
     45.2499891, 12.0203099,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€
    ('Osteria Altran', 'Italia', 'Ruda', 'Località Cortona 19',
     45.8132892, 13.410651,
     85.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- 1 Star | €€€
    ('Silene', 'Italia', 'Seggiano', 'Località Pescina 9',
     42.9193337, 11.5873607,
     85.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- 1 Star | €€€
    ('Osteria Acquarol', 'Italia', 'San Michele', 'via Johann Georg Plazer 10',
     46.4556659, 11.2589442,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Lorelei', 'Italia', 'Sorrento', 'via Aniello Califano 4',
     40.6298333, 14.3813239,
     160.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- 1 Star | €€€€
    ('Pulejo', 'Italia', 'Roma', 'via dei Gracchi 31',
     41.9071673, 12.4593589,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Cannavacciuolo Countryside', 'Italia', 'Ticciano', 'via Ticciano 137',
     40.6456678, 14.4515515,
     160.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- 1 Star | €€€
    ('Acqua Pazza (Ponza)', 'Italia', 'Ponza', 'via Dietro la Chiesa 3/4',
     40.8950712, 12.9672021,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- 1 Star | €€€€
    ('Taverna del Capitano', 'Italia', 'Marina del Cantone', 'piazza delle Sirene 10/11',
     40.5833911, 14.3565359,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€
    ('Marotta', 'Italia', 'Squille', 'via Marrochelle 52',
     41.146241, 14.4096379,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€
    ('La Coldana', 'Italia', 'Lodi', 'Cascina Coldana',
     45.30426, 9.51811,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('FRE', 'Italia', 'Monforte d''Alba', 'località San Sebastiano 68',
     44.5638925, 7.9622354,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Peter Brunel Ristorante Gourmet', 'Italia', 'Arco', 'via Linfano 47',
     45.8869889, 10.8788696,
     160.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Moebius Sperimentale', 'Italia', 'Milano', 'via Cappelini 25',
     45.4814199, 9.2028665,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Il Visibilio', 'Italia', 'Castelnuovo Berardenga', 'SP 9 di Pievasciata 32',
     43.391479, 11.3899308,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Spinechile', 'Italia', 'Schio', 'contra'' Pacche 2',
     45.746704, 11.337221,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€€
    ('Vescovado', 'Italia', 'Noli', 'piazzale Rosselli',
     44.2075887, 8.4163876,
     160.0, FALSE, FALSE,
     'Ligure', 'marco_f'),
    -- 1 Star | €€€€
    ('The Cook', 'Italia', 'Genova', 'vico Falamonica 9 r',
     44.4080433, 8.9339301,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Andrea Monesi - Locanda di Orta', 'Italia', 'Orta San Giulio', 'via Olina 18',
     45.7987785, 8.4058245,
     160.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€€
    ('Glicine', 'Italia', 'Amalfi', 'via Mauro Comite 9',
     40.6294267, 14.5928504,
     160.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- 1 Star | €€€
    ('Villa Naj', 'Italia', 'Stradella', 'via Martiri Partigiani 5',
     45.0767345, 9.3006567,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Matteo Grandi in Basilica', 'Italia', 'Vicenza', 'piazza dei Signori 1',
     45.547245, 11.5459015,
     160.0, FALSE, FALSE,
     'Km Zero', 'anna_m'),
    -- 1 Star | €€€€
    ('Alici', 'Italia', 'Amalfi', 'via Giovanni Augustariccio 33',
     40.6188758, 14.5766031,
     160.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- 1 Star | €€€€
    ('La Caravella dal 1959', 'Italia', 'Amalfi', 'via Matteo Camera 12',
     40.6338537, 14.6016228,
     160.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- 1 Star | €€€
    ('San Martino (Treviglio)', 'Italia', 'Treviglio', 'viale Cesare Battisti 3',
     45.5241033, 9.5937071,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- 1 Star | €€€
    ('Il Piastrino', 'Italia', 'Pennabilli', 'via Parco Begni 7',
     43.82111, 12.2645054,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€
    ('Guido', 'Italia', 'Rimini', 'lungomare Guido Spadazzi 12',
     44.03226, 12.62138,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- 1 Star | €€€
    ('Massimo Camia', 'Italia', 'La Morra', 'SP3 Alba-Barolo 122',
     44.6267818, 7.957004,
     85.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- 1 Star | €€€€
    ('Il Flauto di Pan', 'Italia', 'Ravello', 'via Santa Chiara 26',
     40.6446307, 14.6111085,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Luisl Stube', 'Italia', 'Algund', 'via Venosta 4',
     46.6780899, 11.1191355,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('SanBrite', 'Italia', 'Cortina d''Ampezzo', 'Località Alverà',
     46.5434618, 12.1565176,
     160.0, FALSE, FALSE,
     'Alpine', 'anna_m'),
    -- 1 Star | €€€
    ('Paca', 'Italia', 'Prato', 'via Fra'' Bartolomeo 13',
     43.8771796, 11.1021824,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('Quintessenza', 'Italia', 'Trani', 'via Lionelli 62',
     41.2814264, 16.4150326,
     85.0, FALSE, FALSE,
     'Apulian', 'riccardo_r'),
    -- 1 Star | €€€€
    ('La Favellina', 'Italia', 'Malo', 'via Cosari 4/6',
     45.6195715, 11.4018545,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Lorenzo', 'Italia', 'Forte dei Marmi', 'via Carducci 61',
     43.9565557, 10.1722156,
     160.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- 1 Star | €€€
    ('Arnaldo - Clinica Gastronomica', 'Italia', 'Rubiera', 'piazza XXIV Maggio 3',
     44.65313, 10.78183,
     85.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- 1 Star | €€€
    ('L''Erba del Re', 'Italia', 'Modena', 'via Castelmaraldo 45',
     44.64916, 10.92368,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('La Primula', 'Italia', 'San Quirino', 'via San Rocco 47',
     46.0348797, 12.6814506,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Vignamare', 'Italia', 'Andora', 'strada Castello',
     43.9729346, 8.150574,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Retroscena', 'Italia', 'Porto San Giorgio', 'largo del Teatro 3',
     43.1799835, 13.7928865,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€€
    ('Li Galli', 'Italia', 'Positano', 'viale Pasitea 318',
     40.6283391, 14.4841948,
     160.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('Il Circolino', 'Italia', 'Monza', 'via Anita Garibaldi 4',
     45.5853814, 9.2782646,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Lunasia', 'Italia', 'Viareggio', 'viale Manin 4a',
     43.86546, 10.24472,
     160.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Famiglia Rana', 'Italia', 'Oppeano', 'via Feniletto 2',
     45.3143007, 11.1219854,
     160.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Rossellinis', 'Italia', 'Ravello', 'via San Giovanni del Toro 28',
     40.650822, 14.6130382,
     160.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Atto di Vito Mollica', 'Italia', 'Firenze', 'via del Corso 6',
     43.7715205, 11.2573751,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Andrea Larossa', 'Italia', 'Torino', 'via Sabaudia 4',
     45.03681, 7.68156,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Otto Geleng', 'Italia', 'Taormina', 'via Teatro Greco 59',
     37.852584, 15.290778,
     160.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Iyo Kaiseki', 'Italia', 'Milano', 'piazza Alvar Aalto 9N02',
     45.4822057, 9.1932579,
     85.0, FALSE, FALSE,
     'Giapponese', 'anna_m'),
    -- 1 Star | €€€
    ('Casa Leali', 'Italia', 'Puegnago sul Garda', 'via Valle 1',
     45.5738761, 10.5079936,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('Don Alfonso 1890 San Barbato', 'Italia', 'Lavello', 'SS 93',
     41.0348692, 15.7714997,
     85.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- 1 Star | €€€
    ('Casa Sgarra', 'Italia', 'Trani', 'lungomare C. Colombo 114',
     41.270434, 16.4341752,
     85.0, FALSE, FALSE,
     'Apulian', 'anna_m'),
    -- 1 Star | €€€
    ('Cetaria', 'Italia', 'Baronissi', 'piazza della Repubblica 9',
     40.7469801, 14.7701282,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Sensi', 'Italia', 'Amalfi', 'via Pietro Comite 4',
     40.6339198, 14.6030985,
     160.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Osteria del Viandante', 'Italia', 'Rubiera', 'piazza XXIV Maggio 15',
     44.65333, 10.781861,
     85.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- 1 Star | €€€€
    ('La Rucola 2.0', 'Italia', 'Sirmione', 'vicolo Strentelle 3',
     45.4928374, 10.6081306,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Impronte', 'Italia', 'Bergamo', 'via Cristoforo Baioni 38',
     45.7088279, 9.6736129,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Qafiz', 'Italia', 'Santa Cristina d''Aspromonte', 'località Calabretto',
     38.2814827, 15.9488692,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Gambero Rosso', 'Italia', 'Marina di Gioiosa Ionica', 'via Montezemolo 65',
     38.2990691, 16.3276783,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- 1 Star | €€€
    ('Grow Restaurant', 'Italia', 'Albiate', 'via San Valerio 4',
     45.659927, 9.2560185,
     85.0, FALSE, FALSE,
     'Km Zero', 'riccardo_r'),
    -- 1 Star | €€€
    ('Līmū', 'Italia', 'Bagheria', 'via Ciro Scianna 177',
     38.0827311, 13.5077378,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€€
    ('Quadri', 'Italia', 'Venezia', 'piazza San Marco 121 (primo piano)',
     45.4342534, 12.3379939,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€€
    ('In Viaggio - Claudio Melis', 'Italia', 'Merano', 'via Belvedere 17',
     46.6729096, 11.1765125,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Volta del Fuenti by Michele De Blasio', 'Italia', 'Vietri sul Mare', 'SS 163 Amalfitana km 47',
     40.6622055, 14.7138129,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€€
    ('Linfa', 'Italia', 'San Gimignano', 'piazza Sant''Agostino 19a',
     43.4704034, 11.0420787,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Cortile Spirito Santo', 'Italia', 'Syracuse', 'via Salomone 21',
     37.0564227, 15.2944533,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Il Refettorio', 'Italia', 'Conca dei Marini', 'via Roma 2',
     40.6207949, 14.5770614,
     160.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- 1 Star | €€€
    ('Mater1apr1ma', 'Italia', 'Pontinia', 'via Sardegna 8',
     41.4102602, 13.0433461,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('Sine by Di Pinto', 'Italia', 'Milano', 'viale Umbria 126',
     45.4618683, 9.2177246,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Unforgettable', 'Italia', 'Torino', 'via Lorenzo Valerio 5/b',
     45.0767241, 7.6780009,
     160.0, FALSE, FALSE,
     'Innovativa', 'anna_m'),
    -- 1 Star | €€€€
    ('Don Geppi', 'Italia', 'Sant'' Agnello', 'corso Marion Crawford 40',
     40.631762, 14.3931433,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Oseleta', 'Italia', 'Cavaion Veronese', 'località Cordevigo',
     45.5252757, 10.7912839,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Le Monzù', 'Italia', 'Capri', 'via Tragara 57',
     40.5454969, 14.2502024,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Il Poggio Rosso', 'Italia', 'Castelnuovo Berardenga', 'località San Felice',
     43.3880261, 11.4599683,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Il Saraceno', 'Italia', 'Cavernago', 'piazza Don Verdelli 2',
     45.6304009, 9.7650842,
     160.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Tivoli', 'Italia', 'Cortina d''Ampezzo', 'località Lacedel 34',
     46.5338851, 12.1222949,
     160.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Vecchio Ristoro', 'Italia', 'Aosta', 'via Tourneuve 4',
     45.7386406, 7.3155868,
     160.0, FALSE, FALSE,
     'Cuisine from the Aosta Valley', 'marco_f'),
    -- 1 Star | €€€€
    ('Il Palagio', 'Italia', 'Firenze', 'borgo Pinti 99',
     43.7774093, 11.2665237,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('La Parolina', 'Italia', 'Trevinano', 'via Giacomo Leopardi 1',
     42.8218494, 11.8692538,
     160.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€€
    ('Storie d''Amore', 'Italia', 'Borgoricco', 'via Desman 418',
     45.5381705, 11.9290391,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€
    ('Cannavacciuolo Vineyard', 'Italia', 'Casanova di Terricciola', 'via del Teatro 8',
     43.5443686, 10.6851821,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Elementi', 'Italia', 'Torgiano', 'via del Colle 38',
     43.0557212, 12.4668702,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€
    ('Equilibrio', 'Italia', 'Dolcedo', 'località Martin 13',
     43.8999334, 7.9589059,
     85.0, FALSE, FALSE,
     'Ligure', 'marco_f'),
    -- 1 Star | €€€€
    ('Idylio by Apreda', 'Italia', 'Roma', 'Piazza dei Caprettari',
     41.8983053, 12.475718,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('ARIA', 'Italia', 'Napoli', 'via Loggia dei Pisani 2',
     40.8433646, 14.2540383,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Gusto by Sadler', 'Italia', 'San Teodoro', 'via Tavolara',
     40.8215359, 9.6775825,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€
    ('Da Gorini', 'Italia', 'San Piero in Bagno', 'via Verdi 5',
     43.8587914, 11.9745195,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Apostelstube', 'Italia', 'Brixen', 'via rio Bianco 4',
     46.7194264, 11.6532447,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€€
    ('Grual', 'Italia', 'Pinzolo', 'via Alpe di Grual 16',
     46.1669869, 10.7666316,
     160.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- 1 Star | €€€
    ('La Zanzara', 'Italia', 'Codigoro', 'via per Volano 52',
     44.80822, 12.252958,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Local', 'Italia', 'Venezia', 'salizzada dei Greci',
     45.4356, 12.345893,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Octavin', 'Italia', 'Arezzo', 'scalinata Camillo Berneri 2',
     43.4645907, 11.8796939,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Caracol', 'Italia', 'Bacoli', 'via Faro 44',
     40.7827339, 14.0834126,
     160.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Contrada Bricconi', 'Italia', 'Oltressenda Alta', 'via Bricconi 3',
     45.922027, 9.9503694,
     160.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€€
    ('Leon d''Oro', 'Italia', 'Pralboino', 'via Gambara 6',
     45.2676884, 10.2144844,
     160.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€€
    ('Al Gatto Verde', 'Italia', 'Modena', 'Stradello Bonaghino 56',
     44.6102868, 10.980026,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Terramira', 'Italia', 'Capolona', 'piazza della Vittoria 13',
     43.5626194, 11.8598776,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- 1 Star | €€€
    ('Osmosi', 'Italia', 'Montepulciano', 'via Umbria 65',
     43.1335561, 11.8352976,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- 1 Star | €€€€
    ('Paolo Griffa al Caffè Nazionale', 'Italia', 'Aosta', 'piazza Emile Chanoux 9',
     45.7376217, 7.3208336,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Fradis Minoris', 'Italia', 'Pula', 'Laguna di Nora',
     38.9867335, 9.0067135,
     160.0, FALSE, FALSE,
     'Sarda', 'anna_m'),
    -- 1 Star | €€€€
    ('Olmo', 'Italia', 'Cornaredo', 'Piazza della Chiesa 7',
     45.48761, 9.0124,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€
    ('Sintesi', 'Italia', 'Ariccia', 'viale dei Castani 17',
     41.725881, 12.6797918,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Marco Bottega Ristorante', 'Italia', 'Genazzano', 'via Trovano 3',
     41.81092, 12.966885,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Locanda de Banchieri', 'Italia', 'Fosdinovo', 'via Porredo 32',
     44.114434, 10.0015367,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€€
    ('Dattilo', 'Italia', 'Strongoli', 'Contrada Dattilo',
     39.2678209, 17.0817921,
     160.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Hyle', 'Italia', 'San Giovanni in Fiore', 'contrada Torre Garga SS 107',
     39.2851384, 16.6441951,
     160.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€
    ('Une', 'Italia', 'Capodacqua', 'via Fiorenzuola 37',
     43.0177785, 12.7839323,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Wistèria', 'Italia', 'Venezia', 'fondamenta del Forner',
     45.43566, 12.32682,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Coria', 'Italia', 'Catania', 'Via Prefettura 21',
     37.5053157, 15.0860216,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€€
    ('Colline Ciociare', 'Italia', 'Acuto', 'via Prenestina 27',
     41.7921649, 13.1806079,
     160.0, FALSE, FALSE,
     'Cuisine from Lazio', 'marco_f'),
    -- 1 Star | €€€
    ('Wood', 'Italia', 'Breuil Cervinia', 'via Guido Rey 26',
     45.9334388, 7.6294846,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€
    ('Trattoria da Amerigo', 'Italia', 'Savigno', 'via Marconi 16',
     44.3903557, 11.0739644,
     45.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- 1 Star | €€€€
    ('El Molin', 'Italia', 'Cavalese', 'via Muratori 2',
     46.292019, 11.4594756,
     160.0, FALSE, FALSE,
     'Alpine', 'marco_f'),
    -- 1 Star | €€€€
    ('Piano35', 'Italia', 'Torino', 'corso Inghilterra 3',
     45.06945, 7.663038,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Trattoria al Cacciatore - La Subida', 'Italia', 'Cormons', 'via Subida 52',
     45.9644549, 13.4962756,
     85.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- 1 Star | €€€€
    ('Aqua Crua', 'Italia', 'Barbarano Vicentino', 'piazza Calcalusso 11/a',
     45.42242, 11.55369,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Damini Macelleria & Affini', 'Italia', 'Arzignano', 'via Cadorna 31',
     45.5174094, 11.334968,
     85.0, FALSE, FALSE,
     'Meats and Grills', 'riccardo_r'),
    -- 1 Star | €€€
    ('All''Enoteca', 'Italia', 'Canale', 'via Roma 57',
     44.7969211, 7.9931019,
     85.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- 1 Star | €€€€
    ('Casa Vissani', 'Italia', 'Baschi', 'vocabolo Cannitello',
     42.7141448, 12.2655765,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Sapio', 'Italia', 'Catania', 'piazza Antonino Gandolfo 11',
     37.505208, 15.0949852,
     160.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Il Sereno Al Lago', 'Italia', 'Torno', 'via Torrazza 10',
     45.8589005, 9.1175648,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Casa Mazzucchelli', 'Italia', 'Sasso Marconi', 'via Porrettana 291',
     44.4125719, 11.2578948,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Ristorante del Lago', 'Italia', 'Bagno di Romagna', 'via Acquapartita 147',
     43.86577, 12.027197,
     85.0, FALSE, FALSE,
     'Cuisine from Romagna', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Principe Cerami', 'Italia', 'Taormina', 'piazza San Domenico 5',
     37.8502374, 15.2833763,
     160.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€€
    ('Nove', 'Italia', 'Alassio', 'via Privata Montagù 9/1',
     44.0118042, 8.1720871,
     160.0, FALSE, FALSE,
     'Ligure', 'marco_f'),
    -- 1 Star | €€€
    ('Trattoria contemporanea', 'Italia', 'Lomazzo', 'via del Ronco 10',
     45.6967845, 9.034291,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€€
    ('sui generis.', 'Italia', 'Saronno', 'via Roma 35',
     45.6249766, 9.0412055,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€€
    ('Il Fuoco Sacro', 'Italia', 'San Pantaleo', 'strada di Buddeo',
     41.0529512, 9.4460803,
     160.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- 1 Star | €€€
    ('Contrada', 'Italia', 'Castelnuovo Berardenga', 'località Monastero d''Ombrone 19',
     43.3510435, 11.56022,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- 1 Star | €€€
    ('Il Cantuccio', 'Italia', 'Albavilla', 'via Dante 32',
     45.8042803, 9.1891665,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- 1 Star | €€€
    ('La Palta', 'Italia', 'Borgonovo Val Tidone', 'Località Bilegno',
     44.9922314, 9.4694233,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- 1 Star | €€€€
    ('Il Falconiere', 'Italia', 'San Martino', 'Località San Martino a Bocena 370',
     43.2821868, 11.9763509,
     160.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Il Desco', 'Italia', 'Verona', 'via Dietro San Sebastiano 7',
     45.4416145, 10.9999722,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- 1 Star | €€€
    ('Il Bavaglino', 'Italia', 'Terrasini', 'via dei Mille 2/b',
     38.1556478, 13.0812287,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('I Pupi', 'Italia', 'Bagheria', 'via del Cavaliere 59',
     38.0803301, 13.5116679,
     160.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- 1 Star | €€€
    ('Al Gambero', 'Italia', 'Calvisano', 'via Roma 11',
     45.3492146, 10.3430573,
     85.0, FALSE, FALSE,
     'Lombardian', 'anna_m'),
    -- 1 Star | €€€
    ('Dissapore di Andrea Catalano', 'Italia', 'Carovigno', 'via Pietro Micca 15',
     40.7073742, 17.6581893,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- 1 Star | €€€
    ('Locanda del Pilone', 'Italia', 'Alba', 'frazione Madonna di Como 34',
     44.66367, 8.04705,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€€
    ('Pashà', 'Italia', 'Conversano', 'via Morgantini 2',
     40.9691184, 17.1151639,
     160.0, FALSE, FALSE,
     'Apulian', 'anna_m'),
    -- 1 Star | €€€€
    ('Saporium', 'Italia', 'Chiusdino', 'località Palazzetto 110',
     43.1414359, 11.1278724,
     160.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- 1 Star | €€€€
    ('La Stüa de Michil', 'Italia', 'Corvara in Badia', 'strada Col Alt 105',
     46.5471597, 11.8762348,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€
    ('Due Colombe', 'Italia', 'Borgonato', 'via Foresti 13',
     45.62138, 10.018,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€
    ('Il Tino', 'Italia', 'Fiumicino', 'via Monte Cadria 127',
     41.747204, 12.257445,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€€
    ('Nin', 'Italia', 'Brenzone sul Garda', 'via Zanardelli 5',
     45.7154863, 10.7716421,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- 1 Star | €€€
    ('Kuppelrain', 'Italia', 'Castelbello', 'via Stazione 16',
     46.6264329, 10.8975683,
     85.0, FALSE, FALSE,
     'Km Zero', 'anna_m'),
    -- 1 Star | €€€
    ('L''Asinello', 'Italia', 'Castelnuovo Berardenga', 'via Nuova 6',
     43.385393, 11.4808519,
     85.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- 1 Star | €€€€
    ('Un Piano nel Cielo', 'Italia', 'Praiano', 'via Capriglione 147',
     40.6136373, 14.5218291,
     160.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Materia', 'Italia', 'Cernobbio', 'via Trieste 1/B',
     45.8383599, 9.0685195,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- 1 Star | €€€
    ('Essenza', 'Italia', 'Terracina', 'via Cavour 38',
     41.287274, 13.2566963,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- 1 Star | €€€
    ('Acqua (Olgiate Olona)', 'Italia', 'Olgiate Olona', 'via Filippo Corridoni 1',
     45.6346559, 8.8878287,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- 1 Star | €€€€
    ('I Tenerumi', 'Italia', 'Isola Vulcano', 'via Vulcanello',
     38.4305495, 14.9560516,
     160.0, FALSE, FALSE,
     'Vegetariana', 'anna_m'),
    -- 1 Star | €€€€
    ('Orma Roma', 'Italia', 'Roma', 'via Boncompagni 31',
     41.9084005, 12.4933748,
     160.0, FALSE, FALSE,
     'Fusion', 'marco_f'),
    -- 1 Star | €€€€
    ('Iris Ristorante', 'Italia', 'Verona', 'Via Leoni 10',
     45.4397874, 11.0005401,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- 1 Star | €€€
    ('Nazionale', 'Italia', 'Vernante', 'via Cavour 60',
     44.2437857, 7.5334988,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- 1 Star | €€€€
    ('Zia', 'Italia', 'Roma', 'via Goffredo Mameli 45',
     41.8870305, 12.4679141,
     160.0, FALSE, FALSE,
     'Innovativa', 'marco_f'),
    -- 1 Star | €€€
    ('Borgo Sant''Anna', 'Italia', 'Monforte d''Alba', 'Località Sant''Anna 84',
     44.5777429, 7.994414,
     85.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Il Tirabusciò', 'Italia', 'Bibbiena', 'via Rosa Scoti 12',
     43.6950608, 11.8175308,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Bib Gourmand | €€
    ('Osteria dell''Accademia', 'Italia', 'Montegridolfo', 'Via Roma 16',
     43.8593, 12.68869,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Bib Gourmand | €
    ('Fratelli Bruzzone', 'Italia', 'Torino', 'via Maria Vittoria 34/a',
     45.0655091, 7.6908482,
     20.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Ostaria Pignatelli', 'Italia', 'Napoli', 'Riviera di Chiaia 216',
     40.8339281, 14.2354339,
     20.0, FALSE, FALSE,
     'Campanian', 'anna_m'),
    -- Bib Gourmand | €
    ('Altavilla', 'Italia', 'Bianzone', 'via Monti 46',
     46.1896712, 10.1084315,
     20.0, FALSE, FALSE,
     'Cuisine from Valtellina', 'marco_f'),
    -- Bib Gourmand | €
    ('La Villa', 'Italia', 'Melfi', 'Contrada Cavallerizza',
     41.0112866, 15.6249303,
     20.0, FALSE, FALSE,
     'Cuisine from Basilicata', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Antica Trattoria Gianna', 'Italia', 'Recorfano', 'via Maggiore 12',
     45.1033105, 10.3448888,
     20.0, FALSE, FALSE,
     'Lombardian', 'anna_m'),
    -- Bib Gourmand | €€
    ('Oberraindlhof', 'Italia', 'Madonna di Senales', 'Raindl 49',
     46.7144175, 10.8830095,
     45.0, FALSE, FALSE,
     'Tradizionale', 'marco_f'),
    -- Bib Gourmand | €€
    ('Fracia', 'Italia', 'Teglio', 'località Fracia',
     46.1706561, 10.007899,
     45.0, FALSE, FALSE,
     'Cuisine from Valtellina', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Clemente', 'Italia', 'Sulmona', 'piazza Santa Monica',
     42.0507934, 13.923711,
     45.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'anna_m'),
    -- Bib Gourmand | €
    ('Dalla Rosa Alda', 'Italia', 'San Giorgio di Valpolicella', 'strada Garibaldi 4',
     45.5344148, 10.8498371,
     20.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Bib Gourmand | €€
    ('Nida', 'Italia', 'Lucca', 'via Nicola Barbantini 338',
     43.8485511, 10.5189835,
     45.0, FALSE, FALSE,
     'Giapponese', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Hostaria Viola', 'Italia', 'Castiglione delle Stiviere', 'via Verdi 32',
     45.3801999, 10.5092241,
     45.0, FALSE, FALSE,
     'Mantuan', 'anna_m'),
    -- Bib Gourmand | €€
    ('Vez', 'Italia', 'San Marzano di San Giuseppe', 'via Addolorata 7/9',
     40.4506478, 17.5057769,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Bib Gourmand | €€
    ('Cile''s', 'Italia', 'Fano', 'viale Cesare Battisti 35',
     43.8463162, 13.0239803,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Trattoria Antichi Sapori', 'Italia', 'Gaione', 'via Montanara 318',
     44.7559486, 10.2862789,
     20.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Bib Gourmand | €€
    ('Old Friend', 'Italia', 'Cagliari', 'via Giuseppe Cesare Abba 51',
     39.2172498, 9.1197194,
     45.0, FALSE, FALSE,
     'Km Zero', 'marco_f'),
    -- Bib Gourmand | €€
    ('Palmerino - Il Bacalà a Sandrigo', 'Italia', 'Sandrigo', 'via Piave 13',
     45.6617521, 11.621564,
     45.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Trattoria da Marino', 'Italia', 'Serravalle Pistoiese', 'via Provinciale Lucchese 102',
     43.8969441, 10.8219738,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Bib Gourmand | €
    ('Scannabue Caffè Restaurant', 'Italia', 'Torino', 'largo Saluzzo 25/h',
     45.0583351, 7.6789861,
     20.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Bib Gourmand | €€
    ('Camiano Piccolo', 'Italia', 'Montefalco', 'via Camiano Piccolo 5',
     42.8920383, 12.6601425,
     45.0, FALSE, FALSE,
     'Umbrian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Osteria Le Panzanelle', 'Italia', 'Lucarelli', 'località Lucarelli 29',
     43.518906, 11.311626,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Bib Gourmand | €€
    ('Raieü', 'Italia', 'Cavi di Lavagna', 'via Milite Ignoto 23',
     44.2900882, 9.380131,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Bib Gourmand | €
    ('Antica Trattoria da Miculan', 'Italia', 'Tricesimo', 'piazza Libertà 16',
     46.1610218, 13.2163029,
     20.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Foresta (Moena)', 'Italia', 'Moena', 'strada de la Comunità de Fiem 42',
     46.3599668, 11.6432225,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Bib Gourmand | €
    ('La Tradizione - Cucina Casalinga', 'Italia', 'Minervino Murge', 'via Imbriani 11/13',
     41.08729, 16.078632,
     20.0, FALSE, FALSE,
     'Apulian', 'marco_f'),
    -- Bib Gourmand | €
    ('Osteria de Börg', 'Italia', 'Rimini', 'via Forzieri 12',
     44.0648372, 12.5634582,
     20.0, FALSE, FALSE,
     'Cuisine from Romagna', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Antica Trattoria la Grotta dal 1918', 'Italia', 'Sasso Marconi', 'via Tignano 3',
     44.4181181, 11.2115002,
     20.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Bib Gourmand | €€
    ('Del Belbo - Da Bardon', 'Italia', 'San Marzano Oliveto', 'valle Asinari 25',
     44.7401454, 8.3182743,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €
    ('Trattoria Losanna', 'Italia', 'Masio', 'via San Rocco 40',
     44.8675184, 8.4205078,
     20.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Casa del Vino della Vallagarina', 'Italia', 'Isera', 'piazza San Vincenzo 1',
     45.8854479, 11.0092968,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Bib Gourmand | €
    ('Angiolina', 'Italia', 'Pisciotta', 'via Passariello 2',
     40.1017552, 15.2284136,
     20.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Bib Gourmand | €
    ('Osteria Didù', 'Italia', 'Imperia', 'via Felice Cascione 70',
     43.8763874, 8.013935,
     20.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Osteria Numero 2', 'Italia', 'Stradella', 'via Ghisiolo 2/a',
     45.18035, 10.8726,
     20.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Bib Gourmand | €
    ('Osteria La Solita Zuppa', 'Italia', 'Chiusi', 'via Porsenna 21',
     43.0159011, 11.9468611,
     20.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Bib Gourmand | €
    ('Osteria Zanchetti', 'Italia', 'Fossombrone', 'via Cesare Battisti 1',
     43.68966, 12.80761,
     20.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Trattoria Ai Due Platani', 'Italia', 'Coloreto', 'via Budellungo 104/a',
     44.7687333, 10.3794418,
     20.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Bib Gourmand | €
    ('Dell''Alba', 'Italia', 'Piadena', 'via del Popolo 31',
     45.1267675, 10.3813957,
     20.0, FALSE, FALSE,
     'Lombardian', 'marco_f'),
    -- Bib Gourmand | €€
    ('Italia', 'Italia', 'Quarona', 'piazza della Libertà 27',
     45.761494, 8.270105,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Osteria dal Moro', 'Italia', 'Giulianova Lido', 'lungomare Spalato 74',
     42.7482346, 13.9734163,
     20.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'anna_m'),
    -- Bib Gourmand | €
    ('Osteria Veglio', 'Italia', 'La Morra', 'frazione Annunziata 9',
     44.6372141, 7.9552187,
     20.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €
    ('Osteria Madonnetta', 'Italia', 'Marostica', 'via Vajenti 21',
     45.7449571, 11.6543603,
     20.0, FALSE, FALSE,
     'Tradizionale', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Buatta Cucina Popolana', 'Italia', 'Palermo', 'via Vittorio Emanuele 176',
     38.1171388, 13.3651296,
     20.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- Bib Gourmand | €€
    ('Antica Trattoria Cattivelli', 'Italia', 'Monticelli d''Ongina', 'via Chiesa di Isola Serafini 2',
     45.0969567, 9.9062332,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Bib Gourmand | €€
    ('Contesto Alimentare', 'Italia', 'Torino', 'via Accademia Albertina 21/e',
     45.0623867, 7.6862316,
     45.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Andrea - Sapori Montani', 'Italia', 'Palazzolo Acreide', 'via Gabriele Judica 4',
     37.061565, 14.903214,
     45.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- Bib Gourmand | €€
    ('Burro & Alici', 'Italia', 'Marotta', 'lungomare Colombo 98',
     43.7665284, 13.1445936,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Bib Gourmand | €
    ('Roma', 'Italia', 'Montoggio', 'via Roma 15',
     44.5139886, 9.0436081,
     20.0, FALSE, FALSE,
     'Ligure', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Il Ciabot', 'Italia', 'Roletto', 'via Costa 7',
     44.9253944, 7.3305602,
     20.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Bib Gourmand | €€
    ('L''Oste Dispensa', 'Italia', 'Orbetello', 'strada provinciale Giannella 113',
     42.439444, 11.1686422,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Bib Gourmand | €
    ('Luna Rossa', 'Italia', 'Terranova di Pollino', 'via Marconi 18',
     39.9771853, 16.297476,
     20.0, FALSE, FALSE,
     'Cuisine from Basilicata', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Al Piave', 'Italia', 'Mariano del Friuli', 'via Cormons 6 - Fraz. Corona',
     45.921916, 13.4820929,
     20.0, FALSE, FALSE,
     'Friulian', 'anna_m'),
    -- Bib Gourmand | €
    ('Taverna dei Caldora', 'Italia', 'Pacentro', 'piazza Umberto I 13',
     42.0499185, 13.9908288,
     20.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'marco_f'),
    -- Bib Gourmand | €€
    ('13 Comuni', 'Italia', 'Velo Veronese', 'piazza della Vittoria 31',
     45.6053, 11.09587,
     45.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Upepidde', 'Italia', 'Ruvo di Puglia', 'vico Sant''Agnese 2',
     41.1147414, 16.4869587,
     20.0, FALSE, FALSE,
     'Apulian', 'anna_m'),
    -- Bib Gourmand | €
    ('Taverna del Grappolo Blu', 'Italia', 'Montalcino', 'scale di via Moglio 1',
     43.0589313, 11.4898268,
     20.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Bib Gourmand | €€
    ('Al Palazzon', 'Italia', 'Galliera Veneta', 'via Ca'' Onorai 2',
     45.6735429, 11.8115922,
     45.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Serendib', 'Italia', 'Milano', 'via Pontida 2',
     45.4798954, 9.1827996,
     20.0, FALSE, FALSE,
     'Indian', 'anna_m'),
    -- Bib Gourmand | €
    ('La Pineta (Sant''Anna)', 'Italia', 'Sant''Anna', 'piazzale Sant''Anna 6',
     44.4981324, 7.3086768,
     20.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €
    ('Stella', 'Italia', 'Casaglia', 'via dei Narcisi 47/a',
     43.1065133, 12.4316475,
     20.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Trattoria Lanzagallo', 'Italia', 'Gaibana', 'via Ravenna 1048',
     44.7540004, 11.6549983,
     20.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Bib Gourmand | €€
    ('Podere San Faustino', 'Italia', 'Fidenza', 'via San Faustino 33 (SS Emilia Nord)',
     44.8734471, 10.0340833,
     45.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Bib Gourmand | €€
    ('Osteria Borsò Gambrinus', 'Italia', 'San Polo di Piave', 'via Capitello 18',
     45.7968129, 12.3885415,
     45.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Osteria la Fefa', 'Italia', 'Finale Emilia', 'via Trento-Trieste 9/c',
     44.8331758, 11.2958166,
     45.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Bib Gourmand | €
    ('Antica Trattoria La Toppa', 'Italia', 'San Donato in Poggio', 'via del Giglio 43',
     43.5354466, 11.2344884,
     20.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Bib Gourmand | €
    ('Dalla Libera', 'Italia', 'Sernaglia della Battaglia', 'via Farra 52',
     45.8771069, 12.132017,
     20.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Apollonia', 'Italia', 'Nals', 'via Sant''Apollonia 3',
     46.529537, 11.186598,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Bib Gourmand | €€
    ('L''Acino', 'Italia', 'Torino', 'via San Domenico 2/a',
     45.0743527, 7.6817442,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €€
    ('QuarantunoDodici', 'Italia', 'Fiumicino', 'via Monte Cadria 127',
     41.747772, 12.257375,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Boivin', 'Italia', 'Levico Terme', 'via Garibaldi 9',
     46.0113105, 11.3003374,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Bib Gourmand | €
    ('Rosselli 77', 'Italia', 'Cuorgnè', 'via F.lli Rosselli 77',
     45.3993734, 7.649048,
     20.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €€
    ('Krone', 'Italia', 'Aldino', 'piazza Principale 4',
     46.3684718, 11.3543035,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Buscone', 'Italia', 'Varzi', 'Località Bosmenso Superiore 41',
     44.7909875, 9.2208794,
     20.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Bib Gourmand | €€
    ('Antica Locanda al Cervo - Landgasthof zum Hirschen', 'Italia', 'San Genesio Atesino', 'via Schrann 9/c',
     46.5340136, 11.333222,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Bib Gourmand | €€
    ('Veneziano', 'Italia', 'Randazzo', 'Contrada Arena',
     37.8763419, 14.9713893,
     45.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Terme', 'Italia', 'Pigna', 'via Madonna Assunta',
     43.92997, 7.66846,
     20.0, FALSE, FALSE,
     'Ligure', 'anna_m'),
    -- Bib Gourmand | €
    ('Al Bersagliere', 'Italia', 'Verona', 'via Dietro Pallone 1',
     45.4363246, 10.9980274,
     20.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Bib Gourmand | €€
    ('Trattoria da Paeto', 'Italia', 'Pianiga', 'via Patriarcato 78',
     45.4693768, 12.0355686,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Il Sogno', 'Italia', 'Vetrego', 'via Vetrego 8',
     45.4663112, 12.0995204,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Bib Gourmand | €€
    ('Tre Noghere', 'Italia', 'Bigolino', 'via Crede 1',
     45.8749598, 12.0169773,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Bib Gourmand | €
    ('Da Giannino - L''Angolo d''Abruzzo', 'Italia', 'Milano', 'via Rosolino Pilo 20',
     45.47267, 9.21326,
     20.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Domenico dal 1968', 'Italia', 'Roma', 'via Satrico 23',
     41.8771101, 12.5075051,
     20.0, FALSE, FALSE,
     'Roman', 'anna_m'),
    -- Bib Gourmand | €€
    ('La Campanara', 'Italia', 'Galeata', 'via Borgo  Pianetto 24/a',
     43.98591, 11.90544,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Bib Gourmand | €€
    ('La Brinca', 'Italia', 'Ne', 'via Campo di Ne 58',
     44.35394, 9.38928,
     45.0, FALSE, FALSE,
     'Ligure', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Sa Mandra', 'Italia', 'Alghero', 'SP 44',
     40.6532916, 8.2975279,
     45.0, FALSE, FALSE,
     'Sarda', 'anna_m'),
    -- Bib Gourmand | €
    ('Borgo Spoltino', 'Italia', 'Mosciano Sant''Angelo', 'strada Selva Alta',
     42.71786, 13.87886,
     20.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'marco_f'),
    -- Bib Gourmand | €€
    ('Zeb', 'Italia', 'Firenze', 'via San Miniato 2r',
     43.7641016, 11.2612484,
     45.0, FALSE, FALSE,
     'Km Zero', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Trattoria alla Ruota', 'Italia', 'Negrar', 'via Proale 6',
     45.5696135, 10.9565875,
     45.0, FALSE, FALSE,
     'Veneziana', 'anna_m'),
    -- Bib Gourmand | €
    ('Da Burde', 'Italia', 'Firenze', 'via Pistoiese 154',
     43.7940341, 11.1858068,
     20.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Bib Gourmand | €€
    ('Locanda del Capitano & Tipico Osteria', 'Italia', 'Montone', 'via Roma 7',
     43.3635471, 12.3268121,
     45.0, FALSE, FALSE,
     'Umbrian', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Da Marchesi', 'Italia', 'Novafeltria', 'Località Ca'' Gianessi 7',
     43.8990174, 12.2584081,
     20.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Bib Gourmand | €€
    ('Le Antiche Sere', 'Italia', 'Lesina', 'via P. Micca 22',
     41.8653128, 15.3543138,
     45.0, FALSE, FALSE,
     'Apulian', 'marco_f'),
    -- Bib Gourmand | €€
    ('Osteria Piazzetta Cattedrale', 'Italia', 'Ostuni', 'largo Arcidiacono Trinchera 7',
     40.732876, 17.578646,
     45.0, FALSE, FALSE,
     'Apulian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Antica Osteria', 'Italia', 'Ossana', 'via Venezia 11',
     46.3072076, 10.7370371,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Bib Gourmand | €€
    ('La Cuccagna - Giro di Vite', 'Italia', 'Crispiano', 'corso Umberto I 168',
     40.6040474, 17.2287707,
     45.0, FALSE, FALSE,
     'Apulian', 'marco_f'),
    -- Bib Gourmand | €
    ('Vecchio Porto', 'Italia', 'Villa San Giovanni', 'lungomare Cenide 55',
     38.2306377, 15.6360812,
     20.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Le Miniere', 'Italia', 'Traversella', 'piazza Martiri 1944 4',
     45.5091669, 7.7508274,
     20.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Bib Gourmand | €
    ('Locanda Cacciatori', 'Italia', 'Ponte dell''Olio', 'località Mistadello di Castione',
     44.8567768, 9.661281,
     20.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Bib Gourmand | €
    ('Osteria della Villetta', 'Italia', 'Palazzolo sull''Oglio', 'via Marconi 104',
     45.6026934, 9.8940244,
     20.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Green T.', 'Italia', 'Roma', 'via del Piè di Marmo 28',
     41.8976162, 12.4792745,
     45.0, FALSE, FALSE,
     'Chinese', 'anna_m'),
    -- Bib Gourmand | €
    ('Da Flavio e Fabrizio "Al Teatro"', 'Italia', 'Mirano', 'via della Vittoria 75',
     45.4908586, 12.1151308,
     20.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Bib Gourmand | €
    ('Trattoria Cibrèo - Il Cibrèino', 'Italia', 'Firenze', 'via dei Macci 122 r',
     43.7712441, 11.2664192,
     20.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €
    ('La Chioccia d''Oro', 'Italia', 'Vallo della Lucania', 'via Novi 2',
     40.2183495, 15.279882,
     20.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Bib Gourmand | €
    ('Cantina dei Cacciatori', 'Italia', 'Monteu Roero', 'località Villa Superiore 59',
     44.7849738, 7.9080622,
     20.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €€
    ('Lo Stuzzichino', 'Italia', 'Sant''Agata sui Due Golfi', 'via Deserto 1/a',
     40.6087388, 14.3723456,
     45.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Taverna 58', 'Italia', 'Pescara', 'corso Manthoné 46',
     42.46174, 14.213223,
     20.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'anna_m'),
    -- Bib Gourmand | €€
    ('Il Conte Matto', 'Italia', 'Trequanda', 'via Taverne 40',
     43.1882927, 11.6666371,
     45.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Bib Gourmand | €
    ('Villa Aurora', 'Italia', 'Soiano del Lago', 'via Ciucani 1/7',
     45.53681, 10.51391,
     20.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Bib Gourmand | €
    ('La Fratanza', 'Italia', 'Nocera Superiore', 'via Garibaldi 37',
     40.7495611, 14.6791142,
     20.0, FALSE, FALSE,
     'Campanian', 'anna_m'),
    -- Bib Gourmand | €€
    ('Bruxaboschi', 'Italia', 'San Desiderio', 'via Francesco Mignone 8',
     44.4231154, 9.0131106,
     45.0, FALSE, FALSE,
     'Ligure', 'marco_f'),
    -- Bib Gourmand | €
    ('Trattoria da Fagiolino', 'Italia', 'Cutigliano', 'via Carega 1',
     44.1000069, 10.7544173,
     20.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Romani', 'Italia', 'Vicomero di Torrile', 'via dei Ronchi 2',
     44.8839459, 10.3150109,
     20.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Bib Gourmand | €
    ('La Cucoma', 'Italia', 'San Pancrazio', 'via Molinaccio 175',
     44.3563305, 12.0786464,
     20.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Bib Gourmand | €€
    ('Trippa', 'Italia', 'Milano', 'via Giorgio Vasari 1',
     45.45215, 9.205526,
     45.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Bib Gourmand | €
    ('L''800', 'Italia', 'Argelato', 'via Centese 33',
     44.6414004, 11.351547,
     20.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Bib Gourmand | €€
    ('Al Convento - Casa Torrente', 'Italia', 'Cetara', 'piazza San Francesco 16',
     40.6473603, 14.7008016,
     45.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Bib Gourmand | €
    ('Dongiò', 'Italia', 'Milano', 'via Corio 3',
     45.4517517, 9.205139,
     20.0, FALSE, FALSE,
     'Calabrian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Nana Piccolo Bistrò', 'Italia', 'Senigallia', 'via Giosuè Carducci 19',
     43.7168212, 13.2173321,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Bib Gourmand | €
    ('Hosteria Grappolo d''Oro', 'Italia', 'Roma', 'piazza della Cancelleria 80',
     41.8964575, 12.4721342,
     20.0, FALSE, FALSE,
     'Roman', 'marco_f'),
    -- Bib Gourmand | €€
    ('Locanda da Condo', 'Italia', 'Col San Martino', 'via Fontana 134',
     45.8969808, 12.0885489,
     45.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Alla Pace', 'Italia', 'Sauris', 'via Sauris di Sotto 38',
     46.4644403, 12.7092398,
     20.0, FALSE, FALSE,
     'Friulian', 'anna_m'),
    -- Bib Gourmand | €
    ('La Torre (Santa Maria Annunziata)', 'Italia', 'Santa Maria Annunziata', 'piazza Annunziata 7',
     40.6026363, 14.3348199,
     20.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Bib Gourmand | €€
    ('Il Latini', 'Italia', 'Firenze', 'via dei Palchetti 6 r',
     43.7716399, 11.2493124,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Lokanda Devetak', 'Italia', 'Savogna d''Isonzo', 'via Brezici 22',
     45.8835521, 13.5603995,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Bib Gourmand | €
    ('Antiche Sere', 'Italia', 'Torino', 'via Cenischia 9',
     45.0706512, 7.6429555,
     20.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €€
    ('Forma', 'Italia', 'Civitavecchia', 'via Trieste 9',
     42.0930835, 11.7917883,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Maso Palù', 'Italia', 'Brentonico', 'via Graziani 56',
     45.8126772, 10.9400019,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Bib Gourmand | €€
    ('Coxinendi', 'Italia', 'Sanluri', 'via Sant''Antioco 1',
     39.5638514, 8.8975961,
     45.0, FALSE, FALSE,
     'Sarda', 'marco_f'),
    -- Bib Gourmand | €€
    ('Agritur El Mas', 'Italia', 'Moena', 'strada de Saslonch 176',
     46.3835301, 11.6620468,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Il Sottomarino', 'Italia', 'Follonica', 'via Fratti 1',
     42.9219683, 10.7550868,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Bib Gourmand | €
    ('Edelweiss', 'Italia', 'Viceno', 'Località Crodo',
     46.22896, 8.307799,
     20.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Bib Gourmand | €€
    ('Antica Locanda di Sesto', 'Italia', 'Ponte a Moriano', 'via Ludovica 1660',
     43.9242922, 10.5267264,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Caffè Grande', 'Italia', 'Rivergaro', 'piazza Paolo 9',
     44.9099838, 9.5966921,
     45.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Bib Gourmand | €
    ('La Baita', 'Italia', 'Faenza', 'via Naviglio 25/c',
     44.2883532, 11.8833579,
     20.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Bib Gourmand | €
    ('Osteria Storica Morelli', 'Italia', 'Pergine Valsugana', 'piazza Petrini 1',
     46.0778797, 11.2777948,
     20.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Locanda del Colonnello', 'Italia', 'Modica', 'via Blandini 9',
     36.8653243, 14.7637418,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Bib Gourmand | €
    ('Vecchia Lama', 'Italia', 'Lama Mocogno', 'via XXIV Maggio 24',
     44.30972, 10.73058,
     20.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Bib Gourmand | €€
    ('Dei Cantoni', 'Italia', 'Longiano', 'via Santa Maria 19',
     44.0717345, 12.3230214,
     45.0, FALSE, FALSE,
     'Cuisine from Romagna', 'riccardo_r'),
    -- Bib Gourmand | €
    ('La Bucaccia', 'Italia', 'Cortona', 'via Ghibellina 17',
     43.2749644, 11.9841148,
     20.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Bib Gourmand | €€
    ('Osteria dei Segreti', 'Italia', 'Appignano', 'via Verdefiore 25',
     43.3777307, 13.373358,
     45.0, FALSE, FALSE,
     'Cuisine from the Marches', 'marco_f'),
    -- Bib Gourmand | €€
    ('Dalie e Fagioli', 'Italia', 'Manerba del Garda', 'via Campagnola 45',
     45.55678, 10.53301,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Lerchner''s In Runggen', 'Italia', 'Saint Lorenzen', 'frazione Ronchi 3/a',
     46.7778091, 11.8759349,
     45.0, FALSE, FALSE,
     'South Tyrolean', 'anna_m'),
    -- Bib Gourmand | €
    ('La Dispensa di Armatore', 'Italia', 'Cetara', 'via Cantone 1',
     40.6461666, 14.7015346,
     20.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Bib Gourmand | €
    ('Da Giocondo', 'Italia', 'Rivisondoli', 'via Suffragio 2',
     41.8691706, 14.0654103,
     20.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Nuova Roma', 'Italia', 'Sasso Marconi', 'via Olivetta 87',
     44.44594, 11.19808,
     20.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Bib Gourmand | €
    ('Osteria del Gallo e della Volpe', 'Italia', 'Ospedaletto d''Alpinolo', 'piazza Umberto I 14',
     40.9390634, 14.7442689,
     20.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Bib Gourmand | €€
    ('Impero', 'Italia', 'Sizzano', 'via Roma 13',
     45.57613, 8.43808,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €
    ('La Locanda del Falco', 'Italia', 'Valdieri', 'piazza Regina Elena 22',
     44.277054, 7.3974799,
     20.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Bib Gourmand | €€
    ('Il Casolare dei Segreti', 'Italia', 'Treia', 'Contrada San Lorenzo 28',
     43.31765, 13.2572,
     45.0, FALSE, FALSE,
     'Cuisine from the Marches', 'marco_f'),
    -- Bib Gourmand | €€
    ('Osteria Platzegg', 'Italia', 'San Michele', 'piazza Municipio 1',
     46.4553442, 11.2588872,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Salotto sul Mare', 'Italia', 'Terrasini', 'via dei Mille 2/b',
     38.1555588, 13.0812438,
     20.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- Bib Gourmand | €€
    ('Moi', 'Italia', 'Roma', 'via Antonio Serra 15',
     41.9436379, 12.4723346,
     45.0, FALSE, FALSE,
     'Seasonal Cuisine', 'marco_f'),
    -- Bib Gourmand | €€
    ('Trattoria della Fortuna', 'Italia', 'Monterotondo', 'Via Salaria 57',
     42.08187, 12.6075,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Ronchi Rò', 'Italia', 'Dolegna del Collio', 'località Cime di Dolegna 12',
     46.0236284, 13.4866949,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Bib Gourmand | €€
    ('I Tri Siochètt', 'Italia', 'Parma', 'strada Farnese 74',
     44.7745082, 10.2995451,
     45.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Bib Gourmand | €€
    ('Rimulas', 'Italia', 'Voghera', 'via Severino Grattoni 8',
     44.99336, 9.00868,
     45.0, FALSE, FALSE,
     'Innovativa', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Ca'' d''Frara', 'Italia', 'Ferrara', 'via del Gambero 4',
     44.8363193, 11.6226073,
     45.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Bib Gourmand | €€
    ('ChiaroScuro', 'Italia', 'Cagliari', 'corso Vittorio Emanuele II 380',
     39.2206741, 9.107065,
     45.0, FALSE, FALSE,
     'Sarda', 'marco_f'),
    -- Bib Gourmand | €€
    ('Mezzolitro Vini e Cucina', 'Italia', 'Rho', 'via Pomè 10',
     45.5294549, 9.042316,
     45.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Da Sapì', 'Italia', 'Esine', 'via Giuseppe Mazzini 36',
     45.9231937, 10.2577142,
     45.0, FALSE, FALSE,
     'Lombardian', 'anna_m'),
    -- Bib Gourmand | €
    ('Locanda delle Grazie', 'Italia', 'Grazie', 'via San Pio X 2',
     45.1564689, 10.6940175,
     20.0, FALSE, FALSE,
     'Mantuan', 'marco_f'),
    -- Bib Gourmand | €€
    ('L''Ortone', 'Italia', 'Firenze', 'piazza Lorenzo Ghiberti 87 r',
     43.770681, 11.2674875,
     45.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Bib Gourmand | €
    ('L''Osteria della Trippa', 'Italia', 'Roma', 'via Goffredo Mameli 15',
     41.8864511, 12.4681489,
     20.0, FALSE, FALSE,
     'Cuisine from Lazio', 'anna_m'),
    -- Bib Gourmand | €€
    ('Su Gologone', 'Italia', 'Oliena', 'Località Su Gologone',
     40.2899704, 9.4893376,
     45.0, FALSE, FALSE,
     'Sarda', 'marco_f'),
    -- Bib Gourmand | €€
    ('Vecchia Marina', 'Italia', 'Roseto degli Abruzzi', 'lungomare Trento 37',
     42.6829548, 14.0128495,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Bib Gourmand | €
    ('La Locanda Gesù Vecchio', 'Italia', 'Napoli', 'via Giovanni Paladino 26',
     40.8471624, 14.2571093,
     20.0, FALSE, FALSE,
     'Campanian', 'anna_m'),
    -- Bib Gourmand | €€
    ('Consorzio', 'Italia', 'Torino', 'via Monte di Pietà 23',
     45.0714727, 7.6792853,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €€
    ('Magazzino 52', 'Italia', 'Torino', 'via Giolitti 52/a',
     45.0631342, 7.6928608,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Oishi', 'Italia', 'Teramo', 'via Mario Capuani 47',
     42.660254, 13.70142,
     45.0, FALSE, FALSE,
     'Giapponese', 'anna_m'),
    -- Bib Gourmand | €
    ('Nerina', 'Italia', 'Romeno', 'via De Gasperi 31',
     46.3805699, 11.0936457,
     20.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Bib Gourmand | €€
    ('Ostaria Tyrol', 'Italia', 'Moena', 'piaz de Ramon 8',
     46.3763283, 11.6611421,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Estrò', 'Italia', 'Pescara', 'piazza della Rinascita 23',
     42.472985, 14.2096135,
     20.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Bib Gourmand | €€
    ('Bros'' Trattoria', 'Italia', 'Scorrano', 'SP Scorrano-Supersano km 2',
     40.081012, 18.2906161,
     45.0, FALSE, FALSE,
     'Tradizionale', 'marco_f'),
    -- Bib Gourmand | €€
    ('Gerani', 'Italia', 'Sant''Antonio Abate', 'via Stabia 609',
     40.7115883, 14.5267472,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Podere 39', 'Italia', 'Firenze', 'via Senese 39 r',
     43.7596765, 11.2415318,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Bib Gourmand | €€
    ('Da Gregorio', 'Italia', 'Morrano Nuovo', 'strada provinciale 101 136',
     42.7755985, 12.1374042,
     45.0, FALSE, FALSE,
     'Umbrian', 'marco_f'),
    -- Bib Gourmand | €€
    ('Coquus', 'Italia', 'Lucera', 'via Luigi Blanch 19',
     41.5074929, 15.3337179,
     45.0, FALSE, FALSE,
     'Tradizionale', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Rosmarino', 'Italia', 'Genova', 'salita del Fondaco 30',
     44.4077059, 8.9333786,
     45.0, FALSE, FALSE,
     'Ligure', 'anna_m'),
    -- Bib Gourmand | €€
    ('Locanda Pincelli', 'Italia', 'Selva Malvezzi', 'via Selva 52',
     44.5560412, 11.6275097,
     45.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Bib Gourmand | €
    ('Cibus', 'Italia', 'Ceglie Messapica', 'via Chianche di Scarano 7',
     40.6465, 17.51819,
     20.0, FALSE, FALSE,
     'Apulian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Trattoria al Parco', 'Italia', 'Buttrio', 'via Stretta 7',
     46.0153555, 13.3319275,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Bib Gourmand | €€
    ('Al Monastero', 'Italia', 'Cividale del Friuli', 'via Ristori 9',
     46.094402, 13.43016,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Bib Gourmand | €€
    ('Al Cambio', 'Italia', 'Bologna', 'via Stalingrado 150',
     44.5286983, 11.3668281,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Osteria Mondo d''Oro', 'Italia', 'Verona', 'via Mondo d''Oro 4',
     45.442, 10.99624,
     20.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Bib Gourmand | €€
    ('Trattoria da Zamboni', 'Italia', 'Lapio', 'via Santa Croce 73',
     45.47452, 11.5357,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Bib Gourmand | €
    ('Trattoria di Via Serra', 'Italia', 'Bologna', 'via Luigi Serra 9/b',
     44.5097151, 11.3455818,
     20.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Il Ritrovo d''Abruzzo', 'Italia', 'Civitella Casanova', 'Contrada Bosco 16',
     42.4057508, 13.8937726,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Bib Gourmand | €
    ('Me Cumpari Turiddu', 'Italia', 'Catania', 'piazza Turi Ferro 36',
     37.506428, 15.088407,
     20.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- Bib Gourmand | €
    ('Antica Filanda', 'Italia', 'Capri Leone', 'SS 157',
     38.08595, 14.734261,
     20.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Vögele', 'Italia', 'Bolzano', 'via Goethe 3',
     46.4991536, 11.3524552,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Bib Gourmand | €
    ('Antica Fattoria del Grottaione', 'Italia', 'Castel del Piano', 'via della Piazza',
     42.94457, 11.47218,
     20.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Bib Gourmand | €
    ('La Pignata', 'Italia', 'Ariano Irpino', 'viale Dei Tigli 7',
     41.1569021, 15.0947663,
     20.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Il Turacciolo', 'Italia', 'Andria', 'piazza Vittorio Emanuele II 4',
     41.2259495, 16.2967455,
     45.0, FALSE, FALSE,
     'Apulian', 'anna_m'),
    -- Bib Gourmand | €€
    ('Da Felice', 'Italia', 'Chiavari', 'corso Valparaiso 136',
     44.3177211, 9.3145164,
     45.0, FALSE, FALSE,
     'Ligure', 'marco_f'),
    -- Bib Gourmand | €
    ('Trattoria da Probo', 'Italia', 'Bagnolo in Piano', 'via Provinciale Nord 13',
     44.7792987, 10.6762816,
     20.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Alla Pasina', 'Italia', 'Dosson', 'via Marie 3',
     45.6289367, 12.2558509,
     45.0, FALSE, FALSE,
     'Veneziana', 'anna_m'),
    -- Bib Gourmand | €
    ('CUCINA.eat', 'Italia', 'Cagliari', 'piazza Galileo Galilei 1',
     39.2202729, 9.1223668,
     20.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Bib Gourmand | €
    ('L''Acquario', 'Italia', 'Castiglione del Lago', 'via Vittorio Emanuele 69',
     43.1271351, 12.0518678,
     20.0, FALSE, FALSE,
     'Umbrian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Nole', 'Italia', 'Pescara', 'viale Regina Margherita 84',
     42.4752574, 14.2057343,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Bib Gourmand | €€
    ('L''Ostreria Fratelli Pavesi', 'Italia', 'Podenzano', 'Località Gariga 8',
     44.9839886, 9.6846769,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Bib Gourmand | €€
    ('Osteria dei Maltagliati', 'Italia', 'Torano Nuovo', 'corso Umberto I 36',
     42.8231884, 13.7770664,
     45.0, FALSE, FALSE,
     'Km Zero', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Osteria Ophis', 'Italia', 'Offida', 'corso Serpente Aureo 54/b',
     42.9355659, 13.6924987,
     45.0, FALSE, FALSE,
     'Cuisine from the Marches', 'anna_m'),
    -- Bib Gourmand | €€
    ('Al Borgo', 'Italia', 'Belluno', 'via Anconetta 8',
     46.1250037, 12.2116816,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €
    ('Gassenwirt', 'Italia', 'Kiens', 'via Paese 42',
     46.8092067, 11.8414858,
     20.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('La Gioconda', 'Italia', 'Cagli', 'via Brancuti',
     43.5461484, 12.6474362,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Bib Gourmand | €€
    ('Osteria La Torre', 'Italia', 'Cherasco', 'via dell''Ospedale 22',
     44.6516877, 7.8570495,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €
    ('Perbacco - Vini e Cucina', 'Italia', 'Cannara', 'via Umberto I 14',
     42.9952602, 12.5831366,
     20.0, FALSE, FALSE,
     'Umbrian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Trattoria Porteri', 'Italia', 'Brescia', 'via Trento 52/d',
     45.5536988, 10.2245503,
     45.0, FALSE, FALSE,
     'Lombardian', 'anna_m'),
    -- Bib Gourmand | €€
    ('Al Becco della Civetta', 'Italia', 'Castelmezzano', 'vico I Maglietta 7',
     40.52995, 16.0463,
     45.0, FALSE, FALSE,
     'Cuisine from Basilicata', 'marco_f'),
    -- Bib Gourmand | €€
    ('Da Fausto (Cavatore)', 'Italia', 'Cavatore', 'Località Valle Prati 1',
     44.63598, 8.44158,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Borgo Poscolle', 'Italia', 'Cavazzo Carnico', 'via Poscolle 21/a',
     46.36565, 13.03708,
     20.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Bib Gourmand | €
    ('Castagneto', 'Italia', 'Montrigiasco', 'via Vignola 14',
     45.7723788, 8.5095252,
     20.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €
    ('Osteria San Giulio', 'Italia', 'Badia di Dulzago', 'Via Dulzago',
     45.55002, 8.6128,
     20.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Kanton Restaurant', 'Italia', 'Capriate San Gervasio', 'via Antonio Gramsci 17',
     45.6111527, 9.5270194,
     45.0, FALSE, FALSE,
     'Chinese', 'anna_m'),
    -- Bib Gourmand | €€
    ('Sot''Ajarchi', 'Italia', 'Ancona', 'via Marconi 93',
     43.6109808, 13.5037316,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Bib Gourmand | €
    ('Il Moro', 'Italia', 'Capriata d''Orba', 'piazza Garibaldi 7',
     44.7299093, 8.6888831,
     20.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Osteria Bartolini (Milano Marittima)', 'Italia', 'Milano Marittima', 'via A. Boito 26',
     44.2683552, 12.3569147,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Bib Gourmand | €€
    ('Accademia Ristorante', 'Italia', 'Casale Monferrato', 'via Mameli 29',
     45.1364114, 8.4533279,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Bib Gourmand | €
    ('Trattoria Pennestri', 'Italia', 'Roma', 'via Giovanni Da Empoli 5',
     41.8734074, 12.479981,
     20.0, FALSE, FALSE,
     'Cuisine from Lazio', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Caffè La Crepa', 'Italia', 'Isola Dovarese', 'piazza Matteotti 14',
     45.1760808, 10.3123463,
     45.0, FALSE, FALSE,
     'Lombardian', 'anna_m'),
    -- Bib Gourmand | €€
    ('Osteria Bartolini (Cesenatico)', 'Italia', 'Cesenatico', 'corso Garibaldi 41',
     44.200314, 12.396823,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Bib Gourmand | €
    ('Dentella', 'Italia', 'Bracca', 'via Dentella 25',
     45.8233063, 9.7074027,
     20.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Gabbiano 1983', 'Italia', 'Corte de'' Cortesi', 'piazza Vittorio Veneto 10',
     45.2724821, 10.0072355,
     45.0, FALSE, FALSE,
     'Lombardian', 'anna_m'),
    -- Bib Gourmand | €€
    ('Futura Osteria', 'Italia', 'Monteriggioni', 'largo Garfonda 10',
     43.3872152, 11.1951088,
     45.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Bib Gourmand | €€
    ('Ahimè', 'Italia', 'Bologna', 'via San Gervasio 6/e',
     44.4966213, 11.337969,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Vascello d''Oro', 'Italia', 'Carrù', 'via San Giuseppe 9',
     44.4796072, 7.8784919,
     20.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Bib Gourmand | €
    ('Romanè', 'Italia', 'Roma', 'via Cipro 106',
     41.9058387, 12.4451294,
     20.0, FALSE, FALSE,
     'Roman', 'marco_f'),
    -- Bib Gourmand | €
    ('Osteria del Miglio 2.10', 'Italia', 'Pieve San Giacomo', 'via Patrioti 2',
     45.13071, 10.18788,
     20.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Osteria Bartolini (Bologna)', 'Italia', 'Bologna', 'piazza Malpighi 16',
     44.4939376, 11.3364404,
     20.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Bib Gourmand | €€
    ('Antichi Sapori', 'Italia', 'Montegrosso', 'piazza Sant''Isidoro 10',
     41.1722059, 16.1411084,
     45.0, FALSE, FALSE,
     'Apulian', 'marco_f'),
    -- Bib Gourmand | €
    ('Da Alighiero', 'Italia', 'Anghiari', 'via Garibaldi 8',
     43.54092, 12.05549,
     20.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Namo Ristobottega', 'Italia', 'Tarquinia', 'via Giovan Battista Marzi 1',
     42.2506881, 11.7555304,
     45.0, FALSE, FALSE,
     'Seasonal Cuisine', 'anna_m'),
    -- Bib Gourmand | €€
    ('Laghetto', 'Italia', 'Brusson', 'rue Trois Villages 291',
     45.7609563, 7.7213729,
     45.0, FALSE, FALSE,
     'Cuisine from the Aosta Valley', 'marco_f'),
    -- Bib Gourmand | €
    ('Magnagallo', 'Italia', 'Campogalliano', 'via Magnagallo Est 7',
     44.6775617, 10.8520227,
     20.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Aciniello', 'Italia', 'Campobasso', 'via Torino 4',
     41.5597957, 14.6568008,
     20.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Bib Gourmand | €
    ('Osteria Expanificio', 'Italia', 'Agrigento', 'piazza Sinatra 16',
     37.3108491, 13.5763684,
     20.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- Bib Gourmand | €
    ('Osteria Magona', 'Italia', 'Bolgheri', 'Località Vallone dei Messi 199',
     43.2187801, 10.611003,
     20.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Locanda del Barone', 'Italia', 'Caramanico Terme', 'Località San Vittorino',
     42.1438896, 13.9941646,
     20.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'anna_m'),
    -- Bib Gourmand | €€
    ('Ai Burattini', 'Italia', 'Adrara San Martino', 'via Madaschi 45',
     45.6996073, 9.9481459,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Bib Gourmand | €€
    ('Locanda Mariella', 'Italia', 'Calestano', 'località Fragnolo 29',
     44.586742, 10.158748,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('Da Pode', 'Italia', 'San Gimignano', 'località Sovestro 63',
     43.4598625, 11.0596245,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Bib Gourmand | €
    ('Battaglino', 'Italia', 'Bra', 'piazza Roma 18',
     44.6937542, 7.8505723,
     20.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €€
    ('Palazzaccio', 'Italia', 'Castelbuono', 'via Umberto I 23',
     37.9317244, 14.0876011,
     45.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Boccondivino', 'Italia', 'Bra', 'via Mendicità Istruita 14',
     44.6961302, 7.8551043,
     20.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Bib Gourmand | €€
    ('Il Fiorile', 'Italia', 'Borghetto di Borbera', 'via XXV Aprile 6',
     44.7240966, 8.9516851,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €€
    ('Osteria La Pimpinella', 'Italia', 'Bra', 'Via San Rocco 70',
     44.6960382, 7.84972,
     45.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Bib Gourmand | €
    ('Mingone', 'Italia', 'Carnello', 'via Pietro Nenni 96',
     41.6893365, 13.6051717,
     20.0, FALSE, FALSE,
     'Cuisine from Lazio', 'anna_m'),
    -- Bib Gourmand | €€
    ('Violetta', 'Italia', 'Calamandrana', 'via Valle San Giovanni 1',
     44.750548, 8.3211782,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Bib Gourmand | €
    ('Il Cedro', 'Italia', 'Moggiona', 'via di Camaldoli 20',
     43.7828245, 11.791567,
     20.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Bib Gourmand | €€
    ('53 Untitled', 'Italia', 'Roma', 'via del Monte della Farina 53',
     41.8951741, 12.4749758,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Bistrot di Pescheria', 'Italia', 'Salerno', 'Via Luigi Guercio 1',
     40.679042, 14.7754517,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Taverna delle Follie', 'Italia', 'Limatola', 'via San Biagio 30',
     41.145072, 14.3898563,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Fourghetti', 'Italia', 'Milano', 'Via Cardinale Ascanio Sforza 77',
     45.441999, 9.1759541,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Sunrise', 'Italia', 'Caserta', 'Corso Trieste 112',
     41.0718419, 14.3351555,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Mammà Isola di Capri', 'Italia', 'Torino', 'Corso Castelfidardo 22',
     45.0663362, 7.660934,
     85.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria Bar Sport', 'Italia', 'Casale Monferrato', 'Strada Alessandria in San Germano 85',
     45.09853, 8.45527,
     45.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Al Pozzo', 'Italia', 'Mason Vicentino', 'via Chiesa 10',
     45.7174776, 11.607205,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Acqua & Sale', 'Italia', 'Scafati', 'Via Monte Grappa 35',
     40.74979, 14.52523,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Donevandro', 'Italia', 'Popoli', 'via Garibaldi 2',
     42.1744979, 13.8329115,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Ape Vino e Cucina', 'Italia', 'Alba', 'Piazza Risorgimento 3',
     44.7005845, 8.0361169,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Da Bob Cook Fish', 'Italia', 'Sorrento', 'largo Parsano vecchio 16',
     40.6237252, 14.3705831,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('DA_MÓ', 'Italia', 'Matera', 'Via Bruno Buozzi 20',
     40.6624784, 16.6113483,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€
    ('Sa Domu Sarda', 'Italia', 'Cagliari', 'via Sassari 51',
     39.2169379, 9.1106285,
     45.0, FALSE, FALSE,
     'Sarda', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Charleston', 'Italia', 'Palermo', 'via Generale Magliocco 19',
     38.1215951, 13.3569428,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Alessandro Feo', 'Italia', 'Marina di Casal Velino', 'via Angelo Lista 24',
     40.1769364, 15.121255,
     45.0, FALSE, FALSE,
     'Campanian', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Dama', 'Italia', 'Cervesina', 'Via Mulino',
     45.0476388, 9.0062926,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Il Ristorante Alain Ducasse Napoli', 'Italia', 'Napoli', 'Via Cristoforo Colombo 45',
     40.840277, 14.2555922,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Palazzo Utini', 'Italia', 'Noceto', 'via Antonio Gramsci 6',
     44.8087145, 10.1788721,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Soul & Fish', 'Italia', 'Sorrento', 'via Marina Grande',
     40.6278681, 14.3644213,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Etra', 'Italia', 'Genova', 'piazza De Ferrari 4',
     44.4081985, 8.9341693,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('20Tre', 'Italia', 'Genova', 'via David Chiossone 20 r',
     44.4087805, 8.9331148,
     45.0, FALSE, FALSE,
     'Km Zero', 'anna_m'),
    -- Selected Restaurants | €€
    ('Ménage', 'Italia', 'Catania', 'Via Euplio Reina 13',
     37.5040814, 15.088074,
     45.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Procaccini', 'Italia', 'Milano', 'Via Giulio Cesare Procaccini 33',
     45.4837396, 9.1708465,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Metodo', 'Italia', 'Marne', 'via Vittorio Emanuele 9',
     45.6223626, 9.5583004,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Gimmy''s', 'Italia', 'Aprica', 'via privata Gemelli',
     46.1535475, 10.1517783,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Locanda Perbellini - Ai Beati', 'Italia', 'Garda', 'via Val Mora 57',
     45.5861354, 10.7174171,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Salvo', 'Italia', 'Napoli', 'Riviera di Chiaia 271',
     40.8333521, 14.2390896,
     20.0, FALSE, FALSE,
     'Pizza', 'anna_m'),
    -- Selected Restaurants | €€
    ('Innesti', 'Italia', 'Pergine Valsugana', 'via San Pietro 8',
     46.062605, 11.2442274,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria del Teatro', 'Italia', 'Cortona', 'via Maffei 2',
     43.2758393, 11.9857699,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Anticofurlo', 'Italia', 'Acqualagna', 'via Furlo 60',
     43.6384218, 12.7132896,
     45.0, FALSE, FALSE,
     'Cuisine from the Marches', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Lupaia', 'Italia', 'Torrita di Siena', 'Località Lupaia 74',
     43.1135781, 11.7501772,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Joca', 'Italia', 'Napoli', 'vico Sospiri 10/C',
     40.8344895, 14.2402287,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Nobuya', 'Italia', 'Milano', 'Via San Nicolao 3/a',
     45.46655, 9.17683,
     85.0, FALSE, FALSE,
     'Japanese Contemporary', 'anna_m'),
    -- Selected Restaurants | €€
    ('Almondo Trattoria', 'Italia', 'Torino', 'piazza Gran Madre di Dio 2/l',
     45.0622509, 7.6985608,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Truth Restaurant', 'Italia', 'Aversa', 'via Fratelli Cervi 18',
     40.960971, 14.2101034,
     45.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Piraña', 'Italia', 'Prato', 'via G. Valentini 110',
     43.8678128, 11.0966872,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Abba', 'Italia', 'Milano', 'via Varesina 177',
     45.5035705, 9.1383706,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Caruso Roof Garden', 'Italia', 'Napoli', 'via Partenope 45',
     40.8299478, 14.248257,
     160.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Tartufo', 'Italia', 'Revere', 'via Guido Rossa 13',
     45.0468267, 11.1275709,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Quintilio', 'Italia', 'Altare', 'via Gramsci 23',
     44.3327751, 8.3468894,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Credenziere', 'Italia', 'Annone Veneto', 'via Quattro Strade 12',
     45.7923516, 12.6932484,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Hostaria del Pavone', 'Italia', 'Vasto', 'via Barbarotta 15/17',
     42.1124503, 14.710181,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Trattoria la Rosa 1908', 'Italia', 'Sant''Agostino', 'via del Bosco 2',
     44.7905538, 11.380013,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Frosch Restaurant', 'Italia', 'Varena', 'via Santi Pietro e Paolo 1',
     46.3021115, 11.4565016,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Exé Restaurant', 'Italia', 'Fiorano Modenese', 'via Circondariale San Francesco 2',
     44.5378933, 10.824509,
     45.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Carusu', 'Italia', 'Agrigento', 'passeggiata Archeologica 8',
     37.2983958, 13.5908391,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Amo Bistrot', 'Italia', 'Verona', 'Vicoletto Due Mori 5',
     45.4446546, 10.9983826,
     45.0, FALSE, FALSE,
     'Fusion', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Evo Ristorante', 'Italia', 'Alberobello', 'via Giovanni XXIII 1',
     40.7875418, 17.2350877,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€€
    ('San Baylon', 'Italia', 'Roma', 'via di Ripetta 232',
     41.9084995, 12.4759096,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Locanda Toscano', 'Italia', 'Pizzo', 'via Benedetto Musolino 22',
     38.7359154, 16.1603839,
     45.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('L’Alchimia', 'Italia', 'Milano', 'viale Premuda 34',
     45.4657917, 9.2074636,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Io e Luna', 'Italia', 'Guarene', 'località Montebello 1',
     44.7450662, 8.0147385,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Giurista', 'Italia', 'Perugia', 'via Bartolo 30',
     43.113269, 12.38963,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Bar à Fromage', 'Italia', 'Cogne', 'rue Grand Paradis 20',
     45.6066147, 7.3558181,
     45.0, FALSE, FALSE,
     'Cuisine from the Aosta Valley', 'anna_m'),
    -- Selected Restaurants | €€
    ('Da Cesare', 'Italia', 'Roma', 'via Crescenzio 13',
     41.9053961, 12.467996,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('Forte del 48', 'Italia', 'San Donà di Piave', 'Via Carlo Vizzotto 1',
     45.630695, 12.575539,
     45.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Bistrot 64', 'Italia', 'Roma', 'via Guglielmo Calderini 64',
     41.93046, 12.466211,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Lazaroun', 'Italia', 'Santarcangelo di Romagna', 'via Del Platano 21',
     44.0640548, 12.4443162,
     45.0, FALSE, FALSE,
     'Cuisine from Romagna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Degusteria Italiana', 'Italia', 'Firenze', 'via Lambertesca 7r',
     43.7686986, 11.2547137,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Cavallino (Tortona)', 'Italia', 'Tortona', 'corso Romita 83',
     44.9005761, 8.8663602,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Il Giardino del Gusto', 'Italia', 'Ventimiglia', 'piazza XX Settembre 6/c',
     43.7922524, 7.6085164,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Derby Grill', 'Italia', 'Monza', 'viale Cesare Battisti 1',
     45.5927421, 9.2713814,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Foresta (Marina di Pisa)', 'Italia', 'Marina di Pisa', 'via Litoranea 2',
     43.6591027, 10.2798543,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Taverna Rovita', 'Italia', 'Maratea', 'via Rovita 13',
     39.9924293, 15.7218212,
     45.0, FALSE, FALSE,
     'Cuisine from Basilicata', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Vitello d''Oro', 'Italia', 'Udine', 'via Erasmo Valvason 4',
     46.0638073, 13.2326338,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Del Fagioli', 'Italia', 'Firenze', 'corso Tintori 47 r',
     43.7675733, 11.2595837,
     20.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €
    ('Mangiare Bere Uomo Donna', 'Italia', 'Suzzara', 'viale Zonta 19',
     44.993989, 10.749578,
     20.0, FALSE, FALSE,
     'Fusion', 'marco_f'),
    -- Selected Restaurants | €€€
    ('ES Cantina&Ristorante', 'Italia', 'Manduria', 'contrada Reni',
     40.3759418, 17.6711765,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Gusto di Xinge', 'Italia', 'Firenze', 'Viale Belfiore 2',
     43.7789417, 11.2410878,
     45.0, FALSE, FALSE,
     'Asian Contemporary', 'anna_m'),
    -- Selected Restaurants | €€
    ('Farmacia dei Sani', 'Italia', 'Ruffano', 'piazza del Popolo 14',
     39.9854051, 18.2476737,
     45.0, FALSE, FALSE,
     'Apulian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Chicco di Grano', 'Italia', 'Castel Mella', 'viale dei Caduti del Lavoro 5',
     45.4942256, 10.1495251,
     45.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Le Tre Colombe', 'Italia', 'Fornace', 'località Santo Stefano 22',
     46.1300714, 11.2141252,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Locanda Lo Scopiccio', 'Italia', 'Perignano', 'via delle Casine 5',
     43.6010738, 10.5803663,
     45.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria Bertaina', 'Italia', 'Mondovì', 'piazza Maggiore 6',
     44.3887393, 7.8299888,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Passion', 'Italia', 'Vintl', 'via San Nicolò 5/b',
     46.8143348, 11.7601154,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €
    ('Brezza fish and chill', 'Italia', 'Soverato', 'Via Marina 24',
     38.690953, 16.5465149,
     20.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Vert Osteria Contemporanea', 'Italia', 'Caprino Veronese', 'Località Bogonza',
     45.60776, 10.809,
     45.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Gallo Nero', 'Italia', 'Sienna', 'Via del Porrione 65/67',
     43.3179114, 11.3342758,
     85.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€
    ('Lalibera', 'Italia', 'Alba', 'via Pertinace 24/a',
     44.6987609, 8.0340111,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Arca', 'Italia', 'San Benedetto del Tronto', 'viale Rinascimento 143',
     42.9264082, 13.897645,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('All’Ombra del Gabbiano', 'Italia', 'Mestre', 'via Caneve 2',
     45.4960257, 12.2442495,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Attico sul Mare', 'Italia', 'Grottammare', 'piazza Kursaal 6',
     42.9914877, 13.8709178,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Alle Ciaspole', 'Italia', 'Fondo', 'località Plazze 4',
     46.479834, 11.1428226,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Saturnino', 'Italia', 'Forio', 'via Soprascaro 17',
     40.7378863, 13.8574114,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('N''uovo Vino e Cucina', 'Italia', 'Sutri', 'S.S. Cassia 46',
     42.2307606, 12.258055,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Artifex', 'Italia', 'Brennero', 'Via Fleres 185',
     46.9646182, 11.3458131,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Su Murruai', 'Italia', 'Riola Sardo', 'via Giuseppe Garibaldi 36',
     39.9963989, 8.5395594,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('177 toledo', 'Italia', 'Napoli', 'via Toledo 177',
     40.8406929, 14.2487385,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Agriturismo Ferdy', 'Italia', 'Lenna', 'Località Fienili',
     45.9246206, 9.669272,
     85.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Il Mirto', 'Italia', 'Forio', 'via Provinciale Lacco',
     40.7531204, 13.8789992,
     160.0, FALSE, FALSE,
     'Vegetariana', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Locanda dei Narcisi', 'Italia', 'Pozzolo Formigaro', 'via Bettole 35',
     44.80856, 8.82527,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Hosteria La Cave Cantù', 'Italia', 'Casteggio', 'via Circonvallazione Cantù 62',
     45.012931, 9.1267958,
     85.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Quattro Gigli', 'Italia', 'Montopoli in Val d''Arno', 'piazza Michele da Montopoli 2',
     43.6701439, 10.760764,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Alla Lanterna', 'Italia', 'Fano', 'SS Adriatica Sud 78',
     43.8187502, 13.0702106,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Soltojo', 'Italia', 'Caiolo', 'via Caiolo Alto 45',
     46.1490975, 9.8147893,
     45.0, FALSE, FALSE,
     'Fusion', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Casa Coppelle', 'Italia', 'Roma', 'piazza delle Coppelle 49',
     41.9007751, 12.4758761,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Retrogusto', 'Italia', 'Otranto', 'via Tenente Eula 7',
     40.147266, 18.487043,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Corte Matilde', 'Italia', 'Pieve di Coriano', 'via Pelate 38',
     45.0261215, 11.1206992,
     45.0, FALSE, FALSE,
     'Mantuan', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Da Severino il Vecchio - Di Luciano', 'Italia', 'Senorbì', 'largo Abruzzi 2',
     39.526185, 9.1334416,
     20.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Veranda (Moltrasio)', 'Italia', 'Moltrasio', 'piazza San Rocco 5',
     45.8601242, 9.101444,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Walser Schtuba', 'Italia', 'Ponte', 'località Riale',
     46.4227509, 8.4164287,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Amo', 'Italia', 'Venezia', 'calle del Fontego dei Tedeschi',
     45.4382293, 12.3369601,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Cip''s Club', 'Italia', 'Venezia', 'isola della Giudecca 10',
     45.4277467, 12.3408021,
     160.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('Tuorlo', 'Italia', 'Torino', 'via Sant''Agostino 15/b',
     45.0750429, 7.680135,
     45.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Luca''s by Paulo Airaudo', 'Italia', 'Firenze', 'Via dei Cavalieri 2/c',
     43.7707, 11.25406,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Corte Federiciana', 'Italia', 'Apricena', 'corso Garibaldi 60',
     41.7855025, 15.4418528,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Alla Locanda', 'Italia', 'Canazei', 'strada Roma 23',
     46.4764038, 11.7672061,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Le Beccherie', 'Italia', 'Treviso', 'piazza Giannino Ancilotto 9',
     45.666269, 12.2468354,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Da Gelsomina', 'Italia', 'Anacapri', 'Via Migliara 72',
     40.5417666, 14.2114125,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('SaleGrosso (Marciana Marina)', 'Italia', 'Marciana Marina', 'piazza della Vittoria 14',
     42.8055611, 10.2000785,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Cestello Firenze', 'Italia', 'Firenze', 'piazza di Cestello 8',
     43.770306, 11.2436567,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Coltivare', 'Italia', 'La Morra', 'borgata Brandini 16',
     44.6392382, 7.9089221,
     85.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Cortiletto', 'Italia', 'Toscolano-Maderno', 'via F.lli Bianchi 1',
     45.63496, 10.60022,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Vineria Derthona', 'Italia', 'Tortona', 'via Lorenzo Perosi 15',
     44.8984572, 8.8655131,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Sabir', 'Italia', 'Zafferana Etnea', 'via delle Ginestre 1',
     37.6863972, 15.1035324,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Wine & Dine', 'Italia', 'Canazei', 'strèdà Roma 5',
     46.4764246, 11.7701224,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('L''Officina', 'Italia', 'Perugia', 'Borgo XX Giugno 56',
     43.1036139, 12.3938137,
     45.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Colline Emiliane', 'Italia', 'Roma', 'via degli Avignonesi 22',
     41.9030135, 12.4874484,
     45.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Ai Torchi', 'Italia', 'Finalborgo', 'via dell''Annunziata 12',
     44.1753615, 8.3275775,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Extra', 'Italia', 'Carrara', 'viale Turigliano 13',
     44.052647, 10.069568,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Kisté - Easy Gourmet', 'Italia', 'Taormina', 'via Santa Maria de'' Greci 2',
     37.850777, 15.283031,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Trattoria Via Vai', 'Italia', 'Bolzone', 'via Libertà 18',
     45.3321006, 9.6612651,
     45.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria del Trentino - Da Marco', 'Italia', 'Piacenza', 'via del Castello 71',
     45.052834, 9.6851448,
     45.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Mamma Rosa', 'Italia', 'San Polo d''Enza', 'via XXIV Maggio 1',
     44.6278667, 10.4174269,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria del Benedet', 'Italia', 'Delebio', 'via Roma 2',
     46.1375374, 9.46318,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Cavallini', 'Italia', 'San Severino Marche', 'viale Bigioli 47',
     43.2297161, 13.1766876,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €
    ('Lucenti', 'Italia', 'Montefiorino', 'via Mazzini 38',
     44.3579699, 10.621784,
     20.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Selected Restaurants | €€
    ('L''Ottava Nota', 'Italia', 'Palermo', 'via Butera 55',
     38.11774, 13.371684,
     45.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Gibas', 'Italia', 'Pesaro', 'strada Panoramica Adriatica',
     43.9275954, 12.877271,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Il Faggio', 'Italia', 'Pollone', 'via Oremo 54',
     45.5823083, 8.0055232,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Antiche Mura', 'Italia', 'Riva del Garda', 'via Bastione 19',
     45.886997, 10.838588,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Aurora', 'Italia', 'Feltre', 'via Garibaldi 68',
     46.015656, 11.907839,
     45.0, FALSE, FALSE,
     'Veneziana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria dai Mazzeri', 'Italia', 'Follina', 'via Pallade 18',
     45.9546545, 12.1195211,
     45.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Enoteca L''Armadillo', 'Italia', 'Courmayeur', 'strada La Palud 27',
     45.8183201, 6.9646826,
     85.0, FALSE, FALSE,
     'Fusion', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Fortuna', 'Italia', 'Campagnola Cremasca', 'via Ponte Rino 6',
     45.39835, 9.6685,
     45.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Pepenero', 'Italia', 'San Miniato', 'piazza del Duomo 4',
     43.6796919, 10.851175,
     45.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Luminist Cafè Bistrot', 'Italia', 'Napoli', 'via Toledo 177',
     40.8406951, 14.24874,
     45.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Il Ristorante - Niko Romito (Rome)', 'Italia', 'Roma', 'via di Ripetta 73',
     41.9067852, 12.4758727,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Quintogusto', 'Italia', 'Savona', 'piazza Sandro Pertini 54 r',
     44.3054183, 8.4814112,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €
    ('Locanda delle Tre Chiavi', 'Italia', 'Isera', 'via Vannetti 8',
     45.88918, 11.01126,
     20.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Musciora', 'Italia', 'Alghero', 'via Mazzini 59',
     40.5586717, 8.317813,
     85.0, FALSE, FALSE,
     'Sarda', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria Mercato', 'Italia', 'Stresa', 'piazza Capucci 9',
     45.8822178, 8.5397648,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Bàcaro Il Gusto', 'Italia', 'Fossò', 'via Provinciale Nord 30',
     45.386677, 12.0493508,
     45.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Casa Rispoli', 'Italia', 'Cava de'' Tirreni', 'piazza san Francesco 7',
     40.6963696, 14.7099255,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('L''Arcangelo', 'Italia', 'Roma', 'via Giuseppe Gioacchino Belli 59',
     41.9065413, 12.4687174,
     45.0, FALSE, FALSE,
     'Roman', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Eea', 'Italia', 'Ponza', 'via Umberto I',
     40.893543, 12.964747,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Verbano', 'Italia', 'Stresa', 'via Ugo Ara 2',
     45.9000678, 8.5217885,
     85.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('La Tavola', 'Italia', 'Laveno', 'via Fortino 40',
     45.9065255, 8.6104635,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Brughiera', 'Italia', 'Senago', 'via XXIV Maggio 23',
     45.5757798, 9.1140087,
     45.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Gasthofstube Stafler', 'Italia', 'Mules', 'Campo di Trens',
     46.8511257, 11.5204347,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€
    ('Buriani dal 1967', 'Italia', 'Pieve di Cento', 'via Matteotti 66',
     44.7112661, 11.3059943,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Da Nonna Sceppa', 'Italia', 'Paestum', 'via Laura 45',
     40.44638, 14.978889,
     45.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Vesta Mare', 'Italia', 'Marina di Pietrasanta', 'viale Roma 41',
     43.933229, 10.1930984,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Tantris', 'Italia', 'Novara', 'corso Risorgimento 384',
     45.4828355, 8.6104091,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Antica Osteria La Rampina', 'Italia', 'San Giuliano Milanese', 'via Emilia fraz. Rampina 3 ang. via Rocca Brivio',
     45.36847, 9.31612,
     85.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Buca di Sant''Antonio', 'Italia', 'Lucca', 'via della Cervia 3',
     43.8427305, 10.5017857,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€
    ('l'' Ciocio - Osteria di Suvereto', 'Italia', 'Suvereto', 'piazza dei Giudici 1',
     43.07921, 10.67901,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria Vecchio Borgo', 'Italia', 'Cuneo', 'via Dronero 8/b',
     44.3930839, 7.5491385,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('4 Ciance', 'Italia', 'Cuneo', 'via Dronero 8/c',
     44.393135, 7.549021,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Ai Mercanti', 'Italia', 'Venezia', 'corte Coppo',
     45.4351522, 12.3351495,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Da Gerry', 'Italia', 'Monfumo', 'via Chiesa 6',
     45.8304724, 11.9209252,
     45.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria da Afro', 'Italia', 'Spilimbergo', 'via Umberto I 14',
     46.1125533, 12.8987118,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€
    ('Le Case della Saracca', 'Italia', 'Monforte d''Alba', 'via Cavour 5',
     44.5846665, 7.9686028,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('L''Osteria H20', 'Italia', 'Moniga del Garda', 'via Pergola 10',
     45.52083, 10.52629,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Cavour', 'Italia', 'Dossobuono', 'via Cavour 40',
     45.3966994, 10.9144069,
     45.0, FALSE, FALSE,
     'Veneziana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria Il Bagatto', 'Italia', 'Limone Piemonte', 'via XX Settembre 16',
     44.2010853, 7.5768821,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Antica Osteria Magenes', 'Italia', 'Barate', 'via Cavour 7',
     45.3846895, 9.0396282,
     85.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Alle Corone', 'Italia', 'Venezia', 'campo della Fava',
     45.437059, 12.3377324,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Rosina', 'Italia', 'Marostica', 'via Marchetti 4',
     45.7745125, 11.6603203,
     45.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Villetta Annessa', 'Italia', 'Riva del Garda', 'via Monte Oro 9',
     45.8863357, 10.8381169,
     85.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Fossa del Grano', 'Italia', 'San Severo', 'via Minuziano 63',
     41.6844115, 15.3820992,
     45.0, FALSE, FALSE,
     'Apulian', 'anna_m'),
    -- Selected Restaurants | €
    ('50 Kalò', 'Italia', 'Napoli', 'piazza Sannazzaro 201/b',
     40.8286403, 14.2199723,
     20.0, FALSE, FALSE,
     'Pizza', 'marco_f'),
    -- Selected Restaurants | €€
    ('Casa Format', 'Italia', 'Orbassano', 'via Tetti Valfrè',
     45.002438, 7.5737295,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Barca (Marina di Pulsano)', 'Italia', 'Marina di Pulsano', 'litoranea Salentina',
     40.3586767, 17.3445166,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €
    ('Fiorentino e Locanda del Giglio', 'Italia', 'Sansepolcro', 'via Luca Pacioli 60',
     43.5714908, 12.1398187,
     20.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Il Sole di Ranco', 'Italia', 'Ranco', 'piazza Venezia 5',
     45.7982244, 8.569655,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Hostaria del Teatro', 'Italia', 'Castiglione delle Stiviere', 'via Ordanino 5b',
     45.3909991, 10.4906348,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Locanda 4 Cuochi', 'Italia', 'Verona', 'via Alberto Mario 12',
     45.44019, 10.99397,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('Menzaghi', 'Italia', 'Fagnano Olona', 'via San Giovanni 74',
     45.6780491, 8.8688605,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Giglio', 'Italia', 'Lucca', 'piazza del Giglio 2',
     43.8410854, 10.5032438,
     85.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€
    ('Castello', 'Italia', 'Santa Vittoria d''Alba', 'via Cagna 4',
     44.69611, 7.9304,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Apollinare', 'Italia', 'Spoleto', 'via Sant''Agata 14',
     42.7335767, 12.735434,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Tre Noci', 'Italia', 'Spirano', 'via Petrarca 16',
     45.5808613, 9.6672084,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Morelli', 'Italia', 'Milano', 'via Aristotile Fioravanti 4',
     45.4832867, 9.1774988,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Malga Roncac', 'Italia', 'Moena', 'strada de Roncac 7',
     46.382225, 11.651086,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Salvo Cacciatori', 'Italia', 'Oneglia', 'via Vieusseux 12',
     43.89001, 8.040223,
     85.0, FALSE, FALSE,
     'Ligure', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Ansitz Steinbock', 'Italia', 'Villandro', 'via Defregger 14',
     46.6323382, 11.5403732,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Pajara Gourmet', 'Italia', 'Fiera di Primiero', 'via Venezia 28',
     46.1768409, 11.8360621,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Osteria Arborina', 'Italia', 'La Morra', 'frazione Annunziata 27/b',
     44.6356283, 7.9535024,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Olona - "Da Venanzio" dal 1922', 'Italia', 'Induno Olona', 'via Olona 38',
     45.8465363, 8.826291,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Al Palazzo', 'Italia', 'Positano', 'via Dei Mulini 23',
     40.6290681, 14.4867101,
     85.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('J Contemporary Japanese Restaurant', 'Italia', 'Napoli', 'via Agostino Depretis 24',
     40.8427582, 14.2549979,
     85.0, FALSE, FALSE,
     'Giapponese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Un Posto a Milano', 'Italia', 'Milano', 'via Cuccagna 2',
     45.4511034, 9.2114879,
     45.0, FALSE, FALSE,
     'Km Zero', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Lo Scudiero', 'Italia', 'Pesaro', 'via Baldassini 2',
     43.9092737, 12.9142506,
     85.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('S''Andira', 'Italia', 'Santa Reparata', 'via Orsa Minore 1',
     41.2316127, 9.1668328,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Al Volt', 'Italia', 'Riva del Garda', 'via Fiume 73',
     45.8863406, 10.8397358,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Nni Lausta', 'Italia', 'Santa Marina Salina', 'via Risorgimento 188',
     38.5596, 14.87111,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Trequarti', 'Italia', 'Val Liona', 'piazza del Donatore 3/4',
     45.409725, 11.452485,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Antica Osteria Il Monte Rosso', 'Italia', 'Suna', 'via Troubetzkoy 128',
     45.9310281, 8.5400656,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Convito di Curina', 'Italia', 'Castelnuovo Berardenga', 'SP 62 24',
     43.34549, 11.46541,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Osteria L''Orciaia', 'Italia', 'Montebenichi', 'via Capitan Goro 10',
     43.4057953, 11.5448434,
     20.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Posta (Sant''Omobono Terme)', 'Italia', 'Sant''Omobono Terme', 'viale Vittorio Veneto 169',
     45.8147902, 9.5328321,
     85.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Hostaria Baccofurore', 'Italia', 'Furore', 'via G.B. Lama 9',
     40.6204277, 14.5486388,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda Radici', 'Italia', 'Melizzano', 'SP 21',
     41.1752087, 14.5126722,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('DanielCanzian', 'Italia', 'Milano', 'via Castelfidardo 7',
     45.478596, 9.189223,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Onda Blu', 'Italia', 'San Mauro a Mare', 'via Orsa Minore 1',
     44.16286, 12.449604,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Ilario Vinciguerra', 'Italia', 'Gallarate', 'via Roma 1',
     45.6619731, 8.7895018,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria del Borgo', 'Italia', 'Galliate', 'via Pietro Custodi 5',
     45.4780716, 8.697357,
     45.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Santo Bevitore', 'Italia', 'Firenze', 'via Santo Spirito 64/66 r',
     43.7690773, 11.2466192,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Al Cavallino Bianco', 'Italia', 'Polesine Parmense', 'via Sbrisi 3',
     45.020774, 10.0907645,
     45.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Moscatello Muliner', 'Italia', 'Pozzolengo', 'località Moscatello 3/5',
     45.3913037, 10.6404351,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€
    ('ME Restaurant', 'Italia', 'Pizzo', 'SP per Vibo Marina',
     38.7239647, 16.1479385,
     85.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('El Filò', 'Italia', 'Pozza di Fassa', 'strada Dolomites 103',
     46.42843, 11.68419,
     85.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€€
    ('L''Oste e il Sacrestano', 'Italia', 'Licata', 'via Sant''Andrea 19',
     37.1000891, 13.9388331,
     85.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Papaveri e Papere', 'Italia', 'San Miniato', 'via Dalmazia 159/d',
     43.6668854, 10.8391901,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Trippi', 'Italia', 'Sondrio', 'via Stelvio 297',
     46.1699887, 9.901579,
     45.0, FALSE, FALSE,
     'Cuisine from Valtellina', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Regina Lucia', 'Italia', 'Syracuse', 'piazza Duomo 6',
     37.0588133, 15.2931068,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Da Giacomo', 'Italia', 'Pizzighettone', 'piazza Municipio 2',
     45.1869216, 9.7831801,
     45.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Locanda di Piero', 'Italia', 'Montecchio Precalcino', 'via Roma 34',
     45.6556659, 11.5580231,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Il Galeone', 'Italia', 'Fano', 'piazzale Amendola 2',
     43.8511366, 13.01097,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €
    ('Entrà', 'Italia', 'Finale Emilia', 'via Salde Entrà 60',
     44.832888, 11.1856644,
     20.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Filippino', 'Italia', 'Lipari', 'piazza Municipio',
     38.4685552, 14.9564985,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Marcelin', 'Italia', 'Montà', 'piazzetta della Vecchia Segheria 1',
     44.8144512, 7.9585452,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Torre (Monselice)', 'Italia', 'Monselice', 'piazza Mazzini 14',
     45.24177, 11.7507885,
     45.0, FALSE, FALSE,
     'Tradizionale', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Ca'' Del Moro', 'Italia', 'Grezzana', 'località Erbin 31',
     45.5313227, 11.0581539,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Viva il Bistrot', 'Italia', 'Noto', 'via Rocco Pirri 19',
     36.8918976, 15.0692098,
     45.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Il Fenicottero Rosa Gourmet', 'Italia', 'Faenza', 'via Emilia Ponente 23',
     44.2947451, 11.8539981,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('I Due Buoi', 'Italia', 'Olivola', 'via Vittorio Veneto 23',
     45.037953, 8.3667204,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Bites', 'Italia', 'Milano', 'via Lambro 11',
     45.4743013, 9.2095483,
     85.0, FALSE, FALSE,
     'Internazionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Fico trentacareghe', 'Italia', 'Lerici', 'Località Fiascherino 7',
     44.0657653, 9.9235847,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('L''Uva e il Malto', 'Italia', 'Grosseto', 'via Mazzini 165',
     42.758942, 11.1139463,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('I Banchi', 'Italia', 'Ragusa', 'via Orfanotrofio 39',
     36.926517, 14.7454195,
     45.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Maso Burba', 'Italia', 'Commezzadura', 'via Pietro Bernardelli 32',
     46.3208346, 10.823971,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Le Rune', 'Italia', 'Genova', 'salita inferiore Sant''Anna 13r',
     44.4117095, 8.9348816,
     45.0, FALSE, FALSE,
     'Ligure', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria la Fontanina', 'Italia', 'Verona', 'portichetti Fontanelle Santo Stefano 3',
     45.4492275, 11.0006288,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Taverna del Leone', 'Italia', 'Positano', 'via Laurito 43',
     40.6248114, 14.5076383,
     45.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Controcorrente (Noli)', 'Italia', 'Noli', 'via Colombo 101',
     44.205749, 8.4134555,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Li Lioni', 'Italia', 'Porto Torres', 'SS 131 km 244',
     40.80912, 8.43872,
     45.0, FALSE, FALSE,
     'Sarda', 'marco_f'),
    -- Selected Restaurants | €€
    ('Taverna La Cialoma', 'Italia', 'Marzamemi', 'piazza Regina Margherita 23',
     36.7420586, 15.1197108,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Sosta di Ottone III', 'Italia', 'Levanto', 'Località Chiesanuova 39',
     44.17155, 9.64716,
     85.0, FALSE, FALSE,
     'Ligure', 'anna_m'),
    -- Selected Restaurants | €€
    ('Le Baccanti', 'Italia', 'Nola', 'via Puccini 5',
     40.9313683, 14.5251246,
     45.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Oberlechner', 'Italia', 'Algund', 'località Velloi 7',
     46.69601, 11.117554,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Osteria della Chiocciola', 'Italia', 'Cuneo', 'via Fossano 1',
     44.3938765, 7.5509969,
     20.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Menarosti', 'Italia', 'Trieste', 'via del Toro 12',
     45.6508, 13.7790259,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Al Bagatto', 'Italia', 'Trieste', 'via Cadorna 7',
     45.6485947, 13.7652132,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Portanova', 'Italia', 'Urbino', 'via Cesare Battisti 67',
     43.7275489, 12.6373808,
     45.0, FALSE, FALSE,
     'Cuisine from the Marches', 'anna_m'),
    -- Selected Restaurants | €€
    ('Contrasto', 'Italia', 'Cercemaggiore', 'via Roma 55',
     41.4592138, 14.7200502,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Bolle', 'Italia', 'Lallio', 'via Provinciale 30',
     45.65983, 9.62902,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Futura', 'Italia', 'Chieti', 'piazza San Giustino 7',
     42.35107, 14.1673768,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Blaue Traube', 'Italia', 'Algund', 'strada Vecchia 44',
     46.6831586, 11.1228848,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Saur', 'Italia', 'Barco', 'via Filippo Turati 8',
     45.3794034, 9.9010106,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Valli di Lanzo', 'Italia', 'Céres', 'via Roma 11',
     45.3146596, 7.3882607,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Cuoco di Bordo', 'Italia', 'Senigallia', 'lungomare Dante Alighieri 94',
     43.709146, 13.2336576,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Le Colonne (Santa Maria Maggiore)', 'Italia', 'Santa Maria Maggiore', 'via Benefattori 7',
     46.1350178, 8.4676249,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Nives', 'Italia', 'Selva di Val Gardena', 'via Nives 4',
     46.5561093, 11.7569582,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Ratanà', 'Italia', 'Milano', 'via G. de Castillia 28',
     45.4857472, 9.1930965,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Soho', 'Italia', 'Genova', 'via al Ponte Calvi 20 r',
     44.41187, 8.928815,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Al Dragone', 'Italia', 'Vieste', 'via Duomo 8',
     41.8817611, 16.1810822,
     45.0, FALSE, FALSE,
     'Apulian', 'anna_m'),
    -- Selected Restaurants | €€
    ('Scrigno del Duomo', 'Italia', 'Trento', 'piazza Duomo 29',
     46.0674352, 11.1209763,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('L''Osteria dell''Orologio', 'Italia', 'Fiumicino', 'via di Torre Clementina 114',
     41.7714217, 12.2303655,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Di Martino Sea Front Pasta Bar', 'Italia', 'Napoli', 'piazza Municipio 1',
     40.8396564, 14.2517377,
     45.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Li Jalantuùmene', 'Italia', 'Monte Sant''Angelo', 'piazza de Galganis 9',
     41.7071157, 15.955461,
     85.0, FALSE, FALSE,
     'Apulian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria Vecchia Noce', 'Italia', 'Vicopisano', 'Località Noce 39',
     43.6907213, 10.5333892,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Bel Ami', 'Italia', 'Maglie', 'via Roma 86',
     40.1234831, 18.2971156,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('LPV Ristorante & Bistrot', 'Italia', 'Venezia', 'riva degli Schiavoni',
     45.4340132, 12.3436845,
     85.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('Alpenrose', 'Italia', 'Brixen', 'via delle Sette Chiese 1',
     46.706382, 11.6390082,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Molo (San Leone)', 'Italia', 'San Leone', 'via Falcone Borsellino 2',
     37.2631586, 13.5788859,
     45.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Vecchia Sorni', 'Italia', 'Sorni', 'piazza Assunta 40',
     46.1722192, 11.1235507,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Alessandro Mecca al Castello di Grinzane Cavour', 'Italia', 'Grinzane Cavour', 'via Castello 5',
     44.6533268, 7.9953423,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Albergaccio di Castellina', 'Italia', 'Castellina in Chianti', 'via Fiorentina 63',
     43.47298, 11.27994,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Capuccina', 'Italia', 'Cureggio', 'via Novara 19/b',
     45.6735288, 8.4622472,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Corte (Follina)', 'Italia', 'Follina', 'via Roma 24',
     45.9525845, 12.1180814,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Cucina', 'Italia', 'Firenze', 'Via Giano della Bella 3r',
     43.7648074, 11.2401345,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Casamare', 'Italia', 'Salerno', 'corso Giuseppe Garibaldi 214',
     40.676859, 14.7640743,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Koinè', 'Italia', 'Legnano', 'vicolo Filippo Corridoni 2/c',
     45.5931637, 8.9194081,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Dina', 'Italia', 'Gussago', 'via Santa Croce 1',
     45.5838633, 10.1562101,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Sogno', 'Italia', 'San Felice del Benaco', 'via Porto San Felice 41',
     45.58019, 10.55525,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Trattoria al Pompiere', 'Italia', 'Verona', 'vicolo Regina d''Ungheria 5',
     45.4419173, 10.9979256,
     45.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Villa De Winckels', 'Italia', 'Tregnago', 'via Sorio 30',
     45.520783, 11.1535074,
     45.0, FALSE, FALSE,
     'Veneziana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Beccaccino', 'Italia', 'Sorico', 'via Boschetto 49',
     46.167914, 9.3935389,
     45.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Antico Albergo', 'Italia', 'Limito', 'via Dante Alighieri 18',
     45.482127, 9.3298984,
     45.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Dodici Fontane', 'Italia', 'Linguaglossa', 'contrada Arrigo',
     37.827248, 15.126535,
     160.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Tana de ''l Ors', 'Italia', 'Forno di Zoldo', 'via Roma 28',
     46.3461088, 12.1785249,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Terrazza Danieli', 'Italia', 'Venezia', 'riva degli Schiavoni',
     45.433869, 12.3420972,
     160.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Ridotto', 'Italia', 'Venezia', 'campo S.S. Filippo e Giacomo',
     45.4351501, 12.3416761,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Camana Veglia', 'Italia', 'Livigno', 'via Ostaria 583',
     46.546807, 10.1390814,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Cucina di Donna Carmela', 'Italia', 'Riposto', 'contrada Grotte 7',
     37.676624, 15.179596,
     85.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Trattoria della Posta', 'Italia', 'Monforte d''Alba', 'Località Sant''Anna 87',
     44.5744898, 7.9886563,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Il Calandrino', 'Italia', 'Rubano', 'via Liguria 1',
     45.4216067, 11.8095767,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Coppola Rossa', 'Italia', 'Manfredonia', 'via Maddalena 28',
     41.6267253, 15.9154397,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Da Manuela', 'Italia', 'Isola Sant''Antonio', 'frazione Capraglia',
     45.0352935, 8.8253002,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Hostaria alla Tavernetta', 'Italia', 'Udine', 'via Artico di Prampero 2',
     46.0617002, 13.2372018,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('Trattoria ''petito', 'Italia', 'Forlì', 'via Corridoni 14',
     44.2179267, 12.0461155,
     45.0, FALSE, FALSE,
     'Cuisine from Romagna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Federico II', 'Italia', 'Termoli', 'via Duomo 30',
     42.0043381, 14.9973442,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Braja', 'Italia', 'Montemagno', 'via San Giovanni Bosco 11',
     44.981582, 8.3208927,
     85.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Patauner', 'Italia', 'Settequerce', 'via Bolzano 6',
     46.5112328, 11.2740597,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Da Nilo', 'Italia', 'Cetona', 'piazza Garibaldi 31',
     42.9645008, 11.9004364,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Quartopiano', 'Italia', 'Rimini', 'via Chiabrera 34/c',
     44.04775, 12.59455,
     45.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Ristorante Da Anita - Chalet Prà delle Nasse', 'Italia', 'San Martino di Castrozza', 'via Cavallazza 24',
     46.2631715, 11.79799,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Da Politano', 'Italia', 'Boves', 'via Santuario 125',
     44.3328613, 7.5238243,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Antica Osteria del Ponte', 'Italia', 'Cassinetta di Lugagnano', 'piazza Gaetano Negri 9',
     45.4259722, 8.9098057,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Scaraboci', 'Italia', 'Marciana Marina', 'via XX Settembre 27',
     42.80472, 10.19958,
     45.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Cacciani', 'Italia', 'Frascati', 'via Diaz 15',
     41.8075835, 12.6788993,
     45.0, FALSE, FALSE,
     'Cuisine from Lazio', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Terrazza (Torbole)', 'Italia', 'Torbole', 'via Benaco 24',
     45.8689645, 10.8749638,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Sichelburg', 'Italia', 'Pfalzen', 'via Castello 1a',
     46.8144957, 11.8829237,
     45.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Verso del Ghiottone', 'Italia', 'Dogliani', 'via Demagistris 5',
     44.5279714, 7.9490561,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Trattoria del Nuovo Macello', 'Italia', 'Milano', 'via Cesare Lombroso 20',
     45.4560446, 9.2267357,
     45.0, FALSE, FALSE,
     'Lombardian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Antica Pesa', 'Italia', 'Roma', 'via Garibaldi 18',
     41.8906, 12.46638,
     45.0, FALSE, FALSE,
     'Roman', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Osteria dei Vespri', 'Italia', 'Palermo', 'piazza Croce dei Vespri 6',
     38.114758, 13.3658272,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Riva (Ponte dell''Olio)', 'Italia', 'Ponte dell''Olio', 'via Riva 16',
     44.8568639, 9.6288342,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Antica Trattoria del Gallo', 'Italia', 'Vigano', 'via Privata Gerli 3',
     45.3799348, 9.0245013,
     45.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('San Michele', 'Italia', 'Fagagna', 'via Castello di Fagagna 33',
     46.114908, 13.0877924,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('L''Angolino da Filippo', 'Italia', 'Marina di San Vito', 'via Sangritana 1',
     42.3083754, 14.4459796,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €
    ('L''antica Pizzeria da Michele', 'Italia', 'Napoli', 'via Cesare Sersale 1/7',
     40.8497214, 14.2634903,
     20.0, FALSE, FALSE,
     'Pizza', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Pierre Alexis 1877', 'Italia', 'Courmayeur', 'via Marconi 50/a',
     45.7894165, 6.9742888,
     85.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Selected Restaurants | €€
    ('Da Gigetto', 'Italia', 'Miane', 'via De Gasperi 5',
     45.942004, 12.0900375,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Maison Delfino', 'Italia', 'Moncalieri', 'via Lagrange 4',
     45.0001263, 7.6736825,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Doppio Gusto', 'Italia', 'Milazzo', 'via Luigi Rizzo 1/2',
     38.2200717, 15.241736,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Aquariva', 'Italia', 'Padenghe sul Garda', 'via Marconi 57',
     45.49376, 10.51139,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Fana Ladina', 'Italia', 'San Vigilio di Marebbe', 'via Plan de Corones 10',
     46.6997472, 11.9311374,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('L''Accanto', 'Italia', 'Vico Equense', 'via Santa Maria Vecchia 2',
     40.6584322, 14.4213407,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('All''Olivo', 'Italia', 'Lucca', 'piazza San Quirico 1',
     43.8429692, 10.5054482,
     45.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Funghetto', 'Italia', 'Latina', 'strada Litoranea 11412',
     41.3919837, 12.9460743,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Alex', 'Italia', 'Marina di Pietrasanta', 'via Versilia 157/159',
     43.923157, 10.2039446,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria della Corte', 'Italia', 'La Spezia', 'via Napoli 86',
     44.108524, 9.815648,
     45.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Antico Ristorante Forassiepi', 'Italia', 'Montecarlo', 'via della Contea 1',
     43.8496128, 10.6703504,
     85.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Chichibio', 'Italia', 'Roccaraso', 'via Guglielmo Marconi 1',
     41.846474, 14.080086,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Da Andrea', 'Italia', 'Marina di Cecina', 'viale della Vittoria 68',
     43.29478, 10.49746,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Ippogrifo', 'Italia', 'Genova', 'via Gestro 9 r',
     44.3973, 8.9453,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Enoteca San Nicola', 'Italia', 'Bobbio', 'contrada di San Nicola 11/a',
     44.7668479, 9.3853918,
     20.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Ai Gondolieri', 'Italia', 'Venezia', 'fondamenta de l''Ospedaleto',
     45.4303027, 12.3307402,
     85.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Tavola di Guido', 'Italia', 'Castellina in Chianti', 'Località Le Piazze 41',
     43.4535857, 11.2410406,
     85.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Svevia', 'Italia', 'Termoli', 'via Giudicato Vecchio 24',
     42.0056596, 14.9973612,
     45.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Santa Teresa', 'Italia', 'Genova', 'via di Porta Soprana 55 r',
     44.4062279, 8.9335702,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('ZELO', 'Italia', 'Milano', 'via Gesù 6/8',
     45.46964, 9.195502,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Da Oscar', 'Italia', 'Barcuzzi', 'via Barcuzzi 16',
     45.4890609, 10.4967641,
     45.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Il Cascinalenuovo', 'Italia', 'Isola d''Asti', 'SS 231  Asti-Alba 15',
     44.8264017, 8.1694186,
     85.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Da Fausto (Fondi)', 'Italia', 'Fondi', 'piazza Cesare Beccaria 6',
     41.361339, 13.4235667,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Al Grop', 'Italia', 'Tavagnacco', 'via Matteotti 7',
     46.1261624, 13.2085225,
     45.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Selected Restaurants | €€
    ('Canapone', 'Italia', 'Grosseto', 'piazza Dante 3',
     42.7592993, 11.1140761,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria del Mirasole', 'Italia', 'San Giovanni in Persiceto', 'via Matteotti 17/a',
     44.6389772, 11.1856784,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Il Sanlorenzo', 'Italia', 'Roma', 'via dei Chiavari 4/5',
     41.8953002, 12.4741868,
     160.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €
    ('El Primero', 'Italia', 'Origgio', 'largo Umberto Boccioni 3',
     45.602239, 9.0256959,
     20.0, FALSE, FALSE,
     'South American', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Don Camillo', 'Italia', 'Syracuse', 'via Maestranza 96',
     37.0607669, 15.2967023,
     85.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Dim Sum', 'Italia', 'Milano', 'via Nino Bixio 29',
     45.4724299, 9.2110853,
     85.0, FALSE, FALSE,
     'Cantonese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Hostaria Uva Rara', 'Italia', 'Monticelli Brusati', 'via Foina 42',
     45.63691, 10.09984,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Ferrata', 'Italia', 'Pordenone', 'via Gorizia 7',
     45.954875, 12.6595471,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Tufo Allegro', 'Italia', 'Pitigliano', 'vicolo della Costituzione 5',
     42.6339811, 11.6670793,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Sa Musciara', 'Italia', 'Portoscuso', 'lungomare C. Colombo 15',
     39.2032205, 8.3791258,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Durnwald', 'Italia', 'Gsies', 'via Nikolaus Amhof 6',
     46.767593, 12.1789264,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Tanzer', 'Italia', 'Issengo', 'via del Paese 1',
     46.8150207, 11.8623126,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Péniche', 'Italia', 'Marina di Massa', 'via Lungo Brugiano 3',
     44.0105909, 10.0990077,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Cipresso - Da Cioni', 'Italia', 'Loro Ciuffenna', 'via Alcide De Gasperi 28',
     43.5883319, 11.6270812,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Bove''s', 'Italia', 'Cuneo', 'via Dronero 2/b',
     44.392628, 7.549974,
     45.0, FALSE, FALSE,
     'Meats and Grills', 'anna_m'),
    -- Selected Restaurants | €€
    ('QB DuePuntoZero', 'Italia', 'Salò', 'via Pietro da Salò 23',
     45.6037558, 10.5199691,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Bistrot Donatella', 'Italia', 'Oviglio', 'piazza Umberto I 1',
     44.8600974, 8.486985,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €
    ('La Masseria', 'Italia', 'Modena', 'via Chiesa 61',
     44.6513, 10.80694,
     20.0, FALSE, FALSE,
     'Apulian', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Innocenti Evasioni', 'Italia', 'Milano', 'via Giuseppe Candiani 66',
     45.5044912, 9.1686366,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Winter Garden Florence', 'Italia', 'Firenze', 'piazza Ognissanti 1',
     43.7721696, 11.2450988,
     160.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('L''Oste Scuro', 'Italia', 'Verona', 'vicolo San Silvestro 10',
     45.4382876, 10.9880573,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Kandoo Nippon Restaurant', 'Italia', 'Cremona', 'piazza Cadorna 15',
     45.1324181, 10.0162531,
     45.0, FALSE, FALSE,
     'Giapponese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Rosita', 'Italia', 'Finale Ligure', 'via Manie 67',
     44.1791461, 8.366972,
     45.0, FALSE, FALSE,
     'Ligure', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Quartina', 'Italia', 'Mergozzo', 'via Pallanza 20',
     45.9613357, 8.4554218,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€
    ('Locanda ''800', 'Italia', 'Negrar', 'via Moron 46',
     45.5168102, 10.9270138,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Da ö Vittorio', 'Italia', 'Recco', 'via Roma 160',
     44.3665421, 9.1466508,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Filippo', 'Italia', 'Pietrasanta', 'via Barsanti 45',
     43.9559386, 10.2325299,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Bislakko', 'Italia', 'Vercelli', 'via Thaon de Revel 87',
     45.3037404, 8.4461672,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Grantosco', 'Italia', 'Grosseto', 'via Solferino 4',
     42.7599627, 11.1155178,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Michelaccio', 'Italia', 'Genova', 'via Frugoni 49 r',
     44.4035, 8.939671,
     45.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Selected Restaurants | €
    ('Trattoria Leoni', 'Italia', 'Barbian', 'via Ricò 42',
     44.6677462, 10.2376867,
     20.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Al Vedel', 'Italia', 'Vedole', 'via Vedole 68',
     44.9223062, 10.3592101,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('La Serra', 'Italia', 'Positano', 'via Marconi 169',
     40.6262458, 14.4785098,
     160.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Il Sale', 'Italia', 'San Vincenzo', 'via San Bartolo 100',
     43.0900139, 10.568827,
     45.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('28 Posti', 'Italia', 'Milano', 'via Corsico 1',
     45.4527541, 9.173279,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('InGalera', 'Italia', 'Milano', 'via Cristina Belgioioso 120',
     45.522552, 9.101541,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Cibrèo', 'Italia', 'Firenze', 'via A. Del Verrocchio 8 r',
     43.7711187, 11.2664401,
     85.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Perbacco', 'Italia', 'Pisciotta', 'contrada Marina Campagna 5',
     40.1153394, 15.2220903,
     45.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Imperialino', 'Italia', 'Moltrasio', 'via Regina 26',
     45.8599702, 9.1012683,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Osteria Fernanda', 'Italia', 'Roma', 'via Crescenzo Del Monte 18/24',
     41.8754069, 12.466636,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Parma Rotta', 'Italia', 'Parma', 'strada Langhirano 158',
     44.7711267, 10.3244781,
     45.0, FALSE, FALSE,
     'Griglia', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Pomiroeu', 'Italia', 'Seregno', 'via Garibaldi 37',
     45.6512011, 9.2043109,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €
    ('Forentum', 'Italia', 'Lavello', 'piazza Plebiscito 16',
     41.0487815, 15.8005646,
     20.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Spin Ristorante-Enoteca', 'Italia', 'Genova', 'via Carlo Barabino 120 r',
     44.4005919, 8.9468644,
     45.0, FALSE, FALSE,
     'Ligure', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Refezione', 'Italia', 'Garbagnate Milanese', 'via Milano 166',
     45.5652961, 9.0854596,
     45.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Kuccagna', 'Italia', 'Dovera', 'via Milano 14',
     45.3822527, 9.5238907,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Marina del Nettuno', 'Italia', 'Messina', 'viale della Libertà-Batteria Masotto',
     38.197887, 15.558124,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Aimo e Nadia BistRo', 'Italia', 'Milano', 'via Matteo Bandello 14',
     45.4639032, 9.1673607,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Cielo', 'Italia', 'Ostuni', 'via Scipione Petrarolo 7',
     40.7344962, 17.5791501,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('I Sette Consoli', 'Italia', 'Orvieto', 'piazza Sant''Angelo 1/a',
     42.7186979, 12.1146421,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Hanswirt', 'Italia', 'Rablà', 'piazza Gerold 3',
     46.6693991, 11.0600354,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€
    ('Terrazza Costantino', 'Italia', 'Sclafani Bagni', 'rione Sant''Antonio 24',
     37.8236762, 13.8561003,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Locanda Perbellini', 'Italia', 'Milano', 'via Moscova 25',
     45.4767006, 9.1897053,
     45.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Ai Cadelach', 'Italia', 'Revine', 'via Grava 2',
     45.9975067, 12.2470269,
     45.0, FALSE, FALSE,
     'Veneziana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Al Covo', 'Italia', 'Venezia', 'campiello della Pescaria',
     45.434174, 12.3476275,
     85.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Capo Santa Chiara', 'Italia', 'Genova', 'via al Capo di Santa Chiara 69',
     44.38971, 8.974857,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Trattoria del Pesce', 'Italia', 'Roma', 'via Folco Portinari 27',
     41.8685979, 12.4494124,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Miranda', 'Italia', 'Riva di Solto', 'via Cornello 8',
     45.7810311, 10.0447336,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Manuelina', 'Italia', 'Recco', 'via Roma 296',
     44.3691057, 9.1501288,
     45.0, FALSE, FALSE,
     'Ligure', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Bagatto', 'Italia', 'Loano', 'via Ricciardi 24',
     44.1277571, 8.2597607,
     45.0, FALSE, FALSE,
     'Ligure', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria la Lanterna', 'Italia', 'Valsolda', 'via Finali 1',
     46.0244726, 9.0722789,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Oscar', 'Italia', 'Ardenza', 'via Franchini 78',
     43.516251, 10.3215893,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria Frangipane', 'Italia', 'Trani', 'via Maraldo da Trani 5',
     41.2783765, 16.4105077,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €
    ('Bacucco d''Oro', 'Italia', 'Mutignano', 'via del Pozzo 10',
     42.5885057, 14.0338289,
     20.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Acanto', 'Italia', 'Milano', 'piazza della Repubblica 17',
     45.4793466, 9.1964968,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Perla del Mare', 'Italia', 'San Vincenzo', 'via della Meloria 9',
     43.1061608, 10.539158,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Conchiglia d''Oro', 'Italia', 'Pineto', 'via Nazionale Adriatica nord',
     42.6142651, 14.0610184,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Le Grenier', 'Italia', 'Saint-Vincent', 'piazza Monte Zerbion 1',
     45.7515866, 7.6479615,
     160.0, FALSE, FALSE,
     'Cuisine from the Aosta Valley', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Baia del Silenzio', 'Italia', 'Sestri Levante', 'via Cappellini 9',
     44.2699348, 9.393904,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('The Cesar', 'Italia', 'Ladispoli', 'località Palo Laziale',
     41.933255, 12.1037651,
     85.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Vairo del Volturno', 'Italia', 'Vairano Patenora', 'via IV Novembre 58',
     41.336734, 14.128015,
     45.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Rio Bistrot', 'Italia', 'Riomaggiore', 'via San Giacomo 46',
     44.0989631, 9.7379136,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Mondschein', 'Italia', 'Sappada', 'borgata Palù 96',
     46.5611322, 12.6847131,
     85.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €
    ('Osteria La Sangiovesa', 'Italia', 'Santarcangelo di Romagna', 'piazza Simone Balacchi 14',
     44.0636873, 12.4443436,
     20.0, FALSE, FALSE,
     'Cuisine from Romagna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Alla Corte Lombarda', 'Italia', 'Mornago', 'via De Amicis 13',
     45.7463645, 8.7497849,
     45.0, FALSE, FALSE,
     'Lombardian', 'anna_m'),
    -- Selected Restaurants | €€
    ('I Savi', 'Italia', 'Scorzè', 'via Spangaro 6',
     45.5689488, 12.1719102,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Ora d''Aria', 'Italia', 'Firenze', 'via dei Georgofili 11r',
     43.7684515, 11.2547889,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda del Feudo', 'Italia', 'Castelvetro di Modena', 'via Trasversale 2',
     44.502912, 10.9427622,
     45.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Dama Restaurant', 'Italia', 'Venezia', 'Fondamenta Savorgnan 461',
     45.444402, 12.3236768,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('aede dining & wines', 'Italia', 'Roma', 'via Federico Cesi 22',
     41.9072612, 12.470643,
     45.0, FALSE, FALSE,
     'European Contemporary', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria a "Le Due Spade"', 'Italia', 'Trento', 'via Don Arcangelo Rizzi 11',
     46.0670041, 11.1203145,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €
    ('Poldo e Gianna Osteria', 'Italia', 'Roma', 'vicolo Rosini 6/7',
     41.9022264, 12.4772745,
     20.0, FALSE, FALSE,
     'Roman', 'marco_f'),
    -- Selected Restaurants | €€
    ('Benso', 'Italia', 'Forlì', 'piazza Cavour 7',
     44.224657, 12.0400813,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('L''Osteria di Santa Marina', 'Italia', 'Venezia', 'campo Santa Marina',
     45.4383688, 12.3390224,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Corte (Palazzolo sull''Oglio)', 'Italia', 'Palazzolo sull''Oglio', 'via San Pancrazio 41',
     45.6060502, 9.8981073,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Agli Amici Dopolavoro', 'Italia', 'Venezia', 'isola delle Rose',
     45.4054719, 12.3201218,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Ponte Pietra', 'Italia', 'Verona', 'via Ponte Pietra 34',
     45.44733, 10.999518,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Da Vincenzo', 'Italia', 'Positano', 'viale Pasitea 172/178',
     40.6278082, 14.485111,
     45.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Selected Restaurants | €
    ('La Notizia 53', 'Italia', 'Napoli', 'via Caravaggio 53/55',
     40.8357424, 14.2099366,
     20.0, FALSE, FALSE,
     'Pizza', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Osaka', 'Italia', 'Milano', 'corso Giuseppe Garibaldi 68',
     45.4763226, 9.1841301,
     85.0, FALSE, FALSE,
     'Giapponese', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Al Segnavento', 'Italia', 'Zelarino', 'via Gatta 76/c',
     45.5357421, 12.2159387,
     85.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€
    ('San Lorenzo', 'Italia', 'Spoleto', 'piazza Sordini 6',
     42.7344912, 12.7348655,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Da Annetta', 'Italia', 'Capolago', 'via Fè 25',
     45.7937434, 8.8075834,
     45.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Selected Restaurants | €
    ('La Palomba', 'Italia', 'Orvieto', 'via Cipriano Manente 16',
     42.718052, 12.108641,
     20.0, FALSE, FALSE,
     'Umbrian', 'marco_f'),
    -- Selected Restaurants | €€
    ('SaQua by Il Frantoio', 'Italia', 'Montescudaio', 'viale Vittorio Veneto 40',
     43.3249891, 10.6276167,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria Boccolicchio', 'Italia', 'Manfredonia', 'via Arco Boccolicchio 15',
     41.627777, 15.91779,
     45.0, FALSE, FALSE,
     'Apulian', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Arva', 'Italia', 'Venezia', 'calle Tiepolo',
     45.4370679, 12.3315175,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Locanda del Falco', 'Italia', 'Rivalta Trebbia', 'Castello di Rivalta 4',
     44.9499571, 9.5906482,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Ristorante Alpes & La FuGa', 'Italia', 'Sarentino', 'via Ronco 24',
     46.6243914, 11.3623949,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Zero Milano', 'Italia', 'Milano', 'corso Magenta 87',
     45.4658869, 9.166515,
     85.0, FALSE, FALSE,
     'Giapponese', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Mater Terrae', 'Italia', 'Roma', 'largo Febo 2',
     41.8998749, 12.4721696,
     160.0, FALSE, FALSE,
     'Vegetariana', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('La Leggenda dei Frati', 'Italia', 'Firenze', 'costa San Giorgio 6/a',
     43.764355, 11.25606,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Tosca', 'Italia', 'Casole d''Elsa', 'località Querceto',
     43.3249529, 11.0832378,
     160.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Rovello', 'Italia', 'Milano', 'Via Ariberto 3',
     45.4576004, 9.1748674,
     85.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Acaja', 'Italia', 'Pinerolo', 'corso Torino 106',
     44.8856657, 7.3353915,
     45.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Controcorrente (Morciano di Romagna)', 'Italia', 'Morciano di Romagna', 'via XXV Luglio 23',
     43.9124479, 12.6467282,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Trattoria Al Passo', 'Italia', 'Venezia', 'via Passo Campalto 118',
     45.4805719, 12.29823,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Bistrot (Rieti)', 'Italia', 'Rieti', 'piazza San Rufo 25',
     42.4021456, 12.8620159,
     45.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €
    ('Osteria del 36', 'Italia', 'Parma', 'Strada Aurelio Saffi 26/a',
     44.8023183, 10.3347971,
     20.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria Manzoni', 'Italia', 'Barzago', 'via Roma 87',
     45.75679, 9.31183,
     45.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Ansitz Romani', 'Italia', 'Termeno sulla Strada del Vino', 'via Andreas Hofer 23',
     46.3134785, 11.2187143,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Da Guido', 'Italia', 'Jesolo', 'via Roma Sinistra 25',
     45.5432677, 12.6507932,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Valle', 'Italia', 'Trofarello', 'via Umberto I 25',
     44.9861358, 7.7537482,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('La Sponda', 'Italia', 'Positano', 'via Colombo 30',
     40.6284968, 14.4880344,
     160.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Oberwirt', 'Italia', 'Marlengo', 'vicolo San Felice 2',
     46.65752, 11.14034,
     85.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Coccinella', 'Italia', 'Serravalle Langhe', 'via Provinciale 5',
     44.5579712, 8.0599063,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Le Lampare al Fortino', 'Italia', 'Trani', 'via Tiepolo',
     41.280422, 16.422161,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Braunwirt', 'Italia', 'Sarentino', 'piazza Chiesa 3',
     46.6427018, 11.3569038,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Boccadoro', 'Italia', 'Noventa', 'via della Resistenza 49',
     45.4230025, 11.9456731,
     45.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Locanda del Notaio', 'Italia', 'Pellio Intelvi', 'Via Piano delle Noci 42',
     45.9764501, 9.0443315,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Bon Wei', 'Italia', 'Milano', 'via Castelvetro 16/18',
     45.4846784, 9.1643511,
     45.0, FALSE, FALSE,
     'Chinese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Colonne', 'Italia', 'Santa Maria del Monte', 'via Fincarà 37',
     45.8606672, 8.7911375,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('MikEle', 'Italia', 'Maranello', 'via Flavio Gioia 1',
     44.5284906, 10.8683522,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Kleine Flamme', 'Italia', 'Vipiteno', 'via Cittanuova 31',
     46.8973392, 11.432459,
     85.0, FALSE, FALSE,
     'Fusion', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Lapprodo', 'Italia', 'Vibo Valentia Marina', 'via Roma 22',
     38.7161233, 16.1221031,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Bacco e Arianna', 'Italia', 'Trezzano sul Naviglio', 'via Circonvallazione 1',
     45.4197975, 9.0739012,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Chiocciola', 'Italia', 'Quartiere di Portomaggiore', 'via Runco 94/f',
     44.71051, 11.75692,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €
    ('Osteria Numero Sette', 'Italia', 'Rastignano', 'via Andrea Costa 7',
     44.4462, 11.3583,
     20.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('LeoneFelice Vista Lago', 'Italia', 'Erbusco', 'via Vittorio Emanuele 23',
     45.6017335, 9.9744178,
     85.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Da Giovanni', 'Italia', 'Cortina Vecchia', 'via Cortina 1040',
     44.8731723, 9.9243205,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Darmagi', 'Italia', 'Mercenasco', 'via Rivera 7',
     45.3515984, 7.8849938,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('L''A Gourmet L''Accademia', 'Italia', 'Reggio Calabria', 'via Largo C. Colombo 6',
     38.1139931, 15.6505925,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Fugascina Ristorante', 'Italia', 'Mergozzo', 'piazza Vittorio Veneto 8',
     45.9605376, 8.4492947,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Riso Amaro', 'Italia', 'Fondi', 'viale Regina Margherita 22',
     41.3581162, 13.4294783,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Al Capitan della Cittadella', 'Italia', 'Verona', 'piazza Cittadella 7/a',
     45.4369536, 10.9936108,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €
    ('Trattoria alla Pergola', 'Italia', 'Fagnano', 'via Nazario Sauro 9',
     45.2587117, 10.9414325,
     20.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Hosteria Giusti', 'Italia', 'Modena', 'via Farini 75',
     44.6472912, 10.92823,
     85.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Trattoria I Masenini', 'Italia', 'Verona', 'via Roma 34',
     45.4394132, 10.9885667,
     45.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Giorgio e Flora', 'Italia', 'Velo', 'via Baldonò 1',
     45.7963, 11.34109,
     45.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Quellenhof Gourmetstube 1897', 'Italia', 'Saint Martin in Passeier', 'via Passiria 47',
     46.7459911, 11.2044911,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Marcandole', 'Italia', 'Salgareda', 'via Argine Piave 7',
     45.6985092, 12.4785183,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €
    ('Gino Sorbillo', 'Italia', 'Napoli', 'via dei Tribunali 32',
     40.8503612, 14.2552584,
     20.0, FALSE, FALSE,
     'Pizza', 'marco_f'),
    -- Selected Restaurants | €€
    ('Menegaldo', 'Italia', 'Monastier di Treviso', 'via Pralongo 216',
     45.645096, 12.485602,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Al Fogher', 'Italia', 'Piazza Armerina', 'strada statale 117 bis',
     37.41098, 14.383366,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Trattoria di Coronate', 'Italia', 'Morimondo', 'Cascina Coronate',
     45.3456746, 8.9657392,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Muraglia - Conchiglia d''Oro', 'Italia', 'Varigotti', 'via Aurelia 133',
     44.1817608, 8.3955959,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Gratschwirt', 'Italia', 'Toblach', 'via Grazze 1',
     46.7310953, 12.2045905,
     85.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€€
    ('L''Archivolto - Osteria Nostrale', 'Italia', 'Ovada', 'piazza Garibaldi 25/26',
     44.6401551, 8.6480495,
     85.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €
    ('Trita Pepe', 'Italia', 'Manoppello Scalo', 'via Gabriele D''Annunzio 4',
     42.30439, 14.054071,
     20.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Bistrot de Venise', 'Italia', 'Venezia', 'calle dei Fabbri 4685',
     45.435544, 12.3364804,
     160.0, FALSE, FALSE,
     'Veneziana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Io Osteria Personale', 'Italia', 'Firenze', 'Borgo San Frediano 167r',
     43.77038, 11.24062,
     45.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Hidalgo', 'Italia', 'Postal', 'via Roma 7',
     46.61744, 11.18844,
     45.0, FALSE, FALSE,
     'Griglia', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Martinatica', 'Italia', 'Pietrasanta', 'via Martinatica 20',
     43.9486764, 10.2424169,
     85.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Rezzano Cucina e Vino', 'Italia', 'Sestri Levante', 'via Asilo Maria Teresa 34',
     44.2719487, 9.3950266,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Melograno', 'Italia', 'Trani', 'via Bovio 193',
     41.27565, 16.41995,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Villa Giulia - Al Terrazzo', 'Italia', 'Valmadrera', 'via Parè 73',
     45.860847, 9.365469,
     85.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Umberto a Mare', 'Italia', 'Forio', 'via del Soccorso 8',
     40.7370555, 13.8550343,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Da Gigi', 'Italia', 'Crandola Valsassina', 'piazza IV Novembre 4',
     46.0225142, 9.3789354,
     45.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Alla Laguna - Vedova Raddi', 'Italia', 'Marano Lagunare', 'piazza Garibaldi 1',
     45.7631856, 13.1655106,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Corte Visconti', 'Italia', 'Somma Lombardo', 'via Roma 9',
     45.6838676, 8.7063043,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('La Terrazza (Rome)', 'Italia', 'Roma', 'via Ludovisi 49',
     41.9063787, 12.4860308,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Corte Sconta', 'Italia', 'Venezia', 'calle del Pestrin',
     45.4347913, 12.3479192,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Trattoria Glisenti', 'Italia', 'Vello', 'via Provinciale 34',
     45.7564348, 10.0790684,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Sapereta', 'Italia', 'Porto Azzurro', 'via Provinciale Ovest 73',
     42.760967, 10.370911,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Filanda (Asola)', 'Italia', 'Asola', 'via Carducci 21/e',
     45.2198465, 10.4064164,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Due Pini', 'Italia', 'Madonna di Campiglio', 'via Spinale 37/bis',
     46.2282, 10.82951,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Enoteca Del Duca', 'Italia', 'Volterra', 'via di Castello 2',
     43.401191, 10.8602737,
     45.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €
    ('La Cantinella', 'Italia', 'Montemarciano', 'località Montemarciano 70/g',
     43.587955, 11.6099216,
     20.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Lumira', 'Italia', 'Castelfranco Emilia', 'corso Martiri 74',
     44.5935952, 11.057083,
     45.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Selected Restaurants | €
    ('Trattoria Ceriati', 'Italia', 'Salsomaggiore Terme', 'Località Cangelasio Ceriati 18',
     44.8130607, 9.9396271,
     20.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Villa Arcadio', 'Italia', 'Salò', 'via Palazzina 2',
     45.59214, 10.50393,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Cascina Vittoria', 'Italia', 'Rognano', 'via Roma 26',
     45.2894891, 9.0895631,
     85.0, FALSE, FALSE,
     'Tradizionale', 'marco_f'),
    -- Selected Restaurants | €
    ('Il Favri', 'Italia', 'San Giorgio della Richinvelda', 'via Borgo Meduna 12',
     46.0373064, 12.8233123,
     20.0, FALSE, FALSE,
     'Friulian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Trillo', 'Italia', 'Massa', 'via Bergiola Vecchia 30',
     44.04829, 10.139887,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('I Tigli', 'Italia', 'San Bonifacio', 'via Camporosolo 11',
     45.3947884, 11.270984,
     45.0, FALSE, FALSE,
     'Pizza', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Tola Rasa', 'Italia', 'Padova', 'via Vicenza 7',
     45.4116299, 11.8628222,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Pacifico', 'Italia', 'Milano', 'via Moscova 29',
     45.4768452, 9.1885942,
     85.0, FALSE, FALSE,
     'Peruvian', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Cantina di Manuela', 'Italia', 'Milano', 'via Carlo Poerio 3',
     45.46841, 9.2102,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('Voltalacarta', 'Italia', 'Genova', 'via Assarotti 60 r',
     44.4111952, 8.9407747,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Opera|02', 'Italia', 'Levizzano Rangone', 'via Medusia 32',
     44.4980553, 10.9174599,
     85.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Filanda (Manciano)', 'Italia', 'Manciano', 'via Marsala 8',
     42.5859791, 11.5154382,
     45.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €
    ('Da Noemi', 'Italia', 'Ferrara', 'via Ragno 31/a',
     44.8330924, 11.618959,
     20.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Da Bibe', 'Italia', 'Galluzzo', 'via delle Bagnese 1r',
     43.7429996, 11.2153083,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Il Liberty', 'Italia', 'Milano', 'viale Monte Grappa 6',
     45.4804651, 9.1912581,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Punta Lena', 'Italia', 'Stromboli', 'via Monsignor Di Mattina 8',
     38.8054864, 15.2402537,
     45.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Merlo', 'Italia', 'Lido di Camaiore', 'via Bernardini 660',
     43.9069174, 10.2154761,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Battipalo', 'Italia', 'Lesa', 'viale Vittorio Veneto 2',
     45.8289297, 8.5659778,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Capestrano', 'Italia', 'Milano', 'via Gian Francesco Pizzi 14',
     45.436924, 9.201944,
     45.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Meltemi', 'Italia', 'Parma', 'piazzale Carbone 3',
     44.8004168, 10.3284359,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Trattoria del Vicolo', 'Italia', 'Lipari', 'vico Ulisse 17',
     38.469032, 14.954721,
     45.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Casa D''Angelo', 'Italia', 'Fara Filiorum Petri', 'via San Nicola 5',
     42.2478801, 14.1832014,
     45.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Ba Restaurant', 'Italia', 'Milano', 'via Raffaello Sanzio 22',
     45.468098, 9.151806,
     85.0, FALSE, FALSE,
     'Chinese', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Taverna Kus', 'Italia', 'San Zeno di Montagna', 'contrada Castello 14',
     45.6424504, 10.7347888,
     85.0, FALSE, FALSE,
     'Km Zero', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Taverna di Fra'' Fiusch', 'Italia', 'Revigliasco', 'via Beria 32',
     45.0184894, 7.7359813,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Il Ristorante - Niko Romito (Milan)', 'Italia', 'Milano', 'via Privata Fratelli Gabba 7/b',
     45.470812, 9.1897291,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Orto', 'Italia', 'Monopoli', 'contrada Tortorella',
     40.8565538, 17.288577,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Profumi di Cous Cous', 'Italia', 'San Vito lo Capo', 'via Regina Margherita 80',
     38.1751048, 12.7358368,
     45.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Da Nicolo', 'Italia', 'Carloforte', 'corso Cavour 32',
     39.1468203, 8.3080589,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Lo Scalco Grasso', 'Italia', 'Mantua', 'via Trieste 55',
     45.1534154, 10.7963241,
     45.0, FALSE, FALSE,
     'Mantuan', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Ca'' Apollonio Gourmet', 'Italia', 'Romano d''Ezzelino', 'via Molinetto 5/a',
     45.7856666, 11.7574494,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda dei Salinari', 'Italia', 'Cervia', 'via XX Settembre 67',
     44.260866, 12.3504612,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Essenziale', 'Italia', 'Firenze', 'piazza di Cestello 3 r',
     43.7703309, 11.2435173,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('L''Antico Giardino', 'Italia', 'Ravalle', 'via Martelli 28',
     44.92783, 11.51125,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Stella d''Italia', 'Italia', 'Pastrengo', 'piazza Carlo Alberto 25',
     45.4925531, 10.7993215,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Braseria', 'Italia', 'Osio Sotto', 'via Risorgimento 15',
     45.615921, 9.5914181,
     45.0, FALSE, FALSE,
     'Meats and Grills', 'marco_f'),
    -- Selected Restaurants | €€
    ('De Gustibus - Maurizio', 'Italia', 'Palmi', 'viale delle Rimembranze 58/60',
     38.3556846, 15.8466654,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Baccanti', 'Italia', 'Matera', 'via Sant''Angelo 58/61',
     40.665718, 16.6125726,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Botero', 'Italia', 'Crema', 'via Giuseppe Verdi 7',
     45.3634877, 9.6842952,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Lipadusa', 'Italia', 'Lampedusa', 'via Bonfiglio 16',
     35.5038155, 12.6116345,
     45.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Pierino Penati', 'Italia', 'Vigano', 'via XXIV Maggio 36',
     45.72778, 9.3263,
     85.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€
    ('Da Odino', 'Italia', 'Quarto d''Altino', 'via Roma 87',
     45.5843, 12.35232,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Gritta', 'Italia', 'Palau', 'Vicolo del Faro',
     41.1858812, 9.3800602,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Al Paradiso', 'Italia', 'Paradiso di Pocenia', 'via Sant''Ermacora 1',
     45.8679453, 13.1455303,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€
    ('Sibilla', 'Italia', 'Tivoli', 'via della Sibilla 50',
     41.9666953, 12.8003138,
     45.0, FALSE, FALSE,
     'Tradizionale', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Mildas', 'Italia', 'Giustino', 'via Rosmini 7',
     46.1510361, 10.7676124,
     85.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Degusteria del Gigante', 'Italia', 'San Benedetto del Tronto', 'via degli Anelli 19',
     42.9511109, 13.8754968,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Next2', 'Italia', 'Positano', 'via Pasitea 242',
     40.6284701, 14.4816249,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Fiaschetteria', 'Italia', 'Besenzone', 'via Bersano 59/b',
     44.9829442, 10.011612,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Balin Sestri Levante', 'Italia', 'Sestri Levante', 'viale Rimembranza 33',
     44.27093, 9.393551,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €
    ('Osteria dalla Peppa', 'Italia', 'Fano', 'via Vecchia 8',
     43.8406532, 13.0191918,
     20.0, FALSE, FALSE,
     'Cuisine from the Marches', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Cascina 1899', 'Italia', 'Rocella Jonica', 'strada statale 106',
     38.31031, 16.37146,
     45.0, FALSE, FALSE,
     'Calabrian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Corsaro Nero', 'Italia', 'Marina di Arbus', 'Località Portu Maga',
     39.5779213, 8.4661307,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Crotto Valtellina', 'Italia', 'Malnate', 'via Fiume 11',
     45.8023266, 8.8712122,
     85.0, FALSE, FALSE,
     'Cuisine from Valtellina', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Cavalluccio Marino', 'Italia', 'Lampedusa', 'contrada Cala Croce 3',
     35.4994591, 12.5954512,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Nicolin', 'Italia', 'Lecco', 'via Paisiello 4',
     45.835037, 9.414134,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Tre Galline', 'Italia', 'Torino', 'via Bellezia 37',
     45.075979, 7.6818915,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Fiorfiore', 'Italia', 'Todi', 'località Chioano',
     42.7859793, 12.4477153,
     45.0, FALSE, FALSE,
     'Umbrian', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Osteria 1126', 'Italia', 'Poggibonsi', 'località Cinciano 2',
     43.506123, 11.176454,
     20.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Casa del Nonno 13', 'Italia', 'Mercato San Severino', 'via Caracciolo 13',
     40.7689224, 14.7048003,
     85.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Estro Vino e Cucina', 'Italia', 'Venezia', 'calle Crosera',
     45.4360388, 12.3250367,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Christian & Manuel', 'Italia', 'Vercelli', 'corso Magenta 71',
     45.3198162, 8.4327203,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Duo Ristorante', 'Italia', 'Lecce', 'via Giuseppe Garibaldi 11',
     40.3561839, 18.1754414,
     85.0, FALSE, FALSE,
     'Apulian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Gatto Rosso', 'Italia', 'Taranto', 'via Cavour 2',
     40.474326, 17.238965,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Umberto De Martino', 'Italia', 'San Paolo d''Argon', 'via Madonna d''Argon 4/6',
     45.6939, 9.804207,
     160.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Rifugio Fuciade', 'Italia', 'San Pellegrino', 'località Fuciada - Soraga di Fassa',
     46.3941786, 11.8290076,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Capriolino', 'Italia', 'Vodo di Cadore', 'via Nazionale 108',
     46.421549, 12.2410254,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Cucina Bacilieri', 'Italia', 'Ferrara', 'via Terranuova 60',
     44.833508, 11.622814,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Al Gatto Nero', 'Italia', 'Burano', 'via Giudecca 88',
     45.4847718, 12.4163344,
     85.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Meridiana', 'Italia', 'Domodossola', 'via Rosmini 11',
     46.1143171, 8.2906172,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('SaleGrosso (Milano Marittima)', 'Italia', 'Milano Marittima', 'viale II Giugno 15',
     44.2685332, 12.3543831,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Satricvm', 'Italia', 'Le Ferriere', 'strada Nettunense 1227',
     41.511547, 12.742595,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Cocchi', 'Italia', 'Parma', 'via Gramsci 16/a',
     44.805573, 10.30785,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Parizzi', 'Italia', 'Parma', 'strada della Repubblica 71',
     44.7996181, 10.3367404,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Antica Locanda del Villoresi', 'Italia', 'Nerviano', 'via Sempione 4',
     45.56193, 8.96935,
     45.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €
    ('Le Viole', 'Italia', 'Castelnovo di Baganzola', 'strada nuova di Castelnuovo 60/a',
     44.866642, 10.327356,
     20.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Basilisco', 'Italia', 'Treviso', 'via Bison 34',
     45.665562, 12.2693492,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €
    ('Da Concettina ai Tre Santi', 'Italia', 'Napoli', 'via Arena alla Sanità 7 bis',
     40.8574297, 14.2531973,
     20.0, FALSE, FALSE,
     'Pizza', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Ma.Ri.Na.', 'Italia', 'Olgiate Olona', 'piazza San Gregorio 11',
     45.6342966, 8.8883882,
     160.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Franceschetta 58', 'Italia', 'Modena', 'strada Vignolese 58',
     44.638753, 10.9316204,
     45.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Selected Restaurants | €€
    ('Sa Corte', 'Italia', 'Oliena', 'via Nuoro 138',
     40.282924, 9.3976647,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Regio Patio', 'Italia', 'Garda', 'via San Francesco d''Assisi 23',
     45.5742513, 10.7082466,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Teresa', 'Italia', 'Pegli', 'piazza Lido di Pegli 5 r',
     44.4244448, 8.803161,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €
    ('Il Mecenate', 'Italia', 'Lucca', 'via del Fosso 94',
     43.8456709, 10.5101093,
     20.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Osteria del Guà', 'Italia', 'Bagnolo', 'via Risaie 1/2',
     45.3575174, 11.37135,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Ristorante 1500', 'Italia', 'San Vigilio', 'via Pavicolo 43',
     46.6211118, 11.140872,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Vino Buono', 'Italia', 'Grumello del Monte', 'via Castello 20',
     45.6366149, 9.8714751,
     45.0, FALSE, FALSE,
     'Lombardian', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Finger''s Garden', 'Italia', 'Milano', 'via Keplero 2',
     45.4927692, 9.1985423,
     85.0, FALSE, FALSE,
     'Fusion', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Emilio', 'Italia', 'Fermo', 'via Girardi 1',
     43.210065, 13.7824585,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('da Omar', 'Italia', 'Lido di Jesolo', 'via Dante Alighieri 21',
     45.5069144, 12.6472815,
     160.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Belle Parti', 'Italia', 'Padova', 'via Belle Parti 11',
     45.408798, 11.8742425,
     45.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Campiello', 'Italia', 'San Giovanni al Natisone', 'via Nazionale 46',
     45.9723045, 13.398605,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('L''Acchiatura', 'Italia', 'Racale', 'via Marzani 12',
     39.96073, 18.09482,
     45.0, FALSE, FALSE,
     'Apulian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Degli Angeli', 'Italia', 'Magliano Sabina', 'località Madonna degli Angeli',
     42.38605, 12.49566,
     45.0, FALSE, FALSE,
     'Cuisine from Lazio', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Trattoria al Ponte', 'Italia', 'Lusia', 'via Bertolda 27',
     45.0819, 11.66184,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Palazzo Petrucci', 'Italia', 'Napoli', 'via Posillipo 16 b/c',
     40.8217366, 14.2151622,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Pozzo', 'Italia', 'Reggio Emilia', 'viale Allegri 7',
     44.70167, 10.6293,
     45.0, FALSE, FALSE,
     'Tradizionale', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Saletta', 'Italia', 'Alghero', 'via fratelli Kennedy 27/b',
     40.5544048, 8.317356,
     85.0, FALSE, FALSE,
     'Sarda', 'anna_m'),
    -- Selected Restaurants | €€€
    ('A Spurcacciun-a', 'Italia', 'Savona', 'via Nizza 41 r',
     44.2903777, 8.4530366,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Aquadulcis', 'Italia', 'Vallo della Lucania', 'contrada Tenda',
     40.2185852, 15.2731101,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Langosteria', 'Italia', 'Milano', 'via Savona 10',
     45.4555197, 9.1682937,
     160.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Perla', 'Italia', 'Varese', 'via Carrobbio 19',
     45.8160744, 8.8255591,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Maxi', 'Italia', 'Vico Equense', 'via Luigi Serio 8',
     40.6750958, 14.4358353,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Punto G', 'Italia', 'Monza', 'via Gian Francesco Parravicini 34',
     45.5839266, 9.2704495,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('L''Ambasciata', 'Italia', 'Quistello', 'piazzetta Ambasciatori del Gusto 1',
     45.0092085, 10.9793406,
     85.0, FALSE, FALSE,
     'Mantuan', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Marixx', 'Italia', 'Ventimiglia', 'Passeggiata Marconi 5',
     43.7903443, 7.5981458,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda Nelli', 'Italia', 'Pietra Ligure', 'via Vittorio Veneto 15',
     44.1493177, 8.2829625,
     45.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('I Giardini dei Rodo', 'Italia', 'Pantelleria', 'via Bonomo Alto - Scauri',
     36.7594738, 11.9891114,
     45.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- Selected Restaurants | €
    ('La Cucina dei Frigoriferi Milanesi', 'Italia', 'Milano', 'via Piranesi 10',
     45.460635, 9.2260824,
     20.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria Borgo Ronche', 'Italia', 'Fontanafredda', 'via Silvio Pellico 54',
     45.97329, 12.586193,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Da Nando', 'Italia', 'Mortegliano', 'via Divisione Julia 14',
     45.9453382, 13.1776686,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Osteria Bellavista', 'Italia', 'Paderno del Grappa', 'via Piovega 30',
     45.8125945, 11.8696986,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria Il Principe e Il Pirata', 'Italia', 'Pantelleria', 'località Punta Karace 7',
     36.8095164, 12.0297886,
     45.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Gong', 'Italia', 'Milano', 'corso Concordia 8',
     45.4677288, 9.2085964,
     160.0, FALSE, FALSE,
     'Chinese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Ruris', 'Italia', 'Isola di Capo Rizzuto', 'Via Spiaggie Rosse',
     38.935787, 17.10719,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Le Due Lanterne', 'Italia', 'Nizza Monferrato', 'piazza Garibaldi 52',
     44.7731085, 8.3531213,
     20.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Ai Campi di Marcello', 'Italia', 'Monfalcone', 'via Napoli 11',
     45.7994807, 13.5334935,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Armani/Ristorante', 'Italia', 'Milano', 'via Manzoni 31',
     45.4704, 9.19308,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Al Cjasal', 'Italia', 'San Michele al Tagliamento', 'via Nazionale 30',
     45.7844849, 12.9721736,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Terrazza Fiorella', 'Italia', 'Massa Lubrense', 'via Vincenzo Maggio 5',
     40.6113139, 14.3406507,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Al Tonno di Corsa', 'Italia', 'Carloforte', 'via Marconi 47',
     39.1468581, 8.3071201,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('A'' Cuncuma', 'Italia', 'Palermo', 'via Judica 21/23',
     38.11678, 13.356125,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Soprattutto', 'Italia', 'Santa Maria di Sala', 'via Noalese 124',
     45.5074704, 12.0307818,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('MoS', 'Italia', 'Desenzano del Garda', 'via Porto Vecchio 28',
     45.4707498, 10.5400444,
     45.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Gina', 'Italia', 'Aosta', 'via Croce di Città 25',
     45.7367641, 7.3167877,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Secondo Tempo', 'Italia', 'Termini Imerese', 'via Vittorio Amedeo 55',
     37.9848444, 13.693866,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Motelombroso', 'Italia', 'Milano', 'Alzaia Naviglio Pavese 256',
     45.4268405, 9.1695741,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Osteria Billis', 'Italia', 'Tortona', 'viale Piave 5',
     44.8995569, 8.8623259,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('DaV  Mare', 'Italia', 'Portofino', 'via Roma 2',
     44.3032249, 9.2093075,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('MaMe Restaurant', 'Italia', 'Viareggio', 'via Michele Coppino 56',
     43.8644236, 10.2504123,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda Marchesani', 'Italia', 'Pomezia', 'piazza Bellini 13',
     41.6737693, 12.49847,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Aria', 'Italia', 'Lavagna', 'Via Costa 18',
     44.30022, 9.37766,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Rada Rooftop', 'Italia', 'Positano', 'via Grotte dell''Incanto 51',
     40.6274841, 14.4893297,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda La Raia', 'Italia', 'Gavi', 'Località Lomellina 26',
     44.71576, 8.7983716,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Specus', 'Italia', 'Valmontone', 'Via Casilina 315',
     41.7754172, 12.9239378,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Cucine Nervi', 'Italia', 'Gattinara', 'corso Vercelli 117',
     45.61078, 8.37214,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Olio', 'Italia', 'Origgio', 'SP233 Varesina 1',
     45.6004849, 9.0282762,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Da Carla', 'Italia', 'Gambolò', 'via Necchi 3/5',
     45.26507, 8.92589,
     45.0, FALSE, FALSE,
     'Lombardian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Hosteria Toblino', 'Italia', 'Sarche di Madruzzo', 'via Garda 3',
     46.0451034, 10.952054,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Jamantè', 'Italia', 'Polignano a Mare', 'via San Vito 97',
     40.9978139, 17.2126493,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Gimmi Restaurant', 'Italia', 'Lecce', 'via San Pietro in Lama 23',
     40.3435723, 18.1570943,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Filo', 'Italia', 'Lezzeno', 'SP 583 - Località Bagnana 96',
     45.9452961, 9.1867367,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Dry Aged', 'Italia', 'Milano', 'via Cesare Da Sesto 1',
     45.4568763, 9.1737929,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Hydra', 'Italia', 'Salerno', 'via Antonio Mazza 30',
     40.6784594, 14.7607543,
     45.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Bottega Culinaria', 'Italia', 'San Vito Chietino', 'contrada Pontoni 72',
     42.3029465, 14.4259026,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Antica Trattoria Centro', 'Italia', 'Levanto', 'corso Italia 25',
     44.1693062, 9.6100282,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Bebop', 'Italia', 'Palermo', 'via Riccardo Wagner 3',
     38.1241378, 13.3584633,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Corbezzoli', 'Italia', 'Bologna', 'via Altura 11 bis',
     44.4625741, 11.3944139,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Augurio', 'Italia', 'Trento', 'via Dietro le Mura B 16',
     46.068051, 11.1265957,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Radici (Modica)', 'Italia', 'Modica', 'via Grimaldi 55',
     36.8603153, 14.7603888,
     45.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('L˜ARIA', 'Italia', 'Blevio', 'via Enrico Caronti 69',
     45.8489326, 9.1081554,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('San Domenico (Pizzo Calabro)', 'Italia', 'Pizzo Calabro', 'via Colapesce 2',
     38.7368891, 16.1636667,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €
    ('Antica Trattoria Giovanelli', 'Italia', 'Sarturano', 'via Centrale 5',
     44.974308, 9.5089055,
     20.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€
    ('iPalici', 'Italia', 'Viagrande', 'via Antonello da Messina 3',
     37.615884, 15.1000181,
     85.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Modì', 'Italia', 'Torregrotta', 'via Bucceri',
     38.1946117, 15.356928,
     85.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Opera', 'Italia', 'Torino', 'via Sant''Antonio da Padova 3',
     45.0684854, 7.6662476,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Autem*', 'Italia', 'Milano', 'via Serviliano Lattuada 2',
     45.4534577, 9.2040052,
     85.0, FALSE, FALSE,
     'Km Zero', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Canova Restaurant', 'Italia', 'Venezia', 'sestiere San Marco 1243',
     45.43326, 12.33718,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('L''Insolita Trattoria Tre Soldi', 'Italia', 'Firenze', 'via Gabriele d''Annunzio 4r/a',
     43.7750043, 11.2889584,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Rear Restaurant', 'Italia', 'Nola', 'SS 7bis Km 50',
     40.9326272, 14.5122565,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Quel Fantastico Giovedì', 'Italia', 'Ferrara', 'via Castelnuovo 9',
     44.831357, 11.6197083,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Le Cicale in Città', 'Italia', 'Genova', 'via Macaggi 53',
     44.40222, 8.94195,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Maison de Filip', 'Italia', 'Trento', 'piazzetta Nicolò Rasmo 7',
     46.0711527, 11.1247675,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Duo (Chiavari)', 'Italia', 'Chiavari', 'via Senatore Dallorso 10',
     44.3174523, 9.3232846,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Hazama', 'Italia', 'Milano', 'via Savona 41',
     45.4534407, 9.1619599,
     160.0, FALSE, FALSE,
     'Giapponese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Il Marchese - Osteria Mercato Liquori (Milan)', 'Italia', 'Milano', 'via dei Bossi 3',
     45.4675105, 9.1865103,
     85.0, FALSE, FALSE,
     'Roman', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('GioEle', 'Italia', 'Stradella', 'via Mazzini 26',
     45.0771712, 9.3021182,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€
    ('Radici Osteria Contemporanea', 'Italia', 'Gallarate', 'via Giuseppe Mazzini 13',
     45.6603438, 8.7906603,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Aprudia', 'Italia', 'Giulianova', 'largo del Forno 16',
     42.7508655, 13.957713,
     85.0, FALSE, FALSE,
     'Km Zero', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Rueda Gaucha', 'Italia', 'Lignano Sabbiadoro', 'viale Europa 18',
     45.6890181, 13.1319961,
     45.0, FALSE, FALSE,
     'Griglia', 'anna_m'),
    -- Selected Restaurants | €€
    ('Antica Macelleria Cecchini - Solociccia', 'Italia', 'Panzano', 'via Chiantigiana 5',
     43.5445111, 11.3164788,
     45.0, FALSE, FALSE,
     'Meats and Grills', 'marco_f'),
    -- Selected Restaurants | €
    ('Brindo', 'Italia', 'Cusago', 'via Libertà 18',
     45.44692, 9.03405,
     20.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Ceresio 7', 'Italia', 'Milano', 'via Ceresio 7',
     45.4838968, 9.1801177,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €
    ('L''Aia dei Cappellani', 'Italia', 'Trecchina', 'contrada Maurino',
     40.0353088, 15.7820677,
     20.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Serendepico', 'Italia', 'Capannori', 'via della Chiesa di Gragnano 36',
     43.8723544, 10.6239469,
     45.0, FALSE, FALSE,
     'Fusion', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Caffè Dante Bistrot', 'Italia', 'Verona', 'piazza dei Signori 2',
     45.44355, 10.99831,
     45.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Locanda Sensi', 'Italia', 'Rivergaro', 'località Case Negri 116',
     44.8948534, 9.6081628,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Le Cedrare', 'Italia', 'Illasi', 'stradone Roma 8',
     45.4696236, 11.1817991,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria da Pietro', 'Italia', 'Castiglione delle Stiviere', 'via Chiassi 19',
     45.3929003, 10.4898173,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€
    ('Il Convito della Venaria', 'Italia', 'Venaria Reale', 'via Andrea Mensa 37/g',
     45.1351493, 7.6258865,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Wicky''s Innovative Japanese Cuisine', 'Italia', 'Milano', 'corso Italia 6',
     45.4598093, 9.1879947,
     85.0, FALSE, FALSE,
     'Giapponese', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Piazza dei Mestieri', 'Italia', 'Torino', 'via Jacopo Durandi 13',
     45.081818, 7.659965,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('CiPASSO', 'Italia', 'Roma', 'via Metastasio 21',
     41.9020011, 12.4759493,
     45.0, FALSE, FALSE,
     'Roman', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Osteria alle Testiere', 'Italia', 'Venezia', 'calle del Mondo Novo',
     45.4370846, 12.3402061,
     85.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Bacchus', 'Italia', 'Olbia', 'via degli Astronauti 2',
     40.908688, 9.513562,
     45.0, FALSE, FALSE,
     'Sarda', 'anna_m'),
    -- Selected Restaurants | €€
    ('Maso Runch-Hof', 'Italia', 'Pedraces', 'via Runch 11',
     46.6116075, 11.8885636,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('Impronta (Albairate)', 'Italia', 'Albairate', 'via Pisani Dossi 28',
     45.422034, 8.9378778,
     45.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Antica Osteria Moirago', 'Italia', 'Moirago', 'via Pavese 4',
     45.3693364, 9.1370235,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Cuculia', 'Italia', 'Firenze', 'via dei Serragli 3r',
     43.7687552, 11.2462521,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Locanda Viola', 'Italia', 'Pagazzano', 'via Morengo 164',
     45.532284, 9.6742914,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('AlpiNN', 'Italia', 'Brunico', 'Plan de Corones',
     46.7384367, 11.9557234,
     85.0, FALSE, FALSE,
     'Alpine', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Duo (San Felice del Benaco)', 'Italia', 'San Felice del Benaco', 'via Cavour 7',
     45.5858285, 10.5466283,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Senso Alfio Ghezzi Lake Garda', 'Italia', 'Limone sul Garda', 'via IV Novembre 86',
     45.824318, 10.812279,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Al Petes', 'Italia', 'Trieste', 'Via dei Capitelli 5/a',
     45.6480887, 13.7672762,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Foresteria', 'Italia', 'Menfi', 'Contrada Passo di Gurra  SP 79 km 91',
     37.5995162, 12.9223075,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Ca'' del Profeta', 'Italia', 'Montaldo Scarampi', 'via Montaldino 19',
     44.8241263, 8.252505,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('i-Fame', 'Italia', 'Rimini', 'viale Regina Elena 28',
     44.0600315, 12.5892022,
     45.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Kirchsteiger', 'Italia', 'Foiana', 'via prevosto Wieser 5',
     46.5889205, 11.1462829,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('Da Orlando', 'Italia', 'Cusago', 'piazza Soncino 19',
     45.4457731, 9.0336515,
     45.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Da Ercole', 'Italia', 'Crotone', 'viale Gramsci 122',
     39.07291, 17.1296597,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Rosa dei Venti', 'Italia', 'Milano', 'via Piero della Francesca 34',
     45.4836808, 9.1643696,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('I Due Cippi dal 1976', 'Italia', 'Saturnia', 'piazza Veneto 26/a',
     42.664566, 11.504727,
     85.0, FALSE, FALSE,
     'Meats and Grills', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('L''Ancora della Tortuga', 'Italia', 'Monterosso al Mare', 'salita Cappuccini 4',
     44.1458615, 9.6544181,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Taverna dello Spuntino', 'Italia', 'Grottaferrata', 'via Cicerone 20',
     41.789047, 12.668739,
     45.0, FALSE, FALSE,
     'Cuisine from Lazio', 'marco_f'),
    -- Selected Restaurants | €€
    ('Piacentino', 'Italia', 'Bobbio', 'piazza San Francesco 19',
     44.7690632, 9.3869786,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Le Logge del Vignola', 'Italia', 'Montepulciano', 'via delle Erbe 6',
     43.0951163, 11.7826219,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Tubladel', 'Italia', 'Ortisei', 'via Trebinger 22',
     46.5706268, 11.6789715,
     85.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Corniola', 'Italia', 'Pescocostanzo', 'via dei Mastri Lombardi 24',
     41.8871637, 14.0692187,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Terre Alte', 'Italia', 'Longiano', 'via Olmadella 11',
     44.090206, 12.337544,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Antico Morer', 'Italia', 'Treviso', 'via Riccati 28',
     45.6671, 12.24052,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Ca'' Mia', 'Italia', 'Alserio', 'via Cascinette 1',
     45.7770357, 9.1962493,
     45.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Dulchemente', 'Italia', 'Olbia', 'via Romeo Papandrea 10',
     40.925562, 9.5027418,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Piccolo Lord', 'Italia', 'Torino', 'corso San Maurizio 69 bis/g',
     45.0672734, 7.6974119,
     45.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Villa Fiordaliso', 'Italia', 'Gardone Riviera', 'corso Zanardelli 150',
     45.622208, 10.56996,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria Antico Brolo', 'Italia', 'Gardone Riviera', 'via Carere 10',
     45.6221753, 10.5619225,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Osteria del Borro', 'Italia', 'San Giustino Valdarno', 'località Borro 52',
     43.5403299, 11.7152709,
     85.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Capriccio (Augusta)', 'Italia', 'Augusta', 'via Filippo Turati 81',
     37.2473395, 15.2202918,
     45.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Sora Maria e Arcangelo', 'Italia', 'Olevano Romano', 'via Roma 42',
     41.8596733, 13.0343862,
     45.0, FALSE, FALSE,
     'Cuisine from Lazio', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Al Vecchio Convento', 'Italia', 'Varese', 'viale Borri 348',
     45.7892898, 8.8494638,
     85.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Le Cupole', 'Italia', 'Rapallo', 'via Aurelia Orientale 369',
     44.3433058, 9.2445572,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Filia Ristorante', 'Italia', 'Verona', 'via Francesco Anzani 19',
     45.4469573, 10.9894087,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Tamo', 'Italia', 'Spoltore', 'via del Mulino 6',
     42.455208, 14.1331166,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Gunè San Frediano', 'Italia', 'Firenze', 'via del Drago d''Oro 1r',
     43.7698742, 11.2427247,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Milano', 'Italia', 'Pallanza', 'corso Zanitello 2',
     45.9206695, 8.5538107,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('MARdiVINO', 'Italia', 'Treviso', 'strada del Nascimben 1/a',
     45.6487674, 12.2357736,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Kosmo Taste the Mountain', 'Italia', 'Livigno', 'via Bondi 473/a',
     46.5373174, 10.1418221,
     45.0, FALSE, FALSE,
     'Alpine', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Gainn', 'Italia', 'Roma', 'via dei Mille 18',
     41.9039109, 12.5031102,
     20.0, FALSE, FALSE,
     'Korean', 'anna_m'),
    -- Selected Restaurants | €€
    ('Cosmo Restaurant', 'Italia', 'Pompei', 'viale Giuseppe Mazzini 103',
     40.7483403, 14.5045954,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Vitique', 'Italia', 'Greve in Chianti', 'via Citille 43/b',
     43.6078617, 11.3019118,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Guallina', 'Italia', 'Mortara', 'via Molino Faenza 19',
     45.2532653, 8.7910448,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Cavallino (Maranello)', 'Italia', 'Maranello', 'via Abetone Inferiore 1',
     44.5300002, 10.8650464,
     45.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria del Tasso', 'Italia', 'Bolgheri', 'via Bolgherese km 3',
     43.2031974, 10.6098381,
     45.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('La Madernassa', 'Italia', 'Guarene', 'località Lora 2',
     44.7352143, 8.0112719,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Vivace', 'Italia', 'Brescia', 'vicolo Rizzardo 2',
     45.5391252, 10.2178411,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Mogano', 'Italia', 'Formello', 'via del Praticello Alto 7',
     42.0812669, 12.3887081,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Zàghara Restaurant', 'Italia', 'Caltanissetta', 'Contrada Bigini snc',
     37.47557, 14.01067,
     45.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Materia Prima', 'Italia', 'Castel di Sangro', 'Località Piana Santa Liberata',
     41.7782026, 14.0979496,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Vecio Macello', 'Italia', 'Verona', 'via Macello 8',
     45.4358243, 10.9993649,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Scatto', 'Italia', 'Torino', 'piazza San Carlo 156',
     45.0682914, 7.6825254,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Antica Osteria il Ronchettino', 'Italia', 'Milano', 'via Lelio Basso 9',
     45.4040984, 9.174216,
     45.0, FALSE, FALSE,
     'Milanese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Ichikawa', 'Italia', 'Milano', 'via Lazzaro Papi 18',
     45.4511942, 9.2078702,
     85.0, FALSE, FALSE,
     'Giapponese', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Le Macine', 'Italia', 'Vittorio Veneto', 'via Lino Carlo del Favero 11',
     45.978663, 12.3092023,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Le Cementine', 'Italia', 'Roncade', 'via Sile 6',
     45.5647626, 12.4074651,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Casa Vicina', 'Italia', 'Torino', 'via Ermanno Fenoglietti 20/b',
     45.0350554, 7.666969,
     160.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Società Milano', 'Italia', 'Milano', 'via Panfilo Castaldi 19',
     45.4772598, 9.2020085,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Campocori', 'Italia', 'Roma', 'via di Santa Maria de'' Calderari 49',
     41.89323, 12.47573,
     85.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Radicì', 'Italia', 'Iseo', 'via Mirolte 53',
     45.6588449, 10.0509609,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Delicato', 'Italia', 'Contigliano', 'via Umberto I 2',
     42.4092142, 12.7659505,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Nugolo', 'Italia', 'Firenze', 'via della Mattonaia 27r',
     43.773003, 11.268848,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Osteria Ricanatti', 'Italia', 'Ostuni', 'corso Cavour 37',
     40.7310026, 17.5794529,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('TerraMadre', 'Italia', 'Nettuno', 'via del Baluardo 7',
     41.4569587, 12.659848,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Altatto Bistrot', 'Italia', 'Milano', 'via Comune Antico 15',
     45.5035529, 9.2092688,
     45.0, FALSE, FALSE,
     'Vegetariana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Altriménti', 'Italia', 'Milano', 'via Monte Bianco 2/a',
     45.4745344, 9.1510147,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Torre (Spilimbergo)', 'Italia', 'Spilimbergo', 'piazza Castello 8',
     46.1104932, 12.9061438,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Comi 107', 'Italia', 'Como', 'via Borgo Vico 107',
     45.8116386, 9.0702401,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Fè Ristorante', 'Italia', 'Noci', 'via Giulio Pastore 2',
     40.7882324, 17.1289381,
     85.0, FALSE, FALSE,
     'Apulian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Cucina Rambaldi', 'Italia', 'Villar Dora', 'via Sant''Ambrogio 55',
     45.1117378, 7.3836001,
     85.0, FALSE, FALSE,
     'Km Zero', 'anna_m'),
    -- Selected Restaurants | €€
    ('Madama Piola', 'Italia', 'Torino', 'via Ormea 6 bis',
     45.0590594, 7.6854084,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Osteria Bakaré', 'Italia', 'Peschiera del Garda', 'Via Venezia 30',
     45.44113, 10.70111,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Carlo e Camillo', 'Italia', 'Torino', 'via Carlo Alberto 35',
     45.0651049, 7.6838793,
     45.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Selected Restaurants | €€
    ('Remo Villa Cariolato', 'Italia', 'Vicenza', 'strada di Bertesina 313',
     45.5579033, 11.5883955,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('PS Ristorante', 'Italia', 'Cerreto Guidi', 'via di Petriolo 7',
     43.74317, 10.84993,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Diana''s Place', 'Italia', 'Roma', 'via Volturno 54',
     41.9037245, 12.5006592,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Enotavola Pino', 'Italia', 'Padova', 'via dell''Arco 37',
     45.405163, 11.8755179,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Il Presidente', 'Italia', 'Lucera', 'via de Nicastri 10',
     41.5077056, 15.3356586,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Osteria della Tana', 'Italia', 'Asiago', 'località Kaberlaba 19',
     45.8546287, 11.4971365,
     85.0, FALSE, FALSE,
     'Veneziana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Trattoria da Me', 'Italia', 'Bologna', 'via San Felice 50/a',
     44.4974146, 11.3323073,
     45.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Seta Sushi Restaurant', 'Italia', 'Bologna', 'corte Isolani 2',
     44.4930591, 11.3486654,
     45.0, FALSE, FALSE,
     'Giapponese', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Chandelle', 'Italia', 'Breuil Cervinia', 'via Piolet 1',
     45.9316973, 7.6296967,
     85.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€
    ('Balìce', 'Italia', 'Milazzo', 'via Ettore Celi 15',
     38.2186394, 15.2381358,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('L''Ostì', 'Italia', 'Corvara in Badia', 'strada Sassongher 2',
     46.5523767, 11.8735655,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Locanda dei Beccaria', 'Italia', 'Montù Beccaria', 'via Marconi 10',
     45.03443, 9.312667,
     45.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Selected Restaurants | €
    ('Palazzo Petrucci Pizzeria', 'Italia', 'Napoli', 'piazza San Domenico Maggiore 5-7',
     40.8484519, 14.2546489,
     20.0, FALSE, FALSE,
     'Pizza', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Cortile Arabo', 'Italia', 'Marzamemi', 'vicolo Villadorata',
     36.7417654, 15.1196579,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Acciuga', 'Italia', 'Roma', 'via Vodice 25',
     41.9181416, 12.4614513,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Giannino dal 1899', 'Italia', 'Milano', 'via Vittor Pisani 6',
     45.4809165, 9.1996251,
     85.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Bottega Lucia', 'Italia', 'Milano', 'via Carlo Ravizza 4',
     45.4675598, 9.1524732,
     45.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Concezione Restaurant', 'Italia', 'Catania', 'via Giuseppe Verdi 143',
     37.5108999, 15.0926827,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Meraviglioso Osteria Moderna', 'Italia', 'Polignano a Mare', 'largo Gelso 16',
     40.9971639, 17.2168149,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Gennaro Amitrano', 'Italia', 'Capri', 'via Marina Piccola 120',
     40.5456676, 14.235691,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda Perbellini al Mare', 'Italia', 'Montallegro', 'Bovo Marina',
     37.3804508, 13.3079456,
     45.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Il Giardinetto', 'Italia', 'Mandello del Lario', 'piazza Garibaldi 10',
     45.91301, 9.31639,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Locale', 'Italia', 'Firenze', 'via delle Seggiole 12r',
     43.7713968, 11.2600385,
     160.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Fàula', 'Italia', 'Cerretto Langhe', 'Località Talloria 1',
     44.589415, 8.0407424,
     85.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Asina Luna', 'Italia', 'Peschiera Borromeo', 'via della Resistenza 23',
     45.4391114, 9.2948791,
     85.0, FALSE, FALSE,
     'Meats and Grills', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Barolino', 'Italia', 'Carpi', 'via Giovanni XXIII 110',
     44.7936775, 10.8758994,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Rivale al Lago', 'Italia', 'Padenghe sul Garda', 'via Marconi 93',
     45.4931277, 10.5124048,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Lillotatini', 'Italia', 'Panicale', 'piazza Umberto I 13-14',
     43.0291675, 12.0992757,
     45.0, FALSE, FALSE,
     'Umbrian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Boccon DiVino', 'Italia', 'Montalcino', 'via Traversa dei Monti 201',
     43.05227, 11.49878,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Giardino "Da Felicin"', 'Italia', 'Monforte d''Alba', 'via Vallada 18',
     44.5825745, 7.9673803,
     85.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Antica Locanda dell''Orco', 'Italia', 'Rivarolo Canavese', 'via Ivrea 109',
     45.3335077, 7.7267256,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Le Vele', 'Italia', 'Misano Adriatico', 'via Litoranea Sud 71',
     43.97567, 12.70971,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Risorgimento', 'Italia', 'Sirmione', 'piazza Carducci 5/6',
     45.492478, 10.607502,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Giardinetto', 'Italia', 'Pettenasco', 'via Provinciale 1',
     45.8218724, 8.4023087,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Kro', 'Italia', 'Ponte di Legno', 'via Tollarini 70/c',
     46.25104, 10.480131,
     45.0, FALSE, FALSE,
     'Alpine', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Trattoria di Campagna', 'Italia', 'Sarre', 'Località Saint Maurice 57',
     45.7174664, 7.2612561,
     45.0, FALSE, FALSE,
     'Cuisine from the Aosta Valley', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Makorè', 'Italia', 'Ferrara', 'via Palestro 10/18',
     44.8370614, 11.6227331,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Lo Stornello', 'Italia', 'Stresa', 'via Cavour 35',
     45.8842105, 8.5389585,
     45.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Chiosco di Bacco', 'Italia', 'Torriana', 'via Santarcangiolese 62',
     43.9860678, 12.40206,
     45.0, FALSE, FALSE,
     'Cuisine from Romagna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Antica Moka', 'Italia', 'Modena', 'Via Emilia Est 1496',
     44.6225361, 10.9828687,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Almatò', 'Italia', 'Roma', 'via Augusto Riboty 20/c',
     41.9154553, 12.4546959,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Razzo', 'Italia', 'Torino', 'via Andrea Doria 17/f',
     45.0639963, 7.6850158,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €
    ('3.0 Ciro Cascella', 'Italia', 'Napoli', 'via San Pasquale 68',
     40.8359663, 14.2363274,
     20.0, FALSE, FALSE,
     'Pizza', 'marco_f'),
    -- Selected Restaurants | €€
    ('Baracca - Storica Hostaria', 'Italia', 'Trebaseleghe', 'via Ronchi 1',
     45.59254, 12.04119,
     45.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Nascostoposto', 'Italia', 'Terni', 'via Sant''Alò',
     42.5621827, 12.6445805,
     45.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Duanima', 'Italia', 'Cagliari', 'via Sebastiano Satta 28',
     39.2166046, 9.1217347,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Adelaide', 'Italia', 'Roma', 'via dell''Arancio 69',
     41.9044658, 12.4764949,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Al Persef', 'Italia', 'Livigno', 'via Saroch 1272',
     46.5201886, 10.124835,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('CENERE - Museum & Bistrot', 'Italia', 'Pompei', 'via Plinio 39',
     40.7486425, 14.4936834,
     45.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Livello 1', 'Italia', 'Roma', 'via Duccio di Buoninsegna 25',
     41.8298125, 12.4892791,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('W Villadorata Country Restaurant', 'Italia', 'Noto', 'contrada Portelle',
     36.8674713, 15.0383198,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Josto', 'Italia', 'Cagliari', 'via Sassari 25',
     39.2165191, 9.1103353,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Bramea', 'Italia', 'Palazzo San Gervasio', 'viale Villa d''Errico 10',
     40.9379252, 15.9740352,
     45.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Toe Drûe', 'Italia', 'Sestri Ponente', 'via Corsi 44 r',
     44.4258298, 8.8407606,
     45.0, FALSE, FALSE,
     'Ligure', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Dimora Ulmo', 'Italia', 'Matera', 'via Pennino 28',
     40.6651164, 16.6102076,
     85.0, FALSE, FALSE,
     'Cuisine from Basilicata', 'marco_f'),
    -- Selected Restaurants | €€
    ('Fattoria delle Torri', 'Italia', 'Modica', 'vico Napolitano 14',
     36.856315, 14.7596559,
     45.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Momento al 25', 'Italia', 'Carpi', 'via San Francesco 20',
     44.7797015, 10.8818081,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Buca di Bacco', 'Italia', 'Pietra Ligure', 'corso Italia 149',
     44.1518199, 8.2897517,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Da Miky', 'Italia', 'Monterosso al Mare', 'via Fegina 104',
     44.1452613, 9.6465188,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Osteria Il Maialetto', 'Italia', 'Monsummano Terme', 'via Della Repubblica 348',
     43.8638368, 10.8170887,
     20.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Posillipo', 'Italia', 'Gabicce Monte', 'via dell''Orizzonte 1',
     43.9639309, 12.7722742,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Pineta (Genoa)', 'Italia', 'Genova', 'via Gualco 82',
     44.4447179, 8.9990585,
     45.0, FALSE, FALSE,
     'Tradizionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Stefano Paganini alla Corte degli Alfieri', 'Italia', 'Magliano Alfieri', 'piazza Raimondo 2',
     44.7667927, 8.0684173,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Vini da Gigio', 'Italia', 'Venezia', 'calle Stua Cannaregio',
     45.4419797, 12.3337217,
     45.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Porcino', 'Italia', 'Badia', 'strada Damez 2/a',
     46.6075518, 11.8938127,
     85.0, FALSE, FALSE,
     'Alpine', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Essenza Bistrot', 'Italia', 'Olbia', 'via delle Terme 8/A',
     40.9248365, 9.5027825,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Gallina', 'Italia', 'Gavi', 'frazione Monterotondo 56',
     44.7132, 8.8251,
     85.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Acqua (Vieste)', 'Italia', 'Vieste', 'lungomare Amerigo Vespucci 50',
     41.8854572, 16.1780243,
     45.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Gagini Restaurant', 'Italia', 'Palermo', 'via dei Cassari 35',
     38.1184687, 13.3661866,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €
    ('Menabò Vino e Cucina', 'Italia', 'Roma', 'via delle Palme 44 d/e',
     41.8890879, 12.5639458,
     20.0, FALSE, FALSE,
     'Km Zero', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Poggio', 'Italia', 'Poggiridenti', 'via Panoramica 4',
     46.1749169, 9.9189094,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Tenne Lodges', 'Italia', 'Colle', 'strada Racines di Dentro 51',
     46.8648828, 11.3068524,
     85.0, FALSE, FALSE,
     'Alpine', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria Il Cappello', 'Italia', 'Trento', 'piazzetta Bruno Lunelli 5',
     46.06961, 11.12538,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Bentōteca', 'Italia', 'Milano', 'via San Calocero 3',
     45.4582847, 9.176373,
     85.0, FALSE, FALSE,
     'Fusion', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Signore te ne ringrazi', 'Italia', 'Macerata', 'via Pescheria Vecchia 26',
     43.3005511, 13.454439,
     45.0, FALSE, FALSE,
     'Cuisine from the Marches', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Burjè 1968', 'Italia', 'Corvara in Badia', 'str. Burje 11',
     46.5478163, 11.8724653,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Ciambella', 'Italia', 'Roma', 'via dell''Arco della Ciambella 20',
     41.8968476, 12.4769553,
     85.0, FALSE, FALSE,
     'Roman', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Incàlmo', 'Italia', 'Este', 'viale Rimembranze 1',
     45.2305496, 11.6594581,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Bino', 'Italia', 'Savona', 'via Ambrogio Aonzo 31r',
     44.3081678, 8.4824464,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Cortile Pepe', 'Italia', 'Cefalu', 'via Nicola Botta 15',
     38.0387336, 14.0215432,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Casu Osteria Contemporanea', 'Italia', 'Giarre', 'corso Italia 294',
     37.7292736, 15.1934203,
     45.0, FALSE, FALSE,
     'Siciliana', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Bianca sul Lago', 'Italia', 'Oggiono', 'via Dante Alighieri 18',
     45.8000413, 9.355424,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Mirabelle', 'Italia', 'Roma', 'via di Porta Pinciana 14',
     41.9078782, 12.4867135,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Portolano', 'Italia', 'Porto San Paolo', 'via Molara 11',
     40.8799943, 9.6356984,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Il Bikini', 'Italia', 'Vico Equense', 'SS 145 Sorrentina km 13',
     40.678535, 14.437112,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Abraxas Osteria', 'Italia', 'Pozzuoli', 'via Scalandrone 15',
     40.832253, 14.070577,
     45.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Henri Restaurant', 'Italia', 'Viareggio', 'viale Ugo Foscolo 10',
     43.8683192, 10.2447506,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Deste', 'Italia', 'Porto Rotondo', 'piazza Rudalza 6',
     41.0285499, 9.5422852,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('De'' Minimi', 'Italia', 'Tropea', 'contrada Paola 6',
     38.6765326, 15.9059125,
     85.0, FALSE, FALSE,
     'Calabrian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('De Gustibus', 'Italia', 'Chieri', 'via Martiri della Libertà 9',
     45.0073562, 7.8222492,
     45.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Il Rivale in Città', 'Italia', 'Brescia', 'via Antonio Gramsci 10',
     45.5364088, 10.2187291,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Kohaku', 'Italia', 'Roma', 'via Marche 66',
     41.9088815, 12.4902485,
     45.0, FALSE, FALSE,
     'Japanese Contemporary', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Salsedine367', 'Italia', 'Lido di Savio', 'via Marradi 11',
     44.30601, 12.34584,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Flurin', 'Italia', 'Glorenza', 'Laubengasse 2',
     46.6706565, 10.5533644,
     45.0, FALSE, FALSE,
     'Km Zero', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Al Malò - Cucina e Miscelazione', 'Italia', 'Rovato', 'piazza Cavour 28',
     45.5668698, 9.9990896,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Cavallo Scosso', 'Italia', 'Asti', 'via al Duca 23/d',
     44.9165713, 8.2055856,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Scuderie Sabaude', 'Italia', 'Pollenzo', 'via Amedeo di Savoia 5',
     44.6833705, 7.8942951,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Nello', 'Italia', 'San Casciano in Val di Pesa', 'Via IV  Novembre 66',
     43.6574806, 11.1867902,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Terrazza Gallia', 'Italia', 'Milano', 'piazza Duca d''Aosta 9',
     45.4857326, 9.2022544,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Teresita by Giardino di Mari', 'Italia', 'Viareggio', 'terrazza della Repubblica 7',
     43.8833328, 10.2331864,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Marc Lanteri', 'Italia', 'Castagnito d''Alba', 'Via Serra 21/d',
     44.7543254, 8.0518105,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda San Martino', 'Italia', 'Chies d''Alpago', 'via Don Ermolao Barattin 23',
     46.1816792, 12.3923716,
     45.0, FALSE, FALSE,
     'Veneziana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Il Principe', 'Italia', 'Pompei', 'via Colle San Bartolomeo 4',
     40.7483504, 14.4983755,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Café Cracco', 'Italia', 'Milano', 'Galleria Vittorio Emanuele II',
     45.4655423, 9.1899588,
     85.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Luciano Cucina Italiana', 'Italia', 'Roma', 'piazza del Teatro di Pompeo 18',
     41.8962136, 12.4729286,
     45.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('L''Imbuto', 'Italia', 'Lucca', 'piazza del Collegio 8',
     43.8458125, 10.5030555,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Josef Stube', 'Italia', 'Madonna di Senales', 'Località Certosa 29',
     46.7054703, 10.9099406,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Barca (Rho)', 'Italia', 'Rho', 'via Ratti 54',
     45.536579, 9.0428519,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Casa Perrotta Restaurant', 'Italia', 'Cernobbio', 'via Cinque Giornate 72',
     45.8379154, 9.0698219,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Impronta (Bassano del Grappa)', 'Italia', 'Bassano del Grappa', 'via Angarano 7',
     45.7674783, 11.7305971,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Faletta 1881', 'Italia', 'Casale Monferrato', 'Regione Mandoletta 81',
     45.1012346, 8.4341845,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Osteria Grande', 'Italia', 'Arezzo', 'piazza Grande 26',
     43.4645755, 11.8848153,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Armando al Pantheon', 'Italia', 'Roma', 'salita de'' Crescenzi 31',
     41.899017, 12.4762745,
     45.0, FALSE, FALSE,
     'Roman', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Vite (Coriano)', 'Italia', 'Coriano', 'via Montepirolo 7',
     43.986595, 12.54202,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Quadri Bistrot', 'Italia', 'Milano', 'Via Solferino 48',
     45.4788921, 9.1885427,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Trattoria dall''Antonia', 'Italia', 'Mira', 'riviera Silvio Trentin 8',
     45.4342596, 12.1303561,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Ling''s Ravioleria Migrante', 'Italia', 'Bologna', 'via Leandro Alberti 34/2c',
     44.4850668, 11.366698,
     20.0, FALSE, FALSE,
     'Asian', 'anna_m'),
    -- Selected Restaurants | €€
    ('aldìVino', 'Italia', 'Corciano', 'via Antonio Gramsci 201',
     43.1036814, 12.294458,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Selvatico', 'Italia', 'Rivanazzano Terme', 'via Silvio Pellico 19',
     44.9317851, 9.015184,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Marchese - Osteria Mercato Liquori (Rome)', 'Italia', 'Roma', 'via di Ripetta 162',
     41.90437, 12.47528,
     45.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Locanda La Posta', 'Italia', 'Cavour', 'via dei Fossi 4',
     44.7851123, 7.3752862,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Prato Gaio', 'Italia', 'Montecalvo Versiggia', 'Località Versa',
     44.9652928, 9.291065,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('I Rodella', 'Italia', 'Deruta', 'strada esterna vicinale della Rocca 2',
     42.9529723, 12.4031515,
     45.0, FALSE, FALSE,
     'Innovativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Materia | Spazio Cucina', 'Italia', 'Catania', 'via Teatro Massimo 29',
     37.5040089, 15.0915737,
     45.0, FALSE, FALSE,
     'Siciliana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Stefenelli Desk', 'Italia', 'Aosta', 'via Claude d''Avise 14',
     45.736147, 7.3161241,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Vescovo Moro', 'Italia', 'Verona', 'via Pontida 3',
     45.4438842, 10.9794643,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Il Narciso', 'Italia', 'Carrara', 'viale Amerigo Vespucci 32',
     44.0370312, 10.0345978,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Osteria 1861', 'Italia', 'Santa Maria di Castellabate', 'via Valentino Izzo 1',
     40.284492, 14.9458799,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Musa Restaurant & Rooftop Terrace', 'Italia', 'Cima', 'Località Cini 29',
     46.02691, 9.09779,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Santamonica', 'Italia', 'Genova', 'lungomare Lombardo 27',
     44.39134, 8.96498,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Enoteca di Buttrio', 'Italia', 'Buttrio', 'via Cividale 38',
     46.0167091, 13.3365071,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda Belvedere', 'Italia', 'Saludecio', 'via San Giuseppe 736',
     43.8797072, 12.6753928,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Antica Trattoria al Gallo 1909', 'Italia', 'Ravenna', 'via Maggiore 87',
     44.42064, 12.191586,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria Il Granaio', 'Italia', 'Rapolano Terme', 'via dei Monaci',
     43.287373, 11.601818,
     45.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria L''Abbiccì', 'Italia', 'Seregno', 'via Medici da Seregno 29',
     45.6487575, 9.2037394,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Spoon', 'Italia', 'Teramo', 'via Mario Capuani 61',
     42.6601179, 13.701862,
     45.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Voce Aimo e Nadia', 'Italia', 'Milano', 'piazza della Scala 6',
     45.4674271, 9.190171,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Sottovoce', 'Italia', 'Como', 'piazza Cavour 24',
     45.8133581, 9.0812671,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Felter alle Rose', 'Italia', 'Salò', 'via Gasparo Da Salò 33',
     45.60674, 10.521176,
     45.0, FALSE, FALSE,
     'Seasonal Cuisine', 'marco_f'),
    -- Selected Restaurants | €€
    ('Locanda della Luna', 'Italia', 'San Giorgio del Sannio', 'via delle Oche 7',
     41.05654, 14.84925,
     45.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Hostaria di Bricai', 'Italia', 'Varallo', 'via Fiume 1',
     45.817917, 8.2520162,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Osteria dello Strecciolo', 'Italia', 'Robbiate', 'via Indipendenza 2',
     45.69081, 9.44196,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Frantoio (Colle di Val d''Elsa)', 'Italia', 'Colle di Val d''Elsa', 'via del Castello 40',
     43.4220531, 11.1197927,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Grifone', 'Italia', 'Castel San Pietro Terme', 'via Ca'' Masino 611/a - loc. Varignana',
     44.402175, 11.5066308,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Indigeno', 'Italia', 'Montepulciano', 'Via di Villa Bianca 15',
     43.08295, 11.79339,
     45.0, FALSE, FALSE,
     'Tradizionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('Riccio Restaurant', 'Italia', 'Bacoli', 'via Molo di Baia 47',
     40.8167162, 14.0716849,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('MU Dimsum', 'Italia', 'Milano', 'via Aminto Caretto 3',
     45.4827848, 9.1996683,
     45.0, FALSE, FALSE,
     'Chinese Contemporary', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Limonaia', 'Italia', 'Torino', 'via Mario Ponzio 10/b',
     45.0674448, 7.6233151,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Hosteria del Platano', 'Italia', 'Varenna', 'via Statale 29',
     45.99987, 9.291888,
     45.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Molo (Passignano sul Trasimeno)', 'Italia', 'Passignano sul Trasimeno', 'via Aganor Pompili 9',
     43.1834467, 12.1366653,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Casa Federici', 'Italia', 'Montoro', 'via Pellegrino Federici',
     40.8031612, 14.7721381,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Locanda Martinelli', 'Italia', 'Nibbiaia', 'piazza Mazzini 11',
     43.4643371, 10.4159811,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Chat Qui Rit', 'Italia', 'Venezia', 'calle Tron',
     45.4344421, 12.3361994,
     160.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('El Brite di Larieto', 'Italia', 'Cortina d''Ampezzo', 'Località Larieto',
     46.5519115, 12.1756742,
     85.0, FALSE, FALSE,
     'Alpine', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Borgia Milano', 'Italia', 'Milano', 'via Giorgio Washington 56',
     45.4621437, 9.1547585,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('[bu:r]', 'Italia', 'Milano', 'via Giuseppe Mercalli 22',
     45.4553804, 9.1896857,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Cucina de'' Mibabbo', 'Italia', 'Milano', 'corso Lodi 19',
     45.4500615, 9.20644,
     45.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Al Baccanale', 'Italia', 'Piombino', 'via XX Settembre 20',
     42.9224072, 10.5270934,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Da Attilio', 'Italia', 'Napoli', 'via Pignasecca 17',
     40.845245, 14.2483589,
     20.0, FALSE, FALSE,
     'Pizza', 'anna_m'),
    -- Selected Restaurants | €€
    ('Da Luciano', 'Italia', 'Passignano sul Trasimeno', 'via Nazionale 11',
     43.18376, 12.13689,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Stefano Mocellin al Padovanino', 'Italia', 'Padova', 'via Santa Chiara 1',
     45.4034021, 11.8769179,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Frantoio (Assisi)', 'Italia', 'Assisi', 'via Fontebella',
     43.072257, 12.6094475,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Palazzo Branciforte', 'Italia', 'Palermo', 'via Bara all''Olivella 2',
     38.1213379, 13.3622661,
     85.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Carter Oblio', 'Italia', 'Roma', 'via Giuseppe Gioachino Belli 21',
     41.907119, 12.4704271,
     45.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('1908', 'Italia', 'Soprabolzano', 'via Paese 18',
     46.5282559, 11.405296,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Risacca Blu', 'Italia', 'Milano', 'via Tunisia',
     45.4770356, 9.205881,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Sostansa', 'Italia', 'Pordenone', 'viale Cossetti 3',
     45.9580982, 12.6608863,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Da Lucio', 'Italia', 'Rimini', 'viale Ortigara 80',
     44.0782307, 12.5696634,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Bis Osteria Italiana Contemporanea', 'Italia', 'Colle di Val d''Elsa', 'Via 20 Settembre 50',
     43.4209554, 11.1168188,
     45.0, FALSE, FALSE,
     'Seasonal Cuisine', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Oro Nero', 'Italia', 'Sauris', 'Località La Maina 10',
     46.452541, 12.7267437,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Dallo Zio', 'Italia', 'Rimini', 'via Santa Chiara 16',
     44.0567555, 12.5700557,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Between', 'Italia', 'Rivoli', 'viale Partigiani d''Italia 98/c',
     45.076191, 7.5150913,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Il Colmetto', 'Italia', 'Rodengo Saiano', 'via Finilnuovo 9',
     45.57434, 10.10229,
     85.0, FALSE, FALSE,
     'Seasonal Cuisine', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('AB Osteria Contemporanea', 'Italia', 'Lavariano', 'via Aquileia 5',
     45.9561008, 13.2272165,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Sushisen', 'Italia', 'Roma', 'via Giuseppe Giulietti 21/a',
     41.8751414, 12.4792824,
     45.0, FALSE, FALSE,
     'Japanese Contemporary', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Giano', 'Italia', 'Roma', 'via Liguria 28',
     41.9062891, 12.4887055,
     85.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Pescatore', 'Italia', 'Cala Gonone', 'via Acqua Dolce 7',
     40.2842545, 9.6395541,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Bottega Aleotti', 'Italia', 'Crevalcore', 'via Paltrinieri 62',
     44.7237395, 11.1463474,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Marelet', 'Italia', 'Treviglio', 'viale Cesare Battisti 17',
     45.5242169, 9.5952355,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Langosteria Paraggi', 'Italia', 'Paraggi', 'via Paraggi a Mare 1',
     44.3113972, 9.2094107,
     160.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Mammaròssa', 'Italia', 'Avezzano', 'via Giuseppe Garibaldi 388',
     42.0256779, 13.4339512,
     85.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'marco_f'),
    -- Selected Restaurants | €€
    ('Hostaria del Vicolo', 'Italia', 'Sciacca', 'vicolo Sammaritano 10',
     37.5085, 13.08324,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('20 Posti', 'Italia', 'Empoli', 'via della Murina 4/a',
     43.7210585, 10.9472176,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Ciotola', 'Italia', 'Porcia', 'via Sant''Antonio 19',
     45.9687603, 12.6109708,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Pista', 'Italia', 'Torino', 'Centro Commerciale Lingotto - via Nizza 262',
     45.0313043, 7.6673382,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Hostaria Ducale', 'Italia', 'Genova', 'Salita di San Matteo 29 r',
     44.4077498, 8.9339308,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Osteria Contemporanea', 'Italia', 'Gattinara', 'via Francesco Mattai 4',
     45.6135926, 8.3682171,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Casa Bertini', 'Italia', 'Recanati', 'via le Grazie 7b',
     43.40711, 13.53811,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Marechiaro', 'Italia', 'Bolzano', 'via Vicenza 14',
     46.4940329, 11.3416189,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Campamac', 'Italia', 'Barbaresco', 'strada della Valle 1',
     44.7249488, 8.0805241,
     85.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €
    ('Trattoria Pomposa - Al Re gras', 'Italia', 'Modena', 'via Castel Maraldo 57',
     44.6492927, 10.9241876,
     20.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Lineadombra', 'Italia', 'Venezia', 'Ponte dell''Umiltà',
     45.4294583, 12.3347372,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Sustanza', 'Italia', 'Napoli', 'Galleria Principe di Napoli 13',
     40.8525964, 14.2503742,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Radici (Castel Giorgio)', 'Italia', 'Castel Giorgio', 'Località Borgo La Chiaracia',
     42.703998, 11.9564988,
     85.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Osteria Origano', 'Italia', 'Minervino di Lecce', 'via Giuseppina Scarciglia 18',
     40.09191, 18.42251,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Innocenti Wine Experiences', 'Italia', 'Poggibonsi', 'via Cassia nord 2/F',
     43.4835043, 11.1489215,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Da Lorenzo', 'Italia', 'Scala', 'via Fra'' Gerardo Sasso 8',
     40.6568953, 14.6104972,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Mirepuà Food Lab', 'Italia', 'Cremolino', 'via Umberto I 69',
     44.6361062, 8.5863841,
     45.0, FALSE, FALSE,
     'Tradizionale', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Corte Gourmet', 'Italia', 'Lainate', 'piazza Angelo Borroni 1',
     45.5712615, 9.0280547,
     85.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Lucia', 'Italia', 'Giulianova Lido', 'via Lampedusa 12',
     42.7474502, 13.9722905,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Antica Osteria del Mare', 'Italia', 'Milano', 'via Ascanio Sforza 105',
     45.4381627, 9.1750117,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('med', 'Italia', 'Treviso', 'piazza del Quartiere Latino 13',
     45.66483, 12.24899,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Forme Restaurant', 'Italia', 'Brescia', 'Via Codignole 52',
     45.5103894, 10.1970917,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria de'' Mercati', 'Italia', 'Sassari', 'via Mercato 2',
     40.7276061, 8.5628406,
     45.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Gallery Bistrot Contemporaneo', 'Italia', 'Troia', 'via Regina Margherita 3/b',
     41.3632895, 15.3128249,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Veranda', 'Italia', 'Cesenatico', 'viale Giosuè Carducci 140',
     44.1991935, 12.4065654,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Nicchia', 'Italia', 'Cavour', 'via Roma 9',
     44.7851524, 7.3748027,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Bistrot La Perla', 'Italia', 'Corvara in Badia', 'strada Col Alt 105',
     46.547083, 11.8761907,
     85.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €
    ('Pascalucci', 'Italia', 'San Nicola Manfredi', 'via Appia 1',
     41.0942246, 14.8327808,
     20.0, FALSE, FALSE,
     'Campanian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Unterwirt', 'Italia', 'Gudon', 'Gudon 45',
     46.6484396, 11.5995819,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Tenuta del Gallo', 'Italia', 'Macchie', 'Strada degli Ortacci 34',
     42.5997785, 12.3696881,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Crotasc', 'Italia', 'Mese', 'via Don Primo Lucchinetti 63',
     46.3110112, 9.3812333,
     45.0, FALSE, FALSE,
     'Cuisine from Valtellina', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Baretto', 'Italia', 'Albignasego', 'via Europa 6',
     45.3468396, 11.867622,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Luge', 'Italia', 'Breuil Cervinia', 'Perreres',
     45.90739, 7.6139812,
     45.0, FALSE, FALSE,
     'Cuisine from the Aosta Valley', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria Cascina dei Fiori', 'Italia', 'Borgo Vercelli', 'Regione Forte',
     45.35148, 8.46273,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Paolo Teverini', 'Italia', 'Bagno di Romagna', 'via del Popolo 2',
     43.83294, 11.9581,
     85.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Antica Osteria dei Camelì', 'Italia', 'Ambivere', 'via Marconi 13',
     45.7195137, 9.5473766,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Roscioli', 'Italia', 'Roma', 'via dei Giubbonari 21',
     41.8942487, 12.4742332,
     45.0, FALSE, FALSE,
     'Roman', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria degli Angeli', 'Italia', 'Malnate', 'via Giuseppe Brusa 5',
     45.8006366, 8.8792188,
     45.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Casa Fantini/Lake Time', 'Italia', 'Pella', 'piazza Motta',
     45.8013764, 8.3882327,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Taverna di Bacco', 'Italia', 'Nettuno', 'largo Luigi Trafelli',
     41.4575676, 12.6582805,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Novo Osteria', 'Italia', 'Borgonovo Val Tidone', 'piazza De Cristoforis 30',
     45.0143347, 9.4472594,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Aqua', 'Italia', 'Torbole', 'lungolago Conca d''Oro 11',
     45.8682988, 10.8769404,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Konnubio', 'Italia', 'Firenze', 'via dei Conti 8 r',
     43.7746881, 11.2529333,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria della Foce', 'Italia', 'Genova', 'via Eugenio Ruspoli 72r',
     44.3984887, 8.9477154,
     45.0, FALSE, FALSE,
     'Km Zero', 'anna_m'),
    -- Selected Restaurants | €€
    ('ConTatto', 'Italia', 'Frascati', 'via Gioberti 11',
     41.8080418, 12.6820784,
     45.0, FALSE, FALSE,
     'Cuisine from Lazio', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Il Grano di Pepe', 'Italia', 'Ravarino', 'via Roma 178/a',
     44.7238155, 11.0996024,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Angiò-Macelleria di Mare', 'Italia', 'Catania', 'viale Africa 28/h',
     37.5158518, 15.104726,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Emozioni', 'Italia', 'Campobasso', 'via Guglielmo Marconi 129',
     41.5604699, 14.6571809,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Trattoria del Cimino dal 1895', 'Italia', 'Caprarola', 'via Filippo Nicolai 44',
     42.326814, 12.239228,
     45.0, FALSE, FALSE,
     'Cuisine from Lazio', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Le Colonne (Caserta)', 'Italia', 'Caserta', 'viale Giulio Douhet 7/9',
     41.0725177, 14.31933,
     85.0, FALSE, FALSE,
     'Campanian', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Acqua Pazza (Bologna)', 'Italia', 'Bologna', 'via Murri 168/d',
     44.4730162, 11.3695149,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Sale Grosso', 'Italia', 'Bologna', 'vicolo De'' Facchini 4/a',
     44.4973069, 11.3482487,
     45.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Trattoria Battibecco', 'Italia', 'Bologna', 'via Battibecco 4/b',
     44.493474, 11.3412365,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €
    ('SottoSopra', 'Italia', 'Baveno', 'corso Garibaldi 40',
     45.9107304, 8.5043938,
     20.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €
    ('Madama Vigna', 'Italia', 'Baldichieri d''Asti', 'via Nazionale 41',
     44.9032949, 8.091008,
     20.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Capanna di Eraclio', 'Italia', 'Codigoro', 'Località per Le Venezie 21',
     44.8672276, 12.0457984,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('San Marco', 'Italia', 'Canelli', 'via Alba 136',
     44.7198462, 8.2799189,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Bacco', 'Italia', 'Barletta', 'piazza Marina 30',
     41.3223875, 16.2844955,
     85.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Carne & Spirito', 'Italia', 'Brescia', 'via dei Gelsi 5',
     45.5178242, 10.1776993,
     45.0, FALSE, FALSE,
     'Steakhouse', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria Al GiGianca', 'Italia', 'Bergamo', 'via Broseta 113',
     45.6934038, 9.6465415,
     45.0, FALSE, FALSE,
     'Tradizionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('Vicolo Colombina', 'Italia', 'Bologna', 'vicolo Colombina 5/b',
     44.4924017, 11.3421624,
     45.0, FALSE, FALSE,
     'Tradizionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Taverna Kerkira', 'Italia', 'Bagnara Calabra', 'corso Vittorio Emanuele 217',
     38.2842082, 15.8004227,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Benedikto', 'Italia', 'Assisi', 'via Eremo delle Carceri 1/a',
     43.0705679, 12.6199671,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Zunica 1880', 'Italia', 'Civitella del Tronto', 'piazza Filippi Pepe 14',
     42.772535, 13.6692461,
     85.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Maison du Gourmet', 'Italia', 'Coloreto', 'strada Budellungo 96',
     44.7692136, 10.3785223,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Aubergine', 'Italia', 'Abano Terme', 'via Ghislandi 5',
     45.35044, 11.77494,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Serpillo', 'Italia', 'Bevagna', 'via di Mezzo 1',
     42.9439497, 12.5781303,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Ristorantino - Da Dino', 'Italia', 'Anzola dell''Emilia', 'via XXV Aprile 11',
     44.5493699, 11.1907781,
     45.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Selected Restaurants | €€
    ('Cacciatori', 'Italia', 'Cartosio', 'via Moreno 30',
     44.5935867, 8.4205801,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('L''Angolo d''Abruzzo', 'Italia', 'Carsoli', 'piazza Aldo Moro 8',
     42.0956419, 13.0807684,
     45.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('VeRo - Venetian Roots', 'Italia', 'Venezia', 'Riva Ca''di Dio 2181',
     45.4330725, 12.3490106,
     160.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Alajmo Cortina', 'Italia', 'Cortina d''Ampezzo', 'Località Ronco 123',
     46.5430865, 12.1260741,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Mirko''s', 'Italia', 'Castellammare del Golfo', 'discesa Annunziata 1',
     38.0280753, 12.880544,
     45.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Bottega del 30', 'Italia', 'Castelnuovo Berardenga', 'via Santa Caterina 2',
     43.3850498, 11.4800311,
     85.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Galileo', 'Italia', 'Civitanova Marche', 'via IV Novembre conc. 25',
     43.3174, 13.7227,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Bul', 'Italia', 'Bari', 'via Villari 52',
     41.1264379, 16.8644214,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Trattoria Toscana al Vecchio Forno', 'Italia', 'San Quirico d''Orcia', 'via Poliziano 18',
     43.0598574, 11.604681,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Caffè delle Rose Bistrot', 'Italia', 'Verbania', 'via Ruga 36',
     45.922741, 8.5512755,
     45.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('I Carracci', 'Italia', 'Bologna', 'via Manzoni',
     44.4963357, 11.3429519,
     85.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Alle Codole', 'Italia', 'Canale d''Agordo', 'via XX Agosto 27',
     46.3570318, 11.913779,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Al Carroponte', 'Italia', 'Bergamo', 'via De Amicis 4',
     45.6880938, 9.6562166,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Barbieri', 'Italia', 'Altomonte', 'via Italo Barbieri',
     39.6949455, 16.1291378,
     45.0, FALSE, FALSE,
     'Calabrian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Matteo Ristorante', 'Italia', 'Biella', 'piazza Duomo 6',
     45.5658857, 8.0528998,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Villa Tiboldi', 'Italia', 'Canale', 'via Case Sparse 127',
     44.7955746, 7.9747841,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Trattoria la Colonna', 'Italia', 'Rottofreno', 'Via Emilia Est 6',
     45.0558734, 9.6092702,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Lord Nelson', 'Italia', 'Chiavari', 'corso Valparaiso 27',
     44.3173709, 9.3151046,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Macelleria Motta', 'Italia', 'Bellinzago Lombardo', 'strada Padana Superiore 90',
     45.5462837, 9.4498531,
     45.0, FALSE, FALSE,
     'Meats and Grills', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Frosio', 'Italia', 'Almè', 'piazza Lemine 1',
     45.7400891, 9.6150933,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Ristorante Mas-cì', 'Italia', 'Clusone', 'piazza Paradiso 1',
     45.8896483, 9.9446745,
     45.0, FALSE, FALSE,
     'Italiana', 'anna_m'),
    -- Selected Restaurants | €
    ('Pantagruele', 'Italia', 'Brindisi', 'salita di Ripalta 1/5',
     40.63923, 17.94697,
     20.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Locanda Gulfi', 'Italia', 'Chiaramonte Gulfi', 'Contrada Patria',
     37.06754, 14.680672,
     45.0, FALSE, FALSE,
     'Siciliana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Madia (Brione)', 'Italia', 'Brione', 'via Aquilini 5',
     45.6385828, 10.1483613,
     45.0, FALSE, FALSE,
     'Lombardian', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Carlo Magno', 'Italia', 'Collebeato', 'via Campiani 9',
     45.5748305, 10.1904216,
     85.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Oblige', 'Italia', 'Vignola', 'Via Jacopo Barozzi 6',
     44.4770216, 11.0089734,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Pierre - Trattoria Sartoriale', 'Italia', 'Treviso', 'viale dei Mille 1/c',
     45.6655324, 12.2544608,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Podere Belvedere Tuscany', 'Italia', 'Pontassieve', 'via San Piero a Strada 23',
     43.8228589, 11.4601067,
     85.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Antica Farmacia', 'Italia', 'Palaia', 'via del Popolo 51',
     43.6050076, 10.7720725,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda San Michele', 'Italia', 'Montorio al Vomano', 'SS 491 3',
     42.5647767, 13.6391665,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('[àbitat]', 'Italia', 'San Fermo della Battaglia', 'via Henry Dunant 1',
     45.814, 9.0315,
     85.0, FALSE, FALSE,
     'Innovativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Porta Antica', 'Italia', 'Brescia', 'via Quarto dei Mille 16',
     45.5483096, 10.2200033,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Parco Gambrinus', 'Italia', 'San Polo di Piave', 'via Capitello 18',
     45.7967527, 12.3886881,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Taverna da Ciacco', 'Italia', 'San Quirico d''Orcia', 'via Dante Alighieri 30/a',
     43.0596966, 11.6045275,
     45.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria del Mare già il "Votapentole"', 'Italia', 'Castiglione della Pescaia', 'via IV Novembre 15',
     42.764017, 10.8831779,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Ai Do Campanili', 'Italia', 'Cavallino', 'via Marco Polo 2',
     45.4656073, 12.4563395,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria dai Coghi', 'Italia', 'Albarè di Costermano', 'via Alcide De Gasperi 9/13',
     45.5730306, 10.7555283,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Locanda dell''Arcimboldo', 'Italia', 'Coriano Veronese', 'via Gennari 5',
     45.2760826, 11.2871374,
     45.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Babette', 'Italia', 'Albenga', 'via Michelangelo 17',
     44.0368952, 8.2113613,
     45.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Lu Pisantinu', 'Italia', 'Arzachena', 'viale Giovanni Maria Orecchioni snc',
     41.1363679, 9.5219499,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Agriturismo Il Cavallino', 'Italia', 'Semproniano', 'SP 55 Saturnia-Semproniano',
     42.6918653, 11.5231198,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Zur Kaiserkron', 'Italia', 'Bolzano', 'piazza della Mostra 1',
     46.4984779, 11.353475,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Kucina', 'Italia', 'Foggia', 'via Giulio De Petra 67',
     41.446014, 15.5498279,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Feria', 'Italia', 'Treviso', 'via della Quercia 8',
     45.6694425, 12.2198612,
     45.0, FALSE, FALSE,
     'Indonesian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Coeur de Bois', 'Italia', 'Cogne', 'viale Cavagnet 31',
     45.6091814, 7.3538732,
     85.0, FALSE, FALSE,
     'Cuisine from the Aosta Valley', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Michelasso', 'Italia', 'Napoli', 'via Santa Brigida 14/16',
     40.8391919, 14.2493909,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Locanda di Nonna Ida', 'Italia', 'Marano di Valpolicella', 'Località Pontarola 12',
     45.57248, 10.91523,
     45.0, FALSE, FALSE,
     'Classica', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Cappuccini Cucina San Francesco', 'Italia', 'Cologne', 'via Cappuccini 54',
     45.5940715, 9.9387228,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Locanda dei Cantù', 'Italia', 'Carona', 'piazza Vittorio Veneto 3',
     46.0202168, 9.7822976,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Oltre.', 'Italia', 'Bologna', 'via Majani 1/b',
     44.49698, 11.33803,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Villa Pignano', 'Italia', 'Volterra', 'località Pignano 6',
     43.4165316, 10.9615533,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Mater Bistrot', 'Italia', 'Milano', 'via Pasquale Sottocorno 1',
     45.4654461, 9.2076996,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Locanda Stella d''Oro', 'Italia', 'Soragna', 'via Mazzini 8',
     44.9284623, 10.1244937,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Osteria dell''Arco', 'Italia', 'Alba', 'piazza Michele Ferrero 5',
     44.697, 8.0345512,
     20.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Andree', 'Italia', 'La Spezia', 'via San Martino della Battaglia 16',
     44.1046382, 9.8202609,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Tre Scalini', 'Italia', 'Novara', 'via Sottile 23',
     45.449049, 8.6102929,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Le Nove Scodelle', 'Italia', 'Milano', 'viale Monza 4',
     45.4873742, 9.2171657,
     20.0, FALSE, FALSE,
     'Chinese', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Locanda di Fabio e Vale', 'Italia', 'Offanengo', 'via Brescia 1',
     45.3791324, 9.7471882,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Casa Coloni', 'Italia', 'Paestum', 'via Tavernelle 86',
     40.4267989, 15.0022711,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Ad Astra', 'Italia', 'Santa Maria della Versa', 'via Cavour 11/13',
     44.9870931, 9.3001821,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Zest', 'Italia', 'Sorrento', 'via Torquato Tasso 61',
     40.62683, 14.3718,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Azotea', 'Italia', 'Torino', 'via Maria Vittoria 49/b',
     45.0636339, 7.6949405,
     45.0, FALSE, FALSE,
     'Peruvian', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Terrazza Tiberio', 'Italia', 'Capri', 'via Croce 11',
     40.5510647, 14.2466467,
     160.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Cracco Portofino', 'Italia', 'Portofino', 'Molo Umberto I 9',
     44.302429, 9.2103994,
     160.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('KELINA Fine Dine', 'Italia', 'Corvara in Badia', 'Strada Lech de Boà s.n.',
     46.5268226, 11.8623869,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Chalet Gerard', 'Italia', 'Selva di Val Gardena', 'via Plan de Gralba 37',
     46.5366668, 11.7791315,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Casa della Rocca', 'Italia', 'Dolcedo', 'via Ripalta 3',
     43.9074746, 7.9487091,
     45.0, FALSE, FALSE,
     'Ligure', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Pellico 3', 'Italia', 'Milano', 'via Silvio Pellico 3',
     45.4655602, 9.1889938,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Masseria Moroseta', 'Italia', 'Ostuni', 'Contrada Lamacavallo',
     40.7384409, 17.6113501,
     160.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Trattoria Margherita', 'Italia', 'Arborea', 'corso Roma 31',
     39.7753123, 8.5824987,
     45.0, FALSE, FALSE,
     'Sarda', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Belvedere', 'Italia', 'Porto Cervo', 'località Farina',
     41.0960486, 9.5287226,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Millo Ristorante', 'Italia', 'Santa Teresa Gallura', 'via Garibaldi 4',
     41.2428918, 9.1897674,
     45.0, FALSE, FALSE,
     'Sarda', 'anna_m'),
    -- Selected Restaurants | €€
    ('Amanõ', 'Italia', 'Cagliari', 'via Sidney Sonnino 68',
     39.2130873, 9.1191723,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Bistrot Royal', 'Italia', 'Courmayeur', 'via Roma 87',
     45.7891036, 6.9731865,
     85.0, FALSE, FALSE,
     'Alpine', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Ai Porteghi Bistrot', 'Italia', 'Padova', 'via Cesare Battisti 105',
     45.4062379, 11.8810599,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Stilla', 'Italia', 'Colognola ai Colli', 'Località Casette 1',
     45.4246544, 11.2071958,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Cocun Cellar Restaurant', 'Italia', 'San Cassiano', 'strada Prè de Vì 31',
     46.560614, 11.9571789,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Oltremare', 'Italia', 'Maiori', 'via Diego Taiani 3',
     40.644876, 14.6475241,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Remulass', 'Italia', 'Milano', 'via Nino Bixio 21',
     45.47234, 9.209951,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Pentole e Provette', 'Italia', 'Fasano', 'Via Musco 37',
     40.8365964, 17.3618341,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Urubamba', 'Italia', 'Napoli', 'via Gaetano Filangieri 16/c',
     40.8361738, 14.2406752,
     85.0, FALSE, FALSE,
     'Fusion', 'anna_m'),
    -- Selected Restaurants | €€
    ('L''Agave', 'Italia', 'Framura', 'località Chiama',
     44.1999789, 9.5570426,
     45.0, FALSE, FALSE,
     'Ligure', 'marco_f'),
    -- Selected Restaurants | €€€
    ('San Tommaso 10', 'Italia', 'Torino', 'via San Tommaso 10',
     45.0709541, 7.68178,
     85.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Il Tempo Nuovo', 'Italia', 'Ugento', 'via Castello 13',
     39.9294116, 18.1618541,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Zanze XVI', 'Italia', 'Venezia', 'fondamenta dei Tolentini',
     45.4388376, 12.3212634,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Principe di Belludia', 'Italia', 'Noto', 'contrada Belludia sp 51',
     36.8448679, 15.0018149,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('SOMS', 'Italia', 'Pescara', 'via Piave 61',
     42.4713925, 14.2067878,
     45.0, FALSE, FALSE,
     'Cuisine from Abruzzo', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Cut', 'Italia', 'Caravaggio', 'via Amilcare Bietti 28',
     45.49439, 9.63953,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Maragoncello', 'Italia', 'Montichiari', 'via San Giovanni 1',
     45.4268188, 10.367252,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Anastasia', 'Italia', 'Civitanova Marche', 'via Bainsizza 3',
     43.3035996, 13.7354778,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Qualia', 'Italia', 'Cefalu', 'via Giovanni Amendola 16/b',
     38.0381222, 14.0211888,
     85.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Al Moro', 'Italia', 'Marina Di Campo', 'via Pietri 1277',
     42.7431631, 10.2192624,
     85.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Li Somari', 'Italia', 'Tivoli', 'Piazza Rivarola 21',
     41.9655435, 12.7994909,
     45.0, FALSE, FALSE,
     'Cuisine from Lazio', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Cala Luna', 'Italia', 'Cefalu', 'via Vincenzo Cavallaro 12',
     38.0336752, 14.037633,
     160.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Antiche Carampane', 'Italia', 'Venezia', 'rio Terà delle Carampane',
     45.4386687, 12.3310933,
     85.0, FALSE, FALSE,
     'Veneziana', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Phi Restaurant - Giancarlo Morelli', 'Italia', 'Baia Sardinia', 'località Forte Cappellini',
     41.1423061, 9.4673947,
     160.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Lanzani Bottega & Bistrot', 'Italia', 'Brescia', 'via Albertano da Brescia 41',
     45.5482028, 10.1793875,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Clandestino Susci Bar', 'Italia', 'Portonovo', 'Baia di Portonovo',
     43.5654162, 13.5918668,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('La Conchiglia', 'Italia', 'Arma di Taggia', 'Lungomare 33',
     43.830084, 7.8485144,
     85.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Le Petit Bellevue', 'Italia', 'Cogne', 'rue Grand Paradis 22',
     45.6067309, 7.3556842,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('L''Argaj', 'Italia', 'Castiglione Falletto', 'via Alba-Monforte 114',
     44.622997, 7.974684,
     45.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Oste Scuro - Finsterwirt', 'Italia', 'Brixen', 'vicolo del Duomo 3',
     46.7157558, 11.6565447,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Gardenia', 'Italia', 'Caluso', 'corso Torino 9',
     45.3034572, 7.8896044,
     85.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Portico', 'Italia', 'Appiano Gentile', 'piazza Libertà 36',
     45.733696, 8.979538,
     45.0, FALSE, FALSE,
     'Km Zero', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('85 Bistrot', 'Italia', 'Sesto San Giovanni', 'piazza Martiri di Via Fani 85',
     45.5342494, 9.2306544,
     45.0, FALSE, FALSE,
     'Lombardian', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Strattoria', 'Italia', 'Montrigiasco', 'Piazza Angelo Gnemmi',
     45.7715102, 8.5130006,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Belé', 'Italia', 'Milano', 'via Angelo Fumagalli 3',
     45.450007, 9.1702014,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Pietramare Natural Food', 'Italia', 'Isola di Capo Rizzuto', 'SS 106',
     38.93424, 16.9935,
     85.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €
    ('Salamensa', 'Italia', 'Montichiari', 'via Monsignor Oscar Romero 29',
     45.4157774, 10.4012442,
     20.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Café Quinson', 'Italia', 'Morgex', 'piazza Principe Tomaso 10',
     45.75689, 7.03578,
     160.0, FALSE, FALSE,
     'Cuisine from the Aosta Valley', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Botte', 'Italia', 'Stresa', 'via Giuseppe Garibaldi 8',
     45.8835969, 8.5410813,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Gennaro Di Pace', 'Italia', 'Monforte d''Alba', 'vicolo della Chiesa 8',
     44.6119183, 7.9755322,
     45.0, FALSE, FALSE,
     'Italiana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Essentia', 'Italia', 'Castrocaro Terme', 'piazza San Nicolò 2',
     44.1713217, 11.9468717,
     45.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('San Martino 26', 'Italia', 'San Gimignano', 'via San Martino 26',
     43.4699456, 11.0419316,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €
    ('Da Cecco', 'Italia', 'Torre del Lago Puccini', 'piazza Belvedere Puccini 10/12',
     43.83215, 10.306611,
     20.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Trippini', 'Italia', 'Civitella del Lago', 'via Italia 14',
     42.7107716, 12.2812758,
     85.0, FALSE, FALSE,
     'Umbrian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria Taviani', 'Italia', 'Bientina', 'piazza Vittorio Emanuele II 28',
     43.7103695, 10.6189082,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Il Labirinto', 'Italia', 'Brescia', 'via Corsica 224',
     45.5230057, 10.1959907,
     45.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Lou Ressignon', 'Italia', 'Cogne', 'via des Mines 22',
     45.6077032, 7.3593237,
     45.0, FALSE, FALSE,
     'Cuisine from the Aosta Valley', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Daniela', 'Italia', 'San Casciano dei Bagni', 'piazza Matteotti 7',
     42.871054, 11.875456,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€
    ('L''Osteria del Castellazzo', 'Italia', 'Salsomaggiore Terme', 'via Borgo Castellazzo 40',
     44.815369, 9.9787597,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Pescarino-Sapori di Terra e di Mare', 'Italia', 'Montemarcello', 'via Borea 52',
     44.0523468, 9.9521972,
     45.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Badessa', 'Italia', 'Casalgrande', 'via Case Secchia 2',
     44.6255198, 10.7485293,
     45.0, FALSE, FALSE,
     'Emilian', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Porta Restaurant', 'Italia', 'Bologna', 'piazza Vieira de Mello 4',
     44.5114605, 11.3572839,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Veranda del Color', 'Italia', 'Bardolino', 'via Santa Cristina 5',
     45.5430437, 10.7252429,
     85.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Loewengrube', 'Italia', 'Bolzano', 'piazza della Dogana 3',
     46.5002845, 11.3608536,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Retrobottega', 'Italia', 'Roma', 'via della Stelletta 4',
     41.9016565, 12.4753274,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Già Sotto l''Arco', 'Italia', 'Carovigno', 'corso Vittorio Emanuele 71',
     40.7071485, 17.6590721,
     160.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Saffron', 'Italia', 'Arezzo', 'piazza Sant''Agostino 16',
     43.4625448, 11.8810875,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('La Terrazza (Asolo)', 'Italia', 'Asolo', 'via Collegio 33',
     45.8030132, 11.9139009,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Locanda del Cardinale', 'Italia', 'Assisi', 'piazza del Vescovado 8',
     43.070105, 12.614407,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Focarile', 'Italia', 'Aprilia', 'via Pontina al km 46',
     41.5816052, 12.6488487,
     45.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Fermata', 'Italia', 'Spinetta Marengo', 'strada Bolla 2',
     44.8790669, 8.6599622,
     85.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('John Restaurant-Casamadre', 'Italia', 'Afragola', 'via Santa Maria la Nova 35',
     40.9336554, 14.3506078,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Cascina Lautier', 'Italia', 'Chieri', 'strada Baldissero 121',
     45.0261605, 7.8269632,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€
    ('Januarius', 'Italia', 'Napoli', 'via Duomo 146/148',
     40.8523593, 14.2588742,
     45.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Taverna del Castello', 'Italia', 'Torrechiara', 'via del Castello 25',
     44.6561141, 10.2749088,
     45.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Marina Grande', 'Italia', 'Amalfi', 'viale delle Regioni 4',
     40.63385, 14.60359,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €
    ('Antica Osteria da Penacio', 'Italia', 'Arcugnano', 'via Soghe 62',
     45.4507347, 11.5324236,
     20.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Antinè', 'Italia', 'Barbaresco', 'via Torino 16',
     44.725571, 8.080728,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Il Carpaccio', 'Italia', 'Acri', 'contrada Cocozzello 197/d',
     39.4807559, 16.2928963,
     20.0, FALSE, FALSE,
     'Calabrian', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Feel Como', 'Italia', 'Como', 'via Diaz 54',
     45.8096946, 9.0809621,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Walter Redaelli', 'Italia', 'Bettolle', 'via XXI Aprile 26',
     43.205414, 11.809369,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Pavone', 'Italia', 'Alghero', 'piazza Sulis 3/4',
     40.5564645, 8.3152583,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria le Logge', 'Italia', 'Sienna', 'via del Porrione 33',
     43.3184521, 11.3333516,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('RistoFante', 'Italia', 'Alzano Lombardo', 'via Mazzini 41',
     45.7322736, 9.7292663,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Sarti del Gusto', 'Italia', 'Cagliari', 'via Vico II Vincenzo Sulis 1/a',
     39.2167238, 9.1175893,
     45.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Stube Ladina', 'Italia', 'Arabba', 'via Precumon 24',
     46.49546, 11.868114,
     45.0, FALSE, FALSE,
     'Alpine', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria de l''Umbreleèr', 'Italia', 'Cicognolo', 'via Mazzini 13',
     45.1625063, 10.1959046,
     45.0, FALSE, FALSE,
     'Lombardian', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Da Peppe', 'Italia', 'Rotonda', 'corso Garibaldi 13',
     39.953684, 16.0397112,
     20.0, FALSE, FALSE,
     'Cuisine from Basilicata', 'anna_m'),
    -- Selected Restaurants | €€
    ('Ventuno.1', 'Italia', 'Alba', 'via Cuneo 8',
     44.6974689, 8.0364653,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €
    ('Osteria Ardenga', 'Italia', 'Diolo', 'via Maestra 6',
     44.9722466, 10.1543563,
     20.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Roof Garden', 'Italia', 'Bergamo', 'piazza della Repubblica 6',
     45.6981456, 9.6672047,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Villa Baroni', 'Italia', 'Bodio Lomnago', 'via Acquadro 12',
     45.7945545, 8.7556142,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('Il Giardino delle Esperidi', 'Italia', 'Bardolino', 'via Mameli 1',
     45.5483964, 10.7201346,
     45.0, FALSE, FALSE,
     'Seasonal Cuisine', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Molteni', 'Italia', 'Adria', 'via Ruzzina 2/4',
     45.0548986, 12.0555485,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Doc', 'Italia', 'Borgio Verezzi', 'via Vittorio Veneto 1',
     44.1598132, 8.3063314,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria dell''Oca Bianca', 'Italia', 'Cavaglià', 'via Umberto I 2',
     45.4058986, 8.0924561,
     45.0, FALSE, FALSE,
     'Piemontese', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Essencia Restaurant', 'Italia', 'Napoli', 'piazza Santa Maria la Nova 9',
     40.8438248, 14.2524835,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Locanda del Molino', 'Italia', 'Cortona', 'località Montanare 10',
     43.254763, 12.0662076,
     45.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Maré', 'Italia', 'Cesenatico', 'Molo di Levante 74',
     44.2067786, 12.4029481,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Indiniò', 'Italia', 'Raveo', 'via Norsinia 21/b',
     46.4343104, 12.8697139,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Canonica', 'Italia', 'Verona', 'vicolo San Matteo 3',
     45.4418856, 10.9938588,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Quattro Sensi', 'Italia', 'Brufa', 'via del Colle 38',
     43.0552896, 12.4664105,
     85.0, FALSE, FALSE,
     'Umbrian', 'riccardo_r'),
    -- Selected Restaurants | €
    ('Calvi Ristorante', 'Italia', 'Altamura', 'via Bari 134',
     40.83617, 16.56198,
     20.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Hostaria dai Musi', 'Italia', 'Alba', 'Piazza Michele Ferrero 4/d',
     44.696673, 8.0338445,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Opificio', 'Italia', 'Noventa Padovana', 'via Roma 131',
     45.4124222, 11.9498026,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria del Posto', 'Italia', 'Corciano', 'via Calderini 15',
     43.1061939, 12.3095201,
     45.0, FALSE, FALSE,
     'Umbrian', 'anna_m'),
    -- Selected Restaurants | €€
    ('Enoclub', 'Italia', 'Alba', 'piazza Michele Ferrero 4',
     44.6969031, 8.0339102,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Locanda del Marinaio', 'Italia', 'Cefalu', 'via Porpora 5',
     38.0409839, 14.0225187,
     45.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('La Veranda (Cernobbio)', 'Italia', 'Cernobbio', 'via Regina 40',
     45.843091, 9.0778706,
     160.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Trattoria del Bivio', 'Italia', 'Cerretto Langhe', 'localtà Cavallotti 9',
     44.5880877, 8.0747632,
     85.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('La Casa degli Spiriti', 'Italia', 'Costermano', 'via Monte Baldo 28',
     45.6144758, 10.7229645,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('I Salotti', 'Italia', 'Chiusi', 'Località Querce al Pino',
     43.01881, 11.90397,
     160.0, FALSE, FALSE,
     'Creativa', 'anna_m'),
    -- Selected Restaurants | €€
    ('Il Doretto', 'Italia', 'Cecina', 'via Pisana Livornese 32',
     43.3358312, 10.5058965,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('Kürbishof', 'Italia', 'Anterivo', 'via Guggal 23',
     46.2746922, 11.372941,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Faraglioni Restaurant', 'Italia', 'Aci Castello', 'lungomare dei Ciclopi 115',
     37.56007, 15.1597116,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('All''Osteria Bottega', 'Italia', 'Bologna', 'via Santa Caterina 51b/55',
     44.4911535, 11.3326794,
     45.0, FALSE, FALSE,
     'Emilian', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Tano Passami l''Olio', 'Italia', 'Milano', 'via Francesco Petrarca 4',
     45.4715781, 9.1696159,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Ensama Pesce', 'Italia', 'Sala Bolognese', 'via Aristide Dondarini 4',
     44.6105112, 11.2558128,
     160.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Restaurant 700', 'Italia', 'Ostuni', 'largo Michele Ayroldi Carissimo 14',
     40.7299792, 17.5770215,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Osteria alla Chiesa', 'Italia', 'Monfumo', 'via Chiesa Monfumo 14',
     45.8302987, 11.9210025,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('uovodiseppia Milano', 'Italia', 'Milano', 'via Amerigo Vespucci 11',
     45.4812369, 9.1928136,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('FØRMA contemporary restaurant', 'Italia', 'L''Aquila', 'via Fortebraccio 53',
     42.3489089, 13.4017873,
     45.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('Campo Cedro', 'Italia', 'Sienna', 'via Pian d''Ovile 54',
     43.3228359, 11.3312107,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Enoteca Meucci', 'Italia', 'Cortona', 'Località Riccio 71',
     43.2243467, 12.0049727,
     45.0, FALSE, FALSE,
     'Toscana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Granoturco Bistrot', 'Italia', 'Castagnole Piemonte', 'via Cavour 1',
     44.8983738, 7.5663275,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Anto e Robi', 'Italia', 'Robbio', 'Piazza della Libertà 8',
     45.2898439, 8.594069,
     45.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Casa Rapisarda', 'Italia', 'Numana', 'via IV Novembre 35',
     43.51088, 13.62254,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Radimare', 'Italia', 'Monopoli', 'via Beato Piergiorgio Frassati 5/a',
     40.9564457, 17.297695,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('La Loggia Bistrò', 'Italia', 'Verona', 'Corte Sgarzarie 7',
     45.4433263, 10.9957797,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Sücar Brüsc', 'Italia', 'Mantua', 'via Cavour 49',
     45.1598974, 10.7950722,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('I 5 Sensi', 'Italia', 'Cuneo', 'via Dronero 4',
     44.3927332, 7.5497669,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Da Marino al St Remy', 'Italia', 'Cagliari', 'via San Salvatore da Horta 7',
     39.21508, 9.11635,
     45.0, FALSE, FALSE,
     'Mediterranea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Repubblica di Perno', 'Italia', 'Monforte d''Alba', 'vicolo Cavour 5',
     44.6094368, 7.9743963,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Lofficina', 'Italia', 'Sirolo', 'via Piave 11',
     43.52627, 13.61662,
     45.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Il Cugnolo', 'Italia', 'Torre di Palme', 'contrada Cugnolo 19',
     43.1376, 13.81738,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La RiMa', 'Italia', 'Novara', 'viale Dante Alighieri 11/c',
     45.4487514, 8.6153729,
     45.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Frades La Terrazza', 'Italia', 'Porto Cervo', 'SP94 - Località Abbiadori',
     41.0943843, 9.5285309,
     85.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Pescheria', 'Italia', 'Salerno', 'corso Giuseppe Garibaldi 227',
     40.6767873, 14.7634393,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Riva (Numana)', 'Italia', 'Numana', 'Via Flaminia 109',
     43.50966, 13.62223,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Dogma', 'Italia', 'Roma', 'piazza Zama 34',
     41.8760196, 12.5093837,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Osteria il Moro', 'Italia', 'Trapani', 'via Giuseppe Garibaldi 86',
     38.0177393, 12.5122901,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Casina', 'Italia', 'Drena', 'località La Casina 1',
     45.9612596, 10.9516043,
     45.0, FALSE, FALSE,
     'Seasonal Cuisine', 'anna_m'),
    -- Selected Restaurants | €€
    ('InAlto Alfio Ghezzi Dolomites', 'Italia', 'Moena', 'via San Pellegrino 32',
     46.3790012, 11.8014773,
     45.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Hostaria da Franz', 'Italia', 'Venezia', 'salizada Sant''Antonin',
     45.435407, 12.3467382,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda Solagna', 'Italia', 'Quero', 'piazza I  Novembre 2',
     45.9389356, 11.9357476,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Locanda Altobelli', 'Italia', 'Terracina', 'via Santissima Annunziata 121',
     41.2899998, 13.2513079,
     45.0, FALSE, FALSE,
     'Tradizionale', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Savô', 'Italia', 'Laigueglia', 'piazza XXV Aprile 8',
     43.9776356, 8.1591594,
     85.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Il Grottino', 'Italia', 'Gualdo Cattaneo', 'piazza Beato Ugolino 5',
     42.9090284, 12.5556707,
     45.0, FALSE, FALSE,
     'Meats and Grills', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Osteria da Fiore', 'Italia', 'Venezia', 'calle del Scaleter',
     45.4386827, 12.3293006,
     160.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€
    ('Castel Toblino', 'Italia', 'Castel Toblino', 'località Castel Toblino 1',
     46.0562632, 10.9670517,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €
    ('U'' Vulesce', 'Italia', 'Cerignola', 'via Cesare Battisti 3',
     41.264648, 15.9036082,
     20.0, FALSE, FALSE,
     'Apulian', 'anna_m'),
    -- Selected Restaurants | €€
    ('Cravero - Osteria Contemporanea', 'Italia', 'Caltignaga', 'via Novara 8',
     45.5162919, 8.5912111,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €
    ('Miseria e Nobiltà', 'Italia', 'Campobasso', 'via Sant''Antonio Abate 16',
     41.56109, 14.6587518,
     20.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Ginevra', 'Italia', 'Ancona', 'Rupi di via XXIX Settembre 12',
     43.616116, 13.507044,
     85.0, FALSE, FALSE,
     'Mediterranea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Al Camin', 'Italia', 'Cortina d''Ampezzo', 'località Alverà 99',
     46.544327, 12.150794,
     45.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('San Quintino Resort', 'Italia', 'Busca', 'via Vigne 6',
     44.5274401, 7.4732865,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Malenco', 'Italia', 'Chiesa in Valmalenco', 'via Funivia 22',
     46.2739486, 9.8515438,
     45.0, FALSE, FALSE,
     'Alpine', 'anna_m'),
    -- Selected Restaurants | €
    ('Evan''s', 'Italia', 'Cassino', 'via Gari 1/3',
     41.4890294, 13.828005,
     20.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Luigi Pomata', 'Italia', 'Cagliari', 'viale Regina Margherita 18',
     39.2129934, 9.1157851,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Caffè Meletti', 'Italia', 'Ascoli Piceno', 'via del Trivio 56 (piazza del Popolo)',
     42.8543036, 13.5750901,
     45.0, FALSE, FALSE,
     'Cuisine from the Marches', 'anna_m'),
    -- Selected Restaurants | €€€
    ('IO Luigi Taglienti', 'Italia', 'Piacenza', 'via Pietro Giordani 14',
     45.0468867, 9.6924674,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Claudio Ristorante', 'Italia', 'Fabbrico', 'via Ferretti 109',
     44.8852991, 10.7869918,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Le Vie del Borgo', 'Italia', 'Toceno', 'via alla Piazza 6',
     46.1436973, 8.4675804,
     45.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Mater', 'Italia', 'Moggiona', 'via di Camaldoli 52',
     43.7817842, 11.7954217,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€
    ('L''Atelier 26', 'Italia', 'Saint-Christophe', 'località Gerandin 26',
     45.7434008, 7.3504867,
     45.0, FALSE, FALSE,
     'Francese', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Terrazza Bartolini', 'Italia', 'Milano Marittima', 'via A. Boito 30',
     44.2683528, 12.3568364,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Tenuta Casa Virginia', 'Italia', 'Villa d''Almè', 'via Cascina Violo 1',
     45.7535753, 9.6172554,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria da Cippi', 'Italia', 'Frisanco', 'Borgo Valdestali 5',
     46.2072604, 12.7585631,
     45.0, FALSE, FALSE,
     'Tradizionale', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Il Riccio', 'Italia', 'Anacapri', 'via Gradola 4/11',
     40.5609576, 14.203649,
     160.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Rosso di Sera', 'Italia', 'Castelletto sopra Ticino', 'via Pietro Nenni 2',
     45.7155611, 8.5911082,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Osteria del Tempo Perso', 'Italia', 'Ravenna', 'via Gamba 12',
     44.420345, 12.197758,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Laurin', 'Italia', 'Bolzano', 'via Laurin 4',
     46.4980585, 11.3570705,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €
    ('Posta (Bologna)', 'Italia', 'Bologna', 'via della Grada 21/a',
     44.4973648, 11.328168,
     20.0, FALSE, FALSE,
     'Toscana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Lio Pellegrini', 'Italia', 'Bergamo', 'via San Tomaso 47',
     45.7035284, 9.6755047,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Regallo', 'Italia', 'Biella', 'via Tollegno 4',
     45.574481, 8.0517711,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Rifugio Col Alt', 'Italia', 'Corvara in Badia', 'strada Col Alt',
     46.5524411, 11.8864378,
     45.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Ca'' 7', 'Italia', 'Bassano del Grappa', 'via Cunizza da Romano 4',
     45.779289, 11.7415811,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Zafferano', 'Italia', 'Città della Pieve', 'viale Icilio Vanni 1',
     42.9540242, 12.0057342,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Castello Malvezzi', 'Italia', 'Brescia', 'via Colle San Giuseppe 1',
     45.5755269, 10.249472,
     85.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Particolare di Siena', 'Italia', 'Sienna', 'via B. Peruzzi 26',
     43.3225911, 11.3367701,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Baita Fraina', 'Italia', 'Cortina d''Ampezzo', 'Località Fraina 1',
     46.5236962, 12.152584,
     85.0, FALSE, FALSE,
     'Country cooking', 'anna_m'),
    -- Selected Restaurants | €€€
    ('I 5 Campanili', 'Italia', 'Busto Arsizio', 'via Maino 18',
     45.620154, 8.8475578,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Vitis', 'Italia', 'Brixen', 'vicolo del Duomo 3',
     46.715773, 11.6565591,
     85.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria da Nando', 'Italia', 'Aosta', 'via Sant''Anselmo 99',
     45.7387983, 7.3262368,
     45.0, FALSE, FALSE,
     'Cuisine from the Aosta Valley', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Elephant', 'Italia', 'Brixen', 'via Rio Bianco 4',
     46.7191054, 11.6535404,
     85.0, FALSE, FALSE,
     'Classica', 'marco_f'),
    -- Selected Restaurants | €€
    ('A'' Paranza', 'Italia', 'Atrani', 'via Traversa Dragone 1',
     40.636899, 14.6082597,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Baretto di San Vigilio', 'Italia', 'Bergamo', 'via al Castello 1',
     45.70842, 9.65015,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€
    ('L''Acciuga (Ravenna)', 'Italia', 'Ravenna', 'viale Francesco Baracca 74',
     44.417719, 12.1925247,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€
    ('Agorà', 'Italia', 'Rende', 'via Rossini 178',
     39.345249, 16.242226,
     45.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Le Chiavi d''Oro', 'Italia', 'Arezzo', 'piazza San Francesco 7',
     43.4649614, 11.8805991,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€
    ('Dispensa Franciacorta', 'Italia', 'Torbiato', 'via Principe Umberto 23',
     45.6084857, 10.001314,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Fiamma Cremisi', 'Italia', 'Calvisano', 'via De Gasperi 37',
     45.37821, 10.34027,
     45.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Marsupino 1901', 'Italia', 'Briaglia', 'via Roma Serra 20',
     44.4003047, 7.8789728,
     85.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Ladinia', 'Italia', 'Corvara in Badia', 'strada Pedercorvara 10',
     46.5468459, 11.877733,
     85.0, FALSE, FALSE,
     'Regionale', 'marco_f'),
    -- Selected Restaurants | €€
    ('La Locanda di Pietracupa', 'Italia', 'San Donato in Poggio', 'via Madonna di Pietracupa 31',
     43.5316232, 11.2420871,
     45.0, FALSE, FALSE,
     'Toscana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Locanda Alpina', 'Italia', 'Brez', 'piazza Municipio 23',
     46.43114, 11.10719,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Feva', 'Italia', 'Castelfranco Veneto', 'Borgo Treviso 62',
     45.6720316, 11.9321644,
     160.0, FALSE, FALSE,
     'Creativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Trattoria Rigoletto', 'Italia', 'Brescia', 'via Fontane 54/b',
     45.573784, 10.242922,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Antico Ristorante Pagnanelli', 'Italia', 'Castel Gandolfo', 'via Gramsci 4',
     41.7495226, 12.648721,
     85.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€€
    ('La Sosta', 'Italia', 'Brescia', 'via San Martino della Battaglia 20',
     45.5337317, 10.2210584,
     85.0, FALSE, FALSE,
     'Lombardian', 'marco_f'),
    -- Selected Restaurants | €€
    ('Enoteca di Canelli - Casa Crippa', 'Italia', 'Canelli', 'corso Libertà 65/a',
     44.7155011, 8.2882991,
     45.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Residenza del Lago', 'Italia', 'Candia Canavese', 'via Roma 48',
     45.3272604, 7.8848734,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€
    ('Molin Vecio', 'Italia', 'Caldogno', 'via Giaroni 116',
     45.6096315, 11.524484,
     45.0, FALSE, FALSE,
     'Veneziana', 'marco_f'),
    -- Selected Restaurants | €€€
    ('El Gato', 'Italia', 'Chioggia', 'corso del Popolo 653',
     45.2218635, 12.2798212,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Dolia Gaeta', 'Italia', 'Gaeta', 'piazza Conca 22',
     41.210569, 13.5820891,
     85.0, FALSE, FALSE,
     'Contemporanea', 'anna_m'),
    -- Selected Restaurants | €€
    ('Romolo Mare', 'Italia', 'Bordighera', 'lungomare Argentina 1',
     43.7769699, 7.6731692,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Da Miro alla Lanterna', 'Italia', 'Viareggio', 'via Coppino 289',
     43.8635054, 10.2478156,
     85.0, FALSE, FALSE,
     'Pesce', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Pernambucco', 'Italia', 'Albenga', 'viale Italia 35',
     44.0469607, 8.223478,
     85.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Lamberti', 'Italia', 'Alassio', 'via Gramsci 57',
     44.0103712, 8.1772583,
     85.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Collina', 'Italia', 'Almenno San Bartolomeo', 'via Ca'' Paler 5',
     45.7583892, 9.5707144,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Le Cicale', 'Italia', 'Spinetta Marengo', 'via Pineroli 32',
     44.87612, 8.69275,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Mistral', 'Italia', 'Bellagio', 'via Teresio Olivelli 1',
     45.98775, 9.26198,
     160.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Stube Gourmet', 'Italia', 'Asiago', 'corso IV Novembre 65/67',
     45.8734842, 11.5098715,
     160.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Magiargè Osteria Contemporanea', 'Italia', 'Bordighera', 'via Dritta 2',
     43.7810218, 7.6732415,
     45.0, FALSE, FALSE,
     'Ligure', 'anna_m'),
    -- Selected Restaurants | €€
    ('Alla Fassa', 'Italia', 'Castelletto di Brenzone', 'via Nascimbeni 11',
     45.6908541, 10.7523726,
     45.0, FALSE, FALSE,
     'Pesce', 'marco_f'),
    -- Selected Restaurants | €
    ('I Pifferi', 'Italia', 'Sala Baganza', 'via Zappati 36',
     44.7161428, 10.215919,
     20.0, FALSE, FALSE,
     'Emilian', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Nido del Picchio', 'Italia', 'Carpaneto Piacentino', 'viale Patrioti 6',
     44.9125495, 9.793899,
     85.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Pinocchio', 'Italia', 'Borgomanero', 'via Matteotti 147',
     45.68877, 8.45695,
     85.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€
    ('Ortica', 'Italia', 'Pieve Ligure', 'via Giovanni Migone 4',
     44.3775228, 9.0966738,
     45.0, FALSE, FALSE,
     'Km Zero', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('Blum', 'Italia', 'Taormina', 'via Nazionale 147',
     37.85599, 15.30046,
     160.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €
    ('Antica Locanda', 'Italia', 'Caserta', 'piazza della Seta',
     41.0985915, 14.3172081,
     20.0, FALSE, FALSE,
     'Campanian', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Miil', 'Italia', 'Cermes', 'via Palade 1',
     46.62567, 11.147209,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Frades Porto Cervo', 'Italia', 'Milano', 'via Giuseppe Mazzini 20',
     45.461581, 9.1883869,
     85.0, FALSE, FALSE,
     'Sarda', 'anna_m'),
    -- Selected Restaurants | €€
    ('Villa Salina', 'Italia', 'Moretta', 'Via Santuario 25',
     44.7642736, 7.5322252,
     45.0, FALSE, FALSE,
     'Piemontese', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Scola', 'Italia', 'Castelbianco', 'via Pennavaire 166',
     44.1123062, 8.0586737,
     85.0, FALSE, FALSE,
     'Creativa', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Da Poli alla Stazione', 'Italia', 'Castelnovo di Sotto', 'viale della Repubblica 10',
     44.81269, 10.56648,
     45.0, FALSE, FALSE,
     'Classica', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Baita Piè Tofana', 'Italia', 'Cortina d''Ampezzo', 'Località Rumerlo',
     46.54099, 12.09893,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Antica Cucina 1983', 'Italia', 'Barletta', 'piazza Marina 4/5',
     41.3222121, 16.2844164,
     45.0, FALSE, FALSE,
     'Apulian', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('12 Ristorante', 'Italia', 'Cesenatico', 'via Armellini 12/a',
     44.1983653, 12.3938933,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Duomo (Alessandria)', 'Italia', 'Alessandria', 'via Parma 28',
     44.9122, 8.6191122,
     45.0, FALSE, FALSE,
     'Italiana Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Locanda Baggio', 'Italia', 'Asolo', 'via Bassane 1',
     45.8111209, 11.9088151,
     85.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€€
    ('Da Tonino', 'Italia', 'Capri', 'via Dentecala 14',
     40.548478, 14.253413,
     85.0, FALSE, FALSE,
     'Campanian', 'anna_m'),
    -- Selected Restaurants | €€
    ('Osteria Casale Ferrovia', 'Italia', 'Carovigno', 'via Stazione 1',
     40.7365002, 17.6852297,
     45.0, FALSE, FALSE,
     'Apulian', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Villa Pizzini', 'Italia', 'Stresa', 'Località Mottarone',
     45.8768, 8.4586,
     85.0, FALSE, FALSE,
     'Country cooking', 'riccardo_r'),
    -- Selected Restaurants | €€€€
    ('MOI Omakase', 'Italia', 'Prato', 'Viale Piave 10',
     43.8783258, 11.0986903,
     160.0, FALSE, FALSE,
     'Sushi', 'anna_m'),
    -- Selected Restaurants | €€
    ('Mima', 'Italia', 'Vico Equense', 'via Madonnelle 9',
     40.6596932, 14.4311256,
     45.0, FALSE, FALSE,
     'Seasonal Cuisine', 'marco_f'),
    -- Selected Restaurants | €
    ('Al Baliaggio', 'Italia', 'Venosa', 'via Vittorio Emanuele II 136',
     40.9636, 15.82133,
     20.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria Mood', 'Italia', 'San Bartolomeo al Mare', 'via Cesare Battisti 58',
     43.9236402, 8.0970532,
     45.0, FALSE, FALSE,
     'Ligure', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Suscettibile Salerno', 'Italia', 'Salerno', 'via dei Principati 45',
     40.6788381, 14.7661808,
     85.0, FALSE, FALSE,
     'Country cooking', 'marco_f'),
    -- Selected Restaurants | €€
    ('Gnocchetto', 'Italia', 'Tavernerio', 'via Primo Maggio 56',
     45.801, 9.14181,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('La Piemontese', 'Italia', 'Mariano Comense', 'via San Martino 48',
     45.70517, 9.18446,
     45.0, FALSE, FALSE,
     'Piemontese', 'anna_m'),
    -- Selected Restaurants | €€
    ('Meta Ristorante', 'Italia', 'Celle Ligure', 'via Generale Pescetto 5',
     44.3409071, 8.5444326,
     45.0, FALSE, FALSE,
     'Mediterranea', 'marco_f'),
    -- Selected Restaurants | €€€€
    ('Capogiro', 'Italia', 'Baia Sardinia', 'Località Li Mucchi Bianchi',
     41.1283048, 9.4720013,
     160.0, FALSE, FALSE,
     'Moderna', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Shiroya', 'Italia', 'Roma', 'via dei Baullari 147',
     41.8962633, 12.4724383,
     45.0, FALSE, FALSE,
     'Giapponese', 'anna_m'),
    -- Selected Restaurants | €€€€
    ('Hebbo Wine & Deli', 'Italia', 'Toblach', 'Località Lago di Dobbiaco',
     46.7066182, 12.2185029,
     160.0, FALSE, FALSE,
     'Innovativa', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Sotto l''Arco', 'Italia', 'Bologna', 'via Aretusi 5',
     44.50801, 11.28821,
     85.0, FALSE, FALSE,
     'Italiana', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Sa Cardiga e Su Schironi', 'Italia', 'Capoterra', 'strada statale 195 rotonda per Capoterra',
     39.1519524, 9.0187872,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('Erbaluigia', 'Italia', 'Pisa', 'via San Frediano 10/12',
     43.7184362, 10.3999667,
     45.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€€
    ('Locanda 53 Supper Club', 'Italia', 'Arco', 'via Vergolano 53',
     45.9195014, 10.8841028,
     85.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Osteria Europa', 'Italia', 'Asiago', 'Corso IV Novembre 65/67',
     45.87348, 11.5098328,
     45.0, FALSE, FALSE,
     'Veneziana', 'anna_m'),
    -- Selected Restaurants | €€
    ('Peperosa', 'Italia', 'Lucca', 'piazza dell''Anfiteatro 4',
     43.8454659, 10.505623,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Eggentaler', 'Italia', 'Cardano', 'via Val d''Ega 47',
     46.4909623, 11.3945865,
     45.0, FALSE, FALSE,
     'Meats and Grills', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Crub', 'Italia', 'Cava de'' Tirreni', 'corso Umberto I 125',
     40.697891, 14.7097243,
     45.0, FALSE, FALSE,
     'Pesce', 'anna_m'),
    -- Selected Restaurants | €€
    ('/gu.stà.re/ oltrecucina', 'Italia', 'Borgomanero', 'via Antonio Gramsci 20/b',
     45.6984614, 8.4657234,
     45.0, FALSE, FALSE,
     'Moderna', 'marco_f'),
    -- Selected Restaurants | €€
    ('Rendenèr Alpine Food', 'Italia', 'Pinzolo', 'Via Sorano 35',
     46.1616602, 10.7632894,
     45.0, FALSE, FALSE,
     'Contemporanea', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('Umami', 'Italia', 'Badalucco', 'Via Ugo Secondo Partigiano 1',
     43.9198701, 7.8439315,
     45.0, FALSE, FALSE,
     'Moderna', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Visione Restaurant and Living', 'Italia', 'Barbaresco', 'Strada Nicolini Basso 34',
     44.7065543, 8.0839593,
     85.0, FALSE, FALSE,
     'Contemporanea', 'marco_f'),
    -- Selected Restaurants | €€
    ('Ristorante de LEN', 'Italia', 'Cortina d''Ampezzo', 'Via Cesare Battisti 66',
     46.53884, 12.13403,
     45.0, FALSE, FALSE,
     'Regionale', 'riccardo_r'),
    -- Selected Restaurants | €€
    ('AceroRosso', 'Italia', 'Vodo di Cadore', 'Via Ruvignan 1',
     46.4108978, 12.2787883,
     45.0, FALSE, FALSE,
     'Regionale', 'anna_m'),
    -- Selected Restaurants | €€€
    ('Café Les Paillotes', 'Italia', 'Pescara', 'piazza Le Laudi 2',
     42.4548186, 14.2397968,
     85.0, FALSE, FALSE,
     'Moderna', 'marco_f')


-- =============================================================================
-- SEZIONE 3: RECENSIONI  (9 recensioni sui primi ristoranti)
-- =============================================================================

INSERT INTO recensioni (nome_ristorante, username_cliente, stelle, testo, risposta)
VALUES
    ('Casa Perbellini 12 Apostoli', 'mario_r', 5, 'Un''''esperienza gastronomica indimenticabile. Cucina di altissimo livello, servizio impeccabile.', 'Grazie mille per il suo entusiasmo! La aspettiamo presto.'),
    ('Le Calandre', 'mario_r', 4, 'Piatti creativi e ingredienti di prima qualità. Ambiente raffinato, prezzo elevato ma giustificato.', NULL),
    ('Enrico Bartolini al Mudec', 'mario_r', 5, 'Ogni portata è stata una sorpresa. Il menù degustazione è un viaggio gastronomico straordinario.', 'Grazie! Il suo apprezzamento è la nostra più grande soddisfazione.'),
    ('Uliassi', 'giulia_v', 5, 'Il miglior ristorante che abbia mai visitato. Vista mozzafiato, cucina di perfezione assoluta.', 'Grazie Giulia! Siamo felici che la sua serata sia stata memorabile.'),
    ('Enoteca Pinchiorri', 'giulia_v', 4, 'Cucina creativa di grande eleganza nel cuore della città. Un piccolo neo: difficile prenotare.', NULL),
    ('Atelier Moessmer Norbert Niederkofler', 'giulia_v', 5, 'Ristorante storico con cucina moderna di altissimo livello. Abbinamento perfetto tra classicità e innovazione.', 'Grazie! Cerchiamo ogni giorno di onorare la nostra storia con piatti contemporanei.'),
    ('Villa Crespi', 'luca_b', 4, 'Cucina tecnica e originale. Servizio attento e professionale. Ambiente elegante.', 'Grazie Luca! Il suo feedback ci sprona a migliorare ogni giorno.'),
    ('Quattro Passi', 'luca_b', 5, 'Una stella Michelin pienamente meritata. Cucina italiana contemporanea che sorprende ad ogni piatto.', 'Grazie! La sua visita è stata un onore per tutto il nostro team.'),
    ('La Pergola', 'luca_b', 4, 'Ottimo indirizzo per chi ama la cucina di qualità. Buon rapporto qualità-prezzo. Consigliato.', NULL)


-- =============================================================================
-- SEZIONE 4: PREFERITI  (9 preferiti tra i 3 clienti)
-- =============================================================================

INSERT INTO preferiti (username, nome_ristorante)
VALUES
    ('mario_r', 'Casa Perbellini 12 Apostoli'),
    ('mario_r', 'Le Calandre'),
    ('mario_r', 'Enrico Bartolini al Mudec'),
    ('giulia_v', 'Uliassi'),
    ('giulia_v', 'Enoteca Pinchiorri'),
    ('giulia_v', 'Atelier Moessmer Norbert Niederkofler'),
    ('luca_b', 'Villa Crespi'),
    ('luca_b', 'Quattro Passi'),
    ('luca_b', 'La Pergola')