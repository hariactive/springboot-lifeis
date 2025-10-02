package com.Lifeisgood.products;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImpl implements ProductService{
	
	private ProductRepo productrepo;
	private ObjectMapper mapper;
	
	ProductServiceImpl(ProductRepo productrepo, ObjectMapper mapper){
		this.productrepo = productrepo;
		this.mapper = mapper;
	}

	@Override
    public ProductDto AddProduct(ProductDto dto) {
        ProductEntity entity = mapper.convertValue(dto, ProductEntity.class);
        ProductEntity savedEntity = productrepo.save(entity);
        return mapper.convertValue(savedEntity, ProductDto.class);
    }

	@Override
	public List<ProductDto> AddBulkProduct(List<ProductDto> list) {
		List<ProductEntity> ProdEntities = Arrays.asList(mapper.convertValue(list,ProductEntity[].class));
		List<ProductEntity> saveall = productrepo.saveAll(ProdEntities);
		List<ProductDto> empdto = Arrays.asList(mapper.convertValue(saveall,ProductDto[].class));
		return empdto;
	}

	@Override
	public List<ProductDto> FindBulkProducts() {
		List<ProductEntity> list = productrepo.findAll();
		List<ProductDto> Productentities = Arrays.asList(mapper.convertValue(list,ProductDto[].class));
		return Productentities;
	}

	@Override
	public ProductDto FindProductsById(Long id) {
		ProductEntity entity = productrepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
		ProductDto dto = mapper.convertValue(entity, ProductDto.class);
		return dto;
	}
	
	@Override
	public ProductDto UpdateProductById(ProductDto dto, Long id) {
		Optional<ProductEntity> eOptional = productrepo.findById(id);
		
		if (eOptional.isPresent()) {
			ProductEntity convertValue = mapper.convertValue(dto,ProductEntity.class);
			convertValue.setId(id);
			ProductEntity entity = productrepo.save(convertValue);
			ProductDto empdto = mapper.convertValue(entity, ProductDto.class);
			return empdto;
		}
		else {
			throw new RuntimeException("Product not found to update " + id);
		}
	}

	@Override
	public String DeleteProduct(Long id) {
		productrepo.deleteById(id);
		return "particular product record deleted";
	}
}