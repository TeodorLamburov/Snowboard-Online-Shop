package onlineshop.service.impl;

import onlineshop.model.dtos.BoardSizeServiceModel;
import onlineshop.model.entities.BoardSize;
import onlineshop.repository.BoardSizeRepository;
import onlineshop.service.BoardSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardSizeServiceImpl implements BoardSizeService {

    private final BoardSizeRepository boardSizeRepository;
    private final ModelMapper modelMapper;

    public BoardSizeServiceImpl(BoardSizeRepository boardSizeRepository, ModelMapper modelMapper) {
        this.boardSizeRepository = boardSizeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addSize(BoardSizeServiceModel boardSizeServiceModel) {
        BoardSize boardSize = this.modelMapper.map(boardSizeServiceModel, BoardSize.class);
        this.boardSizeRepository.save(boardSize);
    }

    @Override
    public List<BoardSizeServiceModel> findAllBoardSizes() {
        return this.boardSizeRepository.findAll()
                .stream()
                .map(s -> this.modelMapper.map(s, BoardSizeServiceModel.class)
                )
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBoardSizeById(String id) {
        this.boardSizeRepository.delete(this.boardSizeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id")));
    }
}
