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

  public Student(String first_name, String last_name) {
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

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students(first_name, last_name) VALUES (:first_name, :last_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("first_name", this.first_name)
        .addParameter("last_name", this.last_name)
        .executeUpdate()
        .getKey();
    }
  }

  public void deleteStudent() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM students WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();

      String studentCourseDeleteQuery = "DELETE FROM students_courses WHERE student_id = :student_id";
      con.createQuery(studentCourseDeleteQuery)
        .addParameter("student_id", id)
        .executeUpdate();

      String studentDepartmentDeleteQuery = "DELETE FROM students_departments WHERE student_id = :student_id";
      con.createQuery(studentDepartmentDeleteQuery)
        .addParameter("student_id", id)
        .executeUpdate();
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students where id = :id";
      Student student = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
      return student;
    }
  }

  public void setEnrollmentDate(String enrollment_date) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET enrollment_date = :enrollment_date WHERE id = :id";
      con.createQuery(sql)
      .addParameter("enrollment_date", enrollment_date)
      .addParameter("id", id)
      .executeUpdate();
    }
  }
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
