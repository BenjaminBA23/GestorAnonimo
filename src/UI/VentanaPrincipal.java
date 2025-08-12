/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author Ben
 */
import DAO.TareaDAO;
import dominio.Tarea;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private JTextField txtTitulo;              // Campo de texto para el título de la tarea
    private JComboBox<Integer> comboPrioridad; // ComboBox para seleccionar la prioridad
    private JCheckBox checkEspecial;           // CheckBox para marcar la tarea como especial
    private JTable tablaTareas;                // Tabla para mostrar las tareas

    public VentanaPrincipal() {
        // Configurar la ventana principal
        setTitle("Gestión de Tareas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana
        setLayout(new BorderLayout()); // Usar BorderLayout para organizar los componentes

        // Crear los componentes de la interfaz gráfica
        crearFormulario();
        crearTabla();
        crearBotones();
    }

    private void crearFormulario() {
        // Panel para el formulario (título, prioridad, especial)
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2)); // Usamos GridLayout para ordenar los componentes

        // Crear los componentes
        txtTitulo = new JTextField();
        comboPrioridad = new JComboBox<>(new Integer[]{1, 2, 3}); // Prioridad 1=Alta, 2=Media, 3=Baja
        checkEspecial = new JCheckBox("Especial");

        // Agregar los componentes al panel
        panelFormulario.add(new JLabel("Título:"));
        panelFormulario.add(txtTitulo);
        panelFormulario.add(new JLabel("Prioridad:"));
        panelFormulario.add(comboPrioridad);
        panelFormulario.add(checkEspecial);

        // Añadir el panel al borde norte de la ventana
        add(panelFormulario, BorderLayout.NORTH);
    }

    private void crearTabla() {
        // Crear la tabla para mostrar las tareas
        tablaTareas = new JTable(new DefaultTableModel(new Object[]{"ID", "Título", "Prioridad", "Estado", "Fecha"}, 0));

        // Añadir la tabla a un JScrollPane para permitir el desplazamiento
        add(new JScrollPane(tablaTareas), BorderLayout.CENTER);
    }

    private void crearBotones() {
    // Crear el botón de agregar tarea
    JButton btnAgregar = new JButton("Agregar");
    btnAgregar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            agregarTarea();
        }
    });
    // Crear el panel para los botones
    JPanel panelBotones = new JPanel();
    panelBotones.add(btnAgregar);

    // Crear el botón "Alternar Estado"
    JButton btnAlternarEstado = new JButton("Alternar Estado");
    btnAlternarEstado.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            alternarEstadoTarea();
        }
    });
    panelBotones.add(btnAlternarEstado);  // Agregar el botón de "Alternar Estado"

    // Crear el botón "Eliminar"
    JButton btnEliminar = new JButton("Eliminar");
    btnEliminar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            eliminarTarea();  // Llamar al método para eliminar la tarea
        }
    });
    panelBotones.add(btnEliminar);  // Agregar el botón de "Eliminar" al panel

    // Crear el botón "Modificar"
    JButton btnModificar = new JButton("Modificar");
    btnModificar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            modificarTarea();  // Llamar al método para modificar la tarea
        }
    });
    panelBotones.add(btnModificar);  // Agregar el botón de "Modificar" al panel

    // Crear el botón "Ver Tareas"
    JButton btnVerTareas = new JButton("Ver Tareas");
    btnVerTareas.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            verTareas();  // Llamar al método para ver todas las tareas
        }
    });
    panelBotones.add(btnVerTareas);  // Agregar el botón de "Ver Tareas" al panel

    add(panelBotones, BorderLayout.SOUTH);  // Agregar los botones al borde sur de la ventana
}
    private void verTareas() {
    // Obtener todas las tareas de la base de datos
    TareaDAO tareaDAO = new TareaDAO();
    List<Tarea> listaTareas = tareaDAO.listarTareas();

    // Obtener el modelo de la tabla
    DefaultTableModel model = (DefaultTableModel) tablaTareas.getModel();
    model.setRowCount(0);  // Limpiar las filas de la tabla

    // Agregar las tareas a la tabla
    for (Tarea tarea : listaTareas) {
        model.addRow(new Object[]{tarea.getId(), tarea.getTitulo(), tarea.getPrioridad(), tarea.getEstado(), tarea.getFecha()});
    }
}
    
   private void eliminarTarea() {
    int row = tablaTareas.getSelectedRow();  // Obtener la fila seleccionada
    if (row >= 0) {
        int idTarea = (int) tablaTareas.getValueAt(row, 0);  // Obtener el ID de la tarea
        TareaDAO tareaDAO = new TareaDAO();

        // Llamar al método de eliminar tarea en el DAO, que ahora elimina la tarea físicamente
        tareaDAO.eliminarTarea(idTarea);

        // Actualizar la tabla después de la eliminación
        actualizarTabla();
    } else {
        JOptionPane.showMessageDialog(this, "Selecciona una tarea para eliminar.");
    }
}
   
