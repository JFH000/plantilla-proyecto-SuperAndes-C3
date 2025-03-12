package uniandes.edu.co.proyecto.model.entities;

import java.util.Date;
import java.util.Timer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="citas")

public abstract class CitaEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
        private long id_Cita;
        private Date fecha;
        private Timer hora;
        private String estado;
        private int servicio_id;
        private int afiliado_id; 
        private int medico_id; 
        private int ips_id;
    
        
        public long getId_Cita() {
        return id_Cita;
    }
    public void setId_Cita(long id_Cita) {
        this.id_Cita = id_Cita;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Timer getHora() {
        return hora;
    }
    public void setHora(Timer hora) {
        this.hora = hora;
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
    public int getAfiliado_id() {
        return afiliado_id;
    }
    public void setAfiliado_id(int afiliado_id) {
        this.afiliado_id = afiliado_id;
    }
    public int getMedico_id() {
        return medico_id;
    }
    public void setMedico_id(int medico_id) {
        this.medico_id = medico_id;
    }
    public int getIps_id() {
        return ips_id;
    }
    public void setIps_id(int ips_id) {
        this.ips_id = ips_id;
    }
    public CitaEntity(Date fecha, Timer hora, String estado, int servicio_id, int afiliado_id, int medico_id,
            int ips_id) {
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.servicio_id = servicio_id;
        this.afiliado_id = afiliado_id;
        this.medico_id = medico_id;
        this.ips_id = ips_id;
    }
    public CitaEntity(){;}

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private ServicioEntity servicio;

    @ManyToOne
    @JoinColumn(name = "afiliado_id")
    private AfiliadoEntity afiliado;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private MedicoEntity medico;

    @ManyToOne
    @JoinColumn(name = "ips_id")
    private IPSEntity ips;

    @OneToOne(mappedBy = "cita")
    private HistorialCitasEntity historialCitas;
}


