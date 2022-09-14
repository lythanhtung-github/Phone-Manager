package vn.ltt.shop.service;

import vn.ltt.shop.model.Product;
import vn.ltt.shop.utils.TypeSort;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    void add(Product newProduct);

    void update(Product newProduct);

    void deleteById(long id);

    boolean existsByName(String productName);

    Product findById(long id);

    boolean existById(long id);

    List<Product> sortById(TypeSort type);

    List<Product> sortByName(TypeSort type);

    List<Product> sortByPrice(TypeSort type);

    List<Product> sortByQuantity(TypeSort type);

    List<Product> sortByManufacturer(TypeSort type);

    List<Product> sortByCreateAt(TypeSort type);

    List<Product> findByName(String value);

    List<Product> findByManufacturer(String value);

    Product findProductById(long id);
}
