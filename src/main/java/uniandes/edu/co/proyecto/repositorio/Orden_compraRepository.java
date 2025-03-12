package uniandes.edu.co.proyecto.repositorio;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

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
        value = """
            INSERT INTO ordenes_compra (
                fecha_creacion,
                fecha_esperada,
                estado,
                id_bodega,
                id_proveedor
            )
            VALUES (
                :fecha_creacion,
                :fecha_esperada,
                :estado,
                :id_bodega,
                :id_proveedor
            )
            """,
        nativeQuery = true
    )
    void insertarOrden_compra(  @Param("fecha_creacion") Date fecha_creacion,
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
        value = """
            UPDATE ordenes_compra
            SET estado = 'anulada'
            WHERE id = :id
            """,
        nativeQuery = true
    )
    void anularOrden_compra(@Param("id") Integer id);

    /**
     * Recupera todos los registros de la tabla 'ordenes_compra'.
     *
     * @return Collection de las órdenes de compra encontradas.
     */
    @Query(
        value = """
            SELECT
                oc.*,
                c.cantidad,
                c.precio_unitario,
                c.id_producto
            FROM ordenes_compra oc
            JOIN compra c ON c.id_orden_compra = oc.id
            """,
        nativeQuery = true
    )
    Collection<Object> getAll();

    /**
     * RNF6: Obtiene una orden de compra dado su id
     *
     * @param id Identificador de la orden de compra que se quiere encontrar.
     * @return Collection con la orden de compra encontrada.
     */
    @Query(
        value = """
            SELECT *
            FROM ordenes_compra
            WHERE id = :id
            """,
        nativeQuery = true
    )
    Collection<Orden_compra> darOrden_compra(@Param("id") Integer id);

    /**
     * Obtiene la última orden de compra creada.
     *
     * @return Collection con un único elemento que será el último ID creado.
     */
    @Query(
        value = """
            SELECT *
            FROM ordenes_compra
            WHERE id = (
                SELECT MAX(id)
                FROM ordenes_compra
            )
            """,
        nativeQuery = true
    )
    Collection<Orden_compra> getLast();
    
    
     /**
     * RFC6: Obtiene los documentos de ingreso de productos a bodega de los últimos 30 días 
     * para una sucursal y bodega específicas.
     */
    @Query(
        value = "SELECT new uniandes.edu.co.proyecto.modelo.DocumentoIngresoDTO(s.nombre AS nombreSucursal, " +
                "b.nombre AS nombreBodega, d.numero_documento AS numeroDocumento, d.fecha_ingreso AS fechaIngreso, " +
                "p.nombre AS nombreProveedor) " +
                "FROM sucursales s " +
                "JOIN bodegas b ON b.id_sucursal = s.id " +
                "JOIN documentos_ingreso d ON d.id_bodega = b.id " +
                "JOIN proveedores p ON p.id = d.id_proveedor " +
                "WHERE s.id = :idSucursal " +
                "AND b.id = :idBodega " +
                "AND d.fecha_ingreso >= CURRENT_DATE - 30",
        nativeQuery = false
    )
    List<uniandes.edu.co.proyecto.modelo.DocumentoIngresoDTO> obtenerDocumentosIngreso(@Param("idSucursal") Integer idSucursal,
                                                       @Param("idBodega") Integer idBodega);

    /**
     * RNF2: Leer los ids de los productos que se compran en una orden de compra
     *
     * @param id_orden_compra orden de compra que se quiere observas
     * @return Collection<Integer> Collecton con los productos encontrados asociados a la ordend de compra
     */
    @Query(
        value = """
            SELECT co.id_producto
            FROM compra co
            WHERE co.id_orden_compra = :id_orden_compra
            """,
        nativeQuery = true
    )
    Collection<Integer> getProductos(@Param("id_orden_compra")Integer id_orden_compra);

    /**
     * RFNF7 : Actualiza el estado de una orden de compra a 'entregada'.
     *
     * @param id Identificador de la orden de compra cuya estado va a cambiar.
     */
    @Modifying
    @Transactional
    @Query(
        value = """
            UPDATE ordenes_compra
            SET estado = 'entregada'
            WHERE id = :id
            """,
        nativeQuery = true
    )
    void actualizarEstadoAEntregado(@Param("id") Integer id);

}



