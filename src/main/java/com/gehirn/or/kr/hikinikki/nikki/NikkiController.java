package com.gehirn.or.kr.hikinikki.nikki;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nikki")
@RequiredArgsConstructor
public class NikkiController {
    private final NikkiRepo nikkiRepo;

    static int nikkiPerPage = 10;

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
    public List<Nikki> getRecentNikki(
            @RequestParam(value = "page", required = false) Integer pageNum
    ) {
        if (pageNum == null) {
            pageNum = 0;
        }
        var pageReq = PageRequest.of(pageNum, nikkiPerPage, Sort.by("createdAt").descending());

        return nikkiRepo.findAll(pageReq).getContent();
    }

    @PostMapping
    public Nikki postNikki(@RequestBody Nikki nikki) {
        return nikkiRepo.save(nikki);
    }
}
