package uniandes.edu.co.proyecto.modelo;

import java.sql.Date;

public class DocumentoIngresoDTO {

    private String nombreSucursal;
    private String nombreBodega;
    private String numeroDocumento;
    private Date fechaIngreso;
    private String nombreProveedor;

    public DocumentoIngresoDTO(String nombreSucursal, String nombreBodega, String numeroDocumento, Date fechaIngreso, String nombreProveedor) {
        this.nombreSucursal = nombreSucursal;
        this.nombreBodega = nombreBodega;
        this.numeroDocumento = numeroDocumento;
        this.fechaIngreso = fechaIngreso;
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
}
