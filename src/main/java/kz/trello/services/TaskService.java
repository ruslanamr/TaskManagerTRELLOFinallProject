package kz.trello.services;

import java.util.List;
import kz.trello.models.Comment;
import kz.trello.models.Folder;
import kz.trello.models.Task;
import kz.trello.models.TaskCategories;

public interface TaskService {

  List<TaskCategories> getListCategories();

  void addCategory(TaskCategories taskCategories);

  void deleteCategory(Long id);

  void updateCategory(TaskCategories taskCategories);


  List<Folder> getListFolders();

  Folder getFolderById(Long id);

  void addFolder(Folder folder);

  void updateFolder(Folder folder);

  void deleteFolder(Long id);

  void addCategoryForFolder(Long id, List<Long> categoryId);

  void deleteCategoryFromFolder(Long folderId, Long catId);

  List<Task> getListTask();
  Task getTaskById(Long id);
  List <Task> getTaskByFolder(Long folder_id);
  void addTask(Task task);
  void updateTask(Task task);
  void deleteTask(Long id);

  List<Comment> getListCommentByTask(Long taskId);
  void addComment(String comment, Long taskId);


}
