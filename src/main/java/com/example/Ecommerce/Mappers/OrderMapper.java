package com.example.Ecommerce.Mappers;

import com.example.Ecommerce.DTOS.OrderDto;
import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.Models.Order;
import com.example.Ecommerce.Models.Product;

import java.util.List;

public class OrderMapper {
    public static OrderDto OrderToDtoMapper(Order order){
        List<ProductDto> productDtos = order.getProducts().stream().map(ProductMapper::MapToDto).toList();
        return new OrderDto(order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getTotalPrice(),
                order.getPaymentMethod(),
                productDtos);
    }

    public static Order DtoToOrderMapper(OrderDto orderDto){
        return Order.builder()
                .Id(orderDto.Id())
                .orderDate(orderDto.orderDate())
                .paymentMethod(orderDto.paymentMethod())
                .totalAmount(orderDto.totalAmount())
                .totalPrice(orderDto.totalPrice())
                .products(orderDto.products().stream().map(ProductMapper::MapDtoToProduct).toList())
                .build();
    }


}
