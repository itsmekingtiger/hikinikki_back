package com.gehirn.or.kr.hikinikki.nikki;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NikkiPageData {
    private final List<Nikki> nikkis;
    private final Integer totalPage;
    private final Integer currentPage;
}
