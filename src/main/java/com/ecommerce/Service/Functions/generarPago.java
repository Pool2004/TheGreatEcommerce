package com.ecommerce.Service.Functions;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenModel;

import java.util.List;

public class generarPago {

    public Double generarPago(Integer precioArticulo, Integer cantidadArticulo){

        Double totalPagar = 0.0;

        totalPagar += precioArticulo * cantidadArticulo;

        return totalPagar;

    }


    public String generarConstancia(OrdenModel orden, List<ArticuloModel> articulos){

        String textoRespuesta = "";


        Integer idOrden = orden.getIdOrden();
        String fechaOrden = orden.getFecha();
        Double valorTotal = orden.getValorTotal();
        String direccionEnvio = orden.getDireccion();

        Integer idDepartamento = orden.getIdDepartamento().getIdDepartamento();
        String tipoEntrega = orden.getTipoEntrega();


        Integer idUsuario = orden.getIdUsuario().getIdUsuario();
        String estadoOrden = orden.getEstado().name();









        return textoRespuesta;
    }





}
