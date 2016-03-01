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
    return last_name;
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

  public void addCourse(Course course) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (student_id, course_id, complete) VALUES (:student_id, :course_id, :complete)";
      con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .addParameter("course_id", course.getId())
        .addParameter("complete", false)
        .executeUpdate();
    }
  }

  public ArrayList<Course> getCourses() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT course_id FROM students_courses WHERE student_id = :student_id";
      List<Integer> courseIds = con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Course> courses = new ArrayList<Course>();

      for (Integer courseId : courseIds) {
        String courseQuery = "Select * FROM courses WHERE id = :courseId ORDER BY course_name";
        Course course = con.createQuery(courseQuery)
          .addParameter("courseId", courseId)
          .executeAndFetchFirst(Course.class);
        courses.add(course);
      }
      return courses;
    }
  }

  public void addDepartment(Department department) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_departments (student_id, department_id) VALUES (:student_id, :department_id)";
      con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .addParameter("department_id", department.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Department> getDepartments() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT department_id FROM students_departments WHERE student_id = :student_id";
      List<Integer> departmentIds = con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Department> departments = new ArrayList<Department>();

      for (Integer departmentId : departmentIds) {
        String departmentQuery = "Select * FROM departments WHERE id = :departmentId ORDER BY department_name";
        Department department = con.createQuery(departmentQuery)
          .addParameter("departmentId", departmentId)
          .executeAndFetchFirst(Department.class);
        departments.add(department);
      }
      return departments;
    }
  }

  // public void removeTask(int taskId) {
  //   try(Connection con = DB.sql2o.open()){
  //     String sql ="DELETE FROM categories_tasks WHERE category_id =  :categoryId AND task_id = :taskId";      con.createQuery(sql)
  //       .addParameter("categoryId", this.getId())
  //       .addParameter("taskId", taskId)
  //       .executeUpdate();
  //   }
  // }
}
