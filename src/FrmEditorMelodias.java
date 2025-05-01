import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class FrmEditorMelodias extends JFrame {
    private Melodia melodia;
    private JTable tablaNotas;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> comboNota;
    private JComboBox<String> comboFigura;
    private JComboBox<Integer> comboOctava;
    

    public FrmEditorMelodias() {
        melodia = new Melodia();
        
        setTitle("Editor de Melodías");
        setSize(950, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(null);
        panelSuperior.setPreferredSize(new Dimension(900, 40));
        
        JButton btnCargar = new JButton("Cargar Melodía");
        btnCargar.setBounds(10, 10, 120, 30);
        JButton btnGuardar = new JButton("Guardar Melodía");
        btnGuardar.setBounds(125, 10, 129, 30);
        JButton btnAgregar = new JButton("Agregar Nota");
        btnAgregar.setBounds(250,10,115,30);
        JButton btnModificar = new JButton("Modificar Nota");
        btnModificar.setBounds(570, 10, 120, 30);
        JButton btnEliminar = new JButton("Eliminar Nota");
        btnEliminar.setBounds(690, 10, 120, 30);
        JButton btnReproducir = new JButton("Reproducir Melodía");
        btnReproducir.setBounds(800, 10, 150,30);

        comboNota = new JComboBox<>(new String[]{"DO", "RE", "MI", "FA", "SOL", "LA", "SI"});
        comboNota.setBounds(365, 10, 70, 30);
        comboFigura = new JComboBox<>(new String[]{"REDONDA", "BLANCA", "NEGRA", "CORCHEA"});
        comboFigura.setBounds(432, 10, 100, 30);
        comboOctava = new JComboBox<>(new Integer[]{3, 4, 5});
        comboOctava.setBounds(530, 10, 50, 30);

        panelSuperior.add(btnCargar);
        panelSuperior.add(btnGuardar);
        panelSuperior.add(btnAgregar);
        panelSuperior.add(comboNota);
        panelSuperior.add(comboFigura);
        panelSuperior.add(comboOctava);
        panelSuperior.add(btnModificar);
        panelSuperior.add(btnEliminar);
        panelSuperior.add(btnReproducir);

        add(panelSuperior, BorderLayout.NORTH);


        modeloTabla = new DefaultTableModel(new Object[]{"Nota", "Figura", "Octava"}, 0);
        tablaNotas = new JTable(modeloTabla);
        add(new JScrollPane(tablaNotas), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> agregarNota());
        btnModificar.addActionListener(e -> modificarNota());
        btnEliminar.addActionListener(e -> eliminarNota());
        btnGuardar.addActionListener(e -> guardarMelodia());
        btnCargar.addActionListener(e -> cargarMelodia());
        btnReproducir.addActionListener(e -> reproducirMelodia());

        setVisible(true);
    }

    private void agregarNota() {
        String nota = (String) comboNota.getSelectedItem();
        String figura = (String) comboFigura.getSelectedItem();
        int octava = (Integer) comboOctava.getSelectedItem();

        NotaMu nueva = new NotaMu(nota, figura, octava);
        melodia.agregarNota(nueva);
        modeloTabla.addRow(new Object[]{nota, figura, octava});
    }

    private void modificarNota() {
        int filaSeleccionada = tablaNotas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nota = (String) comboNota.getSelectedItem();
            String figura = (String) comboFigura.getSelectedItem();
            int octava = (Integer) comboOctava.getSelectedItem();

            NotaMu nueva = new NotaMu(nota, figura, octava);
            melodia.modificarNota(filaSeleccionada, nueva);

            modeloTabla.setValueAt(nota, filaSeleccionada, 0);
            modeloTabla.setValueAt(figura, filaSeleccionada, 1);
            modeloTabla.setValueAt(octava, filaSeleccionada, 2);
        }
    }

    private void eliminarNota() {
        int filaSeleccionada = tablaNotas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            melodia.eliminarNota(filaSeleccionada);
            modeloTabla.removeRow(filaSeleccionada);
        }
    }

    private void guardarMelodia() {
        JFileChooser chooser = new JFileChooser();
        int resultado = chooser.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                melodia.guardarEnArchivo(chooser.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(this, "La melodía se guardó correctamente.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el archivo.");
            }
        }
    }

    private void cargarMelodia() {
        JFileChooser chooser = new JFileChooser();
        int resultado = chooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                melodia.cargarDesdeArchivo(chooser.getSelectedFile().getPath());
                modeloTabla.setRowCount(0); 
                for (NotaMu nota : melodia.getNotas()) {
                    modeloTabla.addRow(new Object[]{nota.getNota(), nota.getFigura(), nota.getOctava()});
                }
                JOptionPane.showMessageDialog(this, "La melodía se cargó correctamente.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo.");
            }
        }
    }

    private void reproducirMelodia() {
        if (!melodia.getNotas().isEmpty()) {
            ReproductorMelodia.reproducir(melodia.getNotas());
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron notas para reproducir.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FrmEditorMelodias::new);
    }
}

