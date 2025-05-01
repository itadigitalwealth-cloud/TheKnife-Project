package theknife.gui.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import theknife.GestoreFile;
import theknife.Recensione;
import theknife.Ristorante;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

/**
 * Mostra i dettagli di UN singolo ristorante e le sue recensioni.
 */
public class RestaurantDetailPanel extends GradientPanel {

    private FancyFrame parent;
    private Ristorante ristoranteCorrente;

    private JTextArea textArea;
    private JButton btnIndietro;

    public RestaurantDetailPanel(FancyFrame parent) {
        super(new Color(230, 230, 230), new Color(210, 210, 210));
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("Dettagli Ristorante", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel bot = new JPanel();
        btnIndietro = new JButton("Torna Indietro");
        bot.add(btnIndietro);

        add(bot, BorderLayout.SOUTH);

        btnIndietro.addActionListener(e -> parent.showCard(FancyFrame.CARD_SEARCH));
    }

    public void setRistorante(Ristorante r) {
        this.ristoranteCorrente = r;
        refreshDetails();
    }

    private void refreshDetails() {
        if (ristoranteCorrente == null) {
            textArea.setText("Nessun ristorante selezionato.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(ristoranteCorrente.getNome()).append("\n");
        sb.append("Città: ").append(ristoranteCorrente.getCitta()).append("\n");
        sb.append("Nazione: ").append(ristoranteCorrente.getNazione()).append("\n");
        sb.append("Indirizzo: ").append(ristoranteCorrente.getIndirizzo()).append("\n");
        sb.append("Fascia Prezzo: ").append(ristoranteCorrente.getFasciaPrezzo()).append(" €\n");
        sb.append("Delivery: ").append(ristoranteCorrente.isDelivery() ? "sì" : "no").append("\n");
        sb.append("Prenotazione: ").append(ristoranteCorrente.isPrenotazione() ? "sì" : "no").append("\n");
        sb.append("Tipo Cucina: ").append(ristoranteCorrente.getTipoCucina()).append("\n");
        sb.append("Proprietario: ").append(ristoranteCorrente.getProprietario()).append("\n");

        double media = GestoreFile.calcolaMediaStelle("data/recensioni.csv", ristoranteCorrente.getNome());
        sb.append("\nMedia Stelle: ").append(String.format("%.2f", media)).append("\n");

        sb.append("\n=== Recensioni ===\n");
        List<Recensione> recs = GestoreFile.caricaRecensioni("data/recensioni.csv");
        for (Recensione rec : recs) {
            if (rec.getIdRistorante().equalsIgnoreCase(ristoranteCorrente.getNome())) {
                sb.append(" - ").append(rec.getUsername()).append(" [").append(rec.getStelle()).append(" stelle]\n");
                sb.append("   Testo: ").append(rec.getTesto()).append("\n");
                if (rec.getRisposta() != null && !rec.getRisposta().isEmpty()) {
                    sb.append("   Risposta: ").append(rec.getRisposta()).append("\n");
                }
                sb.append("\n");
            }
        }

        textArea.setText(sb.toString());
    }
}
