 CREATE TABLE teachers (
         id INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(100),
       email VARCHAR(100) UNIQUE,
        password VARCHAR(100),
        department VARCHAR(50)
     );