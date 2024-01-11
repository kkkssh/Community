package com.kkkssh.community.controller;

import com.kkkssh.community.dto.BoardDTO;
import com.kkkssh.community.dto.CommentDTO;
import com.kkkssh.community.service.BoardService;
import com.kkkssh.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm(){
        return "save";  //save.html
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        System.out.println("boardDTO = " +boardDTO);
        boardService.save(boardDTO);
        return "index";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                                @PageableDefault(page = 1)Pageable pageable){
        // 해당 게시글의 조회수를 하나 올리고
        // 게시글 데이터를 가져와서 detail.html에 출력
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        //댓글 목록 가져오기
        List<CommentDTO> commentDTOList = commentService.findAll(id);

        model.addAttribute("board",boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("commentList",commentDTOList);
        return "detail";

    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board",board);
        return "detail";
//        return "redirect:/board/"+boardDTO.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/paging";
    }

    // /board/paging?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1)Pageable pageable,  //page 1 고정
                         Model model){ 
        pageable.getPageNumber();
        Page<BoardDTO> boardDTOPage = boardService.paging(pageable);
        //보여지는 페이지 수 3개
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 ...
        int endPage = Math.min((startPage + blockLimit - 1), boardDTOPage.getTotalPages());

        model.addAttribute("boardList", boardDTOPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";


    }


}
