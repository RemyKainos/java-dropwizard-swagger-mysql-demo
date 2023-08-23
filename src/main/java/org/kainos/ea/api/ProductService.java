package org.kainos.ea.api;

import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.*;
import org.kainos.ea.core.ProductValidator;
import org.kainos.ea.db.ProductDAO;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;


public class ProductService {
    private ProductDAO productDAO = new ProductDAO();
    private ProductValidator productValidator = new ProductValidator();
    public List<Product> getAllProducts() throws FailedToGetProductsException {
        try{
            List<Product> productList = productDAO.getAllProducts();
            return productList;
        } catch (SQLException e){
            System.err.println(e.getMessage());

            throw new FailedToGetProductsException();
        }
    }

    public Product getProductById(int id) throws FailedToGetProductsException, ProductDoesNotExistException{
        try{
            Product product = productDAO.getProductById(id);

            if(product == null){
                throw new ProductDoesNotExistException();
            }

            return product;

        } catch (Exception e){
            throw new FailedToGetProductsException();
        }
    }

    public int createProduct(ProductRequest product) throws FailedToCreateProductException, InvalidProductException{
        try{
            String validation = productValidator.isValidProduct(product);

            if(validation != null){
                throw new InvalidProductException(validation);
            }


            int id = productDAO.createProduct(product);

            if(id == -1){
                throw new FailedToCreateProductException();
            }

            return id;
        } catch (SQLException e){
            System.err.println(e.getMessage());

            throw new FailedToCreateProductException();
        }
    }

    public void updateProduct(int id, ProductRequest product) throws InvalidProductException, ProductDoesNotExistException, FailedToUpdateProductException{
        try{
            String validation = productValidator.isValidProduct(product);

            if(validation != null){
                throw new InvalidProductException(validation);
            }

            Product productToUpdate = productDAO.getProductById(id);

            if(productToUpdate == null){
                throw new ProductDoesNotExistException();
            }

            productDAO.updateProduct(id, product);
        }catch (SQLException e){
            System.err.println(e.getMessage());

            throw new FailedToUpdateProductException();
        }
    }

    public void deleteProduct(int id) throws ProductDoesNotExistException, FailedToDeleteProductException{
        try{
            Product productToDelete = productDAO.getProductById(id);

            if(productToDelete == null){
                throw new ProductDoesNotExistException();
            }

            productDAO.deleteProduct(id);
        } catch (SQLException e){
            throw new FailedToDeleteProductException();
        }
    }
}
