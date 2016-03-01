import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import org.sql2o.*;

public class Department {
  private int id;
  private String department_name;

  public int getId() {
    return id;
  }

  public String getDepartmentName() {
    return department_name;
  }


  public Department(String department_name) {
    this.department_name = department_name;
  }

  @Override
  public boolean equals(Object otherDepartment){
    if (!(otherDepartment instanceof Department)) {
      return false;
    } else {
      Department newDepartment = (Department) otherDepartment;
      return this.getDepartmentName().equals(newDepartment.getDepartmentName()) &&
             this.getId() == newDepartment.getId();
    }
  }


  public static List<Department> all() {
    String sql = "SELECT * FROM departments ORDER BY department_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Department.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO departments (department_name) VALUES (:department_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("department_name", department_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Department find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM departments where id=:id";
      Department department = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Department.class);
      return department;
    }
  }

  public void update(String department_name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE departments SET department_name = :department_name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("department_name", department_name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    String sql = "DELETE FROM departments WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();

    String joinStudentsDeleteQuery = "DELETE FROM students_departments WHERE department_id = :departmentId";
    con.createQuery(joinStudentsDeleteQuery)
      .addParameter("departmentId", id)
      .executeUpdate();

    String joinCoursesDeleteQuery = "DELETE FROM courses_departments WHERE department_id = :departmentId";
    con.createQuery(joinCoursesDeleteQuery)
      .addParameter("departmentId", id)
      .executeUpdate();
    }
  }
  // public List<Task> getAllTasks() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM tasks ORDER BY description";
  //     return con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeAndFetch(Task.class);
  //   }
  // }
  //
  // public void addCategory(Category category) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO categories_tasks (category_id, task_id) VALUES (:category_id, :task_id)";
  //     con.createQuery(sql)
  //       .addParameter("category_id", category.getId())
  //       .addParameter("task_id", this.getId())
  //       .executeUpdate();
  //   }
  // }
  //
  // public ArrayList<Category> getCategories() {
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "SELECT category_id FROM categories_tasks WHERE task_id = :task_id";
  //     List<Integer> categoryIds = con.createQuery(sql)
  //       .addParameter("task_id", this.getId())
  //       .executeAndFetch(Integer.class);
  //
  //     ArrayList<Category> categories = new ArrayList<Category>();
  //
  //     for (Integer categoryId : categoryIds) {
  //         String taskQuery = "Select * From categories WHERE id = :categoryId ORDER BY name";
  //         Category category = con.createQuery(taskQuery)
  //           .addParameter("categoryId", categoryId)
  //           .executeAndFetchFirst(Category.class);
  //         categories.add(category);
  //     }
  //     return categories;
  //   }
  // }
  //
  // public void removeCategory(int categoryId) {
  //   try(Connection con = DB.sql2o.open()){
  //     String sql ="DELETE FROM categories_tasks WHERE category_id =  :categoryId AND task_id = :taskId";      con.createQuery(sql)
  //       .addParameter("categoryId", categoryId)
  //       .addParameter("taskId", this.getId())
  //       .executeUpdate();
  //   }
  // }
  //
  // public void deCompleteTask() {
  // try(Connection con = DB.sql2o.open()){
  //   String sql = "UPDATE tasks SET complete = false WHERE id = :id";
  //   con.createQuery(sql)
  //     .addParameter("id", id)
  //     .executeUpdate();
  //   }
  // }
  //
  // public void completeTask() {
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "UPDATE tasks SET complete = true WHERE id = :id";
  //     con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeUpdate();
  //   }
  // }
  //
  // public void addDue(String dueDate) {
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "UPDATE tasks SET due = :dueDate WHERE id = :id";
  //     con.createQuery(sql)
  //       .addParameter("id", id)
  //       .addParameter("dueDate", dueDate)
  //       .executeUpdate();
  //   }
  // }
}
