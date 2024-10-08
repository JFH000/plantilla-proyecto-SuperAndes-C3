package uniandes.edu.co.proyecto.repositorio;

import java.sql.Date;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Orden_compra;

public interface Orden_compraRepository extends JpaRepository<Orden_compra, Integer> {

    /**
     * RF7 : Crea una orden de compra.
     * SQL : Inserta un nuevo registro en la tabla 'ordenes_compra'
     *       con un ID generado automáticamente y valores específicos
     *       para fechas, estado, bodega y proveedor.
     *
     * @param fecha_creacion  Fecha en la que se crea la orden (formato YYYY-MM-DD).
     * @param fecha_esperada  Fecha en la que se espera que llegue el pedido (formato YYYY-MM-DD).
     * @param estado          Estado en el que se encuentra la orden (vigente, entregada o anulada).
     * @param id_bodega       Identificador de la bodega que solicita la orden de compra.
     * @param id_proveedor    Identificador del proveedor al que se le compran los productos.
     */
    @Modifying
    @Transactional
    @Query(
        value = "INSERT INTO ordenes_compra (id, fecha_creacion, fecha_esperada, estado, id_bodega, id_proveedor) " +
                "VALUES (ids_orden_compra.NEXTVAL, :fecha_creacion, :fecha_esperada, :estado, :id_bodega, :id_proveedor)",
        nativeQuery = true
    )
    void insertarOrden_compra(@Param("fecha_creacion") Date fecha_creacion,
                              @Param("fecha_esperada") Date fecha_esperada,
                              @Param("estado") String estado,
                              @Param("id_bodega") Integer id_bodega,
                              @Param("id_proveedor") Integer id_proveedor);

    /**
     * RF8 : Actualiza el estado de una orden de compra a 'anulada'.
     * SQL : Actualiza el estado de la orden de compra para
     *       el registro con el ID especificado.
     *
     * @param id Identificador de la orden de compra cuya estado va a cambiar.
     */
    @Modifying
    @Transactional
    @Query(
        value = "UPDATE ordenes_compra " +
                "SET estado = 'anulada' " +
                "WHERE id = :id",
        nativeQuery = true
    )
    void anularOrden_compra(@Param("id") Integer id);

    /**
     * SQL : Recupera todos los registros de la tabla 'ordenes_compra'.
     *
     * @return Collection de las órdenes de compra encontradas.
     */
    @Query(
        value = "SELECT * FROM ordenes_compra",
        nativeQuery = true
    )
    Collection<Orden_compra> getAll();

    /**
     * SQL : Recupera el registro de la tabla 'ordenes_compra' cuyo ID coincide con el valor especificado.
     *
     * @param id Identificador de la orden de compra que se quiere encontrar.
     * @return Collection con la orden de compra encontrada.
     */
    @Query(
        value = "SELECT * FROM ordenes_compra " +
                "WHERE id = :id",
        nativeQuery = true
    )
    Collection<Orden_compra> darOrden_compra(@Param("id") Integer id);

    /**
     * Obtiene la última orden de compra creada.
     *
     * @return Collection con un único elemento que será el último ID creado.
     */
    @Query(
        value = "SELECT * FROM ordenes_compra " +
                "WHERE id = (SELECT MAX(id) FROM ordenes_compra)",
        nativeQuery = true
    )
    Collection<Orden_compra> getLast();
}
