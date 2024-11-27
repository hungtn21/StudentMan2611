package vn.edu.hust.studentman

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

  private lateinit var listView: ListView
  private lateinit var students: ArrayList<Student>
  private lateinit var adapter: ArrayAdapter<Student>

  companion object {
    const val REQUEST_ADD = 1
    const val REQUEST_EDIT = 2
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Thiết lập Toolbar
    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    // Khởi tạo ListView và danh sách sinh viên
    listView = findViewById(R.id.studentListView)
    students = ArrayList()
    students.add(Student("Tran Van An", "20225000"))
    students.add(Student("Tran Van Cuong", "20225001"))
    students.add(Student("Tran Thi Bao", "20225002"))
    students.add(Student("Tran Thi Chau", "20225003"))
    students.add(Student("Tran Thi Diep", "20225004"))
    students.add(Student("Tran Thi Hoa", "20225005"))
    students.add(Student("Tran Thi Phuong", "20225006"))
    students.add(Student("Tran Thi Giang", "20225007"))
    students.add(Student("Tran Thi Hang", "20225008"))
    students.add(Student("Tran Thi Uyen", "20225009"))
    students.add(Student("Tran Thi Ngoc", "20225010"))
    students.add(Student("Tran Thi Mai", "20225011"))
    students.add(Student("Tran Thi Lan", "20225012"))
    students.add(Student("Tran Thi Khanh", "20225013"))
    students.add(Student("Tran Thi Phuc", "20225014"))


    // Thiết lập Adapter cho ListView
    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students)
    listView.adapter = adapter

    // Xử lý sự kiện khi click vào một mục trong danh sách
    listView.setOnItemClickListener { parent, view, position, id ->
      val selectedStudent = students[position]

      // Hiển thị hộp thoại với các tùy chọn
      val options = arrayOf("Edit", "Remove")
      val builder = AlertDialog.Builder(this)
      builder.setTitle("Choose an action")
        .setItems(options) { dialog, which ->
          when (which) {
            0 -> { // Edit
              val editIntent = Intent(this, EditStudentActivity::class.java)
              editIntent.putExtra("student_id", selectedStudent.studentId)
              editIntent.putExtra("student_name", selectedStudent.name)
              editIntent.putExtra("position", position)
              startActivityForResult(editIntent, REQUEST_EDIT)
            }
            1 -> { // Remove
              students.removeAt(position)
              adapter.notifyDataSetChanged()
            }
          }
        }
      builder.show()
    }
  }

  // Tạo Option Menu (Add new)
  override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
    return when (item.itemId) {
      R.id.add_new -> {
        // Chuyển đến AddStudentActivity
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, REQUEST_ADD)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  // Nhận kết quả trả về từ AddStudentActivity và EditStudentActivity
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK && data != null) {
      when (requestCode) {
        REQUEST_ADD -> {
          val name = data.getStringExtra("student_name")
          val studentId = data.getStringExtra("student_id")
          if (name != null && studentId != null) {
            students.add(Student(name, studentId))
            adapter.notifyDataSetChanged()
          }
        }
        REQUEST_EDIT -> {
          val position = data.getIntExtra("position", -1)
          val name = data.getStringExtra("student_name")
          val studentId = data.getStringExtra("student_id")
          if (position != -1 && name != null && studentId != null) {
            students[position] = Student(name, studentId)
            adapter.notifyDataSetChanged()
          }
        }
      }
    }
  }
}
