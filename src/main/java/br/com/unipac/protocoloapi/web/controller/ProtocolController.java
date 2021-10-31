package br.com.unipac.protocoloapi.web.controller;

import br.com.unipac.protocoloapi.model.domain.Protocol;
import br.com.unipac.protocoloapi.exception.BadResourceException;
import br.com.unipac.protocoloapi.exception.ResourceAlreadyExistsException;
import br.com.unipac.protocoloapi.exception.ResourceNotFoundException;
import br.com.unipac.protocoloapi.model.service.ProtocolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/protocols")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProtocolController {

    private final ProtocolService protocolService;

    private final int ROW_PER_PAGE = 10;

    @GetMapping
    public String getAll(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber) throws ResourceNotFoundException {
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

    @GetMapping(value = "{protocolId}")
    public String getById(Model model, @PathVariable Long protocolId) throws ResourceNotFoundException {
        Protocol savedProtocol = protocolService.findById(protocolId);
        model.addAttribute("protocol", savedProtocol);
        model.addAttribute("allowDelete", false);
        return "protocol-detail";
    }

    @GetMapping(value = "/add")
    public String showAdd(Model model) {
        model.addAttribute("protocol", new Protocol());
        model.addAttribute("add", true);
        return "protocol-form";
    }

    @PostMapping(value = "/add")
    public String add(@Valid @ModelAttribute("protocol") Protocol protocol, BindingResult result, RedirectAttributes redirectAttributes) throws BadResourceException, ResourceAlreadyExistsException {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Houve erros no cadastro");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "protocol-form";
        }

        protocolService.save(protocol);
        redirectAttributes.addFlashAttribute("message", "Dados Cadastrados com sucesso!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/protocols";
    }

    @GetMapping(value = "/{protocolId}/edit")
    public String showEdit(Model model, @PathVariable("protocolId") Long protocolId) throws ResourceNotFoundException {
        Protocol savedProtocol = protocolService.findById(protocolId);

        model.addAttribute("protocol", savedProtocol);
        model.addAttribute("add", false);
        return "protocol-form";
    }

    @PostMapping(value = "/{protocolId}/update")
    public String update(@PathVariable("protocolId") Long protocolId, @Valid @ModelAttribute("protocol") Protocol protoco, Model model, BindingResult result, RedirectAttributes redirectAttributes) throws ResourceNotFoundException, BadResourceException {

        if (result.hasErrors()) {
            Protocol savedProtocol = protocolService.findById(protocolId);

            model.addAttribute("protocol", savedProtocol);
            model.addAttribute("add", false);
            redirectAttributes.addFlashAttribute("message", "Houve erros no editar");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "protocol-form";
        }

        protocolService.update(protocolId, protoco);
        redirectAttributes.addFlashAttribute("message", "Dados Cadastrados com sucesso!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/protocols";
    }

    @GetMapping(value = "/{protocolId}/delete")
    public String delete(@PathVariable("protocolId") Long protocolId, RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
        protocolService.deleteById(protocolId);

        redirectAttributes.addFlashAttribute("message", "Dados Deletados com sucesso!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/protocols";
    }
}
