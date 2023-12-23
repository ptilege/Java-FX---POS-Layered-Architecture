package dao.custom.impl;

import db.DBConnection;
import dto.OrderDetailDto;
import dao.custom.OrderDetailDao;
import dto.OrderDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean saveOrderDetails(List<OrderDetailDto> list) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (OrderDetailDto dto:list) {
            pstm.setString(1,dto.getOrderId());
            pstm.setString(2,dto.getItemCode());
            pstm.setInt(3,dto.getQty());
            pstm.setDouble(4,dto.getUnitPrice());
            if (!(pstm.executeUpdate()>0)){
                return false;
            }
        }
        return true;
    }
    @Override
    public List<OrderDetailDto> allOrders() throws SQLException, ClassNotFoundException {
        List<OrderDetailDto> list = new ArrayList<>();

        String sql = "SELECT * FROM orderdetail";
        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            while (resultSet.next()) {
                OrderDetailDto orderDetailsDto = new OrderDetailDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getDouble(4)
                );
                list.add(orderDetailsDto);
            }
        }
        return list;
    }

    @Override
    public boolean deleteOrder(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM orderdetail WHERE orderId = ?";
        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, orderId);
            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public OrderDto getOrder(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orderdetail WHERE orderId = ?";
        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, orderId);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return new OrderDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            null // You need to fetch order details separately
                    );
                }
            }
        }
        return null;
    }

}
