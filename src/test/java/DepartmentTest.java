import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class DepartmentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Department.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Department firstDepartment = new Department("Maths");
    Department secondDepartment = new Department("Maths");
    assertTrue(firstDepartment.equals(secondDepartment));
  }

  @Test
  public void save_savesDepartmentToDatabase() {
    Department myDepartment = new Department("Maths");
    myDepartment.save();
    assertEquals(Department.all().size(), 1);
  }

  @Test
  public void find_findsDepartmentInTheDatabase() {
    Department myDepartment = new Department("Maths");
    myDepartment.save();
    assertEquals(Department.find(myDepartment.getId()), myDepartment);
  }

  @Test
  public void update_updatesDepartmentInTheDatabase() {
    Department myDepartment = new Department("Maths");
    myDepartment.save();
    myDepartment.update("Science");
    assertEquals(Department.find(myDepartment.getId()).getDepartmentName(), "Science");
  }

  @Test
  public void delete_deletesDepartmentInTheDatabase() {
    Department myDepartment = new Department("Maths");
    myDepartment.save();
    myDepartment.delete();
    assertEquals(Department.all().size(), 0);
  }

}
