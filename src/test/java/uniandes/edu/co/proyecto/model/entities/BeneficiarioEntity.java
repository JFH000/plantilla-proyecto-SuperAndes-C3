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
@Table(name="Beneficiarios")
@PrimaryKeyJoinColumn(name="id_afiliado")

public abstract class BeneficiarioEntity extends AfiliadoEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
        private long id_Beneficiario; {
            
}

        public long getId_Beneficiario() {
            return id_Beneficiario;
        }

        public void setId_Beneficiario(long id_Beneficiario) {
            this.id_Beneficiario = id_Beneficiario;
        }
}
