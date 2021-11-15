package com.gehirn.or.kr.hikinikki.nikki;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gehirn.or.kr.hikinikki.nnn.NNNService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

@RestController
@RequestMapping("/api/nikki")
@RequiredArgsConstructor
public class NikkiController {
    static int nikkiPerPage = 10;
    private final NikkiRepo nikkiRepo;
    private final NNNService nnnService;
    private final ObjectMapper objectMapper;

    @GetMapping("/{id}")
    public ResponseEntity<Nikki> getNikki(@PathVariable("id") Long id) {
        var optionalNikki = nikkiRepo.findById(id);
        if (optionalNikki.isPresent()) {
            var nikki = optionalNikki.get();
            return new ResponseEntity<>(nikki, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/recent")
    public NikkiPageData getRecentNikki(
            @RequestParam(value = "page", required = false) Integer pageNum
    ) {
        if (pageNum == null) {
            pageNum = 0;
        }
        var pageReq = PageRequest.of(pageNum, nikkiPerPage, Sort.by("createdAt").descending());
        var page = nikkiRepo.findAll(pageReq);

        var nikkiPageData = new NikkiPageData
                .NikkiPageDataBuilder()
                .nikkis(page.getContent())
                .totalPage(page.getTotalPages())
                .currentPage(pageNum)
                .build();

        return nikkiPageData;
    }

    @PostMapping
    public Nikki postNikki(@RequestBody Nikki nikki) {
        try {
            final var new_nikki = nikkiRepo.save(nikki);
            var msg = new TextMessage(objectMapper.writeValueAsString(new_nikki));
            nnnService.broadcast(msg);
            return new_nikki;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
