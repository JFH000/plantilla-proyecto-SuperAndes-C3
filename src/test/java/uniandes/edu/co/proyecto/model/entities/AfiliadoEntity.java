package uniandes.edu.co.proyecto.model.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;


@Entity
@Table(name="afiliados")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class AfiliadoEntity {
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
       
        public void setId_Afiliado(long id_Afiliado) {
            this.id_Afiliado = id_Afiliado;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public void setTipo(int tipo) {
            this.tipo = tipo;
        }
        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
        public void setFecha_nacimiento(Date fecha_nacimiento) {
            this.fecha_nacimiento = fecha_nacimiento;
        }
        public void setContribuyente_id(int contribuyente_id) {
            this.contribuyente_id = contribuyente_id;
        }
        public void setParentesco(String parentesco) {
            this.parentesco = parentesco;
        }
        public AfiliadoEntity(String nombre, int tipo, String direccion, String telefono, Date fecha_nacimiento,
                int contribuyente_id, String parentesco) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.direccion = direccion;
            this.telefono = telefono;
            this.fecha_nacimiento = fecha_nacimiento;
            this.contribuyente_id = contribuyente_id;
            this.parentesco = parentesco;
        }
        public AfiliadoEntity(){;}
            @ManyToOne
            @JoinColumn(name = "contribuyente_id")
            private ContribuyenteEntity contribuyente;

            @OneToMany(mappedBy = "afiliado")
            private List<CitaEntity> citas;

            @OneToMany(mappedBy = "afiliado")
            private List<RecetaEntity> recetas;

            @OneToMany(mappedBy = "afiliado")
            private List<OrdenServicioEntity> ordenesServicio;

            @ManyToOne
            @JoinColumn(name = "eps_id")
            private EPSEntity eps;
                
}
