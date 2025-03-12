package uniandes.edu.co.proyecto.model.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="medicos")


public abstract class MedicoEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
        private long id_Medico;
        private String nombre;
        private int especialidad_id;
        private String registro_medico;
        private int ips_id;
        
        public long getId_Medico() {
            return id_Medico;
        }
        public void setId_Medico(long id_Medico) {
            this.id_Medico = id_Medico;
        }
        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public int getEspecialidad_id() {
            return especialidad_id;
        }
        public void setEspecialidad_id(int especialidad_id) {
            this.especialidad_id = especialidad_id;
        }
        public String getRegistro_medico() {
            return registro_medico;
        }
        public void setRegistro_medico(String registro_medico) {
            this.registro_medico = registro_medico;
        }
        public int getIps_id() {
            return ips_id;
        }
        public void setIps_id(int ips_id) {
            this.ips_id = ips_id;
        }
        public MedicoEntity(String nombre, int especialidad_id, String registro_medico, int ips_id) {
            this.nombre = nombre;
            this.especialidad_id = especialidad_id;
            this.registro_medico = registro_medico;
            this.ips_id = ips_id;
        }
        public MedicoEntity(){;}

        @ManyToOne
        @JoinColumn(name = "especialidad_id")
        private EspecialidadEntity especialidad;

        @ManyToOne
        @JoinColumn(name = "ips_id")
        private IPSEntity ips;

        @OneToMany(mappedBy = "medico")
        private List<CitaEntity> citas;

        @OneToMany(mappedBy = "medico")
        private List<RecetaEntity> recetas;

        @OneToMany(mappedBy = "medico")
        private List<OrdenServicioEntity> ordenesServicio;

}
