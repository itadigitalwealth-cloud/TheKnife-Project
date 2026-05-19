/**
 * TheKnife – Modulo Server
 * Classe principale del server TheKnife.
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */

package it.uninsubria.theknife.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Punto di ingresso del modulo server di TheKnife.
 * <p>
 * All'avvio richiede interattivamente:
 * <ol>
 *   <li>Host del server PostgreSQL</li>
 *   <li>Porta PostgreSQL (default: 5432)</li>
 *   <li>Nome del database</li>
 *   <li>Username PostgreSQL</li>
 *   <li>Password PostgreSQL</li>
 *   <li>Porta su cui il server TheKnife resta in ascolto (default: 9090)</li>
 * </ol>
 * </p>
 *
 * <p>
 * Una volta configurato, il server apre un {@link ServerSocket} e rimane
 * in attesa di connessioni. Per ogni client accettato crea un nuovo
 * {@link ClientHandler} e lo sottomette a un {@link ExecutorService}
 * (thread pool a dimensione fissa), realizzando la gestione concorrente
 * di più utenti richiesta dalle specifiche.
 * </p>
 *
 * <p>
 * Il server può essere arrestato digitando {@code quit} sulla console.
 * </p>
 *
 * <p>Avvio da riga di comando:</p>
 * <pre>
 *   java -jar bin/serverTK.jar
 * </pre>
 * oppure tramite Maven:
 * <pre>
 *   mvn -pl theknife-server exec:java
 * </pre>
 */
public class ServerTK {

    /** Numero massimo di client gestiti contemporaneamente dal thread pool. */
    private static final int MAX_CLIENT_PARALLELI = 50;

    /** Porta di default del server TheKnife (se l'utente non ne specifica una). */
    private static final int PORTA_DEFAULT = 9090;

    /** Porta di default di PostgreSQL. */
    private static final int PORTA_DB_DEFAULT = 5432;

    /**
     * Metodo principale del server.
     *
     * @param args argomenti da riga di comando (non utilizzati: tutto viene
     *             richiesto interattivamente per non esporre credenziali)
     */
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  TheKnife – Avvio Server");
        System.out.println("===========================================");

        Scanner scanner = new Scanner(System.in);

        // --- Raccolta parametri database ---
        String dbHost     = chiediStringa(scanner, "Host PostgreSQL", "localhost");
        int    dbPorta    = chiediIntero(scanner,  "Porta PostgreSQL", PORTA_DB_DEFAULT);
        String dbNome     = chiediStringa(scanner, "Nome database",    "theknife");
        String dbUtente   = chiediStringa(scanner, "Username DB",      "postgres");
        String dbPassword = chiediPassword(scanner, "Password DB");

        // --- Porta del server TheKnife ---
        int portaServer = chiediIntero(scanner, "Porta server TheKnife", PORTA_DEFAULT);

        // --- Verifica connessione al DB prima di aprire il socket ---
        System.out.println("\n[Server] Verifica connessione al database...");
        try (DatabaseManager test = new DatabaseManager(
                dbHost, dbPorta, dbNome, dbUtente, dbPassword)) {
            System.out.println("[Server] Connessione al database riuscita.");
        } catch (Exception e) {
            System.err.println("[Server] ERRORE: impossibile connettersi al database.");
            System.err.println("[Server] Dettaglio: " + e.getMessage());
            System.exit(1);
        }

        // --- Thread pool per la gestione concorrente dei client ---
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENT_PARALLELI);

        // --- Thread separato per il comando di arresto ---
        avviaThreadArresto(scanner, pool);

        // --- Loop principale di accettazione connessioni ---
        System.out.println("[Server] In ascolto sulla porta " + portaServer + "...");
        System.out.println("[Server] Digita 'quit' per arrestare il server.\n");

        try (ServerSocket serverSocket = new ServerSocket(portaServer)) {
            // serverSocket.setSoTimeout non impostato: accept() è bloccante.
            // Il thread di arresto chiuderà il serverSocket per sbloccarla.
            while (!pool.isShutdown()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler handler = new ClientHandler(
                        clientSocket, dbHost, dbPorta, dbNome, dbUtente, dbPassword
                    );
                    pool.submit(handler);
                } catch (IOException e) {
                    if (pool.isShutdown()) {
                        // Arresto normale: l'IOException è stata causata dalla
                        // chiusura del serverSocket da parte del thread di arresto.
                        break;
                    }
                    System.err.println("[Server] Errore nell'accettare la connessione: "
                                       + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("[Server] Impossibile aprire il socket sulla porta "
                               + portaServer + ": " + e.getMessage());
            pool.shutdownNow();
        }

        System.out.println("[Server] Server arrestato.");
    }

    // =========================================================================
    // Thread di arresto
    // =========================================================================

    /**
     * Avvia un thread daemon che legge dalla console e arresta il server
     * quando l'utente digita {@code quit}.
     *
     * @param scanner scanner sulla console
     * @param pool    thread pool da terminare all'arresto
     */
    private static void avviaThreadArresto(Scanner scanner, ExecutorService pool) {
        Thread threadArresto = new Thread(() -> {
            while (true) {
                String input = scanner.nextLine().trim();
                if ("quit".equalsIgnoreCase(input)) {
                    System.out.println("[Server] Arresto in corso...");
                    pool.shutdown();
                    try {
                        // Attende al massimo 10 secondi che i thread attivi completino
                        if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                            pool.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        pool.shutdownNow();
                        Thread.currentThread().interrupt();
                    }
                    break;
                }
            }
        });
        // Daemon: non impedisce la terminazione della JVM
        threadArresto.setDaemon(true);
        threadArresto.setName("thread-arresto");
        threadArresto.start();
    }

    // =========================================================================
    // Metodi di supporto per l'input interattivo
    // =========================================================================

    /**
     * Legge una stringa dalla console con un valore di default.
     *
     * @param scanner    scanner sulla console
     * @param etichetta  nome del parametro da mostrare
     * @param defVal     valore di default (mostrato tra parentesi)
     * @return stringa inserita dall'utente, o {@code defVal} se vuota
     */
    private static String chiediStringa(Scanner scanner, String etichetta, String defVal) {
        System.out.printf("  %s [%s]: ", etichetta, defVal);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defVal : input;
    }

    /**
     * Legge un numero intero dalla console con un valore di default.
     *
     * @param scanner   scanner sulla console
     * @param etichetta nome del parametro
     * @param defVal    valore di default
     * @return intero inserito, o {@code defVal} se l'input è vuoto o non valido
     */
    private static int chiediIntero(Scanner scanner, String etichetta, int defVal) {
        System.out.printf("  %s [%d]: ", etichetta, defVal);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return defVal;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("  Valore non valido, uso default: " + defVal);
            return defVal;
        }
    }

    /**
     * Legge la password dalla console.
     * Se disponibile usa {@link System#console()} per nascondere l'input;
     * in ambienti senza console (IDE, redirect) cade back su Scanner.
     *
     * @param scanner   scanner sulla console (fallback)
     * @param etichetta nome del parametro
     * @return password inserita
     */
    private static String chiediPassword(Scanner scanner, String etichetta) {
        java.io.Console console = System.console();
        if (console != null) {
            char[] pwd = console.readPassword("  %s: ", etichetta);
            return new String(pwd);
        } else {
            // Fallback per IDE o ambienti senza console nativa
            System.out.printf("  %s: ", etichetta);
            return scanner.nextLine();
        }
    }
}