import javax.sound.midi.*;
import java.util.List;

public class ReproductorMelodia {

    private static int NotaMidi(String nota, int octava) {
        int base = 0;
        switch (nota) {
            case "DO": base = 0; break;
            case "RE": base = 2; break;
            case "MI": base = 4; break;
            case "FA": base = 5; break;
            case "SOL": base = 7; break;
            case "LA": base = 9; break;
            case "SI": base = 11; break;
        }
        return 12 * (octava + 1) + base;
    }

    private static int DuracionFigura(String figura) {
        switch (figura) {
            case "REDONDA": return 1600;
            case "BLANCA": return 800;
            case "NEGRA": return 400;
            case "CORCHEA": return 200;
            default: return 400; 
        }
    }

    public static void reproducir(List<NotaMu> notas) {
        try {
            Synthesizer sintetizador = MidiSystem.getSynthesizer();
            sintetizador.open();
            MidiChannel canal = sintetizador.getChannels()[0];

            for (NotaMu nota : notas) {
                int notaMidi = NotaMidi(nota.getNota(), nota.getOctava());
                int duracion = DuracionFigura(nota.getFigura());

                canal.noteOn(notaMidi, 600); 
                Thread.sleep(duracion);
                canal.noteOff(notaMidi);
            }

            sintetizador.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
