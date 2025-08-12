# Sistema de Gestión de Tareas

Este proyecto es una aplicación de escritorio en Java utilizando **Swing** para la interfaz gráfica y **JDBC** para la conexión con una base de datos SQL Server. El sistema permite gestionar tareas, las cuales pueden ser **agregadas**, **modificadas**, **eliminadas** y **consultadas**.

## Funcionalidades

- **Agregar Tarea**: Permite al usuario agregar una nueva tarea proporcionando un título, prioridad, estado y una opción para marcarla como especial.
- **Modificar Tarea**: Permite editar los detalles de una tarea existente, como el título, prioridad, estado y si es especial.
- **Eliminar Tarea**: Permite eliminar completamente una tarea de la base de datos (se elimina físicamente de la tabla `tareas`).
- **Ver Tareas**: Muestra todas las tareas almacenadas en la base de datos en una tabla.

## Requisitos

- **Java 8 o superior**
- **SQL Server** para la base de datos
- **JDBC** para la conexión entre Java y SQL Server

## Instalación

1. **Clona el repositorio** en tu máquina local:
   ```bash
   git clone https://github.com/tuusuario/gestor-tareas.git
Configura la base de datos:
Asegúrate de tener SQL Server instalado y crear una base de datos llamada GestorTareasDB.

Creación de la base de datos:

sql
Copiar
CREATE DATABASE GestorTareasDB;
GO
Importa las tablas a la base de datos:
A continuación, debes crear la tabla tareas con la siguiente estructura:

sql
Copiar
CREATE TABLE tareas (
    id INT PRIMARY KEY IDENTITY,
    titulo NVARCHAR(255),
    prioridad INT,
    estado NVARCHAR(50) CHECK (estado IN ('Pendiente', 'Hecho')),
    especial BIT,
    fecha DATETIME
);
Conexión JDBC:
Configura la conexión en la clase Conexion con los parámetros de tu base de datos, por ejemplo:

java
Copiar
public class Conexion {
    public static Connection conectar() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=GestorTareasDB";
            String usuario = "sa";  // Tu usuario de SQL Server
            String contrasena = "tu_contrasena";  // Tu contraseña de SQL Server
            return DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
Descripción de las Clases
1. Tarea
Representa la entidad Tarea que contiene los atributos id, titulo, prioridad, estado, especial y fecha.

El ID es autoincrementable y se genera automáticamente en la base de datos.

2. TareaDAO
insertarTarea(Tarea tarea): Inserta una nueva tarea en la base de datos.

listarTareas(): Devuelve una lista con todas las tareas almacenadas en la base de datos.

obtenerTareaPorId(int idTarea): Devuelve una tarea según su ID.

actualizarEstadoTarea(Tarea tarea): Actualiza el estado de una tarea (de Pendiente a Hecho).

eliminarTarea(int idTarea): Elimina completamente una tarea de la base de datos utilizando DELETE.

Código del método eliminarTarea:

java
Copiar
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
3. VentanaPrincipal
crearFormulario(): Crea el formulario para ingresar los detalles de la tarea (titulo, prioridad, especial).

crearTabla(): Crea la tabla para mostrar todas las tareas.

crearBotones(): Crea los botones para agregar, modificar, eliminar tareas y ver todas las tareas.

agregarTarea(): Agrega una nueva tarea a la base de datos.

modificarTarea(): Permite modificar una tarea seleccionada.

eliminarTarea(): Elimina completamente la tarea seleccionada.

verTareas(): Muestra todas las tareas de la base de datos.

Código del botón Eliminar en VentanaPrincipal:

java
Copiar
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
Uso
Iniciar la aplicación:
Ejecuta el programa para abrir la ventana principal y comienza a interactuar con las tareas. Puedes agregar, eliminar, modificar y consultar tareas.

Eliminar una tarea:

Haz clic en la tarea que deseas eliminar de la tabla.

Haz clic en el botón Eliminar.

La tarea será eliminada físicamente de la base de datos y la tabla se actualizará.

Modificar una tarea:

Haz clic en la tarea que deseas modificar de la tabla.

Haz clic en el botón Modificar.

Se abrirá un cuadro de diálogo para cambiar el título de la tarea. Luego, la tarea será actualizada en la base de datos.

Ver todas las tareas:

Haz clic en el botón Ver Tareas para cargar todas las tareas existentes desde la base de datos y mostrarlas en la tabla.

Conclusión
Este sistema permite gestionar tareas de manera eficiente, permitiendo al usuario realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre las tareas. Puedes adaptarlo para agregar más funcionalidades o personalizarlo según tus necesidades.
