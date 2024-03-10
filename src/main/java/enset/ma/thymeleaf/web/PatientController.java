package enset.ma.thymeleaf.web;

import enset.ma.thymeleaf.entities.Patient;
import enset.ma.thymeleaf.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "4") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String kw){
        //Page<Patient> patientList = patientRepository.findAll(PageRequest.of(page,size));
        Page<Patient> patientList = patientRepository.findPatientByNomContainsIgnoreCase(kw,PageRequest.of(page,size));
        model.addAttribute("listePatients", patientList.getContent());
        model.addAttribute("pages", new int[patientList.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword",kw);
        return "patients";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@RequestParam(name = "id") Long id,
                         String keyword,
                         int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String homme(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin/formPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatient(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @PostMapping("/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword){
        if(bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(Model model, Long id, String keyword, int page){
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient itrouvable");
        model.addAttribute("patient", patient);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "update";
    }
}
