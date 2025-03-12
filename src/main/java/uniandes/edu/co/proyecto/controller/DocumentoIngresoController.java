package uniandes.edu.co.proyecto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uniandes.edu.co.proyecto.modelo.DocumentoIngresoDTO;
import uniandes.edu.co.proyecto.repositorio.Orden_compraRepository;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoIngresoController {

    @Autowired
    private Orden_compraRepository orden_compraRepository;

    /**
     * RFC6: Consulta de documentos de ingreso de productos a bodega con transacción SERIALIZABLE.
     */
    @GetMapping("/consulta-ingreso")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<?> consultarDocumentosIngreso(@RequestParam Integer idSucursal, @RequestParam Integer idBodega) {
        try {
            // Temporizador de 30 segundos para pruebas de concurrencia
            Thread.sleep(30000);

            // Ejecutar la consulta de documentos de ingreso
            List<DocumentoIngresoDTO> documentos = orden_compraRepository.obtenerDocumentosIngreso(idSucursal, idBodega);

            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            // En caso de error, enviar mensaje de error y hacer rollback
            return ResponseEntity.status(500).body("No se pudo realizar la consulta: " + e.getMessage());
        }
    }
}