package com.example.Ecommerce.Mappers;

import com.example.Ecommerce.DTOS.OrderDto;
import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.Models.Order;
import com.example.Ecommerce.Models.OrderProducts;
import com.example.Ecommerce.Models.Product;


import java.util.List;

public class OrderMapper {
    public static OrderDto OrderToDtoMapper(Order order){
        List<OrderProducts> orderProductsList = order.getOrderproducts();
        List<ProductDto> productDtos =orderProductsList.stream().map(orderProducts -> {
            Product product = orderProducts.getProduct();
            product.setQuantity(orderProducts.getQuantity());
            product.setPrice(orderProducts.getPrice());
            return  ProductMapper.MapToDto(product);
        }).toList();
        return new OrderDto(order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getTotalPrice(),
                order.getPaymentMethod(),
                productDtos);
    }

    public static Order DtoToOrderMapper(OrderDto orderDto){
        Order order = Order.builder()
                .Id(orderDto.Id())
                .orderDate(orderDto.orderDate())
                .paymentMethod(orderDto.paymentMethod())
                .totalAmount(orderDto.totalAmount())
                .totalPrice(orderDto.totalPrice())
//                .products(orderDto.products().stream().map(ProductMapper::MapDtoToProduct).toList())
                .build();
        List<OrderProducts> orderProductsList = orderDto.products().stream().map(productDto ->
             OrderProducts.builder()
                    .order(order)
                    .product(ProductMapper.MapDtoToProduct(productDto))
                    .price(productDto.price())
                    .quantity(productDto.quantity())
                    .build()
        ).toList();
        order.setOrderproducts(orderProductsList);
        return order;
    }


}
