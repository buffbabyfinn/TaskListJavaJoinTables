import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import org.sql2o.*;

public class Course {
  private int id;
  private String course_name;
  private String course_number;

  public int getId() {
    return id;
  }

  public String getCourseName() {
    return course_name;
  }

  public String getCourseNumber() {
    return course_number;
  }


  public Course(String course_name, String course_number) {
    this.course_name = course_name;
    this.course_number = course_number;
  }

  @Override
  public boolean equals(Object otherCourse){
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getCourseName().equals(newCourse.getCourseName()) &&
             this.getId() == newCourse.getId() &&
             this.getCourseNumber().equals(newCourse.getCourseNumber());
    }
  }

  public static List<Course> all() {
    String sql = "SELECT * FROM courses ORDER BY course_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses (course_name, course_number) VALUES (:course_name, :course_number)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("course_name", course_name)
        .addParameter("course_number", course_number)
        .executeUpdate()
        .getKey();
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where id=:id";
      Course course = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return course;
    }
  }

  public void updateCourse(String course_name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET course_name = :course_name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("course_name", course_name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void deleteCourse() {
    String sql = "DELETE FROM courses WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();

    String courseDepartmentDeleteQuery = "DELETE FROM courses_departments WHERE course_id = :course_id";
    con.createQuery(courseDepartmentDeleteQuery)
      .addParameter("course_id", id)
      .executeUpdate();

    String courseStudentDeleteQuery = "DELETE FROM students_courses WHERE course_id = :course_id";
    con.createQuery(courseStudentDeleteQuery)
      .addParameter("course_id", id)
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