private void modificarTarea() {
    int row = tablaTareas.getSelectedRow();  // Obtener la fila seleccionada
    if (row >= 0) {
        int idTarea = (int) tablaTareas.getValueAt(row, 0);  // Obtener el ID de la tarea
        TareaDAO tareaDAO = new TareaDAO();
        Tarea tarea = tareaDAO.obtenerTareaPorId(idTarea);  // Obtener los datos de la tarea seleccionada

        // Mostrar un formulario para modificar los datos de la tarea
        String nuevoTitulo = JOptionPane.showInputDialog(this, "Nuevo título:", tarea.getTitulo());
        if (nuevoTitulo != null && !nuevoTitulo.trim().isEmpty()) {
            tarea.setTitulo(nuevoTitulo);  // Cambiar el título de la tarea

            // Actualizar la tarea en la base de datos
            tareaDAO.modificarTarea(tarea);
            actualizarTabla();  // Actualizar la tabla después de la modificación
        }
    }
}
    private void agregarTarea() {
        // Obtener los datos de los componentes
        String titulo = txtTitulo.getText();
        int prioridad = (Integer) comboPrioridad.getSelectedItem();
        boolean especial = checkEspecial.isSelected();
        Date fecha = new Date(); // Fecha actual

        // Validación de los campos
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título no puede estar vacío.");
            return;
        }

        if (prioridad < 1 || prioridad > 3) {
            JOptionPane.showMessageDialog(this, "La prioridad debe ser entre 1 y 3.");
            return;
        }

        // Crear un objeto Tarea
        Tarea tarea = new Tarea(titulo, prioridad, "Pendiente", especial, fecha);

        // Insertar la tarea en la base de datos
        TareaDAO tareaDAO = new TareaDAO();
        tareaDAO.insertarTarea(tarea);

        // Actualizar la tabla con la nueva tarea
        actualizarTabla();
    }

    private void actualizarTabla() {
        // Obtener las tareas de la base de datos
        TareaDAO tareaDAO = new TareaDAO();
        List<Tarea> listaTareas = tareaDAO.listarTareas();

        // Obtener el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) tablaTareas.getModel();
        model.setRowCount(0);  // Limpiar las filas de la tabla

        // Agregar las tareas a la tabla
        for (Tarea tarea : listaTareas) {
            model.addRow(new Object[]{tarea.getId(), tarea.getTitulo(), tarea.getPrioridad(), tarea.getEstado(), tarea.getFecha()});
        }
    }
    private void alternarEstadoTarea() {
        int row = tablaTareas.getSelectedRow();  // Obtener la fila seleccionada
        if (row >= 0) {
            int idTarea = (int) tablaTareas.getValueAt(row, 0);  // Obtener el ID de la tarea
            TareaDAO tareaDAO = new TareaDAO();
            Tarea tarea = tareaDAO.obtenerTareaPorId(idTarea);  // Obtener la tarea por ID

            // Alternar el estado entre "Pendiente" y "Hecho"
            String nuevoEstado = tarea.getEstado().equals("Pendiente") ? "Hecho" : "Pendiente";

            // Verificar que el nuevo estado sea válido según la restricción CHECK
            if (!nuevoEstado.equals("Pendiente") && !nuevoEstado.equals("Hecho")) {
                JOptionPane.showMessageDialog(this, "El estado es inválido.");
                return;
            }

            tarea.setEstado(nuevoEstado);  // Cambiar el estado de la tarea

            // Actualizar el estado de la tarea en la base de datos
            tareaDAO.actualizarEstadoTarea(tarea);
            actualizarTabla();  // Actualizar la tabla después de cambiar el estado
        }
    }




    public static void main(String[] args) {
        // Crear la ventana y hacerla visible
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}
