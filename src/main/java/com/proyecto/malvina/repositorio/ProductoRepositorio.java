package com.proyecto.malvina.repositorio;
import com.proyecto.malvina.entidad.Producto;
import com.proyecto.malvina.enums.Tipo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto,String>{

	
	@Query("SELECT c FROM Producto c WHERE c.nombre =:nombre")
	public Producto buscarPorNombre(@Param("nombre")String nombre);
        
        @Query("SELECT c FROM Producto c WHERE c.tipo LIKE :tipo")
	public List<Producto> buscarPorTipo(@Param("tipo") Tipo tipoo);
        
        @Query("SELECT c FROM Producto c WHERE c.tipo NOT LIKE 'DESHABILITADOS' GROUP BY c.tipo")
	public List<Producto> buscarPrimeroTipo();
        
        @Query("SELECT c FROM Producto c WHERE c.tipo NOT LIKE 'DESHABILITADOS'")
	public List<Producto> buscarHabilitados();
	
}