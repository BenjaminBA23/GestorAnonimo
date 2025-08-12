/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import DAO.TareaDAO;
import dominio.Tarea;

/**
 *
 * @author Ben
 */
public class TareaServicio {

    private TareaDAO tareaDAO = new TareaDAO();

    // Método para agregar una tarea
    public void agregarTarea(String titulo, int prioridad, String estado, boolean especial, java.util.Date fecha) {
        Tarea tarea = new Tarea(titulo, prioridad, estado, especial, fecha);
        tareaDAO.insertarTarea(tarea);
    }

    public void listarTareas() {
    TareaDAO tareaDAO = new TareaDAO();  // Crear una instancia de TareaDAO
    tareaDAO.listarTareas(); // Llamar al método en la instancia
}

}