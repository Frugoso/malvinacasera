package com.proyecto.malvina.repositorio;
import com.proyecto.malvina.entidad.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface FotoRepositorio extends JpaRepository<Foto,String>{

}