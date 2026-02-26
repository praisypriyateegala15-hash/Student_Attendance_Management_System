
mysql> CREATE TABLE teacher_sections (
         teacher_id INT,
        department VARCHAR(50),
       section VARCHAR(10),
        FOREIGN KEY (teacher_id) REFERENCES teachers(id)
    );