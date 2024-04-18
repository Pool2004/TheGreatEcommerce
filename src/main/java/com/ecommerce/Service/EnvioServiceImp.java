package com.ecommerce.Service;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.DepartamentoModel;
import com.ecommerce.Model.EnvioModel;
import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Repository.IEnvioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;

@Service
@Primary

public class EnvioServiceImp implements IEnvioService{

    @Autowired
    IEnvioRepository envioRepository;

    private List<EnvioModel> enviosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        enviosExistentes= this.envioRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearEnvio(EnvioModel envio) {

        String textoRespuesta = "";

        String tipoEntrega = envio.getTipoEntrega();
        String direccion = envio.getDireccion();
        OrdenModel idOrden = envio.getIdOrden();
        DepartamentoModel idDepartmaneto = envio.getIdDepartamento();


        enviosExistentes = this.envioRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(enviosExistentes.isEmpty()){

            this.envioRepository.save(envio);

            textoRespuesta =  "El envio ha sido creado con éxito.";
            System.out.println("Anda entrando aca");

        } else {
            if (tipoEntrega == null || tipoEntrega.isBlank()) {
                textoRespuesta = "el tipo de entrega no puede ser vacio a nula";
            } else if (direccion == null || direccion.isBlank()) {
                textoRespuesta = "La direccion no puede estar vacia o ser nula";
            } else if (idOrden == null) {
                textoRespuesta = "El id de su orden no puede ser nula";
            } else if (idDepartmaneto == null) {
                textoRespuesta = "El id de su departamento no puede ser nulo";
            } else {
                this.envioRepository.save(envio);
                textoRespuesta = "El envio ha sido creado con éxito.";
            }
        }
        return textoRespuesta;
  }

    @Override
    public List<EnvioModel> listarEnvio() {
        return this.envioRepository.findAll();
    }

    @Override
    public Optional<EnvioModel> obtenerEnvioPorId(Integer idEnvio) {
        return this.envioRepository.findById(idEnvio);
    }

    @Override
    public String actualizarEnvioPorId(EnvioModel envio, Integer idEnvio) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.
        try {
            Optional<EnvioModel> envioEncontrado = this.envioRepository.findById(idEnvio);

            if (envioEncontrado.isPresent()) {

                EnvioModel envioActualizar = envioEncontrado.get();

                BeanUtils.copyProperties(envio, envioActualizar);

                this.envioRepository.save(envioActualizar);

                return "El envio con código: " + idEnvio + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "El envio con código: " + idEnvio + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
            }
        }catch(NullPointerException e){
            textoRespuesta = "Alguno de los valores son nulos, verifique los campos";
        }catch(UncheckedIOException e){
            textoRespuesta = "Se presento un error, inesperado. Verifique el JSON y los valores no puede ser nulos.";
        }catch(DataIntegrityViolationException e){
            textoRespuesta = "Un error en el JSON, verifique.";
        }

        return textoRespuesta;
    }
}
