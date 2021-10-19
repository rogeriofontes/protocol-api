package br.com.unipac.protocoloapi.controller;

import br.com.unipac.protocoloapi.model.domain.Protocol;
import br.com.unipac.protocoloapi.model.service.ProtocolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/protocols")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProtocolController {

    private final ProtocolService protocolService;

    private final int ROW_PER_PAGE = 10;

    @GetMapping
    public String getAll(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Protocol> protocolList = protocolService.findAll(pageNumber, ROW_PER_PAGE);

        Long count = protocolService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;

        model.addAttribute("protocols", protocolList);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);

        return "protocol-list.html";
    }

}
