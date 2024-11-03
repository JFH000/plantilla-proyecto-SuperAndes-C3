package uniandes.edu.co.proyecto.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.MS;
import uniandes.edu.co.proyecto.Services.ConsultarDocumentosService;
import uniandes.edu.co.proyecto.repositorio.DocumentoRepository;


@RestController
public class DocumentoController {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private ConsultarDocumentosService consultarDocumentosService;

    /**
     * RF6 - RF7: Controller que ejecuta la búsqueda de docuemntos dependiendo del nivel de aislamiento
     *
     * @param isolation_level Nivel de aislamiento con el que se quiere hacer la transaccion
     * @param id_bodega       Identificador de la bodega
     * @param id_sucursal     Identificador de la sucursal
     * @return ResponseEntity<?> Resultado de la transacción
     */
    @GetMapping("/documentos")
    public ResponseEntity<?> getMethodName( @RequestParam(required=false) String isolation_level,
                                            @RequestParam(required=false) Integer id_bodega,
                                            @RequestParam(required=false) Integer id_sucursal) {
        try {
            if(id_bodega == null || id_sucursal == null || isolation_level == null)
                throw new Exception("Parametros de entrada incompletos \nid_bodega: "+id_bodega+"\nid_sucursal: "+id_sucursal+"\nisolation_level: "+isolation_level);
            if(isolation_level.equals("SERIALIZABLE")){
                return consultarDocumentosService.consultarDocumentos_serializable( id_bodega,
                                                                                    id_sucursal);
            }else if(isolation_level.equals("READ_COMMITTED")){
                return consultarDocumentosService.consultarDocumentos_read_committed(   id_bodega,
                                                                                        id_sucursal);
            }else{
                throw new Exception("isolation level incorrecto o no implementado");
            }
        } catch (Exception e) {
            Map<String,Object> response = MS.response("not_ok","get",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
