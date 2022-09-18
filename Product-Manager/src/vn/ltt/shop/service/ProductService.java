package vn.ltt.shop.service;

import vn.ltt.shop.utils.CSVUtils;
import vn.ltt.shop.utils.TypeSort;
import vn.ltt.shop.model.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {
    public final static String DATA_PRODUCT_PATH = "data/products.csv";
    public final static String DATA_PRODUCT_DELETED_PATH = "data/data_deleted/product_deleted.csv";
    private static ProductService instance;

    private ProductService() {
    }

    public static ProductService getInstance() {
        if (instance == null) instance = new ProductService();
        return instance;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        List<String> lines = CSVUtils.read(DATA_PRODUCT_PATH);
        for (String line : lines) {
            products.add(Product.parse(line));
        }
        return products;
    }

    @Override
    public List<Product> findAllDeleted() {
        List<Product> products = new ArrayList<>();
        List<String> lines = CSVUtils.read(DATA_PRODUCT_DELETED_PATH);
        for (String line : lines) {
            products.add(Product.parse(line));
        }
        return products;
    }

    @Override
    public void add(Product newProduct) {
        List<Product> products = findAll();
        newProduct.setCreatedAt(Instant.now());
        products.add(newProduct);
        CSVUtils.write(DATA_PRODUCT_PATH, products);
    }

    @Override
    public void update(Product newProduct) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getId() == newProduct.getId()) {
                product.setName(newProduct.getName());
                product.setPrice(newProduct.getPrice());
                product.setQuantity(newProduct.getQuantity());
                product.setUpdatedAt(Instant.now());
                CSVUtils.write(DATA_PRODUCT_PATH, products);
                break;
            }
        }
    }

    @Override
    public void deleteById(long id) {
        List<Product> products = findAll();
        List<Product> productsDeleted = findAllDeleted();
        for (int i = 0; i < products.size(); i++) {
            if ((products.get(i)).getId() == id) {
                productsDeleted.add(products.get(i));
                products.remove(products.get(i));
            }
        }
        CSVUtils.write(DATA_PRODUCT_PATH, products);
        CSVUtils.write(DATA_PRODUCT_DELETED_PATH, productsDeleted);
    }

    @Override
    public void deleteInFileDeleted(long id) {
        List<Product> products = findAllDeleted();
        for (int i = 0; i < products.size(); i++) {
            if ((products.get(i)).getId() == id) {
                products.remove(products.get(i));
            }
        }
        CSVUtils.write(DATA_PRODUCT_DELETED_PATH, products);
    }

    @Override
    public boolean existsByName(String productName) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getName().equals(productName))
                return true;
        }
        return false;
    }

    @Override
    public Product findById(long id) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getId() == id)
                return product;
        }
        return null;
    }

    @Override
    public Product findByIdDeleted(long id) {
        List<Product> products = findAllDeleted();
        for (Product product : products) {
            if (product.getId() == id)
                return product;
        }
        return null;
    }

    @Override
    public boolean existById(long id) {
        return findById(id) != null;
    }


    @Override
    public List<Product> sortById(TypeSort type) {
        List<Product> products = findAll();
        if (type == TypeSort.ASC) {
            products.sort((o1, o2) -> {
                double result = o1.getId() - o2.getId();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            products.sort((o1, o2) -> {
                double result = o1.getId() - o2.getId();
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        CSVUtils.write(DATA_PRODUCT_PATH, products);
        return products;
    }

    @Override
    public List<Product> sortByName(TypeSort type) {
        List<Product> products = findAll();
        if (type == TypeSort.ASC) {
            products.sort((o1, o2) -> {
                double result = o1.getName().compareTo(o2.getName());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            products.sort((o1, o2) -> {
                double result = o1.getName().compareTo(o2.getName());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        CSVUtils.write(DATA_PRODUCT_PATH, products);
        return products;
    }

    @Override
    public List<Product> sortByPrice(TypeSort type) {
        List<Product> products = findAll();
        if (type == TypeSort.ASC) {
            products.sort((o1, o2) -> {
                double result = o1.getPrice() - o2.getPrice();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            products.sort((o1, o2) -> {
                double result = o1.getPrice() - o2.getPrice();
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        CSVUtils.write(DATA_PRODUCT_PATH, products);
        return products;
    }

    @Override
    public List<Product> sortByQuantity(TypeSort type) {
        List<Product> products = findAll();
        if (type == TypeSort.ASC) {
            products.sort((o1, o2) -> {
                int result = o1.getQuantity() - o2.getQuantity();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            products.sort((o1, o2) -> {
                int result = o1.getQuantity() - o2.getQuantity();
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        CSVUtils.write(DATA_PRODUCT_PATH, products);
        return products;
    }

    @Override
    public List<Product> sortByManufacturer(TypeSort type) {
        List<Product> products = findAll();
        if (type == TypeSort.ASC) {
            products.sort((o1, o2) -> {
                double result = o1.getManufacturer().compareTo(o2.getManufacturer());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            products.sort((o1, o2) -> {
                double result = o1.getManufacturer().compareTo(o2.getManufacturer());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        CSVUtils.write(DATA_PRODUCT_PATH, products);
        return products;
    }

    @Override
    public List<Product> sortByCreateAt(TypeSort type) {
        List<Product> products = findAll();
        if (type == TypeSort.ASC) {
            products.sort((o1, o2) -> {
                double result = o1.getCreatedAt().compareTo(o2.getCreatedAt());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            products.sort((o1, o2) -> {
                double result = o1.getCreatedAt().compareTo(o2.getCreatedAt());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        CSVUtils.write(DATA_PRODUCT_PATH, products);
        return products;
    }

    @Override
    public List<Product> findByName(String value) {
        List<Product> products = findAll();
        List<Product> productsFind = new ArrayList<>();
        for (Product item : products) {
            if ((item.getName().toUpperCase()).contains(value.toUpperCase())) {
                productsFind.add(item);
            }
        }
        if (productsFind.isEmpty()) {
            return null;
        }
        return productsFind;
    }

    @Override
    public List<Product> findByManufacturer(String value) {
        List<Product> products = findAll();
        List<Product> productsFind = new ArrayList<>();
        for (Product item : products) {
            if ((item.getManufacturer().toUpperCase()).contains(value.toUpperCase())) {
                productsFind.add(item);
            }
        }
        if (productsFind.isEmpty()) {
            return null;
        }
        return productsFind;
    }

    @Override
    public Product findProductById(long id) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public boolean existByIdDeleted(long id) {
        return findByIdDeleted(id) != null;
    }

}