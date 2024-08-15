package io.nerdbyteslns.firstjobapp.company;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    public ResponseEntity<List<Company>> findAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Company company) {
        companyService.create(company);
        return ResponseEntity.ok("Company created successfully");
    }


    @GetMapping("/{id}")
    public ResponseEntity<Company> findById(@PathVariable Long id) {
        Company company = companyService.findById(id);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (companyService.delete(id)) {
            return ResponseEntity.ok("Company deleted successfully");
        }

        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Company company) {
        if (companyService.update(id, company)) {
            return ResponseEntity.ok("Company updated successfully");
        }

        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }
}
