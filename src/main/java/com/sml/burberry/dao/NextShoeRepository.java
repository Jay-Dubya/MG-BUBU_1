package com.sml.burberry.dao;


import com.sml.burberry.model.GenericShoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by michaelgoode on 29/11/2016.
 */
@Repository
public class NextShoeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveShoe(String code, String image) {
        final String insertSql = "insert into NXUP_SHOEIMAGES (Code,ImageName) values (?,?)";
        PreparedStatement preparedStatement = null;
        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = conn.prepareStatement(insertSql);
            preparedStatement.setString(1, code.trim().toUpperCase());
            preparedStatement.setString(2, image.trim().toUpperCase());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
    }

    public List<GenericShoe> findShoeByReference(String code ) {
        final String sql = "select * from NXUP_SHOEIMAGES where Code=? order by Code,ImageName";
        PreparedStatement preparedStatement = null;
        List<GenericShoe> items = new ArrayList<GenericShoe>();
        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, code.trim().toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                GenericShoe genericShoe = new GenericShoe();
                genericShoe.setImagename(resultSet.getString("ImageName"));
                genericShoe.setImagecode(resultSet.getString("Code"));
                items.add(genericShoe);
            }
        } catch (SQLException ex) {
            ex.getMessage();
            return items;
        } finally {
            try {
                preparedStatement.close();
                return items;
            } catch (SQLException ex) {
                ex.getMessage();
                return items;
            }
        }
    }

    public List<GenericShoe> findShoeByImage(String imageCode ) {
        final String sql = "select * from BUBU_SHOES where ImageName=? order by Code,ImageName";
        PreparedStatement preparedStatement = null;
        List<GenericShoe> items = new ArrayList<GenericShoe>();
        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, imageCode.trim().toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                GenericShoe genericShoe = new GenericShoe();
                genericShoe.setImagename(resultSet.getString("ImageName"));
                genericShoe.setImagecode(resultSet.getString("Code"));
                genericShoe.setUploadDate(resultSet.getString("rowUpdate"));
                items.add(genericShoe);
            }
        } catch (SQLException ex) {
            ex.getMessage();
            return items;
        } finally {
            try {
                preparedStatement.close();
                return items;
            } catch (SQLException ex) {
                ex.getMessage();
                return items;
            }
        }
    }

    public void postBatch(HashSet<GenericShoe> items, String filename) {
        String sqlInsertShoe = "insert into BUBU_SHOES (Code,ImageName,rowUpdate) values (?,?,GetDate())";

        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsertShoe);
            for (GenericShoe shoe : items) {
                preparedStatement.setString(1, shoe.getImagecode());
                preparedStatement.setString(2, shoe.getImagename());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
