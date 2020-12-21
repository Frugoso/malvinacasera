package com.proyecto.malvina.servicio;
import com.proyecto.malvina.entidad.Foto;
import com.proyecto.malvina.error.ErrorService;
import com.proyecto.malvina.repositorio.FotoRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;





@Service
public class FotoService {

	@Autowired
	private FotoRepositorio fotoRepositorio;
	
	
	
	@Transactional
	public Foto guardar(MultipartFile archivo)throws ErrorService{
		
		if(archivo != null) {
			try {
				
				
				
				Foto foto = new Foto();
				
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				
				return fotoRepositorio.save(foto);
				
			}catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		
		return null;
	}
}