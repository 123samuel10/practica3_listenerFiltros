package com.example.practica3_segundocorte.serviceImpl;

import com.example.practica3_segundocorte.ConexionBaseDatos;
import com.example.practica3_segundocorte.model.Category;
import com.example.practica3_segundocorte.model.ProductoCategory;
import com.example.practica3_segundocorte.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryImpl  implements Repository {
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return ConexionBaseDatos.getConnection();
    }

    private ProductoCategory createProduct(ResultSet resultSet) throws SQLException {
        ProductoCategory product = new ProductoCategory();
        product.setId((long) resultSet.getInt("id"));
        product.setNombre(resultSet.getString("product_name"));
        product.setPrecio(resultSet.getDouble("precio"));
        product.setFechaRegistro(resultSet.getDate("date_register").toLocalDate());
        Category category = new Category();
        category.setId(resultSet.getInt("id_category"));
        category.setNombre(resultSet.getString("category_name"));
        product.setCategory(category);
        return product;
    }

    @Override
    public List<ProductoCategory> getList() {
        List<ProductoCategory> products = new ArrayList<>();
        try (Statement statement=getConnection().createStatement();
             ResultSet resultSet=statement.executeQuery("SELECT p.id,p.product_name,p.precio,p.date_register,p.id_category,c.category_name FROM productos_category as p join categories as c on(p.id_category=c.id)")
        ){
            while (resultSet.next()) {
                ProductoCategory product = createProduct(resultSet);
                products.add(product);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
    @Override
    public ProductoCategory getById(Long id) {
        ProductoCategory product = null;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("SELECT p.id,p.product_name,p.precio,p.date_register,p.id_category,c.category_name FROM productos_category as p join categories as c on(p.id_category=c.id) where p.id=?")){
            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                product = createProduct(resultSet);
            }
            resultSet.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return product;
    }
    @Override
    public void save(Object o) {
        ProductoCategory product = (ProductoCategory) o;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("INSERT INTO productos_category(product_name,precio,date_register,id_category) VALUES (?,?,?,?)")){
            preparedStatement.setString(1,product.getNombre());
            preparedStatement.setLong(2,product.getPrecio().longValue());
            preparedStatement.setDate(3, Date.valueOf(product.getFechaRegistro()));
            preparedStatement.setLong(4,product.getCategory().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delateById(Long id) {
        ProductoCategory product = null;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("DELETE FROM productos_category WHERE id =?")){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update(Object o) {
        ProductoCategory product = (ProductoCategory) o;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("UPDATE products_category SET product_name=? ,precio=?,date_register=?,id_category=? where id=?")){
            preparedStatement.setString(1,product.getNombre());
            preparedStatement.setLong(2,product.getPrecio().longValue());
            preparedStatement.setDate(3,Date.valueOf(product.getFechaRegistro()));
            preparedStatement.setLong(4,product.getCategory().getId());
            preparedStatement.setLong(5,product.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
