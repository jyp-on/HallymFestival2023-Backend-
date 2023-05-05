package com.hallym.festival.domain.booth.controller;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.booth.service.BoothService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/booth")
@RequiredArgsConstructor
@Log4j2
public class BoothController {

    private final BoothService boothService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseDTO<BoothDTO> list(PageRequestDTO pageRequestDTO){

        PageResponseDTO<BoothDTO> responseDTO = boothService.list(pageRequestDTO);

        log.info(responseDTO);

        return responseDTO;
    }

    @GetMapping("/{bno}")
    public BoothDTO read(@PathVariable("bno") Long bno){

        BoothDTO boothDTO = boothService.getOne(bno);

        log.info(boothDTO);

        return boothDTO;
    }

    @PostMapping("register")
    public Map<String, String> registerPost(@Valid @RequestBody BoothDTO boothDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()) { //검증에 문제가 있다면 입력 화면으로 리다이렉트
            log.info("has errors.......");
            log.info("-----register----알맞지 않은 입력 값입니다-----");
            return Map.of("result","failed");
        }

        log.info(boothDTO);

        boothService.register(boothDTO);

        return Map.of("result","register success");
    }

    @PutMapping ("/modify/{bno}")
    public Map<String, String> modify( @PathVariable("bno") Long bno, @Valid @RequestBody BoothDTO boothDTO ,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes){

        log.info("board modify post......." + boothDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            log.info("-----modify----알맞지 않은 입력 값입니다-----");

            return Map.of("result","failed");
        }

        boothService.modify(boothDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boothDTO.getBno());

        return Map.of("result","modify success");
    }

    @DeleteMapping ("/{bno}")
    public Map<String, String> remove(@PathVariable("bno") Long bno) {

        log.info("remove post.. " + bno);

        boothService.remove(bno);

        return Map.of("result","Delete Booth" + bno + " is success");
    }
}