package uniandes.edu.co.proyecto.Services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.repositorio.DocumentoRepository;

@Service
public class ConsultarDocumentosService {

    @Autowired
    private DocumentoRepository documentoRepository;

    /**
     * RFC6: Consultar los documentos (Serializable)
     *
     * @return ResponseEntity<?> Resultado de la transaccion
     * @throws Exception
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public ResponseEntity<?> consultarDocumentos_serializable(Integer id_bodega, Integer id_sucusal) throws Exception{
        documentoRepository.start();
        Thread.sleep(20000);
        Collection<Object[]> response = documentoRepository.getAllPorBodegaYSucursal(   id_bodega,
                                                                                        id_sucusal);
        return ResponseEntity.ok(response);
    }

    /**
     * RFC7: Consultar los documentos (Read Committed)
     *
     * @return ResponseEntity<?> Resultado de la transaccion
     * @throws Exception
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public ResponseEntity<?> consultarDocumentos_read_committed(Integer id_bodega, Integer id_sucusal) throws Exception{
        documentoRepository.start();
        Thread.sleep(20000);
        Collection<Object[]> response = documentoRepository.getAllPorBodegaYSucursal(   id_bodega,
                                                                                        id_sucusal);
        return ResponseEntity.ok(response);
    }

}
