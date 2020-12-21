package com.proyecto.malvina.controlador;

import com.proyecto.malvina.entidad.Pedido;
import com.proyecto.malvina.entidad.Producto;
import com.proyecto.malvina.entidad.Usuario;
import com.proyecto.malvina.enums.Estado;
import com.proyecto.malvina.servicio.PedidoService;
import com.proyecto.malvina.servicio.ProductoService;
import com.proyecto.malvina.servicio.UsuarioService;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;

@Controller
public class PedidoUsuarioController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private HttpSession session;

    
   
    @GetMapping("/compra")
    public String compra(ModelMap modelo) {

        Usuario u = (Usuario) session.getAttribute("usuariosession");
        Pedido pedido = pedidoService.carrito(u.getId());

        List<String[]> lista = new ArrayList();

        Double total = 0.0;

        for (int i = 0; i < pedido.getProductos().size(); i++) {

            String[] carrito = new String[4];

            carrito[0] = pedido.getProductos().get(i).getId();
            carrito[1] = pedido.getProductos().get(i).getNombre();
            carrito[2] = pedido.getCantidad().get(i).toString();

            Double precioFinal = pedido.getCantidad().get(i) * pedido.getProductos().get(i).getPrecioVenta();

            carrito[3] = precioFinal.toString();

            total += precioFinal;

            lista.add(carrito);

        }

        modelo.put("listacarrito", lista);
        modelo.put("total", total);

        Integer largoCarrito = 0;
      
        if (pedido != null) {
            largoCarrito = pedido.getProductos().size();
        }
        modelo.put("largocarrito", largoCarrito);
        
        return "compra.html";
    }

    @PostMapping("/quitar")
    public String quitar(@RequestParam String idUsuario, @RequestParam String id) {

        Pedido pedido = pedidoService.carrito(idUsuario);

        Producto producto = productoService.buscarPorId(id);

        pedidoService.quitar(pedido, producto);

        return "redirect:/compra";
    }

    @PostMapping("/confirmar")
    public String confirmar(@RequestParam String idUsuario, @RequestParam String total) {

        Pedido pedido = pedidoService.carrito(idUsuario);

        Double totalDouble = Double.parseDouble(total);

        pedidoService.confirmarCarrito(pedido, totalDouble);

        return "redirect:/inicio";
    }
//
//    @PreAuthorize("hasRole('ROLE_USUARIO')||hasRole('ROLE_ADMIN')")
//    @GetMapping("/miscompras")
//    public String miscompras(ModelMap modelo, @RequestParam(required = false) String estado) {
//
//        Usuario u = (Usuario) session.getAttribute("usuariosession");
//        
//        Pedido carrito = pedidoService.carrito(u.getId());
//        
//        List<Pedido> pedidos = pedidoService.mispedidos(u.getId());
//
//        Set<Estado> estados = EnumSet.allOf(Estado.class);
//        
//        if (estado != null) {
////            pedidos = pedidoService.listarPedidosPorEstado(u.getId(), Estado.valueOf(estado));
//        } else {
//            pedidos = pedidoService.listarPedidosPorEstado(u.getId(),Estado.CARRITO)
//        }
//        
//        modelo.put("estados", estados);
//
//        modelo.put("pedidos", pedidos);
//
//        Integer largoCarrito = 0;
//        
//        if (carrito != null) {
//            largoCarrito = carrito.getProductos().size();
//        }
//        modelo.put("largocarrito", largoCarrito);
//        
//        return "miscompras.html";
//    }
//    
  
}