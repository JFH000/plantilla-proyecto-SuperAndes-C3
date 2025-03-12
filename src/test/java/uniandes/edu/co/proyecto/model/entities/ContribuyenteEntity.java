package uniandes.edu.co.proyecto.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="citas")
@PrimaryKeyJoinColumn(name="id_afiliado")

public abstract class ContribuyenteEntity extends AfiliadoEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
        private long id_Contribuyente;

    public long getId_Contribuyente() {
        return id_Contribuyente;
    }

    public void setId_Contribuyente(long id_Contribuyente) {
        this.id_Contribuyente = id_Contribuyente;
    }


}
