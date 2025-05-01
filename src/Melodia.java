import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Melodia {
    private List<NotaMu> notas;

    public Melodia() {
        notas = new ArrayList<>();
    }

    public void agregarNota(NotaMu nota) {
        notas.add(nota);
    }

    public void modificarNota(int indice, NotaMu nuevaNota) {
        if (indice >= 0 && indice < notas.size()) {
            notas.set(indice, nuevaNota);
        }
    }

    public void eliminarNota(int indice) {
        if (indice >= 0 && indice < notas.size()) {
            notas.remove(indice);
        }
    }

    public List<NotaMu> getNotas() {
        return notas;
    }

    public void guardarEnArchivo(String ruta) throws IOException {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta));
        escritor.write("[\n");
        for (int i = 0; i < notas.size(); i++) {
            NotaMu nota = notas.get(i);
            escritor.write("  { \"nota\": \"" + nota.getNota() + "\", \"figura\": \"" + nota.getFigura() + "\", \"octava\": " + nota.getOctava() + " }");
            if (i < notas.size() - 1) {
                escritor.write(",\n");
            }
        }
        escritor.write("\n]");
        escritor.close();
    }

    public void cargarDesdeArchivo(String ruta) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(ruta));
        StringBuilder contenido = new StringBuilder();
        String linea;
        while ((linea = lector.readLine()) != null) {
            contenido.append(linea.trim());
        }
        lector.close();

        String json = contenido.toString();
        notas.clear();

        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1, json.length() - 1);
        }

        String[] objetos = json.split("\\},\\{");

        for (String obj : objetos) {
            obj = obj.replace("{", "").replace("}", "").trim();
            String[] campos = obj.split(",");

            String nota = "";
            String figura = "";
            int octava = 0;

            for (String campo : campos) {
                String[] claveValor = campo.split(":");
                String clave = claveValor[0].replace("\"", "").trim();
                String valor = claveValor[1].replace("\"", "").trim();

                if (clave.equals("nota")) {
                    nota = valor;
                } else if (clave.equals("figura")) {
                    figura = valor;
                } else if (clave.equals("octava")) {
                    octava = Integer.parseInt(valor);
                }
            }
            notas.add(new NotaMu(nota, figura, octava));
        }
    }
}
