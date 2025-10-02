package com.Lifeisgood.products;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {

	
	public ProductDto AddProduct(ProductDto dto);
	public List<ProductDto> AddBulkProduct(List<ProductDto> list);
	public List<ProductDto> FindBulkProducts();
	public ProductDto FindProductsById(Long id);
	public ProductDto UpdateProductById(ProductDto dto, Long id);
	public String DeleteProduct(Long id);
}

