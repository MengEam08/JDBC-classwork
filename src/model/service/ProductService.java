package model.service;

import exception.ExceptionHandling;
import model.dto.OrderDto;
import model.dto.ProductDto;
import model.entity.Order;
import model.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> queryAllProducts() throws ExceptionHandling;
    void addNewProduct(Product product) throws ExceptionHandling;
    void updateProductById(Integer id) throws ExceptionHandling;
    void deleteProductById(Integer id);
}
