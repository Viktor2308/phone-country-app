package com.github.viktor2308.phonecountryapp.service.impl;

import com.github.viktor2308.phonecountryapp.entity.PhoneCodeEntity;
import com.github.viktor2308.phonecountryapp.repository.PhoneCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.github.viktor2308.phonecountryapp.utils.Constants.REMOVE_NON_NUMERIC_AND_SPECIAL_CHARS;
import static com.github.viktor2308.phonecountryapp.utils.Constants.SPLIT_IGNORING_COMMAS_IN_PARENTHESES;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WikiDataInitServiceImpl {

    @Value("${wiki.url}")
    String wikiUrl;

    final PhoneCodeRepository phoneCodeRepository;

    @PostConstruct
    public void loadDataFromWiki() {
        try {
            Document wikiPage = Jsoup.connect(wikiUrl).get();
            Optional<Element> tableOpt = Optional.ofNullable(wikiPage.select("table.wikitable").first());
            if (tableOpt.isPresent()) {
                for (var row : tableOpt.get().select("tr").subList(2, tableOpt.get().select("tr").size())) {
                    var columns = row.select("td, th");
                    String country = columns.get(0).text();
                    List<Integer> cods = parseCods(columns.get(1).text());
                    cods.forEach(code -> {
                        PhoneCodeEntity phoneCodeEntity = phoneCodeRepository.findByCodeAndCountry(code, country);
                        if (phoneCodeEntity == null) {
                            phoneCodeRepository.save(PhoneCodeEntity.builder()
                                    .code(code)
                                    .country(country)
                                    .build());
                        }
                    });
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading data, check your internet connection and restart the application", e);
        }
    }

    public List<Integer> parseCods(String cods) {
        List<Integer> result = new ArrayList<>();
        cods = cods.replaceAll(REMOVE_NON_NUMERIC_AND_SPECIAL_CHARS, "");
        String[] parts = cods.split(SPLIT_IGNORING_COMMAS_IN_PARENTHESES);

        for (String part : parts) {
            part = part.trim();

            if (part.contains("(") && part.contains(")")) {
                String baseCode = part.substring(0, part.indexOf("(")).trim();
                String suffixes = part.substring(part.indexOf("(") + 1, part.indexOf(")")).trim();
                String[] suffixArray = suffixes.split(",");
                for (String suffix : suffixArray) {
                    String fullCode = baseCode + suffix.trim();
                    result.add(Integer.parseInt(fullCode));
                }
            } else {
                result.add(Integer.parseInt(part));
            }
        }
        return result;
    }
}
