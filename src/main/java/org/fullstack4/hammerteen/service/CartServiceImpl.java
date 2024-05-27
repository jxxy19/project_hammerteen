package org.fullstack4.hammerteen.service;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.domain.CartEntity;
import org.fullstack4.hammerteen.domain.OrderDetailEntity;
import org.fullstack4.hammerteen.dto.CartDTO;
import org.fullstack4.hammerteen.dto.LectureDTO;
import org.fullstack4.hammerteen.dto.OrderDetailDTO;
import org.fullstack4.hammerteen.repository.CartRepository;
import org.fullstack4.hammerteen.repository.LectureRepository;
import org.fullstack4.hammerteen.repository.OrderDetailRepository;
import org.fullstack4.hammerteen.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartServiceIf{
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final LectureRepository lectureRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CartDTO> myCartList(String userId) {
        List<CartEntity> cartEntityList = cartRepository.findAllByUserId(userId);
        List<CartDTO> cartDTOList = null;
        if(cartEntityList != null) {
            cartDTOList = cartEntityList.stream().map(vo -> modelMapper.map(vo, CartDTO.class)).collect(Collectors.toList());
            cartDTOList.forEach(dto -> {
                dto.setLectureDTO(modelMapper.map(lectureRepository.findById(dto.getLectureIdx()), LectureDTO.class));
            });
        }
        return cartDTOList;
    }

    @Override
    public List<OrderDetailDTO> myOrderList(String userId) {
        List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.findAllByUserId(userId);
        List<OrderDetailDTO> orderDetailDTOList = null;
        if(orderDetailEntityList != null) {
            orderDetailDTOList = orderDetailEntityList.stream().map(vo -> modelMapper.map(vo, OrderDetailDTO.class)).collect(Collectors.toList());
        }
        return orderDetailDTOList;
    }

    @Override
    public int countList(String userId) {
        return cartRepository.countAllByUserId(userId);
    }

    @Override
    public int addCart(CartDTO cartDTO) {
        int idx = cartRepository.save(modelMapper.map(cartDTO, CartEntity.class)).getCartIdx();
        return idx;
    }

    @Transactional
    @Override
    public void deleteCart(int idx) {
        cartRepository.deleteById(idx);
    }

    @Transactional
    @Override
    public void deleteCartByLectureIdx(int idx, String userIdx) {
        cartRepository.deleteAllByLectureIdxAndUserId(idx, userIdx);
    }
}
