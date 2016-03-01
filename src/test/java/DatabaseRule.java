import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/university_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteCoursesQuery = "DELETE FROM courses *;";
      String deleteStudentsQuery = "DELETE FROM students *;";
      String deleteDepartmentsQuery = "DELETE FROM departments *;";
      String deleteCoursesDepartmentsQuery = "DELETE FROM courses_departments *;";
      String deleteStudentsCoursesQuery = "DELETE FROM students_courses *;";
      String deleteStudentsDepartmentsQuery = "DELETE FROM students_departments *;";
      con.createQuery(deleteCoursesQuery).executeUpdate();
      con.createQuery(deleteStudentsQuery).executeUpdate();
      con.createQuery(deleteDepartmentsQuery).executeUpdate();
      con.createQuery(deleteCoursesDepartmentsQuery).executeUpdate();
      con.createQuery(deleteStudentsCoursesQuery).executeUpdate();
      con.createQuery(deleteStudentsDepartmentsQuery).executeUpdate();
    }
  }
}
