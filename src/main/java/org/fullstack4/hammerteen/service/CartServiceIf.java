package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.CartDTO;
import org.fullstack4.hammerteen.dto.OrderDetailDTO;

import java.util.List;

public interface CartServiceIf {
    List<CartDTO> myCartList(String userId);
    List<OrderDetailDTO> myOrderList(String userId);
    int countList(String userId);

    int addCart(CartDTO cartDTO);
    void deleteCart(int idx);
    void deleteCartByLectureIdx(int idx, String userIdx);
}
