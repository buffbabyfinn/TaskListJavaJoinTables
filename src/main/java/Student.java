import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Student {
  private int id;
  private String first_name;
  private String last_name;
  private String enrollment_date;

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return first_name;
  }

  public String getLastName() {
    return first_name;
  }

  public String getEnrollmentDate() {
    return enrollment_date;
  }

  public Student(String first_name, String last_lame) {
    this.first_name = first_name;
    this.last_name = last_name;
  }

  public static List<Student> all() {
    String sql = "SELECT * FROM students ORDER BY last_name, first_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  @Override
  public boolean equals(Object otherStudent){
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getFirstName().equals(newStudent.getFirstName()) &&
      this.getLastName().equals(newStudent.getLastName());
    }
  }

  // public void save() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO Categories(name) VALUES (:name)";
  //     this.id = (int) con.createQuery(sql, true)
  //       .addParameter("name", this.name)
  //       .executeUpdate()
  //       .getKey();
  //   }
  // }
  //
  // public void deleteCategory() {
  //   String sql = "DELETE FROM Categories WHERE id=:id";
  //   try(Connection con = DB.sql2o.open()) {
  //     con.createQuery(sql)
  //     .addParameter("id", id)
  //     .executeUpdate();
  //     String joinDeleteQuery = "DELETE FROM categories_tasks WHERE category_id = :categoryId";
  //     con.createQuery(joinDeleteQuery)
  //       .addParameter("categoryId", id)
  //       .executeUpdate();
  //   }
  // }
  //
  // public static Category find(int id) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM Categories where id=:id";
  //     Category Category = con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeAndFetchFirst(Category.class);
  //     return Category;
  //   }
  // }
  //
  // public void addTask(Task task) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO categories_tasks (category_id, task_id) VALUES (:category_id, :task_id)";
  //     con.createQuery(sql)
  //       .addParameter("category_id", this.getId())
  //       .addParameter("task_id", task.getId())
  //       .executeUpdate();
  //   }
  // }
  //
  // public ArrayList<Task> getTasks() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT task_id FROM categories_tasks  JOIN tasks ON (tasks.id = categories_tasks.task_id) WHERE category_id = :category_id ORDER BY description";
  //     List<Integer> taskIds = con.createQuery(sql)
  //       .addParameter("category_id", this.getId())
  //       .executeAndFetch(Integer.class);
  //
  //     ArrayList<Task> tasks = new ArrayList<Task>();
  //
  //     for (Integer taskId : taskIds) {
  //       String taskQuery = "Select * FROM tasks WHERE id = :taskId ORDER BY description";
  //       Task task = con.createQuery(taskQuery)
  //         .addParameter("taskId", taskId)
  //         .executeAndFetchFirst(Task.class);
  //       tasks.add(task);
  //     }
  //     return tasks;
  //   }
  // }
  //
  // public void removeTask(int taskId) {
  //   try(Connection con = DB.sql2o.open()){
  //     String sql ="DELETE FROM categories_tasks WHERE category_id =  :categoryId AND task_id = :taskId";      con.createQuery(sql)
  //       .addParameter("categoryId", this.getId())
  //       .addParameter("taskId", taskId)
  //       .executeUpdate();
  //   }
  // }
}
