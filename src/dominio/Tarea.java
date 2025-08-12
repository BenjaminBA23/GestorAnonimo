/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.Date;

/**
 *
 * @author Ben
 */
public class Tarea {
    private int id;
    private String titulo;
    private int prioridad;
    private String estado;
    private boolean especial;
    private java.util.Date fecha;

    // Constructor
    public Tarea(String titulo, int prioridad, String estado, boolean especial, java.util.Date fecha) {
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.estado = estado;
        this.especial = especial;
        this.fecha = fecha;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getPrioridad() { return prioridad; }
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public boolean isEspecial() { return especial; }
    public void setEspecial(boolean especial) { this.especial = especial; }
    public java.util.Date getFecha() { return fecha; }
    public void setFecha(java.util.Date fecha) { this.fecha = fecha; }
}
