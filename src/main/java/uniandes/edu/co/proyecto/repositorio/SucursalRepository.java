package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    /**
     * RF2 : Crea una nueva sucursal.
     * SQL : Inserta una nueva sucursal en la tabla 'sucursales'
     *        con un ID generado automáticamente y valores específicos
     *        para nombre, tamaño, teléfono, dirección y ciudad.
     *
     * @param nombre    Nombre de la sucursal.
     * @param tamanio   Tamaño de la sucursal en metros cuadrados.
     * @param telefono  Número de teléfono de la sucursal.
     * @param direccion Ubicación de la sucursal.
     * @param id_ciudad Identificador de la ciudad donde se encuentra la sucursal.
     */
    @Modifying
    @Transactional
    @Query(
        value = """
            INSERT INTO sucursales (
                nombre,
                tamanio,
                telefono,
                direccion,
                id_ciudad
            )
            VALUES (
                :nombre,
                :tamanio,
                :telefono,
                :direccion,
                :id_ciudad
            )
            """,
        nativeQuery = true
    )
    void insertarSucursal(  @Param("nombre") String nombre,
                            @Param("tamanio") String tamanio,
                            @Param("telefono") String telefono,
                            @Param("direccion") String direccion,
                            @Param("id_ciudad") Integer id_ciudad);

    /**
     * Obtiene la última sucursal creada.
     *
     * @return Collection con un único elemento que será el último ID creado.
     */
    @Query(
        value = """
            SELECT *
            FROM sucursales
            WHERE id = (
                SELECT MAX(id)
                FROM sucursales
            )
            """,
        nativeQuery = true
    )
    Collection<Sucursal> getLast();

    /**
     * RF4 : Muestra las sucursales en las que hay un producto dado su ID o nombre.
     *
     * @param id     Identificador del producto a buscar.
     * @param nombre Nombre del producto a buscar.
     * @return Collection con las sucursales encontradas.
     */
    @Query(
        value = """
            SELECT
                    su.*,
                    pr.id,
                    pr.nombre
            FROM sucursales su
            JOIN bodegas bo ON su.id = bo.id_sucursal
            JOIN contiene co ON bo.id = co.id_bodega
            JOIN productos pr ON co.id_producto = pr.id
            WHERE pr.id = :id OR pr.nombre = :nombre
            """,
        nativeQuery = true
    )
    Collection<Object[]> darSucursalesConProducto(  @Param("id") Integer id,
                                                    @Param("nombre") String nombre);

}
