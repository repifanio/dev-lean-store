package br.com.rcedesenvolvimento.dev_lean_store.modules.courses;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rcedesenvolvimento.dev_lean_store.modules.courses.entities.CourseEntity;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/cursos")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("")
    public ResponseEntity<CourseEntity> postMethodName(@Valid @RequestBody CourseEntity course) {
        try {
            var courseInserted = this.courseRepository.save(course);
            return ResponseEntity.ok().body(courseInserted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<CourseEntity>> listCourses() {
        var courses = this.courseRepository.findAll();
        return ResponseEntity.ok().body(courses);
    }

    @PutMapping("{id}")
    public ResponseEntity<CourseEntity> editAllCourse(@Valid @RequestBody CourseEntity course, @PathVariable String id
           ) {
        try {
            UUID uuid = UUID.fromString(id);
            var courseOptional = this.courseRepository.findById(uuid);

            if (courseOptional.isPresent()) {
                var courseFound = courseOptional.get();

                courseFound.setName(course.getName());
                courseFound.setCategory(course.getCategory());
                courseFound.setIsActive(course.getIsActive());

                this.courseRepository.save(courseFound);
                return ResponseEntity.ok().body(courseFound);

            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);

        this.courseRepository.deleteById(uuid);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<CourseEntity> activeCourse(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);

        var courseOptional = this.courseRepository.findById(uuid);

        if (courseOptional.isPresent()) {
            var courseToActive = courseOptional.get();

            var newCourseActive = CourseEntity.builder()
                    .id(courseToActive.getId())
                    .name(courseToActive.getName())
                    .category(courseToActive.getCategory())
                    .isActive(true)
                    .createdAt(courseToActive.getCreatedAt())
                    .updatedAt(courseToActive.getUpdatedAt())
                    .build();

            this.courseRepository.save(newCourseActive);

            return ResponseEntity.ok().body(newCourseActive);

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
