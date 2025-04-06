package com.example.Ecommerce.Service;

import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.Mappers.ProductMapper;
import com.example.Ecommerce.Models.Cart;
import com.example.Ecommerce.Models.Customer;
import com.example.Ecommerce.Models.Order;
import com.example.Ecommerce.Models.Product;
import com.example.Ecommerce.Repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CustomerService customerService;
    private final CartRepository cartRepository;
    private final OrderService orderService;
    private final ProductService productService;

    //    check correct cart
    public Cart checkCart(Authentication authentication,Long cartId){
        Customer customer = customerService.returnCustomer(authentication);
        if(!customer.getCart().getId().equals(cartId)){
            throw new RuntimeException("cart with Id"+cartId+"Doesn't belong to current user");
        }
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("no cart In Repo"));
    }

    public Cart getCurrentCart(Authentication authentication) {
        Customer customer = customerService.returnCustomer(authentication);
        return customer.getCart();
    }

    @Transactional
    public Cart editCartItems(Authentication authentication, Long cartID, Map<Long, ProductDto> productDtoMap) {
        Cart cart = checkCart(authentication,cartID);
        Map<Long,Product> productMap = Map.of();
        List<Product> productList = cart.getProducts();
        for(int i=0;i<productList.size();i++){
            if(productDtoMap.containsKey(productList.get(i).getId())){
                Long Id = productList.get(i).getId();
//                Product oldProduct = productMap.get(Id);
                ProductDto oldProduct = productDtoMap.get(Id);
                Product newProduct = productList.get(i);
                int diff =  newProduct.getQuantity()-oldProduct.quantity();
                cart.setTotalAmount(cart.getTotalAmount()+diff);
                cart.setTotalPrice(cart.getTotalPrice()+diff*oldProduct.price());
                productList.set(i,ProductMapper.MapDtoToProduct(productDtoMap.get(Id)));
            }
        }
        cart.setProducts(productList);
        return cart;
    }

    @Transactional
    public Order finalizePurchase(Authentication authentication, Long cartID, String paymentMethod) {
//        check cart and customer then pass them to order to finalize
        Customer customer = customerService.returnCustomer(authentication);
        Cart cart = checkCart(authentication,cartID);
        Order order = orderService.SaveOrder(customer.getID(),cart,paymentMethod);
        List<Product> products = cart.getProducts();
        for (Product product : products) {
            Long productID = product.getId();
            int amount = product.getQuantity();
            productService.editStock(productID, amount);
        }
        products.clear();
        cart.setProducts(products);
        cart.setTotalPrice(0.0);
        cart.setTotalAmount(0);
        return order;
    }
}
