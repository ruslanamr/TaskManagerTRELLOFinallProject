package kz.trello.services.impl;

import java.util.ArrayList;
import java.util.List;
import kz.trello.models.Comment;
import kz.trello.models.Folder;
import kz.trello.models.Task;
import kz.trello.models.TaskCategories;
import kz.trello.repositories.CommentRepository;
import kz.trello.repositories.FolderRepository;
import kz.trello.repositories.TaskCategoryRepository;
import kz.trello.repositories.TaskRepository;
import kz.trello.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  @Autowired
  public TaskCategoryRepository taskCategoryRepository;
  @Autowired
  public FolderRepository folderRepository;
  @Autowired
  public TaskRepository taskRepository;
  @Autowired
  public CommentRepository commentRepository;

  @Override
  public List<TaskCategories> getListCategories() {
    return taskCategoryRepository.findAll();
  }

  @Override
  public void addCategory(TaskCategories taskCategories) {
    taskCategoryRepository.save(taskCategories);
  }

  @Override
  public void deleteCategory(Long id) {
    taskCategoryRepository.deleteById(id);
  }

  @Override
  public void updateCategory(TaskCategories taskCategories) {
    taskCategoryRepository.save(taskCategories);
  }

  @Override
  public List<Folder> getListFolders() {
    return folderRepository.findAll();
  }

  @Override
  public void addFolder(Folder folder) {
    folderRepository.save(folder);
  }

  @Override
  public void updateFolder(Folder folder) {
    folderRepository.save(folder);
  }

  @Override
  public void deleteFolder(Long id) {
    List<Task> tasks = taskRepository.findByFolder_Id(id);
    for(Task task : tasks){
      deleteTask(task.getId());
    }
    folderRepository.deleteById(id);
  }

  @Override
  public Folder getFolderById(Long id) {
    return folderRepository.findById(id).orElse(null);
  }

  @Override
  public void addCategoryForFolder(Long id, List<Long> categoryId) {
    Folder folder = getFolderById(id);
    List<TaskCategories> taskListCategories = new ArrayList<>();
    for (Long category : categoryId) {
      TaskCategories taskCategory = taskCategoryRepository.findById(category).orElse(null);
      taskListCategories.add(taskCategory);
    }
    folder.setCategories(taskListCategories);
    updateFolder(folder);
  }

  @Override
  public void deleteCategoryFromFolder(Long folderId, Long catId) {
    Folder folder = getFolderById(folderId);
    List<TaskCategories> taskCategoriesList = folder.getCategories();
    for (TaskCategories categories : taskCategoriesList) {
      if (categories.getId()==catId){
        taskCategoriesList.remove(categories);
        break;
      }
    }
    folder.setCategories(taskCategoriesList);
    updateFolder(folder);
  }

  @Override
  public List<Task> getListTask() {
    return taskRepository.findAll();
  }

  @Override
  public Task getTaskById(Long id) {
    return taskRepository.findById(id).orElse(null);
  }

  @Override
  public List<Task> getTaskByFolder(Long folder_id) {
    return taskRepository.findByFolder_Id(folder_id);
  }

  @Override
  public void addTask(Task task) {
    taskRepository.save(task);
  }

  @Override
  public void updateTask(Task task) {
    taskRepository.save(task);
  }

  @Override
  public void deleteTask(Long id) {
    taskRepository.deleteById(id);
  }

  @Override
  public List<Comment> getListCommentByTask(Long taskId) {
    return commentRepository.findAllByTaskId(taskId);
  }

  @Override
  public void addComment(String comment, Long taskId) {
    Comment com = new Comment();
    com.setComment(comment);
    com.setTask(getTaskById(taskId));
    commentRepository.save(com);
  }
}
