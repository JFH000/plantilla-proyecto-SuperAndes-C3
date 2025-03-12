package uniandes.edu.co.proyecto.model.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name="afiliados")


public abstract class EspecialidadEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
        private long id_Especialidad;
        private String nombre;

        public long getId_Especialidad() {
            return id_Especialidad;
        }
        public void setId_Especialidad(long id_Especialidad) {
            this.id_Especialidad = id_Especialidad;
        }
        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public EspecialidadEntity(String nombre) {
            this.nombre = nombre;
        }
        public EspecialidadEntity(){;}

}
