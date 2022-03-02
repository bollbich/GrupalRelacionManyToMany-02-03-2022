package com.formacionsprongboot.apirest.productos.controller;

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

import com.formacionsprongboot.apirest.productos.entity.Producto;
import com.formacionsprongboot.apirest.productos.service.ProductoService;


@RestController
@RequestMapping("/api")
public class ProductoController {
	
	@Autowired
	private ProductoService servicio;
	
	@GetMapping({"/Productos", "/todos"})
	public List<Producto> index()
	{
		return servicio.ListarTodosProductos();
	}
	
	@GetMapping("/Producto/buscarProducto/{id}")
	public ResponseEntity<?> FinProductoById(@PathVariable Long id)
	{
		Producto producto = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			producto = servicio.FinById(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
		
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(producto == null)
		{
			response.put("mensaje", "El ID de producto ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		
		}
		else
		{
			return new ResponseEntity<Producto>(producto,HttpStatus.OK);
		}		
	}
	
	
	@PostMapping("/Producto/guardarProducto")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> SaveProducto(@RequestBody Producto producto)
	{		
		Map<String, Object> response = new HashMap<>();
		
		try {
			servicio.save(producto);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la insert a la base de datos");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
		
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "¡El producto ha sido creado con exito!");
		response.put("Producto",producto);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	
	@PutMapping("/Producto/updateProducto/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Map<String, Object>> UpdateProducto(@RequestBody Producto producto, @PathVariable Long id)
	{
		Map<String, Object> response = new HashMap<>();
		
		ResponseEntity<Map<String, Object>> resultado = null;
		
		Producto productoUpdate = null;	
		
		productoUpdate = servicio.FinById(id);
		
		try {
				productoUpdate.setCodigo(producto.getCodigo());
				productoUpdate.setTipo(producto.getTipo());
				productoUpdate.setCantidad(producto.getCantidad());
				productoUpdate.setPrecio(producto.getPrecio());
				productoUpdate.setMarca(producto.getMarca());
				productoUpdate.setFecha_ingreso(producto.getFecha_ingreso());
				productoUpdate.setDescripcion(producto.getDescripcion());
				
				servicio.save(productoUpdate);				
		}
		catch (NullPointerException f) {
					
			response.put("mensaje", "Error el producto no existe en la base de datos");
			response.put("error", f.getMessage());
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
				
		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al realizar la update a la base de datos");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);						
		}
		
		
		if(productoUpdate==null)
		{
			response.put("mensaje", "El ID de producto ".concat(id.toString()).concat(" no existe en la base de datos"));
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		else
		{
			response.put("mensaje", "¡El producto ha sido actualizado con exito!");
			response.put("Producto",productoUpdate);
			resultado = new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		}		
		return resultado;
	}
	
	
	@DeleteMapping("/Producto/deleteProducto/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Map<String, Object>> DeleteProducto(@PathVariable Long id)
	{		
		
		Map<String, Object> response = new HashMap<>();
		
		Producto producto = null;
		
		ResponseEntity<Map<String, Object>> resultado = null;
		
		try {
			producto = servicio.FinById(id);
			
			String nombreFotoAnterior = producto.getImagen();
			
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
		
		if(producto !=null)
		{
			response.put("mensaje", "¡El producto ha sido eliminado con exito!");
			response.put("Producto",producto);
			
			resultado = new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		}				
		return resultado;				
	}

	
	@PostMapping("/Producto/uploadImagen")
	public ResponseEntity<Map<String,Object>> UploadProductoImagen(@RequestParam("archivo")MultipartFile archivo, @RequestParam("id")Long id)
	{
		ResponseEntity<Map<String, Object>> resultado = null;
		
		Map<String, Object> response = new HashMap<>();
		
		Producto productoImageUpload = null;
		
		productoImageUpload = servicio.FinById(id);
		
		Path rutaArchivo = null;
		
		try {
			
			if(!archivo.isEmpty())
			{
				//String nombreArchivo = id + "-" +archivo.getOriginalFilename().replace(" ", "");
				
				String nombreArchivo = UUID.randomUUID().toString() + "-" +archivo.getOriginalFilename().replace(" ", "");
				
				rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			}
			productoImageUpload.setImagen(rutaArchivo.toString());
			
			String nombreFotoAnterior = productoImageUpload.getImagen();
			
			if(nombreFotoAnterior!= null && nombreFotoAnterior.length()>0)
			{
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				
				if(archivoFotoAnterior.exists()&& archivoFotoAnterior.canRead()){
					archivoFotoAnterior.delete();
				}
			}
			
			Files.copy(archivo.getInputStream(), rutaArchivo);
				
			servicio.save(productoImageUpload);				
		}
		catch (NullPointerException f) {
					
			response.put("mensaje", "Error el producto no existe en la base de datos");
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
		
		
		if(productoImageUpload==null)
		{
			response.put("mensaje", "El ID de producto ".concat(id.toString()).concat(" no existe en la base de datos"));
			resultado = new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		else
		{
			response.put("mensaje", "¡El producto ha sido actualizado con exito!");
			response.put("Producto",productoImageUpload);
			resultado = new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		}		
		return resultado;
	}

	@GetMapping("/Producto/verImagen/{nombreImagen:.+}")
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
