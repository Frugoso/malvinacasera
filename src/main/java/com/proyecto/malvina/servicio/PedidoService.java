package com.proyecto.malvina.servicio;
import com.proyecto.malvina.entidad.Pedido;
import com.proyecto.malvina.entidad.Producto;
import com.proyecto.malvina.entidad.Usuario;
import com.proyecto.malvina.enums.Estado;
import com.proyecto.malvina.error.ErrorService;
import com.proyecto.malvina.repositorio.PedidoRepositorio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private ProductoService productoService;

    

    public List<Pedido> pendientes() {
        return pedidoRepositorio.pendientes();
    }

    public Pedido buscarPorId(String id) {

        return pedidoRepositorio.getOne(id);
    }

    public List<Pedido> findAll() {

        return pedidoRepositorio.findAll();

    }

    @Transactional
    public void registrarPedido(List<Producto> productos, List<Integer> cantidades, Date fecha, Estado estado) throws ErrorService {

        validar(productos, cantidades, estado);

        Pedido pedido = new Pedido();
        pedido.setCantidad(cantidades);
        pedido.setProductos(productos);
        pedido.setFecha(new Date());
        pedido.setPrecioTotal(this.calcularTotal(productos, cantidades));
        pedido.setEstado(estado);

        pedidoRepositorio.save(pedido);

    }

    public Double calcularTotal(List<Producto> productos, List<Integer> cantidad) {
        double total = 0;

        for (int i = 0; i < productos.size(); i++) {
            total += productos.get(i).getPrecioVenta() * cantidad.get(i);

        }
        return total;
    }


    public Date convertirStringADate(String fecha) {

        try {
            DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
            Date convertido = fechaHora.parse(fecha);
            return convertido;
        } catch (java.text.ParseException ex) {
            Logger.getLogger(PedidoService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Transactional
    public void modificarPedido(String id, List<Producto> productos, List<Integer> cantidades, Estado estado) throws ErrorService {

        validar(productos, cantidades, estado);

        Optional<Pedido> respuesta = pedidoRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Pedido pedido = respuesta.get();
            pedido.setCantidad(cantidades);
            pedido.setProductos(productos);

            pedido.setPrecioTotal(this.calcularTotal(productos, cantidades));
            pedido.setEstado(estado);

            pedidoRepositorio.save(pedido);

        }
    }




   



    public Pedido carrito(String id) {
        return pedidoRepositorio.carrito(id);
    }

    @Transactional
    public void miCarrito(Usuario usuario, Producto producto, Integer cantidad) throws ErrorService {
        Pedido p = new Pedido();
        p.setUsuario(usuario);
        p.setEstado(Estado.CARRITO);
        p.setFecha(new Date());
        List<Producto> productos = new ArrayList<Producto>();
        List<Integer> cantidades = new ArrayList<Integer>();
        p.setProductos(productos);
        p.setCantidad(cantidades);
        this.agregar(p, producto, cantidad);
    }

    @Transactional
    public void agregar(Pedido pedido, Producto producto, Integer cantidad) throws ErrorService {
        //habría que verificar las cantidades antes de agregar
        
            boolean x = false;

            List<Producto> productos = pedido.getProductos();
            List<Integer> cantidades = pedido.getCantidad();

            if (!productos.isEmpty() && productos != null) {
                for (int i = 0; i < productos.size(); i++) {
                    if (productos.get(i).equals(producto)) {
                        x = true;
                        
                            cantidades.set(i, cantidad + cantidades.get(i));
                        
                        break;
                    }
                }
            }

            if (!x) {
                productos.add(producto);
                cantidades.add(cantidad);
            }

            pedido.setProductos(productos);
            pedido.setCantidad(cantidades);

            pedidoRepositorio.save(pedido);
         
    }

    @Transactional
    public void quitar(Pedido pedido, Producto producto) {

        List<Producto> productos = pedido.getProductos();
        List<Integer> cantidades = pedido.getCantidad();

        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).equals(producto)) {
                productos.remove(i);
                cantidades.remove(i);
                break;
            }
        }

        pedido.setProductos(productos);
        pedido.setCantidad(cantidades);

        pedidoRepositorio.save(pedido);

    }

    @Transactional
    public void confirmarCarrito(Pedido pedido, Double total) {

        pedido.setFecha(new Date());
        pedido.setPrecioTotal(total);
        pedido.setEstado(Estado.CONFIRMADO);
        pedidoRepositorio.save(pedido);

    }


    public void validar(List<Producto> productos, List<Integer> cantidades, Estado estado) throws ErrorService {

        if (productos == null || productos.isEmpty()) {

            throw new ErrorService("La lista de productos no puede estar vacía");
        }
        if (cantidades == null || cantidades.isEmpty()) {

            throw new ErrorService("La lista de cantidades no puede estar vacía");
        }

        if (estado == null) {

            throw new ErrorService("El estado del pedido no puede ser nulo");
        }

    }

    @Transactional
    public List<Pedido> pedidosPorProducto(Producto producto) {
        return (List<Pedido>) pedidoRepositorio.pedidosPorProducto(producto);
    }
}