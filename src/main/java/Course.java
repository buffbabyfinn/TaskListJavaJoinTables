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


  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (student_id, course_id, complete) VALUES (:student_id, :course_id, :complete)";
      con.createQuery(sql)
        .addParameter("student_id", student.getId())
        .addParameter("course_id", this.getId())
        .addParameter("complete", false)
        .executeUpdate();
    }
  }

  public ArrayList<Student> getStudents() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT student_id FROM students_courses WHERE course_id = :course_id";
      List<Integer> studentIds = con.createQuery(sql)
        .addParameter("course_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Student> students = new ArrayList<Student>();

      for (Integer studentId : studentIds) {
          String taskQuery = "Select * From students WHERE id = :studentId ORDER BY last_name, first_name";
          Student student = con.createQuery(taskQuery)
            .addParameter("studentId", studentId)
            .executeAndFetchFirst(Student.class);
          students.add(student);
      }
      return students;
    }
  }

  public void addDepartment(Department department) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses_departments (department_id, course_id) VALUES (:department_id, :course_id)";
      con.createQuery(sql)
        .addParameter("department_id", department.getId())
        .addParameter("course_id", this.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Department> getDepartments() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT department_id FROM courses_departments WHERE course_id = :course_id";
      List<Integer> departmentIds = con.createQuery(sql)
        .addParameter("course_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Department> departments = new ArrayList<Department>();

      for (Integer departmentId : departmentIds) {
          String taskQuery = "Select * From departments WHERE id = :departmentId ORDER BY department_name";
          Department department = con.createQuery(taskQuery)
            .addParameter("departmentId", departmentId)
            .executeAndFetchFirst(Department.class);
          departments.add(department);
      }
      return departments;
    }
  }

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
