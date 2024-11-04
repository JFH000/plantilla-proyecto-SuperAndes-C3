package uniandes.edu.co.proyecto.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.MS;
import uniandes.edu.co.proyecto.modelo.Tipo_categoria;
import uniandes.edu.co.proyecto.repositorio.Tipo_categoriaRepository;

@RestController
public class Tipos_categoriaController {

    @Autowired
    private Tipo_categoriaRepository tipo_categoriaRepository;

    /**
     * Extrae las categorías de la tabla tipos_categoria.
     *
     * @return Collection<Tipo_categoria> de las categorías encontradas.
     */
    @GetMapping("/tipos_categoria")
    public Collection<Tipo_categoria> darTipos_categoria() {
        return tipo_categoriaRepository.findAll();
    }

    /**
     * Extrae una categoría dada su id o nombre.
     *
     * @param id     Identificador de la categoría que se quiere encontrar.
     * @param nombre Nombre de la categoría que se quiere encontrar.
     * @return ResponseEntity<?> Resultado de la transacción.
     */
    @GetMapping("/tipos_categoria/consulta")
    public ResponseEntity<?> darTipo_categoria( @RequestParam(required = false) Integer id,
                                                @RequestParam(required = false) String nombre) {
        try {
            if (id == null && nombre == null) {
                throw new Exception(MS.SIN_PARAMETROS_EXCEPTION);
            } else {
                Collection<Tipo_categoria> tipos = tipo_categoriaRepository.darTipo_categoriaPorIdONombre(  id,
                                                                                                            nombre);
                if (tipos.isEmpty()) {
                    throw new Exception(MS.SIN_RESULTADOS_EXCEPTION);
                }
                Map<String, Object> response = new HashMap<>();
                response.put("tipos", tipos);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = MS.response("not ok", "get", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Añade una categoría a la tabla tipos_categoria dada su información.
     *
     * @param tipo_categoria Información de la categoría que se quiere crear.
     * @return ResponseEntity<Map<String, Object>> Resultado de la transacción.
     */
    @CrossOrigin(origins = "null")
    @PostMapping("/tipos_categoria/new/save")
    public ResponseEntity<Map<String, Object>> tipo_categoriaGuardar(@RequestBody Tipo_categoria tipo_categoria) {
        tipo_categoriaRepository.insertarTipo_categoria(tipo_categoria.getNombre(),
                                                        tipo_categoria.getDescripcion(),
                                                        tipo_categoria.getCaracteristicas());
        tipo_categoria.setId(getLast().getId());
        Map<String, Object> response = MS.response("ok", "create", tipo_categoria);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Devuelve la última instancia creada.
     *
     * @return Tipo_categoria Última fila añadida.
     */
    public Tipo_categoria getLast() {
        return tipo_categoriaRepository.getLast().iterator().next();
    }

}
