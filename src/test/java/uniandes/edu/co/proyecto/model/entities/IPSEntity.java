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
@Table(name="ips")


public abstract class IPSEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
        private long id_IPS;
        private String NIT;
        private String nombre;
        private String direccion;
        private String telefono;
        private List servicios_prestados;
        public long getId_IPS() {
            return id_IPS;
        }
        public void setId_IPS(long id_IPS) {
            this.id_IPS = id_IPS;
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
        public String getDireccion() {
            return direccion;
        }
        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
        public String getTelefono() {
            return telefono;
        }
        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
        public List getServicios_prestados() {
            return servicios_prestados;
        }
        public void setServicios_prestados(List servicios_prestados) {
            this.servicios_prestados = servicios_prestados;
        }
        public IPSEntity(String nIT, String nombre, String direccion, String telefono, List servicios_prestados) {
            NIT = nIT;
            this.nombre = nombre;
            this.direccion = direccion;
            this.telefono = telefono;
            this.servicios_prestados = servicios_prestados;
        }
        public IPSEntity(){;}
        @OneToMany(mappedBy = "ips")
        private List<MedicoEntity> medicos;

        @OneToMany(mappedBy = "ips")
        private List<CitaEntity> citas;

        @OneToMany(mappedBy = "ips")
        private List<OrdenServicioEntity> ordenesServicio;

        @ManyToOne
        @JoinColumn(name = "eps_id")
        private EPSEntity eps;


}
