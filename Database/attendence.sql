CREATE TABLE attendance (
        id INT PRIMARY KEY AUTO_INCREMENT,
        roll_no VARCHAR(20),
        date DATE,
        status VARCHAR(10),  -- Present / Absent
        FOREIGN KEY (roll_no) REFERENCES students(roll_no)
     );
