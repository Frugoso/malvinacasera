package com.proyecto.malvina.servicio;

import com.proyecto.malvina.entidad.Foto;
import com.proyecto.malvina.entidad.Producto;
import com.proyecto.malvina.enums.Tipo;
import com.proyecto.malvina.error.ErrorService;
import com.proyecto.malvina.repositorio.FotoRepositorio;
import com.proyecto.malvina.repositorio.ProductoRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class ProductoService {

    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Autowired
    private FotoRepositorio fotoRepositorio;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private FotoService fotoService;

    public Producto buscarPorId(String id) {

        return productoRepositorio.getOne(id);
    }

    public List<Producto> findAll() {

        return productoRepositorio.findAll();

    }
    
    public Producto buscarPorNombre(String nombre){
        return productoRepositorio.buscarPorNombre(nombre);
    }
    
    public List<Producto> buscarPorTipo(Tipo tipo){
        return productoRepositorio.buscarPorTipo(tipo);
    };

    @Transactional
    public List<Producto> listarProducto() throws ErrorService {

        List<Producto> respuesta = productoRepositorio.findAll();

        if (respuesta != null) {

            return respuesta;

        } else {
            throw new ErrorService("No se encontro productos");
        }

    }

    @Transactional
    public void registrarProducto(String nombre, Integer cantidad, Double precioVenta, String descripcion,
            MultipartFile archivo, Tipo tipo) throws ErrorService {

        validar(nombre, cantidad, precioVenta, descripcion, tipo);

        Producto producto = new Producto();

        producto.setNombre(nombre);
        producto.setPrecioVenta(precioVenta);
        producto.setDescripcion(descripcion);
        if (archivo != null) {
            Foto foto = fotoService.guardar(archivo);
            producto.setFoto(foto);
        }
        producto.setTipo(tipo);

        productoRepositorio.save(producto);

    }

    @Transactional
    public void modificarProducto(String id, String nombre, Integer cantidad, Double precioVenta, String descripcion,
            MultipartFile archivo, Tipo tipo) throws ErrorService {

        validar(nombre, cantidad, precioVenta, descripcion, tipo);

        Optional<Producto> respuesta = productoRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Producto producto = respuesta.get();

            producto.setNombre(nombre);
        
            producto.setPrecioVenta(precioVenta);
            producto.setDescripcion(descripcion);
            if (archivo != null) {
                Foto foto = fotoService.guardar(archivo);
                producto.setFoto(foto);
            }
            producto.setTipo(tipo);

            productoRepositorio.save(producto);

        } else {
            throw new ErrorService("No se ha encontrado el producto solicitado");
        }

    }

    
    @Transactional
    public void bajaProducto(String id) throws ErrorService {

        Optional<Producto> respuesta = productoRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Producto producto = respuesta.get();

            fotoRepositorio.delete(producto.getFoto());
            productoRepositorio.delete(producto);

        } else {
            throw new ErrorService("No se ha encontrado el producto solicitado");
        }
    }

    public List<Producto> listarProductosPorTipo(String tipo) {
        return productoRepositorio.buscarPorTipo(Tipo.valueOf(tipo));
    }

  

    public void validar(String nombre, Integer cantidad, Double precioVenta, String descripcion,
            Tipo tipo) throws ErrorService {

        if (nombre == null || nombre.isEmpty()) {

            throw new ErrorService("El nombre no puede ser nulo");
        }


        if (cantidad == null || cantidad < 0) {

            throw new ErrorService("La cantidad no puede ser nula");
        }

        if (precioVenta == null || precioVenta < 0) {

            throw new ErrorService("El precio de venta no puede ser nulo");
        }

        if (descripcion == null || descripcion.isEmpty()) {

            throw new ErrorService("La descripción no puede ser nula");
        }

        if (tipo == null) {

            throw new ErrorService("Debe indicar a que rubro pertenece el producto");
        }
    }

}