package com.proyecto.malvina.repositorio;
import com.proyecto.malvina.entidad.Pedido;
import com.proyecto.malvina.entidad.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, String> {

    @Query("SELECT p FROM Pedido p WHERE p.estado LIKE CONFIRMADO")
    public List<Pedido> pendientes();

    @Query("SELECT p FROM Pedido p, IN(p.usuario) u WHERE p.estado LIKE 'CARRITO' AND u.id LIKE :id")
    public Pedido carrito(@Param("id") String id);

    @Query("SELECT u.nombre, COUNT(*) FROM Pedido p, IN(p.productos) u WHERE p.estado LIKE 'CONFIRMADO' ORDER BY COUNT(*)")
    public List<String[]> pedidoDeProductos();

    @Query("SELECT p FROM Pedido p WHERE :producto member of p.productos")
    public List<Pedido> pedidosPorProducto(@Param("producto") Producto producto);
    
}