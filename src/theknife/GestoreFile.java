package theknife;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lettura/scrittura su CSV:
 * - utenti (7 campi)
 * - ristoranti (11 campi, l'ultimo "proprietario")
 * - recensioni (5 campi)
 * - preferiti (2 campi)
 * - cercaRistoranti(...) con nomeParziale, citta, tipoCucina, maxPrezzo,
 *   delivery, prenotazione
 */
public class GestoreFile {
    private static final String SEP = ";";

    // ------- UTENTI -------
    public static List<Utente> caricaUtenti(String percorso) {
        List<Utente> lista = new ArrayList<>();
        File f = new File(percorso);
        if (!f.exists())
            return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(SEP);
                if (c.length < 7)
                    continue;
                Utente u = new Utente(c[0], c[1], c[2], c[3], c[4], c[5], c[6]);
                lista.add(u);
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file utenti: " + e.getMessage());
        }
        return lista;
    }

    public static void salvaUtenti(List<Utente> utenti, String percorso) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(percorso))) {
            pw.println("Nome;Cognome;Username;Password;DataNascita;Domicilio;Ruolo");
            for (Utente u : utenti) {
                pw.println(u.getNome() + SEP + u.getCognome() + SEP + u.getUsername() + SEP +
                        u.getPasswordCifrata() + SEP + u.getDataNascita() + SEP +
                        u.getDomicilio() + SEP + u.getRuolo());
            }
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio del file utenti: " + e.getMessage());
        }
    }

    public static boolean usernameEsistente(String username, String percorso) {
        return caricaUtenti(percorso).stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    public static void aggiungiUtente(Utente u, String percorso) {
        if (usernameEsistente(u.getUsername(), percorso)) {
            System.out.println("ERRORE: username " + u.getUsername() + " già esistente!");
            return;
        }
        List<Utente> lista = caricaUtenti(percorso);
        lista.add(u);
        salvaUtenti(lista, percorso);
    }

    // ------- RISTORANTI -------
    public static List<Ristorante> caricaRistoranti(String percorso) {
        List<Ristorante> lista = new ArrayList<>();
        File f = new File(percorso);
        if (!f.exists())
            return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(SEP);
                if (c.length < 11)
                    continue;
                Ristorante r = new Ristorante(c[0], c[1], c[2], c[3],
                        Double.parseDouble(c[4]), Double.parseDouble(c[5]),
                        Double.parseDouble(c[6]), "si".equalsIgnoreCase(c[7]),
                        "si".equalsIgnoreCase(c[8]), c[9], c[10]);
                lista.add(r);
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file ristoranti: " + e.getMessage());
        }
        return lista;
    }

    public static boolean ristoranteEsistente(String nome, String percorso) {
        return caricaRistoranti(percorso).stream()
                .anyMatch(r -> r.getNome().equalsIgnoreCase(nome));
    }

    public static void salvaRistoranti(List<Ristorante> list, String percorso) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(percorso))) {
            pw.println("Nome;Nazione;Citta;Indirizzo;Latitudine;Longitudine;FasciaPrezzo;Delivery;Prenotazione;TipoCucina;Proprietario");
            for (Ristorante r : list) {
                pw.println(r.getNome() + SEP + r.getNazione() + SEP + r.getCitta() + SEP + r.getIndirizzo() + SEP +
                        r.getLatitudine() + SEP + r.getLongitudine() + SEP + r.getFasciaPrezzo() + SEP +
                        (r.isDelivery() ? "si" : "no") + SEP + (r.isPrenotazione() ? "si" : "no") + SEP +
                        r.getTipoCucina() + SEP + (r.getProprietario() != null ? r.getProprietario() : ""));
            }
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio del file ristoranti: " + e.getMessage());
        }
    }

    public static void aggiungiRistorante(Ristorante r, String percorso) {
        if (ristoranteEsistente(r.getNome(), percorso)) {
            System.out.println("ERRORE: ristorante " + r.getNome() + " già esistente!");
            return;
        }
        List<Ristorante> lista = caricaRistoranti(percorso);
        lista.add(r);
        salvaRistoranti(lista, percorso);
    }

    // ------- RECENSIONI -------
    public static List<Recensione> caricaRecensioni(String percorso) {
        List<Recensione> lista = new ArrayList<>();
        File f = new File(percorso);
        if (!f.exists())
            return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(SEP);
                if (c.length < 5)
                    continue;
                Recensione rec = new Recensione(c[0], c[1], Integer.parseInt(c[2]), c[3], c[4]);
                lista.add(rec);
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file recensioni: " + e.getMessage());
        }
        return lista;
    }

    public static void salvaRecensioni(List<Recensione> recs, String percorso) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(percorso))) {
            pw.println("IdRistorante;Username;Stelle;Testo;Risposta");
            for (Recensione r : recs) {
                pw.println(r.getIdRistorante() + SEP + r.getUsername() + SEP + r.getStelle() + SEP +
                        r.getTesto() + SEP + (r.getRisposta() != null ? r.getRisposta() : ""));
            }
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio del file recensioni: " + e.getMessage());
        }
    }

    public static void aggiungiRecensione(Recensione rec, String percorso) {
        List<Recensione> lista = caricaRecensioni(percorso);
        lista.add(rec);
        salvaRecensioni(lista, percorso);
    }

    public static void modificaRecensione(String percorso, String user, String idRisto,
                                          String nuovoTesto, int nuoveStelle) {
        List<Recensione> list = caricaRecensioni(percorso);
        for (Recensione r : list) {
            if (r.getUsername().equalsIgnoreCase(user)
                    && r.getIdRistorante().equalsIgnoreCase(idRisto)) {
                r.setTesto(nuovoTesto);
                r.setStelle(nuoveStelle);
            }
        }
        salvaRecensioni(list, percorso);
    }

    public static void eliminaRecensione(String percorso, String user, String idRisto) {
        List<Recensione> list = caricaRecensioni(percorso);
        list.removeIf(r -> r.getUsername().equalsIgnoreCase(user)
                && r.getIdRistorante().equalsIgnoreCase(idRisto));
        salvaRecensioni(list, percorso);
    }

    // ------- PREFERITI -------
    public static List<String> caricaPreferiti(String percorso, String username) {
        List<String> result = new ArrayList<>();
        File f = new File(percorso);
        if (!f.exists())
            return result;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(SEP);
                if (c.length < 2)
                    continue;
                if (c[0].equalsIgnoreCase(username)) {
                    result.add(c[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura dei preferiti: " + e.getMessage());
        }
        return result;
    }

    public static void salvaPreferiti(String percorso, List<String[]> righe) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(percorso))) {
            pw.println("Username;NomeRistorante");
            for (String[] row : righe) {
                pw.println(row[0] + SEP + row[1]);
            }
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dei preferiti: " + e.getMessage());
        }
    }

    public static void aggiungiPreferito(String percorso, String username, String risto) {
        List<String[]> righe = new ArrayList<>();
        File f = new File(percorso);
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String header = br.readLine();
                String line;
                while ((line = br.readLine()) != null) {
                    String[] c = line.split(SEP);
                    if (c.length == 2)
                        righe.add(c);
                }
            } catch (IOException e) {
                System.err.println("Errore nella lettura dei preferiti: " + e.getMessage());
            }
        }
        // evita duplicati
        boolean giaPresente = righe.stream()
                .anyMatch(row -> row[0].equalsIgnoreCase(username)
                        && row[1].equalsIgnoreCase(risto));
        if (!giaPresente) {
            righe.add(new String[]{username, risto});
        }
        salvaPreferiti(percorso, righe);
    }

    public static void rimuoviPreferito(String percorso, String username, String risto) {
        List<String[]> righe = new ArrayList<>();
        File f = new File(percorso);
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String header = br.readLine();
                String line;
                while ((line = br.readLine()) != null) {
                    String[] c = line.split(SEP);
                    if (c.length == 2) {
                        righe.add(c);
                    }
                }
            } catch (IOException e) {
                System.err.println("Errore nella lettura dei preferiti: " + e.getMessage());
            }
        }
        righe.removeIf(row -> row[0].equalsIgnoreCase(username)
                && row[1].equalsIgnoreCase(risto));
        salvaPreferiti(percorso, righe);
    }

    // ------- RICERCA -------
    public static List<Ristorante> cercaRistoranti(String percorso,
                                               String nomeParziale,
                                               String citta,
                                               String tipoCucina,
                                               Double maxPrezzo,
                                               Boolean delivery,
                                               Boolean prenotazione) {
    return caricaRistoranti(percorso).stream().filter(r -> {
        if (nomeParziale != null && !nomeParziale.isEmpty() &&
            !r.getNome().toLowerCase().contains(nomeParziale.toLowerCase())) return false;
        if (citta != null && !citta.isEmpty() &&
            !r.getCitta().toLowerCase().contains(citta.toLowerCase())) return false;
        if (tipoCucina != null && !tipoCucina.isEmpty() &&
            !r.getTipoCucina().toLowerCase().contains(tipoCucina.toLowerCase())) return false;
        if (maxPrezzo != null && r.getFasciaPrezzo() > maxPrezzo) return false;
        if (delivery != null && delivery && !r.isDelivery()) return false;
        if (prenotazione != null && prenotazione && !r.isPrenotazione()) return false;        
        return true;
    }).collect(Collectors.toList());
}


    public static double calcolaMediaStelle(String percorsoRecensioni, String nomeRisto) {
        List<Recensione> recs = caricaRecensioni(percorsoRecensioni);
        List<Recensione> filtrate = recs.stream()
                .filter(r -> r.getIdRistorante().equalsIgnoreCase(nomeRisto))
                .collect(Collectors.toList());
        if (filtrate.isEmpty())
            return 0.0;

        double somma = filtrate.stream().mapToInt(Recensione::getStelle).sum();
        return somma / filtrate.size();
    }
}
