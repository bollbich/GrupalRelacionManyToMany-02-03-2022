package com.formacionsprongboot.apirest.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.formacionsprongboot.apirest.entity.Cliente;
import com.formacionsprongboot.apirest.service.ClienteService;


	@RestController
	@RequestMapping("/api")
public class ClienteController {
	
	@Autowired
	private ClienteService servicio;
	
	@GetMapping({"/Clientes", "/todos"})
	public List<Cliente> index()
	{
		return servicio.ListarTodosClientes();
	}
	
	@GetMapping("/Cliente/buscarCliente/{id}")
	public ResponseEntity<?> FinClienteById(@PathVariable Long id)
	{
		Cliente cliente = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			cliente = servicio.FinById(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
		
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(cliente == null)
		{
			response.put("mensaje", "El ID de cliente ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		
		}
		else
		{
			return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
		}		
	}
	
	
	@PostMapping("/Cliente/guardarCliente")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> SaveCliente(@RequestBody Cliente cliente)
	{		
		Map<String, Object> response = new HashMap<>();
		
		try {
			servicio.save(cliente);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la insert a la base de datos");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
		
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "¡El cliente ha sido creado con exito!");
		response.put("Cliente",cliente);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	
	@PutMapping("/Cliente/updateCliente/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Map<String, Object>> UpdateCliente(@RequestBody Cliente cliente, @PathVariable Long id)
	{
		Map<String, Object> response = new HashMap<>();
		
		ResponseEntity<Map<String, Object>> resultado = null;
		
		Cliente clienteUpdate = null;	
		
		clienteUpdate = servicio.FinById(id);
		
		try {
			clienteUpdate.setCodigo(cliente.getCodigo());
			clienteUpdate.setTipo(cliente.getTipo());
			clienteUpdate.setCantidad(cliente.getCantidad());
			clienteUpdate.setPrecio(cliente.getPrecio());
			clienteUpdate.setMarca(cliente.getMarca());
			clienteUpdate.setFecha_ingreso(cliente.getFecha_ingreso());
			clienteUpdate.setDescripcion(cliente.getDescripcion());
				
				servicio.save(clienteUpdate);				
		}
		catch (NullPointerException f) {
					
			response.put("mensaje", "Error el cliente no existe en la base de datos");
			response.put("error", f.getMessage());
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
				
		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al realizar la update a la base de datos");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);						
		}
		
		
		if(clienteUpdate==null)
		{
			response.put("mensaje", "El ID de cliente ".concat(id.toString()).concat(" no existe en la base de datos"));
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		else
		{
			response.put("mensaje", "¡El cliente ha sido actualizado con exito!");
			response.put("Cliente",clienteUpdate);
			resultado = new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		}		
		return resultado;
	}
	
	
	@DeleteMapping("/Cliente/deleteCliente/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Map<String, Object>> DeleteCliente(@PathVariable Long id)
	{		
		
		Map<String, Object> response = new HashMap<>();
		
		Cliente cliente = null;
		
		ResponseEntity<Map<String, Object>> resultado = null;
		
		try {
			cliente = servicio.FinById(id);
			
			String nombreFotoAnterior = cliente.getImagen();
			
			if(nombreFotoAnterior!= null && nombreFotoAnterior.length()>0)
			{
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				
				if(archivoFotoAnterior.exists()&& archivoFotoAnterior.canRead()){
					archivoFotoAnterior.delete();
				}
			}
			
			 servicio.Delete(id);
			 
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar el delete a la base de datos");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
		
			resultado =  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cliente !=null)
		{
			response.put("mensaje", "¡El cliente ha sido eliminado con exito!");
			response.put("Cliente",cliente);
			
			resultado = new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		}				
		return resultado;				
	}

	
	@PostMapping("/Cliente/uploadImagen")
	public ResponseEntity<Map<String,Object>> UploadClienteImagen(@RequestParam("archivo")MultipartFile archivo, @RequestParam("id")Long id)
	{
		ResponseEntity<Map<String, Object>> resultado = null;
		
		Map<String, Object> response = new HashMap<>();
		
		Cliente clienteImageUpload = null;
		
		clienteImageUpload = servicio.FinById(id);
		
		Path rutaArchivo = null;
		
		try {
			
			if(!archivo.isEmpty())
			{
				//String nombreArchivo = id + "-" +archivo.getOriginalFilename().replace(" ", "");
				
				String nombreArchivo = UUID.randomUUID().toString() + "-" +archivo.getOriginalFilename().replace(" ", "");
				
				rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			}
			clienteImageUpload.setImagen(rutaArchivo.toString());
			
			String nombreFotoAnterior = clienteImageUpload.getImagen();
			
			if(nombreFotoAnterior!= null && nombreFotoAnterior.length()>0)
			{
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				
				if(archivoFotoAnterior.exists()&& archivoFotoAnterior.canRead()){
					archivoFotoAnterior.delete();
				}
			}
			
			Files.copy(archivo.getInputStream(), rutaArchivo);
				
			servicio.save(clienteImageUpload);				
		}
		catch (NullPointerException f) {
					
			response.put("mensaje", "Error el cliente no existe en la base de datos");
			response.put("error", f.getMessage());
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
				
		} 
		catch (DataAccessException e) {
			
			response.put("mensaje", "Error al realizar el upload a la base de datos");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);						
		} 
		catch (IOException e) {
			
			response.put("mensaje", "Error al subir la imagen");
			response.put("error", e.getMessage());
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		catch (MaxUploadSizeExceededException e) {
			
			response.put("mensaje", "Error al subir la imagen, archivo demasiado grande");
			response.put("error", e.getMessage());
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		
		if(clienteImageUpload==null)
		{
			response.put("mensaje", "El ID de cliente ".concat(id.toString()).concat(" no existe en la base de datos"));
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		else
		{
			response.put("mensaje", "¡El cliente ha sido actualizado con exito!");
			response.put("Cliente",clienteImageUpload);
			resultado = new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		}		
		return resultado;
	}

	@GetMapping("/cliente/verImagen/{nombreImagen:.+}")
	public ResponseEntity<Resource> VerImagen(@PathVariable String nombreImagen){
		
		Path rutaImagen = Paths.get("uploads").resolve(nombreImagen).toAbsolutePath();
		
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaImagen.toUri());
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		
		if(!recurso.exists()&& !recurso.isReadable())
		{
			throw new RuntimeException("Error no se puede cargar la imagen "+nombreImagen);
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\" "+recurso.getFilename()+"\"");
		
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
		
	}
}
