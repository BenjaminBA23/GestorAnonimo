/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import dominio.Tarea;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Ben
 */
public class TareaDAO {

    // Método para insertar una tarea
    public void insertarTarea(Tarea tarea) {
        try (Connection con = Conexion.conectar()) {
            String sql = "INSERT INTO tareas (titulo, prioridad, estado, especial, fecha) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, tarea.getTitulo());
                ps.setInt(2, tarea.getPrioridad());
                ps.setString(3, tarea.getEstado());
                ps.setBoolean(4, tarea.isEspecial());
                ps.setDate(5, new java.sql.Date(tarea.getFecha().getTime()));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todas las tareas
    public List<Tarea> listarTareas() {
        List<Tarea> listaTareas = new ArrayList<>();
        try (Connection con = Conexion.conectar()) {
            String sql = "SELECT * FROM tareas";  // Obtener todas las tareas
            try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tarea tarea = new Tarea(
                        rs.getString("titulo"),
                        rs.getInt("prioridad"),
                        rs.getString("estado"),
                        rs.getBoolean("especial"),
                        rs.getDate("fecha")
                    );
                    tarea.setId(rs.getInt("id"));
                    listaTareas.add(tarea);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaTareas;
    }

    // Método para obtener una tarea por su ID
    public Tarea obtenerTareaPorId(int idTarea) {
        Tarea tarea = null;
        try (Connection con = Conexion.conectar()) {
            String sql = "SELECT * FROM tareas WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idTarea);  // Establecer el ID de la tarea a buscar
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Crear un objeto Tarea con los datos obtenidos
                        tarea = new Tarea(
                            rs.getString("titulo"),
                            rs.getInt("prioridad"),
                            rs.getString("estado"),
                            rs.getBoolean("especial"),
                            rs.getDate("fecha")
                        );
                        tarea.setId(rs.getInt("id"));  // Establecer el ID de la tarea
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarea;  // Devolver la tarea encontrada
    }

    // Método para actualizar el estado de una tarea
    public void actualizarEstadoTarea(Tarea tarea) {
        try (Connection con = Conexion.conectar()) {
            String sql = "UPDATE tareas SET estado = ? WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, tarea.getEstado());  // Establecer el nuevo estado de la tarea
                ps.setInt(2, tarea.getId());  // Establecer el ID de la tarea a actualizar
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar (marcar como eliminada) una tarea
public void eliminarTarea(int idTarea) {
    try (Connection con = Conexion.conectar()) {
        // Cambiar de UPDATE a DELETE para eliminar completamente la tarea
        String sql = "DELETE FROM tareas WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTarea);  // Establecer el ID de la tarea a eliminar
            ps.executeUpdate();      // Ejecutar el DELETE para eliminar la tarea
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Método para modificar una tarea
    public void modificarTarea(Tarea tarea) {
        try (Connection con = Conexion.conectar()) {
            String sql = "UPDATE tareas SET titulo = ?, prioridad = ?, estado = ?, especial = ?, fecha = ? WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, tarea.getTitulo());  // Actualizar el título
                ps.setInt(2, tarea.getPrioridad());
                ps.setString(3, tarea.getEstado());  // Actualizar el estado
                ps.setBoolean(4, tarea.isEspecial());
                ps.setDate(5, new java.sql.Date(tarea.getFecha().getTime()));  // Actualizar la fecha
                ps.setInt(6, tarea.getId());  // ID de la tarea que estamos modificando
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
