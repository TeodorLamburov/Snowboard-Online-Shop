package onlineshop.service;

import onlineshop.model.dtos.BoardSizeServiceModel;

import java.util.List;

public interface BoardSizeService {

    void addSize(BoardSizeServiceModel boardSizeServiceModel);

    List<BoardSizeServiceModel> findAllBoardSizes();

    void deleteBoardSizeById(String id);
}
