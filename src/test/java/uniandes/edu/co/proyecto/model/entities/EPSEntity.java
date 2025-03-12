package uniandes.edu.co.proyecto.model.entities;

import java.util.Date;
import java.util.List;
import java.util.Timer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name="citas")

public abstract class EPSEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
        private long id_EPS;
        private String NIT;
        private String nombre;
        private String ubicacion;
        private String telefono;
        private Date fecha_creacion;

        public long getId_EPS() {
            return id_EPS;
        }

        public void setId_EPS(long id_EPS) {
            this.id_EPS = id_EPS;
        }

        public String getNIT() {
            return NIT;
        }

        public void setNIT(String nIT) {
            NIT = nIT;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getUbicacion() {
            return ubicacion;
        }

        public void setUbicacion(String ubicacion) {
            this.ubicacion = ubicacion;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public Date getFecha_creacion() {
            return fecha_creacion;
        }

        public void setFecha_creacion(Date fecha_creacion) {
            this.fecha_creacion = fecha_creacion;
        }
        public EPSEntity(String nIT, String nombre, String ubicacion, String telefono, Date fecha_creacion) {
            NIT = nIT;
            this.nombre = nombre;
            this.ubicacion = ubicacion;
            this.telefono = telefono;
            this.fecha_creacion = fecha_creacion;
        }

        public EPSEntity(){;}
    

}
