import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void save_savesTheCourseIntoTheDatabase() {
    Course testCourse = new Course("PE", "PE4567");
    testCourse.save();
    assertEquals(Course.all().size(), 1);
  }

  @Test
  public void find_findsCourseFromDatabase() {
    Course testCourse = new Course("PE", "PE4567");
    testCourse.save();
    Course savedCourse = Course.find(testCourse.getId());
    assertTrue(testCourse.equals(savedCourse));
  }

  @Test
  public void updateCourse_updatedTheNameOfTheCourse() {
    Course myCourse = new Course("CompSci", "PROG101");
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    savedCourse.updateCourse("Computer Science");
    assertEquals(Course.all().get(0).getCourseName(), "Computer Science");
  }

  @Test
  public void delete_deletesCourseFromDatabase() {
    Course myCourse = new Course("CompSci", "PROG101");
    myCourse.save();
    myCourse.deleteCourse();
    assertEquals(Course.all().size(), 0);
  }
}
