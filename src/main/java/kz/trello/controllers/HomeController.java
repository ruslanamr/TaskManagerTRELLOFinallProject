package kz.trello.controllers;

import java.util.List;
import kz.trello.models.Folder;
import kz.trello.models.Task;
import kz.trello.models.TaskCategories;
import kz.trello.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

  @Autowired
  public TaskService taskService;

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("folders", taskService.getListFolders());
    return "/home";
  }

  @GetMapping("/categories")
  public String categories(Model model) {
    model.addAttribute("categories", taskService.getListCategories());
    return "/categories";
  }

  @PostMapping("/addcategory")
  public String addcategory(TaskCategories taskCategories) {
    taskService.addCategory(taskCategories);
    return "redirect:/categories";
  }

  @PostMapping("/deletecategory")
  public String deleteCategory(@RequestParam(name = "id") Long id) {
    taskService.deleteCategory(id);
    return "redirect:/categories";
  }

  @PostMapping("/editcategory")
  public String editcategory(TaskCategories taskCategories) {
    taskService.updateCategory(taskCategories);
    return "redirect:/categories";
  }

  @PostMapping("/addfolder")
  public String addfolder(Folder folder) {
    taskService.addFolder(folder);
    return "redirect:/";
  }

  @GetMapping("/folderdetails/{id}")
  public String folderdetails(@PathVariable Long id, Model model) {
    model.addAttribute("folder", taskService.getFolderById(id));
    model.addAttribute("categories", taskService.getListCategories());
    model.addAttribute("tasks", taskService.getTaskByFolder(id));
    return "/folder";
  }

  @PostMapping("/addCategories/{id}")
  public String addCategories(@PathVariable Long id,
      @RequestParam(name = "operators_name") List<Long> category_id,
      Model model) {
    taskService.addCategoryForFolder(id, category_id);
    return "redirect:/folderdetails/" + id;
  }

  @PostMapping("/deleteCategoryFromFolder")
  public String deleteCategoryFromFolder(@RequestParam(name = "cat_id") Long cat_id,
      @RequestParam(name = "folder_id") Long folder_id) {
    taskService.deleteCategoryFromFolder(folder_id, cat_id);
    return "redirect:/folderdetails/" + folder_id;
  }

  @PostMapping("/addTask")
  public String addTask(Task task) {
    taskService.addTask(task);
    return "redirect:/folderdetails/" + task.getFolder().getId();
  }

  @GetMapping("/taskdetail/{id}")
  public String taskdetail(@PathVariable Long id, Model model) {
    model.addAttribute("taskdetail", taskService.getTaskById(id));
    model.addAttribute("comments", taskService.getListCommentByTask(id));

    return "/taskdetail";
  }

  @PostMapping("/editTask")
  public String editTask(Task task) {
    taskService.updateTask(task);
    return "redirect:/folderdetails/" + task.getFolder().getId();
  }

  @PostMapping("/deletetask")
  public String taskdetail(@RequestParam(name = "id") Long id,
      @RequestParam(name = "folder") Long f_id) {
    taskService.deleteTask(id);
    return "redirect:/folderdetails/" + f_id;
  }

  @PostMapping("/deletefolder")
  public String deletefolder(@RequestParam(name = "folder") Long id) {
    taskService.deleteFolder(id);
    return "redirect:/";
  }

  @PostMapping("/editfolder")
  public String editfolder(Folder folder){
    taskService.updateFolder(folder);
    return "redirect:/";
  }

  @PostMapping("/addcomment")
  public String addcomment(@RequestParam(name = "comment") String comment,
                          @RequestParam(name = "task_id") Long taskId){
    taskService.addComment(comment, taskId);
    return "redirect:/taskdetail/" + taskId;
  }

}
