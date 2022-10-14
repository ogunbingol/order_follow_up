package project.order.followup.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

import project.order.followup.business.abstracts.UserService;
import project.order.followup.core.entities.User;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.ErrorDataResult;
import project.order.followup.core.utilities.results.Result;

@RestController
@RequestMapping(value="/api/users")
public class UsersController {
	private UserService userService;
	
	@Autowired
	public UsersController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostMapping(value="/add")
	public ResponseEntity<?> add(@Valid @RequestBody User user) {
		
		return ResponseEntity.ok(this.userService.add(user)) ;
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<?> update(@Valid @RequestBody User user) {
		
		return ResponseEntity.ok(this.userService.update(user)) ;
	}
	
	@DeleteMapping("/delete")
	public Result deleteByUserId(@RequestParam int id) {
		return this.userService.deleteByUserId(id);
	}
	
	@GetMapping("/getAllUsers")
	public DataResult<List<User>> getAllUsers(){
		return this.userService.getAllUsers();
	}
	
	
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){
		Map<String,String> validationErrors=new HashMap<String,String>();
		for(FieldError fieldError:exceptions.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		ErrorDataResult<Object> errors=new ErrorDataResult<Object>(validationErrors,"Doğrulama hataları");
		return errors;
	}
}
