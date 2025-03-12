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
@Table(name="servicios")
"lol"

public abstract class ServicioEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
        private long id_Afiliado;
        private String nombre;
        private int tipo;
        private String direccion;
        private String telefono;
        private Date fecha_nacimiento; 
        private int contribuyente_id; 
        private String parentesco;
        public long getId_Afiliado() {
            return id_Afiliado;
        }
        public void setId_Afiliado(long id_Afiliado) {
            this.id_Afiliado = id_Afiliado;
        }
        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public int getTipo() {
            return tipo;
        }
        public void setTipo(int tipo) {
            this.tipo = tipo;
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
        public Date getFecha_nacimiento() {
            return fecha_nacimiento;
        }
        public void setFecha_nacimiento(Date fecha_nacimiento) {
            this.fecha_nacimiento = fecha_nacimiento;
        }
        public int getContribuyente_id() {
            return contribuyente_id;
        }
        public void setContribuyente_id(int contribuyente_id) {
            this.contribuyente_id = contribuyente_id;
        }
        public String getParentesco() {
            return parentesco;
        }
        public void setParentesco(String parentesco) {
            this.parentesco = parentesco;
        }
        public ServicioEntity(String nombre, int tipo, String direccion, String telefono, Date fecha_nacimiento,
        int contribuyente_id, String parentesco) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.contribuyente_id = contribuyente_id;
        this.parentesco = parentesco;
        }
        public ServicioEntity(){;}

        @ManyToOne
        @JoinColumn(name = "especialidad_id")
        private EspecialidadEntity especialidad;

        @OneToMany(mappedBy = "servicio")
        private List<CitaEntity> citas;

        @OneToMany(mappedBy = "servicio")
        private List<OrdenServicioEntity> ordenesServicio;

}
