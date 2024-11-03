package uniandes.edu.co.proyecto.repositorio;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /**
     * RF6.1 : Crea un producto.
     * SQL   : Inserta un nuevo producto en la tabla 'productos' con un ID
     *         generado automáticamente y valores específicos para nombre,
     *         expiración, código de barras, volumen, peso y categoría.
     *
     * @param nombre             Nombre del producto.
     * @param fecha_expiracion   Fecha de expiración en formato YYYY-MM-DD (puede ser null).
     * @param codigo_barras      Número entero que representa el código de barras.
     * @param volumen            Volumen del producto en metros cúbicos.
     * @param peso               Peso del producto en gramos.
     * @param id_tipo_categoria  Identificador de la categoría del producto.
     */
    @Modifying
    @Transactional
    @Query(
        value = """
            INSERT INTO productos (
                nombre,
                fecha_expiracion,
                codigo_barras,
                volumen,
                peso,
                id_tipo_categoria
            )
            VALUES (
                :nombre,
                :fecha_expiracion,
                :codigo_barras,
                :volumen,
                :peso,
                :id_tipo_categoria
            )
            """,
        nativeQuery = true
    )    
    void insertarProducto(@Param("nombre") String nombre, 
                          @Param("fecha_expiracion") Date fecha_expiracion, 
                          @Param("codigo_barras") String codigo_barras, 
                          @Param("volumen") Float volumen, 
                          @Param("peso") Float peso, 
                          @Param("id_tipo_categoria") Integer id_tipo_categoria);

    /**
     * RF6.2 : Lee un producto por ID o nombre.
     * SQL   : Recupera el producto de la tabla 'productos' cuyo ID o
     *         nombre coincide con los valores especificados.
     *
     * @param id     Identificador del producto.
     * @param nombre Nombre del producto.
     * @return Collection de el/los productos encontrados.
     */
    @Query(
        value = """
            SELECT *
            FROM productos b
            WHERE id = :id
               OR b.nombre = :nombre
            """,
        nativeQuery = true
    )    
    Collection<Producto> darProductoPorIdONombre(@Param("id") Integer id, 
                                                 @Param("nombre") String nombre);

    /**
     * RF6.3 : Actualiza un producto.
     * SQL   : Actualiza los detalles del producto en la tabla 'productos'
     *         para el registro cuyo ID coincide con el valor especificado.
     *
     * @param id                  Identificador del producto.
     * @param nombre              Nombre actualizado.
     * @param fecha_expiracion    Fecha de expiración actualizada en formato YYYY-MM-DD.
     * @param codigo_barras       Número actualizado.
     * @param volumen             Valor actualizado.
     * @param peso                Valor actualizado.
     * @param id_tipo_categoria   Identificador de la categoría actualizado.
     */
    @Modifying
    @Transactional
    @Query(
        value = """
            UPDATE productos
            SET nombre = :nombre,
                fecha_expiracion = :fecha_expiracion,
                codigo_barras = :codigo_barras,
                volumen = :volumen,
                peso = :peso,
                id_tipo_categoria = :id_tipo_categoria
            WHERE id = :id
            """,
        nativeQuery = true
    )
    void actualizarProducto(@Param("id") Integer id,
                            @Param("nombre") String nombre,
                            @Param("fecha_expiracion") Date fecha_expiracion,
                            @Param("codigo_barras") String codigo_barras,
                            @Param("volumen") Float volumen,
                            @Param("peso") Float peso,
                            @Param("id_tipo_categoria") Integer id_tipo_categoria);

    /**
     * Obtiene el último producto creado.
     *
     * @return Collection con un único elemento que será el último ID creado.
     */
    @Query(
        value = """
            SELECT *
            FROM productos
            WHERE id = (
                SELECT MAX(id)
                FROM productos
            )
            """,
        nativeQuery = true
    )
    Collection<Producto> getLast();

    /**
     * RFC1 : Muestra el índice de ocupación de cada una de las bodegas.
     *
     * @param lista_productos Lista con los IDs de los productos que se quieren consultar.
     * @return Collection con el resultado de la consulta.
     */
    @Query(
        value = """
            SELECT  co.id_bodega,
                    pr.id AS id_producto,
                    (SUM(co.cantidad) / co.capacidad) AS porcentaje_ocupacion
            FROM contiene co
            JOIN productos pr ON co.id_producto = pr.id
            WHERE pr.id IN (:lista_productos)
            GROUP BY co.id_bodega, pr.id, co.capacidad
            ORDER BY pr.id ASC, co.id_bodega ASC
            """,
        nativeQuery = true
    )
    Collection<Object[]> darPorcentajeOcupacion(@Param("lista_productos") List<Integer> lista_productos);

    /**
     * RFC2.1 : Muestra los productos que cumplen con estar en un rango de precios.
     *
     * @param minPrice Valor inferior del rango.
     * @param maxPrice Valor superior del rango.
     * @return Collection con los productos encontrados en el rango de precios.
     */
    @Query(
        value = """
            SELECT  co.id_bodega,
                    bo.nombre,
                    pr.*,
                    co.costo_promedio AS precio,
                    tc.nombre
            FROM contiene co
            JOIN productos pr ON co.id_producto = pr.id
            JOIN bodegas bo ON co.id_bodega = bo.id
            JOIN tipos_categoria tc ON pr.id_tipo_categoria = tc.id
            WHERE co.costo_promedio BETWEEN :minPrice AND :maxPrice
            ORDER BY pr.id ASC, co.costo_promedio DESC
            """,
        nativeQuery = true
    )
    Collection<Object[]> darProductosEnRangoDePrecios(  @Param("minPrice") Float minPrice,
                                                        @Param("maxPrice") Float maxPrice);

    /**
     * RFC2.2 : Muestra los productos con fecha de vencimiento posterior o inferior a una fecha dada.
     *
     * @param minFecha Rango inferior de la búsqueda.
     * @param maxFecha Rango superior de la búsqueda.
     * @return Collection con los productos encontrados.
     */
    @Query(
        value = """
            SELECT *
            FROM productos
            WHERE fecha_expiracion BETWEEN :minFecha AND :maxFecha
            """,
        nativeQuery = true
    )
    Collection<Producto> darProductosEnRangoDeFechaDeVencimiento(   @Param("minFecha") Date minFecha,
                                                                    @Param("maxFecha") Date maxFecha);

    /**
     * RFC2.3 : Muestra los productos pertenecientes a una sucursal dado su ID.
     *
     * @param id_sucursal Identificador de la sucursal de la que se quieren extraer los productos.
     * @return Collection con los datos de los productos pertenecientes a la sucursal.
     */
    @Query(
        value = """
            SELECT  pr.*,
                    bo.id_sucursal AS sucursal
            FROM productos pr
            JOIN contiene co ON pr.id = co.id_producto
            JOIN bodegas bo ON co.id_bodega = bo.id
            WHERE bo.id_sucursal = :id_sucursal
            """,
        nativeQuery = true
    )
    Collection<Object[]> darProductosPertenecientesASucursal(@Param("id_sucursal") Integer id_sucursal);

    /**
     * RFC2.4 : Muestra los productos pertenecientes a un tipo de categoría.
     *
     * @param id_tipo_categoria Identificador de la categoría a la que se le quieren extraer los productos.
     * @return Collection con los productos encontrados.
     */
    @Query(
        value = """
            SELECT *
            FROM productos pr
            WHERE pr.id_tipo_categoria = :id_tipo_categoria
            """,
        nativeQuery = true
    )
    Collection<Producto> darProductosPertenecientesATipoCategoria(@Param("id_tipo_categoria") Integer id_tipo_categoria);

    /**
     * RFC3 : Muestra el inventario de productos en bodega.
     *
     * @param id_sucursal Identificador de la sucursal.
     * @param id_bodega   Identificador de la bodega.
     * @return Collection con el ID, nombre, cantidad, cantidad mínima y costo promedio del producto en la bodega.
     */
    @Query(
        value = """
            SELECT
                    pr.id,
                    pr.nombre,
                    co.cantidad,
                    co.cantidad_minima,
                    co.costo_promedio,
                    bo.id,
                    bo.id_sucursal
            FROM productos pr
            JOIN contiene co ON pr.id = co.id_producto
            JOIN bodegas bo ON co.id_bodega = bo.id
            WHERE bo.id = :id_bodega
                AND bo.id_sucursal = :id_sucursal
            """,
        nativeQuery = true
    )
    Collection<Object[]> darInventarioDeBodega( @Param("id_sucursal") Integer id_sucursal,
                                                @Param("id_bodega") Integer id_bodega);

    /**
     * RFC5 : Muestra todos los productos que requieren una orden de compra.
     *
     * @return Collection de productos y demás datos.
     */
    @Query(
        value = """
            SELECT
                    pr.id,
                    pr.nombre AS producto,
                    bo.id AS bodega,
                    bo.id_sucursal AS sucursal,
                    co.cantidad,
                    co.cantidad_minima,
                    ofr.id_proveedor AS proveedor
            FROM productos pr
            JOIN contiene co ON pr.id = co.id_producto
            JOIN bodegas bo ON co.id_bodega = bo.id
            JOIN ofrece ofr ON pr.id = ofr.id_producto
            WHERE co.cantidad < co.cantidad_minima
            ORDER BY pr.id ASC
            """,
        nativeQuery = true
    )
    Collection<Object[]> darProductosQueRequierenOrdenCompra();

}
