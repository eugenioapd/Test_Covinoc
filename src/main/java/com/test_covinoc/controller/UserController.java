package com.test_covinoc.controller;

import com.test_covinoc.models.User;
import com.test_covinoc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/user/newUser")
    public ResponseEntity<?> save(@Valid @RequestBody User user, BindingResult result){

        User newUser = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(err -> "El Campo '" + err.getField() +"' "+ err.getDefaultMessage()).collect(Collectors.toList());
            response.put("Errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            newUser = userService.save(user);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el registro en la base de dato");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El usuario fue registrado con éxito");
        response.put("user", newUser);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
            }

    @GetMapping(value = "/user/getUsers")
    public ResponseEntity<List<User>> findAll() {
        try {
            return new ResponseEntity(userService.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/getUser/{id}")
    public ResponseEntity<?> findById (@PathVariable Integer id){
        User user = null;
        Map<String, Object> response = new HashMap<>();

        try{
            user = userService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(user == null){
            response.put("mensaje", "El usuario ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping("/user/updateUser/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Integer id){

        User userActual = userService.findById(id);
        User userUpdated = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){

            List<String> errors = result.getFieldErrors().stream().map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage()).collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if(userActual == null){
            response.put("mensaje", " Error:no se pudo editar, el usuario ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            userActual.setName(user.getName());
            userActual.setPhone(user.getPhone());
            userUpdated = userService.save(userActual);

        } catch(DataAccessException e){
            response.put("mensaje", "Error al actualizar el usuario en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido actualizado con éxito");
        response.put("cliente", userUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("user/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Map<String, Object> response = new HashMap<>();

        try {
            userService.delete(id);
        } catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar el usuario de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El usuario fue eliminado con éxito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    }


