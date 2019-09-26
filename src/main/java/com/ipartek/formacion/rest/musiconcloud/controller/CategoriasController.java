package com.ipartek.formacion.rest.musiconcloud.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.formacion.rest.musiconcloud.domain.Categoria;
import com.ipartek.formacion.rest.musiconcloud.domain.ResponseMensaje;
import com.ipartek.formacion.rest.musiconcloud.model.CategoriaRepository;

//definir que ips pueden entrar y que metodos permitir
//@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET})
//controlador de spring para un servicio REST
@RestController
public class CategoriasController {
	
	@Autowired									//nuestro antiguo .getInstance()
	CategoriaRepository categoriaRepository; 	//nuestro antiguo DAO
	
	//este metodo escucha en:
	@RequestMapping(value = { "/categoria/" }, method = RequestMethod.GET)
	public ResponseEntity<Object> listar(){
		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			ArrayList<Categoria> lista = (ArrayList<Categoria>) categoriaRepository.findAll();
			if (!lista.isEmpty()) {
				response = new ResponseEntity<Object>(lista, HttpStatus.OK);
			} else {
				response = new ResponseEntity<Object>(lista, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;	
	}

	@RequestMapping(value = { "/categoria/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> detalle(@PathVariable int id){ //sustituye al request.getParameter()
		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			Optional<Categoria> categoria = categoriaRepository.findById(id);
			if (categoria.isPresent()) {
				response = new ResponseEntity<Object>(categoria, HttpStatus.OK);
			} else {
				response = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;	
	}
	
	@RequestMapping(value = { "/categoria/" }, method = RequestMethod.POST)
	public ResponseEntity<Object> crear(@RequestBody Categoria categoria){ 
		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			categoriaRepository.save(categoria);
			response = new ResponseEntity<Object>(categoria, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			ResponseMensaje responseBody = new ResponseMensaje("Longitud Nombre min 3 max 50"); 
			response = new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);	
		} catch (Exception e) {
			e.printStackTrace();
			ResponseMensaje responseBody = new ResponseMensaje("Existe el nombre de la Categoria"); 
			response = new ResponseEntity<Object>(responseBody, HttpStatus.CONFLICT);
		}
		return response;	
	}
	
}
