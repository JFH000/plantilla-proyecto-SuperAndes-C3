package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Contiene;

public interface ContieneRepository extends JpaRepository<Contiene, Integer>{

    /**
     * RNF3: Leer una fila de la tabla contiene dado su PK
     *
     * @param id_bodega    bodega
     * @param id_producto  producto
     * @return Collection<Contiene> collection con las filas encontradas
     */
    @Modifying
    @Transactional
    @Query(
    value = """
        SELECT *
        FROM contiene
        WHERE id_bodega = :id_bodega
            AND id_producto = :id_producto
        """,
    nativeQuery = true
    )
    Collection<Contiene> getPorPK(  @Param("id_bodega")Integer id_bodega,
                                    @Param("id_producto")Integer id_producto);

    /**
     * RNF4: Crear una fila de la tabla contiene
     *
     * @param id_bodega        identificador de la bodega
     * @param id_producto      identificador del producto
     * @param id_orden_compra  identificador de la orden de compra
     */
    @Modifying
    @Transactional
    @Query(
    value = """
        INSERT INTO contiene (
            id_bodega,
            id_producto,
            cantidad,
            capacidad,
            costo_promedio,
            cantidad_minima
        )
        VALUES (
            :id_bodega,
            :id_producto,
            (SELECT cantidad
                FROM compra
                WHERE id_orden_compra = :id_orden_compra
                    AND id_producto = :id_producto),
            3 * (SELECT cantidad
                    FROM compra
                    WHERE id_orden_compra = :id_orden_compra
                        AND id_producto = :id_producto),
            (SELECT precio_unitario
                FROM compra
                WHERE id_orden_compra = :id_orden_compra
                    AND id_producto = :id_producto),
            1
        )
        """,
    nativeQuery = true
    )
    void createFila(@Param("id_bodega")Integer id_bodega,
                    @Param("id_producto")Integer id_producto,
                    @Param("id_orden_compra")Integer id_orden_compra);

    /**
     * RNF5: actualiza una fila de la tabla contiene
     *
     * @param id_bodega        identificador de la bodega
     * @param id_producto      identificador del producto
     * @param id_orden_compra  identificador de la orden de compra
     */
    @Modifying
    @Transactional
    @Query(
        value = """
            UPDATE contiene
            SET costo_promedio = (
                    (costo_promedio * cantidad) +
                    (
                        (SELECT precio_unitario
                        FROM compra
                        WHERE id_orden_compra = :id_orden_compra
                        AND id_producto = :id_producto) *
                        (SELECT cantidad
                        FROM compra
                        WHERE id_orden_compra = :id_orden_compra
                        AND id_producto = :id_producto)
                    )
                ) / (
                    cantidad +
                    (SELECT cantidad
                    FROM compra
                    WHERE id_orden_compra = :id_orden_compra
                    AND id_producto = :id_producto)
                ),
                cantidad = cantidad +
                    (SELECT cantidad
                    FROM compra
                    WHERE id_orden_compra = :id_orden_compra
                    AND id_producto = :id_producto)
            WHERE id_bodega = :id_bodega
            AND id_producto = :id_producto
            """,
        nativeQuery = true
    )
    void actualizarFila(@Param("id_bodega")Integer id_bodega,
                        @Param("id_producto")Integer id_producto,
                        @Param("id_orden_compra")Integer id_orden_compra);
    
}
