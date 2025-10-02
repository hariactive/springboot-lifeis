package com.Lifeisgood.products;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Lifeisgood.utils.MyResponseHandler;


import jakarta.validation.Valid;

@RequestMapping("/products")
@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	ProductController(ProductService productService){
		this.productService = productService;
	}


	@GetMapping()
	public String hello() {
		return "gh hello";
	}
	
	@PostMapping(value = "/addproduct")
	public ResponseEntity<Object> AddProduct(@Valid @RequestBody ProductDto dto) {
		ProductDto pdto = productService.AddProduct(dto);
		if (pdto!= null) {
			ResponseEntity<Object> resentity = MyResponseHandler.generateResponse(HttpStatus.CREATED, true, "user registered", pdto);
		    return resentity;
		}
		else {
			ResponseEntity<Object> resentity = MyResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Error occured ", pdto);
			 return resentity;
		}
	}
	
	@PostMapping(value = "/addbulkproduct")
    public ResponseEntity<Object> AddBulkProduct(@RequestBody List<ProductDto> dto) {
        List<ProductDto> gdto = productService.AddBulkProduct(dto);
        if (gdto != null && !gdto.isEmpty()) {
            return MyResponseHandler.generateResponse(HttpStatus.CREATED, true, "All users registered", gdto);
        } else {
            return MyResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Error occurred", null);
        }
    } 
	
	 @GetMapping(value = "/findall")
	 public ResponseEntity<Object> FindBulkProducts() {
	        List<ProductDto> all = productService.FindBulkProducts();
	        if (all != null && !all.isEmpty()) {
	            return MyResponseHandler.generateResponse(HttpStatus.OK, true, "Users fetched successfully", all);
	        } else {
	            return MyResponseHandler.generateResponse(HttpStatus.NO_CONTENT, true, "No users found", null);
	        }
	    }
	 
	 
	 @GetMapping(value = "/getone/{id}")
	 public ResponseEntity<Object> FindProductsById(@PathVariable Long id) {
	     ProductDto emp = productService.FindProductsById(id);  // Changed to FindProductsById
	     if (emp != null) {
	         return MyResponseHandler.generateResponse(HttpStatus.OK, true, "User found", emp);
	     } else {
	         return MyResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "User not found", null);
	     }
	 }

	 @PutMapping(value = "/update/{id}")
	 public ResponseEntity<Object> UpdateProductById(@RequestBody ProductDto dto, @PathVariable Long id) {  // Changed to Long
	     ProductDto updateEmp = productService.UpdateProductById(dto, id);  // Use correct method
	     if (updateEmp != null) {
	         return MyResponseHandler.generateResponse(HttpStatus.OK, true, "User updated successfully", updateEmp);
	     } else {
	         return MyResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "User not found", null);
	     }
	 }

	 @DeleteMapping(value = "/delete/{id}")
	 public ResponseEntity<Object> DeleteProduct(@PathVariable Long id) {
	     String deleteResult = productService.DeleteProduct(id);  // Changed to DeleteProduct
	     if ("success".equalsIgnoreCase(deleteResult)) {
	         return MyResponseHandler.generateResponse(HttpStatus.OK, true, "User deleted successfully", null);
	     } else {
	         return MyResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "User not found", null);
	     }
	 }
	
}
