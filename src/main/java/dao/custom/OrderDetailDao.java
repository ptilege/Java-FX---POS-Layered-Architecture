package dao.custom;

import dto.OrderDetailDto;
import dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDao {
    boolean saveOrderDetails(List<OrderDetailDto> list) throws SQLException, ClassNotFoundException;
    List<OrderDetailDto> allOrders() throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String orderId) throws SQLException, ClassNotFoundException;

    OrderDto getOrder(String orderId) throws SQLException, ClassNotFoundException;
}
