package onlineshop.web;

import onlineshop.model.binding.BoardSizeBindingModel;
import onlineshop.model.binding.ProductAddBinding;
import onlineshop.model.dtos.BoardSizeServiceModel;
import onlineshop.model.view.BoardSizeViewModel;
import onlineshop.service.BoardSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sizes")
public class BoardSizeController {

    private final BoardSizeService boardSizeService;
    private final ModelMapper modelMapper;

    public BoardSizeController(BoardSizeService boardSizeService, ModelMapper modelMapper) {
        this.boardSizeService = boardSizeService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    @PreAuthorize("hasRole('Role_MODERATOR')")
    public String addBicycleSize(Model model) {

        if (model.getAttribute("sizeAdd") == null) {
            model.addAttribute("sizeAdd", new BoardSizeBindingModel());
        }
        return "add-size";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('Role_MODERATOR')")
    public String addBoardSizeConfirm(@Valid @ModelAttribute("boardSizeBindingModel") BoardSizeBindingModel boardSizeBindingModel,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("boardSizeBindingModel", boardSizeBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.boardSizeBindingModel", bindingResult);
            return "redirect:/sizes/add";
        }

        BoardSizeServiceModel boardSizeServiceModel = this.modelMapper.map(boardSizeBindingModel, BoardSizeServiceModel.class);
        this.boardSizeService.addSize(boardSizeServiceModel);

        return "redirect:/sizes/all";
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allBicycleSizes(ModelAndView modelAndView) {
        List<BoardSizeViewModel> sizes = this.boardSizeService.findAllBoardSizes()
                .stream()
                .map(c -> this.modelMapper.map(c, BoardSizeViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("sizes", sizes);
        modelAndView.setViewName("all-sizes");

        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView delete(@PathVariable String id, ModelAndView modelAndView) {
        this.boardSizeService.deleteBoardSizeById(id);
        modelAndView.setViewName("redirect:/sizes/all");
        return modelAndView;
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @ResponseBody
    public List<BoardSizeViewModel> fetchBoardSizes() {
        return this.boardSizeService.findAllBoardSizes()
                .stream()
                .map(s -> this.modelMapper.map(s, BoardSizeViewModel.class))
                .collect(Collectors.toList());
    }
}
