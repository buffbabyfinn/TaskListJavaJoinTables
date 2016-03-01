import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Student firstStudent = new Student("Daren", "Schaad");
    Student secondStudent = new Student("Daren", "Schaad");
    assertTrue(firstStudent.equals(secondStudent));
  }

  @Test
  public void save_savesIntoDatabase() {
    Student firstStudent = new Student("Daren", "Schaad");
    firstStudent.save();
    assertTrue(Student.all().get(0).equals(firstStudent));
  }

  @Test
  public void all_returnsAllStudents() {
    Student firstStudent = new Student("Daren", "Schaad");
    firstStudent.save();
    assertEquals(Student.all().size(), 1);
  }

  @Test
  public void find_findStudentInDatabase() {
  Student firstStudent = new Student("Daren", "Schaad");
  firstStudent.save();
  Student savedStudent = Student.find(firstStudent.getId());
  assertTrue(firstStudent.equals(savedStudent));
  }

  @Test
  public void delete_deletesStudentFromDatabase() {
    Student myStudent = new Student("Megan", "Fayer");
    myStudent.save();
    myStudent.deleteStudent();
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void setEnrollmentDate_setsDateStudentsEnrolled() {
    String enrollment_date = "2016/05/30";
    Student myStudent = new Student("Megan", "Fayer");
    myStudent.save();
    myStudent.setEnrollmentDate(enrollment_date);
    assertEquals(Student.all().get(0).getEnrollmentDate(), "2016/05/30");
  }

  @Test
  public void addCourse_addsCourseToStudentInJoinTable() {
    Student myStudent = new Student("Megan", "Fayer");
    myStudent.save();
    Course testCourse = new Course("PE", "PE4567");
    testCourse.save();
    myStudent.addCourse(testCourse);
    Course savedCourse = myStudent.getCourses().get(0);
    assertTrue(testCourse.equals(savedCourse));
    assertEquals(myStudent.getCourses().get(0), testCourse);
  }

  @Test
  public void addDepartment_addsDepartmentToStudentInJoinTable() {
    Student myStudent = new Student("Megan", "Fayer");
    myStudent.save();
    Department testDepartment = new Department("PE");
    testDepartment.save();
    myStudent.addDepartment(testDepartment);
    Department savedDepartment = myStudent.getDepartments().get(0);
    assertTrue(testDepartment.equals(savedDepartment));
    assertEquals(savedDepartment, testDepartment);
  }


}
