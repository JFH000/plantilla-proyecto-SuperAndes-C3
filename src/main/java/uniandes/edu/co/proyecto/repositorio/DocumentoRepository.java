package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Documento;

public interface DocumentoRepository extends JpaRepository<Documento,Integer>{

    /**
     * RNF1: Agregar un documento a la tabla documentos
     *
     * @param id_orden_compra orden de compra asociada al nuevo documento
     */
    @Modifying
    @Transactional
    @Query(
        value = """
            INSERT INTO documentos (
                id_orden_compra
            )
            VALUES (
                :id_orden_compra
            )
            """,
        nativeQuery = true
    )
    void addDocumento(@Param("id_orden_compra")Integer id_orden_compra);

    /**
     * RFC6 - RFC7: Extrae la información necesaria de un documento
     *
     * @param id_bodega    idetificador de la bodega
     * @param id_sucursal  identificador de la sucursal
     * @return Collection<Object[]> Collection con todos los resultados obtenidos
     */
    @Modifying
    @Transactional
    @Query(
        value="""
                SELECT
                    doc.id,
                    doc.id_orden_compra,
                    doc.fecha_creacion,
                    su.id,
                    su.nombre,
                    bo.id,
                    bo.nombre,
                    prov.id,
                    prov.nombre,
                    pr.id,
                    pr.nombre,
                    co.cantidad,
                    co.precio_unitario
                FROM documentos doc
                JOIN ordenes_compra oc ON oc.id = doc.id_orden_compra
                JOIN compra co ON co.id_orden_compra = oc.id
                JOIN productos pr ON pr.id = co.id_producto
                JOIN proveedores prov ON prov.id = oc.id_proveedor
                JOIN bodegas bo ON bo.id = oc.id_bodega
                JOIN sucursales su ON su.id = bo.id_sucursal
                WHERE bo.id = :id_bodega
                    AND su.id = :id_sucursal
                    AND doc.fecha_creacion >= CURRENT_DATE - 30
                ORDER BY doc.id ASC
            """,
        nativeQuery=true
    )
    Collection<Object[]> getAllPorBodegaYSucursal(@Param("id_bodega") Integer id_bodega, @Param("id_sucursal") Integer id_sucursal);

    /**
     * No tiene ningun motivo de existencia (como yo), pero es muy importante
     * Se usa para iniciar la transacción
     * @return Collection<Object[]> collection de ciudades
     */
    @Query(
        value="select * from ciudades",
        nativeQuery=true
    )
    Collection<Object[]> start();

}
