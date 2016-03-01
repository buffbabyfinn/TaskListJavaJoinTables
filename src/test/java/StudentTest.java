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
}
