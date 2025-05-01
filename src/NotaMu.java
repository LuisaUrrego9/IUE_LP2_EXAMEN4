public class NotaMu {
    private String nota;
    private String figura;
    private int octava;

    public NotaMu() {
    }

    public NotaMu(String nota, String figura, int octava) {
        this.nota = nota;
        this.figura = figura;
        this.octava = octava;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFigura() {
        return figura;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }

    public int getOctava() {
        return octava;
    }

    public void setOctava(int octava) {
        this.octava = octava;
    }
}
