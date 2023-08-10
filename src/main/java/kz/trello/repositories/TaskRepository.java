package kz.trello.repositories;

import java.util.List;
import kz.trello.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByFolder_Id(Long id);

 }
