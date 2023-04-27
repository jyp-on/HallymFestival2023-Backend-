package com.hallym.festival.domain.notice.controller;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping(value = "notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping
    public NoticeDto createNotice(@RequestBody NoticeDto noticeDto) {
        return noticeService.create(noticeDto);
    }

    @GetMapping("/list")
    public PageResponseDTO<NoticeDto> getNoticeListPage(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<NoticeDto> responseDTO = noticeService.getNoticeListPage(pageRequestDTO);
        return responseDTO;
    }

    @GetMapping("/{nno}")
    public NoticeDto getNotice(@PathVariable("nno") Long nno) {
        return noticeService.getNotice(nno);
    }

    @DeleteMapping("/{nno}")
    public Map<String, String> deleteNotice(@PathVariable("nno") Long nno) {
        String result = noticeService.delete(nno);
        return Map.of("result", result);
    }

    @PutMapping("/{nno}")
    public Map<String, String> updateNotice(@RequestBody NoticeDto noticeDto, @PathVariable("nno") Long nno) {
        noticeService.modify(nno, noticeDto);
        return Map.of("result", "update success");
    }

    @GetMapping("/search")
    public PageResponseDTO<NoticeDto> searchPage(@RequestParam(value = "keyword") String keyword, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<NoticeDto> responseDTO = noticeService.searchPage(keyword, pageRequestDTO);
        return responseDTO;
    }

}
