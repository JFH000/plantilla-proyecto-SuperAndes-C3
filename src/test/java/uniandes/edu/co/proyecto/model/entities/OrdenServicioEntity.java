package uniandes.edu.co.proyecto.model.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="ordenServicio")

public abstract class OrdenServicioEntity {
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private long id_OrdenService;
        private Date fecha;
        private String estado;
        private int servicio_id;
        private int medico_id;
        private int afiliado_id; 
        private int ips_id;
        
        public long getId_OrdenService() {
            return id_OrdenService;
        }
        public void setId_OrdenService(long id_OrdenService) {
            this.id_OrdenService = id_OrdenService;
        }
        public Date getFecha() {
            return fecha;
        }
        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }
        public String getEstado() {
            return estado;
        }
        public void setEstado(String estado) {
            this.estado = estado;
        }
        public int getServicio_id() {
            return servicio_id;
        }
        public void setServicio_id(int servicio_id) {
            this.servicio_id = servicio_id;
        }
        public int getMedico_id() {
            return medico_id;
        }
        public void setMedico_id(int medico_id) {
            this.medico_id = medico_id;
        }
        public int getAfiliado_id() {
            return afiliado_id;
        }
        public void setAfiliado_id(int afiliado_id) {
            this.afiliado_id = afiliado_id;
        }
        public int getIps_id() {
            return ips_id;
        }
        public void setIps_id(int ips_id) {
            this.ips_id = ips_id;
        } 
        public OrdenServicioEntity(Date fecha, String estado, int servicio_id, int medico_id, int afiliado_id,
                int ips_id) {
            this.fecha = fecha;
            this.estado = estado;
            this.servicio_id = servicio_id;
            this.medico_id = medico_id;
            this.afiliado_id = afiliado_id;
            this.ips_id = ips_id;
        }
        public OrdenServicioEntity(){;}

        @ManyToOne
        @JoinColumn(name = "servicio_id")
        private ServicioEntity servicio;

        @ManyToOne
        @JoinColumn(name = "medico_id")
        private MedicoEntity medico;

        @ManyToOne
        @JoinColumn(name = "afiliado_id")
        private AfiliadoEntity afiliado;

        @ManyToOne
        @JoinColumn(name = "ips_id")
        private IPSEntity ips;
}
