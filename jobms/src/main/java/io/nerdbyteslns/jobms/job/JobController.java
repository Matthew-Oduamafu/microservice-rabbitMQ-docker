package io.nerdbyteslns.jobms.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping()
    public ResponseEntity<List<Job>> findAll() {
        return new ResponseEntity<>(jobService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Job job) {
        jobService.create(job);
        return new ResponseEntity<>("Job added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findById(@PathVariable Long id) {
        var job = jobService.findById(id);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }
//        return ResponseEntity.ok(job);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (jobService.delete(id)) {
            return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Job job) {
        if (jobService.update(id, job)) {
            return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
