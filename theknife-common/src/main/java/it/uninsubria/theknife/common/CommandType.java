/**
 * TheKnife – Modulo Common
 * Enumerazione dei comandi del protocollo Client/Server.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.common;

/**
 * Definisce tutti i comandi che il client può inviare al server.
 * <p>
 * Ogni comando corrisponde a una funzionalità dell'applicazione
 * come descritta nelle specifiche di progetto. Il server legge il
 * tipo di comando dalla {@link Request} ricevuta e instrada la
 * logica al metodo appropriato del {@code DatabaseManager}.
 * </p>
 *
 * <p>Convenzione di nomenclatura:</p>
 * <ul>
 *   <li>Comandi senza prefisso: accessibili anche da utenti guest</li>
 *   <li>Comandi {@code CLIENTE_*}: richiedono login con ruolo "cliente"</li>
 *   <li>Comandi {@code RISTORATORE_*}: richiedono login con ruolo "ristoratore"</li>
 * </ul>
 */
public enum CommandType {

    // -------------------------------------------------------------------------
    // Comandi senza autenticazione (utenti guest e registrati)
    // -------------------------------------------------------------------------

    /**
     * Ricerca ristoranti con filtri combinabili.
     * Parametri attesi nella Request:
     * "citta" (obbligatorio), "tipoCucina", "prezzoMin", "prezzoMax",
     * "delivery", "prenotazione", "stelleMin".
     */
    CERCA_RISTORANTI,

    /**
     * Recupera tutti i dettagli di un ristorante specifico.
     * Parametri: "nomeRistorante".
     */
    VISUALIZZA_RISTORANTE,

    /**
     * Recupera le recensioni di un ristorante (con media stelle e conteggio).
     * Parametri: "nomeRistorante".
     */
    VISUALIZZA_RECENSIONI,

    /**
     * Registra un nuovo utente (cliente o ristoratore).
     * Parametri: tutti i campi di Utente.
     */
    REGISTRAZIONE,

    /**
     * Autentica un utente esistente.
     * Parametri: "username", "passwordHash".
     * Risposta: oggetto Utente se le credenziali sono corrette, errore altrimenti.
     */
    LOGIN,

    // -------------------------------------------------------------------------
    // Comandi cliente (richiedono login con ruolo "cliente")
    // -------------------------------------------------------------------------

    /** Aggiunge un ristorante alla lista dei preferiti. Parametri: "nomeRistorante". */
    CLIENTE_AGGIUNGI_PREFERITO,

    /** Rimuove un ristorante dalla lista dei preferiti. Parametri: "nomeRistorante". */
    CLIENTE_RIMUOVI_PREFERITO,

    /** Recupera la lista dei ristoranti preferiti dell'utente loggato. */
    CLIENTE_VISUALIZZA_PREFERITI,

    /** Aggiunge una recensione. Parametri: "nomeRistorante", "stelle", "testo". */
    CLIENTE_AGGIUNGI_RECENSIONE,

    /** Modifica una recensione già inserita. Parametri: "nomeRistorante", "stelle", "testo". */
    CLIENTE_MODIFICA_RECENSIONE,

    /** Elimina una recensione inserita dall'utente loggato. Parametri: "nomeRistorante". */
    CLIENTE_ELIMINA_RECENSIONE,

    /** Recupera tutte le recensioni inserite dall'utente loggato. */
    CLIENTE_VISUALIZZA_MIE_RECENSIONI,

    // -------------------------------------------------------------------------
    // Comandi ristoratore (richiedono login con ruolo "ristoratore")
    // -------------------------------------------------------------------------

    /**
     * Aggiunge un nuovo ristorante. Parametri: tutti i campi di Ristorante
     * (escluso "proprietario", ricavato dalla sessione server).
     */
    RISTORATORE_AGGIUNGI_RISTORANTE,

    /**
     * Recupera i ristoranti del ristoratore loggato con media stelle e
     * numero recensioni.
     */
    RISTORATORE_VISUALIZZA_RIEPILOGO,

    /** Recupera le recensioni di tutti i ristoranti del ristoratore loggato. */
    RISTORATORE_VISUALIZZA_RECENSIONI,

    /**
     * Aggiunge o aggiorna la risposta a una recensione (max una per recensione).
     * Parametri: "nomeRistorante", "usernameCliente", "risposta".
     */
    RISTORATORE_RISPONDI_RECENSIONE
}