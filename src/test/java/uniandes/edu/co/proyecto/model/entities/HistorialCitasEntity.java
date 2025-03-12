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
@Table(name="Historial_Citas")

public abstract class HistorialCitasEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
        private long id_Historias;
        private int afiliado_id;
        private int cita_id;
        private int orden_id;
        public long getId_Historias() {
            return id_Historias;
        }
        public void setId_Historias(long id_Historias) {
            this.id_Historias = id_Historias;
        }
        public int getAfiliado_id() {
            return afiliado_id;
        }
        public void setAfiliado_id(int afiliado_id) {
            this.afiliado_id = afiliado_id;
        }
        public int getCita_id() {
            return cita_id;
        }
        public void setCita_id(int cita_id) {
            this.cita_id = cita_id;
        }
        public int getOrden_id() {
            return orden_id;
        }
        public void setOrden_id(int orden_id) {
            this.orden_id = orden_id;
        }
        public HistorialCitasEntity(int afiliado_id, int cita_id, int orden_id) {
            this.afiliado_id = afiliado_id;
            this.cita_id = cita_id;
            this.orden_id = orden_id;
        }

        public HistorialCitasEntity(){;}
        
}